package lazecoding.project.common.util.file;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * FileProperties
 *
 * @author lazecoding
 */
@Configuration("FileProperties")
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    /**
     * local、minio
     */
    private String operator;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
