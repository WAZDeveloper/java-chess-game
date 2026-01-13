package ui;

import javax.swing.*;
import java.awt.*;
import model.*;

public class ChessWindow extends JFrame {

    private JButton[][] boardButtons;
    private Board gameBoard;

    private boolean whiteTurn = true;

    private Piece selectedPiece = null;
    private int selectedRow;
    private int selectedCol;

    public ChessWindow() {
        setTitle("Chess Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));

        boardButtons = new JButton[8][8];
        gameBoard = new Board();

        createBoard();
        setVisible(true);
    }

    private void createBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                JButton button = new JButton();
                button.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 36));
                button.setFocusPainted(false);

                // Colores del tablero
                if ((row + col) % 2 == 0) {
                    button.setBackground(new Color(240, 217, 181));
                } else {
                    button.setBackground(new Color(181, 136, 99));
                }

                int r = row;
                int c = col;
                button.addActionListener(e -> onSquareClick(r, c));

                boardButtons[row][col] = button;
                add(button);
            }
        }

        refreshBoard();
    }

    private void refreshBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = gameBoard.getPiece(row, col);
                boardButtons[row][col].setText(
                        piece == null ? "" : piece.getSymbol());
            }
        }
    }

    private void onSquareClick(int row, int col) {

        Piece clickedPiece = gameBoard.getPiece(row, col);

        // SELECCIONAR PIEZA
        if (selectedPiece == null) {

            if (clickedPiece == null)
                return;

            // Validar turno
            if (clickedPiece.isWhite() != whiteTurn)
                return;

            selectedPiece = clickedPiece;
            selectedRow = row;
            selectedCol = col;

            boardButtons[row][col].setBorder(BorderFactory.createLineBorder(Color.RED, 3));
            return;
        }

        // INTENTAR MOVER PIEZA
        if (selectedPiece.isValidMove(
                selectedRow, selectedCol,
                row, col,
                gameBoard.getGrid())) {

            gameBoard.movePiece(selectedRow, selectedCol, row, col);
            whiteTurn = !whiteTurn;
        }

        // LIMPIAR SELECCIÓN VISUAL
        boardButtons[selectedRow][selectedCol].setBorder(null);
        selectedPiece = null;

        refreshBoard();
    }
}
