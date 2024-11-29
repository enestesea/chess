package org.example.figures;

import org.example.common.ChessBoard;

// Ферзь

public class Queen extends ChessPiece {

    public Queen(String color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "Q"; // Символ ферзя
    }

    @Override
    public boolean canMoveToPosition(ChessBoard board, int line, int column, int toLine, int toColumn) {
        if (isOutOfBounce(toLine, toColumn)) {
            return false;
        }

        if (line == toLine || column == toColumn || Math.abs(line - toLine) == Math.abs(column - toColumn)) {
            return board.isPathClear(line, column, toLine, toColumn);
        }
        return false;
    }

}