(ns cljtable.store.segment-test
  (:require [clojure.test :refer :all]
            [cljtable.store.segment :refer :all]
            [cljtable.store.writer :as w]
            [cljtable.store.reader :as r]))

(defn segment-fixture [f]
  (reset! active-segment nil)
  (reset! old-segments {})
  (f)
  (close-segment-fully! @active-segment)
  (reset! active-segment nil))

(use-fixtures :each segment-fixture)

(deftest roll-new-segment-test
  (testing "adding a new segment and querying afterwards"
    ;TODO
    (is (nil? @active-segment))
    ;(is (nil? @old-segments))
    (is ((comp not nil?) (do
                           (roll-new-segment! 0)
                           @active-segment)))
    (println @active-segment)
    (w/write! "A" "B" @active-segment)
    (is (= "B" (r/read-direct "A" @active-segment)))
    (roll-new-segment! 1)
    (is (nil? (r/read-direct "A" @active-segment)))
    (w/write! "A" "B" @active-segment)
    (is (= "B" (r/read-direct "A" @active-segment)))
    (println @active-segment)
    (println @old-segments)
    ))
