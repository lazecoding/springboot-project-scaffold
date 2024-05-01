package lazecoding.project.util.time;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 空闲时间
 *
 * @author lazecoding
 */
public class FreeTimeSlot extends TimeBlock {


    public FreeTimeSlot() {
        super();
    }

    public FreeTimeSlot(LocalDateTime start, LocalDateTime end) {
        super(start, end);
    }

    public FreeTimeSlot(Date startDate, Date endDate) {
        super(startDate, endDate);
    }

    /**
     * 是否冲突，有交集
     */
    public boolean hasIntersection(FreeTimeSlot other) {
        return this.start.isBefore(other.end) && other.start.isBefore(this.end);
    }

    /**
     * 获取交集时间块
     */
    public FreeTimeSlot intersection(FreeTimeSlot other) {
        LocalDateTime intersectionStart = this.start.isAfter(other.start) ? this.start : other.start;
        LocalDateTime intersectionEnd = this.end.isBefore(other.end) ? this.end : other.end;
        return new FreeTimeSlot(intersectionStart, intersectionEnd);
    }

    @Override
    public String toString() {
        return "FreeTimeSlot{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
