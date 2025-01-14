package lazecoding.project.config;

import lazecoding.project.config.model.Address;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

/**
 * 项目 ES 配置
 *
 * @author lazecoding
 */
@Configuration("ElasticSearchConfig")
@ConfigurationProperties(prefix = "elastic.search")
public class ElasticSearchConfig {

    private List<Address> addresses;

    private String username;

    private String password;

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ProjectSearchConfig{" +
                "addresses=" + addresses +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}