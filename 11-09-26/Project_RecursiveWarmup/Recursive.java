public class Recursive {
    public static void main(String[] args){
        // This is messy but matches the warmup cases
        int number = 4096;
        System.out.println("Sum of digits for " + number + " = " + sumDigits(number));

        System.out.println("2 raised to the power of 10 = " + power(2, 10));

        System.out.print("The number 10 in binary is: ");
        printBinary(10);

        System.out.println("\nThe string banana has: " + countChar("banana", 'a') + "'a' characters.");
    }

    private static int sumDigits(int digits){
        if(digits < 10){
            return digits;
        }

        return (digits % 10) + sumDigits(digits / 10);
    }

    private static int power(int number, int exponent){
        if(exponent == 0){
            return 1;
        }

        return number * power(number, exponent - 1);
    }

    private static int countChar(String string, char characterToCount){
        if(string.isEmpty()){
            return 0;
        }

        int matchFound = string.charAt(0) == characterToCount ? 1 : 0;

        return matchFound + countChar(string.substring(1), characterToCount);

    }

    private static void printBinary(int number){
        // Probably could use StringBuilder here and return the completed string
        if(number == 0){
            return;
        }
        
        printBinary(number / 2);

        System.out.print(number % 2);
    }
}
