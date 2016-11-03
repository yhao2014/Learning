package com.ckooc;

import java.util.ArrayList;

/**
 * Created by yhao on 2016/11/3.
 */
public class MultTree {
    private TreeNode root;

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public MultTree(Node root) {
        this.root = new TreeNode(root);
    }
}
