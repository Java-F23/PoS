package GUI;

/**
 * GUI.PaymentMethod enum that represents the payment methods available.
 * The payment methods are CASH, CREDIT_CARD, and MOBILE_PAYMENT.
 */
public enum PaymentMethod {
    CASH,           // Represents cash payment method
    VISA,    // Represents credit card payment method
    MOBILE;  // Represents mobile payment method

    public static PaymentMethod fromString(String value) {
        for (PaymentMethod method : PaymentMethod.values()) {
            if (method.name().equalsIgnoreCase(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Invalid payment method: " + value);
    }

    }
