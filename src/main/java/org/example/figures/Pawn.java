package org.example.figures;

//Пешка

import org.example.common.ChessBoard;

public class Pawn extends ChessPiece {

    public Pawn(String color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "P"; // Символ пешки
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isValidPosition(toLine, toColumn)) {
            return false;
        }

        if (line == toLine && column == toColumn) {
            return false;
        }

        int direction = color.equals("White") ? 1 : -1; // Направление движения
        int startLine = color.equals("White") ? 1 : 6;  // Начальная позиция для первого хода

        if (column == toColumn) { // Пешка может двигаться только вперед
            if (line + direction == toLine) {
                return true; // Обычный ход вперед на 1 клетку
            }
            else
                return line == startLine && line + 2 * direction == toLine; // Первый ход вперед на 2 клетки
        }
        return false;
    }

    private boolean isValidPosition(int line, int column) {
        return line >= 0 && line < 8 && column >= 0 && column < 8;
    }
}
