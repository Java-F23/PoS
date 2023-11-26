package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JOptionPane;

import model.Cashier;
import model.PaymentMethod;
import model.ProductInventory;
import model.ShoppingCart;
import views.InventoryScreen;
import views.SalesScreen;

public class MakePaymentController implements ActionListener {
    PaymentMethod paymentMethod;

    public MakePaymentController(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // String command = e.getActionCommand();
        if (ShoppingCart.isCartEmpty()) {

            System.out.println("Processing " + SalesScreen.getPaymentMethod() + " payment");
            // if Cart is empty
            JOptionPane.showMessageDialog(null, "Cart cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Cart is not empty and payment method is selected
            System.out.println("Processing " + paymentMethod + " payment");
            Cashier cashier = new Cashier(SalesScreen.getPaymentMethod());

            // generate receipt (write to csv)
            cashier.processPaymentAndGenerateReceipt();

            // Decrement the stock of the product(s) in the inventory
            ShoppingCart
                    .getCartItems()
                    .keySet()
                    .stream()
                    .forEach(product -> {
                        BigDecimal quantityInCart = ShoppingCart.getCartItems().get(product);
                        ProductInventory.decrementProductStock(product.getId(), quantityInCart);
                    });

            ProductInventory.overwriteInventoryToCsv();
            // Update the data in the catalogue table with the new stock

            Object[][] updatedInventory = ProductInventory.getInventoryForTable();
            SalesScreen.updateCatalogueTableData(updatedInventory);

            // Clear the cart, starting a new session
            ShoppingCart.clearCart();

            // update cart view
            SalesScreen.updateCartTableModel(new Object[0][0]);
            InventoryScreen.updateInventoryTableModel(updatedInventory);

            // Set the order total to zero
            SalesScreen.updateOrderTotalLabel(BigDecimal.ZERO.toString());

            JOptionPane.showMessageDialog(null,
                    "Thanks! You can view your receipt in the proj. directory (client_receipt.csv)", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
