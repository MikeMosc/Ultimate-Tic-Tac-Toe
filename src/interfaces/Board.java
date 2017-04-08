package interfaces;

import models.Square;

/**
 * Created by Mike on 4/5/2017.
 */
public interface Board
{

    public boolean isGameOver();
    public boolean hasXWon();
    public boolean hasOWon();
    public Square findBestMove();
    public int miniMax(int depth, boolean isMaximizingPlayer);

}
