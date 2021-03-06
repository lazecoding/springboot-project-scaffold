package lazecoding.project.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.*;
import org.springframework.core.Ordered;

/**
 * ProjectContextListener
 *
 * @author lazecoding
 */
public class ProjectContextListener implements ApplicationListener<ApplicationEvent>, Ordered {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProjectContextListener() {
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ApplicationStartingEvent) {
            logger.debug("ApplicationStartingEvent...");
        } else if (applicationEvent instanceof ApplicationEnvironmentPreparedEvent) {
            logger.debug("ApplicationEnvironmentPreparedEvent...");
        } else if (applicationEvent instanceof ApplicationContextInitializedEvent) {
            logger.debug("ApplicationContextInitializedEvent...");
        } else if (applicationEvent instanceof ApplicationPreparedEvent) {
            logger.debug("ApplicationPreparedEvent...");
        } else if (applicationEvent instanceof ApplicationContextEvent) {
            // ApplicationContextEvent 是一个抽象类
            if (applicationEvent instanceof ContextRefreshedEvent) {
                // 从源码可知该事件在spring的上下文被初始化和刷新时触发。这里的刷新其实就是指由ConfigurableApplicationContext定义的refresh方法，在重新加载属性文件等时调用。
                logger.debug("ApplicationContextEvent - ContextRefreshedEvent...");
                // 容器初始化完毕
            } else if (applicationEvent instanceof ContextStartedEvent) {
                // spring上下文启动完成触发,既ConfigurableApplicationContext的start方法。奇怪的是spring自己启动完成后触发的不是这个事件，而是上面的RefreshedEvent。
                logger.debug("ApplicationContextEvent - ContextStartedEvent...");
            } else if (applicationEvent instanceof ContextClosedEvent) {
                // ConfigurableApplicationContext.stop()，stop后的上下文是可以调用start再次启用的。
                logger.debug("ApplicationContextEvent - ContextClosedEvent...");
            } else if (applicationEvent instanceof ContextStoppedEvent) {
                // ConfigurableApplicationContext.close() close方法后，所有bean被摧毁，无法再次start or refresh。
                logger.debug("ApplicationContextEvent - ContextStoppedEvent...");
            }
        } else if (applicationEvent instanceof ApplicationStartedEvent) {
            logger.debug("ApplicationStartedEvent...");
        } else if (applicationEvent instanceof ApplicationReadyEvent) {
            logger.debug("ApplicationReadyEvent...");
        } else if (applicationEvent instanceof ApplicationFailedEvent) {
            logger.debug("ApplicationFailedEvent...");
        }
    }

    @Override
    public int getOrder() {
        return -2147483629;
    }
}
