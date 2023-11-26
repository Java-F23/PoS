package model;

/**
 * model.InsufficientQuantityException class that represents a custom exception.
 * This exception is thrown when there are no products defined in the inventory (empty inventory),
 * or the user requests to add quantity for a product that is more than its actual stock.
 */
public class InsufficientQuantityException extends Exception {
    /**
     * Constructor that initializes the exception with the given message.
     *
     * @param message The message of the exception.
     */
    public InsufficientQuantityException(String message) {
        super(message);
    }
}
