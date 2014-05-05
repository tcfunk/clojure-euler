;; Project Euler
;;
;; Multiples of 3 and 5

(ns com.teamgamestar
  (:require clojure.repl
            clojure.string))

(def failed-movie-titles ["Gone With the Moving Air" "Swellfellas"])

(eval (first ['failed-movie-titles 'failed-movie-titles]))

(or false false false [1 2 3 5])


;; Functions

(defn too-enthusiastic
  "Return a cheer that might be a bit too enthusiastic."
  [name]
  (str "OH. MY. GOD! " name " YOU ARE MOST DEFINITELY LIKE THE BEST "
       "MAN SLASH WOMAN EVER I LOVE YOU AND WE SHOULD RUN AWAY TO SOMEWHERE"))

(too-enthusiastic "Zelda")


;; Variable-Arity Functions

(defn some-chop
  ([chop-target chop-type]
    (str "I " chop-type " chop " chop-target "! Take that!"))
  ([chop-target]
    (some-chop chop-target "karate")))

(some-chop "Kanye West")


;; Rest-params (as in, the rest of the params)

(defn favorite-things
  [name & things]
  (str "Hi, " name ", here are my favorite things: "
       (clojure.string/join ", " things)))

(favorite-things "Suzie" "bubble gum" "sprinkles")


;; Destructuring

(defn my-first
  [[first-thing]]
  (str "First thing: " first-thing))

(my-first ["oven" "bike" "waraxe"])


;; Function body can contain any number of forms
;; Clojure returns the last one evaluated
(defn illustrative-function
  []
  (+ 1 304)
  30
  "joe")

(illustrative-function)


;; Anonymous Functions

((fn [x] (* x 3)) 8)

(#(* % 3) 8)


;; Bringing it all together??

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn has-matching-part?
  [part]
  (re-find #"^left-" (:name part)))

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps which have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts final-body-parts (conj final-body-parts part)]
        (if (has-matching-part? part)
          (recur remaining (conj final-body-parts (matching-part part)))
          (recur remaining final-body-parts))))))

(defn better-symmetrize-body-parts
  "Expects a seq of maps which have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (let [final-body-parts (conj final-body-parts part)]
              (if (has-matching-part? part)
                (conj final-body-parts (matching-part part))
                final-body-parts)))
          []
          asym-body-parts))

(symmetrize-body-parts asym-hobbit-body-parts)

(better-symmetrize-body-parts asym-hobbit-body-parts)

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-part-size-num (reduce + 0 (map :size sym-parts))
        target (inc (rand body-part-size-num))]
    (loop [[part & rest] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur rest (+ accumulated-size (:size part)))))))

(hit asym-hobbit-body-parts)

