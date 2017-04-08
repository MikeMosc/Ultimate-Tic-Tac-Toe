package controllers;

import gui.MainStage;
import models.SmallBoard;
import models.Square;

public class Main {

    public static void main(String[] args) {

        new MainStage();

        SmallBoard b = new SmallBoard();
        System.out.println(b.toString());

        System.out.println("Select turn:\\n\\n1. Computer 2. User: ");
        int choice = b.scan.nextInt();
        if(choice == 1){
            Square p = new Square(0, 0);
            b.placeMove(p, 'O');
            System.out.println(b.toString());
        }

        while (!b.isGameOver()) {
            System.out.println("Your move: ");
            Square userMove = new Square(b.scan.nextInt(), b.scan.nextInt());

            b.placeMove(userMove, 'X'); //2 for O and O is the user
            System.out.println(b.toString());
            if (b.isGameOver()) {
                break;
            }
            Square cpu = new Square();
            cpu = b.findBestMove();
            b.placeMove(cpu, 'O');
            System.out.println(b.toString());
        }
        if (b.hasOWon()) {
            System.out.println("Unfortunately, you lost!");
        } else if (b.hasXWon()) {
            System.out.println("You win! This is not going to get printed.");
        } else {
            System.out.println("It's a draw!");
        }

    }
}
