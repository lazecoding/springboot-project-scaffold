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
     * key: token; value：CurrentUser
     */
    CURRENT_USER(ProjectConstants.PROJECT_NAME + ":current-user:", JWTOperator.MAX_AGE_MILLISECOND, TimeUnit.MILLISECONDS);

    private final String head;

    private final Long ttl;

    private final TimeUnit timeUnit;

    CacheConstants(String head, Long ttl, TimeUnit timeUnit) {
        this.head = head;
        this.ttl = ttl;
        this.timeUnit = timeUnit;
    }

    public String getHead() {
        return head;
    }

    public String getCacheKey(String key) {
        return head + key;
    }

    public Long getTtl() {
        return ttl;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
