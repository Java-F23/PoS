package GUI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.DuplicateProductException;
import exceptions.InvalidFormatException;
import helpers.CSVHandler;

/**
 * GUI.ProductInventory class that represents an inventory of products.
 * The inventory uses a GUI.CustomMap to store GUI.Product objects with their
 * ids as keys.
 */
public class ProductInventory {

    private static CustomMap inventory = new CustomMap();

    public ProductInventory() throws InvalidFormatException {
        List<String> returnedProducts = CSVHandler.readFromCsv("productInventory.csv");

        for (String product : returnedProducts) {

            List<String> splitProduct = Arrays.asList(product.split(","));
            String id = splitProduct.get(0);
            String name = splitProduct.get(1);
            BigDecimal quantity = new BigDecimal(splitProduct.get(2));
            BigDecimal price = new BigDecimal(splitProduct.get(3));
            BigDecimal taxRate = new BigDecimal(splitProduct.get(4));

            Product newProduct = new Product(id, name, quantity, price, taxRate);

            inventory.put(newProduct.getId(), newProduct);

        }

    }

    /**
     * GUI.ProductInventory class that represents an inventory of products.
     * The inventory uses a GUI.CustomMap to store GUI.Product objects with their
     * ids as keys.
     */

    public static CustomMap getInventory() {
        return inventory;
    }

    public static void overwriteInventoryToCsv() {
        // NEW PRODUCTS ARRAY
        List<String> newProductsArray = new ArrayList<>();

        for (Product product : inventory.getValues()) {
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
        ArrayList<String> keys = inventory.getKeys();
        ArrayList<Product> values = inventory.getValues();

        Object[][] tableData = new Object[keys.size()][5];
        for (int i = 0; i < keys.size(); i++) {
            Product product = values.get(i);
            tableData[i][0] = product.getId();
            tableData[i][1] = product.getName();
            tableData[i][2] = product.getQuantity();
            tableData[i][3] = product.getPrice();
            tableData[i][4] = product.getTaxRate();
        }

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
        // GUI.Product product = new GUI.Product(id, name, quantity, price);

        inventory.put(product.getId(), product);

    }

    public static void writeProductToCSV(Product product) {
        String productRecord = CSVHandler.convertToCSV(
                product.getId(),
                product.getName(),
                product.getQuantity().toString(),
                product.getPrice().toString(),
                product.getTaxRate().toString());

        CSVHandler.writeMessageToCsv("productInventory.csv", productRecord, true);

    }

    // Method to add a new product to the inventory
    // public static void addProduct(String product) {
    // // GUI.Product product = new GUI.Product(id, name, quantity, price);

    // inventory.put(product.getId(), product);
    // String productRecord = CSVHandler.convertToCSV(product.getId(),
    // product.getName(),
    // product.getQuantity().toString(),
    // product.getPrice().toString(),
    // product.getTaxRate().toString());
    // CSVHandler.writeMessageToCsv("productInventory.csv", productRecord);
    // }

    /*
     * Method to add a new product to the inventory.
     *
     * @param product The product to add.
     *
     */
    public static void updateProduct(String id, BigDecimal price, BigDecimal quantity, String name) {
        Product product = inventory.get(id);
        if (product != null) {
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setName(name);
        } else {
            System.out.println("GUI.Product not found in inventory.");
        }
    }

    public static boolean doesProductIDExist(String productID) throws DuplicateProductException {
        Product product = inventory.get(productID);
        if (product == null) {
            return false;
        } else
            throw new DuplicateProductException(
                    "Product with id " + product.getId() + " already exists. Please change the ID.");

        // return inventory.get(productID);
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

    /**
     * Method to remove a product from the inventory.
     * If the product exists, remove it and print a success message.
     * If the product does not exist, print a message indicating that the product
     * was not found.
     *
     * @param id The id of the product to remove.
     */
    public static void removeProduct(String id) {
        Product removedProduct = inventory.remove(id);
        if (removedProduct == null) {
            System.out.println("GUI.Product not found in inventory.");
        } else {
            System.out.println("GUI.Product Removed Successfully!");
        }
    }

    /**
     * Method to print all the products in the inventory.
     * Prints the id, name, quantity, price, and tax rate of each product.
     * Also prints the total number of products.
     */
    public static void printProducts() {
        System.out.println("Printing Inventory Products\n");
        System.out.format("%-10s%-20s%-10s%-10s%-10s", "ID", "Name", "Quantity", "Price", "Tax Rate");
        System.out.println("\n------------------------------------------------------------------------------");
        for (Product product : getInventory().getValues()) {
            // for (GUI.Product product : this.getInventory().values()) {
            System.out.format("%-10s%-20s%-10s%-10s%-10s", product.getId(), product.getName(), product.getQuantity(),
                    product.getPrice(), product.getTaxRate());
            System.out.println();
        }
        System.out.println("Showing " + Product.getProductCount() + " Products");

    }
}
