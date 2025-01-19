package lazecoding.project.task.single;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * SingleTask
 *
 * @author lazecoding
 */
@Component
public class SingleTask {


    private static final Logger logger = LoggerFactory.getLogger(SingleTask.class);

    /**
     * 是否进行中
     */
    private static boolean isRunning = false;

    @Async
    @Scheduled(cron = "0/5 * * * * *")
    public void doTask() {
        logger.info("SingleTask Scheduled!");
        if (isRunning) {
            return;
        }
        try {
            isRunning = true;
            logger.info("SingleTask doTask start!");
            Thread.sleep(7000);
            logger.info("SingleTask doTask end!");
        } catch (Exception e) {
            logger.error("SingleTask Exception", e);
        } finally {
            isRunning = false;
            logger.info("SingleTask finally!");
        }
    }

}
