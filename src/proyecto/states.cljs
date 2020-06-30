(ns proyecto.states 
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce p-state (atom {:startcounter 0
                        :dialog {:counter 0
                                 :go-to :default
                                 :started? true
                                 :rejected? false
                                 :play? false
                                 :index 0
                                 :cursor 0
                                 :text [""]
                                 :k :default}
                        :anim {:open-dialog false
                               :open-img false}}))
