(ns cljtable.store.loader
  (:require [cljtable.store.reader :as r])
  (:import (java.nio.channels SeekableByteChannel)))

(defn read-next-key-and-offset-and-increment!
  "given a channel that is at the end position of a record (or at the beginning of the file)
  1. reads the length of the key
  2. reads the key
  3. reads the operation type
  4. if update - reads the length of the value
  5. reads the value
  6. calculates total bytes read returns the key and new offset"
  [chan offset-atom]
  ;TODO skip instead of read?
  (let [old-offset @offset-atom
        kl (r/read-int-from-chan chan)
        k (r/read-nippy-from-chan chan kl)
        op_type (r/read-byte-from-chan chan)]
    (if (= op_type (byte 41))
      (let [vl (r/read-int-from-chan chan)
            v (r/read-nippy-from-chan chan vl)]
        (do
          (swap! offset-atom + 4 kl 1 4 vl)
          {:key k :old-offset old-offset :new-offset @offset-atom}))
      (do
        (swap! offset-atom + 4 kl 1)
        {:key k :old-offset old-offset :new-offset @offset-atom})
      )))

(defn append-next-line-to-index!
  "given an index in the atom, append the read result to the index and return the latest offset"
  [index-atom read-result]
  (swap! index-atom assoc (:key read-result) (:old-offset read-result))
  (:new-offset read-result))

(defn load-index
  "goes through all the keys and re-construct {:key offset} hash index
  1. position the chan to the beginning offset
  2. read and assoc accordingly"
  [^SeekableByteChannel chan]
  (.position chan 0)
  (let [offset-atom (atom 0)
        end (.size chan)
        index (atom {})]
    (loop [current 0]
      (if (= current end)
        @index
        (recur (append-next-line-to-index! index (read-next-key-and-offset-and-increment! chan offset-atom)))
        )
      )
    )
  )

(loop [i 5 acc 1]
  (println i acc)
  (if (zero? i)
    acc
    (recur (dec i) (* acc i))))