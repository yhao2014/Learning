package com.ckooc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhao on 2016/11/3.
 */
public class TreeNode implements Serializable {
    private Node currNode;
    private int x;
    private int y;
    private List<TreeNode> childList;

    public TreeNode(Node currNode) {
        this.currNode = currNode;
        this.x = 0;
        this.y = 0;
        this.childList = new ArrayList<>();
    }

    public TreeNode(Node currNode, int x, int y, List<TreeNode> childList) {
        this.currNode = currNode;
        this.x = x;
        this.y = y;
        this.childList = childList;
    }

    public Node getCurrNode() {
        return currNode;
    }

    public void setCurrNode(Node currNode) {
        this.currNode = currNode;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<TreeNode> getChildList() {
        return childList;
    }

    public void setChildList(List<TreeNode> childList) {
        this.childList = childList;
    }
}
