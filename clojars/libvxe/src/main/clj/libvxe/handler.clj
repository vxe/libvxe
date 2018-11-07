(ns libvxe.handler
  (:require
   [duratom.core]
   [clojure.tools.logging :as log]
   [compojure.api.sweet :refer :all]
   [ring.util.http-response :refer :all]
   [schema.core :as s])
  (:use
   [libvxe.spotify]
   [libvxe.core]
   [libvxe.food]
   [com.rpl.specter]
   [doric.core :only [table csv html org raw]])
  (:gen-class))





(def food-directory
  (context "/v1" []
           :tags ["food-directory"]
           (GET "/add-food" []
                :return String
                :query-params [name :- String, carbs :- String, fat :- String, protein :- String, serving-size :- String,units :- String ]
                :summary ""
                (ok (cheshire.core/generate-string
                     (str name carbs fat protein))))
           (GET "/shopping-list" []
                :return String
                :query-params [foods :- String]
                :summary ""
                (ok (cheshire.core/generate-string ""))
                     ;; (str domain edge))))
           (GET "/generate-dish" []
                :return String
                :query-params [edge :- String]
                :summary ""
                (ok (cheshire.core/generate-string (str edge)))))))


(def meal-planner
  (context "/v1" []
           :tags ["meal-planner"]
           (POST "/set-calorie-target" []
                :return String
                :query-params [edge :- String]
                :summary ""
                (ok (cheshire.core/generate-string (str edge))))
           (POST "/set-carb-target" []
                :return String
                :query-params [edge :- String]
                :summary ""
                (ok (cheshire.core/generate-string (str edge))))
           (POST "/set-fat-target" []
                :return String
                :query-params [edge :- String]
                :summary ""
                (ok (cheshire.core/generate-string (str edge))))
           (POST "/set-protein-target" []
                :return String
                :query-params [edge :- String]
                :summary ""
                (ok (cheshire.core/generate-string (str edge))))
           )


  )



(s/defschema Pizza
  {:name s/Str
   (s/optional-key :description) s/Str
   :size (s/enum :L :M :S)
   :origin {:country (s/enum :FI :PO)
            :city s/Str}})

(def app
  (api
    {:swagger
     {:ui "/api-docs"
      :spec "/swagger.json"
      :data {:info {:title "Sample API"
                    :description "Compojure Api example"}
             :tags [{:name "api", :description "some apis"}]
             :consumes ["application/json"]
             :produces ["application/json"]}}}

    (context "/api" []
      :tags ["api"]

      (GET "/plus" []
        :return {:result Long}
        :query-params [x :- Long, y :- Long]
        :summary "adds two numbers together"
        (ok {:result (+ x y)}))

      (POST "/echo" []
        :return Pizza
        :body [pizza Pizza]
        :summary "echoes a Pizza"
        (ok pizza)))))
;; (def app2
;;   (api
;;    {:swagger
;;     {:ui "/"
;;      :spec "/swagger.json"
;;      :data {:info {:title "server"
;;                    :description "Server"}
;;             :tags [{:name "parsec", :description "some apis"}]}}}

;;    ;; food-directory
;;    ;; meal-planner
;;    ))
