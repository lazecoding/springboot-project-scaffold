package lazecoding.project.common.util.time;

import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


/**
 * 在可重叠时间块下计算空闲时间、时间交集、合并时间
 *
 * @author lazecoding
 */
public class FreeTimeCalculator {


    /**
     * 根据忙碌时间获取空闲时间
     */
    public static List<FreeTimeSlot> calculateFreeTimeList(List<TimeBlock> timeBlocks, LocalDateTime startTime, LocalDateTime endTime) {
        List<FreeTimeSlot> freeTimes = new ArrayList<>();
        if (CollectionUtils.isEmpty(timeBlocks)) {
            // 全天空闲
            freeTimes.add(new FreeTimeSlot(startTime, endTime));
            return freeTimes;
        }
        // 将日程按照开始时间排序
        timeBlocks.sort((s1, s2) -> s1.getStart().compareTo(s2.getStart()));
        LocalDateTime currentTime = LocalDateTime.of(startTime.getYear(), startTime.getMonthValue(), startTime.getDayOfMonth(), startTime.getHour(), startTime.getMinute()); // 初始时间设为最早可能的时间
        for (TimeBlock block : timeBlocks) {
            // 如果当前日程的开始时间大于当前时间，说明这段时间是空闲的
            if (block.getStart().isAfter(currentTime)) {
                freeTimes.add(new FreeTimeSlot(currentTime, block.getStart()));
            }
            // 更新当前时间为当前日程的结束时间
            // 比较时间获取当前时间和时间段，哪个晚用哪个
            if (block.getEnd().isAfter(currentTime)) {
                currentTime = block.getEnd();
            }
        }
        // 检查并添加最后的空闲时间（如果有的话）
        if (currentTime.isBefore(endTime)) {
            freeTimes.add(new FreeTimeSlot(currentTime, endTime));
        }
        return freeTimes;
    }


    /**
     * 根据忙碌时间获取空闲时间(指定日期)
     */
    public static List<FreeTimeSlot> calculateFreeTimeList(List<TimeBlock> timeBlocks, LocalDate reserveDate) {
        LocalDateTime startTime = reserveDate.atStartOfDay();
        LocalDateTime endTime = startTime.plusDays(1);
        return calculateFreeTimeList(timeBlocks, startTime, endTime);
    }


    /**
     * 获取空闲时间交集
     */
    public static List<FreeTimeSlot> getIntersection(List<FreeTimeSlot> timeSlots1, List<FreeTimeSlot> timeSlots2) {
        List<FreeTimeSlot> intersection = new ArrayList<>();
        // 任意一个空闲集合为空，则无交集
        if (CollectionUtils.isEmpty(timeSlots1) || CollectionUtils.isEmpty(timeSlots2)) {
            return intersection;
        }
        for (FreeTimeSlot slot1 : timeSlots1) {
            for (FreeTimeSlot slot2 : timeSlots2) {
                if (slot1.hasIntersection(slot2)) {
                    intersection.add(slot1.intersection(slot2));
                }
            }
        }
        return intersection;
    }


    /**
     * 合并连续的时间段
     */
    public static List<FreeTimeSlot> mergeFreeTimeSlots(List<FreeTimeSlot> timeSlots) {
        List<FreeTimeSlot> mergedSlots = new ArrayList<>();
        if (CollectionUtils.isEmpty(timeSlots)) {
            return mergedSlots;
        }
        timeSlots.sort((s1, s2) -> s1.getStart().compareTo(s2.getStart()));
        FreeTimeSlot currentSlot = timeSlots.get(0);
        for (int i = 1; i < timeSlots.size(); i++) {
            FreeTimeSlot nextSlot = timeSlots.get(i);
            if (currentSlot.getEnd().isAfter(nextSlot.getStart()) || currentSlot.getEnd().isEqual(nextSlot.getStart())) {
                currentSlot = new FreeTimeSlot(currentSlot.getStart(), nextSlot.getEnd());
            } else {
                mergedSlots.add(currentSlot);
                currentSlot = nextSlot;
            }
        }
        mergedSlots.add(currentSlot);
        return mergedSlots;
    }

    /**
     * 合并连续的时间段
     */
    public static List<TimeBlock> mergeTimeBlocks(List<TimeBlock> timeBlocks) {
        List<TimeBlock> mergedBlocks = new ArrayList<>();
        if (CollectionUtils.isEmpty(timeBlocks)) {
            return mergedBlocks;
        }
        timeBlocks.sort((s1, s2) -> s1.getStart().compareTo(s2.getStart()));
        TimeBlock currentBlock = timeBlocks.get(0);
        for (int i = 1; i < timeBlocks.size(); i++) {
            TimeBlock nextBlock = timeBlocks.get(i);
            if (currentBlock.getEnd().isAfter(nextBlock.getStart()) || currentBlock.getEnd().isEqual(nextBlock.getStart())) {
                currentBlock = new TimeBlock(currentBlock.getStart(), nextBlock.getEnd());
            } else {
                mergedBlocks.add(currentBlock);
                currentBlock = nextBlock;
            }
        }
        mergedBlocks.add(currentBlock);
        return mergedBlocks;
    }


}
