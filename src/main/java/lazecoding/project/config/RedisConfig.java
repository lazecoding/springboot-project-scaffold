package lazecoding.project.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        boolean enable = redissonConfigProperties.getEnable();
        if (!enable) {
            return null;
        }
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

}
