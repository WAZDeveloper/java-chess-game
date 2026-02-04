package model;

public abstract class Piece {

    protected boolean isWhite;
    protected String symbol;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public String getSymbol() {
        return symbol;
    }

    public abstract boolean isValidMove(
            int startRow, int startCol,
            int targetRow, int targetCol,
            Piece[][] board);
}
