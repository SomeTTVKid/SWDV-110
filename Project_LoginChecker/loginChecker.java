import java.util.*;

public class loginChecker {
    public static void main(String[] args) {
        String username = "admin";
        String password = "Secret123";
        ArrayList<String> usernames = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) { 
            System.out.println("Enter your username.");
            String userUsername = scanner.nextLine();
            usernames.add(userUsername);

            System.out.println("Enter your password.");
            String userPassword = scanner.nextLine();

            if(userUsername.equalsIgnoreCase(username) && userPassword.equals(password)){
                System.out.println("Welcome!");
                running = false;
            }else{
                System.out.println("Access Denied.");
            }

            String first = usernames.get(0);
            for(String name : usernames){
                if(name.compareTo(first) < 0){
                    first = name;
                }
            }

            System.out.println(first);            
        }

    }
}
