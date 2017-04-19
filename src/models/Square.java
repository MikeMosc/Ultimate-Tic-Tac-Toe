package models;

/**
 * Created by Mike Moscariello on 4/4/2017.
 * This class represents a space on a tic tac toe board and is important for remembering the last move that was taken.
 */
public class Square
{
    int x, y; //X and Y coordinates on a tic tac toe board.
    int score = 0; //Depreciated; not used in Ultimate Tic Tac Toe

    /**
     * Overloaded constructor with an x and y coordinate.
     * @param x
     * @param y
     */
    public Square(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Default constructor
     */
    public Square(){

    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getScore(){
        return score;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setScore(int score){
        this.score = score;
    }

    public String toString(){
        return "[" + x + ", " + y + "]";
    }
}
