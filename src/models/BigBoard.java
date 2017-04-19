package models;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.min;
import static java.lang.Math.max;

/**
 * Ultimate Tic Tac Toe big board model class.
 * This class is part of the model to represent the ultimate tic tac toe board.
 * Artificial Intelligence.
 * April 19th, 2017.
 * @author Matthew Gimbut
 * @author Michael Moscariello
 */
public class BigBoard
{
    public SmallBoard[][] smallBoards = new SmallBoard[3][3];
    private boolean isUserMove;

    List<Square> availableMoves;

    private final char xSpace = 'X'; //Player; not used for BigBoard
    private final char oSpace = 'O'; //Computer; not used for BigBoard
    private final char emptySpace = '_'; //not used for bigBoard

    /**
     * Constructor for BigBoard; initializes a 3 x 3 grid of SmallBoards
     */
    public BigBoard(){
        isUserMove = true;
        for(int i = 0; i < smallBoards.length; i++){
            for(int j = 0; j < smallBoards[0].length; j++){
                smallBoards[i][j] = new SmallBoard();
            }
        }
    }

    /**
     * Checks if the current bigboard game is over; this method is not currently used for this class
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver(){
        boolean b = false;

        if(hasXWon() || hasOWon() || getAvailableMoves().isEmpty()){
            b = true;
        }
        return b;
    }

    /**
     * Returns the location of any SmallBoards that have available moves to take
     * @param lastMove The last move of either player
     * @return a list of available SmallBoard Squares.
     */
    public List<Square> getAvailableMoves(Square lastMove){

        availableMoves = new ArrayList<>();

            if(smallBoards[lastMove.getX()][lastMove.getY()].isActive == true){
                availableMoves.add(new Square(lastMove.getX(), lastMove.getY()));
            }
        return availableMoves;

    }

    /**
     * returns the location of any smallBoards that have available moves to take
     * @return A list of available SmallBoard Squares
     */
    public List<Square> getAvailableMoves(){
        availableMoves = new ArrayList<>();
        for(int i = 0; i < smallBoards.length; i++){
            for(int j = 0; j < smallBoards[0].length; j++){
                if(smallBoards[i][j].isActive == true){
                    availableMoves.add(new Square(i, j));
                }
            }
        }
        return availableMoves;
    }

    /**
     * Checks if the entire game has been won by O
     * @return true if the game has been won by O, false otherwise
     */
    public boolean hasOWon(){
        //Check for Diagonal Wins
        if ((smallBoards[0][0].getHasBeenWonBy() == 'O' && smallBoards[1][1].getHasBeenWonBy() == 'O' && smallBoards[2][2].getHasBeenWonBy() == 'O')
                || (smallBoards[0][2].getHasBeenWonBy() == 'O' && smallBoards[1][1].getHasBeenWonBy() == 'O' && smallBoards[2][0].getHasBeenWonBy() == 'O')) {
            return true;
        }
        for(int i = 0; i < smallBoards.length; i++){
            //Check for row wins
            if(smallBoards[i][0].getHasBeenWonBy() == 'O' && smallBoards[i][1].getHasBeenWonBy() == 'O' && smallBoards[i][2].getHasBeenWonBy() == 'O'){
                return true;
            }
            //Check for column wins
            else if(smallBoards[0][i].getHasBeenWonBy() == 'O' && smallBoards[1][i].getHasBeenWonBy() == 'O' && smallBoards[2][i].getHasBeenWonBy() == 'O'){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the entire game has been won by X
     * @return true if the game has been won by X, false otherwise
     */
    public boolean hasXWon(){
        //Check for Diagonal Wins
        if ((smallBoards[0][0].getHasBeenWonBy() == 'X' && smallBoards[1][1].getHasBeenWonBy() == 'X' && smallBoards[2][2].getHasBeenWonBy() == 'X')
                || (smallBoards[0][2].getHasBeenWonBy() == 'X' && smallBoards[1][1].getHasBeenWonBy() == 'X' && smallBoards[2][0].getHasBeenWonBy() == 'X')) {
            return true;
        }
        for(int i = 0; i < smallBoards.length; i++){
            //Check for row wins
            if(smallBoards[i][0].getHasBeenWonBy() == 'X' && smallBoards[i][1].getHasBeenWonBy() == 'X' && smallBoards[i][2].getHasBeenWonBy() == 'X'){
                return true;
            }
            //Check for column wins
            else if(smallBoards[0][i].getHasBeenWonBy() == 'X' && smallBoards[1][i].getHasBeenWonBy() == 'X' && smallBoards[2][i].getHasBeenWonBy() == 'X'){
                return true;
            }
        }
        return false;
    }

    /**
     * The minimax algorithm for the game AI
     * @param depth The depth of the current node being looked at
     * @param isMaximizingPlayer The current player; Human = true; Computer = false
     * @param lastMove The last move taken in the previous SmallBoard Minimax
     * @param alpha Alpha value necessary for alpha-beta pruning
     * @param beta Beta value necessary for alpha-beta pruning
     * @return The best value received from the recursive minimax algorithm
     */
    public int miniMax(int depth, boolean isMaximizingPlayer, Square lastMove, int alpha, int beta)
    {

        int bestVal = 0;

        //Base Cases
        if (hasXWon())
        {
            //Occurs if the game has been won by X
            return 10000 - depth;
        }
        else if (hasOWon())
        {
            //Occurs if the game has been won by O
            return -10000 + depth;
        }
        else if(getAvailableMoves().isEmpty() && isMaximizingPlayer){
            //Occurs if there are no available moves left and it is the human player's turn
            return 800;
        }
        else if(getAvailableMoves().isEmpty() && !isMaximizingPlayer){
            //Occurs if there are no available moves left and it is the computer player's turn
            return -800;
        }
        else if (depth >= 7 && smallBoards[lastMove.getX()][lastMove.getY()].countOSpots() >
                smallBoards[lastMove.getX()][lastMove.getY()].countXSpots()) {
            //Occurs if The depth limit has been reached and more squares in the last move have been taken by O
            return -100 * smallBoards[lastMove.getX()][lastMove.getY()].countOSpots();
        }
        else if (depth >= 7 && smallBoards[lastMove.getX()][lastMove.getY()].countXSpots() >
                smallBoards[lastMove.getX()][lastMove.getY()].countOSpots()) {
            //Occurs if The depth limit has been reached and more squares in the last move have been taken by X
            return 100 * smallBoards[lastMove.getX()][lastMove.getY()].countXSpots();
        }
        else if(depth >= 7 && smallBoards[lastMove.getX()][lastMove.getY()].countOSpots() ==
                smallBoards[lastMove.getX()][lastMove.getY()].countXSpots()){
            //Neither player is in the lead; return 0
            return 0;
        }
        else {

            List<Square> statesAvailable = getAvailableMoves(lastMove);
            if (statesAvailable.isEmpty() && isMaximizingPlayer)
            {
                return 800;
            }else if(statesAvailable.isEmpty() && !isMaximizingPlayer){
                return -800;
            }

            if (isMaximizingPlayer)
            {
                bestVal = -1000;

                //bestVal receives the max of the next SmallBoard minimax (with last move's coordinates)
                // vs bestVal
                bestVal = max(bestVal, smallBoards[lastMove.getX()][lastMove.getY()].miniMax(depth, isMaximizingPlayer, this, alpha, beta));
                System.out.println("Bigboard BestVal: " + bestVal);

                //For use with alpha-beta pruning
                alpha = max(alpha, bestVal);
                if (beta >= alpha)
                {
                    return bestVal;
                }

            }
            else
            {
                //Minimax will always start with Minimizing (computer) player
                bestVal = 1000;

                //bestVal receives the min of the next SmallBoard minimax (with last move's coordinates)
                //vs bestVal
                bestVal = min(bestVal, smallBoards[lastMove.getX()][lastMove.getY()].miniMax(depth, isMaximizingPlayer, this, alpha, beta));
                System.out.println("Bigboard BestVal: " + bestVal);

                //For use with alpha-beta pruning
                beta = min(beta, bestVal);
                if (beta <= alpha)
                {
                    return bestVal;
                }

            }
        }
        System.out.println("BigBoard Minimax Best Val: " + bestVal);
        return bestVal;
    }

    /**
     * Getter for the boolean that keeps track of whether it's the user's turn.
     * @return Whether or not it's the user's turn.
     */
    public boolean isUserMove() {
        return isUserMove;
    }

    /**
     * Setter for the boolean that keeps track of whether it's the user's turn.
     * @param userMove Whether or not it's the user's turn.
     */
    public void setUserMove(boolean userMove) {
        isUserMove = userMove;
    }

    @Override
    public String toString(){
        String s = new String();

        for(int i = 0; i < smallBoards.length; i++){
            for(int j = 0; j < smallBoards[0].length; j++){
                s += smallBoards[i][j].toString();
            }
            s += "\n";
        }

        return s;
    }
}
