package org.example.figures;

import org.example.common.ChessBoard;

//Конь

public class Horse extends ChessPiece {

    public Horse(String color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "H"; // Символ коня
    }

    @Override
    public boolean canMoveToPosition(ChessBoard board, int line, int column, int toLine, int toColumn) {
        if (isOutOfBounce(toLine, toColumn)) {
            return false;
        }
        int dx = Math.abs(line - toLine);
        int dy = Math.abs(column - toColumn);
        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
            return board.board[toLine][toColumn] == null ||
                    !board.board[toLine][toColumn].getColor().equals(this.color);
        }
        return false;
    }

}
