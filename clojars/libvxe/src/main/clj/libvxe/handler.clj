(ns libvxe.handler
  (:require
   [duratom.core]
   [clojure.tools.logging :as log]
   [compojure.api.sweet :refer :all]
   [ring.util.http-response :refer :all]
   [schema.core :as s])
  (:use
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
                :return Stringn
                :query-params [foods :- String]
                :summary ""
                (ok (cheshire.core/generate-string
                     (str domain edge))))
           (GET "/generate-dish" []
                :return String
                :query-params [edge :- String]
                :summary ""
                (ok (cheshire.core/generate-string (str edge))))))


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



(def app
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "server"
                   :description "Server"}
            :tags [{:name "parsec", :description "some apis"}]}}}

   food-directory
   meal-planner
   ))
