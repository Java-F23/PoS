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

import GUI.Product;
import GUI.ProductInventory;
import GUI.SalesScreen;
import exceptions.DuplicateProductException;

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
            case "removeProduct":
                System.out.println("Handle Remove Product from Cart here!");
                // removeProductFromCart();
                break;
            case "createNewProduct":
                System.out.println("Handle Add New Product here!");
                // textFields.forEach(field -> System.out.println("Field Value: ", field));
                // Arrays
                // .stream(textFields)
                // .forEach(textField -> {
                // System.out.println("Field Value: " + textField.getText());
                // });

                createNewProduct();

                // addProductToCart();
                break;
            // add more cases for other buttons
        }
    }

    private void createNewProduct() {
        // Get the text from each textfield
        String[] row = new String[textFields.length];
        for (int i = 0; i < textFields.length; i++) {
            row[i] = textFields[i].getText();
        }
        try {
            ProductInventory.doesProductIDExist(textFields[0].getText());
            // Add the new row to the table model
            ProductInventory.addProduct(new Product(textFields[0].getText(),
                    textFields[1].getText(),
                    new BigDecimal(textFields[2].getText()),
                    new BigDecimal(textFields[3].getText()),
                    new BigDecimal(textFields[4].getText())));
            // System.out.println("row var " + row);
            tableModel.addRow(row);
            // Update the table
            inventoryTable.setModel(tableModel);
            // update the PoS catalogue Table
            SalesScreen.updateCatalogueTableData(ProductInventory.getInventoryForTable());
        } catch (IllegalArgumentException | InputMismatchException | DuplicateProductException err) {
            // Output the row in a dialogue
            JOptionPane.showMessageDialog(null, err.getMessage());
            // JOptionPane.showMessageDialog(null, String.join(", ", row));
        }
    }
}
