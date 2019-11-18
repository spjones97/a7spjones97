package a7;

import javax.swing.*;
import java.awt.*;

public class OthelloGame {
    public static void main(String[] args) {
        // Create top level window
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Othello");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create panel for content that uses BorderLayout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        mainFrame.setContentPane(topPanel);

        // Create OthelloWidget component in center of content panel
        OthelloWidget othelloWidget = new OthelloWidget();
        topPanel.add(othelloWidget, BorderLayout.CENTER);

        // Pack main frame and make visible
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
