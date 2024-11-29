package org.example.common;

import org.example.figures.ChessPiece;

import org.example.figures.*;

public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8]; // Поле для игры
    public String nowPlayer;

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    public String nowPlayerColor() {
        return this.nowPlayer;
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (isOutOfBounce(endLine, endColumn)){
            return false;
        }
        if (checkPos(startLine) && checkPos(startColumn) && checkPos(endLine) && checkPos(endColumn)) {
            ChessPiece piece = board[startLine][startColumn];

            if (piece == null || !nowPlayer.equals(piece.getColor())) return false;
            if (board[endLine][endColumn] != null && board[endLine][endColumn].getColor().equals(nowPlayer)) return false;

            if (piece.canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
                board[endLine][endColumn] = piece;
                board[startLine][startColumn] = null;

                if (piece instanceof King || piece instanceof Rook) {
                    piece.check = false;
                }

                this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";
                return true;
            }
        }
        return false;
    }
    public boolean isPathClear(int startLine, int startColumn, int endLine, int endColumn) {
        if (isOutOfBounce(endLine, endColumn)){
            return false;
        }
        if (startLine == endLine) {
            // Проверка по горизонтали
            int step = startColumn < endColumn ? 1 : -1;
            for (int col = startColumn + step; col != endColumn; col += step) {
                if (col < 0 || col > 7 || startLine < 0 || startLine > 7) {
                    continue;
                }
                if (board[startLine][col] != null) return false;
            }
        } else if (startColumn == endColumn) {
            // Проверка по вертикали
            int step = startLine < endLine ? 1 : -1;
            for (int row = startLine + step; row != endLine; row += step) {
                if (row < 0 || row > 7 || startColumn < 0 || startColumn > 7) {
                    continue;
                }
                if (board[row][startColumn] != null) return false;
            }
        } else {
            // Проверка по диагонали
            int rowStep = startLine < endLine ? 1 : -1;
            int colStep = startColumn < endColumn ? 1 : -1;
            int row = startLine + rowStep;
            int col = startColumn + colStep;
            while (row != endLine && col != endColumn) {
                if (row < 0 || row > 7 || col < 0 || col > 7) {
                    continue;
                }
                if (board[row][col] != null) return false;
                row += rowStep;
                col += colStep;
            }
        }
        return true;
    }
    public boolean castling0() {
        int row = nowPlayer.equals("White") ? 0 : 7;
        King king = board[row][4] instanceof King ? (King) board[row][4] : null;
        Rook rook = board[row][0] instanceof Rook ? (Rook) board[row][0] : null;

        if (king != null && rook != null && king.check && rook.check) {
            // Используем this для вызова isPathClear
            if (isPathClear(row, 1, row, 3)) {
                if (!king.isUnderAttack(this, row, 4) &&
                        !king.isUnderAttack(this, row, 3) &&
                        !king.isUnderAttack(this, row, 2)) {

                    // Выполняем рокировку
                    board[row][2] = king;
                    board[row][4] = null;
                    board[row][3] = rook;
                    board[row][0] = null;

                    king.check = false;
                    rook.check = false;
                    nowPlayer = nowPlayer.equals("White") ? "Black" : "White";

                    return true;
                }
            }
        }
        return false;
    }

    public boolean castling7() {
        int row = nowPlayer.equals("White") ? 0 : 7;
        King king = board[row][4] instanceof King ? (King) board[row][4] : null;
        Rook rook = board[row][7] instanceof Rook ? (Rook) board[row][7] : null;

        if (king != null && rook != null && king.check && rook.check) {
            // Используем this для вызова isPathClear
            if (isPathClear(row, 5, row, 6)) {
                if (!king.isUnderAttack(this, row, 4) &&
                        !king.isUnderAttack(this, row, 5) &&
                        !king.isUnderAttack(this, row, 6)) {

                    // Выполняем рокировку
                    board[row][6] = king;
                    board[row][4] = null;
                    board[row][5] = rook;
                    board[row][7] = null;

                    king.check = false;
                    rook.check = false;
                    nowPlayer = nowPlayer.equals("White") ? "Black" : "White";

                    return true;
                }
            }
        }
        return false;
    }

    public boolean isKingInCheck(String color) {
        // Найдем короля
        int kingRow = -1, kingCol = -1;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece instanceof King && piece.getColor().equals(color)) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }
        // Проверим, атакует ли кто-то короля
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && !piece.getColor().equals(color)) { // Противник
                    if (piece.canMoveToPosition(this, row, col, kingRow, kingCol)) {
                        return true; // Король под шахом
                    }
                }
            }
        }
        return false; // Король не под шахом
    }


    public boolean checkPos(int pos) {
        return pos >= 0 && pos < 8;
    }
    public boolean isUnderAttack(int row, int col, String color) {
        String enemyColor = color.equals("White") ? "Black" : "White";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.getColor().equals(enemyColor)) {
                    if (piece.canMoveToPosition(this, i, j, row, col)) {
                        return true;  // Клетка под атакой
                    }
                }
            }
        }
        return false;  // Клетка не под атакой
    }
    public boolean isCheckmate(String color) {
        // Находится ли король под шахом
        if (!isKingInCheck(color)) return false;

        int kingRow = -1, kingCol = -1;

        // Находим  короля данного цвета
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               ChessPiece piece = board[row][col];
                if (piece instanceof King && piece.getColor().equals(color)) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
            if (kingRow != -1) {
                break;
            }
        }

        //Проверка, может ли король уйти из шаха
        int[] directions = {-1, 0, 1};
        for (int dx : directions) {
            for (int dy : directions) {
                int newRow = kingRow + dx;
                int newCol = kingCol + dy;
                if (newRow < 0 || newCol < 0) continue;
                if (checkPos(newRow) && checkPos(newCol)) {
                    ChessPiece target = board[newRow][newCol];
                    if (target == null || !target.getColor().equals(color)) {
                        if (!isUnderAttack(newRow, newCol, color)) {
                            return false;  // Король может уйти из шаха
                        }
                    }
                }
            }
        }

        // Проверка, могут ли другие фигуры перекрыть шах или съесть атакующую фигуру
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.getColor().equals(color)) {
                    for (int toRow = 0; toRow < 8; toRow++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
                            if (piece.canMoveToPosition(this, row, col, toRow, toCol)) {
                                ChessPiece originalPiece = board[toRow][toCol];
                                board[toRow][toCol] = piece;
                                board[row][col] = null;

                                boolean stillInCheck = isKingInCheck(color);

                                board[row][col] = piece;  // Восстановить положение
                                board[toRow][toCol] = originalPiece;

                                if (!stillInCheck) return false;  // Состояние шаха устранено
                            }
                        }
                    }
                }
            }
        }

        return true;  // Никакие ходы не устранили шах, значит это мат
    }


    public void printBoard() {
        System.out.println("Turn " + nowPlayer);
        System.out.println("\nPlayer 2 (Black)\n");
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");
        for (int i = 7; i >= 0; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println("\n");
        }
        System.out.println("Player 1 (White)");
    }

    private boolean isOutOfBounce(int col, int row) {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            return true;
        }

        return false;
    }
}
