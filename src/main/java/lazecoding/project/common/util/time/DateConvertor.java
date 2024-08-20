package lazecoding.project.common.util.time;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 时间转换器
 * timestamp - Date - LocalDateTime ~ Instant
 *
 * @author lazecoding
 */
public class DateConvertor {

    /**
     * 默认时区（中国大陆）
     */
    public static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Shanghai");


    private DateConvertor() {
    }

    /**
     * 获取  Instant
     */
    public static Instant toInstant(long timestamp) {
        return Instant.ofEpochMilli(timestamp);
    }

    /**
     * 获取  Instant
     */
    public static Instant toInstant(Date date) {
        return date.toInstant();
    }

    /**
     * 根据 Instant 获取 LocalDateTime
     */
    public static LocalDateTime ofInstant(Instant instant) {
        return LocalDateTime.ofInstant(instant, DEFAULT_ZONE);
    }


    /**
     * instant 转 LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Instant instant) {
        return DateConvertor.ofInstant(instant);
    }

    /**
     * timestamp 转 LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(long timestamp) {
        return DateConvertor.ofInstant(DateConvertor.toInstant(timestamp));
    }

    /**
     * date 转 LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return DateConvertor.ofInstant(DateConvertor.toInstant(date));
    }

    /**
     * LocalDate 转 LocalDateTime (当天 0:00)
     */
    public static LocalDateTime toLocalDateTime(LocalDate localDate) {
        return localDate.atStartOfDay();
    }

    /**
     * LocalDateTime 转 LocalDate
     */
    public static LocalDate toLocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();
    }

    /**
     * instant 转 timestamp
     */
    public static long toTimeStamp(Instant instant) {
        return instant.toEpochMilli();
    }


    /**
     * localDateTime 转 timestamp
     */
    public static long toTimeStamp(LocalDateTime localDateTime) {
        return DateConvertor.toTimeStamp(localDateTime.atZone(DEFAULT_ZONE).toInstant());
    }

    /**
     * date 转 timestamp
     */
    public static long toTimeStamp(Date date) {
        return date.getTime();
    }

    /**
     * instant 转 Date
     */
    public static Date toDate(Instant instant) {
        return Date.from(instant);
    }

    /**
     * instant 转 Date
     */
    public static Date toDate(long timestamp) {
        return Date.from(DateConvertor.toInstant(timestamp));
    }

    /**
     * instant 转 Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return DateConvertor.toDate(localDateTime.atZone(DEFAULT_ZONE).toInstant());
    }


    public static void print(Instant instant) {
        System.out.println("DateConvertor#print " + DateConvertor.toDate(instant).toInstant());
    }

    public static void print(LocalDateTime localDateTime) {
        System.out.println("DateConvertor#print " + DateConvertor.toDate(localDateTime).toInstant());
    }

    public static void print(long timestamp) {
        System.out.println("DateConvertor#print " + DateConvertor.toDate(timestamp).toInstant());
    }

}
