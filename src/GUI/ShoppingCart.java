package GUI;

import GUI.Product;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * GUI.ShoppingCart class that represents a shopping cart with cart items.
 * The cart items are stored in a GUI.CustomMapCart.
 */
public class ShoppingCart {

    private static CustomMapCart cartItems;

    private static BigDecimal cartTotal;

    // Constructor
    public ShoppingCart() {
        cartItems = new CustomMapCart();
        cartTotal = BigDecimal.ZERO;
    }

    // clears the cart; invoked upon successful completion of a transaction, terminating the current user session
    public static void clearCart() {
        cartItems.getKeys().clear();
        cartItems.getValues().clear();
    }
    public static BigDecimal getCartTotal() {
        return cartTotal;
    }

    // Returns true if the cart is empty; used for validation
    public static boolean isCartEmpty() {
        return cartItems.getKeys().isEmpty();
    }

    public static void setCartTotal(BigDecimal cartTotal) {
        ShoppingCart.cartTotal = cartTotal;
    }
    public static CustomMapCart getCartItems() {
        return cartItems;
    }
    public static void printCartItems() {
        ArrayList<Product> keys = cartItems.getKeys();
        ArrayList<BigDecimal> values = cartItems.getValues();

        System.out.println("Product\tQuantity");
        for (int i = 0; i < keys.size(); i++) {
            Product product = keys.get(i);
            BigDecimal quantity = values.get(i);
            System.out.println(product.getName() + "\t" + quantity);
        }
    }

    // Returns the cartItems as 2-D Object array which is compatible with JTable
    public static Object[][] getCartItemsForTable() {
        ArrayList<Product> keys = cartItems.getKeys();
        ArrayList<BigDecimal> values = cartItems.getValues();

        Object[][] tableData = new Object[keys.size()][5];
        for (int i = 0; i < keys.size(); i++) {
            Product product = keys.get(i);
            BigDecimal quantity = values.get(i);
            tableData[i][0] = product.getId();
            tableData[i][1] = product.getName();
            tableData[i][2] = quantity;
//            tableData[i][2] = product.getQuantity();
            tableData[i][3] = product.getPrice();
            tableData[i][4] = product.getTaxRate();
//            tableData[i][5] = quantity;
        }

        return tableData;
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
            for (Product product : cartItems.getKeys()) {
                BigDecimal price = product.getPrice();
                BigDecimal quantity = cartItems.get(product);
                BigDecimal taxRate = product.getTaxRate();

                BigDecimal priceWithTax = price.multiply(taxRate.add(BigDecimal.ONE));
                BigDecimal lineTotal = priceWithTax.multiply(quantity);

                total = total.add(lineTotal);

//                total = total.add(price.multiply(quantity));
            }
        } catch (Exception e) {
            System.out.println("Error calculating total: " + e.getMessage());
        }
        setCartTotal(total);
        return total;
    }
}