package model;

public class King extends Piece {

    public King(boolean isWhite) {
        super(isWhite);
        symbol = isWhite ? "♔" : "♚";
    }

    @Override
    public boolean isValidMove(int sr, int sc, int tr, int tc, Piece[][] board) {

        int rowDiff = Math.abs(tr - sr);
        int colDiff = Math.abs(tc - sc);

        if (rowDiff <= 1 && colDiff <= 1) {
            Piece target = board[tr][tc];
            return target == null || target.isWhite() != isWhite;
        }

        return false;
    }
}
