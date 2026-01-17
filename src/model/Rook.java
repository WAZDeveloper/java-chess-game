package model;

public class Rook extends Piece {

    public Rook(boolean white) {
        super(white, white ? "♖" : "♜");
    }

    @Override
    public boolean isValidMove(
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece[][] board) {
        // Movimiento Recto
        if (fromRow != toRow && fromCol != toCol)
            return false;

        return isPathClear(fromRow, fromCol, toRow, toCol, board);
    }

    protected boolean isPathClear(
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