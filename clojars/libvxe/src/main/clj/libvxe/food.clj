(ns libvxe.food
  (:require
   [duratom.core]
   [clojure.tools.logging :as log]
   [compojure.api.sweet :refer :all]
   [ring.util.http-response :refer :all]
   [schema.core :as s])
  (:use
   [com.rpl.specter]
   [doric.core :only [table csv html org raw]]))



(def food-db (duratom.core/duratom
              :local-file
              :file-path "food-db"
              :init {}))


(defn add-food! [name c f p ss u]
  (swap! food-db
         assoc 
         (keyword name) {:carbs c
                         :fat f
                         :protein p
                         :calories (+ (* 4 c) (* 9 f) (* 4 p))
                         :serving-size ss
                         :units u
                         }))


(defn print-food-db []
  (let [headers [:name :carbs :fat :protein :calories :serving-size :units]
        data (for [[food stats] @food-db]
               {
                :name (name food)
                :carbs (:carbs stats)
                :fat (:fat stats)
                :protein (:protein stats)
                :calories (:calories stats)
                :serving-size (:serving-size stats)
                :units (:units stats)
                }
               )
        ]
    (println (table headers data))))

(defn print-food [foods]
  (let [headers [:name :carbs :fat :protein :calories :serving-size :units]
        data (for [food foods]
               {
                :name (name food)
                :carbs (:carbs (food @food-db))
                :fat (:fat (food @food-db))
                :protein (:protein (food @food-db))
                :calories (:calories (food @food-db))
                :serving-size (:serving-size (food @food-db))
                :units (:units (food @food-db))
                }
               )
        ]
    (println (table headers data))))

