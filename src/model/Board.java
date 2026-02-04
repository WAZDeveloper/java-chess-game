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

        // Reinas
        board[0][3] = new Queen(false);
        board[7][3] = new Queen(true);

        // Reyes
        board[0][4] = new King(false);
        board[7][4] = new King(true);
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    public Piece[][] getGrid() {
        return board;
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

    // ================== JAQUE ==================
    public boolean isKingInCheck(boolean whiteKing) {

        int kingRow = -1, kingCol = -1;

        // Buscar el rey
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board[r][c];
                if (p instanceof King && p.isWhite() == whiteKing) {
                    kingRow = r;
                    kingCol = c;
                }
            }
        }

        // Ver si alguna pieza enemiga lo ataca
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board[r][c];
                if (p != null && p.isWhite() != whiteKing) {
                    if (p.isValidMove(r, c, kingRow, kingCol, board)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // ================== JAQUE MATE ==================
    public boolean isCheckMate(boolean whiteKing) {

        if (!isKingInCheck(whiteKing))
            return false;

        // Probar todos los movimientos posibles
        for (int sr = 0; sr < 8; sr++) {
            for (int sc = 0; sc < 8; sc++) {

                Piece piece = board[sr][sc];
                if (piece == null || piece.isWhite() != whiteKing)
                    continue;

                for (int tr = 0; tr < 8; tr++) {
                    for (int tc = 0; tc < 8; tc++) {

                        if (!piece.isValidMove(sr, sc, tr, tc, board))
                            continue;

                        // Simular movimiento
                        Piece captured = board[tr][tc];
                        board[tr][tc] = piece;
                        board[sr][sc] = null;

                        boolean stillInCheck = isKingInCheck(whiteKing);

                        // Revertir
                        board[sr][sc] = piece;
                        board[tr][tc] = captured;

                        if (!stillInCheck)
                            return false;
                    }
                }
            }
        }

        return true;
    }
}
