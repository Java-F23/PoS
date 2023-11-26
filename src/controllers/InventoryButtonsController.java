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

public class InventoryButtonsController implements ActionListener {
    private JTextField[] textFields;

    private DefaultTableModel tableModel;
    private JTable inventoryTable;

    public InventoryButtonsController(JTextField[] textFields, DefaultTableModel tableModel, JTable inventoryTable) {
        this.textFields = textFields;
        this.tableModel = tableModel;
        this.inventoryTable = inventoryTable;
    }

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
