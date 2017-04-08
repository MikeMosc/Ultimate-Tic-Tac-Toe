package models;

import interfaces.Board;

/**
 * Created by Mike on 4/3/2017.
 */
public class BigBoard
{
    private SmallBoard[][] smallBoards = new SmallBoard[3][3];

    private final char xSpace = 'X'; //Player
    private final char oSpace = 'O'; //Computer
    private final char emptySpace = '_';

    public BigBoard(){
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

    public boolean hasOWon(){
        //Check for Diagonal Wins
        if ((smallBoards[0][0].hasOWon() && smallBoards[1][1].hasOWon() && smallBoards[2][2].hasOWon())
                || (smallBoards[0][2].hasOWon() && smallBoards[1][1].hasOWon() && smallBoards[2][0].hasOWon())) {
            return true;
        }
        for(int i = 0; i < smallBoards.length; i++){
            //Check for row wins
            if(smallBoards[i][0].hasOWon() && smallBoards[i][1].hasOWon() && smallBoards[i][2].hasOWon()){
                return true;
            }
            //Check for column wins
            else if(smallBoards[0][i].hasOWon() && smallBoards[1][i].hasOWon() && smallBoards[2][i].hasOWon()){
                return true;
            }
        }
        return false;
    }

    public boolean hasXWon(){
        //Check for Diagonal Wins
        if ((smallBoards[0][0].hasXWon() && smallBoards[1][1].hasXWon() && smallBoards[2][2].hasXWon())
                || (smallBoards[0][2].hasXWon() && smallBoards[1][1].hasXWon() && smallBoards[2][0].hasXWon())) {
            return true;
        }
        for(int i = 0; i < smallBoards.length; i++){
            //Check for row wins
            if(smallBoards[i][0].hasXWon() && smallBoards[i][1].hasXWon() && smallBoards[i][2].hasXWon()){
                return true;
            }
            //Check for column wins
            else if(smallBoards[0][i].hasXWon() && smallBoards[1][i].hasXWon() && smallBoards[2][i].hasXWon()){
                return true;
            }
        }
        return false;
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
