package y3.layout;

import y3.tree.TreeNode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public class Contour implements Serializable {

    public TreeNode setup(TreeNode root) {
        TreeNode dt = firstwalk(root, 1.0);
        double min = secondwork(dt, 0, 0, -9999);

        if (min < 0) {
            thirdwork(dt, -min);
        }

        return dt;
    }

    public TreeNode firstwalk(TreeNode root, double distance) {

        if (root.getChildList().isEmpty()) {        //设置叶节点的x坐标为其前一个兄弟节点的x坐标加上间隔distance，如果它是父节点的第一个节点，设置x=0
            if (root.getLmostSibling() != null) {
                root.setX(getLBrother(root).getX() + distance);
            } else {
                root.setX(0.0);
            }
        } else {
            List<TreeNode> childList = root.getChildList();
            TreeNode defaultAncestor = childList.get(0);

            for (TreeNode child : childList) {
                firstwalk(child, 1.0);
                defaultAncestor = apportion(child, defaultAncestor, distance);
            }

            System.out.println("finished root = " + root.getId() + " children");

            execShift(root);

            double midpoint = (childList.get(0).getX() + childList.get(childList.size() - 1).getX()) / 2;

            TreeNode w = getLBrother(root);
            if (w != null) {
                root.setX(w.getX() + distance);
                root.setOffset(root.getX() - midpoint);
            } else {
                root.setX(midpoint);
            }
        }

        return root;
    }

    public double secondwork(TreeNode root, double m, double depth, double min) {
        root.setX(root.getX() + m);
        root.setY(depth);

        if (min == -9999 || root.getX() < min) {
            min = root.getX();
        }

        for (TreeNode child : root.getChildList()) {
            min = secondwork(child, m + root.getOffset(), depth + 1, min);
        }

        return min;
    }

    public void thirdwork(TreeNode root, double n) {
        root.setX(root.getX() + n);
        for (TreeNode child : root.getChildList()) {
            thirdwork(child, n);
        }
    }

    public TreeNode getLeftChild(TreeNode currNode) {
        TreeNode leftChild = null;

        if (currNode.getThread() != null) {
            leftChild = currNode.getThread();
        } else {
            List<TreeNode> childList = currNode.getChildList();
            if (!childList.isEmpty()) {
                leftChild = childList.get(childList.size() - 1);
            }
        }

        return leftChild;
    }

    public TreeNode getRightChild(TreeNode currNode) {
        TreeNode rightChild = null;

        if (currNode.getThread() != null) {
            rightChild = currNode.getThread();
        } else {
            List<TreeNode> childList = currNode.getChildList();

            if (!childList.isEmpty()) {
                rightChild = childList.get(childList.size() - 1);
            }

        }

        return rightChild;
    }


    /**
     * 取得左边的兄弟节点
     * @param currNode
     * @return
     */
    public TreeNode getLBrother(TreeNode currNode) {
        TreeNode n = null;
        TreeNode parent = currNode.getParent();
        if (parent != null) {
            for (TreeNode child : parent.getChildList()) {
                if (child.getId().equals(currNode.getId())) return n;
                else n = child;
            }
        }

        return n;
    }


    public TreeNode apportion(TreeNode v, TreeNode defaultAncestor, double distance) {
        TreeNode w = getLBrother(v);

        if (w != null) {
            TreeNode vir = v;
            TreeNode vor = v;
            TreeNode vil = w;
            TreeNode vol = v.getLmostSibling();

            double sir = v.getOffset();
            double sor = v.getOffset();
            double sil = vil.getOffset();
            double sol = vol.getOffset();

            while (getRightChild(vil) != null && getLeftChild(vir) != null){
                vil = getRightChild(vil);
                vir = getLeftChild(vir);
                vol = getLeftChild(vol);
                vor = getRightChild(vor);
                vor.setAncestor(v);

                double shift = vil.getX() + sil - vir.getX() - sir + distance;

                if (shift > 0) {
                    moveSubtree(ancestor(vil, v, defaultAncestor), v, shift);
                    sir += shift;
                    sor += shift;
                }

                sil += vil.getOffset();
                sir += vir.getOffset();
                sol += vol.getOffset();
                sor += vor.getOffset();
            }

            if (getRightChild(vil) != null && getRightChild(vor) == null) {
                vor.setThread(getRightChild(vil));
                vor.setOffset(vor.getOffset() + sil - sor);
            } else {
                if (getLeftChild(vir) != null && getLeftChild(vol) == null) {
                    vol.setThread(getLeftChild(vir));
                    vol.setOffset(vol.getOffset() + sir - sol);
                }
                defaultAncestor = v;
            }
        }

        return defaultAncestor;
    }


    public void moveSubtree(TreeNode wl, TreeNode wr, double shift) {
        int subtrees = wr.getNumber() - wl.getNumber();

        System.out.println(wl.getId() + " is conflicted with " + wr.getId() + " moving " + subtrees + " shift " + shift);

        wr.setChange(wr.getChange() - (shift / subtrees));
    }

    public TreeNode ancestor(TreeNode vil, TreeNode v, TreeNode defaultAncestor) {
        List<TreeNode> brothers = v.getParent().getChildList();
        if (brothers.contains(vil.getAncestor())) {
            return vil.getAncestor();
        } else {
            return defaultAncestor;
        }
    }

    public void execShift(TreeNode v) {
        double shift = 0;
        double change = 0;


        for (TreeNode w : v.getChildList()) {
            System.out.println("shift: " + w.getId() + " " + shift + " " + w.getChange());
            w.setX(w.getX() + shift);
            w.setOffset(w.getOffset() + shift);
            change += w.getChange();
            shift += w.getShift() + change;
        }
    }
}
