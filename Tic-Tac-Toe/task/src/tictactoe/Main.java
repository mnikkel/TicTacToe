package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        char[][] board = new char[3][3];

        var scan = new Scanner(System.in);
        System.out.print("Enter cells: ");
        System.out.println();
        String rawMoves = scan.nextLine();
        String moves = rawMoves.replace('_', ' ');
        for (int i = 0; i < moves.length(); i++) {
            int y = i / 3;
            int x = i % 3;
            board[y][x] = moves.charAt(i);
        }

        printBoard(board);
        int a;
        int b;
        int x;
        int y;
        boolean validMove;
        do {
            System.out.print("Enter the coordinates: ");
            System.out.println();
            a = scan.nextInt();
            b = scan.nextInt();
            int[] coords = convertCoordinates(a, b);
            x = coords[0];
            y = coords[1];
            validMove = isValidMove(a, b, x, y, board);
        } while (!validMove);

        char[][] newBoard = placeMoves(board, x, y);
        System.out.println(checkStatus(newBoard));
        printBoard(newBoard);
    }

    private static boolean isValidMove(int a, int b, int x, int y, char[][] board) {
        if (a < 1 || a > 3 || b < 1 || b > 3) {
            return false;
        } else if (board[y][x] != ' ') {
            System.out.println("This cell is occupied! Try again!");
            return false;
        }

        return true;
    }

    private static int[] convertCoordinates(int x, int y) {
        x--;
        switch (y) {
            case 1:
                y = 2;
                break;
            case 2:
                y = 1;
                break;
            case 3:
                y = 0;
        }
        return new int[]{x, y};
    }

    private static char[][] placeMoves(char[][] board, int x, int y) {
        board[y][x] = 'X';
        return board;
    }

    private static String checkStatus(char[][] board) {
        int xCount = count(board, 'X');
        int oCount = count(board, 'O');
        if (Math.abs(xCount - oCount) > 1) {
            return "Impossible";
        } else {
            String check1 = checkRow(board);
            String check2 = checkRow(mirror(board));
            String check3 = checkDiagonal(board);
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

    private static String checkDiagonal(char[][] board) {
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return board[1][1] + " wins";
        } else if (board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return board[1][1] + " wins";
        }
        return "Draw";
    }

    private static char[][] mirror(char[][] board) {
        char[][] mirror = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mirror[i][j] = board[j][i];
            }
        }

        return mirror;
    }

    private static String checkRow(char[][] board) {
        char winner = '_';
        for (char[] row : board) {
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

    private static int count(char[][] board, char match) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == match) {
                    count++;
                }
            }
        }
        return count;
    }

    private static void printBoard(char[][] board) {
        String hSep = "---------";
        System.out.println(hSep);
        for (char[] row : board) {
            System.out.print("| ");
            for (char ch : row) {
                System.out.print(ch + " ");
            }
            System.out.println("|");
        }
        System.out.println(hSep);
    }
}
