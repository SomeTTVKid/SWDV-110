import java.time.Duration;
import java.time.Instant;

public class ClassWork {
    public static void main(String[] args) {
        // LocalDate d = LocalDate.of(2026, 1, 31);

        // System.out.printf("%s\n", d.plusMonths(1));
        // System.out.println(d.plusDays(30));
        // System.out.println(d.getDayOfWeek());
        // System.out.println(d.isLeapYear());
        // System.out.println(d);

        // LocalTime blah = LocalTime.of(13, 0, 5);

        // LocalDate today = LocalDate.now();
        // LocalDate start = LocalDate.of(2026, 10, 12);
        // LocalDate end = LocalDate.of(2026, Month.DECEMBER, 12);

        // // LocalDate dueHyphenated = LocalDate.parse("2026-12-31");
        // // System.out.println(dueHyphenated);

        // LocalTime classStart = LocalTime.of(13, 0, 0);
        // LocalTime classEnd = LocalTime.parse("16:30");

        // LocalDateTime lecture = LocalDateTime.of(start, classStart);

        // System.out.println(lecture);

        // LocalDate start = LocalDate.of(2026, 10, 13);
        // LocalDate nextWeek = start.plusWeeks(1);
        // LocalDate lastMonth = start.minusMonths(1);
        // LocalDate firstDay = start.withDayOfMonth(1);
        // LocalDate chained = start.plusYears(1).plusDays(5);

        // LocalTime t = LocalTime.of(16, 35).plusMinutes(30);
        // start.plusDays(1); // Bug/Error? they are immutable so this is useless

        // LocalDate due = LocalDate.of(2026, 12, 4);
        // LocalDate turnedIn = LocalDate.of(2026, 12, 6);

        // if(turnedIn.isAfter(due)){
        //     System.out.println("Late!");
        // }else if(turnedIn.isEqual(due)){
        //     System.out.println("Just in time");
        // }

        // List<LocalDate> dates = new ArrayList<>();
        // dates.add(turnedIn);
        // dates.add(due);
        // Collections.sort(dates); // TODO GRAB FROM SLIDES

        //~~~~~~~~~~~~~~~~~~~~~~~~ FORMATTING OUTPUT ~~~~~~~~~~~~~~~~~~~~~

        // LocalDateTime lecture = LocalDateTime.of(2026, 11, 23, 13, 0);

        // DateTimeFormatter f1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        // DateTimeFormatter f2 =
        // DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy 'at' h:mm a");
        // DateTimeFormatter f3 =
        // DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);

        // System.out.println(lecture.format(f1));
        // System.out.println(lecture.format(f2));
        // System.out.println(lecture.format(f3));

        //~~~~~~~~~~~~~~~~~~~~~~~~ PARSING INPUT SAFELY ~~~~~~~~~~~~~~~~~~~~~~

        // DateTimeFormatter us = DateTimeFormatter.ofPattern("M/d/yyyy");
        // Scanner sc = new Scanner(System.in);
        // LocalDate date = null;

        // while (date == null){
        //     System.out.print("Enter a date (m/d/yyyy): ");
        //     String text = sc.nextLine().trim();
        //     try {
        //         date = LocalDate.parse(text, us);
        //     } catch (DateTimeParseException e) {
        //         System.out.println("Sorry, '" + text + "' is not a valid date.");
        // }

        //~~~~~~~~~~~~~~~~~~~~~~~ CONVERTING TIME ZONES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        // ZoneId boise = ZoneId.of("America/Boise");
        // ZonedDateTime meeting = ZonedDateTime.of(LocalDateTime.of(2026, 11, 23, 13, 0), boise);

        // List<String> cities = List.of("America/New_York", "Europe/London", "Asia/Tokyo");
        // for (String id : cities){
        //     ZonedDateTime there = meeting.withZoneSameInstant(ZoneId.of(id));
        //     System.out.println(id + " -> " + there.toLocalDateTime());
        // }

        //~~~~~~~~~~~~~~~~~~~~~~~ INSTANT TIMESTAMPS & TIMING ~~~~~~~~~~~~~~~~~~~~~~~~~~

        Instant start = Instant.now();

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 1_000_000; i++){
            sb.append(i);
        }

        Instant end = Instant.now();
        Duration elapsed = Duration.between(start, end);
        System.out.println("Took " + elapsed.toMillis() + " ms");

        

        
        
    }
}
