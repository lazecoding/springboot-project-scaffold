package lazecoding.project.common.constant;

import lazecoding.project.common.util.security.JWTOperator;

import java.util.concurrent.TimeUnit;

/**
 * CacheConstants
 *
 * @author lazecoding
 */
public enum CacheConstants {


    /**
     * 用户 token
     * key: token; value：userId
     */
    ACCESS_TOKEN(ProjectConstants.PROJECT_NAME + ":access-token:", JWTOperator.MAX_AGE_MILLISECOND, TimeUnit.MILLISECONDS);

    private final String name;

    private final Long ttl;

    private final TimeUnit timeUnit;


    private CacheConstants(String name, Long ttl, TimeUnit timeUnit) {
        this.name = name;
        this.ttl = ttl;
        this.timeUnit = timeUnit;
    }

    public String getName() {
        return name;
    }

    public String getCacheKey(String key) {
        return name + key;
    }

    public Long getTtl() {
        return ttl;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
