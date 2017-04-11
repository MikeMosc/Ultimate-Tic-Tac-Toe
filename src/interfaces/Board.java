package interfaces;

import models.BigBoard;
import models.Square;

/**
 * Created by Mike on 4/5/2017.
 */
public interface Board
{

    public boolean isGameOver();
    public boolean hasXWon();
    public boolean hasOWon();
    public Square findBestMove(BigBoard b);
    public int miniMax(int depth, boolean isMaximizingPlayer, BigBoard b, int alpha, int beta);

}
