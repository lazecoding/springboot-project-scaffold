package lazecoding.project.common.util.interrupt;

import java.io.Serializable;
import java.util.UUID;

/**
 * 任务信息
 *
 * @author lazecoding
 */
public class TaskInfo implements Serializable {

    private static final long serialVersionUID = -1;

    /**
     * 任务 Id
     */
    private String taskId;

    /**
     * 发起时间
     */
    private long ctime = System.currentTimeMillis();

    /**
     * 模块
     */
    private String module;

    /**
     * 业务 Id
     */
    private String busId;

    /**
     * 进度
     */
    private String process;

    /**
     * 扩展信息
     */
    private Object extend;


    public TaskInfo() {
        this.taskId = UUID.randomUUID().toString();
    }

    public TaskInfo(String taskId) {
        this.taskId = taskId;
    }


    public TaskInfo(String module, String busId, String process) {
        this.taskId = UUID.randomUUID().toString();
        this.module = module;
        this.busId = busId;
        this.process = process;
    }

    public TaskInfo(String taskId, String module, String busId, String process) {
        this.taskId = taskId;
        this.module = module;
        this.process = process;
        this.busId = busId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Object getExtend() {
        return extend;
    }

    public void setExtend(Object extend) {
        this.extend = extend;
    }
}
