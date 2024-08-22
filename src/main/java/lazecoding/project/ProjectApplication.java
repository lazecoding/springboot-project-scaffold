package lazecoding.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Boot 启动类
 *
 * @author lazecoding
 */
@EnableJpaAuditing
@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProjectApplication.class, args);

    }

}
