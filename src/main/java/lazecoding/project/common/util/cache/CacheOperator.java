package lazecoding.project.common.util.cache;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RBuckets;
import org.redisson.api.RLock;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 缓存操作类
 *
 * @author lazecoding
 */
public class CacheOperator {

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
     * 获取分布式锁 RLock
     *
     * @param lockKey 锁的唯一键
     */
    public static RLock getLock(String lockKey) {
        if (!StringUtils.hasText(lockKey)) {
            return null;
        }
        lockKey = "locker:" + lockKey;
        return RedissonClientUtil.getRedissonClient().getLock(lockKey);
    }

    /**
     * 私有，禁止实例化
     */
    private CacheOperator() {
    }
}
