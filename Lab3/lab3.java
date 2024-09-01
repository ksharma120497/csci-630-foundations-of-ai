

/*
    This is the entry point of the code
 */

public class lab3 {
    public static void main(String args[]){
        if(args[0].equals("train")){
            Train train = new Train(args[1], args[2], args[3]);
             train.trainingModel();
        }
        else{
            Predict predict = new Predict(args[1], args[2]);
            predict.showResults();
        }
    }
}
