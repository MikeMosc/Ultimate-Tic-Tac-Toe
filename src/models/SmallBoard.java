package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.Integer.min;
import static java.lang.Math.max;

/**
 * Ultimate Tic Tac Toe small board model class.
 * This class is part of the model to represent the ultimate tic tac toe board.
 * Artificial Intelligence.
 * April 19th, 2017.
 * @author Matthew Gimbut
 * @author Michael Moscariello
 */
public class SmallBoard {
    public char[][] smallBoard = new char[3][3];
    List<Square> availableMoves;
    public Scanner scan = new Scanner(System.in);
    private final char xSpace = 'X'; //Player
    private final char oSpace = 'O'; //Computer
    private final char emptySpace = '-';
    boolean isActive = true; //False when somebody wins a board or when there ar no available spaces left
    private char hasBeenWonBy; //Saves who won the current board

    /**
     * Constructor for each smallboard object;  fills the double array with empty '-' spaces
     * and sets the board to not have a winner.
     */
    public SmallBoard(){
        hasBeenWonBy = '-';
        for(int i = 0; i < smallBoard.length; i++)
        {
            for(int j = 0; j < smallBoard[0].length; j++)
            {
                smallBoard[i][j] = emptySpace;
            }
        }

    }

    /**
     * Not used, deprecated method; initially checked to see if a smallboard was won
     * @return Boolean true if game is over and false if it is not
     */
    public boolean isGameOver(){
        boolean b = false;

        if(hasXWon() || hasOWon() || getAvailableMoves().isEmpty()){
            b = true;
            isActive = false;
        }

        return b;
    }

    /**
     * This method is used to count the number of spots taken over by the computer 'O' player
     * @return Number of spots the computer is in
     */
    public int countOSpots(){
        int count = 0;

        for(int i = 0; i < smallBoard.length; i++){
            for(int j = 0; j < smallBoard[0].length; j++){
                if(smallBoard[i][j] == oSpace){
                    count = count + 1;
                }
            }
        }
        return count;
    }

    /**
     * This method is used to count the number of spots taken over by the 'X' player
     * @return Number of spots the player is in
     */
    public int countXSpots(){
        int count = 0;

        for(int i = 0; i < smallBoard.length; i++){
            for(int j = 0; j < smallBoard[0].length; j++){
                if(smallBoard[i][j] == xSpace){
                    count = count + 1;
                }
            }
        }
        return count;
    }

    /**
     * This method checks for possible smallboard wins (row, column, diagonal) for the computer player
     * @return True if computer has won the smallboard, false otherwise
     */
    public boolean hasOWon(){
        //Check for Diagonal Wins
        if ((smallBoard[0][0] == smallBoard[1][1] && smallBoard[0][0] == smallBoard[2][2] && smallBoard[0][0] == oSpace)
                || (smallBoard[0][2] == smallBoard[1][1] && smallBoard[0][2] == smallBoard[2][0] && smallBoard[0][2] == oSpace)) {
            return true;
        }
        for(int i = 0; i < smallBoard.length; i++){
            //Check for row wins
            if(smallBoard[i][0] == smallBoard[i][1] && smallBoard[i][0] == smallBoard[i][2] && smallBoard[i][0] == oSpace){
                return true;
            }
            //Check for column wins
            else if(smallBoard[0][i] == smallBoard[1][i] && smallBoard[0][i] == smallBoard[2][i] && smallBoard[0][i] == oSpace){
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks for possible smallboard wins (row, column, diagonal) for the human player
     * @return True if user has won the smallboard, false otherwise
     */
    public boolean hasXWon(){
        //Check for Diagonal Wins
        if ((smallBoard[0][0] == smallBoard[1][1] && smallBoard[0][0] == smallBoard[2][2] && smallBoard[0][0] == xSpace)
                || (smallBoard[0][2] == smallBoard[1][1] && smallBoard[0][2] == smallBoard[2][0] && smallBoard[0][2] == xSpace)) {
            return true;
        }
        for(int i = 0; i < smallBoard.length; i++){
            //Check for row wins
            if(smallBoard[i][0] == smallBoard[i][1] && smallBoard[i][0] == smallBoard[i][2] && smallBoard[i][0] == xSpace){
                return true;
            }
            //Check for column wins
            else if(smallBoard[0][i] == smallBoard[1][i] && smallBoard[0][i] == smallBoard[2][i] && smallBoard[0][i] == xSpace){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the entire smallboard for empty spaces and adds them to a list of available moves
     * @return A list of square objects to keep track of available moves left.
     */
    public List<Square> getAvailableMoves(){
        availableMoves = new ArrayList<>();
        for(int i = 0; i < smallBoard.length; i++){
            for(int j = 0; j < smallBoard[0].length; j++){
                if(smallBoard[i][j] == emptySpace){
                    availableMoves.add(new Square(i, j));
                }
            }
        }
        return availableMoves;
    }

    /**
     * This method takes in a square object and current player, and places the correct character at the
     * respective location
     * @param square its coordinates are where the player gets placed on the board
     * @param player the character that has to be placed on the board (X or O)
     * @return A square object to keep track of the last move taken.
     */
    public Square placeMove(Square square, char player){
        smallBoard[square.getX()][square.getY()] = player;
        return square;
    }

    /**
     * This method calls the minimax method to receive the best value and create the square that the
     * computer should move.
     * @param b the current bigboard state that needs to be passed into and used for the minimax algorithm
     * @return The square that the computer will move into
     */
    public Square findBestMove(BigBoard b){
        int bestVal = -1000;
        Square bestMove = new Square();
        for(int i = 0; i < smallBoard.length; i++){
            for(int j = 0; j < smallBoard[0].length; j++){
                if(smallBoard[i][j] == emptySpace){

                    smallBoard[i][j] = xSpace;

                    int moveVal = miniMax(0, false, b, -1000, 1000);
                    System.out.println("moveVal = " + moveVal);
                    //Undo the move
                    smallBoard[i][j] = emptySpace;

                    if(moveVal >= bestVal){
                        bestMove.setX(i);
                        bestMove.setY(j);
                        bestVal = moveVal;
                    }
                }
            }
        }
        System.out.println("BestMove = " + bestMove.toString());
        return bestMove;
    }

    /**
     * The minimax algorithm for the ultimate tic tac toe AI
     * @param depth The depth of the current node being looked at
     * @param isMaximizingPlayer The current player; Human = true; Computer = false
     * @param b The current bigBoard instance being observed
     * @param alpha Alpha value necessary for alpha-beta pruning
     * @param beta Beta value necessary for alpha-beta pruning
     * @return The best value received from the recursive minimax algorithm
     */
    public int miniMax(int depth, boolean isMaximizingPlayer, BigBoard b, int alpha, int beta){

        int bestVal = 0;

        //Base Cases
        if (hasXWon()){
            //If x has won this board, return a high heuristic value - current depth
            return 1000 - depth;
        }
        else if (hasOWon()){
            //if O has won this board, return a low heuristic value + current depth
            return -1000 + depth;
        }
        else if(getAvailableMoves().isEmpty() && isMaximizingPlayer){
            //Receiving a tie is better than losing; return 800 when there are no available moves left
            //in this small board and it is the player's turn
            return 800;
        }
        else if(getAvailableMoves().isEmpty() && !isMaximizingPlayer){
            //Receiving a tie is better than losing; return -800 when there are no available moves left
            //in this small board and it is the computer's turn
            return -800;
        }
        else if (depth >= 7 && countOSpots() > countXSpots()) {
            //If the maximum depth has been reached and O has placed more on the current smallboard than
            //x, return -100 * the number of O spaces as the heuristic value
            return -100 * countOSpots();
        }
        else if(depth >= 7 && countXSpots() > countOSpots()){
            //If the maximum depth has been reached and X has placed more on the current smallboard than
            //O, return 100 * the number of X spaces as the heuristic value
            return 100 * countXSpots();
        }
        else if(depth >= 7 && countOSpots() == countXSpots()){
            //No player is in the lead; return 0.
            return 0;
        }
        else{ //continue to recursively call minimax

            List<Square> statesAvailable = getAvailableMoves();
            if (statesAvailable.isEmpty() && isMaximizingPlayer)
            {
                return 800;
            }
            else if(statesAvailable.isEmpty() && !isMaximizingPlayer){
                return -800;
            }

            if (isMaximizingPlayer)
            {
                bestVal = -1000;
                for (int i = 0; i < smallBoard.length; i++)
                {
                    for (int j = 0; j < smallBoard[0].length; j++)
                    {
                        if (smallBoard[i][j] == emptySpace)
                        {

                            //Creating a new move for the next iteration of minimax
                            smallBoard[i][j] = xSpace;
                            Square lastMove = new Square(i, j);

                            //Receiving the max of the big board minimax vs the current bestval
                            bestVal = max(bestVal, b.miniMax(depth + 1, !isMaximizingPlayer, lastMove, alpha, beta));
                            alpha = max(alpha, bestVal);

                            //Undo the move
                            smallBoard[i][j] = emptySpace;

                            //for Alpha beta pruning
                            if (beta >= alpha)
                            {
                                break;
                            }

                        }
                    }
                }
            }
            else
            {
                //Minimax will always start with Minimizing (computer) player
                bestVal = 1000;

                for (int i = 0; i < smallBoard.length; i++)
                {
                    for (int j = 0; j < smallBoard[0].length; j++)
                    {
                        if (smallBoard[i][j] == emptySpace)
                        {
                            //Creating a new move for the next iteration of minimax
                            smallBoard[i][j] = oSpace;
                            Square lastMove = new Square(i, j);

                            //Receiving the min of the big board minimax vs the current bestval
                            bestVal = min(bestVal, b.miniMax(depth + 1, !isMaximizingPlayer, lastMove, alpha, beta));

                            //Undo the move
                            smallBoard[i][j] = emptySpace;

                            beta = min(beta, bestVal);
                            if (beta <= alpha)
                            {
                                break;
                            }

                        }
                    }
                }
            }
        }
        System.out.println("Smallboard Minimax Best Val: " + bestVal);
        return bestVal;
    }

    @Override
    public String toString(){
        String s = new String();

        for(int i = 0; i < smallBoard.length; i++){
            s += "|" + smallBoard[i][0] + "|" + smallBoard[i][1] + "|" + smallBoard[i][2] + "|\n";

        }

        return s;
    }

    /**
     * Getter for the char that contains the winning player's letter.
     * @return The winning player's letter or '-' if no winner.
     */
    public char getHasBeenWonBy() {
        return this.hasBeenWonBy;
    }

    /**
     * Setter for the char that contains the winning player's letter.
     * @param hasBeenWonBy The player that has won the board.
     */
    public void setHasBeenWon(char hasBeenWonBy) {
        this.hasBeenWonBy = hasBeenWonBy;
    }
}
