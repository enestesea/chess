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
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isValidPosition(toLine, toColumn)) {
            return false;
        }

        if (line == toLine && column == toColumn) {
            return false;
        }

        // Ферзь ходит как по диагонали, так и по прямой
        return (Math.abs(line - toLine) == Math.abs(column - toColumn)) || (line == toLine || column == toColumn);
    }

    private boolean isValidPosition(int line, int column) {
        return line >= 0 && line < 8 && column >= 0 && column < 8;
    }
}