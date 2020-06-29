(ns proyecto.states 
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce p-state (atom {:test 0
                        :counter 0
                        :dialog {:go-to :default
                                 :started? false
                                 :rejected? false
                                 :play? false
                                 :index 0
                                 :cursor 0
                                 :k :default}}))
