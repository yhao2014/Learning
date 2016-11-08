package y3.layout;



import y3.tree.TreeNode;

import java.util.List;

/**
 * Created by yhao on 2016/11/7.
 */
public class WS {

    public void layout(TreeNode root, int nodeSize) {
        setup(root, 0, new int[nodeSize], new int[nodeSize]);
        addmods(root, 0);
    }

    public void setup(TreeNode root, int depth, int[] nexts, int[] offset) {
        for (TreeNode child : root.getChildList()) {
            setup(child, depth + 1, nexts, offset);
        }

        root.setY(depth);

        int place;

        List<TreeNode> childList = root.getChildList();
        int size = childList.size();

        if (childList.isEmpty()) {
            place = nexts[depth];
            root.setX(place);
        } else {
            place = (int) ((childList.get(0).getX() + childList.get(size - 1).getX()) / 2);
        }

        offset[depth] = Math.max(offset[depth], nexts[depth] - place);

        if (!childList.isEmpty()) {
            root.setX(place + offset[depth]);
        }

        nexts[depth] += 2;
        root.setOffset(offset[depth]);

    }

    public void addmods(TreeNode root, int modsum) {
        root.setX(root.getX() + modsum);
        modsum += root.getOffset();

        for (TreeNode child : root.getChildList()) {
            addmods(child, modsum);
        }
    }
}
