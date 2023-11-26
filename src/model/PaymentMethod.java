package model;

import java.util.Arrays;

/**
 * GUI.PaymentMethod enum that represents the payment methods available.
 * The payment methods are CASH, CREDIT_CARD, and MOBILE_PAYMENT.
 */
public enum PaymentMethod {
    CASH, // Represents cash payment method
    VISA, // Represents credit card payment method
    MOBILE; // Represents mobile payment method

    public static PaymentMethod fromString(String value) {
        System.out.println("Given value: " + value);
        return Arrays.stream(PaymentMethod.values())
                .filter(method -> method.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment method: " + value));
    }

}
