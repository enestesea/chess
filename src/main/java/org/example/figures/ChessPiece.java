package org.example.figures;

// Фигура абстрактный класс

import org.example.common.ChessBoard;

public abstract class ChessPiece {
    protected String color;
    public boolean check = true; // По умолчанию true

    public ChessPiece(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public abstract boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);

    public abstract String getSymbol();

    public boolean isOutOfBounce(int col, int row) {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            return true;
        }

        return false;
    }
}