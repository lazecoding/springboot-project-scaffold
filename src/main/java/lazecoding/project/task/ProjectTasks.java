package lazecoding.project.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Project Tasks
 * <p>
 * fixedRate 上一个任务开始执行后间隔 N 毫秒
 * cron 表达式:
 * 第一个字段（秒）：0 表示在每小时的第0秒。
 * 第二个字段（分）：0 表示在每分钟的第0分。
 * 第三个字段（小时）：1 表示在凌晨1点。
 * 第四个字段（天 - 月）：* 表示每天。
 * 第五个字段（月）：* 表示每个月。
 * 第六个字段（天 - 星期）：? 表示不指定星期的某一天。
 *
 * @author lazecoding
 */
@Component
public class ProjectTasks {

    private static final Logger logger = LoggerFactory.getLogger(ProjectTasks.class);

    @Scheduled(cron = "0 0 0 * * *")
    public void performTaskWithCron() {
        logger.info("Hello, New Day!");
    }

}
