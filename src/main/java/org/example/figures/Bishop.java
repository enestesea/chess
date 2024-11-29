package org.example.figures;

import org.example.common.ChessBoard;

//Слон

public class Bishop extends ChessPiece {

    public Bishop(String color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "B"; // Символ слона
    }

    @Override
    public boolean canMoveToPosition(ChessBoard board, int line, int column, int toLine, int toColumn) {
        if (isOutOfBounce(toLine, toColumn)) {
            return false;
        }
        if (Math.abs(line - toLine) != Math.abs(column - toColumn)) return false;
        return board.isPathClear(line, column, toLine, toColumn);
    }

}
