package org.example.figures;

//Ладья

import org.example.common.ChessBoard;

public class Rook extends ChessPiece {

    public Rook(String color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "R"; // Символ ладьи
    }

    @Override
    public boolean canMoveToPosition(ChessBoard board, int line, int column, int toLine, int toColumn) {
        if (isOutOfBounce(toLine, toColumn)) {
            return false;
        }

        if (line != toLine && column != toColumn) return false;
        return board.isPathClear(line, column, toLine, toColumn);
    }

}
