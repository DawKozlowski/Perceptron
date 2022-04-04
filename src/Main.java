import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        ArrayList<String> trainingList = Perceptron.readFile(args[0]);
        ArrayList<String> testList = Perceptron.readFile(args[1]);
        double learningParameter=Double.parseDouble(args[2]);
        ArrayList<String> trainingRightAnswers=Perceptron.rightAnswers(trainingList);
        ArrayList<String> testRightAnswers=Perceptron.rightAnswers(testList);
        Double[] weightsVector = Perceptron.initWeightsVector(trainingList);
        Perceptron.threshold = 20.0;
        for(int j=0;j<50;j++) {
            System.out.println("Threshold="+Perceptron.threshold);
            System.out.println("Weights="+ Arrays.toString(weightsVector));
            Double accuracy=Perceptron.learning(trainingList, trainingRightAnswers, weightsVector, Perceptron.threshold, learningParameter);
            System.out.println("Training accuracy="+accuracy+"%");
        }
        System.out.println("TEST");
        System.out.println("Threshold="+Perceptron.threshold);
        System.out.println("Weights="+ Arrays.toString(weightsVector));
        Perceptron.testing(testList, testRightAnswers, weightsVector, Perceptron.threshold);

        while(true) {
            System.out.println("\n Insert vector");
            Scanner sc = new Scanner(System.in);
            String vector = sc.next();
            if(vector.equals("quit")){
                System.exit(1);
            }
            if(vector.split(",").length!=testList.get(1).split(",").length-1){
                throw new IllegalArgumentException("Wrong number of attributes");
            }
            ArrayList<String> vectorArray= new ArrayList<>();
            vectorArray.add(vector);
            Perceptron.testingInputVector(vectorArray, testRightAnswers,weightsVector, Perceptron.threshold);
        }
    }

}
