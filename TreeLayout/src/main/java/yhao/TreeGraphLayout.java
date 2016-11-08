package yhao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import yhao.layout.Knuth;
import yhao.tree.TreeUtil;
import yhao.tree.TreeNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 树形布局类
 *
 * Created by yhao on 2016/11/5.
 */
public class TreeGraphLayout implements Serializable {
    private int centerX = 400;
    private int centerY = 400;
    private int stepX = 20;
    private int stepY = 40;
    private int[] degrees;
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();
    private HashMap<String, Node> nodeMap = new HashMap<>();
    private HashMap<String, Integer> nodeIndexMap = new HashMap<>();
    private HashMap<String, String> edgeMap = new HashMap<>();

    public TreeGraphLayout() {
    }

    public TreeGraphLayout(JSONObject data) {
        this.loadData(data);
    }

    public void loadData(JSONObject data) {
        JSONArray nodesArr = data.getJSONArray("points");
        JSONArray edgesArr = data.getJSONArray("deges");
        int nodeSize = nodesArr.size();
        int edgeSize = edgesArr.size();

        this.degrees = new int[nodeSize];

        for (int i = 0; i < nodeSize; i++) {
            JSONObject node = nodesArr.getJSONObject(i);
            String name = node.getString("name");
            double x = node.getDouble("x");
            double y = node.getDouble("y");
            double ef = node.getDouble("ef");

            Node tempNode = new Node(name, x, y, ef);
            this.nodes.add(tempNode);
            this.nodeMap.put(name, tempNode);
            this.nodeIndexMap.put(name, i);
        }

        for (int i = 0; i < edgeSize; i++) {
            JSONObject edge = edgesArr.getJSONObject(i);
            String src = edge.getString("s");
            String dis = edge.getString("e");
            Double cf = edge.getDouble("cf");

            this.edgeMap.put(dis, src);

            Node srcNode = this.nodeMap.get(src);
            Node DisNode = this.nodeMap.get(dis);

            this.edges.add(new Edge(srcNode, DisNode, cf));

            int srcIndex = this.nodeIndexMap.get(src);
            int disIndex = this.nodeIndexMap.get(dis);

            this.degrees[srcIndex]++;
            this.degrees[disIndex]--;
        }
    }

    public JSONObject outputData() {
        JSONObject result = new JSONObject();

        JSONArray nodesArr = new JSONArray();
        for (Node node : this.nodes) {
            String name = node.getName();
            double x = node.getX();
            double y = node.getY();
            JSONObject tempNode = new JSONObject();
            tempNode.put("nodeId", this.nodeIndexMap.get(name));
            tempNode.put("x", x);
            tempNode.put("y", y);
            nodesArr.add(tempNode);
        }

        JSONArray edgesArr = new JSONArray();
        for (Edge edge : this.edges) {
            Integer srcIndex = this.nodeIndexMap.get(edge.getSrc().getName());
            Integer disIndex = this.nodeIndexMap.get(edge.getDis().getName());
            JSONObject tempEdge = new JSONObject();
            tempEdge.put("source", srcIndex);
            tempEdge.put("target", disIndex);
            edgesArr.add(tempEdge);
        }

        result.put("nodes", nodesArr);
        result.put("edges", edgesArr);

        return result;
    }

    public void update() {
        TreeUtil treeUtilUtil = new TreeUtil(this.nodes, this.edgeMap);
        TreeNode tree = treeUtilUtil.createTree();

        //knuth的多叉树实现
        Knuth knuth = new Knuth();
        knuth.setup(tree, 0);

        updateNode(tree);
    }

    public void updateNode(TreeNode tree) {
        String nodeName = tree.getName();
        Node node = nodeMap.get(nodeName);
        node.setX(tree.getX() * stepX + centerX);
        node.setY(tree.getY() * stepY + centerY);

        if (!tree.getChildList().isEmpty()) {
            tree.getChildList().forEach(this::updateNode);
        }
    }

    public JSONObject computeNodeDist() {
        this.update();
        return this.outputData();
    }

    public static void main(String[] args) {

        TreeGraphLayout treeLayout = new TreeGraphLayout();
        JSONObject resultJson = treeLayout.computeNodeDist();
        String result = JSON.toJSONString(resultJson);
        System.out.println(result);
    }
}
