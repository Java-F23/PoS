package model;

import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 * model.Receipt class that represents a receipt for a shopping cart.
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

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

        String cartItemsReceipt = ShoppingCart.getCartItems().keySet().stream()
                .map(product -> {
                    BigDecimal quantity = ShoppingCart.getCartItems().get(product);
                    return String.format("%-20s%-15s%-15s%n", product.getName(), quantity, product.getPrice());
                })
                .collect(Collectors.joining());

        receipt.append(cartItemsReceipt);
        receipt.append("-----------------------------\n");
        receipt.append(String.format("%-20s%-15s%n", "Total Amount:", ShoppingCart.calculateTotal()));
        return receipt.toString();
    }

    public void generateReceipt() {
        System.out.println("Receipt:");
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("-----------------------------");
        System.out.printf("%-20s%-15s%n", "Item", "Quantity", "Unit Price");

        ShoppingCart.getCartItems().keySet().stream().forEach(product -> {
            BigDecimal quantity = ShoppingCart.getCartItems().get(product);
            System.out.printf("%-20s%-15s%-15s%n", product.getName(), quantity, product.getPrice());
        });

        System.out.println("-----------------------------");
        System.out.printf("%-20s%-15s%n", "Total Amount:", ShoppingCart.calculateTotal());
    }

}
