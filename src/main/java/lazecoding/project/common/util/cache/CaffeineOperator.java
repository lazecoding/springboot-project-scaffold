package lazecoding.project.common.util.cache;

import com.github.benmanes.caffeine.cache.Cache;
import lazecoding.project.common.util.BeanUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

/**
 * CaffeineCache 封装
 *
 * @author lazecoding
 */
@Component
public class CaffeineOperator {

    @Resource
    private Cache<String, CacheModel<Object>> caffeineCache;

    /**
     * AtomicLong Map
     */
    public static final Map<String, AtomicLong> ATOMIC_LONG_MAP = new ConcurrentHashMap<>();


    /**
     * 实例内部类
     */
    private static final class InstanceHolder {
        /**
         * 实例
         */
        private static final CaffeineOperator INSTANCE = BeanUtil.getBean(CaffeineOperator.class);
    }

    /**
     * 获取 CaffeineCache 实例
     */
    public static CaffeineOperator getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 判断缓存是否存在
     */
    public boolean exits(String key) {
        if (!StringUtils.hasText(key)) {
            return false;
        }
        long ttl = ttl(key);
        return ttl(key) == -1L || ttl > 0;
    }

    /**
     * 新增缓存
     */
    public <T> void set(String key, T value) {
        if (!StringUtils.hasText(key)) {
            return;
        }
        CacheModel<Object> cacheModel = new CacheModel<>(value);
        caffeineCache.put(key, cacheModel);
    }


    /**
     * 新增缓存,设置 ttl
     */
    public <T> void set(String key, T value, long ttl, TimeUnit timeUnit) {
        this.set(key, value, timeUnit.toSeconds(ttl));
    }


    /**
     * 新增缓存,设置 ttl，单位 秒
     */
    public <T> void set(String key, T value, long ttl) {
        if (!StringUtils.hasText(key)) {
            return;
        }
        CacheModel<Object> cacheModel = new CacheModel<>(value, TimeUnit.SECONDS.toMillis(ttl));
        caffeineCache.put(key, cacheModel);
    }

    /**
     * 删除缓存
     */
    public void delete(String key) {
        if (!StringUtils.hasText(key)) {
            return;
        }
        caffeineCache.invalidate(key);
    }

    /**
     * 删除缓存
     */
    public void deletes(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return;
        }
        caffeineCache.invalidateAll(keys);
    }

    /**
     * 匹配删除缓存
     */
    public void deleteKeys(String pattern) {
        if (!StringUtils.hasText(pattern)) {
            return;
        }
        Iterable<String> keyIterable = keys(pattern);
        if (keyIterable == null) {
            return;
        }
        for (String key : keyIterable) {
            delete(key);
        }
    }

    /**
     * 根据规则获取 keys
     */
    public Iterable<String> keys(String pattern) {
        if (!StringUtils.hasText(pattern)) {
            return null;
        }
        Set<String> keySet = caffeineCache.asMap().keySet();
        if (keySet.isEmpty()) {
            return null;
        }
        pattern = pattern.replace("*", ".*");
        Pattern compiledPattern = Pattern.compile(pattern);
        List<String> keys = new ArrayList<>(keySet);
        for (String key : keySet) {
            if (compiledPattern.matcher(key).matches()) {
                keys.add(key);
            }
        }
        return keys;
    }

    /**
     * 获取 ttl，-1 永久，-2 不存在
     */
    public long ttl(String key) {
        if (!StringUtils.hasText(key)) {
            return -2L;
        }
        CacheModel<Object> cacheModel = caffeineCache.getIfPresent(key);
        if (cacheModel == null) {
            return -2L;
        }
        if (cacheModel.getExp() == -1) {
            return -1L;
        }
        long ttl = cacheModel.getExp() - System.currentTimeMillis();
        if (ttl <= 0) {
            delete(key);
            return -2L;
        } else {
            return ttl;
        }
    }

    /**
     * 设置 ttl，单位 秒
     */
    public boolean expire(String key, long ttl) {
        if (!StringUtils.hasText(key)) {
            return false;
        }
        CacheModel<Object> cacheModel = caffeineCache.getIfPresent(key);
        if (cacheModel == null) {
            return false;
        }
        // 已经过期的不能 expire
        if (cacheModel.exp != -1 && cacheModel.exp < System.currentTimeMillis()) {
            delete(key);
            return false;
        }
        cacheModel.setTtl(TimeUnit.SECONDS.toMillis(ttl));
        caffeineCache.put(key, cacheModel);
        return true;
    }

    /**
     * 设置 ttl
     */
    public boolean expire(String key, long ttl, TimeUnit timeUnit) {
        return this.expire(key, timeUnit.toSeconds(ttl));
    }

    /**
     * 获取缓存
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        if (!StringUtils.hasText(key)) {
            return null;
        }
        CacheModel<Object> cacheModel = caffeineCache.getIfPresent(key);
        if (cacheModel == null) {
            return null;
        }
        // 无期限
        if (cacheModel.exp == -1) {
            return (T) cacheModel.getValue();
        }
        if (cacheModel.exp < System.currentTimeMillis()) {
            delete(key);
            return null;
        }
        return (T) cacheModel.getValue();
    }

    /**
     * 批量获取缓存
     */
    public <T> Map<String, T> gets(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return null;
        }
        Map<String, T> map = new HashMap<>();
        for (String key : keys) {
            T v = this.get(key);
            if (v != null) {
                map.put(key, v);
            }
        }
        return map;
    }


    /**
     * 缓存数据
     *
     * @param <T>
     */
    static class CacheModel<T> {

        /**
         * 值
         */
        private T value;

        /**
         * 设置的 ttl
         */
        private long ttl = -1;

        /**
         * 过期的时间戳
         */
        private long exp = -1;


        public CacheModel() {
        }

        public CacheModel(T value) {
            this.value = value;
        }

        public CacheModel(T value, long ttl) {
            this.value = value;
            this.ttl = ttl;
            this.exp = System.currentTimeMillis() + ttl;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public long getTtl() {
            return ttl;
        }

        public void setTtl(long ttl) {
            this.ttl = ttl;
            this.exp = System.currentTimeMillis() + ttl;
        }

        public long getExp() {
            return exp;
        }
    }

}
