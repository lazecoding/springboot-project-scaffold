package lazecoding.project.common.util.cache;

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
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.delete(key);
        } else {
            CaffeineOperator.getInstance().delete(key);
            return 1L;
        }
    }

    /**
     * 删除多个缓存
     */
    public static long deletes(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return 0L;
        }
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.deletes(keys);
        } else {
            CaffeineOperator.getInstance().deletes(keys);
            return 1L;
        }
    }

    /**
     * 根据规则获取 keys
     */
    public static Iterable<String> keys(String pattern) {
        if (!StringUtils.hasText(pattern)) {
            return null;
        }
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.keys(pattern);
        } else {
            return CaffeineOperator.getInstance().keys(pattern);
        }
    }

    /**
     * 删除多个缓存
     */
    public static long deleteKeys(String pattern) {
        if (!StringUtils.hasText(pattern)) {
            return 0L;
        }
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.deleteKeys(pattern);
        } else {
            CaffeineOperator.getInstance().deleteKeys(pattern);
            return 1L;
        }
    }

    /**
     * 设置 ttl，单位 秒
     */
    public static boolean expire(String key, long ttl) {
        if (!StringUtils.hasText(key)) {
            return false;
        }
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.expire(key, ttl, TimeUnit.SECONDS);
        } else {
            return CaffeineOperator.getInstance().expire(key, ttl);
        }
    }


    /**
     * 设置 ttl
     */
    public static boolean expire(String key, long ttl, TimeUnit timeUnit) {
        if (!StringUtils.hasText(key)) {
            return false;
        }
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.expire(key, ttl, timeUnit);
        } else {
            return CaffeineOperator.getInstance().expire(key, ttl, timeUnit);
        }
    }

    /**
     * 获取 ttl，单位 秒
     */
    public static long ttl(String key) {
        if (!StringUtils.hasText(key)) {
            return -2L;
        }
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.ttl(key);
        } else {
            return CaffeineOperator.getInstance().ttl(key);
        }
    }

    /**
     * 获取 value
     */
    public static <T> T get(String key) {
        if (!StringUtils.hasText(key)) {
            return null;
        }
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.get(key);
        } else {
            return CaffeineOperator.getInstance().get(key);
        }
    }

    /**
     * 获取 values
     */
    public static <T> Map<String, T> gets(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return null;
        }
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.gets(keys);
        } else {
            return CaffeineOperator.getInstance().gets(keys);
        }
    }

    /**
     * 存储缓存
     */
    public static <T> void set(String key, T value) {
        if (!StringUtils.hasText(key)) {
            return;
        }
        if (RedissonClientUtil.enableRedis()) {
            RedissonOperator.set(key, value);
        } else {
            CaffeineOperator.getInstance().set(key, value);
        }
    }

    /**
     * 存储缓存并设置 ttl，单位 秒
     */
    public static <T> boolean set(String key, T value, long ttl) {
        if (!StringUtils.hasText(key)) {
            return false;
        }
        if (RedissonClientUtil.enableRedis()) {
            RedissonOperator.set(key, value, ttl);
        } else {
            CaffeineOperator.getInstance().set(key, value, ttl);
        }
        return true;
    }

    /**
     * 存储缓存并设置 ttl
     */
    public static <T> boolean set(String key, T value, long ttl, TimeUnit timeUnit) {
        if (!StringUtils.hasText(key)) {
            return false;
        }
        if (RedissonClientUtil.enableRedis()) {
            RedissonOperator.set(key, value, ttl, timeUnit);
        } else {
            CaffeineOperator.getInstance().set(key, value, ttl, timeUnit);
        }
        return true;
    }

    /**
     * 递增
     */
    public static long incr(String key) {
        if (!StringUtils.hasText(key)) {
            return 0L;
        }
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.incr(key);
        } else {
            return CaffeineOperator.getInstance().incr(key);
        }
    }

    /**
     * 递增 N
     */
    public static long incr(String key, long step) {
        if (!StringUtils.hasText(key)) {
            return 0L;
        }
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.incr(key, step);
        } else {
            return CaffeineOperator.getInstance().incr(key, step);
        }
    }

    /**
     * 递减
     */
    public static long decr(String key) {
        if (!StringUtils.hasText(key)) {
            return 0L;
        }
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.decr(key);
        } else {
            return CaffeineOperator.getInstance().decr(key);
        }
    }

    /**
     * 递减 N
     */
    public static long decr(String key, long step) {
        return incr(key, Math.negateExact(step));
    }

    /**
     * 私有，禁止实例化
     */
    private CacheOperator() {
    }
}
