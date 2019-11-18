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
        _board = new JSpotBoard(8, 6);
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
        _board.getSpotAt(3, 3).setSpotColor(Color.WHITE);
        _board.getSpotAt(4, 4).setSpotColor(Color.WHITE);
        _board.getSpotAt(4, 3).setSpotColor(Color.BLACK);
        _board.getSpotAt(3, 4).setSpotColor(Color.BLACK);

        // Reset gameWont and nextToPlay fields
        _gameWon = false;
        _nextToPlay = Player.WHITE;

        // Display game start message
        _message.setText("Welcome to Othello. White to play");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void spotClicked(Spot spot) {

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