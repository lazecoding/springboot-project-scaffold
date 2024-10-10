package lazecoding.project.common.util.script.js.model;



import lazecoding.project.common.util.script.js.function.FunctionGroups;
import lazecoding.project.common.util.script.js.function.Functions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * FunctionHolder
 *
 * @author lazecoding
 */
public class FunctionHolder implements Serializable {

    private static final long serialVersionUID = -1L;

    private List<GroupInfo> groupInfos;

    private List<FunctionInfo> functionInfos;

    private FunctionHolder() {
        Functions[] functions = Functions.values();
        FunctionGroups[] functionGroups = FunctionGroups.values();
        this.functionInfos = new ArrayList<>();
        for (Functions function : functions) {
            this.functionInfos.add(new FunctionInfo(function));
        }
        this.groupInfos = new ArrayList<>();
        for (FunctionGroups group : functionGroups) {
            this.groupInfos.add(new GroupInfo(group));
        }
    }

    public static FunctionHolder getInstance() {
        return new FunctionHolder();
    }

    public List<GroupInfo> getGroupInfos() {
        return groupInfos;
    }

    public void setGroupInfos(List<GroupInfo> groupInfos) {
        this.groupInfos = groupInfos;
    }

    public List<FunctionInfo> getFunctionInfos() {
        return functionInfos;
    }

    public void setFunctionInfos(List<FunctionInfo> functionInfos) {
        this.functionInfos = functionInfos;
    }
}
