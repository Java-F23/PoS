package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.InputMismatchException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import exceptions.InvalidFormatException;
import model.InsufficientQuantityException;
import model.Product;
import model.ProductInventory;
import model.ShoppingCart;
import views.SalesScreen;

public class SalesButtonsController implements ActionListener {
    // JTable to display the products in the catalogue
    private JTable table;
    // responsible for handling the data and column definition for the table
    // private DefaultTableModel tableModel;
    // represents input data to the table
    // private Object[][] tableData;

    public SalesButtonsController(JTable table, DefaultTableModel tableModel,
            Object[][] tableData) {
        this.table = table;
        // this.tableModel = tableModel;
        // this.tableData = tableData;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "addToCart":
                addProductToCart();
                break;
            case "removeFromCart":
                removeProductFromCart();
                break;
            default:
                break;
        }
    }

    public void removeProductFromCart() {
        // Get the selected row in the table
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                // A row is selected
                // Get the selected row in the table
                String productId = table.getValueAt(selectedRow, 0).toString();
                String productName = table.getValueAt(selectedRow, 1).toString();
                BigDecimal quantity = new BigDecimal(table.getValueAt(selectedRow, 2).toString());
                BigDecimal price = new BigDecimal(table.getValueAt(selectedRow, 3).toString());
                BigDecimal taxRate = new BigDecimal(table.getValueAt(selectedRow, 4).toString());

                // Create a new Product object

                Product product = new Product(productId, productName, quantity, price, taxRate);

                // Remove the product from the cart
                ShoppingCart.removeProduct(product);

                // Calculate the total amount of the cart
                ShoppingCart.calculateTotal();


                // Update the order total label
                SalesScreen.updateOrderTotalLabel(ShoppingCart.getCartTotal().toString());

                // Update the cart table model and view to the user
                SalesScreen.updateCartTableModel(ShoppingCart.getCartItemsForTable());

                JOptionPane.showMessageDialog(null, "Removed product Successfully");

            } catch (InputMismatchException | IllegalArgumentException | InvalidFormatException err) {
                // If there's an error (e.g., input mismatch or illegal argument), show the
                // error message
                JOptionPane.showMessageDialog(null, err.getMessage());
            }

        } else {
            // No row is selected
            JOptionPane.showMessageDialog(null, "Error: Please select a Product.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // callback method for the addProduct button
    public void addProductToCart() {
        // Get the selected row in the table
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                // If a row is selected
                // Get the product ID and quantity from the selected row
                String productId = table.getValueAt(selectedRow, 0).toString();
                BigDecimal quantity = new BigDecimal(table.getValueAt(selectedRow, 2).toString());

                // Get the product from the inventory
                Product product = ProductInventory.getProductAvailability(productId, quantity);

                // Add the product to the cart
                ShoppingCart.addProduct(product, quantity);
                // Calculate the total amount of the cart
                ShoppingCart.calculateTotal();

                // Show a success message
                JOptionPane.showMessageDialog(null, "Added product Successfully");

                // Update the order total label
                SalesScreen.updateOrderTotalLabel(ShoppingCart.getCartTotal().toString());

                // Update the cart table model
                SalesScreen.updateCartTableModel(ShoppingCart.getCartItemsForTable());

            } catch (InsufficientQuantityException | InputMismatchException | NumberFormatException err) {
                JOptionPane.showMessageDialog(null, err.getMessage());
            }

        } else {
            // If no row is selected, show an error message
            JOptionPane.showMessageDialog(null, "Error: Please select a Product.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
