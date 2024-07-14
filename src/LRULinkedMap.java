import java.util.LinkedHashMap;
import java.util.Map;

public class LRULinkedMap<K, V> {

    private final LinkedHashMap<K, V> cacheMap;

    public LRULinkedMap(int cacheSize) {
        cacheMap = new LinkedHashMap<>(cacheSize, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return cacheMap.size() > cacheSize;
            }
        };
    }

    public void put(K key, V value) {
        cacheMap.put(key, value);
    }

    public V get(K key) {
        return cacheMap.get(key);
    }
}
