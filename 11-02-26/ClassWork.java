

public class ClassWork {
    public static void main(){
        // words.sort((a, b) -> a.length() - b.length());

        // Predicate<String> isLong = s -> s.length() > 5;
        // Predicate<String> startsJ = s -> s.startsWith("J");
        // Predicate<String> both = isLong.and(startsJ);

        // Function<String, Integer> length = s -> s.length();
        // Function<Integer, Integer> square = n -> n * n;

        // Consumer<String> shout = s -> System.out.println(s.toUpperCase() + "!");

        // System.out.println(both.test("JavaScript")); // true
        // System.out.println(length.andThen(square).apply("abc")); // 9
        // shout.accept("lambdas"); // LAMBDAS!

        // @FunctionInterface
        // interface MathOperation{
        //     int apply(int a, int b);
        // }

        // MathOperation add = (a, b) -> a + b;
        // MathOperation multiply = (a, b) -> a * b;
        // MathOperation power = (a, b) -> (int)Math.pow(a, b);


        // record Student(String name, double gpa, String major){}

        // List<Student> roster = new ArrayList<>(List.of(
        //      new Student("Ana", 3.8, "CS"),...
        // ))

        // roster.sort(Comparator.comparing(Student::gpa).reversed().thenComparing(Student::name));

        // roster.forEach(s -> System.out.println(s.name() + " " + s.gpa()));

    }
}
