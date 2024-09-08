package lazecoding.project.common.util.concurrent;

import lazecoding.project.common.util.cache.RedissonClientUtil;
import lazecoding.project.common.util.cache.RedissonOperator;
import org.redisson.api.RAtomicLong;
import org.springframework.util.StringUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * AtomicNumber 原子计数
 *
 * @author lazecoding
 */
public class AtomicNumber {

    /**
     * 唯一标识
     */
    private final String key;

    /**
     * AtomicLong Map
     */
    public static final Map<String, AtomicLong> ATOMIC_LONG_MAP = new ConcurrentHashMap<>();


    public AtomicNumber(String key) {
        if (!StringUtils.hasText(key)) {
            throw new NullPointerException("key is null");
        }
        this.key = key;
    }

    public AtomicNumber(String key, long value) {
        if (!StringUtils.hasText(key)) {
            throw new NullPointerException("key is null");
        }
        this.key = key;
        set(value);
    }


    public String getKey() {
        return key;
    }

    /**
     * 获取值
     */
    public long get() {
        if (RedissonClientUtil.enableRedis()) {
            RAtomicLong rAtomicLong = RedissonClientUtil.getRedissonClient().getAtomicLong(key);
            return rAtomicLong.get();
        } else {
            AtomicLong atomicLong = ATOMIC_LONG_MAP.computeIfAbsent(key, k -> new AtomicLong());
            return atomicLong.get();
        }
    }

    public void set(long value) {
        if (RedissonClientUtil.enableRedis()) {
            RAtomicLong rAtomicLong = RedissonClientUtil.getRedissonClient().getAtomicLong(key);
            rAtomicLong.set(value);
        } else {
            AtomicLong atomicLong = ATOMIC_LONG_MAP.computeIfAbsent(key, k -> new AtomicLong());
            atomicLong.set(value);
        }
    }

    /**
     * 删除值
     */
    public void delete() {
        if (RedissonClientUtil.enableRedis()) {
            RedissonOperator.delete(key);
        } else {
            // 处理 ATOMIC_LONG_MAP
            ATOMIC_LONG_MAP.remove(key);
        }
    }

    /**
     * 递增
     */
    public long incr() {
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.incr(key);
        } else {
            AtomicLong atomicLong = ATOMIC_LONG_MAP.computeIfAbsent(key, k -> new AtomicLong());
            return atomicLong.incrementAndGet();
        }
    }

    /**
     * 递增 N
     */
    public long incr(long step) {
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.incr(key, step);
        } else {
            AtomicLong atomicLong = ATOMIC_LONG_MAP.computeIfAbsent(key, k -> new AtomicLong());
            return atomicLong.addAndGet(step);
        }
    }

    /**
     * 递减
     */
    public long decr() {
        if (RedissonClientUtil.enableRedis()) {
            return RedissonOperator.decr(key);
        } else {
            AtomicLong atomicLong = ATOMIC_LONG_MAP.computeIfAbsent(key, k -> new AtomicLong());
            return atomicLong.decrementAndGet();
        }
    }

    /**
     * 递减 N
     */
    public long decr(long step) {
        return incr(Math.negateExact(step));
    }

}
