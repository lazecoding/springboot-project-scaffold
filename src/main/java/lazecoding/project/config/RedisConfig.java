package lazecoding.project.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import java.util.List;

/**
 * Redis Config（分布式缓存）
 *
 * @author lazecoding
 */
@Configuration
public class RedisConfig {


    @Autowired
    private RedissonConfigProperties redissonConfigProperties;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient getRedissonClient() {
        boolean enableCluster = redissonConfigProperties.getEnableCluster();
        Config config = new Config();
        // 编码类型
        config.setCodec(JsonJacksonCodec.INSTANCE);
        config.setTransportMode(TransportMode.NIO);
        String connectionTimeoutConfig = redissonConfigProperties.getConnectionTimeout();
        if (!StringUtils.hasText(connectionTimeoutConfig)) {
            connectionTimeoutConfig = RedissonConfigProperties.DefaultConfig.connectionTimeout;
        }
        int connectionTimeout = Integer.parseInt(connectionTimeoutConfig);
        String passwordConfig = redissonConfigProperties.getPassword();
        if (enableCluster) {
            List<String> clusterNodes = redissonConfigProperties.getCluster().getNodeAddresses();
            ClusterServersConfig clusterServersConfig = config.useClusterServers()
                    .addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
            //设置密码
            if (StringUtils.hasText(passwordConfig)) {
                clusterServersConfig.setPassword(passwordConfig);
            }
            //redis连接心跳检测，防止一段时间过后，与redis的连接断开
            clusterServersConfig.setPingConnectionInterval(connectionTimeout);
        } else {
            SingleServerConfig singleServerConfig = config.useSingleServer()
                    .setAddress(redissonConfigProperties.getSingle().getAddress())
                    .setDatabase(redissonConfigProperties.getSingle().getDatabase());
            //设置密码
            if (StringUtils.hasText(passwordConfig)) {
                singleServerConfig.setPassword(passwordConfig);
            }
            //redis连接心跳检测，防止一段时间过后，与redis的连接断开
            singleServerConfig.setPingConnectionInterval(connectionTimeout);
        }
        return Redisson.create(config);
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        // 配置连接属性
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // string key serializer
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // string value serializer
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash key serializer
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // hash value serializer
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // connectionFactory
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // must do!
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
