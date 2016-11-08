package yhao.tree;

import yhao.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yhao on 2016/11/5.
 */
public class TreeNode implements Serializable {
    private String name;
    private String parentName;
    private double x;
    private double y;
    private int offset;
    private List<TreeNode> childList;

    public TreeNode(Node node, HashMap<String, String> edgeMap) {
        this.name = node.getName();
        this.parentName = edgeMap.get(node.getName());
        this.x = node.getX();
        this.y = node.getY();
        this.offset = 0;
        this.childList = new ArrayList<>();
    }



    public TreeNode(String name, String parentName, double x, double y, int offset, List<TreeNode> childList) {
        this.name = name;
        this.parentName = parentName;
        this.x = x;
        this.y = y;
        this.offset = offset;
        this.childList = childList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<TreeNode> getChildList() {
        return childList;
    }

    public void setChildList(List<TreeNode> childList) {
        this.childList = childList;
    }
}
