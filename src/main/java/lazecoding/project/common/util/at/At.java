package lazecoding.project.common.util.at;

import java.io.Serializable;

/**
 * 信息内容
 *
 * @author lazecoding
 */
public class At implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * Id
     */
    private String id;

    /**
     * 显示内容
     */
    private String name;

    public At() {
    }

    public At(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
