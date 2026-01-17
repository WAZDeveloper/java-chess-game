package model;

public class Bishop extends Piece {

    public Bishop(boolean white) {
        super(white, white ? "♗" : "♝");
    }

    @Override
    public boolean isValidMove(
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece[][] board) {
        // Movimiento diagonal
        if (Math.abs(fromRow - toRow) != Math.abs(fromCol - toCol)) {
            return false;
        }

        return isPathClear(fromRow, fromCol, toRow, toCol, board);
    }

    private boolean isPathClear(
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece[][] board) {
        int rowStep = Integer.compare(toRow, fromRow);
        int colStep = Integer.compare(toCol, fromCol);

        int r = fromRow + rowStep;
        int c = fromCol + colStep;

        while (r != toRow || c != toCol) {
            if (board[r][c] != null)
                return false;
            r += rowStep;
            c += colStep;
        }

        return board[toRow][toCol] == null ||
                board[toRow][toCol].isWhite() != white;
    }
}