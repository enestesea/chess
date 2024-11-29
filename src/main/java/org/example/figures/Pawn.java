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
    public boolean canMoveToPosition(ChessBoard board, int line, int column, int toLine, int toColumn) {
        if (isOutOfBounce(toLine, toColumn)) {
            return false;
        }
        if (column != toColumn) {
            // Пешка бьет по диагонали
            return board.board[toLine][toColumn] != null &&
                    !board.board[toLine][toColumn].getColor().equals(this.color) &&
                    Math.abs(column - toColumn) == 1 &&
                    ((this.color.equals("White") && toLine - line == 1) ||
                            (this.color.equals("Black") && line - toLine == 1));
        }

        // Пешка ходит вперед
        if (board.board[toLine][toColumn] == null) {
            if (this.color.equals("White")) {
                if (line == 1 && toLine - line == 2 && column == toColumn) {
                    return board.isPathClear(line, column, toLine, toColumn);
                }
                return toLine - line == 1 && column == toColumn;
            } else {
                if (line == 6 && line - toLine == 2 && column == toColumn) {
                    return board.isPathClear(line, column, toLine, toColumn);
                }
                return line - toLine == 1 && column == toColumn;
            }
        }
        return false;
    }

}
