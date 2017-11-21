(ns compact-uuids.core.test
  (:require
    [clojure.test :refer [deftest is are]]
    [compact-uuids.core :as uuid])
  (:import
    [java.util UUID]))


(deftest test-random
  (dotimes [_ 10000]
    (let [u (UUID/randomUUID)]
      (is (= u (uuid/read (uuid/str u)))))))


(deftest test-manual
  (are [uuid str] (and (= (uuid/str uuid) str)
                       (= uuid (uuid/read str)))
    #uuid "00000000-0000-0000-0000-000000000000" "0000000000000000000000"
    #uuid "00000000-0000-0001-0000-000000000001" "0000000000100000000001"
    #uuid "00000000-0000-003f-0000-000000000040" "0000000000~00000000010"
    #uuid "00000000-0000-0fff-0000-000000001000" "000000000~~00000000100"
    #uuid "ffffffff-ffff-ffff-ffff-ffffffffffff" "F~~~~~~~~~~F~~~~~~~~~~"))


(deftest test-read-both
  (let [u (UUID/randomUUID)]
    (is (= u (uuid/read-both (uuid/str u))))
    (is (= u (uuid/read-both (str u))))))