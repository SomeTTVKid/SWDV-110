package edu.cwi.swdv110.music;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Finish `App` and run `mvn -q compile exec:java`.

// If you're reading this comment
// The formatting is 100% broken for my personal data 
// The personal data also sadly does not include all required fields 
// I requested my data from Spotify but it will not get to me in time
// So I used a third party application to grab similar data of mine from Spotify
// And used AI to convert the needed fields to create the CSV

public class App {

    public static void main(String[] args) throws IOException {
        // TODO Part 4a: ONE stream pipeline: read all lines of plays.csv, skip the header,
        //   drop blank lines, map with Play::parse, and collect into an ArrayList
        //   (Collectors.toCollection(ArrayList::new) – clean() needs a list it can modify).
        List<Play> plays = new ArrayList<>();
        List<Play> zaynePlaylist = new ArrayList<>();

        List<String> lines = Files.readAllLines(Path.of("plays.csv"));
        plays = lines.stream().skip(1).filter(line -> !line.isBlank()).map(Play::parse).collect(Collectors.toCollection(ArrayList::new));

        // TODO Part 4b: clean the list and print "Loaded N plays (M bad rows removed)"
        int removed = PlayCleaner.clean(plays);

        System.out.println("Loaded " + plays.size() + " plays " + "(" + removed + " bad rows removed)");

        // TODO Part 4c: create two PlayFormatter lambdas:
        //   compact  -> "Afterglow – Lumen"
        PlayFormatter compact = play -> play.song() + " - " + play.artist();
        //   detailed -> "2026-10-11  maya    Afterglow          Lumen              3:56"
        PlayFormatter detailed = play -> String.format("%s  %-8s%-19s%-19s%d:%02d", play.date(), play.user(), play.song(), play.artist(), play.seconds() / 60, play.seconds() % 60);

        // TODO Part 4d: print the reports shown in the instructions, using Map.forEach((k, v) -> ...)
        //   and Optional.ifPresent(...)

        // Minutes per user
        System.out.println("\nMinutes per user:");
        MusicStats.secondsPerUser(plays).forEach((user, seconds) -> {
            System.out.printf(" %-10s%2d min%n", user, seconds / 60);
        });

        // Plays by genre
        System.out.println("\nPlays per genre:");
        MusicStats.playsPerGenre(plays).forEach((genre, playCount) -> {
            System.out.printf(" %-12s%s%n", genre.toUpperCase(), "#".repeat(playCount.intValue()));
        });

        // Plays by day
        System.out.println("\nPlays by day:");
        MusicStats.playsByDay(plays).forEach((day, playCount) -> {
            System.out.printf(" %-11s%d%n", day, playCount);
        }); 

        // Top 3 artists 
        System.out.println("\nTop 3 artists: " + MusicStats.topArtists(plays, 3));

        // Longest play
        MusicStats.longestPlay(plays).ifPresent(play ->
            System.out.println("Longest play: " + compact.format(play))
        );

        // Most recent
        System.out.println("\nMost recent:");
        MusicStats.recent(plays, 5).forEach(play -> {
            System.out.printf("%s  %-8s%-19s%-19s%d:%02d%n", play.date(), play.user(), play.song(), play.artist(), play.seconds() / 60, play.seconds() % 60);
        });

        // TODO Part 4e: loop – ask for a user name (or 'quit') and print their summary
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while(running){
            System.out.println("\nEnter 'Zayne' for my personal playlist");
            System.out.print("\nUser to summarize (or 'quit'): ");
            String userInput = sc.next();
            
            // Quit the program
            if(userInput.equalsIgnoreCase("quit")){
                System.out.println("\nHave a nice day.");
                running = false;
                continue;
            }

            if(userInput.equalsIgnoreCase("zayne")){
                List<String> personalLines = Files.readAllLines(Path.of("spotify_plays.csv"));
                zaynePlaylist = personalLines.stream().skip(1).filter(line -> !line.isBlank()).map(Play::parse).collect(Collectors.toCollection(ArrayList::new));

                int customRemoved = PlayCleaner.clean(zaynePlaylist);

                System.out.println("\nLoaded " + zaynePlaylist.size() + " plays " + "(" + customRemoved + " bad rows removed)");

                // Minutes per user
                System.out.println("\nMinutes per user:");
                MusicStats.secondsPerUser(zaynePlaylist).forEach((user, seconds) -> {
                    System.out.printf(" %-10s%2d min%n", user, seconds / 60);
                });

                // Plays by genre
                System.out.println("\nPlays per genre:");
                MusicStats.playsPerGenre(zaynePlaylist).forEach((genre, playCount) -> {
                    System.out.printf(" %-12s%s%n", genre.toUpperCase(), "#".repeat(playCount.intValue()));
                });

                // Plays by day
                System.out.println("\nPlays by day:");
                MusicStats.playsByDay(zaynePlaylist).forEach((day, playCount) -> {
                    System.out.printf(" %-11s%d%n", day, playCount);
                }); 

                // Top 3 artists 
                System.out.println("\nTop 3 artists: " + MusicStats.topArtists(zaynePlaylist, 3));

                // Longest play
                MusicStats.longestPlay(zaynePlaylist).ifPresent(play ->
                    System.out.println("Longest play: " + compact.format(play))
                );

                // Most recent
                System.out.println("\nMost recent:");
                MusicStats.recent(zaynePlaylist, 5).forEach(play -> {
                    System.out.printf("%s  %-8s%-19s%-19s%d:%02d%n", play.date(), play.user(), play.song(), play.artist(), play.seconds() / 60, play.seconds() % 60);
                });

            }

            // If a user exists
            if(plays.stream().anyMatch(play -> play.user().equalsIgnoreCase(userInput))){
                long playCount = plays.stream().filter(PlayFilters.byUser(userInput)).count();

                String topGenre = MusicStats.topGenre(plays, userInput).orElse("none");

                long skipPercent = Math.round(MusicStats.skipRate(plays, userInput) * 100);

                // General user stats
                System.out.printf("%s: %d plays, %d min, top genre %s, skip rate %d%%%n",
                    userInput,
                    playCount,
                    MusicStats.totalMinutes(plays, PlayFilters.byUser(userInput)),
                    topGenre,
                    skipPercent);

                // Genre minutes for top listened genre
                long genreMinutes = MusicStats.totalMinutes(
                    plays,
                    PlayFilters.byUser(userInput).and(PlayFilters.byGenre(topGenre))
                );
                System.out.printf("  %s minutes: %d%n", topGenre, genreMinutes);

                // Holy chaining...
                String fullListens = plays.stream()
                    .filter(PlayFilters.byUser(userInput))
                    .filter(PlayFilters.skipped().negate())
                    .map(compact::format)
                    .distinct()
                    .sorted()
                    .collect(Collectors.joining("; "));

                System.out.println("  Full listens:  " + fullListens);               
            }

        }
    }
}