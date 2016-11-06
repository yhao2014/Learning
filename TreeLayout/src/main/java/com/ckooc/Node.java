package com.ckooc;

/**
 * 节点类，包含: 节点ID, 父节点ID, 节点信息
 *
 * Created by yhao on 2016/11/3.
 */
public class Node {
    private int nodeId;
    private int parentId;
    private String text;

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Node(int nodeId, int parentId) {
        new Node(nodeId, parentId, "");
    }

    public Node(int nodeId, int parentId, String text) {
        this.nodeId = nodeId;
        this.parentId = parentId;
        this.text = text;
    }
}
