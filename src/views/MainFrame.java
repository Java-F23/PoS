package views;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
// import GUI.HomeScreen;
import views.WelcomeScreen;

public class MainFrame extends JFrame {
    CardLayout cardLayout;

    public MainFrame(String title) {
        super(title);
        // Create a CardLayout for the main frame
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Create and add the welcome screen
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        add(welcomeScreen, "WelcomeScreen");

        HomeScreen homeScreen = new HomeScreen();
        add(homeScreen, "HomeScreen");

        // Add an ActionListener to the "Continue" button
        welcomeScreen.continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the next screen when the "Continue" button is clicked
                cardLayout.show(getContentPane(), "HomeScreen");
                // cardLayout.next(getContentPane(), "HomeScreen");
            }
        });

        // Create and add other screens
        // OtherScreen otherScreen = new OtherScreen();
        // add(otherScreen, "OtherScreen");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

// class WelcomeScreen extends JPanel {
// JButton continueButton;

// public WelcomeScreen() {
// // Set the layout manager for this panel
// setLayout(new GridBagLayout());
// GridBagConstraints gbc = new GridBagConstraints();

// // Add a welcome message
// JLabel welcomeLabel = new JLabel("Welcome!");
// welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24)); // Set the font size
// gbc.gridx = 0; // Set the x grid position
// gbc.gridy = 0; // Set the y grid position
// gbc.weighty = 1; // Give the label vertical space
// gbc.anchor = GridBagConstraints.CENTER; // Anchor the label to the top
// add(welcomeLabel, gbc);

// // Add a "Continue" button
// continueButton = new JButton("Continue");

// // continueButton.setMargin(new Insets(0, 0, 20, 0)); // Add bottom margin

// gbc.gridy = 1; // Set the y grid position
// gbc.weighty = 0; // Reset the vertical space
// gbc.insets = new Insets(0, 0, 300, 0); // Add bottom margin
// gbc.anchor = GridBagConstraints.CENTER; // Anchor the button to the center

// add(continueButton, gbc);
// }
// }
