import java.util.*;

public class TicketManager {
    static Deque<String> tickets = new ArrayDeque<>();
    static Deque<String> resolved = new ArrayDeque<>();

    public static void main(){
        Scanner scanner = new Scanner(System.in);
        int userChoice;

        System.out.println("Welcome to the help desk ticketing system. Please select an option.");
        System.out.println("1. New Ticket - 2. Next Ticket - 3. Peek? - 4. Count - 5. Undo - 6. Quit");

        try {
            userChoice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Please enter a valid option.");
            main();
            return;
        }
        
        switch(userChoice){
            case 1:
                System.out.println("Enter the new ticket.");
                String newTicket = scanner.nextLine();
                tickets.offer(newTicket);
                main();
                return;
            case 2:
                if(tickets.isEmpty()){
                    System.out.println("No more tickets to work on.");
                    main();
                    return;
                }
                String currentTicket = tickets.poll();
                resolved.push(currentTicket);
                System.out.println("Ticket has been resolved.");
                main();
                return;
            case 3:
                System.out.println(tickets.peek());
                main();
                return;
            case 4:
                System.out.println("There are " + tickets.size() + " tickets remaining.");
                main();
                return;
            case 5:
                // This will crash if trying to do more tickets than the were originally
                String previousTicket = resolved.pop();
                tickets.offer(previousTicket);
                main();
                return;
            case 6:
                return;
                
        }
    }

}
