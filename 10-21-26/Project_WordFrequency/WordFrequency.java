import java.util.*;

public class WordFrequency {
    public static void main(String[] args) {
        String text = "The harsh, snow-swept province of Skyrim is locked in a bitter civil war following the assassination of the High King. " + 
        "Yet, an even greater threat emerges from the shadows of legend as the ancient dragon Alduin returns to consume the world. " + 
        "The only hope for survival rests on the shoulders of the Dovahkiin, a prophecy foretold hero born with the body of a mortal and the soul of a dragon. " +
        "By mastering the power of the Thu'um, this legendary warrior must traverse treacherous mountains and ancient ruins to halt the impending apocalypse.";

        String cleanText = text.toLowerCase().replaceAll("[^a-z ]", "");

        int uses = 0;
        String mostFrequentWord = "";

        Map<String, Integer> counts = new TreeMap<>();
        for(String word : cleanText.split(" ")){
            int current = counts.getOrDefault(word, 0);
            counts.put(word, current + 1);
        }

        for(Map.Entry<String, Integer>e : counts.entrySet()){
            System.out.println(e.getKey() + ": " + "*".repeat(e.getValue()) + " (" + e.getValue() + ")");
            if(e.getValue() > uses){
                uses = e.getValue();
                mostFrequentWord = e.getKey();
            }
        }

        System.out.println("Most frequent word: '" + mostFrequentWord + "' (Appeared " + uses + " times)");

        System.out.println("Unique word count " + counts.size());

    }
}
