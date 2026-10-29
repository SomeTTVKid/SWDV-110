package edu.cwi.swdv110.calendar;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class App {

static final ZoneId CAMPUS = ZoneId.of("America/Boise");

public static void main(String[] args) {
    // Example semester – these dates are for practice, not the official CWI calendar.
    Semester sem = new Semester("SWDV-110 - Spring 2027",
    LocalDate.of(2027, 1, 11), LocalDate.of(2027, 5, 7),
    Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
    LocalTime.of(18, 0), LocalTime.of(20, 30));
    sem.addHoliday(DateTools.nthWeekday(2027, Month.JANUARY, 3, DayOfWeek.MONDAY)); // MLK Day
    sem.addHoliday(DateTools.nthWeekday(2027, Month.FEBRUARY, 3, DayOfWeek.MONDAY)); // Presidents' Day
    sem.addBreak(LocalDate.of(2027, 3, 22), LocalDate.of(2027, 3, 26)); // Spring break

    // TODO Part 4a: print the name, a line of '=' the same length, then every meeting:
    // "Week N" (only on the first meeting of each week) and the formatted date
    
    int previousWeek = 0;
    System.out.println(sem.getName() + "\n" + "=".repeat(sem.getName().length()));
    for(LocalDate date : sem.meetingDates()){
        int week = sem.weekNumber(date);

        if (week != previousWeek) {
            System.out.print("Week " + week + " ");
            previousWeek = week;
        }

        System.out.println(DateTools.formatShort(date));
    }
    System.out.print("\n".repeat(2));
    
    // TODO Part 4b: print days off, number of meetings, total class time as "75h 0m"
    // (toHours, toMinutesPart) and the semester length as "3 months, 26 days"

    System.out.println("Days off: " + sem.getDaysOff().size());
    System.out.println("Meetings: " + sem.meetingDates().size());
    System.out.println("Class time: " + sem.totalClassTime().toHours() + "h " + sem.totalClassTime().toMinutesPart() + "m");
    System.out.println("Length: " + sem.length().toTotalMonths() + " months, " + sem.length().getDays() + " days");

    System.out.print("\n".repeat(2));

    // TODO Part 4c: build a ZonedDateTime for the FIRST meeting at the class start time
    // in CAMPUS, and print it for America/Boise, America/Los_Angeles,
    // America/New_York and Asia/Tokyo using DateTools.inZone

    ZoneId campus = ZoneId.of("America/Boise");

    ZonedDateTime firstMeeting = ZonedDateTime.of(sem.meetingDates().get(0), sem.getClassStart(), campus);

    List<String> zones = List.of("America/Boise", "America/Los_Angeles", "America/New_York", "Asia/Tokyo");

    System.out.println("First class, for online students: ");
    for(String zoneNames : zones){
        ZoneId zone = ZoneId.of(zoneNames);
        System.out.println(zoneNames + " " + DateTools.inZone(firstMeeting, zone));
    }
    System.out.print("\n".repeat(2));

    // Coutdown
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime nextClassStart = null;

    for(LocalDate meeting : sem.meetingDates()){
        LocalDateTime day = LocalDateTime.of(meeting, sem.getClassStart());

        // Check if the day is after today
        // Dont want to include days that have already passed
        if(day.isAfter(now)){
            nextClassStart = day;
            break;
        }
    }

    // If its null, there is nothing coming up
    if(nextClassStart == null){
        System.out.println("No upcoming classes!");

    }else{
        Duration timeRemaining = Duration.between(now, nextClassStart);
        System.out.println("Next class starts in " 
            + timeRemaining.toDays() + " days, "
            + timeRemaining.toHoursPart() + " hours, "
            + timeRemaining.toMinutesPart() + " minutes."
        );

    }


    // TODO Part 4d: loop – read a date (any of the 3 formats) or 'quit'. Print:
    // the next class and its week number, days until the semester ends, and the
    // due date of a lab assigned at that class (5 school days, skipping days off).
    // If there are no more classes say so. Catch IllegalArgumentException for bad input.
    Scanner sc = new Scanner(System.in);
    boolean running = true;

    while(running){
        System.out.print("Enter a date (or 'quit'): ");
        String userInput = sc.nextLine().trim();

        if(userInput.equalsIgnoreCase("quit")){
            System.out.println("Thank you for using the SemesterPlanning Application!");
            // Could just return but would rather use this boolean
            running = false;
            continue;
        }

        try {
            LocalDate date = DateTools.parseFlexible(userInput);
            LocalDate nextDate = sem.nextMeeting(date);

            if(nextDate == null){
                System.out.println("No more classes - enjoy your summer!\n");
                continue;
            }

            LocalDate labDate = DateTools.addSchoolDays(nextDate, 5, sem.getDaysOff());

            System.out.println("Next class: " + DateTools.formatShort(nextDate) + " (week " + sem.weekNumber(nextDate) + ")");
            System.out.println("Days until the semester ends: " + sem.daysLeft(date));
            System.out.println("A lab assigned that day is due " + DateTools.formatShort(labDate));
            System.out.print("\n".repeat(2));

        } catch (IllegalArgumentException e) {
            System.out.println("Unrecognized date: " + userInput + " try 2027-03-15, 3/15/2027 or Mar 15, 2027");
            System.out.print("\n".repeat(2));
        }
    }
    }
}
