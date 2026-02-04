package model;

public class Pawn extends Piece {

    public Pawn(boolean isWhite) {
        super(isWhite);
        symbol = isWhite ? "♙" : "♟";
    }

    @Override
    public boolean isValidMove(
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece[][] board) {
        int direction = isWhite ? -1 : 1;
        int startRow = isWhite ? 6 : 1;

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
                board[toRow][toCol].isWhite != isWhite) {
            return true;
        }

        return false;
    }
}
