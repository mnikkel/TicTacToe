package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var scan = new Scanner(System.in);
        System.out.print("Enter cells: ");
        System.out.println();
        Board board = new Board(scan.nextLine());

        board.printBoard();
        boolean validMove;
        do {
            System.out.print("Enter the coordinates: ");
            System.out.println();
            boolean x = board.setX(scan.nextInt());
            boolean y = board.setY(scan.nextInt());
            char square = board.getCurrentSquare();
            if (square != ' ') {
                System.out.println("This cell is occupied! Try again!");
            }
            validMove = x && y && square == ' ';
        } while (!validMove);

        board.placeMove();
        System.out.println(board.checkStatus());
        board.printBoard();
    }
}

class Board {
    private char[][] moves = new char[3][3];
    private int x;
    private int y;

    Board(String rawMoves) {
        String spaces = rawMoves.replace('_', ' ');
        for (int i = 0; i < spaces.length(); i++) {
            int y = i / 3;
            int x = i % 3;
            this.moves[y][x] = spaces.charAt(i);
        }
    }

    public void printBoard() {
        String hSep = "---------";
        System.out.println(hSep);
        for (char[] row : this.moves) {
            System.out.print("| ");
            for (char ch : row) {
                System.out.print(ch + " ");
            }
            System.out.println("|");
        }
        System.out.println(hSep);
    }

    public boolean setX(int a) {
        if (a < 1 || a > 3) {
            return false;
        } else {
            this.x = --a;
            return true;
        }
    }

    public boolean setY(int b) {
        if (b < 1 || b > 3) {
            return false;
        } else {
            switch (b) {
                case 1:
                    this.y = 2;
                    break;
                case 2:
                    this.y = 1;
                    break;
                case 3:
                    this.y = 0;
            }
            return true;
        }
    }

    public char getCurrentSquare() {
        return moves[y][x];
    }

    public void placeMove() {
        moves[y][x] = 'X';
    }

    public String checkStatus() {
        int xCount = count('X');
        int oCount = count('O');
        if (Math.abs(xCount - oCount) > 1) {
            return "Impossible";
        } else {
            String check1 = checkRow(moves);
            String check2 = checkRow(mirror());
            String check3 = checkDiagonal();
            int xWins = 0;
            int oWins = 0;
            if ((check1 + check2 + check3).contains("Impossible")) {
                return "Impossible";
            }
            if (check1.equals("X wins")) {
                xWins++;
            } else if (check2.equals("X wins")) {
                xWins++;
            } else if (check3.equals("X wins")) {
                xWins++;
            }
            if (check1.equals("O wins")) {
                oWins++;
            } else if (check2.equals("O wins")) {
                oWins++;
            } else if (check3.equals("O wins")) {
                oWins++;
            }
            if (xWins > 0 && oWins > 0) {
                return "Impossible";
            } else if (xWins > 0) {
                return "X wins";
            } else if (oWins > 0) {
                return "O wins";
            } else if (xCount + oCount < 9) {
                return "Game not finished";
            } else {
                return "Draw";
            }
        }
    }

    private String checkDiagonal() {
        if (moves[0][0] == moves[1][1] && moves[0][0] == moves[2][2]) {
            return moves[1][1] + " wins";
        } else if (moves[0][2] == moves[1][1] && moves[0][2] == moves[2][0]) {
            return moves[1][1] + " wins";
        }
        return "Draw";
    }

    private char[][] mirror() {
        char[][] mirror = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mirror[i][j] = moves[j][i];
            }
        }

        return mirror;
    }

    private String checkRow(char[][] moves) {
        char winner = '_';
        for (char[] row : moves) {
            if (row[0] == row[1] && row[0] == row[2] && (row[0] == 'X' || row[0] == 'O')) {
                if (winner == 'X' || winner == 'O') {
                    return "Impossible";
                } else {
                    winner = row[0];
                }
            }
        }
        if (winner == 'X') {
            return "X wins";
        } else if (winner == 'O') {
            return "O wins";
        } else {
            return "Draw";
        }
    }

    private int count(char match) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (moves[i][j] == match) {
                    count++;
                }
            }
        }
        return count;
    }
}
