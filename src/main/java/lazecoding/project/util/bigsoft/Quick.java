package lazecoding.project.util.bigsoft;

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

        List<ComparableModel> ComparableModelList = new LinkedList<>();
        ComparableModelList.add(new ComparableModel(6L, "6L"));
        ComparableModelList.add(new ComparableModel(11L, "11L"));
        ComparableModelList.add(new ComparableModel(88L, "88L"));
        ComparableModelList.add(new ComparableModel(2L, "2L"));
        ComparableModelList.add(new ComparableModel(99L, "99L"));
        ComparableModelList.add(new ComparableModel(3L, "3L"));
        ComparableModelList.add(new ComparableModel(10L, "10L"));
        Object obj = ComparableModelList.toArray();
        System.out.println(obj);
        ComparableModel[] ComparableModels = new ComparableModel[1];
        ComparableModels = ComparableModelList.toArray(ComparableModels);

        // 倒序
        sort(ComparableModels, true);
        show(ComparableModels);

        System.out.println();

        // 正序
        sort(arr);
        show(arr);
    }
}
