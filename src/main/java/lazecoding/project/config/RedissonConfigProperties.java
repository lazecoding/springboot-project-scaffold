package lazecoding.project.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.List;

/**
 * RedisConfigProperties
 *
 * @author lazecoding
 */
@Configuration("RedissonConfigProperties")
@ConfigurationProperties(prefix = "spring.redisson")
public class RedissonConfigProperties implements Serializable {

    private static final long serialVersionUID = 8815222005846355408L;

    /**
     * 是否使用 Redis 缓存，关闭则使用本地缓存
     */
    private Boolean enable = Boolean.FALSE;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否开启集群
     */
    private Boolean enableCluster = Boolean.FALSE;

    /**
     * 集群配置
     */
    private Cluster cluster;

    /**
     * 单机配置
     */
    private Single single;

    /**
     * 链接超时时间
     */
    private String connectionTimeout;

    public static class DefaultConfig {

        /**
         * 链接超时时间
         */
        public static String connectionTimeout = "30000";

    }

    public static class Cluster {
        private List<String> nodeAddresses;

        public List<String> getNodeAddresses() {
            return nodeAddresses;
        }

        public void setNodeAddresses(List<String> nodeAddresses) {
            this.nodeAddresses = nodeAddresses;
        }

        @Override
        public String toString() {
            return "{" +
                    "nodeAddresses=" + nodeAddresses +
                    '}';
        }
    }

    public static class Single {
        private String address;
        public Integer database;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getDatabase() {
            return database;
        }

        public void setDatabase(Integer database) {
            this.database = database;
        }

        @Override
        public String toString() {
            return "Single{" +
                    "address='" + address + '\'' +
                    ", database=" + database +
                    '}';
        }
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public Single getSingle() {
        return single;
    }

    public void setSingle(Single single) {
        this.single = single;
    }

    public Boolean getEnableCluster() {
        return enableCluster;
    }

    public void setEnableCluster(Boolean enableCluster) {
        this.enableCluster = enableCluster;
    }

    public String getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(String connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
