package lazecoding.project.common.util.interrupt;


import lazecoding.project.common.util.cache.RedissonOperator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
     * 单个任务缓存
     */
    private static final String TASK_CACHE_HEAD = "keeper_task";

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
     * 获取 task 缓存 Key
     */
    private static String getTaskKey(String taskId) {
        return TASK_CACHE_HEAD + taskId;
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
        RedissonOperator.set(getTaskKey(taskId), taskInfo, TASK_TTL);
        RedissonOperator.mapPut(getHolderKey(), taskId , taskInfo);
        return true;
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
        return RedissonOperator.expire(getTaskKey(taskId), TASK_TTL);
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
        RedissonOperator.set(getTaskKey(taskId), taskInfo, TASK_TTL);
        RedissonOperator.mapPut(getHolderKey(), taskId , taskInfo);
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
        // 取消任务先取消 holder 缓存
        boolean isSuccess = RedissonOperator.mapRemove(getHolderKey(), taskId);
        if (isSuccess) {
            RedissonOperator.delete(getTaskKey(taskId));
        }
        return isSuccess;
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
        TaskInfo taskInfo = RedissonOperator.get(getTaskKey(taskId));
        // holder 中存在， task 中不存在才可以重启
        if (taskInfo != null) {
            // 如果 task 缓存中存在，说明重启使其处于 进行中 状态目的已经达到
            return true;
        }
        taskInfo = findTask(taskId);
        if (taskInfo == null) {
            return false;
        }
        RedissonOperator.set(getTaskKey(taskId), taskInfo, TASK_TTL);
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
        TaskInfo taskInfo = RedissonOperator.get(getTaskKey(taskId));
        // holder 中存在， task 中不存在才可以重启
        if (taskInfo != null) {
            // 如果 task 缓存中存在，说明重启使其处于 进行中 状态目的已经达到
            return true;
        }
        taskInfo = findTask(taskId);
        if (taskInfo == null) {
            return false;
        }
        taskInfo.setProcess(process);
        RedissonOperator.set(getTaskKey(taskId), taskInfo, TASK_TTL);
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
        List<TaskInfo> tasks = new ArrayList<>();
        List<String> errorTaskIds = new ArrayList<>();
        Set<String> taskIds = RedissonOperator.mapKeys(getHolderKey());
        if (!CollectionUtils.isEmpty(taskIds)) {
            for (String taskId : taskIds) {
                TaskInfo taskInfo = findTask(taskId);
                // 防止意外出现异常数据，判空
                if (taskInfo == null) {
                    // 对于这种情况是 task cache 不存在， holder cache 中也没有有效数据，则需要强制清理
                    errorTaskIds.add(taskId);
                } else {
                    tasks.add(taskInfo);
                }
            }
        }
        // 强制清退异常任务
        if (!CollectionUtils.isEmpty(errorTaskIds)) {
            for (String errorTaskId : errorTaskIds) {
                cancel(errorTaskId);
            }
        }
        return tasks;
    }


    /**
     * 获取中断的任务
     */
    public static List<TaskInfo> findInterruptTasks() {
        List<TaskInfo> tasks = new ArrayList<>();
        List<String> errorTaskIds = new ArrayList<>();
        Set<String> taskIds = RedissonOperator.mapKeys(getHolderKey());
        if (!CollectionUtils.isEmpty(taskIds)) {
            for (String taskId : taskIds) {
                TaskInfo taskInfo = RedissonOperator.get(getTaskKey(taskId));
                if (taskInfo == null) {
                    taskInfo = findTask(taskId);
                    // 防止意外出现异常数据，判空
                    if (taskInfo == null) {
                        // 对于这种情况是 task cache 不存在， holder cache 中也没有有效数据，则需要强制清理
                        errorTaskIds.add(taskId);
                    } else {
                        tasks.add(taskInfo);
                    }
                }
            }
        }
        // 强制清退异常任务
        if (!CollectionUtils.isEmpty(errorTaskIds)) {
            for (String errorTaskId : errorTaskIds) {
                cancel(errorTaskId);
            }
        }
        return tasks;
    }

}
