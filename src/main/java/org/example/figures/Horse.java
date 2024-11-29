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
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        // Проверяем что ход не выходит за пределы доски
        if (!isValidPosition(toLine, toColumn)) {
            return false;
        }
        // Конь не может сходить на ту же клетку
        if (line == toLine && column == toColumn) {
            return false;
        }
        // Проверка хода буквой "Г"
        int deltaX = Math.abs(line - toLine);
        int deltaY = Math.abs(column - toColumn);
        return (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
    }

    private boolean isValidPosition(int line, int column) {
        return line >= 0 && line < 8 && column >= 0 && column < 8;
    }
}
