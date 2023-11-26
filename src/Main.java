import views.MainFrame;
import exceptions.InvalidFormatException;
import model.ProductInventory;
import model.ShoppingCart;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws InvalidFormatException {
        ProductInventory productInventory = new ProductInventory();
        ShoppingCart cart = new ShoppingCart();
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame = new MainFrame("Point of Sale"); // Create an instance of MainFrame
                frame.setSize(1200, 800); // Set the size of the frame
                frame.setVisible(true); // Make the frame visible
            }
        });
    }
}
