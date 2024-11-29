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
    public boolean canMoveToPosition(ChessBoard board, int line, int column, int toLine, int toColumn) {
        if (isOutOfBounce(toLine, toColumn)) {
            return false;
        }

        if (Math.abs(line - toLine) <= 1 && Math.abs(column - toColumn) <= 1) {
            return board.board[toLine][toColumn] == null ||
                    !board.board[toLine][toColumn].getColor().equals(this.color);
        }

        return false;
    }


    public boolean isUnderAttack(ChessBoard board, int line, int column) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board.board[i][j];
                if (piece != null && !piece.getColor().equals(this.color)) {
                    if (piece.canMoveToPosition(board, i, j, line, column)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
