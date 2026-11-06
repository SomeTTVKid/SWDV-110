package edu.cwi.swdv110.music;

import java.util.List;
import java.util.function.UnaryOperator;

/**
 * Fixes messy rows IN PLACE using the List methods that take lambdas.
 */
public final class PlayCleaner {

    private PlayCleaner() {
    }

    /**
     * " hip-HOP " -> "Hip-Hop", "ELECTRONIC" -> "Electronic" (capitalize after
     * '-' and ' ')
     */
    public static String titleCase(String text) {
        // TODO Part 1 – you wrote one of these in Week 1!
        StringBuilder clean = new StringBuilder();

        boolean capitalize = true;
        
        for(char ch : text.trim().toCharArray()){
            if(ch == ' ' || ch == '-'){
                clean.append(ch);
                capitalize = true;
            }else if(capitalize){
                clean.append(Character.toUpperCase(ch));
                capitalize = false;
            }else{
                clean.append(Character.toLowerCase(ch));
            }
        }

        return clean.toString();
    }

    /**
     * 1. removeIf: delete plays with seconds <= 0 or a blank song title. 
     * 2. replaceAll: records are immutable, so replace each Play with a NEW Play
     * whose user is lowercase and whose genre is title-cased. Write the lambda
     * as a UnaryOperator<Play> variable first, then pass it to replaceAll.
     * Returns how many plays were removed.
     */
    public static int clean(List<Play> plays) {
        int startLength = plays.size();
        plays.removeIf(play -> play.seconds() <= 0 || play.song().isBlank());
        UnaryOperator<Play> cleanPlayData = play -> new Play(
            play.user().toLowerCase(),
            play.song(),
            play.artist(),
            titleCase(play.genre()),
            play.date(),
            play.seconds()
        );
        plays.replaceAll(cleanPlayData);

        return startLength - plays.size();
    }
}
