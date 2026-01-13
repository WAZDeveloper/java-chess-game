import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Color;

/**
 * Ventana principal del juego de ajedrez
 */
public class ChessWindow extends JFrame {

    // Matriz 8x8 de botones (casillas del tablero)
    private JButton[][] board = new JButton[8][8];

    public ChessWindow() {

        // Título de la ventana
        setTitle("Ajedrez - Java BY: WAZDeveloper");

        // Tamaño de la ventana
        setSize(600, 600);

        // Cierra el programa al cerrar la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout de 8 filas x 8 columnas
        setLayout(new GridLayout(8, 8));

        // Crear el tablero
        createBoard();

        // Mostrar la ventana
        setVisible(true);
    }

    /**
     * Crea el tablero de ajedrez visual
     */
    private void createBoard() {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                JButton square = new JButton();

                // Colores del tablero (blanco y negro)
                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.GRAY);
                }

                // Quita el borde del botón
                square.setBorderPainted(false);

                board[row][col] = square;
                add(square);
            }
        }
    }
}
