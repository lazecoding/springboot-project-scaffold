package lazecoding.project.common.util.script.js.model;

import lazecoding.project.common.util.script.js.function.FunctionGroups;
import lazecoding.project.common.util.script.js.function.Functions;

import java.io.Serializable;

/**
 * 函数信息
 *
 * @author lazecoding
 */
public class FunctionInfo implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 函数名
     */
    private String name;

    /**
     * 中文别名
     */
    private String alias;

    /**
     * 分组
     */
    private String group;


    /**
     * 分组
     */
    private String groupName;


    /**
     * 描述，形如：返回其参数列表中第一个非空值\n:param args: 任意数量的参数\n:return: 第一个非空值
     */
    private String description;

    public FunctionInfo() {
    }

    public FunctionInfo(Functions function) {
        this.name = function.getName();
        this.alias = function.getAlias();
        this.group = function.getGroup();
        this.groupName = FunctionGroups.COLLECTION.getNameByGroup(function.getGroup());
        this.description = function.getDescription();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
