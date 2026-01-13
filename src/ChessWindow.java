import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;

/**
 * Ventana principal del juego de ajedrez
 */
public class ChessWindow extends JFrame {

    private JButton[][] board = new JButton[8][8];

    // Guarda la casilla seleccionada
    private JButton selectedSquare = null;
    private int selectedRow;
    private int selectedCol;

    // true = turno blancas, false = turno negras
    private boolean whiteTurn = true;

    public ChessWindow() {

        setTitle("Ajedrez - Java");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));

        createBoard();
        placePieces();

        setVisible(true);
    }

    /**
     * Crea el tablero y asigna eventos
     */
    private void createBoard() {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                JButton square = new JButton();
                square.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 36));

                Color baseColor = ((row + col) % 2 == 0)
                        ? Color.WHITE
                        : Color.GRAY;

                square.setBackground(baseColor);
                square.setBorderPainted(false);

                final int r = row;
                final int c = col;

                // Evento de clic
                square.addActionListener(e -> onSquareClick(square, r, c));

                board[row][col] = square;
                add(square);
            }
        }
    }

    /**
     * Coloca peones iniciales
     */
    private void placePieces() {

        for (int col = 0; col < 8; col++) {
            board[1][col].setText("♟"); // negras
            board[6][col].setText("♙"); // blancas
        }
    }

    /**
     * Maneja el clic en una casilla
     */
    private void onSquareClick(JButton square, int row, int col) {

        // Si no hay selección previa
        if (selectedSquare == null) {

            // No hacer nada si está vacía
            if (square.getText().isEmpty()) {
                return;
            }

            // Validar turno
            if (whiteTurn && !square.getText().equals("♙"))
                return;
            if (!whiteTurn && !square.getText().equals("♟"))
                return;

            selectedSquare = square;
            selectedRow = row;
            selectedCol = col;
            square.setBackground(Color.YELLOW);
            return;
        }

        // Si se vuelve a hacer clic en la misma casilla
        if (square == selectedSquare) {
            resetColors();
            selectedSquare = null;
            return;
        }

        if (isValidPawnMove(selectedRow, selectedCol, row, col)) {

            square.setText(selectedSquare.getText());
            selectedSquare.setText("");

            whiteTurn = !whiteTurn;
        }

        resetColors();
        selectedSquare = null;

    }

    /**
     * Restaura los colores originales del tablero
     */
    private void resetColors() {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                if ((row + col) % 2 == 0) {
                    board[row][col].setBackground(Color.WHITE);
                } else {
                    board[row][col].setBackground(Color.GRAY);
                }
            }
        }
    }

    /**
     * Valida el movimiento del peón
     */
    private boolean isValidPawnMove(int fromRow, int fromCol, int toRow, int toCol) {

        String piece = selectedSquare.getText();
        int direction = piece.equals("♙") ? -1 : 1;

        // Movimiento hacia adelante
        if (fromCol == toCol && toRow == fromRow + direction) {

            // Casilla destino debe estar vacía
            return board[toRow][toCol].getText().isEmpty();
        }

        // Captura en diagonal
        if (Math.abs(fromCol - toCol) == 1 && toRow == fromRow + direction) {

            String target = board[toRow][toCol].getText();

            // Captura solo a pieza contraria
            if (piece.equals("♙") && target.equals("♟"))
                return true;
            if (piece.equals("♟") && target.equals("♙"))
                return true;
        }

        return false;
    }

}
