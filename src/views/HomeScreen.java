package views;

import javax.swing.*;

import java.awt.*;


public class HomeScreen extends JPanel {

        public HomeScreen() {
                // Create a BorderLayout for this panel
                setLayout(new BorderLayout());
                // Create a tabbed pane
                JTabbedPane tabbedPane = new JTabbedPane();

                // Create a Sales panel
                // JPanel salesPanel = new SalesScreen();
                JPanel salesPanel = new SalesScreen();
                // salesPanel.add(new JLabel("Sales Screen"));

                // Create an Inventory panel
                JPanel inventoryPanel = new InventoryScreen();
                // inventoryPanel.add(new JLabel("Inventory Screen"));

                // Add the panels to the tabbed pane
                tabbedPane.addTab("Sales", salesPanel);
                tabbedPane.addTab("Inventory", inventoryPanel);

                // Add the tabbed pane to this panel
                add(tabbedPane, BorderLayout.CENTER);
        }
}
