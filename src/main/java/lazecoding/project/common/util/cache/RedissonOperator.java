package lazecoding.project.common.util.cache;

import org.checkerframework.checker.units.qual.K;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RBuckets;
import org.redisson.api.RMap;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * RedissonOperator
 *
 * @author lazecoding
 */
public class RedissonOperator {

    /**
     * key 是否存在
     */
    public static boolean exists(String key) {
        if (!StringUtils.hasText(key)) {
            return true;
        }
        long ttl = ttl(key);
        return ttl(key) == -1L || ttl > 0;
    }

    /**
     * 删除 key
     */
    public static long delete(String key) {
        if (!StringUtils.hasText(key)) {
            return 0L;
        }
        return RedissonClientUtil.getRedissonClient().getKeys().delete(key);
    }

    /**
     * 删除多个缓存
     */
    public static long deletes(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return 0L;
        }
        return RedissonClientUtil.getRedissonClient().getKeys().delete(keys.toArray(new String[0]));
    }

    /**
     * 根据规则获取 keys
     */
    public static Iterable<String> keys(String pattern) {
        if (!StringUtils.hasText(pattern)) {
            return null;
        }
        return RedissonClientUtil.getRedissonClient().getKeys().getKeysByPattern(pattern);
    }

    /**
     * 删除多个缓存
     */
    public static long deleteKeys(String pattern) {
        if (!StringUtils.hasText(pattern)) {
            return 0L;
        }
        return RedissonClientUtil.getRedissonClient().getKeys().deleteByPattern(pattern);
    }

    /**
     * 设置 ttl，单位 秒
     */
    public static boolean expire(String key, long ttl) {
        return RedissonClientUtil.getRedissonClient().getKeys().expire(key, ttl, TimeUnit.SECONDS);
    }


    /**
     * 设置 ttl
     */
    public static boolean expire(String key, long ttl, TimeUnit timeUnit) {
        return RedissonClientUtil.getRedissonClient().getKeys().expire(key, ttl, timeUnit);
    }

    /**
     * 获取 ttl，单位 秒
     */
    public static long ttl(String key) {
        return RedissonClientUtil.getRedissonClient().getKeys().remainTimeToLive(key);
    }

    /**
     * 获取 value
     */
    public static <T> T get(String key) {
        if (!StringUtils.hasText(key)) {
            return null;
        }
        RBucket<T> rBucket = RedissonClientUtil.getRedissonClient().getBucket(key);
        return rBucket.get();
    }

    /**
     * 获取 values
     */
    public static <T> Map<String, T> gets(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return null;
        }
        RBuckets rBuckets = RedissonClientUtil.getRedissonClient().getBuckets();
        return rBuckets.get(keys.toArray(new String[0]));
    }

    /**
     * 存储缓存
     */
    public static <T> void set(String key, T value) {
        RBucket<T> rBucket = RedissonClientUtil.getRedissonClient().getBucket(key);
        rBucket.set(value);
    }

    /**
     * 存储缓存并设置 ttl，单位 秒
     */
    public static <T> void set(String key, T value, long ttl) {
        RBucket<T> rBucket = RedissonClientUtil.getRedissonClient().getBucket(key);
        rBucket.set(value, ttl, TimeUnit.SECONDS);
    }

    /**
     * 存储缓存并设置 ttl
     */
    public static <T> boolean set(String key, T value, long ttl, TimeUnit timeUnit) {
        RBucket<T> rBucket = RedissonClientUtil.getRedissonClient().getBucket(key);
        rBucket.set(value, ttl, timeUnit);
        return true;
    }

    /**
     * 递增
     */
    public static long incr(String key) {
        if (!StringUtils.hasText(key)) {
            return 0L;
        }
        RAtomicLong rAtomicLong = RedissonClientUtil.getRedissonClient().getAtomicLong(key);
        return rAtomicLong.incrementAndGet();
    }

    /**
     * 递增 N
     */
    public static long incr(String key, long step) {
        if (!StringUtils.hasText(key)) {
            return 0L;
        }
        RAtomicLong rAtomicLong = RedissonClientUtil.getRedissonClient().getAtomicLong(key);
        return rAtomicLong.addAndGet(step);
    }

    /**
     * 递减
     */
    public static long decr(String key) {
        if (!StringUtils.hasText(key)) {
            return 0L;
        }
        RAtomicLong rAtomicLong = RedissonClientUtil.getRedissonClient().getAtomicLong(key);
        return rAtomicLong.decrementAndGet();
    }

    /**
     * 递减 N
     */
    public static long decr(String key, long step) {
        return incr(key, Math.negateExact(step));
    }

    /**
     * 设置值,如果具有指定键的映射项已经存在，则返回先前的值，新键返回 null
     */
    public static <K, V> V mapPut(String key, K item, V value) {
        RMap<K, V> rMap = RedissonClientUtil.getRedissonClient().getMap(key);
        return rMap.put(item, value);
    }

    /**
     * 设置值,仅当之前没有存储带有指定键的值时，才存储按键映射的指定值。
     */
    public static <K, V> V mapPutIfAbsent(String key, K item, V value) {
        RMap<K, V> rMap = RedissonClientUtil.getRedissonClient().getMap(key);
        return rMap.putIfAbsent(item, value);
    }


    /**
     * 设置值,仅当映射已经存在时，才存储按键映射的指定值。
     */
    public static <K, V> V mapPutIfExists(String key, K item, V value) {
        RMap<K, V> rMap = RedissonClientUtil.getRedissonClient().getMap(key);
        return rMap.putIfExists(item, value);
    }

    /**
     * 获取 Map 指定键
     */
    public static <K, V> V mapGet(String key, K item) {
        RMap<K, V> rMap = RedissonClientUtil.getRedissonClient().getMap(key);
        return rMap.get(item);
    }

    /**
     * 移除 Map 指定键
     */
    public static <K, V> V mapRemove(String key, K item) {
        RMap<K, V> rMap = RedissonClientUtil.getRedissonClient().getMap(key);
        return rMap.remove(item);
    }

    /**
     * 批量移除 Map 指定键
     */
    public static <K, V> long mapRemove(String key, K... items) {
        if (items == null || items.length == 0) {
            return 0L;
        }
        RMap<K, V> rMap = RedissonClientUtil.getRedissonClient().getMap(key);
        return rMap.fastRemove(items);
    }

    /**
     * 获取 Map 全部键
     */
    public static <K, V> Set<K> mapKeys(String key) {
        RMap<K, V> rMap = RedissonClientUtil.getRedissonClient().getMap(key);
        return rMap.keySet();
    }

    /**
     * 根据规则获取 Map 键
     */
    public static <K, V> Set<K> mapKeys(String key, String pattern) {
        if (!StringUtils.hasText(pattern)) {
            return null;
        }
        RMap<K, V> rMap = RedissonClientUtil.getRedissonClient().getMap(key);
        return rMap.keySet(pattern);
    }

    /**
     * 获取 Map 全部值
     */
    public static <K, V> Collection<V> mapValues(String key) {
        RMap<K, V> rMap = RedissonClientUtil.getRedissonClient().getMap(key);
        return rMap.values();
    }

    /**
     * 根据规则获取 Map 值
     */
    public static <K, V> Collection<V> mapValues(String key, String pattern) {
        if (!StringUtils.hasText(pattern)) {
            return null;
        }
        RMap<K, V> rMap = RedissonClientUtil.getRedissonClient().getMap(key);
        return rMap.values(pattern);
    }


    /**
     * 私有，禁止实例化
     */
    private RedissonOperator() {
    }
}
