package controllers;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.InputMismatchException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

// import GUI.SalesScreen;
import exceptions.DuplicateProductException;
import exceptions.InvalidFormatException;
import model.Product;
import model.ProductInventory;
import views.SalesScreen;

/**
 * The InventoryButtonsController class is an implementation of the
 * ActionListener interface.
 * It's responsible for handling button click events in the inventory screen of
 * a retail application.
 * It contains several fields, including an array of JTextField objects for
 * input fields,
 * a DefaultTableModel object for handling the data and column definition for
 * the inventory table,
 * and a JTable object for displaying the inventory items.
 *
 * The InventoryButtonsController class has a constructor that initializes these
 * fields.
 * It also overrides the actionPerformed method from the ActionListener
 * interface.
 * This method is called when a button is clicked. It gets the action command of
 * the event,
 * checks if it's "createNewProduct", and if so, calls the createNewProduct
 * method.
 */
public class InventoryButtonsController implements ActionListener {
    private JTextField[] textFields;

    private DefaultTableModel tableModel;
    private JTable inventoryTable;

    /**
     * Constructor for the InventoryButtonsController class.
     * It initializes the textFields, tableModel, and inventoryTable fields.
     *
     * @param textFields     the array of JTextField objects for input fields.
     * @param tableModel     the DefaultTableModel object for handling the data and
     *                       column definition for the inventory table.
     * @param inventoryTable the JTable object for displaying the inventory items.
     */
    public InventoryButtonsController(JTextField[] textFields, DefaultTableModel tableModel, JTable inventoryTable) {
        this.textFields = textFields;
        this.tableModel = tableModel;
        this.inventoryTable = inventoryTable;
    }

    /**
     * Handles button click events.
     * This method is called when a button is clicked. It gets the action command of
     * the event,
     * checks if it's "createNewProduct", and if so, calls the createNewProduct
     * method.
     *
     * @param e the ActionEvent object generated by the button click event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "createNewProduct":
                System.out.println("Handle Add New Product here!");

                createNewProduct();

                // addProductToCart();
                break;
            default:
                break;
        }
    }

    /**
     * Creates a new product and adds it to the inventory.
     * This method gets the text from each text field, checks if a product with the
     * same ID already exists,
     * creates a new product with the input data, adds the product to the inventory,
     * adds a new row to the table model with the product data, and updates the
     * table and the catalogue table.
     * If an exception occurs during this process, it shows a dialogue with the
     * exception message.
     */
    private void createNewProduct() {
        // Get the text from each textfield
        String[] row = Arrays.stream(textFields)
                .map(JTextField::getText)
                .toArray(String[]::new);

        try {
            ProductInventory.doesProductIDExist(textFields[0].getText());

            Product newProduct = new Product(textFields[0].getText(),
                    textFields[1].getText(),
                    new BigDecimal(textFields[2].getText()),
                    new BigDecimal(textFields[3].getText()),
                    new BigDecimal(textFields[4].getText()));
            // Add the new row to the table model
            ProductInventory.addProduct(newProduct);
            ProductInventory.overwriteInventoryToCsv();
            
            tableModel.addRow(row);
            // Update the table
            inventoryTable.setModel(tableModel);
            // update the PoS catalogue Table
            SalesScreen.updateCatalogueTableData(ProductInventory.getInventoryForTable());
        } catch (IllegalArgumentException | InputMismatchException | DuplicateProductException
                | InvalidFormatException err) {
            // Output the row in a dialogue
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
}
