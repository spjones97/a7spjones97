package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TicTacToeWidget extends JPanel implements ActionListener, SpotListener {

    private enum Player {BLACK, WHITE};

    private JSpotBoard _board;      // playing area
    private JLabel _message;        // label for messages
    private boolean _gameWon;      // indicates if game has been won already
    private Player _nextToPLay;   // identifies who has the next turn

    public TicTacToeWidget() {

        // Create SpotBoard and message label
        _board = new JSpotBoard(3, 3);
        _message = new JLabel();

        // Set layout and place SpotBoard at the center
        setLayout(new BorderLayout());
        add(_board, BorderLayout.CENTER);

        // Create SubPanel for message area and reset button
        JPanel resetMessagePanel = new JPanel();
        resetMessagePanel.setLayout(new BorderLayout());

        // Set reset button. Add ourselves as the action listener
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

        // Reset game won and next to play fields
        _gameWon = false;
        _nextToPLay = Player.WHITE;

        // Display game start message
        _message.setText("Welcome to Tic Tac Toe. White to play");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handles reset game button game. Simply resets the game
        resetGame();
    }

    @Override
    public void spotClicked(Spot spot) {
        // If game already won, do nothing
        if (_gameWon) {
            return;
        }

        // Set up player and next player name strings,
        // Set up player color as local variables to be used later

        String playerName = null;
        String nextPlayerName = null;
        Color playerColor = null;

        if (_nextToPLay == Player.WHITE) {
            playerColor = Color.WHITE;
            playerName = "White";
            nextPlayerName = "Black";
            _nextToPLay = Player.BLACK;
        } else {
            playerColor = Color.BLACK;
            playerName = "Black";
            nextPlayerName = "White";
            _nextToPLay = Player.WHITE;
        }

        // Set color of spot clicked and toggle
        if (spot.isEmpty()) {
            spot.setSpotColor(playerColor);
            spot.toggleSpot();
        } else {
            return;
        }

        // Check to see if player has three spots in a row
        for (int i = 0; i < 3; i++) {
            if (_board.getSpotAt(i, 0).getSpotColor() == playerColor &&
                    _board.getSpotAt(i, 1).getSpotColor() == playerColor &&
                    _board.getSpotAt(i, 2).getSpotColor() == playerColor) {
                _gameWon = true;
            }
            if (_board.getSpotAt(0, i).getSpotColor() == playerColor &&
                    _board.getSpotAt(1, i).getSpotColor() == playerColor &&
                    _board.getSpotAt(2, i).getSpotColor() == playerColor) {
                _gameWon = true;
            }
            if (_board.getSpotAt(0, 0).getSpotColor() == playerColor &&
                    _board.getSpotAt(1, 1).getSpotColor() == playerColor &&
                    _board.getSpotAt(2, 2).getSpotColor() == playerColor) {
                _gameWon = true;
            }
            if (_board.getSpotAt(0, 2).getSpotColor() == playerColor &&
                    _board.getSpotAt(1, 1).getSpotColor() == playerColor &&
                    _board.getSpotAt(2, 0).getSpotColor() == playerColor) {
                _gameWon = true;
            }
        }

        if (_gameWon) {
            _message.setText(playerName + " got three in a row. Game over.");
        } else {
            _message.setText(nextPlayerName + " to play.");
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
