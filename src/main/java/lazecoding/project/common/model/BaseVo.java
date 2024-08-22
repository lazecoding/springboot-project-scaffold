package lazecoding.project.common.model;

import java.io.Serializable;

/**
 * BaseVo
 *
 * @author lazecoding
 */
public class BaseVo implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 链路 Id
     */
    private String traceId = "";

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
