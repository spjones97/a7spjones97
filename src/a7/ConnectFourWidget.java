package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ConnectFourWidget extends JPanel implements ActionListener, SpotListener {

    private enum Player {RED, BLACK};

    private JSpotBoard _board;
    private JLabel _message;
    private boolean _gameWon;
    private Player _nextToPlay;

    public ConnectFourWidget() {

        // Create SpotBoard and messae label
        _board = new JSpotBoard(7, 6);
        _message = new JLabel();

        // Set layout and place SpotBoard at the center
        setLayout(new BorderLayout());
        add(_board, BorderLayout.CENTER);

        // Create SubPanel for message area and reset button
        JPanel resetMessagePanel = new JPanel();
        resetMessagePanel.setLayout(new BorderLayout());

        // Set reset button. Add ourselves as action listener
        JButton resetButton = new JButton("Restart");
        resetButton.addActionListener(this);
        resetMessagePanel.add(resetButton, BorderLayout.EAST);
        resetMessagePanel.add(_message, BorderLayout.CENTER);

        // Add SubPanel in south area of the layout
        add(resetMessagePanel, BorderLayout.SOUTH);

        // Add ourselves as spot listener for all spots on the spot board
        _board.addSpotListener(this);

        // Reset game
        resetGame();
    }

    private void resetGame() {
        // Clear all spots on the board
        for (Spot s : _board) {
            s.clearSpot();
            s.setSpotColor(new Color(0.8f, 0.8f, 0.8f));
        }

        // Reset gameWon and nextToPlay fields
        _gameWon = false;
        _nextToPlay = Player.RED;

        // Display game start message
        _message.setText("Welcome to Connect Four. Red to play");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handles reset button
        resetGame();
    }

    @Override
    public void spotClicked(Spot spot) {

    }

    @Override
    public void spotEntered(Spot spot) {
        // Check to see if column is full
        boolean isOpen = false;
        int xValue = spot.getSpotX();
        for (int i = 0; i < 6; i++) {
            if (_board.getSpotAt(xValue, i).isEmpty()) {
                isOpen = true;
            }
        }

        if (isOpen) {
            for (int i = 0; i < 6; i++) {
                _board.getSpotAt(xValue, i).highlightSpot();
            }
        }
    }

    @Override
    public void spotExited(Spot spot) {
        // Unhighlight spot
        int xValue = spot.getSpotX();
        for (int i = 0; i < 6; i++) {
            _board.getSpotAt(xValue, i).unhighlightSpot();
        }
    }
}
