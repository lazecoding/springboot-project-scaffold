package lazecoding.project.common.util.concurrent;

import lazecoding.project.common.util.cache.RedissonClientUtil;
import org.redisson.api.RLock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Locker
 * RLock 和 ReentrantLock 都是可重入锁
 *
 * @author lazecoding
 */
public class Locker {

    /**
     * 锁唯一标识
     */
    private final String lockKey;

    /**
     * 分布式锁
     */
    private RLock rLock;

    /**
     * 本地锁集合
     */
    private static final Map<String, ReentrantLock> LOCAL_LOCKS = new ConcurrentHashMap<>();

    /**
     * 构造锁对象
     **/
    public Locker(String lockKey) {
        this.lockKey = lockKey;
    }

    /**
     * 加锁
     **/
    public boolean tryLock() {
        boolean locked;
        if (RedissonClientUtil.enableRedis()) {
            this.rLock = RedissonClientUtil.getRedissonClient().getLock(this.lockKey);
            locked = this.rLock.tryLock();
        } else {
            ReentrantLock lock = LOCAL_LOCKS.computeIfAbsent(this.lockKey, k -> new ReentrantLock());
                locked = lock.tryLock();
        }
        return locked;
    }

    /**
     * 加锁
     **/
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        boolean locked;
        if (RedissonClientUtil.enableRedis()) {
            this.rLock = RedissonClientUtil.getRedissonClient().getLock(this.lockKey);
            locked = this.rLock.tryLock(timeout, unit);
        } else {
            ReentrantLock lock = LOCAL_LOCKS.computeIfAbsent(this.lockKey, k -> new ReentrantLock());
            locked = lock.tryLock(timeout, unit);
        }
        return locked;
    }

    /**
     * 解锁
     */
    public void unlock() {
        if (RedissonClientUtil.enableRedis()) {
            // 分布式锁
            this.rLock.unlock();
            this.rLock = null;
        } else {
            // 本地锁
            ReentrantLock lock = LOCAL_LOCKS.get(this.lockKey);
            if (lock != null) {
                lock.unlock();
                LOCAL_LOCKS.remove(this.lockKey);
            }
        }
    }

}
