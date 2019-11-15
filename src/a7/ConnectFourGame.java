package a7;

import javax.swing.*;
import java.awt.*;

public class ConnectFourGame {
    public static void main(String[] args) {

        // Create top level window
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Connect Four");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create panel for content that uses BorderLayout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        mainFrame.setContentPane(topPanel);

        // Create ConnectFourWidget component in center of content panel
        ConnectFourWidget connectFourWidget = new ConnectFourWidget();
        topPanel.add(connectFourWidget, BorderLayout.CENTER);

        // Pack main frame and make visible
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
