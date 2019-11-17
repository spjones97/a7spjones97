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

        // Create SpotBoard and message label
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
        // If game already won, do nothing
        if (_gameWon) {
            return;
        }

        // Set up player and next player name strings
        // Set up player color as local variables to be used later
        String playerName = null;
        String nextPlayerName = null;
        Color playerColor = null;

        if (_nextToPlay == Player.RED) {
            playerColor = Color.RED;
            playerName = "Red";
            nextPlayerName = "Black";
            _nextToPlay = Player.BLACK;
        } else {
            playerColor = Color.BLACK;
            playerName = "Black";
            nextPlayerName = "Red";
            _nextToPlay = Player.RED;
        }

        // Set color of lowest spot on column clicked and toggle
        int xValue = spot.getSpotX();
        for (int i = 0; i < 6; i++) {
            if (!_board.getSpotAt(xValue, 0).isEmpty()) {
                break;
            }
            if (i != 5) {
                if (_board.getSpotAt(xValue, i).isEmpty() &&
                    !_board.getSpotAt(xValue, (i + 1)).isEmpty()) {
                    _board.getSpotAt(xValue, i).setSpotColor(playerColor);
                    _board.getSpotAt(xValue, i).toggleSpot();
                    _message.setText(nextPlayerName + "'s turn");
                    break;
                }
            } else if (i == 5) {
                _board.getSpotAt(xValue, i).setSpotColor(playerColor);
                _board.getSpotAt(xValue, i).toggleSpot();
                _message.setText(nextPlayerName + "'s turn");
                break;
            }
        }

        // Check to see if board is full
        boolean isFull = true;
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 6; y++) {
                if (_board.getSpotAt(x, y).isEmpty()) {
                    isFull = false;
                }
            }
        }

        // Check to see if current play won
        if (checkX(playerColor)) {
            _gameWon = true;
        } else if (checkY(playerColor)) {
            _gameWon = true;
        } else if (checkBackSlash(playerColor)) {
            _gameWon = true;
        } else if (checkForwardSlash(playerColor)) {
            _gameWon = true;
        }

        if (_gameWon) {
            _message.setText(playerName + " got four in a row. Game over.");
        } else if (isFull && !_gameWon) {
            _message.setText("Draw. Restart to play again.");
        } else {
            _message.setText(nextPlayerName + " to play.");
        }
    }

    protected boolean checkX(Color c) {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 4; x++) {
                boolean four = true;
                int counter = x;
                while (counter < (x + 4) && counter < 7) {
                    if (_board.getSpotAt(counter, y).getSpotColor() != c) {
                        four = false;
                    }
                    counter += 1;
                }
                if (four) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean checkY(Color c) {
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 3; y++) {
                boolean four = true;
                int counter = y;
                while (counter < (y + 4) && counter < 6) {
                    if (_board.getSpotAt(x, counter).getSpotColor() != c) {
                        four = false;
                    }
                    counter += 1;
                }
                if (four) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean checkBackSlash(Color c) {
        boolean four = true;
        int y = 2;
        for (int x = 0; x < 4; x+=1, y+=1) {
            if (_board.getSpotAt(x, y).getSpotColor() != c) {
                four = false;
            }
        }
        if (four) {
            return true;
        }

        y = 1;
        for (int x = 0; x < 2; x += 1, y += 1) {
            four = true;
            int counterX = x;
            int counterY = y;
            while (counterX < (x + 4) && counterX < 5) {
                if (_board.getSpotAt(counterX, counterY).getSpotColor() != c) {
                    four = false;
                }
                counterX += 1;
                counterY += 1;
            }
            if (four) {
                return true;
            }
        }

        for (int xy = 0; xy < 3; xy += 1) {
            four = true;
            int counter = xy;
            while(counter < (xy + 4) && counter < 6) {
                if (_board.getSpotAt(counter, counter).getSpotColor() != c) {
                    four = false;
                }
                counter += 1;
            }
            if (four) {
                return true;
            }
        }

        y = 0;
        for (int x = 1; x < 4; x+=1, y+=1) {
            four = true;
            int counterX = x;
            int counterY = y;
            while (counterX < (x + 4) && counterX < 7) {
                if (_board.getSpotAt(counterX, counterY).getSpotColor() != c) {
                    four = false;
                }
                counterX += 1;
                counterY += 1;
            }
            if (four) {
                return true;
            }
        }

        y = 0;
        for (int x = 2; x < 4; x+=1, y+=1) {
            four = true;
            int counterX = x;
            int counterY = y;
            while (counterX < (x + 4) && counterX < 7) {
                if (_board.getSpotAt(counterX, counterY).getSpotColor() != c) {
                    four = false;
                }
                counterX += 1;
                counterY += 1;
            }
            if (four) {
                return true;
            }
        }

        y=0;
        for (int x = 3; x < 4; x+=1, y+=1) {
            four = true;
            int counterX = x;
            int counterY = y;
            while (counterX < (x + 4) && counterX < 7) {
                if (_board.getSpotAt(counterX, counterY).getSpotColor() != c) {
                    four = false;
                }
                counterX += 1;
                counterY += 1;
            }
            if (four) {
                return true;
            }
        }
        return false;
    }

    protected boolean checkForwardSlash(Color c) {
        boolean four = true;
        int y = 0;
        for (int x = 3; x >= 0; x-=1, y+=1) {
            if (_board.getSpotAt(x, y).getSpotColor() != c) {
                four = false;
            }
        }
        if (four) {
            return true;
        }

        y = 4;
        for (int x = 0; x < 2; x+=1, y-=1) {
            four = true;
            int counterX = x;
            int counterY = y;
            while (counterX < (x + 4) && counterX < 5) {
                if (_board.getSpotAt(counterX, counterY).getSpotColor() != c) {
                    four = false;
                }
                counterX += 1;
                counterY -= 1;
            }
            if (four) {
                return true;
            }
        }

        y = 5;
        for (int x = 0; x < 3; x+=1, y-=1) {
            four = true;
            int counterX = x;
            int counterY = y;
            while (counterX < (x + 4) && counterX < 6) {
                if (_board.getSpotAt(counterX, counterY).getSpotColor() != c) {
                    four = false;
                }
                counterX += 1;
                counterY -= 1;
            }
            if (four) {
                return true;
            }
        }

        y = 5;
        for (int x = 1; x < 4; x+=1, y-=1) {
            four = true;
            int counterX = x;
            int counterY = y;
            while (counterX < (x + 4) && counterX < 7) {
                if (_board.getSpotAt(counterX, counterY).getSpotColor() != c) {
                    four = false;
                }
                counterX += 1;
                counterY -= 1;
            }
            if (four) {
                return true;
            }
        }

        y = 5;
        for (int x = 2; x < 4; x+=1, y-=1) {
            four = true;
            int counterX = x;
            int counterY = y;
            while (counterX < (x + 4) && counterX < 7) {
                if (_board.getSpotAt(counterX, counterY).getSpotColor() != c) {
                    four = false;
                }
                counterX += 1;
                counterY -= 1;
            }
            if (four) {
                return true;
            }
        }

        y=5;
        for (int x = 3; x < 4; x+=1, y-=1) {
            four = true;
            int counterX = x;
            int counterY = y;
            while (counterX < (x + 4) && counterX < 7) {
                if (_board.getSpotAt(counterX, counterY).getSpotColor() != c) {
                    four = false;
                }
                counterX += 1;
                counterY -= 1;
            }
            if (four) {
                return true;
            }
        }

        return false;
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

        if (isOpen && !_gameWon) {
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
