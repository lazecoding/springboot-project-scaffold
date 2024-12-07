package lazecoding.project.common.util.interrupt;


import lazecoding.project.common.util.cache.RedissonOperator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 * 断续器
 * <p>
 * 说明：
 * TaskInfo 标识一个任务，TaskInfo.module、TaskInfo.busId 用于标识业务，module 必填
 * 通过 InterruptTaskJob 中获取中断的 TaskInfo，根据 TaskInfo.module 进行处理，未匹配处理的任务丢弃
 * <p>
 * 函数：
 * register 注册
 * keep、updatedKeep 任务保活，可更新进度
 * cancel 取消任务/完成任务
 * restart 重启任务
 * findTask 获取任务信息
 * findAllTask 获取所有任务
 * findInterruptTasks 获取中断任务
 * <p>
 * 使用：
 * 异步任务开始时 register
 * 任务处理过程中间断 keep、updatedKeep 保持活跃，可更新进度
 * 任务完成、异常等想结束任务  cancel
 * 任务中断重启,可初始化进度 restart
 * <p>
 * 获取中断任务，根据 {@link TaskInfo} 中业务属性来自行处理业务
 *
 * @author lazecoding
 */
public class Interruptor {

    /**
     * 单个任务缓存,ttl 单位 / 秒 （ 10 分钟）
     */
    private static final int TASK_TTL = 60 * 10;

    /**
     * 任务集合
     */
    private static final String HOLDER_CACHE_HEAD = "keeper_holder";

    /**
     * 任务集合
     */
    private static final String HOLDER_CACHE_NAME = "tasks";


    /**
     * 私有，禁止实例化
     */
    private Interruptor() {
    }

    /**
     * 获取 holder 缓存 Key
     */

    private static String getHolderKey() {
        return HOLDER_CACHE_HEAD + HOLDER_CACHE_NAME;
    }

    /**
     * 注册任务（自定义 task）
     */
    public static boolean register(TaskInfo taskInfo) {
        if (taskInfo == null) {
            return false;
        }
        String taskId = taskInfo.getTaskId();
        if (!StringUtils.hasText(taskId)) {
            return false;
        }
        String module = taskInfo.getModule();
        if (!StringUtils.hasText(module)) {
            return false;
        }
        // taskId 重复注册
        if (findTask(taskId) != null) {
            return false;
        }
        taskInfo.setExpireTime(createExpireTime());
        RedissonOperator.mapPut(getHolderKey(), taskId, taskInfo);
        return true;
    }

    /**
     * 生成过期时间
     */
    private static long createExpireTime() {
        return System.currentTimeMillis() + TASK_TTL;
    }

    /**
     * 保持任务
     *
     * @param taskId 任务 Id
     */
    public static boolean keep(String taskId) {
        if (!StringUtils.hasText(taskId)) {
            return false;
        }
        TaskInfo taskInfo = findTask(taskId);
        if (taskInfo == null) {
            return false;
        }
        taskInfo.setExpireTime(createExpireTime());
        RedissonOperator.mapPut(getHolderKey(), taskId, taskInfo);
        return true;
    }

    /**
     * 更新进度并保持任务
     *
     * @param taskId 任务 Id
     */
    public static boolean updatedKeep(String taskId, String process) {
        if (!StringUtils.hasText(taskId)) {
            return false;
        }
        TaskInfo taskInfo = findTask(taskId);
        if (taskInfo == null) {
            return false;
        }
        taskInfo.setProcess(process);
        taskInfo.setExpireTime(createExpireTime());
        RedissonOperator.mapPut(getHolderKey(), taskId, taskInfo);
        return true;
    }


    /**
     * 取消任务
     *
     * @param taskId 任务 Id
     */
    public static boolean cancel(String taskId) {
        if (!StringUtils.hasText(taskId)) {
            return false;
        }
        return RedissonOperator.mapRemove(getHolderKey(), taskId);
    }

    /**
     * 重启任务
     *
     * @param taskId 任务 Id
     */
    public static boolean restart(String taskId) {
        if (!StringUtils.hasText(taskId)) {
            return false;
        }
        TaskInfo taskInfo = findTask(taskId);
        if (taskInfo == null) {
            return false;
        }
        // 存在缓存且过期时间大于当前时间则 进行中
        if (taskInfo.isRunning()) {
            return true;
        }
        taskInfo.setExpireTime(createExpireTime());
        RedissonOperator.mapPut(getHolderKey(), taskId, taskInfo);
        return true;
    }


    /**
     * 重启任务并更新进度
     *
     * @param taskId  任务 Id
     * @param process 进度
     */
    public static boolean restart(String taskId, String process) {
        if (!StringUtils.hasText(taskId)) {
            return false;
        }
        TaskInfo taskInfo = findTask(taskId);
        if (taskInfo == null) {
            return false;
        }
        // 存在缓存且过期时间大于当前时间则 进行中
        if (taskInfo.isRunning()) {
            return true;
        }
        taskInfo.setProcess(process);
        taskInfo.setExpireTime(createExpireTime());
        RedissonOperator.mapPut(getHolderKey(), taskId, taskInfo);
        return true;
    }


    /**
     * 获取任务信息
     *
     * @param taskId 任务 Id
     */
    public static TaskInfo findTask(String taskId) {
        if (!StringUtils.hasText(taskId)) {
            return null;
        }
        return RedissonOperator.mapGet(getHolderKey(), taskId);
    }


    /**
     * 获取所有任务
     */
    public static List<TaskInfo> findAllTask() {
        Collection<TaskInfo> values = RedissonOperator.mapValues(getHolderKey());
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }
        List<TaskInfo> tasks = new ArrayList<>();
        tasks.addAll(values);
        return tasks;
    }


    /**
     * 获取中断的任务
     */
    public static List<TaskInfo> findInterruptTasks() {
        Collection<TaskInfo> values = RedissonOperator.mapValues(getHolderKey());
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }
        List<TaskInfo> tasks = new ArrayList<>();
        // 过期时间小于等于当前时间则过期
        for (TaskInfo taskInfo : values) {
            if (taskInfo.isExpired()) {
                tasks.add(taskInfo);
            }
        }
        return tasks;
    }

}
