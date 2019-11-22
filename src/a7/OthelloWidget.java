package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class OthelloWidget extends JPanel implements ActionListener, SpotListener {

    private enum Player {WHITE, BLACK};

    private JSpotBoard _board;
    private JLabel _message;
    private boolean _gameWon;
    private Player _nextToPlay;

    public OthelloWidget() {
        // Create SpotBoard and message label
        _board = new JSpotBoard(6, 6);
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
            s.setSpotColor(s.getBackground());
        }

        for (int col = 0; col < _board.getSpotWidth(); col++) {
            for (int row = 0; row < _board.getSpotHeight(); row++) {
                if (col == (_board.getSpotWidth() / 2 - 1) &&
                    row == (_board.getSpotHeight() / 2 - 1)) {
                    _board.getSpotAt(col, row).setSpotColor(Color.WHITE);
                    _board.getSpotAt(col, row).toggleSpot();
                }
                if (col == (_board.getSpotWidth() / 2) &&
                        row == (_board.getSpotHeight() / 2)) {
                    _board.getSpotAt(col, row).setSpotColor(Color.WHITE);
                    _board.getSpotAt(col, row).toggleSpot();
                }
                if (col == (_board.getSpotWidth() / 2) &&
                        row == (_board.getSpotHeight() / 2 - 1)) {
                    _board.getSpotAt(col, row).setSpotColor(Color.BLACK);
                    _board.getSpotAt(col, row).toggleSpot();
                }
                if (col == (_board.getSpotWidth() / 2 - 1) &&
                        row == (_board.getSpotHeight() / 2)) {
                    _board.getSpotAt(col, row).setSpotColor(Color.BLACK);
                    _board.getSpotAt(col, row).toggleSpot();
                }
            }
        }

        // Reset gameWont and nextToPlay fields
        _gameWon = false;
        _nextToPlay = Player.BLACK;

        // Display game start message
        _message.setText("Welcome to Othello. Black to play");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handles reset button
        resetGame();
    }

    @Override
    public void spotClicked(Spot spot) {
        // If game already won, do nothing
        if (_gameWon) {
            return;
        }

        // Set up player and next player name strings
        // Set up player color as local variables to be used later
        String playerName = null;
        String nextPlayerName = null;
        Color playerColor = null;

        if (_nextToPlay == Player.WHITE) {
            playerColor = Color.WHITE;
            playerName = "White";
            nextPlayerName = "Black";
            _nextToPlay = Player.BLACK;
        } else {
            playerColor = Color.BLACK;
            playerName = "Black";
            nextPlayerName = "White";
            _nextToPlay = Player.WHITE;
        }

        // Set color of spot clicked and toggle
        if (spot.isEmpty()) {
            spot.setSpotColor(playerColor);
            spot.toggleSpot();
        } else {
            return;
        }
    }

    @Override
    public void spotEntered(Spot spot) {
        // Highlight spot if game is not over
        if (spot.isEmpty() && !_gameWon) {
            spot.highlightSpot();
        } else {
            return;
        }
    }

    @Override
    public void spotExited(Spot spot) {
        // Unhighlight spot
        spot.unhighlightSpot();
    }
}