import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentRoster {
    public static void main(String[] args) {
        record Student(String name, double gpa, String major){}

        List<Student> studentRoster = new ArrayList<>(List.of(
            new Student("joe", 4.0, "SWDV"),
            new Student("tim", 3.8, "Art"),
            new Student("brady", 2.9, "CS"),
            new Student("brandon", 1.2, "Math"),
            new Student("george", 3.2, "Law"),
            new Student("jose", 1.8, "Math"),
            new Student("james", 4.0, "SWDV"),
            new Student("skylar", 0.8, "SWDV")
        ));

        studentRoster.sort(Comparator.comparing(Student::name));
        
        System.out.println(studentRoster);
        System.out.println("\n");

        studentRoster.sort(Comparator.comparing(Student::major).thenComparing(Comparator.comparingDouble(Student::gpa).reversed()));

        System.out.println(studentRoster);
        System.out.println("\n");

        studentRoster.removeIf(student -> student.gpa() < 2.0);

        studentRoster.forEach(student -> System.out.println(student.name().substring(0, 1).toUpperCase() + student.name().substring(1)));



    }


}
