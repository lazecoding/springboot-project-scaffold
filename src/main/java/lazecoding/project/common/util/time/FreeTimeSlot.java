package lazecoding.project.common.util.time;

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
        return this.getStart().isBefore(other.getEnd()) && other.getStart().isBefore(this.getEnd());
    }

    /**
     * 获取交集时间块
     */
    public FreeTimeSlot intersection(FreeTimeSlot other) {
        LocalDateTime intersectionStart = this.getStart().isAfter(other.getStart()) ? this.getStart() : other.getStart();
        LocalDateTime intersectionEnd = this.getEnd().isBefore(other.getEnd()) ? this.getEnd() : other.getEnd();
        return new FreeTimeSlot(intersectionStart, intersectionEnd);
    }

    @Override
    public String toString() {
        return "FreeTimeSlot{" +
                "start=" + this.getStart() +
                ", end=" + this.getEnd() +
                '}';
    }
}
