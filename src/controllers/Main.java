package controllers;

import gui.MainStage;
import models.BigBoard;
import models.SmallBoard;
import models.Square;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        try {
            MainStage main = new MainStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Square lastMove = new Square(0,0);
        //SmallBoard b = new SmallBoard();

        BigBoard b = new BigBoard();
        System.out.print(b.toString());

        System.out.println(b.toString());

        System.out.println("Select turn:\\n\\n1. Computer 2. User: ");
        int choice = scan.nextInt();
        if(choice == 1){
            Square p = new Square(2, 0);
            lastMove = b.smallBoards[lastMove.getX()][lastMove.getY()].placeMove(p, 'O');
            System.out.println(b.toString());
        }

        while (!b.isGameOver()) {
            System.out.println("Your move: ");
            Square userMove = new Square(scan.nextInt(), scan.nextInt());

            lastMove = b.smallBoards[lastMove.getX()][lastMove.getY()].placeMove(userMove, 'X'); //2 for O and O is the user
            System.out.println(b.toString());
            if (b.isGameOver()) {
                break;
            }
            Square cpu = new Square();
            cpu = b.smallBoards[lastMove.getX()][lastMove.getY()].findBestMove(b);
            lastMove = b.smallBoards[lastMove.getX()][lastMove.getY()].placeMove(cpu, 'O');
            System.out.println(b.toString());
        }
        if (b.hasOWon()) {
            System.out.println("Unfortunately, you lost!");
        } else if (b.hasXWon()) {
            System.out.println("You win! This is not going to get printed.");
        } else {
            System.out.println("It's a draw!");
        }*/

    }
}
