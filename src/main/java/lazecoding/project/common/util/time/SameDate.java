package lazecoding.project.common.util.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 同一天判断
 * 我们认为第一天 0 点和第二天 0 点是同一天（第一天结束时间）
 *
 * @author lazecoding
 */
public class SameDate {

    /**
     * 是否是同一天
     * 我们认为第一天 0 点和第二天 0 点是同一天（第一天结束时间）
     */
    public static boolean isSameDate(LocalDateTime date1, LocalDateTime date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        if (date1.isEqual(date2)) {
            return true;
        }
        LocalDateTime beforeTime;
        LocalDateTime afterTime;
        if (date1.isBefore(date2)) {
            beforeTime = date1;
            afterTime = date2;
        } else {
            beforeTime = date2;
            afterTime = date1;
        }

        // 判断 afterTime 是不是 0 点,如果是则减少 1 S
        if (afterTime.isEqual(afterTime.toLocalDate().atStartOfDay())) {
            afterTime = afterTime.minusSeconds(1);
        }
        // 比较是不是同一天
        return isSameDate(beforeTime.toLocalDate(), afterTime.toLocalDate());
    }

    /**
     * 是否是同一天
     * 我们认为第一天 0 点和第二天 0 点是同一天（第一天结束时间）
     */
    public static boolean isSameDate(LocalDate date, LocalDateTime dateTime) {
        if (date == null || dateTime == null) {
            return false;
        }
        LocalDateTime localDateTime = date.atStartOfDay();
        if (localDateTime.isEqual(dateTime)) {
            return true;
        }
        if (localDateTime.isBefore(dateTime)) {
            // 判断 afterTime 是不是 0 点,如果是则减少 1 S
            if (dateTime.isEqual(dateTime.toLocalDate().atStartOfDay())) {
                dateTime = dateTime.minusSeconds(1);
            }
        }
        return isSameDate(date, dateTime.toLocalDate());
    }


    /**
     * 是否是同一天
     * 我们认为第一天 0 点和第二天 0 点是同一天（第一天结束时间）
     */
    public static boolean isSameDate(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return ChronoUnit.DAYS.between(date1, date2) == 0;
    }


    /**
     * 是否是同一天
     * 我们认为第一天 0 点和第二天 0 点是同一天（第一天结束时间）
     */
    public static boolean isSameDate(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return isSameDate(DateConvertor.toLocalDateTime(date1), DateConvertor.toLocalDateTime(date2));
    }

    /**
     * 是否是同一天
     * 我们认为第一天 0 点和第二天 0 点是同一天（第一天结束时间）
     */
    public static boolean isSameDate(long timestamp1, long timestamp2) {
        return isSameDate(DateConvertor.toLocalDateTime(timestamp1), DateConvertor.toLocalDateTime(timestamp2));
    }

    private SameDate() {
    }

}
