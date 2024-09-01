
import java.io.*;
import java.util.ArrayList;

public class Train {
    String examples;
    String hypothesisOut;
    String learningType;
    ArrayList<String[]> trainingData;
    TreeNode root;
    public Train(String examples, String hypothesisOut, String learningType){
        this.examples = examples;
        this.hypothesisOut = hypothesisOut;
        this.learningType = learningType;
        this.trainingData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.examples))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 1) {
                    trainingData.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void trainingModel(){
        if(this.learningType.equals("dt"))
            decisionTreeLearningAlgorithm();
        else
            adaLearningAlgorithm();
    }

    public void decisionTreeLearningAlgorithm(){
        DecisionTree decisionTree = new DecisionTree(this.trainingData);
        TreeNode root=decisionTree.generateDecisionTree(null, this.trainingData);
        TreeNode root1=decisionTree.generateDecisionTree(root, null);
        TreeNode root2=decisionTree.generateDecisionTree(root1, null);
        TreeNode root3=decisionTree.generateDecisionTree(root2, null);
        TreeNode root4=decisionTree.generateDecisionTree(root3, null);
        TreeNode root5=decisionTree.generateDecisionTree(root4, null);
        decisionTree.takeTheMaxResult(root5);
        decisionTree.rootNode = root5;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.hypothesisOut))) {
            oos.writeObject(decisionTree);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("best.model"))) {
            oos.writeObject(decisionTree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adaLearningAlgorithm(){
        AdaBoost adaBoost = new AdaBoost(this.trainingData);
        adaBoost.createStumps();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.hypothesisOut))) {
            oos.writeObject(adaBoost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("best.model"))) {
            oos.writeObject(adaBoost);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
