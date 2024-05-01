package lazecoding.project.util.time;

/**
 * 时间冲突
 *
 * @author lazy
 */
public class TimeConflict {


    /**
     * 判断时间是否冲突
     *
     * @return true 冲突
     */
    public static boolean conflict(TimeBlock timeOne, TimeBlock timeTwo) {
        return timeOne.start.isBefore(timeTwo.end) && timeTwo.start.isBefore(timeOne.end);
    }

}
