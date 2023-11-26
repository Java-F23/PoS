package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import controllers.InventoryButtonsController;
import model.ProductInventory;

public class InventoryScreen extends JPanel {
    public static final String[] COLUMN_NAMES = { "PID", "Name", "Quantity", "Price", "Tax Rate" };
    private JTextField[] textFields = new JTextField[5]; // Array to store the textfields
    private static DefaultTableModel tableModel;
    private JTable inventoryTable;

    public InventoryScreen() {
        // Create a GridBagLayout for this panel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create a JPanel for the textboxes
        JPanel textboxPanel = new JPanel();
        textboxPanel.setLayout(new GridLayout(5, 1, 24, 0)); // 5 rows, 1 column, 24px vertical spacing
        textboxPanel.setToolTipText("Define a New Product");

        // Add 5 textboxes to the textboxPanel
        for (int i = 0; i < COLUMN_NAMES.length; i++) {
            JPanel pairPanel = new JPanel(new BorderLayout());
            pairPanel.add(new JLabel(COLUMN_NAMES[i]), BorderLayout.NORTH);
            textFields[i] = new JTextField(20);
            pairPanel.add(textFields[i], BorderLayout.CENTER);
            textboxPanel.add(pairPanel);
        }

        // Add the textboxPanel to this panel
        gbc.gridx = 0; // Set the x grid position
        gbc.gridy = 0; // Set the y grid position
        gbc.weightx = 0.3; // Give the textboxPanel half the horizontal space
        gbc.weighty = 0.3; // Give the textboxPanel all the vertical space
        gbc.fill = GridBagConstraints.CENTER; // Make the textboxPanel fill its display area
        add(textboxPanel, gbc);

        // Create a JTable for the inventory
        Object[][] data = ProductInventory.getInventoryForTable(); // Replace with your data

        tableModel = new DefaultTableModel(data, COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };
        inventoryTable = new JTable(tableModel);
        inventoryTable.setToolTipText("View Inventory");

        // Add the inventoryTable to this panel
        gbc.gridx = 1; // Set the x grid position
        gbc.weightx = 0.7; // Give the inventoryTable half the horizontal space
        gbc.gridy = 0; // Set the y grid position
        gbc.fill = GridBagConstraints.BOTH; // Make the textboxPanel fill its display area
        add(new JScrollPane(inventoryTable), gbc); // Add the inventoryTable to a JScrollPane to enable scrolling

        // Create an Add button
        JButton addButton = new JButton("Add Product");
        // Add an ActionListener to the Add button
        addButton.setToolTipText("Add Product to Inventory");
        addButton.setActionCommand("createNewProduct");
        addButton.addActionListener(
                new InventoryButtonsController(textFields, tableModel, inventoryTable));

        // Add the Add button to this panel
        gbc.gridx = 0; // Set the x grid position
        gbc.gridy = 1; // Set the y grid position
        gbc.weightx = 1; // Give the Add button the full horizontal space
        gbc.weighty = 0; // Reset the vertical space
        gbc.gridwidth = 2; // Make the Add button span both columns

        add(addButton, gbc); // Add the Add button to this panel
    }

    /**
     * Updates the inventory table with new data.
     * This method is used to dynamically update the inventory table with new data.
     * It takes an array of arrays (2D array) as a parameter, where each inner array
     * represents a row in the table.
     * The method uses the setDataVector method of the DefaultTableModel class to
     * set the new data for the table.
     *
     * @param inventoryItems the new data for the inventory table. Each inner array
     *                       represents a row in the table.
     */
    public static void updateInventoryTableModel(Object[][] inventoryItems) {
        tableModel.setDataVector(inventoryItems, COLUMN_NAMES);

    }
}
