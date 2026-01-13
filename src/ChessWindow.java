import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;

/**
 * Ventana principal del juego de ajedrez
 */
public class ChessWindow extends JFrame {

    // Tablero 8x8
    private JButton[][] board = new JButton[8][8];

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
     * Crea el tablero visual
     */
    private void createBoard() {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                JButton square = new JButton();
                square.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 36));

                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.GRAY);
                }

                square.setBorderPainted(false);

                board[row][col] = square;
                add(square);
            }
        }
    }

    /**
     * Coloca las piezas iniciales
     */
    private void placePieces() {

        // Peones negros (fila 1)
        for (int col = 0; col < 8; col++) {
            board[1][col].setText("♟");
        }

        // Peones blancos (fila 6)
        for (int col = 0; col < 8; col++) {
            board[6][col].setText("♙");
        }
    }
}
