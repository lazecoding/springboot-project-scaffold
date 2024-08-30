package lazecoding.project.common.util.cache;

import lazecoding.project.common.util.BeanUtil;
import lazecoding.project.config.RedissonConfigProperties;
import org.redisson.api.RedissonClient;
import org.springframework.util.ObjectUtils;

/**
 * RedissonClientUtil
 *
 * @author lazecoding
 */
public class RedissonClientUtil {

    private static RedissonClient redissonClient;

    private static final RedissonConfigProperties redissonConfigProperties = BeanUtil.getBean(RedissonConfigProperties.class);

    private RedissonClientUtil() {
    }

    public static RedissonClient getRedissonClient() {
        if (enableRedis()) {
            if (ObjectUtils.isEmpty(redissonClient)) {
                synchronized (RedissonClientUtil.class) {
                    redissonClient = BeanUtil.getBean(RedissonClient.class);
                }
            }
        }
        return redissonClient;
    }

    /**
     * 是否启用 Redis，不启用则使用本地缓存
     */
    public static boolean enableRedis() {
        return redissonConfigProperties.getEnable();
    }

}
