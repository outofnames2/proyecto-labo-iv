(ns proyecto.core
  (:require [proyecto.states :refer [p-state]]
            [reagent.dom :as rd]
            ["framer-motion" :as fr :refer [motion]]))

(def play-audio false)
(defn dialog-bleep [] (when (true? play-audio)
                        (.play (js/Audio "/assets/audio.wav"))))
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

(defn anim-state [k]
  (-> @p-state
      (get-in [:anim k])))
(defn dialog-state [k]
  (-> @p-state
      (get-in [:dialog k])))
(defn merge! [props]
  (-> p-state
      (swap! #(update-in %1 [:dialog] merge %2) props)))

(defn put! [k v]
  (swap! p-state assoc-in k v))
(defn change! [k v]
  (swap! p-state update-in k v))

  (defn dialog-event [props]
    (let [stay (dialog-state :go-to)
          old-id (dialog-state :k)
          {:keys [id go text]} props]
      (if (and (not (dialog-state :started?))
               (not (dialog-state :play?)))
        (do
          (put! [:anim :open-dialog] true)
          (merge! {:go-to (if (nil? go) stay go)
                   :started? true
                   :play? true
                   :index 0
                   :cursor 1
                   :text (if (nil? text) "-" text)
                   :k (if (nil? id) old-id id)}))
        (do (merge! {:rejected true})
            (js/setTimeout #(merge! {:rejected false}) 600)))))

  (def proto
    {:ideas {:icon "assets/folder.svg"
             :icon-fn nil
             :cont-style nil
             :cont-text "Ideas~"
             :cont-extra nil
             :click-fn #(dialog-event {:id :ideas
                                       :go :ideas
                                       :text ["Bueno, aca estan algunas cosas que pense previamente"
                                              "vamos a ver que onda"
                                              "a ver si encontramos La Idea"
                                              "yeeeh"]})
             :hover-fn nil}
     :corpus {:icon "assets/corpus.png"
              :icon-fn {:whileHover nil}
              :cont-style "border-2 border-black rounded-lg bg-white"
              :cont-text "Corpus"
              :cont-extra {:whileHover {:scale 1.3}}
              :click-fn (fn []
                          (when (and (not (dialog-state :started?))
                                     (not (dialog-state :play?))) (put! [:anim :open-img] true))
                          (when (> 2 (dialog-state :counter))
                            (dialog-event {:id :corpus
                                           :text ["Ahhh yehh, pense en algo hablando sobre el cuerpo."
                                                  "El cuerpo pensado como interfaz, 
                        una herramienta que nos permite interactuar con el mundo basicamente"
                                                  "pensado asi porque ehm.."
                                                  "..."
                                                  "pienso eso,"
                                                  "je"]})))


              :hover-fn nil}
     :persona {:icon "assets/persona.png"
               :icon-fn {:whileHover nil}
               :cont-style "border-2 border-black rounded-lg bg-white"
               :cont-text "Persona"
               :cont-extra {:whileHover {:scale 1.3}}
               :click-fn (fn []
                           (when (and (not (dialog-state :started?))
                                      (not (dialog-state :play?))) (put! [:anim :open-img] true))
                           (when (> 2 (dialog-state :counter))
                             (dialog-event {:id :persona
                                            :text ["hhmmm,"
                                                   "persona. . . recuerdo que descubri el termino en un juego."
                                                   "No el del dia a dia jeje"
                                                   "pero que en latin significa 'Mascara' o algo asi"
                                                   "quede como ':0' porque era y es como veo a la identidad y esas cosas lokas."
                                                   "Asi que por eso ta'en latin"
                                                   ". . ."
                                                   "y lo'tro porque se ve pioh nomas,"
                                                   "se llamaba Persona 3 el juego a todo esto"
                                                   "...je, personapersonapersona"]})))
               :hover-fn nil}
     :ideasno {:icon "assets/folder.svg"
               :cont-text "Ideas~"}
     :recuerdos {:icon "assets/folder.svg"
                 :cont-text "Recuerdos"
                 :click-fn #(dialog-event {:id :recuerdos
                                           :go :recuerdos
                                           :text ["aaaaaaaaaaaaaaaaaAAAAAAAH"
                                                  "cierto, taba todo vacio aca"
                                                  "me habia olvidad--"
                                                  "ah aclaro, si me acordara tendria que estar aca eso, jeje"
                                                  "bueno eeehmm.. . ."
                                                  "me estoy desesperando un poco, tal vez, creo"
                                                  ". . ."
                                                  "uh eh, no puse un boton de atras"
                                                  "eeeeeehmmmm"
                                                  "..."
                                                  "claramente no voy a poder hacer esto"
                                                  "el deseo no me mueve, el interes tampoco..."
                                                  "excepto para observar pasivamente el mundo, pero al momento de hacer..."
                                                  "*sigh*"
                                                  "y a la hora de compartir, creo que prefiero.... algo mas directo, personal"
                                                  "no siento la necesidad de comunicarme con alguien a travez de una obra, no quiere decir que no lo haga"
                                                  "pero el deseo no esta"
                                                  "y aun asi me interesa?"
                                                  "a este punto no se ni si pienso lo que estoy diciendo o simplemente lo estoy diciendo"
                                                  "ambas?"
                                                  "nuse"
                                                  "nidea"
                                                  ". . ."
                                                  "tendria que haber puesto un boton para volver al inicio no?"
                                                  "al inicio vacio, porque no termine.... vacio"
                                                  "vaaaaacio"
                                                  ". . ."
                                                  "bueno hora de cortar el chorro"
                                                  "se pincho todo"
                                                  "esta incompleta exploracion a resultado ser desastrosa"
                                                  "o lo seria si hubiese algo que pueda serlo"
                                                  "qUe NoH AAy"
                                                  "PorqQUE nOol O isE, Ni lO reEcordeNisEnti"
                                                  "leik always"
                                                  "... bueno,"
                                                  "gracias por pasarte por aca"
                                                  "ahora te digo lo que saque de todo esto,"
                                                  "aunque para saber posta vas a tener que abrir la consola del navegador"
                                                  "mi conclusion es. . . . . ."
                                                  ]})
                 }
     :vacio {:icon "assets/vacio.png"} })

(let [grid-style
      "p-4 border-2 items-center justify-center  
       border-black bg-white rounded-md
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



 (defn read-text [ms started? play? index cursor text-list text]


     (cond (and (< cursor (count text))
                (true? started?)
                (true? play?))
           (do
             (when (= cursor 1) (dialog-bleep))
             (merge! {:play? true})

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

(defn dialog-box []
  (let [vo {:open {:height 300
                   :y 0}
            :close {:height 200
                    :y 138 }}
        valert  {:alert {:color "red"
                         :background-color "rgb(255, 180, 180)"
                         :scale 1.01
                         :transition {:duration 0}}
                 :normal {:color "black"
                          :background-color "rgb(255, 255, 255)"
                          :scale 1
                          :transition {:duration 0.2}}}
        open (anim-state :open-dialog)
        ms 1
        started? (dialog-state :started?)
        rejected? (dialog-state :rejected)
        play? (dialog-state :play?)
        index (dialog-state :index)
        cursor (dialog-state :cursor)
        text-list (dialog-state :text)
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
      :animate (if open "open" "close")
      :initial false
      :variants vo}

       [:> (.-div motion)
        {:class "flex justify-around items-center 
                      p-2 border-2 
                      rounded-md border-black text-center"
         :variants valert
         :animate (if rejected? "alert" "normal")
         :initial false}
        [:> (.-button motion)
         {:class "w-6 h-6 
                  rounded-md border-2 border-black
                  text-center text-black font-black
                  focus:outline-none"
          :animate (if open {:rotate 180} {:rotate 0})
          :initial false
          :on-click #(change! [:anim :open-dialog] not)}
         "^"]
        [:> (.-div motion)
         {:class "font-bold"
          :variants valert
          :animate (if rejected? "alert" "normal")
          :initial false}
         (if rejected? "'CUCHA CARAJO" "'cucha...")]
        [:div]]
     [:> (.-p motion)
      {:class "p-2 mt-2 border-2 rounded-md 
               h-full text-xl
               border-black 
               hover:border-gray-500
               hover:text-gray-500"
       :on-click #(when
                   (and (< index (dec (count text-list))) (false? play?))
                    (merge! {:index (inc (dialog-state :index))
                             :cursor 1
                             :play? true}))
       :whileTap {:scale 0.95}}
      (take cursor text)]

     [:div {:class "mt-2 flex flex-col justify-center items-center"}
      [:div {:class "mt-2 p-2 border-2 
                    rounded-md border-black
                    bg-red-200 text-center  font-bold"} (dialog-state :counter)]]]))




(defn img-open [k]
     [:div [:> (.-div motion)
            {:class "fixed inset-0 bg-black opacity-50 h-screen"
             :animate {:opacity 0.7}
             :exit {:opacity 0}}]
      [:> (.-div motion)
       {:class "flex justify-center 
                items-center fixed
                h-full w-full 
                p-8"
        :initial {:opacity 0}
        :animate {:opacity 1}
        :exit {:opacity 0}}

       [:div {:class "relative mb-20 flex flex-col 
                     items-center 
                     sm:max-w-xl max-h-screen"}
        [:img
         {:class "h-48 lg:h-auto rounded-md bg-gray-100"
          :src (get-in proto [k :icon])}]

        [:> (.-button motion) {:class "mt-2 rounded-full bg-teal-300
                         w-10 h-10 p-2
                         text-center text-teal-800 
                         focus:outline-none
                         hover:text-teal-800
                         hover:bg-teal-100 
                         font-black"
                               :on-click #(do
                                            (when
                                             (and (not (dialog-state :started?))
                                                  (not (dialog-state :play?)))
                                              (change! [:dialog :counter] inc)
                                              (put! [:anim :open-img] false))
                                            (if (= 1 (dialog-state :counter))
                                              (dialog-event {:id :img-close
                                                             :go-to :ideas
                                                             :text
                                                             ["ueesap, una idea mas"]})
                                              (dialog-event {:id :img-close
                                                             :go :default
                                                             :text
                                                             ["Ahhhhhhh, bueno"
                                                              "bien"
                                                              "esas eran unas ideas..."
                                                              "yeeeeeeeeeeeeeeeah"
                                                              "ahora definitivamente tengo el trabajo resuelto"
                                                              "..."
                                                              ". . ."
                                                              "nosemeocurrenada"
                                                              "bueno, veamos dentro de mis recuerdos supongo"]})))
                               :whileHover {:scale 2}
                               :transition {:yoyo "Infinity"
                                            :duration 1}} "?"]]]])
(let [itm #(item (get proto %))
      ideas (itm :ideas)
      corpus (itm :corpus)
      persona (itm :persona)
      vacio (itm :vacio)
      
      ideasno (itm :ideasno)
      recuerdos (itm :recuerdos)
      to-default [ideas]
      to-ideas [corpus persona]
      to-default-2 [ideasno recuerdos]
      to-recuerdos [vacio vacio vacio vacio vacio vacio vacio vacio vacio vacio vacio vacio
                    vacio vacio vacio vacio vacio vacio vacio vacio vacio vacio vacio vacio]
      ]
  
  
  (def dust-to-dust
    {:vacio true
     :startcounter 0
     :dialog {:counter 0
              :go-to :default
              :started? false
              :rejected? false
              :play? false
              :index 0
              :cursor 0
              :text [""]
              :k :default}
     :anim {:open-dialog false
            :open-img false}})
  (defn main []

    
      [:div (if-not (> 2 (get @p-state :startcounter))
              [:div
               [:> fr/AnimatePresence
                (cond-> (dialog-state :k)
                  (anim-state :open-img) (img-open))]
               (grid (cond (and (= (dialog-state :go-to) :default)
                                (< 1 (dialog-state :counter))) to-default-2
                           (= (dialog-state :go-to) :default) to-default
                           (= (dialog-state :go-to) :ideas) to-ideas
                           (and (not (dialog-state :started?))
                                (= (dialog-state :go-to) :recuerdos))
                           (do (swap! p-state assoc :startcounter 0))
                           (= (dialog-state :go-to) :recuerdos) to-recuerdos))

               (dialog-box)]
              [:div {:class "flex justify-center items-center flex-col bg-gray-200 h-screen w-screen"}
               [:p (cond (= 0 (get @p-state :startcounter))
                         "no termine mi trabajo"
                         (= 1 (get @p-state :startcounter))
                         ".....me ayudarias a completarlo?")]
               [:button {:class "border-2 border-black w-5"
                         :on-click #(change! [:startcounter] inc)} ">"]])]
      
      ))

(defn prueba [] (js/console.log ["key->" (dialog-state :k) "go->" (dialog-state :go-to)]))
(defn start []
  (rd/render [main]
             (. js/document (getElementById "proyecto"))))
