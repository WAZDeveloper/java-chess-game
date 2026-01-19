package ui;

import javax.swing.*;
import java.awt.*;
import model.*;

public class ChessWindow extends JFrame {

    private JButton[][] boardButtons;
    private Color[][] originalColors = new Color[8][8];
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

                // Color del tablero
                Color color;
                if ((row + col) % 2 == 0) {
                    color = new Color(240, 217, 181);
                } else {
                    color = new Color(181, 136, 99);
                }

                button.setBackground(color);
                originalColors[row][col] = color;

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

    private void clearHighlights() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                boardButtons[r][c].setBackground(originalColors[r][c]);
            }
        }
    }

    private void highlightMoves(Piece piece, int fromRow, int fromCol) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                if (r == fromRow && c == fromCol)
                    continue;

                boolean valid = piece.isValidMove(
                        fromRow, fromCol,
                        r, c,
                        gameBoard.getGrid());

                if (valid) {
                    boardButtons[r][c].setBackground(new Color(144, 238, 144)); // verde
                } else {
                    boardButtons[r][c].setBackground(new Color(240, 128, 128)); // rojo
                }
            }
        }
    }

    private void onSquareClick(int row, int col) {

        Piece clickedPiece = gameBoard.getPiece(row, col);

        // SELECCIONAR PIEZA
        if (selectedPiece == null) {

            if (clickedPiece == null)
                return;
            if (clickedPiece.isWhite() != whiteTurn)
                return;

            selectedPiece = clickedPiece;
            selectedRow = row;
            selectedCol = col;

            clearHighlights();
            highlightMoves(selectedPiece, selectedRow, selectedCol);

            boardButtons[row][col].setBorder(
                    BorderFactory.createLineBorder(Color.BLUE, 3));
            return;
        }

        // INTENTAR MOVER
        if (selectedPiece.isValidMove(
                selectedRow, selectedCol,
                row, col,
                gameBoard.getGrid())) {

            gameBoard.movePiece(selectedRow, selectedCol, row, col);
            whiteTurn = !whiteTurn;
        }

        // LIMPIAR
        boardButtons[selectedRow][selectedCol].setBorder(null);
        selectedPiece = null;

        clearHighlights();
        refreshBoard();
    }
}
