package lazecoding.project.common.util.script.js.model;

import lazecoding.project.common.util.script.js.function.FunctionGroups;

import java.io.Serializable;

/**
 * 分组信息
 *
 * @author lazecoding
 */
public class GroupInfo implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 分组
     */
    private String group;


    /**
     * 组名称
     */
    private String name;

    public GroupInfo() {
    }

    public GroupInfo(FunctionGroups functionGroup) {
        this.group = functionGroup.getGroup();
        this.name = functionGroup.getName();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
