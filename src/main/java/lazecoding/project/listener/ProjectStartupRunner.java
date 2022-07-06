package lazecoding.project.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 系统启动后执行
 *
 * @author lazecoding
 */
@Component
public class ProjectStartupRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ProjectStartupRunner.class);

    @Override
    public void run(ApplicationArguments args) {
        try {
            System.out.println("初始化...");
        } catch (Exception e) {
            logger.error("ApplicationRunner Exception", e);
        }
    }
}
