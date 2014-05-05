;; Project Euler
;;
;; Multiples of 3 and 5

(ns com.teamgamestar
  (:require clojure.repl
            clojure.string))

(defn is-divisible
  [n]
  (or (= 0 (mod n 3))
      (= 0 (mod n 5))))

(reduce + (reduce (fn [nums n]
                    (if (is-divisible n)
                      (conj nums n)
                      nums))
                  []
                  (take 1000 (iterate inc 0))))
