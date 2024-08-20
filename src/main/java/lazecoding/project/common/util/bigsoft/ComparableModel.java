package lazecoding.project.common.util.bigsoft;

/**
 * ComparableModel
 *
 * @author lazecoding
 */
public class ComparableModel implements Comparable {

    /**
     * 权重
     */
    private long weight;

    /**
     * 数据
     */
    private Object obj;


    public ComparableModel() {
    }


    public ComparableModel(long weight, Object obj) {
        this.weight = weight;
        this.obj = obj;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "ComparableModel{" +
                "weight=" + weight +
                ", obj=" + obj +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        ComparableModel s = (ComparableModel) (o);
        return Long.compare(this.weight, s.weight);
    }
}
