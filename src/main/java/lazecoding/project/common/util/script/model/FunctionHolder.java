package lazecoding.project.common.util.script.model;
import lazecoding.project.common.util.script.function.FunctionGroups;
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

    private List<GroupInfo> groups;


    private FunctionHolder() {
        this.groups = new ArrayList<>();
        for (FunctionGroups group : FunctionGroups.values()) {
            this.groups.add(new GroupInfo(group));
        }
    }

    public static FunctionHolder getInstance() {
        return new FunctionHolder();
    }


    public List<GroupInfo> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupInfo> groups) {
        this.groups = groups;
    }
}
