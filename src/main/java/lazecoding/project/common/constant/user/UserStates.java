package lazecoding.project.common.constant.user;

/**
 * UserStates
 * 用户状态 0 未激活；1 已激活；2 已冻结；3.已注销
 *
 * @author lazecoding
 */
public class UserStates {

    private UserStates() {
    }

    /**
     * 未激活
     */
    public static final int UNACTIVATED = 0;

    /**
     * 已激活
     */
    public static final int ACTIVATED = 1;

    /**
     * 已冻结
     */
    public static final int FREEZING = 2;

    /**
     * 已注销
     */
    public static final int CANCELLED = 3;

}
