package y3.tree;


import y3.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhao on 2016/11/5.
 */
public class TreeNode implements Serializable {
    private String id;
    private TreeNode parent;
    private double x;
    private double y;
    private double offset;
    private double change;
    private double shift;
    private int number;
    private TreeNode ancestor;
    private TreeNode thread;
    private TreeNode lmostSibling;
    private String[] childIdList;
    private List<TreeNode> childList;

    public TreeNode(Node node) {
        this.id = node.getId();
        this.parent = null;
        this.x = node.getX();
        this.y = node.getY();
        this.offset = 0;
        this.change = 0;
        this.shift = 0;
        this.number = 1;
        this.ancestor = this;
        this.thread = null;
        this.lmostSibling = null;
        this.childIdList = node.getChildren();
        this.childList = new ArrayList<>();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
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

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getShift() {
        return shift;
    }

    public void setShift(double shift) {
        this.shift = shift;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public TreeNode getAncestor() {
        return ancestor;
    }

    public void setAncestor(TreeNode ancestor) {
        this.ancestor = ancestor;
    }

    public TreeNode getThread() {
        return thread;
    }

    public void setThread(TreeNode thread) {
        this.thread = thread;
    }

    public TreeNode getLmostSibling() {
        return lmostSibling;
    }

    public void setLmostSibling(TreeNode lmostSibling) {
        this.lmostSibling = lmostSibling;
    }

    public String[] getChildIdList() {
        return childIdList;
    }

    public void setChildIdList(String[] childIdList) {
        this.childIdList = childIdList;
    }

    public List<TreeNode> getChildList() {
        return childList;
    }

    public void setChildList(List<TreeNode> childList) {
        this.childList = childList;
    }
}
