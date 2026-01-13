package model;

public abstract class Piece {

    protected boolean white;
    protected String symbol;

    public Piece(boolean white, String symbol) {
        this.white = white;
        this.symbol = symbol;
    }

    public boolean isWhite() {
        return white;
    }

    public String getSymbol() {
        return symbol;
    }

    /*
     * Cada pieza define su propia regla de movimiento
     */
    public abstract boolean isValidMove(
            int fromRow, int fromCol,
            int toRow, int toCol,
            Piece[][] board);

}
