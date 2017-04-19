package models;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.min;
import static java.lang.Math.max;

/**
 * Ultimate Tic Tac Toe Controller class.
 * This is the controller class for the on-screen view.
 * This class links up the the fxml file to manipulate all of the UI elements created by JavaFX.
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

    private final char xSpace = 'X'; //Player
    private final char oSpace = 'O'; //Computer
    private final char emptySpace = '_';

    public BigBoard(){
        isUserMove = true;
        for(int i = 0; i < smallBoards.length; i++){
            for(int j = 0; j < smallBoards[0].length; j++){
                smallBoards[i][j] = new SmallBoard();
            }
        }
    }

    public boolean isGameOver(){
        boolean b = false;

        if(hasXWon() || hasOWon() || getAvailableMoves().isEmpty()){
            b = true;
        }
        return b;
    }

    public List<Square> getAvailableMoves(Square lastMove){

        availableMoves = new ArrayList<>();

            if(smallBoards[lastMove.getX()][lastMove.getY()].isActive == true){
                availableMoves.add(new Square(lastMove.getX(), lastMove.getY()));
            }
        return availableMoves;

    }

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

//    public Square findBestMove(Square lastMove){
//        int alpha = -1000;
//        int beta = 1000;
//        int bestVal = 0;
//        Square bestMove = new Square();
//
//        int moveVal = miniMax(0, false, lastMove, alpha, beta);
//
//        if(moveVal > bestVal)
//        {
//            bestMove.setX(lastMove.getX());
//            bestMove.setY(lastMove.getY());
//            bestVal = moveVal;
//        }
//
//        return bestMove;
//    }

    public int miniMax(int depth, boolean isMaximizingPlayer, Square lastMove, int alpha, int beta)
    {

        int bestVal = 0;

        if (hasXWon())
        {
            return 10000 - depth;
        }
        else if (hasOWon())
        {
            return -10000 + depth;
        }
        else if(getAvailableMoves().isEmpty() && isMaximizingPlayer){
            return 800;
        }
        else if(getAvailableMoves().isEmpty() && !isMaximizingPlayer){
            return -800;
        }
        else if (depth >= 10 && isMaximizingPlayer) {
            return bestVal;
        }
        else if(depth >= 10 && !isMaximizingPlayer){
            return bestVal;
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

                bestVal = max(bestVal, smallBoards[lastMove.getX()][lastMove.getY()].miniMax(depth, isMaximizingPlayer, this, alpha, beta));
                System.out.println("Bigboard BestVal: " + bestVal);
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


                bestVal = min(bestVal, smallBoards[lastMove.getX()][lastMove.getY()].miniMax(depth, isMaximizingPlayer, this, alpha, beta));
                System.out.println("Bigboard BestVal: " + bestVal);
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
