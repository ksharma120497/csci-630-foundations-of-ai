

import java.io.Serializable;
import java.util.*;

public class DecisionTree implements Serializable {
    private static final long serialVersionUID = 1L;
    ArrayList<String[]> trainingData;
    HashMap<String, TreeNode> map = new HashMap<>();
    DTFeatures dtFeatures;
    TreeNode rootNode;

    public DecisionTree(ArrayList<String[]> trainingData) {
        this.trainingData = trainingData;
        this.dtFeatures = new DTFeatures();
        this.map.put("A", null);
        this.map.put("B", null);
        this.map.put("C", null);
        this.map.put("D", null);
        this.map.put("E", null);
        this.map.put("F", null);
    }

    LinkedHashMap<String, TreeNode> calculateEntropy(ArrayList<String[]> trainingData){

        for(String key: map.keySet()){
            map.put(key, dtFeatures.chooseFunction(key,trainingData));
        }

        List<Map.Entry<String, TreeNode>> list = new LinkedList<>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, TreeNode>>() {
            public int compare(Map.Entry<String, TreeNode> o1, Map.Entry<String, TreeNode> o2) {
                return Double.compare(o1.getValue().probability, o2.getValue().probability);
            }
        });
        LinkedHashMap<String, TreeNode> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, TreeNode> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        if(sortedMap.keySet().size()>0) {
            String featureId = (String) sortedMap.keySet().toArray()[0];
            map.remove(featureId);
        }
        return sortedMap;
    }

    public TreeNode generateDecisionTree(TreeNode root, ArrayList<String[]> trainingData){
        if(root==null){
            LinkedHashMap<String, TreeNode> sortedMap = calculateEntropy(trainingData);
            String featureId=null;
            if(sortedMap.size()>0)
                 featureId = (String) sortedMap.keySet().toArray()[0];
            return sortedMap.get(featureId);
        }
        if(root.featureValue!=null)
            return root;
        root.left = generateDecisionTree(root.left, root.yesData);
        root.right = generateDecisionTree(root.right, root.noData);
        return root;
    }

    public TreeNode takeTheMaxResult(TreeNode root){
        if(root.left==null & root.right==null && root.featureId==null)
            return root;

        if(root.left==null & root.right==null){
            root.left = new TreeNode(root.maxYes);
            root.right = new TreeNode(root.maxNo);
        }
        if(root.left==null){
            root.left = new TreeNode(root.maxYes);
        }
        if(root.right==null){
            root.right = new TreeNode(root.maxNo);
        }
        takeTheMaxResult(root.left);
        takeTheMaxResult(root.right);
        return root;
    }


    public void displayTree(TreeNode root) {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                if(current.featureValue==null)
                    System.out.print(current.featureId + " ");
                else
                    System.out.print(current.featureValue+" ");
                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
            System.out.println(" ");
        }
    }
}


