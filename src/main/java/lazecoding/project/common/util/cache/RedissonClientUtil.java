package lazecoding.project.common.util.cache;

import lazecoding.project.common.util.BeanUtil;
import org.redisson.api.RedissonClient;
import org.springframework.util.ObjectUtils;

/**
 * RedissonClientUtil
 *
 * @author lazecoding
 */
public class RedissonClientUtil {

    private static RedissonClient redissonClient;

    private RedissonClientUtil() {
    }

    public static RedissonClient getRedissonClient() {
        if (ObjectUtils.isEmpty(redissonClient)) {
            synchronized (RedissonClientUtil.class) {
                redissonClient = BeanUtil.getBean(RedissonClient.class);
            }
        }
        return redissonClient;
    }

}
