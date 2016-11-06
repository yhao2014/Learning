package com.ckooc.y3.layout;

import com.ckooc.y3.tree.TreeNode;

/**
 * Created by yhao on 2016/11/6.
 */
public class Knuth {
    private double i;

    public Knuth() {
        this.i = 0;
    }

    public void setup(TreeNode root, int depth) {
        root.setX(i);
        root.setY(depth);

        if (!root.getChildList().isEmpty()) {
            for (TreeNode child : root.getChildList()) {
                i += 1;
                setup(child, depth + 1);
            }
        }
    }
}
