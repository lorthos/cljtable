(ns cljtable.store.merger
  (:require [cljtable.store.segment :as s]
            [cljtable.store.common :as c])
  (:import (java.util.concurrent Executors)
           (cljtable.store.segment ReadOnlySegment)))

(def merger-exec (Executors/newSingleThreadExecutor))

(defn get-merge-candidate-ids
  "get the id of the segments to be merged

  1000 1001 1002 (1003)->
  0 1002 (1003->
  1 (1003)

  "
  [read-only-segments]
  ;must have at least 2 active segments
  (when (>= (count read-only-segments) 2)
    (take 2 (sort < read-only-segments))
    )
  )

(defn make-merge!
  "merge the two segments and return a new ReadOnlySegment

  Will run in a seperate single background thread"
  [^ReadOnlySegment older-segment ^ReadOnlySegment newer-segment]
  (let [added-keys (atom '())
        deleted-keys (atom '())]
    ;TODO
    )
  ;both segments has indices
  ;for each key in index1
  ;walk on the newer segment
  ;added '()
  ;deleted '()
  ;
  ;walk on the older segment
  ;append to added or deleted if it does not exist

  )

(defn merge!
  [older-segment-id newer-segment-id]
  (let [segment1 (get @s/old-segments older-segment-id)
        segment2 (get @s/old-segments newer-segment-id)]
    (c/do-sequential merger-exec (make-merge! segment1 segment2))
    )
  )