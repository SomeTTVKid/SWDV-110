import java.util.*;

public class parser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your full name (First Middle Last) seperated by spaces"); 
        
        String fullName = scanner.nextLine();

        // Split name up by spaces
        String firstName = fullName.substring(0, fullName.indexOf(' '));
        String firstInitial = firstName.substring(0, 1);

        String middleName = fullName.substring(fullName.indexOf(' ') + 1, fullName.lastIndexOf(' '));
        String middleInital = middleName.substring(0, 1);

        String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
        String lastInital = lastName.substring(0, 1);

        System.out.println(firstInitial + "." + middleInital + "." + lastInital);

        System.out.println("\n");
        System.out.println("Enter your email.");
        String email = scanner.nextLine();

        // Print user and dmoain
        if(!email.contains("@") || !email.contains(".")){
            System.out.print("Invalid email");
        }else{
            int at = email.indexOf('@');
            String username = email.substring(0, at);
            System.out.println(username);
            String domain = email.substring(at + 1);
            System.out.println(domain);
        }
    }
}
