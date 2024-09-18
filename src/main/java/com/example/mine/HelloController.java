package com.example.mine;

import java.util.Random;
import java.util.Scanner;

public class HelloController {
    private static final int SIZE = 5;
    private static final int MINES = 3;
    private static final char MINE = '*';
    private static final char EMPTY = '.';
    private static final char COVERED = '#';

    private char[][] board = new char[SIZE][SIZE];
    private boolean[][] mines = new boolean[SIZE][SIZE];
    private boolean[][] revealed = new boolean[SIZE][SIZE];

    public HelloController() {
        initializeBoard();
        placeMines();
    }

    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = COVERED;
            }
        }
    }

    private void placeMines() {
        Random rand = new Random();
        int minesPlaced = 0;
        while (minesPlaced < MINES) {
            int row = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);
            if (!mines[row][col]) {
                mines[row][col] = true;
                minesPlaced++;
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (revealed[i][j]) {
                    if (mines[i][j]) {
                        System.out.print(MINE + " ");
                    } else {
                        System.out.print(countAdjacentMines(i, j) + " ");
                    }
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < SIZE && newCol >= 0 && newCol < SIZE && mines[newRow][newCol]) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean reveal(int row, int col) {
        if (mines[row][col]) {
            return false;
        }
        revealed[row][col] = true;
        board[row][col] = (char) ('0' + countAdjacentMines(row, col));
        return true;
    }

    public boolean hasWon() {
        int cellsRevealed = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (revealed[i][j] || mines[i][j]) {
                    cellsRevealed++;
                }
            }
        }
        return cellsRevealed == SIZE * SIZE;
    }

    public static void main(String[] args) {
        HelloController game = new HelloController();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            game.printBoard();
            System.out.print("Enter row and column to reveal (0-" + (SIZE - 1) + "): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            if (!game.reveal(row, col)) {
                System.out.println("Boom! You hit a mine.");
                break;
            }
            if (game.hasWon()) {
                System.out.println("Congratulations! You won!");
                break;
            }
        }
        scanner.close();
    }
}