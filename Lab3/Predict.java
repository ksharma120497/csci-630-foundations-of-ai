

import java.io.*;
import java.util.ArrayList;

public class Predict {
    String hypothesis;
    String file;
    ArrayList<String> predictionData;
    TreeNode root;

    ArrayList<TreeNode> stumpList;

    public Predict(String hypothesis, String file){
        this.hypothesis = hypothesis;
        this.file = file;
        predictionData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.file))) {
            String line;
            while ((line = br.readLine()) != null) {
                predictionData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.hypothesis))) {
            if(this.hypothesis.equals("dt.model") || this.hypothesis.equalsIgnoreCase("/autograder/submission/best.model") || this.hypothesis.contains("best.model")) {
                DecisionTree decisionTree = (DecisionTree) ois.readObject();
                root = decisionTree.rootNode;
            }
            else {
                AdaBoost adaBoost = (AdaBoost) ois.readObject();
                this.stumpList = adaBoost.getStumpList();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showResults() {
        if (this.hypothesis.equals("dt.model") || this.hypothesis.equalsIgnoreCase("/autograder/submission/best.model") || this.hypothesis.contains("best.model")) {
            for(String testData: this.predictionData)
                recursiveFunction(root, testData);
        }
        else{
            for (String testData : this.predictionData) {
                double en = 0, nl = 0;
                for (TreeNode node : this.stumpList) {
                    String answer = node.evaluateStumps(testData);
                    if (answer.equals("en"))
                        en += node.amountOfSay;
                    else
                        nl += node.amountOfSay;
                }
                if (en > nl)
                    System.out.println("en");
                else
                    System.out.println("nl");
            }
        }
    }


    public void recursiveFunction(TreeNode root, String test){
        if(root.featureValue!=null) {
            System.out.println(root.featureValue);
            return;
        }
        if(root.evaluateTreeNode(test))
            recursiveFunction(root.left, test);
        else
            recursiveFunction(root.right, test);
    }

}
