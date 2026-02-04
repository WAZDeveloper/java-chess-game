package model;

public class Queen extends Piece {

    public Queen(boolean isWhite) {
        super(isWhite);
        symbol = isWhite ? "♕" : "♛";
    }

    @Override
    public boolean isValidMove(int sr, int sc, int tr, int tc, Piece[][] board) {

        int rowStep = Integer.compare(tr, sr);
        int colStep = Integer.compare(tc, sc);

        if (sr != tr && sc != tc && Math.abs(tr - sr) != Math.abs(tc - sc))
            return false;

        int r = sr + rowStep;
        int c = sc + colStep;

        while (r != tr || c != tc) {
            if (board[r][c] != null)
                return false;
            r += rowStep;
            c += colStep;
        }

        Piece target = board[tr][tc];
        return target == null || target.isWhite() != isWhite;
    }
}
