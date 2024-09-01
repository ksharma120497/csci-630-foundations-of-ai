
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TreeNode implements Serializable {
    ArrayList<String[]> yesData;
    ArrayList<String[]> noData;
    String featureId;
    String featureValue;
    double probability;
    double informationGain;
    TreeNode left;
    TreeNode right;
    String maxYes;
    String maxNo;

    double amountOfSay;

    ArrayList<Integer> errorSampleWeights = new ArrayList<>();


    public TreeNode(){
        this.featureValue = null;
    }

    public TreeNode(String featureValue){
        this.featureValue = featureValue;
    }

    public TreeNode(String featureId, double probability) {
        this.featureId = featureId;
        this.probability = probability;
        this.left = null;
        this.right = null;
    }

    public TreeNode(ArrayList<String[]> yesData, ArrayList<String[]> noData,String featureId, double probability, TreeNode left, TreeNode right, String maxYes, String maxNo) {
        this.yesData = yesData;
        this.noData = noData;
        this.featureId = featureId;
        this.probability = probability;
        this.left = left;
        this.right = right;
        this.maxYes = maxYes;
        this.maxNo = maxNo;
    }

    public String evaluateStumps(String sample){
        switch (this.featureId){
            case "A":{
                for(String word: sample.split(" ")){
                    if(word.contains("Y") || word.contains("y")) {
                        return "en";
                    }
                }
                return "nl";
            }
            case "B":{
                for(String word: sample.split(" ")){
                    if(word.equalsIgnoreCase("de")) {
                        return "nl";
                    }
                }
                return "en";
            }
            case "C":{
                for(String word: sample.split(" ")){
                    if(word.length()>=10) {
                        return "nl";
                    }
                }
                return "en";
            }
            case "D":{
                int isWordRepeat=0;
                for(String word: sample.split(" ")){
                    Map<Character, Integer> charCountMap = new HashMap<>();
                    for (char ch : word.toCharArray()) {
                        charCountMap.put(ch, charCountMap.getOrDefault(ch, 0) + 1);
                    }
                    for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
                        if(entry.getValue()>=2)
                            isWordRepeat++;
                    }
                }
                return isWordRepeat<3?"en":"nl";
            }
            case "E":{
                for(String word: sample.split(" ")){
                    if(word.equalsIgnoreCase("en") || word.equalsIgnoreCase("een") || word.equalsIgnoreCase("aan")) {
                        return "nl";
                    }
                }
                return "en";
            }
            case "F":{
                for(String word: sample.split(" ")){
                    if(word.equalsIgnoreCase("the")) {
                        return "en";
                    }
                }
                return "nl";
            }
        }
        return null;
    }

    public boolean evaluateTreeNode(String sample){
        switch (this.featureId){
            case "A":{
                for(String word: sample.split(" ")){
                    if(word.contains("Y") || word.contains("y")) {
                        return false;
                    }
                }
                return true;
            }
            case "B":{
                for(String word: sample.split(" ")){
                    if(word.equalsIgnoreCase("de")) {
                        return false;
                    }
                }
                return true;
            }
            case "C":{
                for(String word: sample.split(" ")){
                    if(word.length()>=10) {
                        return true;
                    }
                }
                return false;
            }
            case "D":{
                int isWordRepeat=0;
                for(String word: sample.split(" ")){
                    Map<Character, Integer> charCountMap = new HashMap<>();
                    for (char ch : word.toCharArray()) {
                        charCountMap.put(ch, charCountMap.getOrDefault(ch, 0) + 1);
                    }
                    for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
                        if(entry.getValue()>=2)
                            isWordRepeat++;
                    }
                }
                return isWordRepeat<3;
            }
            case "E":{
                for(String word: sample.split(" ")){
                    if(word.equalsIgnoreCase("en") || word.equalsIgnoreCase("een") || word.equalsIgnoreCase("aan")) {
                        return true;
                    }
                }
                return false;
            }
            case "F": {
                for(String word: sample.split(" ")){
                    if(word.equalsIgnoreCase("the")) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public void setAmountOfSay(double amountOfSay) {
        this.amountOfSay = amountOfSay;
    }

    public void setErrorSampleWeights(ArrayList<Integer> errorSampleWeights) {
        this.errorSampleWeights = errorSampleWeights;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "featureId='" + featureId + '\'' +
                ", featureValue='" + featureValue + '\'' +
                ", probability=" + probability +
                ", informationGain=" + informationGain +
                ", maxYes='" + maxYes + '\'' +
                ", maxNo='" + maxNo + '\'' +
                ", amountOfSay='" + amountOfSay + '\'' +
                ", errorListSize='" + errorSampleWeights.size() + '\'' +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
