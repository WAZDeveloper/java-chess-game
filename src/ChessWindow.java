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

        // Caballos negros
        board[0][1].setText("♞");
        board[0][6].setText("♞");

        // Caballos blancos
        board[7][1].setText("♘");
        board[7][6].setText("♘");
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
            String piece = square.getText();

            if (whiteTurn && "♟♞".contains(piece))
                return;
            if (!whiteTurn && "♙♘".contains(piece))
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

        if (isValidMove(selectedRow, selectedCol, row, col)) {

            // Mover la pieza
            square.setText(selectedSquare.getText());
            selectedSquare.setText("");

            // Promocion del peon
            promotePawnIfNeeded(square, row);

            // Cambiar turno
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

        // Fila inicial según color
        int startRow = piece.equals("♙") ? 6 : 1;

        // 1️ Movimiento normal (una casilla hacia adelante)
        if (fromCol == toCol && toRow == fromRow + direction) {
            return board[toRow][toCol].getText().isEmpty();
        }

        // 2️ Doble paso inicial
        if (fromCol == toCol &&
                fromRow == startRow &&
                toRow == fromRow + (2 * direction)) {

            // Ambas casillas deben estar vacías
            int middleRow = fromRow + direction;

            return board[middleRow][toCol].getText().isEmpty()
                    && board[toRow][toCol].getText().isEmpty();
        }

        // 3️ Captura en diagonal
        if (Math.abs(fromCol - toCol) == 1 &&
                toRow == fromRow + direction) {

            String target = board[toRow][toCol].getText();

            if (piece.equals("♙") && target.equals("♟"))
                return true;
            if (piece.equals("♟") && target.equals("♙"))
                return true;
        }

        return false;
    }

    /*
     * Promociona el peon si llega al final del tablero
     */
    private void promotePawnIfNeeded(JButton square, int row) {

        String piece = square.getText();

        // Peon blanco llega arriba
        if (piece.equals("♙") && row == 0) {
            square.setText("♕"); // Reina blanca
        }

        // Peon negro llega abajo
        if (piece.equals("♟") && row == 7) {
            square.setText("♛"); // Reina negra
        }
    }

    /*
     * Decide qye validacion usar segun la pieza
     */

    private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {

        String piece = selectedSquare.getText();

        // Peon
        if (piece.equals("♙") || piece.equals("♟")) {
            return isValidPawnMove(fromRow, fromCol, toRow, toCol);
        }

        // Caballo
        if (piece.equals("♘") || piece.equals("♞")) {
            return isValidKnightMove(fromRow, fromCol, toRow, toCol);
        }

        return false;
    }

    /*
     * Valida el movimiento del caballo
     */

    private boolean isValidKnightMove(int fromRow, int fromCol, int toRow, int toCol) {

        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);

        // Regla en L
        if (!((rowDiff == 2 && colDiff == 1) ||
                (rowDiff == 1 && colDiff == 2))) {
            return false;
        }

        String target = board[toRow][toCol].getText();
        String piece = selectedSquare.getText();

        // Casilla vacia
        if (target.isEmpty())
            return true;

        // Captura solo a pieza enemiga
        if ("♘♙".contains(piece) && "♟♞".contains(target))
            return true;
        if ("♞".contains(piece) && "♙♘".contains(target))
            return true;

        return false;
    }

}
