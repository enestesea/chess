package org.example.figures;

import org.example.common.ChessBoard;

//Король

public class King extends ChessPiece {

    public King(String color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "K"; // Символ короля
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isValidPosition(toLine, toColumn)) {
            return false;
        }
        if (line == toLine && column == toColumn) {
            return false;
        }
        return Math.abs(line - toLine) <= 1 && Math.abs(column - toColumn) <= 1;
    }

    public boolean isUnderAttack(ChessBoard board, int line, int column) {
        if (!isValidPosition(line, column)) {
            return false;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board.board[i][j];
                if (piece != null && !piece.color.equals(this.color)) {
                    if (piece.canMoveToPosition(board, i, j, line, column)) {
                        return true; // Поле под атакой
                    }
                }
            }
        }
        return false;
    }

    private boolean isValidPosition(int line, int column) {
        return line >= 0 && line < 8 && column >= 0 && column < 8;
    }
}
