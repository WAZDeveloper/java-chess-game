package model;

public class Pawn extends Piece {

    public Pawn(boolean white) {
        super(white, white ? "♙" : "♟");
    }

    @Override
    public boolean isValidMove(
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece[][] board) {
        int direction = white ? -1 : 1;
        int startRow = white ? 6 : 1;

        // 1️⃣ Movimiento simple (1 casilla)
        if (fromCol == toCol &&
                toRow - fromRow == direction &&
                board[toRow][toCol] == null) {
            return true;
        }

        // 2️⃣ Movimiento doble inicial (2 casillas)
        if (fromCol == toCol &&
                fromRow == startRow &&
                toRow - fromRow == 2 * direction &&
                board[fromRow + direction][toCol] == null &&
                board[toRow][toCol] == null) {
            return true;
        }

        // 3️⃣ Captura diagonal
        if (Math.abs(fromCol - toCol) == 1 &&
                toRow - fromRow == direction &&
                board[toRow][toCol] != null &&
                board[toRow][toCol].white != white) {
            return true;
        }

        return false;
    }
}
