package lazecoding.project.component;

import io.netty.util.NettyRuntime;

import java.util.concurrent.*;

/**
 * 异步任务执行器
 *
 * @author lazecoding
 */
public class AsyncTaskExecutor {
    /**
     * CPU 核心数量
     */
    private static final int CORE_NUM = Math.max(4, NettyRuntime.availableProcessors());

    /**
     * 私有，禁止实例化
     */
    private AsyncTaskExecutor() {

    }

    /**
     * 异步任务执行器
     */
    private static final ThreadPoolExecutor ASYNC_EXECUTOR = new ThreadPoolExecutor(CORE_NUM, CORE_NUM * 2, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("异步任务执行器");
                thread.setDaemon(true);
                return thread;
            }, new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 延迟任务执行器
     */
    private static final ScheduledExecutorService DELAY_EXECUTOR = new ScheduledThreadPoolExecutor(CORE_NUM, runnable -> {
        Thread thread = new Thread(runnable);
        thread.setName("延迟任务执行器");
        thread.setDaemon(true);
        return thread;
    });

    /**
     * 提交异步任务
     **/
    public static void submitAsyncTask(Runnable task) {
        ASYNC_EXECUTOR.execute(task);
    }

    /**
     * 提交延迟任务
     *
     * @param task      待执行任务
     * @param delayTime 延迟时间（单位/s）
     */
    public static void submitDelayTask(Runnable task, Long delayTime) {
        DELAY_EXECUTOR.schedule(new Runnable() {
            @Override
            public void run() {
                task.run();
            }
        }, delayTime, TimeUnit.SECONDS);
    }

}

