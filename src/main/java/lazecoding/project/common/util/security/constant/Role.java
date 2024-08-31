package lazecoding.project.common.util.security.constant;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 权限集合
     */
    public static final Map<String, RoleDetail> ROLES = new HashMap<>() {
        {
            put(ADMIN, new RoleDetail(ADMIN, "系统管理员", "系统管理员"));
            put(ORGANISER, new RoleDetail(ORGANISER, "组织管理员", "组织管理员"));
            put(INFO, new RoleDetail(INFO, "信息员", "信息员"));
            put(STATISTICS, new RoleDetail(STATISTICS, "运营统计员", "超级管理员"));
            put(USER, new RoleDetail(USER, "普通用户", "普通用户"));
        }
    };


    private Role() {
    }


    public static class RoleDetail {

        /**
         * 角色
         */
        private String role;

        /**
         * 名称
         */
        private String name;

        /**
         * 描述
         */
        private String description;

        public RoleDetail() {
        }

        public RoleDetail(String role, String name, String description) {
            this.role = role;
            this.name = name;
            this.description = description;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
