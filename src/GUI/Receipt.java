package GUI;

import GUI.PaymentMethod;
import GUI.Product;
import GUI.ShoppingCart;

import java.math.BigDecimal;

/**
 * GUI.Receipt class that represents a receipt for a shopping cart.
 * The receipt includes the shopping cart and the payment method.
 */

public class Receipt {

    // private ShoppingCart cart;
    private static PaymentMethod paymentMethod;

    /**
     * Constructor that initializes the receipt with the given shopping cart and
     * payment method.
     *
     * @param cart          The shopping cart for the receipt.
     * @param paymentMethod The payment method for the receipt.
     */
    public Receipt(PaymentMethod paymentMethod) {
        // this.cart = cart;
        this.paymentMethod = paymentMethod;
    }

    // public ShoppingCart getCart() {
    // return cart;
    // }

    // public void setCart(ShoppingCart cart) {
    // this.cart = cart;
    // }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    // Method to generate the receipt

    /**
     * Method to generate the receipt.
     * The receipt includes the payment method, the items in the cart, and the total
     * amount.
     */
    public static String generateStringReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("GUI.Receipt:\n");
        receipt.append("Payment Method: ").append(paymentMethod).append("\n");
        receipt.append("-----------------------------\n");
        receipt.append(String.format("%-20s%-15s%-15s%n", "Item", "Quantity", "Unit Price"));
        // for (Product product : ShoppingCart.getCartItems().getKeys()) {
        for (Product product : ShoppingCart.getCartItems().keySet()) {
            BigDecimal quantity = ShoppingCart.getCartItems().get(product);
            receipt.append(String.format("%-20s%-15s%-15s%n", product.getName(), quantity, product.getPrice()));
        }
        receipt.append("-----------------------------\n");
        receipt.append(String.format("%-20s%-15s%n", "Total Amount:", ShoppingCart.calculateTotal()));
        return receipt.toString();
    }

    public void generateReceipt() {
        System.out.println("Receipt:");
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("-----------------------------");
        System.out.printf("%-20s%-15s%n", "Item", "Quantity", "Unit Price");
        // for (Map.Entry<GUI.Product, BigDecimal> entry :
        // this.cart.getCartItems().entrySet()) {
        // for (Map.Entry<GUI.Product, BigDecimal> entry :
        // this.cart.getCartItems().entrySet()) {
        // System.out.printf("%-20s%-15s%n", entry.getKey().getName(),
        // entry.getValue());
        // }
        // for (Product product : ShoppingCart.getCartItems().getKeys()) {
        for (Product product : ShoppingCart.getCartItems().keySet()) {
            BigDecimal quantity = ShoppingCart.getCartItems().get(product);
            System.out.printf("%-20s%-15s%-15s%n", product.getName(), quantity, product.getPrice());
        }
        // System.out.printf("%-20s%-15s%n", "Item", "Quantity");

        // for (GUI.Product product : this.cart.getCartItems().keys()) {
        // for (GUI.Product product : this.cart.getCartItems().getKeys()) {
        // BigDecimal quantity = this.cart.getCartItems().get(product);
        // System.out.printf("%-20s%-15s%n", product.getName(), quantity);
        // }

        System.out.println("-----------------------------");
        System.out.printf("%-20s%-15s%n", "Total Amount:", ShoppingCart.calculateTotal());
    }
}
