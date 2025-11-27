package performance;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CheckstyleTest {

    public static void main(String[] args) {
        // Ternary operator example
        int a = 10;
        int b = 20;
        int max = (a > b) ? a : b; // ternary operator
        System.out.println("Max: " + max);

        // Lambda expression example
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<String> upperNames = names.stream()
                .map(name -> name.toUpperCase()) // lambda
                .collect(Collectors.toList());

        upperNames.forEach(System.out::println);
    }
}
