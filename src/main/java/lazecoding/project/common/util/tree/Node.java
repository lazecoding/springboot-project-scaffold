package lazecoding.project.common.util.tree;


/**
 * 树节点
 *
 * @author lazecoding
 */
public class Node {

    /**
     * 父节点 Id
     */
    private String pid;

    /**
     * 节点 Id
     */
    private String id;

    /**
     * 值
     */
    private long value;

    /**
     * 汇总值
     */
    private long sum;

    /**
     * 扩展信息
     */
    private Object extension;

    public Node(String pid, String id) {
        this.pid = pid;
        this.id = id;
    }

    public Node(String pid, String id, long value) {
        this.pid = pid;
        this.id = id;
        this.value = value;
    }

    public Node(String pid, String id, long value, Object extension) {
        this.pid = pid;
        this.id = id;
        this.value = value;
        this.extension = extension;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public Object getExtension() {
        return extension;
    }

    public void setExtension(Object extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "Node{" +
                "pid='" + pid + '\'' +
                ", id='" + id + '\'' +
                ", value=" + value +
                ", sum=" + sum +
                ", extension=" + extension +
                '}';
    }
}
