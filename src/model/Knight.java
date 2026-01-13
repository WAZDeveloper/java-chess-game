package model;

public class Knight extends Piece {

    public Knight(boolean white) {
        super(white, white ? "♘" : "♞");
    }

    @Override
    public boolean isValidMove(
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece[][] board) {

        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);

        if (!((rowDiff == 2 && colDiff == 1) ||
                (rowDiff == 1 && colDiff == 2))) {
            return false;
        }

        return board[toRow][toCol] == null ||
                board[toRow][toCol].white != white;
    }
}
