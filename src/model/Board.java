package model;

public class Board {

    private Piece[][] board = new Piece[8][8];

    public Board() {
        setup();
    }

    private void setup() {

        // Peones
        for (int col = 0; col < 8; col++) {
            board[1][col] = new Pawn(false);
            board[6][col] = new Pawn(true);
        }

        // Caballos
        board[0][1] = new Knight(false);
        board[0][6] = new Knight(false);
        board[7][1] = new Knight(true);
        board[7][6] = new Knight(true);

        // Torres
        board[0][0] = new Rook(false);
        board[0][7] = new Rook(false);
        board[7][0] = new Rook(true);
        board[7][7] = new Rook(true);

        // Alfiles
        board[0][2] = new Bishop(false);
        board[0][5] = new Bishop(false);
        board[7][2] = new Bishop(true);
        board[7][5] = new Bishop(true);
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {

        Piece piece = board[fromRow][fromCol];
        board[fromRow][fromCol] = null;

        // PROMOCIÓN DE PEÓN
        if (piece instanceof Pawn) {
            if (piece.isWhite() && toRow == 0) {
                board[toRow][toCol] = new Queen(true);
                return;
            }
            if (!piece.isWhite() && toRow == 7) {
                board[toRow][toCol] = new Queen(false);
                return;
            }
        }

        board[toRow][toCol] = piece;
    }

    public Piece[][] getGrid() {
        return board;
    }

}
