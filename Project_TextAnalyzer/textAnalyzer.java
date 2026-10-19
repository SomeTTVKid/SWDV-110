import java.util.*;

// TODO: Missing longest word, and titlecase

public class textAnalyzer {
    public static void main(String[] args) {
        int wordCount = 0;
        int letterCount = 0;
        int digitCount = 0;
        int vowelCount = 0;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a sentence.");

        String sentence = scanner.nextLine();

        StringBuilder clean = new StringBuilder(sentence);

        // Get count of words by splitting on empty space (\s)
        String[] words = sentence.toLowerCase().split("\\s+");
        for(String word : words){
            wordCount++;

            for(int i = 0; i < word.length(); i++){
                char ch = word.charAt(i);

                if (Character.isLetter(ch)) {
                    letterCount++;
                }

                if("aeiou".indexOf(ch) != -1){
                    vowelCount++;
                }else if(Character.isDigit(ch)){
                    digitCount++;
                }
            }


        }

        clean.reverse();

        String reversed = clean.toString();

        System.out.println(reversed);

        System.out.println("=".repeat(40));

        System.out.println("Word Count: " + wordCount);
        System.out.println("Digit Count: " + digitCount);
        System.out.println("Vowel Count: " + vowelCount);
        System.out.println("Letter Count: " + letterCount);

        System.out.println("=".repeat(40));

    }
}
