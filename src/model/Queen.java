package model;

public class Queen extends Piece {

    public Queen(boolean white) {
        super(white, white ? "♕" : "♛");
    }

    @Override
    public boolean isValidMove(
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece[][] board) {
        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);

        // Movimiento tipo torre o alfil
        if (rowDiff == colDiff || fromRow == toRow || fromCol == toCol) {
            return isPathClear(fromRow, fromCol, toRow, toCol, board);
        }

        return false;
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
                board[toRow][toCol].white != white;
    }
}
