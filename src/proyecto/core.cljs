(ns proyecto.core
  (:require [proyecto.states :refer [p-state]]
            [reagent.dom :as rd]
            ["framer-motion" :as framer :refer [motion]]))

(defn item [props]
  (let [{:keys [icon
                icon-fn
                cont-style
                cont-text
                cont-extra
                click-fn
                hover-fn]} props]

    [:> (.-div motion) (conj {:class (str "flex flex-col
                        items-center justify-center 
                        sm:w-32
                        w-20" " " cont-style)} cont-extra)
     [:> (.-div motion)
      (conj {:class "p-2 w-full h-full"
             :on-click click-fn
             :on-mouse-over hover-fn
             :whileHover {:scale 1.2}} icon-fn)

      [:img {:src icon}]]
     [:div cont-text]]))

(defn dialog-state [k]
  (-> @p-state
      (get-in [:dialog k])))
(defn merge! [props]
  (-> p-state
      (swap! #(update-in %1 [:dialog] merge %2) props)))

(defn put! [k v]
  (swap! p-state assoc k v))


  (defn dialog-event [props]
    (let [stay (dialog-state :go-to)
          {:keys [id go?]} props]
      (if (and (false? (dialog-state :started?))
               (false? (dialog-state :play?)))

        (merge! {:go-to (if (true? go?) id stay)
                 :started? true
                 :play? true
                 :index 0
                 :cursor 1
                 :k id})
        (do (merge! {:rejected true})
            (js/setTimeout #(merge! {:rejected false}) 600)))))

  (def proto
    {:ideas {:icon "assets/folder.svg"
             :icon-fn nil
             :cont-style nil
             :cont-text "Ideas~"
             :cont-extra nil
             :click-fn #(dialog-event {:id :ideas 
                                       :go? true})
             :hover-fn nil
             :dialog ["le diste clih a la carpeteeeta"
                      "ueeeeeeesa"
                      "magico no?"]}
     :corpus {:icon "assets/corpus.png"
              :icon-fn {:whileHover nil}
              :cont-style "border-2 border-black rounded-lg bg-white"
              :cont-text "Corpus"
              :cont-extra {:whileHover {:scale 1.3}}
              :click-fn nil
              :hover-fn #(dialog-event {:id :corpus
                                        :go? false})
              :dialog ["como una jaula de carne"
                       "pero util..."
                       ". . ."
                       "por que si no me muero"]}
     :persona {:icon "assets/persona.png"
               :icon-fn {:whileHover nil}
               :cont-style "border-2 border-black rounded-lg bg-white"
               :cont-text "Persona"
               :cont-extra {:whileHover {:scale 1.3}}
               :click-fn nil
               :hover-fn #(dialog-event {:id :persona
                                         :go? false})
               :dialog ["tengo una makarita mas carita" "je jEEeeeEEeeh"]}})

(let [grid-style
      "p-4 border-2 items-center justify-center  
       border-black bg-white
       grid
       grid-cols-2
       sm:grid-cols-4 
       grid-flow-row row-auto gap-4
       w-full h-full"]
  (defn grid [files]
    [:div {:class "mt-4 p-2"}
     [:div {:class grid-style}
      (if (< 1 (count files))
        (for [i (range (count files))]
          ^{:key i} [:div (nth files i)])
        [:div (first files)])]]))

(def play-audio false)
(defn dialog-bleep [] (when (true? play-audio)
                        (.play (js/Audio "/assets/audio.wav"))))

(defn read-text [ms started? play? index cursor text-list text]


  (cond (and (< cursor (count text))
             (true? started?)
             (true? play?))
        (do
          (when (= cursor 1) (dialog-bleep))
          (merge! {:play? true})
          (js/console.log cursor)

          (js/setTimeout
           #(do (when (not (= " " (nth text cursor)))
                  (dialog-bleep))
                (swap! p-state update-in
                       [:dialog :cursor] inc)) ms))

        (= index (dec (count text-list)))
        (merge! {:started? false
                 :play? false})
        (>= cursor (count text))
        (merge! {:play? false})))

(defn next-button [index text-list play?] [:button
                                           {:class "w-10 h-10 mt-2
               bg-black text-white
               rounded-md
               font-black
               border-none"
                                            :on-click #(when (and (< index (dec (count text-list))) (false? play?))
                                                         (merge! {:index (inc (dialog-state :index))
                                                                  :cursor 1
                                                                  :play? true}))}

                                           ">"])

(defn dialog-box []
  (let [ms 30
        started? (dialog-state :started?)
        rejected? (dialog-state :rejected)
        play? (dialog-state :play?)
        index (dialog-state :index)
        cursor (dialog-state :cursor)
        k (dialog-state :k)
        text-list (get-in proto [k :dialog])
        text  (nth text-list index)]

    (read-text ms started? play? index cursor text-list text)
    [:> (.-div motion)
     {:class "flex flex-col 
              fixed inset-x-0 bottom-0
              mx-2 mb-1 p-4
              rounded-md
              bg-white
              border-2 border-black
              font-mono
              font-medium"

      :animate {:height 300
                :scale 1}}

     (if (true? rejected?)
       [:h1 {:class "p-2 border-2 rounded-md border-black  bg-red-200 text-center  font-bold"} "'cucha carajo"]
       [:h1 {:class "p-2 border-2 rounded-md border-black text-center "} "'cucha..."])
     [:p {:class "mt-2 p-2 border-2 border-black"}
      (take cursor text)]

     (next-button index text-list play?)]))

(let [itm #(item (get proto %))
      ideas (itm :ideas)
      corpus (itm :corpus)
      persona (itm :persona)

      to-default [ideas]
      to-ideas [corpus corpus persona ideas]]
  
  (defn main []
  ;; (get-in proto [(dialog-state :go-to) :go-to])
    [:div proto
     (grid (cond (= (dialog-state :go-to) :default) to-default
                 (= (dialog-state :go-to) :ideas) to-ideas))
     (dialog-box)]))

(defn prueba [] (js/console.log ["key->" (dialog-state :k) "go->" (dialog-state :go-to)]))
(defn start []
  (rd/render [main]
             (. js/document (getElementById "proyecto"))))
