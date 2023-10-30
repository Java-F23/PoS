package GUI;

import GUI.ShoppingCart;

import java.math.BigDecimal;

import static GUI.PaymentMethod.*;


/**
 * GUI.Cashier class that represents a cashier with a shopping cart, payment method, and receipt.
 * The cashier can change the payment method, calculate the total sale, and process the payment.
 */
public class Cashier {
//    private final ShoppingCart cart;
    private PaymentMethod paymentMethod;
    private Receipt receipt;


    /**
     * Constructor that initializes the cashier with the given shopping cart and payment method.
     *
     * @param cart          The shopping cart of the cashier.
     * @param paymentMethod The payment method of the cashier.
     */
    public Cashier( PaymentMethod paymentMethod) {
//        this.cart = cart;
        this.paymentMethod = paymentMethod;
        this.receipt = new Receipt(paymentMethod);
    }

//    public ShoppingCart getCart() {
//
//        return cart;
//    }

    public PaymentMethod getPaymentMethod() {

        return paymentMethod;
    }

    /**
     * Method to change the payment method of the cashier.
     *
     * @param newPaymentMethod The new payment method.
     */
    public void changePaymentMethod(PaymentMethod newPaymentMethod) {
        this.paymentMethod = newPaymentMethod;
        receipt.setPaymentMethod(newPaymentMethod);
    }

    /**
     * Method to calculate the total cost of the sale transaction.
     *
     * @return The total cost of the sale transaction.
     */
    public BigDecimal calculateTotalSale() {
        return ShoppingCart.calculateTotal();
    }

    /**
     * Method to process the payment and generate the receipt.
     * Depending on the payment method, different messages are printed.
     * After processing the payment, the receipt is generated.
     */
    public void processPaymentAndGenerateReceipt() {
        switch (this.paymentMethod) {
            case CASH:
                System.out.println("Processing cash payment...");
                break;
            case VISA:
                System.out.println("Processing credit card payment...");
                break;
            case MOBILE:
                System.out.println("Processing mobile payment...");
                break;
        }
//        this.receipt.generateReceipt();
        String receipt = Receipt.generateStringReceipt();
        SaveMsgToCsv.writeMessageToCsv("client_receipt.csv", receipt);
    }
}
