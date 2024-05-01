package lazecoding.project.util.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TimeMain {


    public static void main(String[] args) {
        LocalDate reserveDate = LocalDate.now();
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        int year = reserveDate.getYear();
        int month = reserveDate.getMonthValue();
        int dayOfMonth = reserveDate.getDayOfMonth();

        // 预定时间
        TimeBlock reserveTimeBlock = new TimeBlock(LocalDateTime.of(year, month, dayOfMonth, 10, 0), LocalDateTime.of(year, month, dayOfMonth, 11, 30));
        // 已经过去的时间（如果是选择当天才需要追加过去的时间）
        TimeBlock postTimeBlock = new TimeBlock(LocalDateTime.of(localDateTimeNow.getYear(), localDateTimeNow.getMonthValue(), localDateTimeNow.getDayOfMonth(), 0, 0),
                LocalDateTime.of(localDateTimeNow.getYear(), localDateTimeNow.getMonthValue(), localDateTimeNow.getDayOfMonth(), localDateTimeNow.getHour(), 0));

        // 比较时间冲突
        System.out.println("左 conflict:" + TimeConflict.conflict(reserveTimeBlock, new TimeBlock(LocalDateTime.of(year, month, dayOfMonth, 9, 0), LocalDateTime.of(year, month, dayOfMonth, 10, 30))));
        System.out.println("中 conflict:" + TimeConflict.conflict(reserveTimeBlock, new TimeBlock(LocalDateTime.of(year, month, dayOfMonth, 10, 30), LocalDateTime.of(year, month, dayOfMonth, 11, 0))));
        System.out.println("右 conflict:" + TimeConflict.conflict(reserveTimeBlock, new TimeBlock(LocalDateTime.of(year, month, dayOfMonth, 11, 0), LocalDateTime.of(year, month, dayOfMonth, 12, 30))));
        System.out.println("无 conflict:" + TimeConflict.conflict(reserveTimeBlock, new TimeBlock(LocalDateTime.of(year, month, dayOfMonth, 16, 0), LocalDateTime.of(year, month, dayOfMonth, 17, 30))));

        // 获取空闲时间1
        List<TimeBlock> timeBlocks1 = new ArrayList<>();
        timeBlocks1.add(new TimeBlock(LocalDateTime.of(year, month, dayOfMonth, 9, 0), LocalDateTime.of(year, month, dayOfMonth, 12, 0)));
        timeBlocks1.add(new TimeBlock(LocalDateTime.of(year, month, dayOfMonth, 15, 0), LocalDateTime.of(year, month, dayOfMonth, 17, 0)));

        long betweenValue = ChronoUnit.DAYS.between(reserveDate, localDateTimeNow);
        if (betweenValue == 0) {
            // 今天
            // 过去的时间不会空闲，如果是选择当天才需要追加过去的时间
            timeBlocks1.add(postTimeBlock);
        } else if (betweenValue > 0) {
            // 未来
        } else {
            // 过去时间
        }
        List<FreeTimeSlot> freeTimes1 = FreeTimeCalculator.calculateFreeTimeList(timeBlocks1, reserveDate);
        System.out.println("freeTimes1:" + freeTimes1);

        // 获取空闲时间1
        List<TimeBlock> timeBlocks2 = new ArrayList<>();
        timeBlocks2.add(new TimeBlock(LocalDateTime.of(year, month, dayOfMonth, 8, 0), LocalDateTime.of(year, month, dayOfMonth, 10, 30)));
        timeBlocks2.add(new TimeBlock(LocalDateTime.of(year, month, dayOfMonth, 16, 0), LocalDateTime.of(year, month, dayOfMonth, 20, 0)));
        // 过去的时间不会空闲，如果是选择当天才需要追加过去的时间
        if (betweenValue == 0) {
            // 今天
            // 过去的时间不会空闲，如果是选择当天才需要追加过去的时间
            timeBlocks2.add(postTimeBlock);
        } else if (betweenValue > 0) {
            // 未来
        } else {
            // 过去时间
        }
        List<FreeTimeSlot> freeTimes2 = FreeTimeCalculator.calculateFreeTimeList(timeBlocks2, reserveDate);
        System.out.println("freeTimes2:" + freeTimes2);

        // 获取空闲时间交集
        List<FreeTimeSlot> freeTimeSlots = FreeTimeCalculator.getIntersection(freeTimes1, freeTimes2);
        System.out.println("空闲时间交集 freeTimeSlots:" + freeTimeSlots);

        freeTimeSlots = FreeTimeCalculator.getIntersection(freeTimeSlots, freeTimeSlots);
        System.out.println("相同空闲时间 空闲时间交集 freeTimeSlots:" + freeTimeSlots);

        List<FreeTimeSlot> nullFreeTime = new ArrayList<>();
        FreeTimeSlot freeTimeSlot = new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 0, 0), LocalDateTime.of(year, month, dayOfMonth, 0, 0).plusDays(1));
        nullFreeTime.add(freeTimeSlot);
        freeTimeSlots = FreeTimeCalculator.getIntersection(freeTimeSlots, nullFreeTime);
        System.out.println("包含全空 空闲时间交集 freeTimeSlots:" + freeTimeSlots);

        freeTimeSlots = FreeTimeCalculator.getIntersection(freeTimes1, null);
        System.out.println("包含无空 空闲时间交集 freeTimeSlots:" + freeTimeSlots);


        System.out.println("=================");

        // 获取空闲时间交集
        List<FreeTimeSlot> freeTimeSlot_1 = new ArrayList<>();
        freeTimeSlot_1.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 0, 0), LocalDateTime.of(year, month, dayOfMonth, 1, 0)));
        freeTimeSlot_1.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 1, 0), LocalDateTime.of(year, month, dayOfMonth, 2, 0)));
        freeTimeSlot_1.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 4, 0), LocalDateTime.of(year, month, dayOfMonth, 6, 0)));
        // 获取空闲时间交集
        List<FreeTimeSlot> freeTimeSlot_2 = new ArrayList<>();
        freeTimeSlot_2.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 0, 0), LocalDateTime.of(year, month, dayOfMonth, 1, 0)));
        freeTimeSlot_2.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 1, 0), LocalDateTime.of(year, month, dayOfMonth, 2, 0)));
        freeTimeSlot_2.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 2, 0), LocalDateTime.of(year, month, dayOfMonth, 2, 30)));
        freeTimeSlot_2.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 3, 0), LocalDateTime.of(year, month, dayOfMonth, 4, 30)));
        freeTimeSlot_2.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 4, 30), LocalDateTime.of(year, month, dayOfMonth, 5, 0)));
        freeTimeSlot_2.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 5, 30), LocalDateTime.of(year, month, dayOfMonth, 6, 0)));
        freeTimeSlot_2.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 6, 0), LocalDateTime.of(year, month, dayOfMonth, 7, 0)));

        List<FreeTimeSlot> freeTimeSlot_result = FreeTimeCalculator.getIntersection(freeTimeSlot_1, freeTimeSlot_2);
        System.out.println("空闲时间交集 freeTimeSlot_result:" + freeTimeSlot_result);
        List<FreeTimeSlot> freeTimeSlot_merge = FreeTimeCalculator.mergeFreeTimeSlots(freeTimeSlot_result);
        System.out.println("空闲时间交集 合并 freeTimeSlot_merge:" + freeTimeSlot_merge);

        System.out.println("=================");

        // 跨天空闲时间计算
        List<TimeBlock> timeBlocks_k_1 = new ArrayList<>();
        timeBlocks_k_1.add(new TimeBlock(LocalDateTime.of(year, month, dayOfMonth, 9, 0).minusDays(1), LocalDateTime.of(year, month, dayOfMonth, 12, 0)));
        timeBlocks_k_1.add(new TimeBlock(LocalDateTime.of(year, month, dayOfMonth, 15, 0), LocalDateTime.of(year, month, dayOfMonth, 17, 0)));
        timeBlocks_k_1.add(new TimeBlock(LocalDateTime.of(year, month, dayOfMonth, 22, 0), LocalDateTime.of(year, month, dayOfMonth, 3, 0).plusDays(1)));

        timeBlocks_k_1.add(new TimeBlock(LocalDateTime.of(year, month, dayOfMonth, 5, 0).plusDays(1), LocalDateTime.of(year, month, dayOfMonth, 9, 0).plusDays(1)));

        List<FreeTimeSlot> freeTimes_k_1 = FreeTimeCalculator.calculateFreeTimeList(timeBlocks_k_1, reserveDate);
        System.out.println("跨天空闲时间计算 freeTimes_k_1：" + freeTimes_k_1);


        System.out.println("=================");

        List<FreeTimeSlot> freeTimeSlot_merge_1 = new ArrayList<>();
        freeTimeSlot_merge_1.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 0, 0), LocalDateTime.of(year, month, dayOfMonth, 2, 0)));
        freeTimeSlot_merge_1.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 1, 0), LocalDateTime.of(year, month, dayOfMonth, 3, 0)));
        freeTimeSlot_merge_1.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 4, 0), LocalDateTime.of(year, month, dayOfMonth, 6, 0)));
        freeTimeSlot_merge_1.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 7, 0), LocalDateTime.of(year, month, dayOfMonth, 8, 0)));
        freeTimeSlot_merge_1.add(new FreeTimeSlot(LocalDateTime.of(year, month, dayOfMonth, 8, 0), LocalDateTime.of(year, month, dayOfMonth, 9, 0)));
        List<FreeTimeSlot> freeTimeSlot_merge_2 = FreeTimeCalculator.mergeFreeTimeSlots(freeTimeSlot_merge_1);
        System.out.println("空闲时间交集 合并 freeTimeSlot_merge_2:" + freeTimeSlot_merge_2);


    }

}
