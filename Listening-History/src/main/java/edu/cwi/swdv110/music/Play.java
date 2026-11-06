package edu.cwi.swdv110.music;

import java.time.LocalDate;

/**
 * One time a user played a song.
 */
public record Play(String user, String song, String artist, String genre, LocalDate date, int seconds) {

    /**
     * Parses "maya,Paper Satellites,Static Bloom,Indie,2026-09-28,161". Trim
     * every field. Wrong number of fields -> IllegalArgumentException. (An
     * empty song title is allowed here – PlayCleaner removes it later.)
     */
    public static Play parse(String csvLine) {
        // TODO Part 1
        String[] parts = csvLine.trim().split(",");
        if(parts.length < 6){
            throw new IllegalArgumentException();
        }

        return new Play(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(), LocalDate.parse(parts[4]), Integer.parseInt(parts[5].trim()));
    }
}
