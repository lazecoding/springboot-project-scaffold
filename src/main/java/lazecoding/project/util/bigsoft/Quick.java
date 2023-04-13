package lazecoding.project.util.bigsoft;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 快排
 *
 * @author lazecoding
 */
public class Quick {

    /**
     * quicksort the array; 默认正序。
     */
    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1, false);
    }

    /**
     * quicksort the array; reverse = true 为倒序。
     */
    public static void sort(Comparable[] a, boolean reverse) {
        sort(a, 0, a.length - 1, reverse);
    }

    /**
     * quicksort the subarray from a[lo] to a[hi]
     */
    private static void sort(Comparable[] a, int lo, int hi, boolean reverse) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi, reverse);
        sort(a, lo, j - 1, reverse);
        sort(a, j + 1, hi, reverse);
    }

    /**
     * partition the subarray a[lo .. hi] by returning an index j,
     * so that a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
     */
    private static int partition(Comparable[] a, int lo, int hi, boolean reverse) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        while (true) {

            // find item on lo to swap
            while (compare(a[++i], v, reverse))
                if (i == hi) break;

            // find item on hi to swap
            while (compare(v, a[--j], reverse))
                if (j == lo) break;      // redundant since a[lo] acts as sentinel

            // check if pointers cross
            if (i >= j) break;

            exch(a, i, j);
        }

        // put v = a[j] into position
        exch(a, lo, j);

        // with a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }

    /**
     * compare v,w
     */
    private static boolean compare(Comparable v, Comparable w, boolean reverse) {
        if (reverse) {
            return (v.compareTo(w) > 0);
        } else {
            return (v.compareTo(w) < 0);
        }
    }

    /**
     * exchange a[i] and a[j]
     */
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    /**
     * print
     */
    public static void show(Comparable[] arr) {
        for (int index = 0; index < arr.length; index++) {
            System.out.print(arr[index] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{6, 11, 88, 2, 99, 3, 10};

        // 可以通过 DynamicArray 容纳大量数据，然后排序
        DynamicArray<ComparableModel> dynamicArray = new DynamicArray<>();
        dynamicArray.insert(0, new ComparableModel(6L, "6L"));
        dynamicArray.insert(1, new ComparableModel(11L, "11L"));
        dynamicArray.insert(2, new ComparableModel(88L, "88L"));
        dynamicArray.insert(3, new ComparableModel(2L, "2L"));
        dynamicArray.insert(4, new ComparableModel(99L, "99L"));
        dynamicArray.insert(5, new ComparableModel(3L, "3L"));
        dynamicArray.insert(6, new ComparableModel(10L, "10L"));
        ComparableModel[] ComparableModels = new ComparableModel[1];
        ComparableModels = dynamicArray.getStaticArray(ComparableModels);

        // 倒序
        sort(ComparableModels, true);
        show(ComparableModels);

        System.out.println();

        // 正序
        sort(arr);
        show(arr);
    }
}
