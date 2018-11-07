(ns libvxe.spotify
  (:require [clojure.data.codec.base64]
            [cemerick.url]
            [home.lib :refer :all]
            [clojure.tools.logging :as log])
  (:use
   [com.rpl.specter :refer :all]
   [clojure.java.shell :only [sh]]))

(defn encode-base64 [original]
(String. (clojure.data.codec.base64/encode (.getBytes original)) "UTF-8"))

(def atm--client-secret (atom ""))

(def atm--client-id (atom ""))

(def atm--access-token (atom ""))

(def atm--authorization-code (atom ""))

(def atm--refresh-token (atom ""))

(defn io-web--set-access-token []
  (reset! atm--refresh-token
                     (-> "curl"
                         (sh
                          "-s"
                          "-H"
                          (str "Authorization: Basic " (encode-base64 (str
                                                                       @atm--client-id
                                                                       ":"
                                                                       @atm--client-secret)))
                          "-d"
                          "grant_type=authorization_code"
                          "-d"
                          (str "code=" @atm--authorization-code)
                          "-d"
                          (str "redirect_uri=" "http%3A%2F%2Flocalhost:8888%2Fspotify%2Flogin")
                          "https://accounts.spotify.com/api/token")
                         :out
                         (cheshire.core/parse-string true)
                         :refresh_token)))

(defn io-web--refresh-access-token []
  (reset! atm--access-token
          (-> "curl"
              (sh
               "-s"
               "-H"
               (str "Authorization: Basic " (encode-base64 (str
                                                            @atm--client-id
                                                            ":"
                                                            @atm--client-secret)))
               "-d"
               "grant_type=refresh_token"
               "-d"
               (str "refresh_token=" @atm--refresh-token)
               "https://accounts.spotify.com/api/token")
              :out
              (cheshire.core/parse-string true)
              :access_token)))

(defn io-mem--set-authorization-code [resp]
  (do 
    (reset! atm--authorization-code resp)
    resp))

(defn io-fs--read-configuration []
  (let [config-file (read-string (slurp (expand-home "~/config.edn")))
        client-secret  (:client-secret config-file)
        client-id (:client-id config-file)]
    (future (reset! atm--client-secret client-secret))
    (future (reset! atm--client-id client-id))))

(defn periodic-token-refresh [period]
  (future 
    (loop []
      (doall
       (count (io-web--refresh-access-token))
       (Thread/sleep period))
      (recur))))

(defn login []
  (let
      [key (do (println "login here\n https://accounts.spotify.com/authorize/?client_id=e11274026afa4840b9b715e7cb0d8fbb&response_type=code&redirect_uri=http://localhost:8888/spotify/login&scope=playlist-read-private%20user-library-read&state=34fFs29kd09")
               (flush) (read-line)
               )]
    (do
      (io-fs--read-configuration)
      (io-web--set-access-token)
      (io-web--refresh-access-token)
      (periodic-token-refresh 300000))))

(defn io-web--search [query]
  (try 
    (cheshire.core/parse-string
     (:out
      (sh "curl"
          "-s"
          "-H"
          (str "Authorization: Bearer " @atm--access-token)
          (str "https://api.spotify.com/v1/search?q=" query))) true)
    (catch Exception e
      (do (io-web--refresh-access-token)
          (log/error "token expired, try again though, it should be refreshed now")))))

(defn get-song-id [song artist]
  (distinct (select [ALL :artists ALL #(re-matches (re-pattern (str ".*" (capitalize-words artist) ".*")) (:name %)) :id]
                    (:items
                     (:tracks (io-web--search
                               (str (cemerick.url/url-encode (capitalize-words song))
                                    "+artist:"
                                    (cemerick.url/url-encode (capitalize-words artist))
                                    "&type=track")))))))

(def playlist:like-from-radio nil)

(def atm--playlists (atom []))

(defn io-web--get-playlists []
  (try
    (reset! atm--playlists
            (:items (cheshire.core/parse-string
                     (:out
                      (sh "curl"
                          "-s"
                          "-H"
                          (str "Authorization: Bearer " @atm--access-token)
                          (str "https://api.spotify.com/v1/me/playlists"))) true)))
    (catch Exception e
      (do (io-web--refresh-access-token)
          (log/error "token expired, try again though, it should be refreshed now")))))

(def atm--library-tracks (atom []))

(def atm--library-track-ids (atom []))

(def atm--library-track-id-map (atom {}))

(defn io-web--get-library-tracks []
  (let [auth-headers (str "Authorization: Bearer " @atm--access-token)]

    (try
      (do 
        (reset! atm--library-tracks
                (flatten (for [offset [0 50 100 150 200 250 300 350 400]]
                           (:items  (cheshire.core/parse-string
                                     (:out
                                      (sh "curl"
                                          "-s"
                                          "-H"
                                          (str "Authorization: Bearer " @atm--access-token)
                                          (str "https://api.spotify.com/v1/me/tracks?limit=50&offset=" offset))) true))
                           )))
        (doseq [track @atm--library-tracks]
          (swap! atm--library-track-ids conj (:id (:track track)))))
      (catch Exception e
        (do
          (prn (str "curl"
                    "-s"
                    "-H"
                    (str "Authorization: Bearer " @atm--access-token)
                    (str "https://api.spotify.com/v1/me/tracks")))
          (io-web--refresh-access-token)
          (log/error "token expired, try again though, it should be refreshed now"))))))

(defn mem--build-track-id-map! []
  (reset! atm--library-track-id-map
            (into {} (flatten (for [track @atm--library-tracks]
                      {(keyword (:id (:track track)))
                       track
                       }
                      )))
          )
)

(defn io-web--get-track-audio-features [id]
(try
  (cheshire.core/parse-string
                   (:out
                    (sh "curl"
                        "-s"
                        "-H"
                        (str "Authorization: Bearer " @atm--access-token)
                        (str "https://api.spotify.com/v1/audio-features/" id))) true)
  (catch Exception e
    (do (io-web--refresh-access-token)
        (log/error "token expired, try again though, it should be refreshed now")))))
