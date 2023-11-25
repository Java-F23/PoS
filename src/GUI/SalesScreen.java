package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controllers.MakePaymentController;
import controllers.PaymentButtonsController;
import controllers.SalesButtonsController;

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
    // represents input data to the table
    private static Object[][] catalogueTableData;

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
                // Make all cells non-editable except for the quantity
                return column == 2;

            }
        };
        // catalogueTable.getColumnModel().getColumn(2).setToolTipText("Adjust the
        // quantity of your product");
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
        // gbc.gridy = 2; // Set the y grid position
        gbc.gridy++;
        gbc.gridy++;
        gbc.gridy++;
        // gbc.gridy++;
        // gbc.gridy++;

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
        // orderTotalLabel.setText("Total: " + getOrderTotal() + " EGP");
        // add(new JLabel("Total: " + getOrderTotal() + " EGP"), gbc);
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
        // orderTotal = BigDecimal.TEN;

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
        // makePaymentButton.setForeground(Color.WHITE); // Set the text color to white
        makePaymentButton.setToolTipText("Finalize Transaction and Generate Receipt");
        makePaymentButton.setActionCommand("makePayment");
        makePaymentButton.addActionListener(
            new MakePaymentController(paymentMethod)
            // new ActionListener() {
            // @Override
            // public void actionPerformed(ActionEvent e) {
            //     if (ShoppingCart.isCartEmpty()) {
            //         // if Cart is empty
            //         JOptionPane.showMessageDialog(null, "Cart cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            //     } else {
            //         // Cart is not empty and payment method is selected
            //         Cashier cashier = new Cashier(getPaymentMethod());

            //         // generate receipt (write to csv)
            //         cashier.processPaymentAndGenerateReceipt();

            //         // Decrement the stock of the product(s) in the inventory
            //         for (GUI.Product product : ShoppingCart.getCartItems().getKeys()) {
            //             BigDecimal quantityInCart = ShoppingCart.getCartItems().get(product);
            //             ProductInventory.decrementProductStock(product.getId(), quantityInCart);
            //         }
            //         // Update the data in the catalogue table with the new stock
            //         updateCatalogueTableData();

            //         // Clear the cart, starting a new session
            //         ShoppingCart.clearCart();

            //         // update cart view
            //         // updateCartTableModel();

            //         // Set the order total to zero
            //         setOrderTotal(BigDecimal.ZERO);
            //         updateOrderTotalLabel(orderTotalLabel);

            //         JOptionPane.showMessageDialog(null,
            //                 "Thanks! You can view your receipt in the proj. directory (client_receipt.csv)", "Success",
            //                 JOptionPane.INFORMATION_MESSAGE);
            //     }
            // }
        // }
        );
        add(makePaymentButton, gbc);

    }

    public static PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public static void setPaymentMethod(PaymentMethod _paymentMethod) {
        // PaymentMethod tempPaymentMethod = PaymentMethod.CASH;
        // tempPaymentMethod = GUI.PaymentMethod.fromString(_paymentMethod);
        paymentMethod = _paymentMethod;
    }

    public static BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public static void setOrderTotal(BigDecimal _orderTotal) {
        orderTotal = _orderTotal;
    }

    public Object[][] getCatalogueTableData() {
        return catalogueTableData;
    }

    public static void setCatalogueTableData(Object[][] _catalogueTableData) {
        catalogueTableData = _catalogueTableData;
    }

    // updates the catalogueTable with the new items; ensures dynamic reloading
    public static void updateCatalogueTableData(Object[][] catalogueData) {
        // setCatalogueTableData(ProductInventory.getInventoryForTable());
        // What to do after?
        catalogueTableModel.setDataVector(catalogueData, COLUMN_NAMES);
    }
    // public static void updateCatalogueTableData() {
    // setCatalogueTableData(ProductInventory.getInventoryForTable());
    // // What to do after?
    // catalogueTableModel.setDataVector(catalogueTableData, COLUMN_NAMES);
    // }

    public DefaultTableModel getCartTableModel() {
        return cartTableModel;
    }

    public void setCartTableModel(DefaultTableModel _cartTableModel) {
        cartTableModel = _cartTableModel;
    }

    public static void setCartTableData(Object[][] _cartTableData) {
        cartTableData = _cartTableData;
    }

    // updates the cartTable with the new items; ensures dynamic reloading
    public static void updateCartTableModel(Object[][] cartItems) {
        // setCartTableData(ShoppingCart.getCartItemsForTable());
        cartTableModel.setDataVector(cartItems, COLUMN_NAMES);

    }

    public static Object[][] getCartTableData() {
        return cartTableData;
    }

    // updates the orderTotalLabel whenever a product is added, removed, or a
    // transaction completes
    public static void updateOrderTotalLabel(String orderTotal) {
        orderTotalLabel.setText("Total: " + orderTotal + " EGP");
    }

    // // callback method for the addProduct button
    // public static void addProductToCart() {
    // // Get the selected row in the catalogueTable
    // int selectedRow = catalogueTable.getSelectedRow();
    // if (selectedRow != -1) {
    // try{
    // // If a row is selected
    // // Get the product ID and quantity from the selected row
    // String productId = catalogueTable.getValueAt(selectedRow, 0).toString();
    // BigDecimal quantity = new BigDecimal(catalogueTable.getValueAt(selectedRow,
    // 2).toString());

    // // Get the product from the inventory
    // Product product = ProductInventory.getProductAvailability(productId,
    // quantity);

    // // Add the product to the cart
    // ShoppingCart.addProduct(product,quantity);

    // // Calculate the total amount of the cart
    // ShoppingCart.calculateTotal();

    // // Show a success message
    // JOptionPane.showMessageDialog(null, "Added product Successfully");

    // // Update the order total
    // setOrderTotal(ShoppingCart.getCartTotal());

    // // Update the order total label
    // updateOrderTotalLabel(orderTotalLabel);

    // // Update the cart table model
    // updateCartTableModel();

    // }
    // catch(InsufficientQuantityException | InputMismatchException err){
    // // If there's an error (e.g., insufficient quantity or input mismatch), show
    // the error message
    // JOptionPane.showMessageDialog(null, err.getMessage());
    // }

    // } else {
    // // If no row is selected, show an error message
    // JOptionPane.showMessageDialog(null, "Error: Please select a Product.",
    // "Error", JOptionPane.ERROR_MESSAGE);
    // }
    // }
    public static void removeProductFromCart() {
        // Get the selected row in the cartTable
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                // A row is selected
                // Get the selected row in the cartTable
                String productId = cartTable.getValueAt(selectedRow, 0).toString();
                String productName = cartTable.getValueAt(selectedRow, 1).toString();
                BigDecimal quantity = new BigDecimal(cartTable.getValueAt(selectedRow, 2).toString());
                BigDecimal price = new BigDecimal(cartTable.getValueAt(selectedRow, 3).toString());
                BigDecimal taxRate = new BigDecimal(cartTable.getValueAt(selectedRow, 4).toString());

                // Create a new Product object

                Product product = new Product(productId, productName, quantity, price, taxRate);

                // Remove the product from the cart
                ShoppingCart.removeProduct(product);

                // Calculate the total amount of the cart
                ShoppingCart.calculateTotal();

                // Update the order total
                setOrderTotal(ShoppingCart.getCartTotal());

                // Update the order total label
                // updateOrderTotalLabel(orderTotalLabel);

                // Update the cart table model and view to the user
                // updateCartTableModel();

                JOptionPane.showMessageDialog(null, "Removed product Successfully");

            } catch (InputMismatchException | IllegalArgumentException err) {
                // If there's an error (e.g., input mismatch or illegal argument), show the
                // error message
                JOptionPane.showMessageDialog(null, err.getMessage());
            }

        } else {
            // No row is selected
            JOptionPane.showMessageDialog(null, "Error: Please select a Product.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
