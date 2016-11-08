package y3;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import y3.layout.Contour;
import y3.layout.Knuth;
import y3.tree.TreeNode;
import y3.tree.TreeUtil;

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
    private ArrayList<Node> nodes = new ArrayList<>();
    private HashMap<String, Node> nodeMap = new HashMap<>();
    private HashMap<String, Integer> nodeIndexMap = new HashMap<>();
    private HashMap<String, String> edgeMap = new HashMap<>();

    public TreeGraphLayout() {
    }

    public TreeGraphLayout(JSONObject data) {
        this.loadData(data);
    }

    public void loadData(JSONObject data) {
        JSONArray nodesArr = data.getJSONArray("root");
        int nodeSize = nodesArr.size();

        for (int i = 0; i < nodeSize; i++) {
            JSONObject node = nodesArr.getJSONObject(i);
            String id = node.getString("id");
            JSONArray childArr = node.getJSONArray("childs");

            String[] children = new String[childArr.size()];

            for (int j = 0; j < childArr.size(); j++) {
                String childId = childArr.getJSONObject(j).getString("id");
                children[j] = childId;

                edgeMap.put(childId, id);
            }

            Node tempNode = new Node(id, children);
            this.nodes.add(tempNode);
            this.nodeMap.put(id, tempNode);
            this.nodeIndexMap.put(id, i);
        }

    }

    public JSONObject outputData() {
        JSONObject result = new JSONObject();

        JSONArray nodesArr = new JSONArray();
        for (Node node : this.nodes) {
            String id = node.getId();
            double x = node.getX();
            double y = node.getY();
            String[] children = node.getChildren();
            JSONObject tempNode = new JSONObject();
            tempNode.put("nodeId", id);
            tempNode.put("x", x);
            tempNode.put("y", y);
            tempNode.put("childs", children);
            nodesArr.add(tempNode);
        }

        result.put("root", nodesArr);

        return result;
    }

    public void update() {
        TreeUtil treeUtil = new TreeUtil(this.nodes);
        TreeNode tree = treeUtil.createTree();

        //knuth的多叉树实现
        Contour contour = new Contour();
        contour.setup(tree);

        updateNode(tree);
    }

    public void updateNode(TreeNode tree) {
        String nodeName = tree.getId();
        Node node = nodeMap.get(nodeName);
        node.setX(tree.getX() * stepX + centerX);
        node.setY(tree.getY() * stepY + centerY);

        if (!tree.getChildList().isEmpty()) {
//            yhao.tree.getChildList().forEach(this::updateNode);
            for (TreeNode child : tree.getChildList()) {
                updateNode(child);
            }
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
