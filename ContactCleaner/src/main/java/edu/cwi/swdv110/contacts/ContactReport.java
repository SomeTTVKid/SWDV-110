package edu.cwi.swdv110.contacts;

import java.util.List;

/** Builds the printable report for a list of cleaned contacts. */
public final class ContactReport {

    /** Use this format for the header and every row so the columns line up. */
    private static final String ROW = "%-22s %-30s %-15s %-9s%n";

    private ContactReport() { }

    /**
    * Returns the full report as ONE string (see the sample output in Part 5):
    * - a header row: NAME EMAIL PHONE USERNAME
    * - a line of 78 dashes
    * - one row per contact; put "!! " in front of an invalid email
    * - another line of dashes
    * - "Total contacts: N | Invalid emails: N | Invalid phones: N"
    */
    public static String buildReport(List<Contact> contacts) {
    // TODO Part 5 – build it with a StringBuilder and String.format(ROW, ...)
    
        // How we access the data 
        // System.out.println(contacts.get(0).email());
        StringBuilder clean = new StringBuilder();

        String email = "";
        int contactCount = 0;
        int invalidEmailCount = 0;
        int invalidPhoneCount = 0;

        clean.append(String.format(ROW, "NAME", "EMAIL", "PHONE", "USERNAME"));
        clean.append("-".repeat(78));
        clean.append("\n");
        for(int i = 0; i < contacts.size(); i++){
            // Put total contacts here
            contactCount++;

            if(ContactParser.isValidEmail(contacts.get(i).email())){
                clean.append(String.format(ROW, contacts.get(i).name(), contacts.get(i).email(), contacts.get(i).phone(), ContactParser.makeUsername(contacts.get(i).name())));
            }else{
                clean.append(String.format(ROW, contacts.get(i).name(), ("!! " + contacts.get(i).email()), contacts.get(i).phone(), ContactParser.makeUsername(contacts.get(i).name())));
                invalidEmailCount++;
            }

            if(contacts.get(i).phone().equals("INVALID")){
                invalidPhoneCount++;
            }
        }
        // clean.append("\n");
        clean.append("-".repeat(78));
        clean.append("\n");

        clean.append("Total contacts: ")
            .append(contactCount)
            .append(" | Invalid emails: ")
            .append(invalidEmailCount)
            .append(" | Invalid phones: ")
            .append(invalidPhoneCount);
    
        return clean.toString();
    }
}
