import redis.clients.jedis.Jedis;

import java.util.LinkedHashMap;

public class LRUCache {
    LinkedHashMap<Integer, Integer> cache;
    private int cap;

    public LRUCache(int capacity) {
        this.cap = capacity;
        cache = new LinkedHashMap<>(capacity);
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        int value = cache.remove(key);
        cache.put(key, value);
        return value;
    }

    public void put(int key, int value) {
        if (cache.containsValue(key)) {
            cache.remove(key);
            cache.put(key, value);
            return;
        }
        cache.put(key, value);
        if (cache.size() > cap) {
            cache.remove(cache.keySet().iterator().next());
        }
    }

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        System.out.println(lRUCache.get(1));
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        System.out.println(lRUCache.get(2));    // 返回 -1 (未找到)
        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        System.out.println(lRUCache.get(1));    // 返回 -1 (未找到)
        System.out.println(lRUCache.get(3));    // 返回 3
        System.out.println(lRUCache.get(4));    // 返回 4
    }
}
