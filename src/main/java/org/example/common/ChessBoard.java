package org.example.common;

import org.example.figures.ChessPiece;

import org.example.figures.*;

public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8]; // Поле для игры
    String nowPlayer;

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    public String nowPlayerColor() {
        return this.nowPlayer;
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (checkPos(startLine) && checkPos(startColumn) && checkPos(endLine) && checkPos(endColumn)) {
            ChessPiece piece = board[startLine][startColumn];

            if (piece == null || !nowPlayer.equals(piece.getColor())) return false;
            if (board[endLine][endColumn] != null && board[endLine][endColumn].getColor().equals(nowPlayer)) return false;

            if (piece.canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
                board[endLine][endColumn] = piece;
                board[startLine][startColumn] = null;

                if (piece instanceof King || piece instanceof Rook) {
                    piece.check = false;
                }

                this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";
                return true;
            }
        }
        return false;
    }

    public boolean castling0() {
        King king = findKing(nowPlayer);
        if (king == null || !king.check) return false;

        int row = nowPlayer.equals("White") ? 0 : 7;
        Rook rook = (Rook) board[row][0];

        if (rook != null && rook.check && isPathClear(row, 0, 4)) {
            if (!king.isUnderAttack(this, row, 4) && !king.isUnderAttack(this, row, 2) && !king.isUnderAttack(this, row, 3)) {
                board[row][2] = king;
                board[row][3] = rook;
                board[row][0] = null;
                board[row][4] = null;
                king.check = false;
                rook.check = false;
                nowPlayer = nowPlayer.equals("White") ? "Black" : "White";
                return true;
            }
        }
        return false;
    }

    public boolean castling7() {
        King king = findKing(nowPlayer);
        if (king == null || !king.check) return false;

        int row = nowPlayer.equals("White") ? 0 : 7;
        Rook rook = (Rook) board[row][7];

        if (rook != null && rook.check && isPathClear(row, 4, 7)) {
            if (!king.isUnderAttack(this, row, 4) && !king.isUnderAttack(this, row, 5) && !king.isUnderAttack(this, row, 6)) {
                board[row][6] = king;
                board[row][5] = rook;
                board[row][7] = null;
                board[row][4] = null;
                king.check = false;
                rook.check = false;
                nowPlayer = nowPlayer.equals("White") ? "Black" : "White";
                return true;
            }
        }
        return false;
    }


    private boolean isPathClear(int line, int startColumn, int endColumn) {
        int step = startColumn < endColumn ? 1 : -1;
        for (int col = startColumn + step; col != endColumn; col += step) {
            if (board[line][col] != null) return false;
        }
        return true;
    }

    private King findKing(String color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece instanceof King && piece.getColor().equals(color)) {
                    return (King) piece;
                }
            }
        }
        return null;
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos < 8;
    }

    public void printBoard() {
        System.out.println("Turn " + nowPlayer);
        System.out.println("\nPlayer 2 (Black)\n");
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");
        for (int i = 7; i >= 0; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println("\n");
        }
        System.out.println("Player 1 (White)");
    }
}
