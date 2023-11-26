package GUI;

import java.math.BigDecimal;
import java.util.Objects;

import exceptions.InvalidFormatException;
import helpers.RegExChecker;

/**
 * GUI.Product class that represents a product with an id, name, quantity,
 * price, and tax rate.
 * The id and name cannot be null or empty, and the quantity cannot be negative.
 * The price cannot be negative, and the tax rate must be between 0 and 1.
 * The class has One static variable, productCount, to maintain the number of
 * created products
 */
public class Product {

    private String id;
    private String name;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal taxRate;

    private static int productCount = 0;

    // Overrides the equality such that the ShoppingCart.remove(Product) method
    // works (as it does equality check)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Constructor that initializes the product with the given id, name, quantity,
     * price, and tax rate.
     * 
     * @param id       The id of the product.
     * @param name     The name of the product.
     * @param quantity The quantity of the product.
     * @param price    The price of the product.
     * @param taxRate  The tax rate of the product.
     * @throws InvalidFormatException
     */
    public Product(String id, String name, BigDecimal quantity, BigDecimal price, BigDecimal taxRate)
            throws InvalidFormatException {
        if (id == null || id.isEmpty() || name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        String regex = "^[^!@$%&]*$";

        // RegEx checking is important in the context of Product IDs as it is
        // conventional to avoid using the below characters for primary keys and IDs
        // as per https://www.w3schools.com/sql/sql_primarykey.ASP

        if (!RegExChecker.isInputValid(regex, id)) {
            throw new InvalidFormatException("Invalid Product ID, " + id + "\n !, @, $, %, & are not allowed.");
        }

        this.id = id;
        this.name = name;

        if (quantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative: " + quantity);
        }
        this.quantity = quantity;

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative: " + price);
        }
        this.price = price;

        if (taxRate.compareTo(BigDecimal.ZERO) < 0 || taxRate.compareTo(BigDecimal.ONE) > 0
                || taxRate.toString().isEmpty()) {
            throw new IllegalArgumentException("Tax rate must be between 0 and 1: " + taxRate);
        }
        this.taxRate = taxRate;
        productCount++;
    }

    /**
     * Default constructor that initializes the product with default values.
     * 
     * @throws InvalidFormatException
     */
    public Product() throws InvalidFormatException {
        this("UNDEFINED NAME " + String.valueOf(productCount + 1), "UNDEFINED NAME",
                new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"));
    }

    /**
     * Constructor that initializes the product with the given id, name, quantity,
     * and price, and a tax rate of 0.
     * 
     * @param id       The id of the product.
     * @param name     The name of the product.
     * @param quantity The quantity of the product.
     * @param price    The price of the product.
     * @throws InvalidFormatException
     */
    public Product(String id, String name, BigDecimal quantity, BigDecimal price) throws InvalidFormatException {
        this(id, name, quantity, price, new BigDecimal("0"));
    }

    public static int getProductCount() {
        return productCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * Method that decrements the quantity of the product by the given quantity.
     * 
     * @param quantity The quantity to decrement by.
     */
    public void decrementQuantity(BigDecimal quantity) {
        this.quantity = this.quantity.subtract(quantity);

    }

    /**
     * Method that increments the quantity of the product by the given quantity.
     * 
     * @param quantity The quantity to increment by.
     */
    public void incrementQuantity(BigDecimal quantity) {
        this.quantity = this.quantity.add(quantity);

    }
}
