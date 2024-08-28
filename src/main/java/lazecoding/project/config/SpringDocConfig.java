package lazecoding.project.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDocConfig
 *
 * address: http://localhost:9977/swagger-ui/index.html
 * form-data 请求的实体参数，注意使用 @ParameterObject 标记
 *
 * @author lazecoding
 */
@Configuration
public class SpringDocConfig {

    /**
     * 接口文档配置
     */
    @Bean
    public OpenAPI openAPI() {
        // 联系人信息
        Contact contact = new Contact()
                .name("lazecoding")
                .email("15062270668@163.com")
                .url("https://github.com/lazecoding");

        // 授权信息
        License license = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                .identifier("Apache-2.0");

        // 项目信息
        Info info = new Info()
                .title("Open API")
                .description("个人项目")
                .version("1.0.0")
                .termsOfService("")
                .license(license)
                .contact(contact);

        return new OpenAPI().info(info);
    }

}
