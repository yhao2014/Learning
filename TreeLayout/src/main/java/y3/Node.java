package y3;

import java.io.Serializable;

/**
 * 节点类，所有布局算法节点的统一封装
 *
 * Created by yhao on 2016/11/5.
 */
public class Node implements Serializable {
    private String id;
    private double x;
    private double y;
    private String[] children;

    public Node(String id, String[] children) {
        this.id = id;
        this.x = -1.0;
        this.y = 0;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String[] getChildren() {
        return children;
    }

    public void setChildren(String[] children) {
        this.children = children;
    }
}
