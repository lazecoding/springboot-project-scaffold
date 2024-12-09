package lazecoding.project.common.util.script.model;

import lazecoding.project.common.util.script.function.FunctionGroups;
import lazecoding.project.common.util.script.function.Functions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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


    private List<FunctionInfo> functions;


    public GroupInfo() {
    }

    public GroupInfo(FunctionGroups functionGroup) {
        this.group = functionGroup.getGroup();
        this.name = functionGroup.getName();
        this.functions = new ArrayList<>();
        for (Functions function : Functions.values()) {
            this.functions.add(new FunctionInfo(function));
        }
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

    public List<FunctionInfo> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionInfo> functions) {
        this.functions = functions;
    }
}
