package Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TwentyFourtyEight {

    static final int HEIGHT = 4;
    static final int WIDTH = 4;
    static final int NUM_TO_WIN = 2048;
    static final int NUMBER_OF_RUNS = 1000;

    static int[][] board = new int[HEIGHT][WIDTH];
    static Scanner s;
    static boolean wonGame = false;
    static int score = 0;

    public static void main(String[] args) {

        s = new Scanner(System.in);
        createRandNum();
        createRandNum();

        while(true) {
            printBoard();

            /*
            String input = s.next();
            ArrayList<String> legalMoves = getLegalMoves();

            while (!legalMoves.contains(input)) {
                System.out.println("Not a legal move.");
                input = s.next();
            }
            int[] dir = new int[2];

            switch (input) {
                case "w":
                    dir = new int[]{-1, 0};
                    break;
                case "s":
                    dir = new int[]{1, 0};
                    break;
                case "a":
                    dir = new int[]{0, -1};
                    break;
                case "d":
                    dir = new int[]{0, 1};
                    break;
                default:
                    System.out.println("THIS SHOULD NEVER HAPPEN!");
                    break;
            }

             */

            move(getBestMove());
            createRandNum();

            if (wonGame) {
                printBoard();
                System.out.println("YOU WIN! :D");
                break;
            } else if (getLegalMoves().isEmpty()) {
                printBoard();
                System.out.println("YOU LOSE! D:");
                break;
            }
        }
    }


    static int[] getBestMove() {
        Run[] runs = new Run[NUMBER_OF_RUNS];

        for (int i = 0; i < runs.length; i++) {
            runs[i] = generateRun();
        }

        ArrayList<Run> up = new ArrayList<>();
        ArrayList<Run> down = new ArrayList<>();
        ArrayList<Run> left = new ArrayList<>();
        ArrayList<Run> right = new ArrayList<>();


        for(Run R : runs) {
            if (R.initialMove[0] == -1 && R.initialMove[1] == 0) {
                up.add(R);
            } else if (R.initialMove[0] == 1 && R.initialMove[1] == 0) {
                down.add(R);
            } else if (R.initialMove[0] == 0 && R.initialMove[1] == -1) {
                left.add(R);
            } else if (R.initialMove[0] == 0 && R.initialMove[1] == 1) {
                right.add(R);
            }
        }

        int[] averages = new int[4];
        // Store the average finalValue of all the Runs in the "up" array
        averages[0] = up.size() > 0 ? sumArray(up) / up.size() : 0;
        averages[1] = down.size() > 0 ? sumArray(down) / down.size() : 0;
        averages[2] = left.size() > 0 ? sumArray(left) / left.size() : 0;
        averages[3] = right.size() > 0 ? sumArray(right) / right.size() : 0;

        int bestAverageValue = averages[0];
        int[] bestMove = new int[]{-1, 0};

        if (averages[1] > bestAverageValue) {
            bestAverageValue = averages[1];
            bestMove = new int[]{1, 0};
        }
        if (averages[2] > bestAverageValue) {
            bestAverageValue = averages[2];
            bestMove = new int[]{0, -1};
        }
        if (averages[3] > bestAverageValue) {
            bestAverageValue = averages[3];
            bestMove = new int[]{0, 1};
        }

        return bestMove;
    }

    static int sumArray(ArrayList<Run> runs) {
        int sum = 0;
        for(Run R : runs) {
            sum += R.finalValue;
        }

        return sum;
    }

    static Run generateRun() {
        int[][] savedBoard = new int[HEIGHT][];
        for (int i = 0; i < board.length; i++) {
            savedBoard[i] = Arrays.copyOf(board[i], WIDTH);
        }
        int saveScore = score;
        boolean winState = wonGame;

        Run theRun = new Run();

        ArrayList<String> legalMoves = getLegalMoves();

        while (!legalMoves.isEmpty()) {
            int[] move = new int[2];
            String randMove = legalMoves.get( (int) (Math.random() * legalMoves.size()));

            switch (randMove) {
                case "w":
                    move = new int[]{-1, 0};
                    break;
                case "s":
                    move = new int[]{1, 0};
                    break;
                case "a":
                    move = new int[]{0, -1};
                    break;
                case "d":
                    move = new int[]{0, 1};
                    break;
            }
            if (theRun.initialMove == null) {
                theRun.initialMove = move;
            }
            move(move);
            createRandNum();
            legalMoves = getLegalMoves();
            //printBoard();
        }

        for (int i = 0; i < board.length; i++) {
            board[i] = Arrays.copyOf(savedBoard[i], WIDTH);
        }
        theRun.finalValue = score;
        score = saveScore;
        wonGame = winState;

        return theRun;
    }



    static void createRandNum() {
        ArrayList<int[]> posLocations = new ArrayList<int[]>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    posLocations.add(new int[]{i, j});
                }
            }
        }

        int[] randindex = posLocations.get( (int) (Math.random() * posLocations.size()));
        if ((Math.random() * 10) >= 9) {
            board[randindex[0]][randindex[1]] = 4;
        } else {
            board[randindex[0]][randindex[1]] = 2;
        }
    }

    static void printBoard() {
        System.out.println("Score: " + score);
        System.out.print("*");
        for (int i = 0; i < WIDTH; i++) {
            System.out.print("***");
        }
        System.out.println("*");
        for (int i = 0; i < board.length; i++) {
            System.out.print("|");
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    System.out.print(" + ");
                } else {
                    System.out.print(" " + board[i][j] + " ");
                }
            }
            System.out.println("|");
        }
        System.out.print("*");
        for (int i = 0; i < WIDTH; i++) {
            System.out.print("***");
        }
        System.out.println("*");
    }

    static void move(int[] dir) {
        if (dir[0] == 0 && dir[1] == -1) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] != 0) {
                        moveNum(i, j, dir);
                    }
                }
            }
        } else if (dir[0] == 0 && dir[1] == 1) {
            for (int i = 0; i < board.length; i++) {
                for (int j = board[0].length - 1; j >= 0; j--) {
                    if (board[i][j] != 0) {
                        moveNum(i, j, dir);
                    }
                }
            }
        } else if (dir[0] == -1 && dir[1] == 0) {
            for (int i = 0; i < board[0].length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[j][i] != 0) {
                        moveNum(j, i, dir);
                    }
                }
            }
        } else if (dir[0] == 1 && dir[1] == 0) {
            for (int i = 0; i < board[0].length; i++) {
                for (int j = board.length - 1; j >= 0; j--) {
                    if (board[j][i] != 0) {
                        moveNum(j, i, dir);
                    }
                }
            }
        }

    }

    static ArrayList<String> getLegalMoves() {
        ArrayList<String> legalMoves = new ArrayList<>();
        int[][] savedBoard = new int[HEIGHT][];
        for (int i = 0; i < board.length; i++) {
            savedBoard[i] = Arrays.copyOf(board[i], WIDTH);
        }
        boolean winState = wonGame;
        int saveScore = score;
        move(new int[]{-1, 0});
        if (!Arrays.deepEquals(board, savedBoard)) {
            legalMoves.add("w");
        }
        for (int i = 0; i < board.length; i++) {
            board[i] = Arrays.copyOf(savedBoard[i], WIDTH);
        }

        move(new int[]{1, 0});
        if (!Arrays.deepEquals(board, savedBoard)) {
            legalMoves.add("s");
        }
        for (int i = 0; i < board.length; i++) {
            board[i] = Arrays.copyOf(savedBoard[i], WIDTH);
        }

        move(new int[]{0, -1});
        if (!Arrays.deepEquals(board, savedBoard)) {
            legalMoves.add("a");
        }
        for (int i = 0; i < board.length; i++) {
            board[i] = Arrays.copyOf(savedBoard[i], WIDTH);
        }

        move(new int[]{0, 1});
        if (!Arrays.deepEquals(board, savedBoard)) {
            legalMoves.add("d");
        }
        for (int i = 0; i < board.length; i++) {
            board[i] = Arrays.copyOf(savedBoard[i], WIDTH);
        }
        wonGame = winState;
        score = saveScore;
        return legalMoves;
    }

    static void moveNum(int row, int col, int[] dir){

        int yChange = dir[0];
        int xChange = dir[1];

        if (yChange == 0) {
            while(col + xChange >= 0 && col + xChange < board[0].length) {

                if (board[row][col + xChange] != 0) {
                    if (board[row][col + xChange] == board[row][col]) {
                        board[row][col] = 0;
                        board[row][col + xChange] *= 2;
                        score += board[row][col + xChange];
                        if (board[row][col + xChange] >= NUM_TO_WIN) {
                            wonGame = true;
                        }
                    }
                    break;
                } else {
                        board[row][col + xChange] = board[row][col];
                        board[row][col] = 0;
                }
                col += xChange;

            }
        } else {
            while(row + yChange >= 0 && row + yChange < board.length) {

                if (board[row + yChange][col] != 0) {
                    if (board[row + yChange][col] == board[row][col]) {
                        board[row][col] = 0;
                        board[row + yChange][col] *= 2;
                        score += board[row + yChange][col];
                        if (board[row + yChange][col] >= NUM_TO_WIN) {
                            wonGame = true;
                        }
                    }
                    break;
                } else {
                    board[row + yChange][col] = board[row][col];
                    board[row][col] = 0;
                }
                row += yChange;

            }
        }
    }

}