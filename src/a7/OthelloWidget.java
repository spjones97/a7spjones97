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
        _board = new JSpotBoard(4, 4);
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

        if (!isValidBoolean(spot)) {
            return;
        }

        // Change players if valid spot exists
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

        // Set color of valid spot clicked and toggle
        if (spot.isEmpty() && isValidBoolean(spot)) {
            spot.setSpotColor(playerColor);
            spot.toggleSpot();
        }

        // Flip spots
        int x = spot.getSpotX();
        int y = spot.getSpotY();
        Color opponentColor;
        if (_nextToPlay == Player.WHITE) {
            opponentColor = Color.WHITE;
        } else {
            opponentColor = Color.BLACK;
        }
        // horizontal right
        if (x < _board.getSpotWidth() - 1 && _board.getSpotAt(x+1, y).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            for (int i = x + 1; i < _board.getSpotWidth(); i++) {
                if (_board.getSpotAt(i, y).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(i, y).getSpotColor() == playerColor && isEmpty) {
                    flipHorizontal(x, y, i, y, playerColor);
                }
            }
        }
        // horizontal left
        if (x > 0 && _board.getSpotAt(x-1, y).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            for (int i = x - 1; i >= 0; i--) {
                if (_board.getSpotAt(i, y).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(i, y).getSpotColor() == playerColor && isEmpty) {
                    flipHorizontal(i, y, x, y, playerColor);
                }
            }
        }
        // vertical down
        if (y < _board.getSpotHeight() - 1 && _board.getSpotAt(x, y+1).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            for (int i = y + 1; i < _board.getSpotHeight(); i++) {
                if (_board.getSpotAt(x, i).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(x, i).getSpotColor() == playerColor && isEmpty) {
                    flipVertical(x, y, x, i, playerColor);
                }
            }
        }
        // vertical up
        if (y > 0 && _board.getSpotAt(x, y-1).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            for (int i = y - 1; i >= 0; i--) {
                if (_board.getSpotAt(x, i).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(x, i).getSpotColor() == playerColor && isEmpty) {
                    flipVertical(x, i, x, y, playerColor);
                }
            }
        }
        // forward slash right
        if (x < _board.getSpotWidth() - 1 && y > 0 && _board.getSpotAt(x + 1, y - 1).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            int i = y - 1;
            for (int j = x + 1; j < _board.getSpotWidth() && i >= 0; j++, i--) {
                if (_board.getSpotAt(j, i).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(j, i).getSpotColor() == playerColor && isEmpty) {
                    flipForwardSlash(x, y, j, i, playerColor);
                }
            }
        }
        // forward slash left
        if (x > 0 && y < _board.getSpotHeight() - 1 && _board.getSpotAt(x - 1, y + 1).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            int i = y + 1;
            for (int j = x - 1; j >= 0 && i < _board.getSpotHeight(); j--, i++) {
                if (_board.getSpotAt(j, i).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(j, i).getSpotColor() == playerColor && isEmpty) {
                    flipForwardSlash(j, i, x, y, playerColor);
                }
            }
        }
        // back slash right
        if (x < _board.getSpotWidth() - 1 && y < _board.getSpotHeight() - 1 && _board.getSpotAt(x + 1, y + 1).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            int i = y + 1;
            for (int j = x + 1; j < _board.getSpotWidth() && i < _board.getSpotHeight(); i++, j++) {
                if (_board.getSpotAt(j, i).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(j, i).getSpotColor() == playerColor && isEmpty) {
                    flipBackSlash(j, i, x, y, playerColor);
                }
            }
        }
        // back slash left
        if (x > 0 && y > 0 && _board.getSpotAt(x-1, y-1).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            int i = y - 1;
            for (int j = x - 1; j >= 0 && i >= 0; j--, i--) {
                if (_board.getSpotAt(j, i).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(j, i).getSpotColor() == playerColor && isEmpty) {
                    flipBackSlash(x, y, j, i, playerColor);
                }
            }
        }

        if (!canMove()) {
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
        }
        if (!canMove()) {
            int black = 0;
            int white = 0;
            _gameWon = true;
            for (int i = 0; i < _board.getSpotWidth(); i++) {
                for (int j = 0; j < _board.getSpotHeight(); j++) {
                    if (_board.getSpotAt(i, j).getSpotColor() == Color.BLACK) {
                        black++;
                    } else if (_board.getSpotAt(i, j).getSpotColor() == Color.WHITE) {
                        white++;
                    }
                }
            }
            if (black > white) {
                _message.setText("Black won. Score: " + black + " to " + white);
            } else if (white > black) {
                _message.setText("White won. Score:  " + white + " to " + black);
            } else {
                _message.setText("Draw. Score: " + black + " to " + white);
            }
            return;
        }

        // Check to see if board is full
        boolean isFull = true;
        for (int i = 0; i < _board.getSpotWidth(); i++) {
            for (int j = 0; j < _board.getSpotHeight(); j++) {
                if (_board.getSpotAt(i, j).isEmpty()) {
                    isFull = false;
                }
            }
        }
        if (isFull) {
            int black = 0;
            int white = 0;
            _gameWon = true;
            for (int i = 0; i < _board.getSpotWidth(); i++) {
                for (int j = 0; j < _board.getSpotHeight(); j++) {
                    if (_board.getSpotAt(i, j).getSpotColor() == Color.BLACK) {
                        black++;
                    } else if (_board.getSpotAt(i, j).getSpotColor() == Color.WHITE) {
                        white++;
                    }
                }
            }
            if (black > white) {
                _message.setText("Black won. Score: " + black + " to " + white);
            } else if (white > black) {
                _message.setText("White won. Score:  " + white + " to " + black);
            } else {
                _message.setText("Draw. Score: " + black + " to " + white);
            }
        } else {
            _message.setText(nextPlayerName + " to play.");
        }
    }

    public void flipHorizontal(int x1, int y1, int x2, int y2, Color c) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                _board.getSpotAt(i, j).clearSpot();
                _board.getSpotAt(i, j).setSpotColor(c);
                _board.getSpotAt(i, j).toggleSpot();
            }
        }
    }

    public void flipVertical(int x1, int y1, int x2, int y2, Color c) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                _board.getSpotAt(i, j).clearSpot();
                _board.getSpotAt(i, j).setSpotColor(c);
                _board.getSpotAt(i, j).toggleSpot();
            }
        }
    }

    public void flipForwardSlash(int x1, int y1, int x2, int y2, Color c) {
        int j = y1;
        for (int i = x1; i <= x2 && j >= y2; i++, j--) {
            _board.getSpotAt(i, j).clearSpot();
            _board.getSpotAt(i, j).setSpotColor(c);
            _board.getSpotAt(i, j).toggleSpot();
        }
    }

    public void flipBackSlash(int x1, int y1, int x2, int y2, Color c) {
        int j = y1;
        for (int i = x1; i >= x2 && j >= y2; i--, j--) {
            _board.getSpotAt(i, j).clearSpot();
            _board.getSpotAt(i, j).setSpotColor(c);
            _board.getSpotAt(i, j).toggleSpot();
        }
    }

    public boolean canMove() {
        for (int i = 0; i < _board.getSpotWidth(); i++) {
            for (int j = 0; j < _board.getSpotHeight(); j++) {
                if (isValidBoolean(_board.getSpotAt(i, j)) && _board.getSpotAt(i, j).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidBoolean(Spot s) {
        // Initialize spot x and y
        int x = s.getSpotX();
        int y = s.getSpotY();

        // Initialize players
        Color playerColor;
        Color opponentColor;
        if (_nextToPlay == Player.WHITE) {
            playerColor = Color.WHITE;
            opponentColor = Color.BLACK;
        } else {
            playerColor = Color.BLACK;
            opponentColor = Color.WHITE;
        }
        boolean isValid = false;

        // Check horizontal right
        if (x < _board.getSpotWidth() - 1 && _board.getSpotAt(x+1, y).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            for (int i = x + 1; i < _board.getSpotWidth(); i++) {
                if (_board.getSpotAt(i, y).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(i, y).getSpotColor() == playerColor && isEmpty) {
                    isValid = true;
                }
            }
        }

        // Check horizontal left
        if (x > 0 && _board.getSpotAt(x-1, y).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            for (int i = x - 1; i >= 0; i--) {
                if (_board.getSpotAt(i, y).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(i, y).getSpotColor() == playerColor && isEmpty) {
                    isValid = true;
                }
            }
        }

        // Check vertical down
        if (y < _board.getSpotHeight() - 1 && _board.getSpotAt(x, y+1).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            for (int i = y + 1; i < _board.getSpotHeight(); i++) {
                if (_board.getSpotAt(x, i).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(x, i).getSpotColor() == playerColor && isEmpty) {
                    isValid = true;
                }
            }
        }

        // Check vertical up
        if (y > 0 && _board.getSpotAt(x, y-1).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            for (int i = y - 1; i >= 0; i--) {
                if (_board.getSpotAt(x, i).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(x, i).getSpotColor() == playerColor && isEmpty) {
                    isValid = true;
                }
            }
        }

        // Check forward slash right
        if (x < _board.getSpotWidth() - 1 && y > 0 && _board.getSpotAt(x + 1, y - 1).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            int i = y - 1;
            for (int j = x + 1; j < _board.getSpotWidth() && i >= 0; j++, i--) {
                if (_board.getSpotAt(j, i).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(j, i).getSpotColor() == playerColor && isEmpty) {
                    isValid = true;
                }
            }
        }

        // Check forward slash left
        if (x > 0 && y < _board.getSpotHeight() - 1 && _board.getSpotAt(x - 1, y + 1).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            int i = y + 1;
            for (int j = x - 1; j >= 0 && i < _board.getSpotHeight(); j--, i++) {
                if (_board.getSpotAt(j, i).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(j, i).getSpotColor() == playerColor && isEmpty) {
                    isValid = true;
                }
            }
        }

        // Check back slash right
        if (x < _board.getSpotWidth() - 1 && y < _board.getSpotHeight() - 1 && _board.getSpotAt(x + 1, y + 1).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            int i = y + 1;
            for (int j = x + 1; j < _board.getSpotWidth() && i < _board.getSpotHeight(); i++, j++) {
                if (_board.getSpotAt(j, i).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(j, i).getSpotColor() == playerColor && isEmpty) {
                    isValid = true;
                }
            }
        }

        // Check back slash left
        if (x > 0 && y > 0 && _board.getSpotAt(x-1, y-1).getSpotColor() == opponentColor) {
            boolean isEmpty = true;
            int i = y - 1;
            for (int j = x - 1; j >= 0 && i >= 0; j--, i--) {
                if (_board.getSpotAt(j, i).isEmpty()) {
                    isEmpty = false;
                }
                if (_board.getSpotAt(j, i).getSpotColor() == playerColor && isEmpty) {
                    isValid = true;
                }
            }
        }

        return isValid;
    }

    @Override
    public void spotEntered(Spot spot) {
        // Highlight if spot is a valid move
        if (!_gameWon && spot.isEmpty() && isValidBoolean(spot)) {
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