package lazecoding.project.util.time;

import java.time.LocalDateTime;

/**
 * 空闲时间
 *
 * @author lazecoding
 */
public class FreeTimeSlot {

    /**
     * 开始时间
     */
    LocalDateTime start;

    /**
     * 结束时间
     */
    LocalDateTime end;

    public FreeTimeSlot() {
    }

    public FreeTimeSlot(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
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
