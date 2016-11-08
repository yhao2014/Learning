package yhao.tree;

import yhao.Node;
import yhao.layout.WS;

import java.io.Serializable;
import java.util.*;

/**
 * 树形类，主要用于将节点和边构造成一颗多叉树
 *
 * Created by yhao on 2016/11/5.
 */
public class TreeUtil implements Serializable {
    private ArrayList<Node> nodes;
    private HashMap<String, String> edgeMap;

    public TreeUtil(ArrayList<Node> nodes, HashMap<String, String> edgeMap) {
        this.nodes = nodes;
        this.edgeMap = edgeMap;
    }

    public TreeNode createTree() {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        ArrayList<TreeNode> treeNodes = createTreeNodes();

        Set<String> keys = edgeMap.keySet();

        String rootName = "";
        for (String key : keys) {
            if (!keys.contains(edgeMap.get(key))) rootName = edgeMap.get(key);
        }

        TreeNode rootTreeNode = null;

        if (rootName.isEmpty()) {
            System.out.println("找不到根节点！");
            return null;
        } else {
            for (TreeNode treeNode : treeNodes) {
                if (treeNode.getName().equals(rootName)) {
                    rootTreeNode = treeNode;
                }
            }
        }

        if (rootTreeNode == null) {
            return null;
        }
        treeNodes.remove(rootTreeNode);

        Queue<TreeNode> queue = new LinkedList<>();
        queue.addAll(treeNodes);

        while (!queue.isEmpty()) {
            TreeNode node = queue.element();
            boolean flag = addChild(rootTreeNode, node);
            if (flag) {
                queue.remove();
            } else {
                queue.remove();
                queue.add(node);        //将添加失败的节点放至队尾
            }
        }

        return rootTreeNode;
    }

    public ArrayList<TreeNode> createTreeNodes() {
        ArrayList<TreeNode> treeNodes = new ArrayList<>();

        for (Node node : nodes) {
            //TreeNode treeNode = new TreeNode(node, edgeMap);
            String name = node.getName();
            String parentName = edgeMap.get(node.getName());
            TreeNode treeNode = new TreeNode(name, parentName, 0, 0, 0, new ArrayList<>());
            treeNodes.add(treeNode);
        }

        return treeNodes;
    }

    public boolean addChild(TreeNode currNode, TreeNode juniorNode) {
        if (currNode.getName().equalsIgnoreCase(juniorNode.getParentName())) {
            currNode.getChildList().add(juniorNode);
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


    public static void main(String[] args) {
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(new Node("a", 0, 0, 1.0));
        nodes.add(new Node("b", 0, 0, 1.0));
        nodes.add(new Node("c", 0, 0, 1.0));
        nodes.add(new Node("d", 0, 0, 1.0));
        nodes.add(new Node("e", 0, 0, 1.0));
        nodes.add(new Node("f", 0, 0, 1.0));
        nodes.add(new Node("g", 0, 0, 1.0));
        nodes.add(new Node("h", 0, 0, 1.0));

        HashMap<String, String> edgeMap = new HashMap<>();
        edgeMap.put("b", "a");
        edgeMap.put("c", "a");
        edgeMap.put("d", "b");
        edgeMap.put("e", "c");
        edgeMap.put("f", "c");
        edgeMap.put("g", "c");
        edgeMap.put("h", "f");

        TreeUtil treeUtil = new TreeUtil(nodes, edgeMap);
        TreeNode tree = treeUtil.createTree();

        //-- Knuth 算法。主要是从左往右每增加一个节点，下一个节点的x坐标就会+1，缺点是结构松散，并且不是平衡的，子树整体右倾
//        Knuth knuth = new Knuth();
//        knuth.setup(yhao.tree, 0);

        //-- “父节点中心+偏移子树偏移”算法。自底向上算法。每个父节点在其子节点x坐标的中间，
        // 如果当前节点的子节点层节点数相对较少，且当前节点同一层前面的节点较多时，会对当前节点及其子树向右偏移
        WS ws = new WS();
        ws.layout(tree, nodes.size());

        System.out.println("werwefsd");
    }
}
