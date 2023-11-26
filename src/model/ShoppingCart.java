package model;

// import helpers.CSVHandler;
// import model.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GUI.ShoppingCart class that represents a shopping cart with cart items.
 * The cart items are stored in a GUI.CustomMapCart.
 */
public class ShoppingCart {

    private static Map<Product, BigDecimal> cartItems;
    private static BigDecimal cartTotal;

    // Constructor
    public ShoppingCart() {
        // cartItems = new CustomMapCart();
        cartItems = new HashMap<>();
        cartTotal = BigDecimal.ZERO;
    }

    // clears the cart; invoked upon successful completion of a transaction,
    // terminating the current user session
    public static void clearCart() {
        cartItems.clear();
    }

    public static BigDecimal getCartTotal() {
        return cartTotal;
    }

    // Returns true if the cart is empty; used for validation
    public static boolean isCartEmpty() {
        return cartItems.isEmpty();
    }

    public static void setCartTotal(BigDecimal cartTotal) {
        ShoppingCart.cartTotal = cartTotal;
    }

    public static Map<Product, BigDecimal> getCartItems() {
        return cartItems;
    }

    public static void printCartItems() {
        cartItems.entrySet().stream().forEach(entry -> {
            Product product = entry.getKey();
            BigDecimal quantity = entry.getValue();
            System.out.println(product.getName() + "\t" + quantity);
        });
    }

    public static Object[][] getCartItemsForTable() {
        List<Object[]> list = cartItems
                .entrySet()
                .stream()
                .map(entry -> {
                    Product product = entry.getKey();
                    BigDecimal quantity = entry.getValue();
                    return new Object[] {
                            product.getId(),
                            product.getName(),
                            quantity,
                            product.getPrice(),
                            product.getTaxRate()
                    };
                }).collect(Collectors.toList());

        return list.toArray(new Object[0][0]);
    }

    /**
     * Method to add a product to the shopping cart.
     * If the product is already in the cart, update its quantity.
     * If an error occurs, print an error message.
     *
     * @param product  The product to add.
     * @param quantity The quantity of the product.
     */
    public static void addProduct(Product product, BigDecimal quantity) {
        try {
            BigDecimal existingQuantity = cartItems.get(product);
            if (existingQuantity != null) {
                quantity = quantity.add(existingQuantity);
            }
            cartItems.put(product, quantity);
        } catch (Exception e) {
            System.out.println("Error adding product to cart: " + e.getMessage());
        }
    }

    /**
     * Method to remove a product from the shopping cart.
     * If an error occurs, print an error message.
     *
     * @param product The product to remove.
     */
    public static void removeProduct(Product product) {
        try {
            cartItems.remove(product);
        } catch (Exception e) {
            System.out.println("Error removing product from cart: " + e.getMessage());
        }
    }

    /**
     * Method to calculate the total cost of items in the shopping cart.
     * If an error occurs, print an error message.
     *
     * @return The total cost of items in the shopping cart.
     */
    public static BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        try {
            total = cartItems.keySet().stream()
                    .map(product -> {
                        BigDecimal price = product.getPrice();
                        BigDecimal quantity = cartItems.get(product);
                        BigDecimal taxRate = product.getTaxRate();

                        BigDecimal priceWithTax = price.multiply(taxRate.add(BigDecimal.ONE));
                        return priceWithTax.multiply(quantity);
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            System.out.println("Error calculating total: " + e.getMessage());
        }
        setCartTotal(total);
        return total;
    }
}