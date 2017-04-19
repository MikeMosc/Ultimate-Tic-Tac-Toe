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
    boolean isActive = true;
    private char hasBeenWonBy;

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

    public boolean isGameOver(){
        boolean b = false;

        if(hasXWon() || hasOWon() || getAvailableMoves().isEmpty()){
            b = true;
            isActive = false;
        }

        return b;
    }

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

    public Square placeMove(Square square, char player){
        smallBoard[square.getX()][square.getY()] = player;
        return square;
    }

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

                    if(moveVal > bestVal){
                        bestMove.setX(i);
                        bestMove.setY(j);
                        bestVal = moveVal;
                    }
                }
            }
        }
        System.out.println("BestMove = " + bestVal);
        return bestMove;
    }

    public int miniMax(int depth, boolean isMaximizingPlayer, BigBoard b, int alpha, int beta){

        int bestVal = 0;

        if (hasXWon()){
            return 1000 - depth;
        }
        else if (hasOWon()){
            return -1000 + depth;
        }
        else if(getAvailableMoves().isEmpty()){
            return bestVal;
        }
        else if(depth >= 7){
            return bestVal;
        }
        else{

            List<Square> statesAvailable = getAvailableMoves();
            if (statesAvailable.isEmpty())
                return 0;

            if (isMaximizingPlayer)
            {
                bestVal = -1000;
                for (int i = 0; i < smallBoard.length; i++)
                {
                    for (int j = 0; j < smallBoard[0].length; j++)
                    {
                        if (smallBoard[i][j] == emptySpace)
                        {

                            smallBoard[i][j] = xSpace;
                            Square lastMove = new Square(i, j);

                            bestVal = max(bestVal, b.miniMax(depth + 1, !isMaximizingPlayer, lastMove, alpha, beta));
                            alpha = max(alpha, bestVal);


                            //Undo the move
                            smallBoard[i][j] = emptySpace;

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
                            smallBoard[i][j] = oSpace;
                            Square lastMove = new Square(i, j);


                            //bestVal = min(bestVal, miniMax(depth+1, !isMaximizingPlayer, b, alpha, beta));
                            bestVal = min(bestVal, b.miniMax(depth + 1, !isMaximizingPlayer, lastMove, alpha, beta));

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
