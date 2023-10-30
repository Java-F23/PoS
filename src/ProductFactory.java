import GUI.Product;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
public class ProductFactory {
    private static int productCount = 0;
    private static Set<String> productIds = new HashSet<>();

    // Method to create a product
    public Product createProduct(String id, String name, BigDecimal quantity, BigDecimal price, BigDecimal taxRate ) {
        if (productIds.contains(id)) {
            throw new IllegalArgumentException("ID is already reserved: " + id);
        }
        productIds.add(id);
        productCount++;
        return new Product(id, name, quantity, price, taxRate);
    }

    public static int getProductCount() {
        return productCount;
    }
}