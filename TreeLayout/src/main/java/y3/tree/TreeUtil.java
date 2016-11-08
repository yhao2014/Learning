package y3.tree;


import y3.Node;
import y3.layout.Contour;
import y3.layout.WS;

import java.io.Serializable;
import java.util.*;

/**
 * 树形类，主要用于将节点和边构造成一颗多叉树
 *
 * Created by yhao on 2016/11/5.
 */
public class TreeUtil implements Serializable {
    private ArrayList<Node> nodes;

    public TreeUtil(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public TreeNode createTree() {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }

        HashMap<String, String> edgeMap = new HashMap<>();

        for (Node node : nodes) {
            String[] childIdList = node.getChildren();
            if (childIdList.length != 0) {
                for (String childId : childIdList) {
                    edgeMap.put(childId, node.getId());
                }
            }
        }

        Set<String> keys = edgeMap.keySet();

        String rootId = "";
        for (String key : keys) {
            if (!keys.contains(edgeMap.get(key))) rootId = edgeMap.get(key);
        }


        HashMap<String, TreeNode> treeNodeMap = new HashMap<>();
        ArrayList<TreeNode> treeNodes = createTreeNodes();

        TreeNode rootTreeNode = null;
        for (TreeNode treeNode : treeNodes) {
            treeNodeMap.put(treeNode.getId(), treeNode);
            if (treeNode.getId().equals(rootId)) {
                rootTreeNode = treeNode;
            }
        }

        if (rootTreeNode == null) {
            return null;
        }

        addChild(rootTreeNode, treeNodeMap, edgeMap);
        setFirstBrother(rootTreeNode);

        return rootTreeNode;
    }

    public ArrayList<TreeNode> createTreeNodes() {
        ArrayList<TreeNode> treeNodes = new ArrayList<>();

        for (Node node : nodes) {
            TreeNode treeNode = new TreeNode(node);
            treeNodes.add(treeNode);
        }

        return treeNodes;
    }

    public void addChild(TreeNode currNode, HashMap<String, TreeNode> treeNodeMap, HashMap<String, String> edgeMap) {
        String[] childIdList = currNode.getChildIdList();
        List<TreeNode> childList = currNode.getChildList();
        for (String childId : childIdList) {
            childList.add(treeNodeMap.get(childId));
        }

        //添加子节点列表
        currNode.setChildList(childList);

        //添加父节点
        currNode.setParent(treeNodeMap.get(edgeMap.get(currNode.getId())));

        for (TreeNode child : childList) {
            child.setY(currNode.getY() + 1);
            child.setNumber(currNode.getNumber() + 1);
            addChild(child, treeNodeMap, edgeMap);
        }
    }


    public void setFirstBrother(TreeNode currNode) {
        if (currNode.getLmostSibling() == null && currNode.getParent() != null) {
            TreeNode first = currNode.getParent().getChildList().get(0);
            if (!currNode.getId().equals(first.getId())) {
                currNode.setLmostSibling(first);
            }
        }

        for (TreeNode child : currNode.getChildList()) {
            setFirstBrother(child);
        }
    }


    public static void main(String[] args) {
        ArrayList<Node> nodes = new ArrayList<>();
        /*nodes.add(new Node("a", new String[]{"b", "c"}));
        nodes.add(new Node("b", new String[]{"d"}));
        nodes.add(new Node("c", new String[]{"e", "f", "g"}));
        nodes.add(new Node("d", new String[]{}));
        nodes.add(new Node("e", new String[]{}));
        nodes.add(new Node("f", new String[]{"h"}));
        nodes.add(new Node("g", new String[]{}));
        nodes.add(new Node("h", new String[]{}));*/

        nodes.add(new Node("root", new String[]{"bigleft", "m1", "m2", "m3", "m4", "bigright"}));
        nodes.add(new Node("bigleft", new String[]{"l1", "l2", "l3", "l4", "l5", "l6", "l7"}));
        nodes.add(new Node("m1", new String[]{}));
        nodes.add(new Node("m2", new String[]{}));
        nodes.add(new Node("m3", new String[]{"m31"}));
        nodes.add(new Node("m4", new String[]{}));
        nodes.add(new Node("bigright", new String[]{"brr"}));
        nodes.add(new Node("l1", new String[]{}));
        nodes.add(new Node("l2", new String[]{}));
        nodes.add(new Node("l3", new String[]{}));
        nodes.add(new Node("l4", new String[]{}));
        nodes.add(new Node("l5", new String[]{}));
        nodes.add(new Node("l6", new String[]{}));
        nodes.add(new Node("l7", new String[]{"ll1"}));
        nodes.add(new Node("m31", new String[]{}));
        nodes.add(new Node("brr", new String[]{"br1", "br2", "br3", "br4", "br5", "br6", "br7"}));
        nodes.add(new Node("ll1", new String[]{}));
        nodes.add(new Node("br1", new String[]{}));
        nodes.add(new Node("br2", new String[]{}));
        nodes.add(new Node("br3", new String[]{}));
        nodes.add(new Node("br4", new String[]{}));
        nodes.add(new Node("br5", new String[]{}));
        nodes.add(new Node("br6", new String[]{}));
        nodes.add(new Node("br7", new String[]{}));

        /*HashMap<String, String> edgeMap = new HashMap<>();
        edgeMap.put("b", "a");
        edgeMap.put("c", "a");
        edgeMap.put("d", "b");
        edgeMap.put("e", "c");
        edgeMap.put("f", "c");
        edgeMap.put("g", "c");
        edgeMap.put("h", "f");*/

        TreeUtil treeUtil = new TreeUtil(nodes);
        TreeNode tree = treeUtil.createTree();

        //-- Knuth 算法。主要是从左往右每增加一个节点，下一个节点的x坐标就会+1，缺点是结构松散，并且不是平衡的，子树整体右倾
//        Knuth knuth = new Knuth();
//        knuth.setup(yhao.tree, 0);

        //-- “父节点中心+偏移子树偏移”算法。自底向上算法。每个父节点在其子节点x坐标的中间，
        // 如果当前节点的子节点层节点数相对较少，且当前节点同一层前面的节点较多时，会对当前节点及其子树向右偏移
//        WS ws = new WS();
//        ws.layout(tree, nodes.size());

        //-- 轮廓法。结合前面的自底向上偏移法
        Contour contour = new Contour();
        contour.setup(tree);

        System.out.println("werwefsd");
    }
}
