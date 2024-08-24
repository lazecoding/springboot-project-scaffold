package lazecoding.project.common.util.security.constant;

/**
 * 内置角色
 *
 * @author lazecoding
 */
public class Role {


    /**
     * 超级管理员，不对外开放 ！！！
     */
    public static final String SUPER = "super";


    /**
     * 系统管理员（全权限）
     */
    public static final String ADMIN = "admin";

    /**
     * 组织管理员（用户和组织架构管理）
     */
    public static final String ORGANISER = "organiser";

    /**
     * 信息员（信息维护权限）
     */
    public static final String INFO = "info";

    /**
     * 运营统计员（运营统计数据）
     */
    public static final String STATISTICS = "statistics";

    /**
     * 普通用户
     */
    public static final String USER = "user";


    private Role() {
    }
}
