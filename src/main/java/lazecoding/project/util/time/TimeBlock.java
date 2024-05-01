package lazecoding.project.util.time;

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
    LocalDateTime start;

    /**
     * 结束时间
     */
    LocalDateTime end;

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

    @Override
    public String toString() {
        return "TimeBlock{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
