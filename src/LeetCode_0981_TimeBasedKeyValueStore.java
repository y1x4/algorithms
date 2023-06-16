import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeetCode_0981_TimeBasedKeyValueStore {

    /**
     * 1 <= key.length, value.length <= 100
     * key and value consist of lowercase English letters and digits.
     * 1 <= timestamp <= 107
     * All the timestamps timestamp of set are strictly increasing.
     * At most 2 * 105 calls will be made to set and get.
     *
     * binary search
     *
     * Time:  set O(1), get O(logN)
     * Space: O(N)
     */
    class TimeMap {

        private Map<String, List<Item>> map;

        public TimeMap() {
            map = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            List<Item> list = map.computeIfAbsent(key, k -> new ArrayList<>());
            list.add(new Item(timestamp, value));
        }

        public String get(String key, int target) {
            List<Item> list = map.get(key);
            if (list == null || list.isEmpty() || list.get(0).timestamp > target)
                return "";

            // find first item which timestamp <= target
            // find first item which timestamp < target + 1
            int lo = 0, hi = list.size(), mid;
            if (list.get(hi).timestamp <= target)
                return list.get(hi).value;

            while (lo < hi) {
                mid = (lo + hi) >>> 1;
                if (list.get(mid).timestamp < target + 1) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
            return list.get(lo - 1).value;
        }
    }

    class Item {
        int timestamp;
        String value;
        public Item(int t, String v) {
            timestamp = t;
            value = v;
        }
    }

    public static void main(String[] args) {
        LeetCode_0981_TimeBasedKeyValueStore.TimeMap obj = new LeetCode_0981_TimeBasedKeyValueStore().new TimeMap();
        obj.set("foo", "bar", 1);
        System.out.println(obj.get("foo", 1));
        System.out.println(obj.get("foo", 3));
        obj.set("foo", "bar2", 4);
        System.out.println(obj.get("foo", 4));
        System.out.println(obj.get("foo", 5));

    }
}
