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

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
