package lazecoding.project.common.util.script.function;

import org.springframework.util.StringUtils;

/**
 * 函数分组
 *
 * @author lazecoding
 */
public enum FunctionGroups {

    /**
     * 系统函数
     */
    SYSTEM("system", "系统函数"),

    /**
     * 集合函数
     */
    COLLECTION("Collection", "集合函数"),

    /**
     * 逻辑函数
     */
    LOGIC("Logic", "逻辑函数"),

    /**
     * 日期函数
     */
    DATE("Date", "日期函数"),

    /**
     * JSON
     */
    JSON("JSON", "JSON 函数"),

    /**
     * 字符串
     */
    STRING("String", "字符串"),

    /**
     * 数学函数
     */
    MATH("Math", "数学函数");

    /**
     * 分组
     */
    private final String group;


    /**
     * 组名称
     */
    private final String name;

    FunctionGroups(String group, String name) {
        this.group = group;
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getNameByGroup(String group) {
        if (!StringUtils.hasText(group)) {
            return null;
        }
        for (FunctionGroups functionGroup : values()) {
            if (functionGroup.getGroup().equals(group)) {
                return functionGroup.getName();
            }
        }
        return "";
    }

}
