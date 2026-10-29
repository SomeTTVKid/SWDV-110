import java.time.LocalDate;
import java.util.Scanner;

public class BirthdayExplorer {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        // Ask for birthday as yyyy-mm-dd and parse() it
        System.out.println("Enter the birthday (yyyy-mm-dd)");
        LocalDate birthday = LocalDate.parse(userInput.next());

        String dayOfWeek = birthday.getDayOfWeek().toString().toLowerCase();
        
        // Print the day of the week that it falls on
        System.out.println("Your birthday is on a " + dayOfWeek);

        // Print which day of the year it was
        System.out.println("Your birthday is the " + birthday.getDayOfYear() + " day of the year!");

        // Print if it was a leap year
        System.out.println("Your birthday is " + (birthday.isLeapYear() ? "a leap year!" : "not a leap year."));

        // Build this year's birthday with withYear() and print if it has already passed
        LocalDate thisYearBirthday = birthday.withYear(LocalDate.now().getYear());
        
        if(thisYearBirthday.isBefore(LocalDate.now())){
            System.out.println("Your birthday has already passed.");
        }else{
            System.out.println("Your birthday has yet to come.");
        }
    }
}
