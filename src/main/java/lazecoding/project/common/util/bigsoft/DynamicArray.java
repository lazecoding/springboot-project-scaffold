package lazecoding.project.common.util.bigsoft;

/**
 * 动态数组
 *
 * @author lazecoding
 */
public class DynamicArray<T> {

    /**
     * 有数据的数量
     */
    private int size;

    /**
     * 数据数组
     */
    private Object[] data;

    public DynamicArray() {
        this(12);
    }

    /**
     * 获取静态数组
     */
    public Object[] getStaticArray() {
        if (size == 0) {
            return null;
        }
        Object[] staticArray = new Object[size];
        for (int i = 0; i < size; i++) {
            staticArray[i] = data[i];
        }
        return staticArray;
    }

    /**
     * 获取静态数组
     */
    public <T> T[] getStaticArray(T[] staticArray) {
        if (size == 0) {
            return null;
        }
        staticArray = (T[]) java.lang.reflect.Array.newInstance(staticArray.getClass().getComponentType(), size);
        Object[] result = staticArray;
        for (int i = 0; i < size; i++) {
            result[i] = data[i];
        }
        return staticArray;
    }

    public DynamicArray(int capacity) {
        data = new Object[capacity];
        size = 0;
    }

    /**
     * 将 t 插入 index 位置
     */
    public void insert(int index, T t) {
        //判断index是否在合理范围
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index 必须 0<= index <= size");
        }
        //判断是否需要扩容
        if (size == data.length) {
            resetSize(2 * data.length);
        }
        //数据搬移
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = t;
        //别忘了++
        size++;
    }

    /**
     * 移除 index 位置的元素
     */
    public T remove(int index) {
        // 判断 index 是否在合理范围
        checkIndex(index);
        Object removeData = data[index];
        // 数据搬移
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        data[size] = null;
        //判断是否需要缩容
        if (size == data.length / 4 && data.length / 2 != 0) {
            resetSize(data.length / 2);
        }
        return (T) removeData;
    }

    /**
     * 获取 index 位置的元素
     */
    public T get(int index) {
        // 判断 index 是否在合理范围
        checkIndex(index);
        return (T) data[index];
    }

    /**
     * 将 index 位置的数据更新为 t
     */
    private void set(int index, T t) {
        //判断移除位置是否正确
        checkIndex(index);
        data[index] = t;
    }

    /**
     * 重新设置数组的大小
     */
    private void resetSize(int capacity) {
        Object[] newData = new Object[capacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    /**
     * 检查 index 是否在合理的区间
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index 必须 0<= index < size");
        }
    }

    /**
     * 获取 size
     */
    public int size() {
        return size;
    }

    /**
     * 判断是否包含元素
     */
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("数组 size=").append(size).append(",length = ").append(data.length).append("\n");
        builder.append("[");
        for (int i = 0; i < size; i++) {
            builder.append(data[i]);
            if (i != size - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}

