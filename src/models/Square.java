package models;

/**
 * Created by Mike on 4/4/2017.
 */
public class Square
{
    int x, y;
    int score = 0;

    public Square(int x, int y){
        this.x = x;
        this.y = y;
    }

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
