package views;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

class WelcomeScreen extends JPanel {
    JButton continueButton;

    public WelcomeScreen() {
        // Set the layout manager for this panel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add a welcome message
        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24)); // Set the font size
        gbc.gridx = 0; // Set the x grid position
        gbc.gridy = 0; // Set the y grid position
        gbc.weighty = 1; // Give the label vertical space
        gbc.anchor = GridBagConstraints.CENTER; // Anchor the label to the top
        add(welcomeLabel, gbc);

        // Add a "Continue" button
        continueButton = new JButton("Continue");


        gbc.gridy = 1; // Set the y grid position
        gbc.weighty = 0; // Reset the vertical space
        gbc.insets = new Insets(0, 0, 300, 0); // Add bottom margin
        gbc.anchor = GridBagConstraints.CENTER; // Anchor the button to the center

        add(continueButton, gbc);
    }
}
