import java.util.*;

public class TodoManager {
    static List<String> tasks = new ArrayList<>();
    public static void main() {
        Scanner scanner = new Scanner(System.in);
        int userChoice;
        int index;

        System.out.println("What would you like to do today?");
        System.out.println("1: add - 2: remove - 3: list - 4: done - 5: quit\n");
        try {
            userChoice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Please enter a valid choice.\n");
            main();
            return;
        }

        switch(userChoice){
            case 1: 
                // prompt user for new task
                // add to tasks
                System.out.println("Please enter the new task.\n");
                tasks.add(scanner.nextLine());
                main();
                return;
            case 2:
                // prompt user for index to remove
                System.out.println("What task would you like to remove?");
                List(tasks);
                try{
                    index = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) { 
                    System.out.println("Please enter a valid choice.\n");
                    main();
                    return;     
                }
                tasks.remove(index);
                System.out.println("Task #" + (index + 1) + " has been removed.\n");
            case 3:
                // List all tasks with proper index
                List(tasks);
                main();
                return;
            case 4:
                // prompt user for index
                System.out.println("What task have you completed?\n");
                List(tasks);
                try {
                    index = Integer.parseInt(scanner.nextLine()) - 1;  
                } catch (Exception e) {
                    System.out.println("Please enter a valid choice.\n");
                    main();
                    return;
                }
                tasks.remove(index);
                System.out.println("Task #" + (index + 1) + " has been completed.\n");
                main();
                return;
                // remove index
                // 
            case 5:
                return;
        }

    }

    public static void List(List<String> tasks){
        for(int i = 0; i < tasks.size(); i++){
            System.out.println((i + 1) + ". " + tasks.get(i));
        }  
        System.out.println("\n");
    }
    
}
