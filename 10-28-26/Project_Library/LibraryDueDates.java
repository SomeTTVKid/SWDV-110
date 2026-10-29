import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

// Not fully happy with this but it works...I think

public class LibraryDueDates {
    public static void main(){
        Scanner userInput = new Scanner(System.in);
        float fee = 0.0f;

        LocalDate checkoutDate = LocalDate.now();

        LocalDate dueDate = checkoutDate.plusDays(21);

        // If due date is on weekend, move it to Monday
        if(dueDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
            dueDate = dueDate.plusDays(2);
        }else if(dueDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            dueDate = dueDate.plusDays(1);
        }

        // Print due date and how many days until its due
        long days = ChronoUnit.DAYS.between(checkoutDate, dueDate);
        System.out.printf("Due date is %s, %s, %d days away%n", dueDate.toString(), dueDate.getYear(), days);

        // Ask for a return date, if late, charge $0.25 per day
        System.out.println("Return date (yyyy-mm-dd)");

        LocalDate returnDate = LocalDate.parse(userInput.next());

        if(returnDate.isAfter(dueDate)){
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);

            fee = daysLate * 0.25f;
        }

        // Print the free formatted as currency
        System.out.printf("You owe: $%,.2f%n",fee);
    }
}
