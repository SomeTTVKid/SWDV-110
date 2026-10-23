package edu.cwi.swdv110.contacts;

/** Static helper methods that clean up one piece of a messy contact line. */
public final class ContactParser {

    private ContactParser() { }

    public static String titleCase(String word) {
    // TODO Part 1 – hint: StringBuilder + setCharAt
        StringBuilder clean = new StringBuilder();

        // First letter is always capital
        boolean capitalize = true;

        for(char ch : word.toCharArray()){
            if(ch == ' ' || ch == '-'){
                clean.append(ch);
                capitalize = true;
            }else if(capitalize){
                clean.append(Character.toUpperCase(ch));
                capitalize = false;
            }else{
                clean.append(Character.toLowerCase(ch));
            }
        }

        return clean.toString();
    }

    /**
    * Cleans a raw name.
    * - trims it and collapses runs of spaces to one space
    * - "Last, First Middle" is reordered to "First Middle Last"
    * - every word is title-cased
    * Returns "" for a blank name.
    * " smith, JOHN " -> "John Smith"
    * "DE LA CRUZ, ana" -> "Ana De La Cruz"
    * "maria garcia-lopez" -> "Maria Garcia-Lopez"
    */
    public static String normalizeName(String raw) {
    // TODO Part 1 – hint: indexOf(','), substring, split("\\s+"), String.join
        String name = raw.trim();
        if(name.isEmpty()){
            return "";
        }

        int comma = name.indexOf(',');
        if(comma >=0){
            // We know there is a comma, we need to remove it and re-order the name
            String[] names = name.trim().split(",\\s+");
            name = names[1] + " " + names[0];
        }
        // This is probably so wrong, but it passes the tests 

        return titleCase(name);
    }

    /**
    * Returns true when the (trimmed) email:
    * 1. has no spaces,
    * 2. has exactly one '@' with at least one character before it, and
    * 3. has a domain (the part after '@') that contains a '.' that is not its first
    * character, does not end with '.', and never has two dots in a row.
    */
    public static boolean isValidEmail(String email) {
    // TODO Part 2 – no regular expressions; use indexOf / lastIndexOf / substring
        String trimmedEmail = email.trim();

        for(int i = 0; i < trimmedEmail.length(); i++){
            if(Character.isWhitespace(trimmedEmail.charAt(i))){
                return false;
            }
        }

        int at = trimmedEmail.indexOf('@');
        if (at <= 0 || at != trimmedEmail.lastIndexOf('@')) {
            return false;
        }

        String domain = trimmedEmail.substring(at + 1);
        boolean validEmail = false;

        for(int i = 0; i < domain.length(); i++){
            if(domain.charAt(i) == '.'){
                validEmail = true;

                // Need to make sure it:
                // Has no domain before dot
                // Is not the last thing in an email
                // does not have a dot before it
                if (i == 0 || i == domain.length() - 1 || domain.charAt(i - 1) == '.') {
                    return false;
                }
            }
        }

        return validEmail;
    }

    /** Trims and lowercases an email: " JSmith@Example.COM " -> "jsmith@example.com" */
    public static String normalizeEmail(String email) {
    // TODO Part 2
        String trimmedEmail = email.trim();

        StringBuilder clean = new StringBuilder();

        for(char ch : trimmedEmail.toCharArray()){
            if(ch == '@' || ch == '-'){
                clean.append(ch);
            }else{
                clean.append(Character.toLowerCase(ch));
            }
        }

        return clean.toString();
    }

    /**
    * Hides all but the first character before the '@':
    * "jsmith@example.com" -> "j*****@example.com"
    * If there are fewer than 2 characters before the '@' (or no '@'), return the email unchanged.
    */
    public static String maskEmail(String email) {
        int at = email.indexOf('@');
        if (at < 2) {
            return email;
        }

        String maskedEmail = email.charAt(0) + "*".repeat(at - 1) + email.substring(at);

        return maskedEmail;
    }

    /**
    * Keeps only the digits. If there are 11 digits and the first is '1', drop that leading 1.
    * Exactly 10 digits -> "(208) 555-0199". Anything else -> "INVALID".
    */
    public static String formatPhone(String raw) {
    // TODO Part 3 – hint: loop over toCharArray(), Character.isDigit, StringBuilder
        StringBuilder clean = new StringBuilder();
        String formattedNumber = "";
        int digitCount = 0;

        for(char ch : raw.toCharArray()){
            if(Character.isDigit(ch)){
                clean.append(ch);
            }
        }
        
        if(clean.length() == 11 && clean.charAt(0) == '1'){
            clean.deleteCharAt(0);
        }else if(clean.length() != 10){
            return "INVALID";
        }

        // Probably a better way to do this
        clean.insert(6, "-");
        clean.insert(3, " ");
        clean.insert(3, ")");
        clean.insert(0, "(");

        String cleanedNumber = clean.toString();
        
        return cleanedNumber;
    }

    /**
    * Username = first letter of the first word + the whole LAST word of a cleaned name,
    * lowercase, letters only, at most 8 characters.
    * "John Smith" -> "jsmith"
    * "Maria Garcia-Lopez" -> "mgarcial"
    * "Ana De La Cruz" -> "acruz"
    * A one-word name uses just that word: "Bob" -> "bob"
    */
    public static String makeUsername(String fullName) {
    // TODO Part 4
        StringBuilder clean = new StringBuilder();
        String userName = " ";
        String[] nameParts;

        nameParts = fullName.trim().split("\\s+");

        if(nameParts.length > 1){
            clean.append(fullName.charAt(0));

            for(char ch : nameParts[nameParts.length - 1].toCharArray()){
                if(Character.isAlphabetic(ch)){
                    clean.append(ch);
                }
            }
        }else{
            for(char ch : nameParts[0].toCharArray()){
                clean.append(ch);
            }
        }

        if(clean.length() > 8){
            clean.delete(8, clean.length());
        }

        for(int i = 0; i < clean.length(); i++){
            char lowercase = Character.toLowerCase(clean.charAt(i));
            clean.setCharAt(i, lowercase);
        }

        userName = clean.toString();

        return userName;
    }

    /**
    * Parses one line of the file: "name | email | phone".
    * Returns a Contact with a normalized name, normalized email and formatted phone.
    * Throws IllegalArgumentException if the line does not have exactly 3 fields
    * or the name is blank. (An invalid email or phone is NOT an exception –
    * the report flags those.)
    */
    public static Contact parseLine(String line) {
        // System.out.println("Line is parseLine: " + line);
    // TODO Part 4 – careful: '|' means something special to split(), so escape it: "\\|"
        String[] fields = line.trim().split("\\|");

        if(fields.length != 3){
            throw new IllegalArgumentException("expected 3 fields but found " + fields.length); 
        }

        if(fields[0].isBlank()){
            throw new IllegalArgumentException("name is blank"); 
        }
    
        return new Contact(
            normalizeName(fields[0]), 
            normalizeEmail(fields[1]), 
            formatPhone(fields[2])
        );
    }
}
