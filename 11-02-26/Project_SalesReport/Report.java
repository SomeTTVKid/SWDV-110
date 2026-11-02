import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Report {
    public static void main(String[] args) {
        record Sale(String product, String region, int quantity, double price){}

        // AI generated list
        List<Sale> sales = List.of(
            new Sale("Laptop", "ID", 2, 1200.00),
            new Sale("Smartphone", "OR", 5, 800.00),
            new Sale("Headphones", "WA", 10, 150.00),
            new Sale("Monitor", "UT", 3, 300.00),
            
            // Duplicate Set 1 (Revenue below 100)
            new Sale("Mouse", "WA", 2, 25.00),      // Revenue: 50.00
            new Sale("Mouse", "WA", 2, 25.00),      // Duplicate
            
            // Duplicate Set 2 (Revenue below 100)
            new Sale("Keyboard", "ID", 1, 75.50),   // Revenue: 75.50
            new Sale("Keyboard", "ID", 1, 75.50),   // Duplicate
            
            new Sale("Tablet", "NV", 4, 450.00),
            new Sale("Smartwatch", "OR", 8, 250.00)
        );

        double totalRevenue = sales.stream().mapToDouble(sale -> sale.quantity() * sale.price()).sum();

        System.out.println("Total Revenue: $" + totalRevenue);

        System.out.println("Products over $100 revenue: ");

        sales.stream()
        // Keep sales with more than 100 revenue
        .filter(sale -> sale.quantity() * sale.price() > 100)
        .map(Sale::product)
        // Sort A-Z with no duplicates
        .sorted()
        .distinct()
        // Print the product
        .forEach(sale -> System.out.println(". " + sale));

        // Single biggest sale based on revenue
        sales.stream().max(Comparator.comparingDouble(sale -> sale.price() * sale.quantity()))
            .ifPresent(sale -> System.out.println("Biggest Sale: " + sale.product() + ". Revenue: $" + sale.price() * sale.quantity()));

        // Revenue per region
        Map<String, Double> salesByRegion = sales.stream().collect(Collectors.groupingBy(Sale::region, Collectors.summingDouble(sale -> sale.price() * sale.quantity())));
        salesByRegion.forEach((region, revenue) -> System.out.println(region + " made $" + revenue));

    }
}
