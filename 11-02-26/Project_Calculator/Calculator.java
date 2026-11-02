import java.util.Map;
import java.util.Scanner;

@FunctionalInterface 
interface MathOperation{
    float apply(float a, float b);
}

public class Calculator {
    public static void main(String[] args) {
        Map<String, MathOperation> operations = Map.of(
            "+", (a, b) -> a + b,
            "-", (a, b) -> a - b,
            "*", (a, b) -> a * b,
            "/", (a, b) -> a / b
        );
        boolean running = true;

        Scanner userInput = new Scanner(System.in);

        System.out.println("Welcome to the lambda calculator application.");

        while(running){
            System.out.print("Please enter your equation (or quit): ");

            if(userInput.next().equalsIgnoreCase("quit")){
                running = false;
                continue;
            }
            
            String[] input = userInput.nextLine().split(" ");

            if(input.length == 3){
                float num1 = Float.parseFloat(input[0]);
                String operator = input[1];
                float num2 = Float.parseFloat(input[2]);

                if(operations.containsKey(operator)){
                    MathOperation operation = operations.get(operator);

                    float result = operation.apply(num1, num2);

                    System.out.println(num1 + " " + operator + " " + num2 + " = " + result);
                }else{
                    System.out.println("Unknown operator " + operator);
                }
            }else{
                System.out.println("Invalid format. Please use 'number operator number'");
            }
        }
        userInput.close();
    }
}
