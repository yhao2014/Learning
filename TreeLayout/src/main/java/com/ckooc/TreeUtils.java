package com.ckooc;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * 生成多叉树工具类
 *
 * Created by yhao on 2016/11/3.
 */
public class TreeUtils {
    private TreeUtils() {
    }

    public static MultTree createTree(Node root, Set<Node> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        Queue<Node> queue = new LinkedList<>();
        MultTree tree = new MultTree(root);

        queue.addAll(nodes);

        while (!queue.isEmpty()) {
            Node node = queue.element();
            boolean flag = addChild(tree.getRoot(), node);
            if (flag) {
                queue.remove();
            } else {
                queue.remove();
                queue.add(node);        //将添加失败的节点放至队尾
            }
        }

        return tree;
    }

    private static boolean addChild(TreeNode currNode, Node juniorNode) {
        if (currNode.getCurrNode().getNodeId() == juniorNode.getParentId()) {
            currNode.getChildList().add(new TreeNode(juniorNode));
            return true;
        } else {
            for (TreeNode child: currNode.getChildList()) {
                if (addChild(child, juniorNode)) {
                    return true;
                }
            }

            return false;
        }
    }

    /*public static void printTree(TreeNode currNode) {
        StringBuilder result = new StringBuilder();


    }*/


    public static void main(String[] args) {
        Set<Node> nodeSet = new HashSet<>();

        Node root = new Node(1, 0, "root");

        nodeSet.add(new Node(2, 1, ""));
        nodeSet.add(new Node(3, 1, ""));
        nodeSet.add(new Node(4, 2, ""));
        nodeSet.add(new Node(5, 3, ""));
        nodeSet.add(new Node(6, 3, ""));
        nodeSet.add(new Node(7, 3, ""));
        nodeSet.add(new Node(8, 6, ""));

        MultTree tree = createTree(root, nodeSet);

        System.out.println("dfsfsa");
    }
}
