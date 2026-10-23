package edu.cwi.swdv110.contacts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ```bash
// mvn -q compile exec:java
// ```

// Your output should match this, including the column alignment:

// ```text
// Skipped line 7: expected 3 fields but found 1
// Skipped line 10: name is blank

// NAME EMAIL PHONE USERNAME
// ------------------------------------------------------------------------------
// John Smith jsmith@example.com (555) 123-4567 jsmith
// Maria Garcia-Lopez maria.g@college.edu (208) 555-0199 mgarcial
// Ana De La Cruz ana.delacruz@mail.com (208) 555-0142 acruz
// Bob !! bob@ INVALID bob
// Kim Lee kim.lee@cwi.edu (208) 555-0175 klee
// Minh Tran !! minh tran@example.com (208) 555-0111 mtran
// Pat O'neil pat.oneil@example.org (986) 555-0123 poneil
// ------------------------------------------------------------------------------
// Total contacts: 7 | Invalid emails: 2 | Invalid phones: 1

// Search (or 'quit'): AN
// Ana De La Cruz a***********@mail.com
// Minh Tran m********@example.com
// 2 match(es)

// Search (or 'quit'): quit
// Bye!

// - **Apostrophes:** make `titleCase` capitalize after `'`, so `o'neil` becomes `O'Neil`. Then update `makeUsername` so it still produces `poneil`.
// - **Duplicates:** flag two contacts whose emails match with `equalsIgnoreCase`.
// - **Export:** write a cleaned `contacts_clean.csv` using `String.join(",", ...)` and `Files.writeString(...)`.
// - **Text block:** print a welcome banner with a text block and `formatted()`.
// - **More tests:** add at least 3 test methods of your own to `ContactParserTest` for edge cases you thought of.

public class App {

    public static void main(String[] args) throws IOException {
        // Reading files is covered later in Murach – this one line gives you every line of the file.
        List<String> lines = Files.readAllLines(Path.of("contacts.txt"));
        List<Contact> contacts = new ArrayList<>();
        List<String> skippedLines = new ArrayList<>();

        Scanner userInput = new Scanner(System.in);
        boolean running = true;

        // TODO Part 5a: loop over lines
        for(int i = 0; i < lines.size(); i++){
            String line = lines.get(i);
            if(line.isBlank() || line.stripLeading().startsWith("#")){
                continue;
            }

            try {
                contacts.add(ContactParser.parseLine(line));
            } catch (IllegalArgumentException e) {
                skippedLines.add("Skipped line " + (i + 1) + ": " + e.getMessage());
            }
        }

        // Print our banner after parsing the contacts
        String title = "Contact Cleaner";
        String loaded = "Loaded %d contacts".formatted(contacts.size());

        // If we want to center the text
        // Get the width of the text
        // Subtract from our 78
        // divide by 2 to get spaces on either side
        // put blanks for that amount before the string
        System.out.println("""
                %s
                %s%s
                %s%s
                %s
                """.formatted(
                "=".repeat(78),
                " ".repeat((78 - title.length()) / 2), title,
                " ".repeat((78 - loaded.length()) / 2), loaded,
                "=".repeat(78)
        ));

        for (String message : skippedLines) {
            System.out.println(message);
        }
        System.out.print("\n");

        // - skip blank lines and lines that start with '#'
        // - parseLine(...) each remaining line and add it to contacts
        // - catch IllegalArgumentException and print "Skipped line N: <message>"
        // (N is the line number in the file, starting at 1)

        // TODO Part 5b: print ContactReport.buildReport(contacts)
        System.out.println(ContactReport.buildReport(contacts));

        // TODO Part 5c: search loop
        // - prompt "Search (or 'quit'): "
        // - 'quit' (any capitalization) ends the program
        // - otherwise print every contact whose name contains the term, ignoring case,
        // showing the name and the MASKED email, then "N match(es)"
        while(running){
            int matches = 0;
            System.out.println("\n");
            System.out.print("Seach (or 'quit'): ");
            String userChoice = userInput.nextLine();
            if(userChoice.equalsIgnoreCase("quit")){
                System.out.println("Bye!");
                running = false;
                return;
            }

            for(Contact contact : contacts){
                if(contact.name().toLowerCase().contains(userChoice.toLowerCase())){
                    matches++;
                    System.out.println(contact.name() + " " + ContactParser.maskEmail(contact.email()));
                }
            }
            System.out.println(matches + " match(es)");
        }
    }
}
