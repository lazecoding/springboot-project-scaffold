package lazecoding.project.common.util.tree;

import java.util.*;

/**
 * 树节点汇总计算
 *
 * @author lazecoding
 */
public class TreeSummary {

    /**
     * 汇总树形数据
     * 加工 nodes 入参列表，为其元素添加了 sum 树形的节点列表，如果是不存在的逻辑根节点会为其创建节点
     *
     * @param nodes  树节点列表
     * @param rootId 逻辑根节点，起始计算的节点
     */
    public static Map<String, Node> summary(List<Node> nodes, String rootId) {
        if (nodes == null || nodes.isEmpty()) {
            return new HashMap<>();
        }
        // 获取每个节点的子节点
        Map<String, List<Node>> childMap = new HashMap<>();
        for (Node node : nodes) {
            String pid = node.getPid();
            if (!childMap.containsKey(pid)) {
                childMap.put(pid, new ArrayList<>());
            }
            childMap.get(pid).add(node);
        }
        // 维护 nodeId 序列
        List<String> nodeOrder = new ArrayList<>();
        recursion(rootId, childMap, nodeOrder);
        // 存储根据 id 存储每个 node
        Map<String, Node> nodeMap = new HashMap<>();
        for (Node node : nodes) {
            nodeMap.put(node.getId(), node);
        }
        // 如果根节点不存在则主动添加
        if (!nodeMap.containsKey(rootId)) {
            Node root = new Node("", rootId, 0L);
            nodes.add(root);
            nodeMap.put(rootId, root);
        }
        // 倒序
        Collections.reverse(nodeOrder);
        for (String nodeId : nodeOrder) {
            Node node = nodeMap.get(nodeId);
            List<Node> childNodes = childMap.get(nodeId);
            long count = node.getValue();
            if (childNodes != null && !childNodes.isEmpty()) {
                for (Node child : childNodes) {
                    count += child.getSum();
                }
            }
            node.setSum(count);
        }
        return nodeMap;
    }

    /**
     * 递归
     */
    public static void recursion(String pid, Map<String, List<Node>> childMap, List<String> nodeOrder) {
        List<Node> childNodes = childMap.get(pid);
        nodeOrder.add(pid);
        if (childNodes != null && !childNodes.isEmpty()) {
            for (Node node : childNodes) {
                recursion(node.getId(), childMap, nodeOrder);
            }
        }
    }

    public static void main(String[] args) {
        // case: 逻辑顶级节点 0
        //     1                     2
        //   3   4          11   12    13        14
        // 5-6-7                              15-16-17
        //     8
        //    9-10
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node("0", "1", 1L));
        nodes.add(new Node("0", "2", 1L));
        nodes.add(new Node("1", "3", 1L));
        nodes.add(new Node("1", "4", 1L));
        nodes.add(new Node("3", "5", 1L));
        nodes.add(new Node("3", "6", 1L));
        nodes.add(new Node("3", "7", 1L));
        nodes.add(new Node("7", "8", 1L));
        nodes.add(new Node("8", "9", 1L));
        nodes.add(new Node("8", "10", 1L));
        nodes.add(new Node("2", "11", 1L));
        nodes.add(new Node("2", "12", 1L));
        nodes.add(new Node("2", "13", 1L));
        nodes.add(new Node("2", "14", 1L));
        nodes.add(new Node("14", "15", 1L));
        nodes.add(new Node("14", "16", 1L));
        nodes.add(new Node("14", "17", 1L));
        Map<String, Node> result = summary(nodes, "0");
        System.out.println(result.toString());
    }
}
