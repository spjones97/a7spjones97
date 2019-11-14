package a7;

import javax.swing.*;
import java.awt.*;

public class TicTacToeGame {
    public static void main(String[] args) {

        // Create top level window
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Tic Tac Toe");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create panel for content that uses BorderLayout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        mainFrame.setContentPane(topPanel);

        // Create TicTacToeWidget component in center of content panel
        TicTacToeWidget ticTacToeWidget = new TicTacToeWidget();
        topPanel.add(ticTacToeWidget, BorderLayout.CENTER);

        // Pack main frame and make visible
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
