import java.io.*;
import java.util.*;

class ReviewProject{
    public void main(){
        // Will have to return double anyways, might as well store everything in a double
        double firstNumber;
        double secondNumber;

        // Prompt user input here 
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your first number.");
        try {
            firstNumber = Double.parseDouble(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Error in converting user input to double for first number.\n" + e);
            main();
            return;
        }
        
        System.out.println("Enter your second number.");
        try {
            secondNumber = Double.parseDouble(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Error in converting user input to double for second number.\n" + e);
            main();
            return;
        }

        // Pesky dividing edgecase
        if(firstNumber == 0.0 || secondNumber == 0.0){
            System.out.println("You cannot divide by zero, please choose a different number.");
            main();
            return;
        }

        arithmeticOperations(firstNumber, secondNumber);

        read();

    }

    private static void arithmeticOperations(double firstNum, double secondNum){
        double sum = firstNum + secondNum;
        double difference = firstNum - secondNum;
        double quotient = firstNum / secondNum;
        double product = firstNum * secondNum;

        ArrayList<Double> transactions = new ArrayList<>();
        transactions.add(sum);
        transactions.add(difference);
        transactions.add(quotient);
        transactions.add(product);

        save(firstNum, secondNum, transactions);
    }

    private static void save(double firstNum, double secondNum, ArrayList<Double> transactions){
        String[] operations = {"+", "-", "/", "*"};

        // Write to save file
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("transactions.txt", false)));
            // Iterating over our arraylist and operations array to get custom formatting 
            for(int i = 0; i < transactions.size(); i++){
                out.print(firstNum + " " + operations[i] + " "+ secondNum + " = " + transactions.get(i) + "\n");
            }
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void read(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("transactions.txt"));
            String line = in.readLine();

            while(line != null){
                System.out.println(line);
                line = in.readLine();
            }
        } catch (Exception e) {
            System.out.println(e);
        }  
    }

}