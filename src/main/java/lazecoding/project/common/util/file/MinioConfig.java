package lazecoding.project.common.util.file;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 配置
 *
 * Linux 启动脚本 - MINIO_ROOT_USER=admin MINIO_ROOT_PASSWORD=password ./minio server ./data --console-address ":9001"
 * Windows 启动脚本 - setx MINIO_ROOT_USER admin; setx MINIO_ROOT_PASSWORD password; ./minio.exe server ./data --console-address ":9001"
 * WEB - <a href="http://127.0.0.1:9001">...</a>
 * API - <a href="http://127.0.0.1:9000">...</a>
 *
 * @author lazecoding
 */
@Configuration("MinioConfig")
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    /**
     * minio API 地址
     */
    private String endpoint;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * bucket
     */
    private String bucket;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
