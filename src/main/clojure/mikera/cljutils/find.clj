(ns mikera.cljutils.find)

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defn indexed?
  ([coll]
    (instance? clojure.lang.Indexed coll))) 

(defn find-first [pred coll]
  "Searches a collection and returns the first item for which pred is true, or nil if not found.
   Like 'some', except it returns the value from the collection (rather than the result of 
   applying the predicate to the value). This is often more useful.
   Note that it is possible to find and return a nil value if the collection contains nils."
  (loop [s (seq coll)] 
    (when s  
      (let [v (first s)]
        (if (pred v)
          v
          (recur (next s)))))))

(defn find-index
  "Searches a collection and returns the index of the first item for which pred is true.
   Returns -1 if not found"
  (^long [pred coll]
    (if (indexed? coll)
      (let [c (count coll)]
        (loop [i 0]
          (if (< i c) 
            (if (pred (nth coll i)) i (recur (inc i)))
            -1)))
      (loop [i 0 s (seq coll)]
        (if s
          (if (pred (first s)) i (recur (inc i) (next s)))
          -1))))) 

(defn find-position 
  "Searches a collection and returns the (long) index of the item's position."
  (^long [coll item] 
    (find-position coll item 0))
  (^long [coll item ^long i] 
    (if (empty? coll) 
      nil
	    (let [v (first coll)]
	      (if (= item v)
	        i
	        (recur (rest coll) item (inc i)))))))