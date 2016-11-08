package y3;

import java.io.Serializable;

/**
 * Created by yhao on 2016/11/5.
 */
public class Edge implements Serializable {
    private Node src;
    private Node dis;
    private double cf;

    public Edge(Node src, Node dis, double cf) {
        this.src = src;
        this.dis = dis;
        this.cf = cf;
    }

    public Node getSrc() {
        return src;
    }

    public void setSrc(Node src) {
        this.src = src;
    }

    public Node getDis() {
        return dis;
    }

    public void setDis(Node dis) {
        this.dis = dis;
    }

    public double getCf() {
        return cf;
    }

    public void setCf(double cf) {
        this.cf = cf;
    }
}
