package edu.cwi.swdv110.music;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Every method is a stream pipeline – no for or while loops in this class!
 */
public final class MusicStats {

    private MusicStats() {
    }
    /**
     * Newest date first; same date -> artist A–Z; same artist -> song A–Z. TODO
     * Part 3: build it with
     * Comparator.comparing(...).reversed().thenComparing(...)
     */
    public static final Comparator<Play> NEWEST_FIRST = Comparator.comparing((Play play) -> play.date()).reversed().thenComparing(play -> play.artist()).thenComparing(play -> play.song());

    /**
     * Total seconds of the plays matching `filter`, divided by 60 (whole
     * minutes).
     */
    public static long totalMinutes(List<Play> plays, Predicate<Play> filter) {
        // TODO – filter, mapToInt, sum
        return plays.stream().filter(filter).mapToInt(Play::seconds).sum() / 60;
    }

    /**
     * genre -> number of plays, sorted by genre.
     */
    public static Map<String, Long> playsPerGenre(List<Play> plays) {
        // TODO – groupingBy(classifier, TreeMap::new, counting())
        return plays.stream().collect(Collectors.groupingBy(play -> play.genre(), TreeMap::new, Collectors.counting()));

    }

    /**
     * user -> total seconds, sorted by user.
     */
    public static Map<String, Integer> secondsPerUser(List<Play> plays) {
        // TODO – summingInt
        return plays.stream().collect(Collectors.groupingBy(play -> play.user(), TreeMap::new, Collectors.summingInt(play -> play.seconds())));
    }

    /**
     * The n artists with the most plays, most first. Ties: artist name A–Z.
     * Hint: group & count into a Map, then stream its entrySet() and sort with * Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey())
     */
    public static List<String> topArtists(List<Play> plays, int n) {
        // TODO
        Map<String, Long> groupedPlay = plays.stream().collect(Collectors.groupingBy(play -> play.artist(), TreeMap::new, Collectors.counting()));        
        return groupedPlay.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey())).limit(n).map(artist -> artist.getKey()).toList();
    }

    /**
     * The single longest play, or Optional.empty() for an empty list.
     */
    public static Optional<Play> longestPlay(List<Play> plays) {
        // TODO – max(Comparator.comparingInt(...))
        return plays.stream().max(Comparator.comparingInt(Play::seconds));
    }

    /**
     * Distinct song titles by this artist (ignore case), sorted A–Z.
     */
    public static List<String> songsBy(List<Play> plays, String artist) {
        // TODO
        return plays.stream().filter(play -> play.artist().equalsIgnoreCase(artist)).map(play -> play.song()).distinct().sorted().toList();
    }

    /**
     * The n most recent plays, ordered by NEWEST_FIRST.
     */
    public static List<Play> recent(List<Play> plays, int n) {
        // TODO
        return plays.stream().sorted(NEWEST_FIRST).limit(n).toList();
    }

    /**
     * MONDAY..SUNDAY -> number of plays (only days that have plays). Uses Week
     * 3's java.time!
     */
    public static Map<DayOfWeek, Long> playsByDay(List<Play> plays) {
        // TODO
        return plays.stream().collect(Collectors.groupingBy(play -> play.date().getDayOfWeek(), TreeMap::new, Collectors.counting()));
    }

    /**
     * Fraction (0.0–1.0) of this user's plays that were skips. 0.0 if the user
     * has no plays.
     */
    public static double skipRate(List<Play> plays, String user) {
        // TODO – reuse PlayFilters.byUser and PlayFilters.skipped
        double userPlays = plays.stream().filter(PlayFilters.byUser(user)).count();

        if(userPlays <= 0){
            return 0.0;
        }

        double userSkips = plays.stream().filter(PlayFilters.byUser(user).and(PlayFilters.skipped())).count();

        return userSkips / userPlays;
    }

    /**
     * The genre this user played most. Ties go to the genre that comes first
     * A–Z. Optional.empty() if the user has no plays.
     */
    public static Optional<String> topGenre(List<Play> plays, String user) {
        // TODO
        double userPlays = plays.stream().filter(PlayFilters.byUser(user)).count();
        if(userPlays <= 0){
            return Optional.empty();
        }

        Map<String, Long> sortedByGenre = plays.stream().filter(PlayFilters.byUser(user)).collect(Collectors.groupingBy(play -> play.genre(), TreeMap::new, Collectors.counting()));
        Optional<Map.Entry<String, Long>> maxPlays = sortedByGenre.entrySet().stream().max(Comparator.<Map.Entry<String, Long>, Long>comparing(Map.Entry::getValue).thenComparing(Map.Entry.<String, Long>comparingByKey().reversed()));

        return maxPlays.map(Map.Entry::getKey);
    }
}
