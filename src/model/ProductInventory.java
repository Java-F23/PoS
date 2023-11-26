package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import exceptions.DuplicateProductException;
import exceptions.InvalidFormatException;
import helpers.CSVHandler;

/**
 * model.ProductInventory class that represents an inventory of products.
 * The inventory uses a GUI.CustomMap to store GUI.Product objects with their
 * ids as keys.
 */
public class ProductInventory {

    // private static CustomMap inventory = new CustomMap();
    private static Map<String, Product> inventory = new HashMap<>();

    public ProductInventory() throws InvalidFormatException {
        List<String> returnedProducts = CSVHandler.readFromCsv("productInventory.csv");
        returnedProducts.stream()
                .map(product -> {
                    List<String> splitProduct = Arrays.asList(product.split(","));
                    String id = splitProduct.get(0);
                    String name = splitProduct.get(1);
                    BigDecimal quantity = new BigDecimal(splitProduct.get(2));
                    BigDecimal price = new BigDecimal(splitProduct.get(3));
                    BigDecimal taxRate = new BigDecimal(splitProduct.get(4));

                    try {
                        return new Product(id, name, quantity, price, taxRate);
                    } catch (InvalidFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error loading data from file.");
                    }
                    return null;
                })
                .forEach(product -> inventory.put(product.getId(), product));
    }

    /**
     * GUI.ProductInventory class that represents an inventory of products.
     * The inventory uses a GUI.CustomMap to store GUI.Product objects with their
     * ids as keys.
     */

    public static Map<String, Product> getInventory() {
        return inventory;
    }

    public static void overwriteInventoryToCsv() {
        // NEW PRODUCTS ARRAY
        List<String> newProductsArray = new ArrayList<>();

        for (Product product : inventory.values()) {
            String productRecord = CSVHandler.convertToCSV(
                    product.getId(),
                    product.getName(),
                    product.getQuantity().toString(),
                    product.getPrice().toString(),
                    product.getTaxRate().toString());
            // append entry to NEW PRODUCTS ARRAY
            newProductsArray.add(productRecord);
        }

        CSVHandler.clearFile("productInventory.csv");
        // write the new products array to the CSV file
        CSVHandler.writeMessageToCsv("productInventory.csv", newProductsArray, true);
    }

    // Returns the inventory products as 2-D Object array which is compatible with
    // JTable
    public static Object[][] getInventoryForTable() {
        // int size = inventory.size();
        Object[][] tableData = inventory.entrySet().stream()
                .map(entry -> {
                    Product product = entry.getValue();
                    return new Object[] {
                            product.getId(),
                            product.getName(),
                            product.getQuantity(),
                            product.getPrice(),
                            product.getTaxRate()
                    };
                })
                .toArray(Object[][]::new);
        return tableData;
    }

    /**
     * Method to check the availability of a product in the inventory.
     * If the product is available and its quantity is greater than or equal to the
     * requested quantity, return the product.
     * If the product is available but its quantity is less than the requested
     * quantity, throw an GUI.InsufficientQuantityException.
     * If the product is not available, return null.
     *
     * @param id       The id of the product to check.
     * @param quantity The requested quantity.
     * @return The product if it is available and its quantity is sufficient.
     * @throws InsufficientQuantityException If the product's quantity is
     *                                       insufficient.
     */
    public static Product getProductAvailability(String id, BigDecimal quantity) throws InsufficientQuantityException {
        Product product = inventory.get(id);
        if (product != null && product.getQuantity().compareTo(quantity) >= 0) {
            return product;
        } else if (product != null && !(product.getQuantity().compareTo(quantity) >= 0)) {
            // assert product != null;
            throw new InsufficientQuantityException("The quantity you specified (" + quantity + ")" +
                    " is more than the available quantity (" + product.getQuantity() + ")");
        } else {
            return null;
        }
    }

    // Method to add a new product to the inventory
    public static void addProduct(Product product) {
        inventory.put(product.getId(), product);
    }

    public static boolean doesProductIDExist(String productID) throws DuplicateProductException {
        Product product = inventory.get(productID);
        if (product == null) {
            return false;
        } else
            throw new DuplicateProductException(
                    "Product with id " + product.getId() + " already exists. Please change the ID.");
    }

    /**
     * Method to decrement the stock of a product in the inventory.
     * If the product exists, decrement its quantity.
     * If the product does not exist, print a message indicating that the product
     * was not found.
     *
     * @param id       The id of the product.
     * @param quantity The quantity to decrement.
     */
    public static void decrementProductStock(String id, BigDecimal quantity) {
        Product product = inventory.get(id);
        if (product != null) {
            // product.setPrice(price);
            product.setQuantity(product.getQuantity().subtract(quantity));
            // product.setName(name);
        } else {
            System.out.println("GUI.Product not found in inventory.");
        }
    }
}
