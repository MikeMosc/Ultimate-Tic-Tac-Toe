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
}
