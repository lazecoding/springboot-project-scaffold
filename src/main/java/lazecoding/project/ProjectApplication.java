package lazecoding.project;

import lazecoding.project.common.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Boot 启动类
 *
 * @author lazecoding
 */
@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class ProjectApplication {

    private static final Logger logger = LoggerFactory.getLogger(ProjectApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(ProjectApplication.class, args);

        // api-docs
        Environment environment = BeanUtil.getBean(Environment.class);
        String port = environment.getProperty("local.server.port");
        try {
            SpringDocConfigProperties springDocConfigProperties = BeanUtil.getBean(SpringDocConfigProperties.class);
            String apiDocsPath = springDocConfigProperties.getApiDocs().getPath();
            System.out.println("Open API: http://localhost" + port + apiDocsPath);
        } catch (Exception e) {
            logger.info("springdoc.api-docs.enabled:false");
        }
        // swagger-ui
        try {
            SwaggerUiConfigParameters swaggerUiConfigParameters = BeanUtil.getBean(SwaggerUiConfigParameters.class);
            String swaggerUiPath = swaggerUiConfigParameters.getPath();
            System.out.println("APIS  UI: http://localhost:" + port + swaggerUiPath);
        } catch (Exception e) {
            logger.info("springdoc.swagger-ui.enabled:false");
        }
    }

}
