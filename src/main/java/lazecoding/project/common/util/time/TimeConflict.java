package lazecoding.project.common.util.time;

/**
 * 时间冲突
 *
 * @author lazecoding
 */
public class TimeConflict {


    /**
     * 判断时间是否冲突
     *
     * @return true 冲突
     */
    public static boolean conflict(TimeBlock timeOne, TimeBlock timeTwo) {
        return timeOne.getStart().isBefore(timeTwo.getEnd()) && timeTwo.getStart().isBefore(timeOne.getEnd());
    }

}
