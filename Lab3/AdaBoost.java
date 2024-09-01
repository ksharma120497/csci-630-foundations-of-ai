

import java.io.Serializable;
import java.util.*;

public class AdaBoost implements Serializable {

    private static final long serialVersionUID = 1L;
    ArrayList<Double> sampleWeight;
    ArrayList<TreeNode> stumpList;
    ArrayList<String[]> trainingData;
    HashMap<String, TreeNode> map = new HashMap<>();

    ADAFeatures adaFeatures;
    public AdaBoost(ArrayList<String[]> trainingData){
        this.trainingData = trainingData;
        this.sampleWeight = new ArrayList<>();
        this.stumpList = new ArrayList<>();
        this.adaFeatures = new ADAFeatures();
        this.map.put("A", null);
        this.map.put("B", null);
        this.map.put("C", null);
        this.map.put("D", null);
        this.map.put("E", null);
        this.map.put("F", null);
    }
    public ArrayList<TreeNode> getStumpList() {
        return stumpList;
    }
    public ArrayList<TreeNode> createStumps(){
        for(int l=0; l<6; l++){
            for(int i=0; i<this.trainingData.size(); i++){
                sampleWeight.add( (1/(double)this.trainingData.size()));
            }
            LinkedHashMap<String, TreeNode> featureIdSortedByImpurity = calculateEntropy(this.trainingData);
            String key= (String) featureIdSortedByImpurity.keySet().toArray()[0];
            TreeNode stump = calculateAmountOfSay(featureIdSortedByImpurity.get(key));
            stumpList.add(stump);
            calculateNewSampleWeights(stump);
            ArrayList<String[]> newTrainingData= new ArrayList<>();
            double[] prefixSum = new double[this.trainingData.size()];
            double sum=0;
            for(int i=0; i<this.trainingData.size();i++){
                sum+=this.sampleWeight.get(i);
                prefixSum[i]=sum;
            }
            for(int i=0; i<this.trainingData.size(); i++){
                double random = Math.random();
                if(random>0 && random<=prefixSum[0])
                    newTrainingData.add(this.trainingData.get(0));
                for(int j=1; j<prefixSum.length; j++){
                    if(random>prefixSum[j-1] && random<=prefixSum[j])
                        newTrainingData.add(this.trainingData.get(j));
                }
            }
            this.trainingData = newTrainingData;
        }
        return stumpList;
    }
    private void calculateNewSampleWeights(TreeNode stump) {
        double totalSampleWeight=0;
        ArrayList<Double> newSampleWeight=new ArrayList<>();
        for(int i=0; i<this.trainingData.size(); i++){
            if(stump.errorSampleWeights.contains(i)){
                double errorSamples=sampleWeight.get(i)*Math.exp(stump.amountOfSay);
                totalSampleWeight+=errorSamples;
                newSampleWeight.add(i,errorSamples);
            }
            else{
                double nonErrorSamples=sampleWeight.get(i)*Math.exp(-1*stump.amountOfSay);
                totalSampleWeight+=nonErrorSamples;
                newSampleWeight.add(i,nonErrorSamples);
            }
        }

        for(int i=0; i<this.trainingData.size(); i++){
            newSampleWeight.add(i, newSampleWeight.get(i)/totalSampleWeight);
        }
        this.sampleWeight = newSampleWeight;
    }
    public TreeNode calculateAmountOfSay(TreeNode stump){
        double totalError=0.0;
        for(Integer errorWeight: stump.errorSampleWeights){
            totalError+=this.sampleWeight.get(errorWeight);
        }
        double amountOfSay = 0.5 * Math.log((1 - totalError)/totalError);
        stump.setAmountOfSay(amountOfSay);
        return stump;
    }
    LinkedHashMap<String, TreeNode> calculateEntropy(ArrayList<String[]> trainingData){

        for(String key: map.keySet()){
            map.put(key, adaFeatures.chooseFunction(key,trainingData));
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

    public void displayStumps(){
        for(TreeNode treeNode: stumpList)
            System.out.println(treeNode);
    }


}
