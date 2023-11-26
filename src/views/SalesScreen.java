package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controllers.MakePaymentController;
import controllers.PaymentButtonsController;
import controllers.SalesButtonsController;
import exceptions.InvalidFormatException;
import model.PaymentMethod;
import model.Product;
import model.ProductInventory;
import model.ShoppingCart;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.InputMismatchException;

/*
The SalesScreen class is a custom panel that represents the sales screen of a retail application.
It contains several components such as two JTable objects for displaying the catalogue and cart items,
a JLabel for showing the total order amount, and a JPanel for the payment options. The payment options
are represented by three JRadioButton objects for Visa, Cash, and Mobile Payment methods.
There's also a JButton for making the payment.

The SalesScreen class also includes several ActionListener objects. These listeners are responsible for
setting the selected payment method and validating and finalizing the transaction when the "Make Payment" button is clicked.
If the cart is empty or no payment method is selected, an error message is displayed. Otherwise, a success message is shown,
the inventory is deducted, and a receipt is generated.

The SalesScreen class extends JPanel, which means it can be added to other Swing containers like JFrame or JDialog.
 The SalesScreen class is a part of the Model-View-Controller (MVC) design pattern, where it acts as the view component.
  It displays the data (catalogue and cart items, total order amount) and captures the user's actions
  (selecting a payment method, making a payment). The actual data manipulation (adding/removing items from the cart,
  calculating the total amount) is handled by the model component, which is not shown in the provided code.
*/

public class SalesScreen extends JPanel {
    public static final String[] COLUMN_NAMES = { "PID", "Name", "Quantity", "Price", "Tax Rate" };

    // JTable to display the products in the catalogue
    private static JTable catalogueTable;
    // responsible for handling the data and column definition for the table
    private static DefaultTableModel catalogueTableModel;

    // JTable to display the products in the cart
    private static JTable cartTable;
    private static DefaultTableModel cartTableModel;
    private static Object[][] cartTableData;

    private static BigDecimal orderTotal;
    // JLabel to display the total amount of the order
    private static JLabel orderTotalLabel;
    private static PaymentMethod paymentMethod;

    public SalesScreen() {
        orderTotal = BigDecimal.ZERO;
        // Create a GridBagLayout for this panel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add a JLabel to the first column
        gbc.gridx = 0; // Set the x grid position
        gbc.gridy = 0; // Set the y grid position
        gbc.weightx = 0.5; // Give the first column 30% of the width
        gbc.fill = GridBagConstraints.CENTER; // Make the JLabel fill its display
        // gbc.insets = new Insets(0, 0, 0, 0); // No padding
        add(new JLabel("Product Catalogue"), gbc);

        // Create a JTable for the first column
        Object[][] data = ProductInventory.getInventoryForTable(); // Replace with your data

        catalogueTableModel = new DefaultTableModel(data, COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable except for the quantity column
                return column == 2;

            }
        };

        catalogueTable = new JTable(catalogueTableModel);
        catalogueTable.setToolTipText("You can change the quantity of your product");

        // Add the JTable to the first column under the JLabel
        gbc.gridy = 1; // Set the y grid position
        add(new JScrollPane(catalogueTable), gbc); // Add the JTable to a JScrollPane to enable scrolling

        gbc.gridx = 0; // Set the x grid position
        gbc.gridy = 2; // Set the y grid position

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setToolTipText("Add the selected product to the cart");
        addToCartButton.setActionCommand("addToCart");
        addToCartButton.addActionListener(
                new SalesButtonsController(catalogueTable, catalogueTableModel, data));
        add(addToCartButton, gbc); // Add the Add button to this panel

        // SECOND COLUMN

        // Add a JLabel to the second column
        gbc.gridx = 1; // Set the x grid position
        gbc.gridy = 0; // Set the y grid position
        gbc.weightx = 0.5; // Give the second column 70% of the width
        gbc.fill = GridBagConstraints.BASELINE; // Make the JLabel fill its display
        gbc.anchor = GridBagConstraints.WEST;

        add(new JLabel("Cart"), gbc);
        // Create a JTable for the first column
        Object[][] cartData = ShoppingCart.getCartItemsForTable(); // Replace with your data

        cartTableModel = new DefaultTableModel(cartData, COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };
        cartTable = new JTable(cartTableModel);
        // Add the JTable to the first column under the JLabel
        gbc.gridy = 1; // Set the y grid position
        add(new JScrollPane(cartTable), gbc); // Add the JTable to a JScrollPane to enable scrolling

        gbc.gridx = 1; // Set the x grid position
        gbc.gridy++;
        gbc.gridy++;
        gbc.gridy++;

        JButton removeProductButton = new JButton("Remove Product");
        removeProductButton.setBackground(Color.decode("#E84855")); // Set the background color to #E84855
        removeProductButton.setForeground(Color.WHITE); // Set the text color to white
        removeProductButton.setToolTipText("Remove the selected product from your cart.");
        removeProductButton.setActionCommand("removeFromCart");
        removeProductButton.addActionListener(
                new SalesButtonsController(cartTable, cartTableModel, cartTableData));

        add(removeProductButton, gbc); // Add the Remove Product button to this panel

        gbc.gridx = 1; // Set the x grid position
        gbc.gridy++;
        gbc.gridy++;
        gbc.gridy++;

        orderTotalLabel = new JLabel("Total: " + getOrderTotal() + " EGP");

        add(orderTotalLabel, gbc);

        gbc.gridx = 1; // Set the x grid position
        gbc.gridy++;
        // Create a JPanel with a FlowLayout
        JPanel paymentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paymentPanel.setToolTipText("Select the payment method.");
        // Create a ButtonGroup
        ButtonGroup paymentGroup = new ButtonGroup();

        // Create a JRadioButton for each payment option
        JRadioButton visaButton = new JRadioButton("Visa");
        visaButton.setActionCommand("visa");
        JRadioButton cashButton = new JRadioButton("Cash");
        cashButton.setActionCommand("cash");
        JRadioButton mobilePaymentButton = new JRadioButton("Mobile");
        mobilePaymentButton.setActionCommand("mobile");

        cashButton.setSelected(true);
        setPaymentMethod(PaymentMethod.CASH);

        // Add each JRadioButton to the ButtonGroup
        paymentGroup.add(visaButton);
        paymentGroup.add(cashButton);
        paymentGroup.add(mobilePaymentButton);

        // Add an ActionListener to each JRadioButton to update the paymentMethod
        // attribute
        visaButton.addActionListener(new PaymentButtonsController());
        cashButton.addActionListener(new PaymentButtonsController());
        mobilePaymentButton.addActionListener(new PaymentButtonsController());

        // Add each JRadioButton to the JPanel
        paymentPanel.add(visaButton);
        paymentPanel.add(cashButton);
        paymentPanel.add(mobilePaymentButton);

        // Add the JPanel to the panel
        add(paymentPanel, gbc);

        gbc.gridx = 1; // Set the x grid position
        gbc.gridy++;
        gbc.gridy++;
        gbc.gridy++;
        gbc.gridy++;

        // Create a JButton with the text "Make Payment!"
        JButton makePaymentButton = new JButton("Make Payment!");

        // Set the preferred size of the JButton to a specific width
        makePaymentButton.setPreferredSize(new Dimension(300, makePaymentButton.getPreferredSize().height));
        makePaymentButton.setBackground(Color.decode("#B5FED9")); // Set the background color to #E84855
        makePaymentButton.setToolTipText("Finalize Transaction and Generate Receipt");
        makePaymentButton.setActionCommand("makePayment");
        makePaymentButton.addActionListener(
                new MakePaymentController(paymentMethod));
        add(makePaymentButton, gbc);

    }

    /**
     * Getter for the payment method.
     * 
     * @return the selected payment method.
     */
    public static PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Setter for the payment method.
     * 
     * @param _paymentMethod the new payment method.
     */
    public static void setPaymentMethod(PaymentMethod _paymentMethod) {
        paymentMethod = _paymentMethod;
    }

    /**
     * Getter for the total order amount.
     * 
     * @return the total order amount.
     */
    public static BigDecimal getOrderTotal() {
        return orderTotal;
    }

    /**
     * Updates the catalogue table with new data.
     * 
     * @param catalogueData the new data for the catalogue table.
     */
    public static void updateCatalogueTableData(Object[][] catalogueData) {
        catalogueTableModel.setDataVector(catalogueData, COLUMN_NAMES);
    }

    /**
     * Updates the cart table with new data.
     * 
     * @param cartItems the new data for the cart table.
     */
    public static void updateCartTableModel(Object[][] cartItems) {
        cartTableModel.setDataVector(cartItems, COLUMN_NAMES);
    }

    /**
     * Updates the order total label with a new total amount.
     * 
     * @param orderTotal the new total amount.
     */
    public static void updateOrderTotalLabel(String orderTotal) {
        orderTotalLabel.setText("Total: " + orderTotal + " EGP");
    }

}
