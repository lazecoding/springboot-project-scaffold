package lazecoding.project.util;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ListUtil
 *
 * @author lazecoding
 */
public class ListUtil {

    /**
     * 将 list 分割成多个指定大小的 list
     *
     * @param list 大 list
     * @param size 每个小 list 大小
     * @return List<List < T>>
     */
    public static <T> List<List<T>> partition(List<T> list, int size) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<List<T>> lists = new ArrayList<>();
        if (list.size() <= size) {
            lists.add(list);
            return lists;
        } else {
            int startIndex = 0;
            int endIndex = size;
            while (true) {
                if (endIndex > list.size()) {
                    endIndex = list.size();
                }
                List<T> itemList = list.subList(startIndex, endIndex);
                lists.add(itemList);
                if (endIndex == list.size()) {
                    break;
                }
                startIndex = endIndex;
                endIndex = endIndex + size;
            }
        }
        return lists;
    }

    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        list1.add("5");
        list1.add("6");
        list1.add("7");
        list1.add("8");
        list1.add("9");
        List<List<String>> lists = ListUtil.partition(list1, 2);
        System.out.println("List<String>");
        for (List<String> item : lists) {
            System.out.println(item);
        }

        System.out.println("List<Integer>");
        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        list2.add(3);
        list2.add(4);
        list2.add(5);
        list2.add(6);
        list2.add(7);
        list2.add(8);
        list2.add(9);
        List<List<Integer>> lists2 = ListUtil.partition(list2, 2);
        for (List<Integer> item : lists2) {
            System.out.println(item);
        }
    }
}
