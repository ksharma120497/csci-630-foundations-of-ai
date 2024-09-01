


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class DTFeatures implements Serializable {
    private TreeNode hasThe(ArrayList<String[]> trainingData) {
        double impurity;
        int nlCountYes=0;
        int enCountYes=0;
        int nlCountNo=0;
        int enCountNo=0;
        ArrayList<String[]> yesData = new ArrayList<>();
        ArrayList<String[]> noData = new ArrayList<>();
        for(String[] data: trainingData){
            boolean hasThe=false;
            String sample = data[1];
            String answer = data[0];
            for(String word: sample.split(" ")){
                if(word.equalsIgnoreCase("the") ) {
                    hasThe = true;
                    break;
                }
            }
            if(hasThe){
                if(answer.equals("en")) enCountYes++;
                else nlCountYes++;
                yesData.add(data);
            }
            else{
                if(answer.equals("en")) enCountNo++;
                else nlCountNo++;
                noData.add(data);
            }
        }
        double totalInYes = enCountYes + nlCountYes;
        double totalInNo = enCountNo + nlCountNo;
        double totalOverall = totalInNo + totalInYes;
        double impurity1 = 1 - Math.pow((enCountYes/totalInYes),2) - Math.pow((nlCountYes/totalInYes),2);
        double impurity2 = 1 - Math.pow((enCountNo/totalInNo),2) - Math.pow((nlCountNo/totalInNo),2);
        impurity = ((totalInYes/totalOverall)*impurity1) + ((totalInNo/totalOverall)*impurity2);
        String yesLabel=null, noLabel=null;

        if(totalInYes==enCountYes) yesLabel = "en";
        else if(totalInYes==nlCountYes) yesLabel="nl";

        if(totalInNo==enCountNo) noLabel="en";
        else if(totalInNo==nlCountNo) noLabel="nl";

        String maxYes = enCountYes > nlCountYes? "en": "nl";
        String maxNo = enCountNo > nlCountNo ? "en" : "nl";

        if(totalInNo==0 || totalInYes==0)
            impurity=0;

        return new TreeNode(yesData, noData,"F",impurity, yesLabel==null? null:new TreeNode(yesLabel), noLabel==null? null:new TreeNode(noLabel), maxYes, maxNo);
    }
    private TreeNode hasEnAn(ArrayList<String[]> trainingData) {
        double impurity;
        int nlCountYes=0;
        int enCountYes=0;
        int nlCountNo=0;
        int enCountNo=0;
        ArrayList<String[]> yesData = new ArrayList<>();
        ArrayList<String[]> noData = new ArrayList<>();
        for(String[] data: trainingData){
            boolean isEn=false;
            String sample = data[1];
            String answer = data[0];
            for(String word: sample.split(" ")){
                if(word.equalsIgnoreCase("en") || word.equalsIgnoreCase("een") || word.equalsIgnoreCase("aan")) {
                    isEn = true;
                    break;
                }
            }
            if(isEn){
                if(answer.equals("en")) enCountYes++;
                else nlCountYes++;
                yesData.add(data);
            }
            else{
                if(answer.equals("en")) enCountNo++;
                else nlCountNo++;
                noData.add(data);
            }
        }
        double totalInYes = enCountYes + nlCountYes;
        double totalInNo = enCountNo + nlCountNo;
        double totalOverall = totalInNo + totalInYes;
        double impurity1 = 1 - Math.pow((enCountYes/totalInYes),2) - Math.pow((nlCountYes/totalInYes),2);
        double impurity2 = 1 - Math.pow((enCountNo/totalInNo),2) - Math.pow((nlCountNo/totalInNo),2);
        impurity = ((totalInYes/totalOverall)*impurity1) + ((totalInNo/totalOverall)*impurity2);
        String yesLabel=null, noLabel=null;

        if(totalInYes==enCountYes) yesLabel = "en";
        else if(totalInYes==nlCountYes) yesLabel="nl";

        if(totalInNo==enCountNo) noLabel="en";
        else if(totalInNo==nlCountNo) noLabel="nl";

        String maxYes = enCountYes > nlCountYes? "en": "nl";
        String maxNo = enCountNo > nlCountNo ? "en" : "nl";

        if(totalInNo==0 || totalInYes==0)
            impurity=0;

        return new TreeNode(yesData, noData,"E",impurity, yesLabel==null? null:new TreeNode(yesLabel), noLabel==null? null:new TreeNode(noLabel), maxYes, maxNo);
    }
    private TreeNode lettersRepeatedMoreThanOnce(ArrayList<String[]> trainingData) {
        double impurity;
        int nlCountYes=0;
        int enCountYes=0;
        int nlCountNo=0;
        int enCountNo=0;
        ArrayList<String[]> yesData = new ArrayList<>();
        ArrayList<String[]> noData = new ArrayList<>();
        for(String[] data: trainingData){
            int isWordRepeat=0;
            String sample = data[1];
            String answer = data[0];
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
            if(isWordRepeat<3){
                if(answer.equals("en")) enCountYes++;
                else nlCountYes++;
                yesData.add(data);
            }
            else{
                if(answer.equals("en")) enCountNo++;
                else nlCountNo++;
                noData.add(data);
            }
        }
        double totalInYes = enCountYes + nlCountYes;
        double totalInNo = enCountNo + nlCountNo;
        double totalOverall = totalInNo + totalInYes;
        double impurity1 = 1 - Math.pow((enCountYes/totalInYes),2) - Math.pow((nlCountYes/totalInYes),2);
        double impurity2 = 1 - Math.pow((enCountNo/totalInNo),2) - Math.pow((nlCountNo/totalInNo),2);
        impurity = ((totalInYes/totalOverall)*impurity1) + ((totalInNo/totalOverall)*impurity2);
        String yesLabel=null, noLabel=null;

        if(totalInYes==enCountYes) yesLabel = "en";
        else if(totalInYes==nlCountYes) yesLabel="nl";

        if(totalInNo==enCountNo) noLabel="en";
        else if(totalInNo==nlCountNo) noLabel="nl";

        String maxYes = enCountYes > nlCountYes? "en": "nl";
        String maxNo = enCountNo > nlCountNo ? "en" : "nl";

        if(totalInNo==0 || totalInYes==0)
            impurity=0;

        return new TreeNode(yesData, noData,"D", impurity, yesLabel==null? null: new TreeNode(yesLabel), noLabel==null? null:new TreeNode(noLabel), maxYes, maxNo);

    }
    private TreeNode lengthOfWordGreaterThan12(ArrayList<String[]> trainingData) {
        double impurity;
        int nlCountYes=0;
        int enCountYes=0;
        int nlCountNo=0;
        int enCountNo=0;
        ArrayList<String[]> yesData = new ArrayList<>();
        ArrayList<String[]> noData = new ArrayList<>();
        for(String[] data: trainingData){
            boolean isDe=false;
            String sample = data[1];
            String answer = data[0];
            for(String word: sample.split(" ")){
                if(word.length()>=10) {
                    isDe = true;
                    break;
                }
            }
            if(isDe){
                if(answer.equals("en")) enCountYes++;
                else nlCountYes++;
                yesData.add(data);
            }
            else{
                if(answer.equals("en")) enCountNo++;
                else nlCountNo++;
                noData.add(data);
            }
        }
        double totalInYes = enCountYes + nlCountYes;
        double totalInNo = enCountNo + nlCountNo;
        double totalOverall = totalInNo + totalInYes;
        double impurity1 = 1 - Math.pow((enCountYes/totalInYes),2) - Math.pow((nlCountYes/totalInYes),2);
        double impurity2 = 1 - Math.pow((enCountNo/totalInNo),2) - Math.pow((nlCountNo/totalInNo),2);
        impurity = ((totalInYes/totalOverall)*impurity1) + ((totalInNo/totalOverall)*impurity2);
        String yesLabel=null, noLabel=null;

        if(totalInYes==enCountYes) yesLabel = "en";
        else if(totalInYes==nlCountYes) yesLabel="nl";

        if(totalInNo==enCountNo) noLabel="en";
        else if(totalInNo==nlCountNo) noLabel="nl";

        String maxYes = enCountYes > nlCountYes? "en": "nl";
        String maxNo = enCountNo > nlCountNo ? "en" : "nl";

        if(totalInNo==0 || totalInYes==0)
            impurity=0;

        return new TreeNode(yesData, noData,"C", impurity, yesLabel==null? null: new TreeNode(yesLabel), noLabel==null? null:new TreeNode(noLabel), maxYes, maxNo);
    }
    private TreeNode hasDe(ArrayList<String[]> trainingData) {
        double impurity;
        int nlCountYes=0;
        int enCountYes=0;
        int nlCountNo=0;
        int enCountNo=0;
        ArrayList<String[]> yesData = new ArrayList<>();
        ArrayList<String[]> noData = new ArrayList<>();
        for(String[] data: trainingData){
            boolean isDe=false;
            String sample = data[1];
            String answer = data[0];
            for(String word: sample.split(" ")){
                if(word.equalsIgnoreCase("de")) {
                    isDe = true;
                    break;
                }
            }
            if(!isDe){
                if(answer.equals("en")) enCountYes++;
                else nlCountYes++;
                yesData.add(data);
            }
            else{
                if(answer.equals("en")) enCountNo++;
                else nlCountNo++;
                noData.add(data);
            }
        }
        double totalInYes = enCountYes + nlCountYes;
        double totalInNo = enCountNo + nlCountNo;
        double totalOverall = totalInNo + totalInYes;
        double impurity1 = 1 - Math.pow((enCountYes/totalInYes),2) - Math.pow((nlCountYes/totalInYes),2);
        double impurity2 = 1 - Math.pow((enCountNo/totalInNo),2) - Math.pow((nlCountNo/totalInNo),2);
        impurity = ((totalInYes/totalOverall)*impurity1) + ((totalInNo/totalOverall)*impurity2);
        String yesLabel=null, noLabel=null;

        if(totalInYes==enCountYes) yesLabel = "en";
        else if(totalInYes==nlCountYes) yesLabel="nl";

        if(totalInNo==enCountNo) noLabel="en";
        else if(totalInNo==nlCountNo) noLabel="nl";

        String maxYes = enCountYes > nlCountYes? "en": "nl";
        String maxNo = enCountNo > nlCountNo ? "en" : "nl";

        if(totalInNo==0 || totalInYes==0)
            impurity=0;

        return new TreeNode(yesData, noData,"B",impurity, yesLabel==null? null:new TreeNode(yesLabel), noLabel==null? null:new TreeNode(noLabel), maxYes, maxNo);
    }
    private TreeNode hasQ(ArrayList<String[]> trainingData) {
        double impurity;
        int nlCountYes=0;
        int enCountYes=0;
        int nlCountNo=0;
        int enCountNo=0;
        boolean isQ=false;
        ArrayList<String[]> yesData = new ArrayList<>();
        ArrayList<String[]> noData = new ArrayList<>();
        for(String[] data: trainingData){
            String sample = data[1];
            String answer = data[0];
            for(String word: sample.split(" ")){
                if(word.contains("Y") || word.contains("y")) {
                    isQ = true;
                    break;
                }
            }
            if(!isQ){
                if(answer.equals("en")) enCountYes++;
                else nlCountYes++;
                yesData.add(data);
            }
            else{
                if(answer.equals("en")) enCountNo++;
                else nlCountNo++;
                noData.add(data);
            }
        }
        double totalInYes = enCountYes + nlCountYes;
        double totalInNo = enCountNo + nlCountNo;
        double totalOverall = totalInNo + totalInYes;
        double impurity1 = 1 - Math.pow((enCountYes/totalInYes),2) - Math.pow((nlCountYes/totalInYes),2);
        double impurity2 = 1 - Math.pow((enCountNo/totalInNo),2) - Math.pow((nlCountNo/totalInNo),2);
        impurity = ((totalInYes/totalOverall)*impurity1) + ((totalInNo/totalOverall)*impurity2);
        String yesLabel=null, noLabel=null;
        if(totalInYes==enCountYes) yesLabel = "en";
        else if(totalInYes==nlCountYes) yesLabel="nl";

        if(totalInNo==enCountNo) noLabel="en";
        else if(totalInNo==nlCountNo) noLabel="nl";

        String maxYes = enCountYes > nlCountYes? "en": "nl";
        String maxNo = enCountNo > nlCountNo ? "en" : "nl";

        if(totalInNo==0 || totalInYes==0)
            impurity=0;

        return new TreeNode(yesData, noData,"A", impurity, yesLabel==null? null:new TreeNode(yesLabel), noLabel==null? null:new TreeNode(noLabel), maxYes, maxNo);

    }
    public TreeNode chooseFunction(String featureId, ArrayList<String[]> trainingData){
        switch(featureId){
            case "A":
                return hasQ(trainingData);
            case "B":
                return hasDe(trainingData);
            case "C":
                return lengthOfWordGreaterThan12(trainingData);
            case "D":
                return lettersRepeatedMoreThanOnce(trainingData);
            case "E":
                return hasEnAn(trainingData);
            case "F":
                return hasThe(trainingData);
        }
        return null;
    }
}
