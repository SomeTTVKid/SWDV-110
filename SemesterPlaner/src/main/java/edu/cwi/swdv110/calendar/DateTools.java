package edu.cwi.swdv110.calendar;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/** Static date helpers used by Semester and App. */
public final class DateTools {
// TODO Part 1: a formatter for "Mon, Jan 11" (pattern letters: EEE, MMM, d)
// TODO Part 2: a formatter for "Mon, Jan 11 at 6:00 PM MST"
// (hint: literal text goes in single quotes: 'at')
    static DateTimeFormatter shortFormat = DateTimeFormatter.ofPattern("EEE, MMM d");
    static DateTimeFormatter longFormat = DateTimeFormatter.ofPattern("EEE, MMM d 'at' h:mm a z");



// TODO Part 2: a List<DateTimeFormatter> of the three accepted input formats:
// 2027-03-15 3/15/2027 Mar 15, 2027
// Always pass Locale.US to ofPattern so "Mon"/"PM" don't change on other computers.
// For the INPUT formatters use "uuuu" (not "yyyy") for the year and add
// .withResolverStyle(ResolverStyle.STRICT) – see the Part 2 notes for why.
    static List<DateTimeFormatter> formatList = List.of(
        DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.US).withResolverStyle(ResolverStyle.STRICT),
        DateTimeFormatter.ofPattern("M/d/uuuu", Locale.US).withResolverStyle(ResolverStyle.STRICT),
        DateTimeFormatter.ofPattern("MMM d, uuuu", Locale.US).withResolverStyle(ResolverStyle.STRICT)
    );


    private DateTools() { }

    // ---------------- Part 1 ----------------

    /** The nth given weekday of a month, e.g. (2027, JANUARY, 3, MONDAY) -> 2027-01-18 (MLK Day). */
    public static LocalDate nthWeekday(int year, Month month, int n, DayOfWeek day) {
        LocalDate firstOfMonth = LocalDate.of(year, month, 1);

        // TODO – TemporalAdjusters.dayOfWeekInMonth
        // This is looking for N passed in, and the DAY passed in, inside of that month
        // So we are looking for the 3 weekday of january that is monday?
        LocalDate nthDate = firstOfMonth.with(TemporalAdjusters.dayOfWeekInMonth(n, day));

        return nthDate;
    }

    /** The last given weekday of a month, e.g. (2027, MAY, MONDAY) -> 2027-05-31 (Memorial Day). */
    public static LocalDate lastWeekday(int year, Month month, DayOfWeek day) {
        LocalDate firstOfMonth = LocalDate.of(year, month, 1);
        // TODO – TemporalAdjusters.lastInMonth
        LocalDate lastDay = firstOfMonth.with(TemporalAdjusters.lastInMonth(day));
    
        return lastDay;
    }

    /** "Mon, Jan 11" */
    public static String formatShort(LocalDate date) {
        // TODO
        String formattedDate = shortFormat.format(date);

        return formattedDate;
    }

    /** True for Saturday and Sunday. */
    public static boolean isWeekend(LocalDate date) {
        // TODO
        return date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    // ---------------- Part 2 ----------------

    /**
    * Accepts "2027-03-15", "3/15/2027" or "Mar 15, 2027" (surrounding spaces allowed).
    * Anything else -> IllegalArgumentException("Unrecognized date: " + text).
    * Hint: try each formatter in turn and catch DateTimeParseException.
    */
    public static LocalDate parseFlexible(String text) {
    // TODO
        for(DateTimeFormatter format : formatList){
            try {
                LocalDate date = LocalDate.parse(text.trim(), format);
                return date;
            } catch (DateTimeParseException e) {
                continue;
            }
        }

        throw new IllegalArgumentException("Unrecognized date: " + text);
    }

    /**
    * Counts forward `days` school days from `start` (start itself is not counted).
    * Weekends and dates in `holidays` are skipped.
    * Fri 2027-01-08 + 1 school day -> Mon 2027-01-11
    */
    public static LocalDate addSchoolDays(LocalDate start, int days, Set<LocalDate> holidays) {
    // TODO
        LocalDate date = start;

        for(int i = 0; i < days;){
            date = date.plusDays(1);

            if(isWeekend(date) || holidays.contains(date)){
                continue;
            }

            i++;
        }
        
        return date;
    }

    /**
    * Late policy: 10% off for each 24 hours (or part of 24 hours) late, maximum 50%.
    * on time or early -> 0
    * 1 minute late -> 10
    * exactly 24 hours late -> 10
    * 24 hours + 1 minute -> 20
    * a week late -> 50
    * Hint: Duration.between(...).toMinutes()
    */
    public static int latePenaltyPercent(LocalDateTime due, LocalDateTime submitted) {
    // TODO
        long timeElapased = Duration.between(due, submitted).toMinutes();
        int minutesInDay = 1440;
        
        // Returned on time or early
        if(timeElapased <= 0){
            return 0;
        }

        long daysPassed = timeElapased / minutesInDay;

        if(timeElapased % minutesInDay > 0){
            daysPassed++;
        }

        long penalty = daysPassed * 10;

        // Cap the penalty
        if(penalty > 50){
            penalty = 50;
        }
        
        return (int)penalty;
    }

    /** Converts to another zone and formats: "Tue, Jan 12 at 10:00 AM JST" */
    public static String inZone(ZonedDateTime when, ZoneId zone) {
    // TODO – withZoneSameInstant
        ZonedDateTime timeThere = when.withZoneSameInstant(zone);
        String formattedTime = longFormat.format(timeThere);
        return formattedTime;
    }
}
