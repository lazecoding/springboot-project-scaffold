package lazecoding.project.common.util.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 时间块
 */
public class TimeBlock {

    /**
     * 开始时间
     */
    private LocalDateTime start;

    /**
     * 结束时间
     */
    private LocalDateTime end;

    public TimeBlock() {
    }

    public TimeBlock(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public TimeBlock(Date startDate, Date endDate) {
        this.start = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.of("Asia/Shanghai"));
        this.end = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.of("Asia/Shanghai"));
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "TimeBlock{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
