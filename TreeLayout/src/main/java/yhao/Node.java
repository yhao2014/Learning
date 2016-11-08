package yhao;

import java.io.Serializable;

/**
 * 节点类，所有布局算法节点的统一封装
 *
 * Created by yhao on 2016/11/5.
 */
public class Node implements Serializable {
    private String name;
    private double x;
    private double y;
    private double ef;

    public Node(String name, double x, double y, double ef) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.ef = ef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getEf() {
        return ef;
    }

    public void setEf(double ef) {
        this.ef = ef;
    }
}
