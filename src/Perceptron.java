import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Perceptron {


    public static double threshold;



    public static Double[] initWeightsVector(ArrayList<String> trainingList){
        Double[] weightsVector = new Double[trainingList.get(0).split(",").length-1];
        Arrays.fill(weightsVector, 1.0);
        return weightsVector;
    }


    public static ArrayList<String> readFile(String file){
        ArrayList<String> list =  new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                list.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static ArrayList<String> rightAnswers(ArrayList<String> list){
        int indexOfAttribute = list.get(0).split(",").length;
        ArrayList<String> listOfAnswers = new ArrayList<>();
        for(String line : list){
            String answer= line.split(",")[indexOfAttribute-1];
            listOfAnswers.add(answer);
        }
        return  listOfAnswers;
    }

    public static Double learning(ArrayList<String> trainingList, ArrayList<String> trainingRightAnswers, Double[] weightsVector, Double threshold,  double learningParameter){
        ArrayList<String> uniqueAnswers= new ArrayList<>(new HashSet<>(trainingRightAnswers));
        String answer1=uniqueAnswers.get(1);
        String answer2=uniqueAnswers.get(0);
        ArrayList<String> answers=new ArrayList<>();
        for (String line : trainingList) {
            int answer;
            String nominalAnswer;
            String[] atr = line.split(",");
            double net = net(atr, weightsVector);
            if (net > threshold) {
                answer = 1;
                nominalAnswer = answer1;
            } else {
                answer = 0;
                nominalAnswer = answer2;
            }
            answers.add(nominalAnswer);
            if (!(nominalAnswer.equals(atr[atr.length - 1]))) {
                if (answer == 1) {
                    settingWeights(weightsVector, answer, 0, learningParameter, atr);
                    threshold = settingThreshold(threshold, answer, 0, learningParameter);
                } else {
                    settingWeights(weightsVector, answer, 1, learningParameter, atr);
                    threshold = settingThreshold(threshold, answer, 1, learningParameter);
                }
            }
        }
        int j = 0;
        for (int i= 0; i < trainingList.size(); i++) {
            if (answers.get(i).equals(trainingRightAnswers.get(i))) {
                j++;
            }
        }
        Perceptron.threshold=threshold;
        return (double) (j * 100 / trainingList.size());
    }

    public static void testing(ArrayList<String> testList, ArrayList<String> testRightAnswers, Double[] weightsVector, Double threshold){
        ArrayList<String> answers=new ArrayList<>();
        ArrayList<String> uniqueAnswers= new ArrayList<>(new HashSet<>(testRightAnswers));
        String answer1=uniqueAnswers.get(1);
        String answer2=uniqueAnswers.get(0);
        for(String line : testList){
            String nominalAnswer;
            String[] atr=line.split(",");
            double net=net(atr, weightsVector);
            if(net>threshold){
                nominalAnswer=answer1;
            }else{
                nominalAnswer=answer2;
            }
            answers.add(nominalAnswer);
            System.out.println(line+" answer->"+nominalAnswer);
        }
        int j=0;
        for(int i=0; i<testList.size(); i++){
            if(answers.get(i).equals(testRightAnswers.get(i))){
                j++;
            }
        }
        double accuracy=j*100/testList.size();
        System.out.println("Accuracy="+accuracy+"%");
    }

    public static void testingInputVector(ArrayList<String> vector, ArrayList<String> testRightAnswers, Double[] weightsVector, Double threshold){
        ArrayList<String> uniqueAnswers= new ArrayList<>(new HashSet<>(testRightAnswers));
        String answer1=uniqueAnswers.get(1);
        String answer2=uniqueAnswers.get(0);
        for(String line : vector){
            String nominalAnswer;
            String[] atr=line.split(",");
            double net=netForInput(atr, weightsVector);
            if(net>threshold){
                nominalAnswer=answer1;
            }else{
                nominalAnswer=answer2;
            }
            System.out.println(line+" answer->"+nominalAnswer);
        }
    }

    public static double net(String[] atr, Double[] weightsVector){
        double result=0;
        for(int i =0;i< atr.length-1;i++){
            result+=Double.parseDouble(atr[i])*weightsVector[i];
        }
        return result;
    }

    public static double netForInput(String[] atr, Double[] weightsVector){
        double result=0;
        for(int i =0;i< atr.length;i++){
            result+=Double.parseDouble(atr[i])*weightsVector[i];
        }
        return result;
    }


    public static void settingWeights(Double[] weights, int answer, int rightAnswer,  double learningParameter, String[] atr){
        for(int i=0;i< weights.length;i++){
            weights[i]=weights[i]+(rightAnswer-answer)*learningParameter*Double.parseDouble(atr[i]);
        }
    }

    public static Double settingThreshold(Double threshold, int answer, int rightAnswer,  double learningParameter){
        threshold=(threshold+(rightAnswer-answer)*learningParameter);
        return threshold;
    }

}
