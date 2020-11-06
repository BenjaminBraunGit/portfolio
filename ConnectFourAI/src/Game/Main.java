package Game;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // 1. board size 3x3 -> 7x6
    // 2. how moves are made (drop from the top)
    // 3. how you win <- HARD

    static String[][] board = new String[6][7];
    static boolean isXsTurn = true;
    static Scanner s;
    static String xPlayer;
    static String oPlayer;
    static int moveToMake;

    static int moves = 0;


    static final int PIECES_TO_WIN = 4;
    static final int AI_DEPTH = 10;
    static final int INFINITY = Integer.MAX_VALUE;
    static final int BIG_NUM = INFINITY - 10;
    static  final int SMALL_NUM = -INFINITY + 10;

    static int timesRecursed = 0;

    /** The main function that runs the game.*/
    public static void main(String[] args) {

        s = new Scanner(System.in);

        System.out.println("Who is X? (type \"ai\" or \"h\") for AI or Human respectively.");
        String input = s.next();
        while(!input.equals("ai") && !input.equals("h")) {
            input = s.next();
        }
        xPlayer = input;

        System.out.println("Who is O? (type \"ai\" or \"h\") for AI or Human respectively.");
        input = s.next();
        while(!input.equals("ai") && !input.equals("h")) {
            input = s.next();
        }
        oPlayer = input;

        initNewGame();
        while (true) {
            printBoard();
            if (isXsTurn && xPlayer.equals("ai")) {
                AITurn();
            } else if (!isXsTurn && oPlayer.equals("ai")) {
                AITurn();
            } else {
                playerTurn();
            }
            if (gameOver()) {
                printBoard();
                if (checkWinner("X")) {
                    System.out.println("GAME OVER! X Wins!");
                } else if (checkWinner("O")) {
                    System.out.println("GAME OVER! O Wins!");
                } else {
                    System.out.println("GAME OVER! Tie!");
                }
                break;
            }
        }
    }


    /** The recursive move finding algorithm that represents the game tree min/max search.
     * The function should set the global variable moveToMake. */
    static int findMove(int depth, int sense, boolean saveMove, int alpha, int beta, int lastMove) {

        timesRecursed++;

        int value = gameOverBetter(lastMove);

        if (depth <= 0 || value > 0) {

            return heuristic(value);
        }
        //Make a couple variables for the value of the best move and the move itself
        int bestValueSoFar = INFINITY * (sense * -1);
        int bestMoveSoFar = -1;

        for (int M : getLegalMovesBetterOrder()) {
            makeMove(M);

            // Where our recursive call is made.
            int valueFromChild = findMove(depth - 1, sense * -1, false, alpha, beta, M);

            //Min/maxing...
            if (sense == 1) {
                //maximizing logic
                if (valueFromChild > bestValueSoFar) {
                    bestValueSoFar = valueFromChild;
                    bestMoveSoFar = M;
                    alpha = Math.max(alpha, bestValueSoFar);
                }

            } else if (sense == -1) {
                //minimizing logic
                if (valueFromChild < bestValueSoFar) {
                    bestValueSoFar = valueFromChild;
                    bestMoveSoFar = M;
                    beta = Math.min(beta, bestValueSoFar);
                }
            }

            retractMove(M);

            //Here is where you want to BREAK if alpha >= beta
            if (alpha >= beta) {
                break;
            }
        }
        if (saveMove) {
            // set global variable moveToMake = bestMoveSoFar
            moveToMake = bestMoveSoFar;
        }
        if (depth == AI_DEPTH - 1) {
            System.out.println("This move will result in a: " + bestValueSoFar);
        }
        //CODE FOR MAKING A RANDOM MOVE:
        //ArrayList<Integer> legalMoves = getLegalMoves();
        //moveToMake = legalMoves.get( (int) (Math.random() * legalMoves.size()));
        return bestValueSoFar; // This will be the value of either the highest of lowest child node
    }

    /** A simple move function that resets the global best move so far and calls findMove.*/
    static int getBestMove() {
        moveToMake = -1;

        int sense;

        if (isXsTurn) {
            sense = 1;
        } else {
            sense = -1;
        }

        findMove(AI_DEPTH, sense, true, -INFINITY, INFINITY, -1);
        System.out.println("Times Recursed: " + timesRecursed);
        timesRecursed = 0;
        return moveToMake;
    }

    /** Resets the board for a new game by setting all indexes in board to spaces.*/
    static void initNewGame() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = " ";
            }
        }
    }

    /** Prints the current state of the board.*/
    static void printBoard() {
        System.out.print("Player to move: ");
        if (isXsTurn) {
            System.out.println("X");
        } else {
            System.out.println("O");
        }
        System.out.println("* * * * * * * * * * * * * * *");
        System.out.println("* 0 * 1 * 2 * 3 * 4 * 5 * 6 *");
        for (int i = 0; i < board.length; i++) {
            System.out.print("|");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(" " + board[i][j] + " |");
            }
            System.out.println();
        }
        System.out.println("_____________________________");
        System.out.println("* * * * * * * * * * * * * * *");
    }

    /** Updates a square on the board based on whose turn it is and whether the move is a retraction.*/
    static void setSquare(int col, boolean retraction) {
        int row = 5;
        int pieceCount = 0;

        // look through the col (for loop) and count how many pieces there are
        for (int i = 0; i < board.length; i++) {
            if (!board[i][col].equals(" ")) {
                pieceCount = 6 - i;
                break;
            }
        }

        if (!retraction) {
            if (pieceCount >= 6) {
                System.out.println("Not a legal move.");
            } else {
                //put the piece in board[row][col] where row is 5 - pieceCount
                row -= pieceCount;
                board[row][col] = isXsTurn ? "X" : "O";
            }
        } else {
            if (pieceCount <= 0) {
                System.out.println("No move to retract");
            } else {
                //remove the piece in board[row][col] where row is 5 - (pieceCount - 1)
                row -= (pieceCount - 1);
                board[row][col] = " ";
            }
        }
    }

    /** Makes the passed in move if legal.*/
    static void makeMove(int move) {
        setSquare(move, false);
        isXsTurn = !isXsTurn;
        moves++;
    }

    /** Retracts the passed in move, if the square is not already blank.*/
    static void retractMove(int move) {
        setSquare(move, true);
        isXsTurn = !isXsTurn;
        moves--;
    }

    /** Preforms a player turn.*/
    static void playerTurn() {
        int move = s.nextInt();
        ArrayList<Integer> legalMoves = getLegalMoves();

        while(!legalMoves.contains(move)) {
            System.out.println("That is not a legal move.");
            move = s.nextInt();
        }
        makeMove(move);
    }

    /** Preforms an AI turn.*/
    static void AITurn() {
        int move = getBestMove();
        makeMove(move);
    }

    /** Returns an ArrayList of the legal move for the current board state.*/
    static ArrayList<Integer> getLegalMoves() {
        ArrayList<Integer> legalMoves = new ArrayList<>();
        for (int i = 0; i < board[0].length; i++) {
            if (board[0][i].equals(" ")) {
                legalMoves.add(i);
            }
        }
        return legalMoves;
    }

    static ArrayList<Integer> getLegalMovesBetterOrder() {
        ArrayList<Integer> legalMoves = new ArrayList<>();
        int startMove = 3;
        if (board[0][startMove].equals(" ")) {
            legalMoves.add(startMove);
        }

        for (int distFromCenter = 1; startMove + distFromCenter < board[0].length && startMove - distFromCenter >= 0; distFromCenter++) {
            if (board[0][startMove + distFromCenter].equals(" ")) {
                legalMoves.add(startMove + distFromCenter);
            }
            if (board[0][startMove - distFromCenter].equals(" ")) {
                legalMoves.add(startMove - distFromCenter);
            }
        }
        return legalMoves;
    }

    /** The heuristic evaluation of the current board array represented as an integer value.*/
    static int heuristic(int x) {
        if (x == 1) {
            return BIG_NUM; //SUPER BIG
        } else if (x == 2) {
            return SMALL_NUM; //SUPER SMALL
        } else if (x == 3)  {
            return 0;
        } else {




            return getPosWins("X") - getPosWins("O"); // X's possible wins - O's possible wins
        }
    }

    /** Returns true or false depending on whether the game is over.*/
    static boolean gameOver() {
        return checkWinner("X") || checkWinner("O") || getLegalMoves().isEmpty();
    }

    /** Returns true or false depending on whether the game is over.*/
    static int gameOverBetter(int lastMove) {
        if (lastMove == -1) {
            return 0;
        }

        if (checkWinner("X", lastMove)) {
            return 1;
        } else if (checkWinner("O", lastMove)) {
            return 2;
        } else if (getLegalMoves().isEmpty()) {
            return 3;
        }
        return 0;
    }

    static int getPosWins(String sym) {

        int posWins = 69;

        if (sym.equals("X")) {
            sym = "O";
        } else {
            sym = "X";
        }

        //check rows

        // for row "R"
        //look at the middle piece of R, if it is SYM then posWins -= 4
        //else if left or right pieces, you can posWins -= 3 (and then check the 3 pieces on the other side)
        //

        for (int row = 5; row >= 0; row--) {
            if (board[row][3].equals(sym)) {
                posWins -= 4;
            } else if (board[row][2].equals(sym)) {
                posWins -= 3;
                //check the 3 on the other side
                if (board[row][4].equals(sym) || board[row][5].equals(sym) || board[row][6].equals(sym)) {
                    posWins -= 1;
                }
            } else if (board[row][4].equals(sym)) {
                posWins -= 3;
                //check the 3 on the other side
                if (board[row][2].equals(sym) || board[row][1].equals(sym) || board[row][0].equals(sym)) {
                    posWins -= 1;
                }
            } else  {
                if (board[row][1].equals(sym)) {
                    posWins -= 2;
                } else if (board[row][0].equals(sym)) {
                    posWins--;
                }
                if (board[row][5].equals(sym)) {
                    posWins -= 2;
                } else if (board[row][6].equals(sym)) {
                    posWins--;
                }
            }
        }

        if (posWins < 45) {
            System.out.println("TOO LOW");
        }

        //check cols

        for (int col = 0; col < board[0].length; col++) {
            if (board[3][col].equals(sym) || board[2][col].equals(sym)) {
                posWins -= 3;
            } else if (board[1][col].equals(sym)) {
                posWins -= 2;
                //check the 3 on the other side
                if (board[4][col].equals(sym) || board[5][col].equals(sym) ) {
                    posWins -= 1;
                }
            } else if (board[4][col].equals(sym)) {
                posWins -= 2;
                //check the 3 on the other side
                if (board[1][col].equals(sym) || board[0][col].equals(sym)) {
                    posWins -= 1;
                }
            } else  {
                if (board[0][col].equals(sym)) {
                    posWins--;
                }
                if (board[5][col].equals(sym)) {
                    posWins--;
                }
            }
        }

        if (posWins < 24) {
            System.out.println("TOO LOW");
        }

        //check diags

        //UPWARDS diags
        //up left / down left
        int diagWins = 0;
        for (int i = 3; i < board.length; i++) {
            diagWins++;
            int row = i;
            int col = 0;
            int winsInDiag = diagWins;
            int weight = 1;
            int squaresChecked = 0;
            // loops along the current diag
            while (row >= 0 && col < board[0].length) {
                squaresChecked++;
                if (board[row][col].equals(sym)) {
                    if (diagWins == 3 && squaresChecked == 2 && winsInDiag == 2) {
                        winsInDiag--;
                    }
                    else if (diagWins == 3 && squaresChecked == 5 && winsInDiag == 3 && board[row-1][col+1].equals(sym)) {
                        winsInDiag -= weight;
                        break;
                    } else {
                        winsInDiag -= weight;
                    }
                    if (winsInDiag <= 0) {
                        posWins -= diagWins;
                        break;
                    }
                }
                if (weight < diagWins) {
                    weight++;
                } else if (squaresChecked >= 4){
                    weight--;
                }

                row--;
                col++;
            }
            if (winsInDiag > 0) {
                posWins -= (diagWins - winsInDiag);
            }
        }

        //down left / down right
        diagWins = 0;
        for (int i = board[5].length - 4; i > 0; i--) {
            diagWins++;
            int row = 5;
            int col = i;
            int winsInDiag = diagWins;
            int weight = 1;
            int squaresChecked = 0;
            while (row >= 0 && col < board[0].length) {
                squaresChecked++;
                if (board[row][col].equals(sym)) {
                    if (diagWins == 3 && squaresChecked == 2 && winsInDiag == 2) {
                        winsInDiag--;
                    }
                    else if (diagWins == 3 && squaresChecked == 5 && winsInDiag == 3 && board[row-1][col+1].equals(sym)) {
                        winsInDiag -= weight;
                        break;
                    } else {
                        winsInDiag -= weight;
                    }
                    if (winsInDiag <= 0) {
                        posWins -= diagWins;
                        break;
                    }
                }
                if (weight < diagWins) {
                    weight++;
                } else if (squaresChecked >= 4){
                    weight--;
                }

                row--;
                col++;
            }
            if (winsInDiag > 0) {
                posWins -= (diagWins - winsInDiag);
            }
        }

        //DOWNWARDS diags
        //up right / down right
        diagWins = 0;
        for (int i = 3; i < board.length; i++) {
            diagWins++;
            int row = i;
            int col = 6;
            int winsInDiag = diagWins;
            int weight = 1;
            int squaresChecked = 0;
            while (row >= 0 && col >= 0) {
                squaresChecked++;
                if (board[row][col].equals(sym)) {
                    if (diagWins == 3 && squaresChecked == 2 && winsInDiag == 2) {
                        winsInDiag--;
                    }
                    else if (diagWins == 3 && squaresChecked == 5 && winsInDiag == 3 && board[row-1][col-1].equals(sym)) {
                        winsInDiag -= weight;
                        break;
                    } else {
                        winsInDiag -= weight;
                    }
                    if (winsInDiag <= 0) {
                        posWins -= diagWins;
                        break;
                    }
                }
                if (weight < diagWins) {
                    weight++;
                } else if (squaresChecked >= 4){
                    weight--;
                }

                row--;
                col--;
            }
            if (winsInDiag > 0) {
                posWins -= (diagWins - winsInDiag);
            }
        }

        //down left / down right
        diagWins = 0;
        for (int i = board[5].length - 4; i < board[5].length - 1; i++) {
            diagWins++;
            int row = 5;
            int col = i;
            int winsInDiag = diagWins;
            int weight = 1;
            int squaresChecked = 0;
            while (row >= 0 && col >= 0) {
                squaresChecked++;
                if (board[row][col].equals(sym)) {
                    if (diagWins == 3 && squaresChecked == 2 && winsInDiag == 2) {
                        winsInDiag--;
                    }
                    else if (diagWins == 3 && squaresChecked == 5 && winsInDiag == 3 && board[row-1][col-1].equals(sym)) {
                        winsInDiag -= weight;
                        break;
                    } else {
                        winsInDiag -= weight;
                    }
                    if (winsInDiag <= 0) {
                        posWins -= diagWins;
                        break;
                    }
                }
                if (weight < diagWins) {
                    weight++;
                } else if (squaresChecked >= 4){
                    weight--;
                }

                row--;
                col--;
            }
            if (winsInDiag > 0) {
                posWins -= (diagWins - winsInDiag);
            }
        }

        if (posWins < 0) {
            System.out.println("TOO LOW");
        }

        return posWins;
    }

    /** Returns true or false depending on whether the passed in symbol has won the game.*/
    static boolean checkWinner(String sym) {

        //Check rows
        for (int i = 0; i < board.length; i++) {
            int numConsec = 0;
            for (int j = 0; j < board[i].length; j++) {
                if (!board[i][j].equals(sym)) {
                    numConsec = 0;
                } else {
                    numConsec++;
                }
                if (numConsec == PIECES_TO_WIN) {
                    return true;
                }
            }
        }

        //Check cols
        for (int i = 0; i < board[0].length; i++) {
            int numConsec = 0;
            for (int j = 0; j < board.length; j++) {
                if (!board[j][i].equals(sym)) {
                    numConsec = 0;
                } else {
                    numConsec++;
                }
                if (numConsec == PIECES_TO_WIN) {
                    return true;
                }
            }
        }

        //Check diags

        //Check diags (starting points upper left to lower left)
        for (int i = 3; i < board.length; i++) {
            int row = i;
            int col = 0;
            int numConsec = 0;
            while (row >= 0 && col < board[0].length) {
                if (!board[row][col].equals(sym)) {
                    numConsec = 0;
                } else {
                    numConsec++;
                }
                if (numConsec == PIECES_TO_WIN) {
                    return true;
                }
                row--;
                col++;
            }
        }

        //Check diags (starting points lower left to lower right)
        for (int i = 1; i < board[5].length - 3; i++) {
            int row = 5;
            int col = i;
            int numConsec = 0;
            while (row >= 0 && col < board[0].length) {
                if (!board[row][col].equals(sym)) {
                    numConsec = 0;
                } else {
                    numConsec++;
                }
                if (numConsec == PIECES_TO_WIN) {
                    return true;
                }
                row--;
                col++;
            }
        }

        //Check diags (starting points upper right to lower right)
        for (int i = 3; i < board.length; i++) {
            int row = i;
            int col = 6;
            int numConsec = 0;
            while (row >= 0 && col >= 0) {
                if (!board[row][col].equals(sym)) {
                    numConsec = 0;
                } else {
                    numConsec++;
                }
                if (numConsec == PIECES_TO_WIN) {
                    return true;
                }
                row--;
                col--;
            }
        }

        //Check diags (starting points lower right to lower left)
        for (int i = 3; i < board[5].length - 1; i++) {
            int row = 5;
            int col = i;
            int numConsec = 0;
            while (row >= 0 && col >= 0) {
                if (!board[row][col].equals(sym)) {
                    numConsec = 0;
                } else {
                    numConsec++;
                }
                if (numConsec == PIECES_TO_WIN) {
                    return true;
                }
                row--;
                col--;
            }
        }

        return false;
    }

    /** Returns true or false depending on whether the passed in symbol has won the game.*/
    static boolean checkWinner(String sym, int move) {

        int row = 5;
        int col = move;
        int pieceCount = 0;

        // look through the col (for loop) and count how many pieces there are
        for (int i = 0; i < board.length; i++) {
            if (!board[i][col].equals(" ")) {
                pieceCount = 6 - i;
                break;
            }
        }

        if (pieceCount <= 0) {
            System.out.println("MOVE NEVER HAPPENED (Row default to 5)");
        } else {
            //remove the piece in board[row][col] where row is 5 - (pieceCount - 1)
            row -= (pieceCount - 1);
        }

        // at this point, ROW and COL are set correctly to the position of the last piece placed


        //Check row
        int numConsec = 0;
        for (int j = 0; j < board[row].length; j++) {
            if (!board[row][j].equals(sym)) {
                numConsec = 0;
            } else {
                numConsec++;
            }
            if (numConsec == 4) {
                return true;
            }
        }

        //Check col
        numConsec = 0;
        for (int j = 0; j < board.length; j++) {
            if (!board[j][col].equals(sym)) {
                numConsec = 0;
            } else {
                numConsec++;
            }
            if (numConsec == 4) {
                return true;
            }
        }

        //Check diags going to the upper left, then working to the lower right
        int tempRow = row;
        int tempCol = col;
        numConsec = 0;

        while (tempRow > 0 && tempCol > 0) {
            tempRow--;
            tempCol--;
        }

        while (tempRow < board.length && tempCol < board[0].length) {
            if (!board[tempRow][tempCol].equals(sym)) {
                numConsec = 0;
            } else {
                numConsec++;
            }
            if (numConsec == 4) {
                return true;
            }
            tempRow++;
            tempCol++;
        }

        //Check diags going to the upper left, then working to the lower right
        tempRow = row;
        tempCol = col;
        numConsec = 0;

        while (tempRow > 0 && tempCol < board[0].length - 1) {
            tempRow--;
            tempCol++;
        }

        while (tempRow < board.length && tempCol > 0) {
            if (!board[tempRow][tempCol].equals(sym)) {
                numConsec = 0;
            } else {
                numConsec++;
            }
            if (numConsec == 4) {
                return true;
            }
            tempRow++;
            tempCol--;
        }

        return false;
    }



}