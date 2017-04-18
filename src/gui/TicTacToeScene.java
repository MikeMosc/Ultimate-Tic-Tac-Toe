package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import models.BigBoard;
import models.Square;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Ultimate Tic Tac Toe Controller class.
 * This is the controller class for the on-screen view.
 * This class links up the the fxml file to manipulate all of the UI elements created by JavaFX.
 * Artificial Intelligence.
 * April 19th, 2017.
 * @author Matthew Gimbut
 * @author Michael Moscariello
 */
public class TicTacToeScene extends BorderPane {

    //Variables corresponding to UI elements from the fxml file.
    @FXML private AnchorPane anchor;
    @FXML private GridPane bigBoardGrid;
    @FXML private Label message;
    @FXML private Button newGame;
    /*
        Each of the 9 small boards have a hard-coded JavaFX GridPane.
        These GridPanes are added to a 2D array so we can access them in parallel with their
        corresponding SmallBoard objects.
        The first number represents the row, the second represents the column.
     */
    @FXML private GridPane small00;
    @FXML private GridPane small01;
    @FXML private GridPane small02;
    @FXML private GridPane small10;
    @FXML private GridPane small11;
    @FXML private GridPane small12;
    @FXML private GridPane small20;
    @FXML private GridPane small21;
    @FXML private GridPane small22;

    @FXML private Label xInfo;
    @FXML private Label oInfo;

    private boolean begin;
    private boolean gameOver;


    //CSS styles for each of the different colors to be used.
    private final String INACTIVE = "-fx-background-color: rgb(255, 59, 71);";
    private final String ACTIVE = "-fx-background-color: rgb(0, 255, 74);";
    private final String X_WIN = "-fx-background-color: rgb(73, 132, 226);";
    private final String O_WIN = "-fx-background-color: rgb(255, 242, 77);";
    private final String X_TEXT = "-fx-text-fill: rgb(73, 132, 226);";
    private final String O_TEXT = "-fx-text-fill: rgb(200, 210, 100);";
    private final String TRANSPARENT = "-fx-background-color: transparent;";

    //2D Array that contains all of the gridpanes in a tic tac toe board like structure.
    private GridPane[][] grids;

    //ArrayList that all 81 buttons are added to in order to modify them all at once easily.
    private ArrayList<Button> buttons;

    //The main BigBoard game object.
    private BigBoard bigBoard;

    /**
     * Constructor for the TicTacToeScene.
     */
    TicTacToeScene() {
        grids = new GridPane[3][3];
        begin = true;
        gameOver = false;
        bigBoard = new BigBoard();
        buttons = new ArrayList<>();

        //Loads fxml file into the Scene.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(
                "gui\\tictactoe.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Error or something");
        }
    }



    /**
     * Initialize method for JavaFX.
     * This method is called by JavaFX immediately after the fxml file is finished being loaded by the constructor.
     * We use this method to initialize all UI elements.
     */
    @FXML void initialize() {
        //Assigns each of the 9 small grids their corresponding location in the 2D array.
        grids[0][0] = small00;
        grids[0][1] = small01;
        grids[0][2] = small02;
        grids[1][0] = small10;
        grids[1][1] = small11;
        grids[1][2] = small12;
        grids[2][0] = small20;
        grids[2][1] = small21;
        grids[2][2] = small22;

        xInfo.setStyle(X_TEXT);
        oInfo.setStyle(O_TEXT);

        resetGrid();

        /**
         * Uses a lambda to set the ActionEvent Listener for the new game button.
         * Removes all the previous contents of the game board, sets text strings back to default, and reset the backend board.
         */
        newGame.setOnAction(event -> {
            removeContents();
            resetGrid();
            xInfo.setText("");
            oInfo.setText("");
            buttons.forEach(button -> button.setDisable(false));
            //First two for loops go through the rows and columns of the big board.
            for(int i = 0; i <  bigBoard.smallBoards.length; i++) {
                for(int j = 0; j <  bigBoard.smallBoards[i].length; j++) {
                    bigBoard.smallBoards[i][j].setHasBeenWon('-');
                    //Second two for loops are to go through the rows and columns of the current small board.
                    for(int k = 0; k < bigBoard.smallBoards[i][j].smallBoard.length; k++) {
                        for(int l = 0; l < bigBoard.smallBoards[i][j].smallBoard[k].length; l++) {
                            bigBoard.smallBoards[i][j].smallBoard[k][l] = '-';
                        }
                    }
                }
            }
            message.setText("Ultimate Tic Tac Toe");
        });

        this.setCenter(anchor);
    }

    /**
     * Removes all child nodes from each of the GridPanes to reset the board.
     */
    private void removeContents() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                ArrayList<BorderPane> removal = grids[i][j].getChildren()
                        .stream()
                        .filter(node -> node instanceof BorderPane)
                        .map(node -> (BorderPane) node)
                        .collect(Collectors.toCollection(ArrayList::new));
                grids[i][j].getChildren().removeAll(removal);
            }
        }
    }

    /**
     * Resets the contents of each GridPane.
     * Recreates each of the BorderPanes and each Button within them.
     */
    private void resetGrid() {
        buttons.clear();
        //First two loops go through the GridPanes that represent each small board.
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                grids[i][j].getStyleClass().removeAll("in-active", "o-win", "x-win");
                grids[i][j].setStyle(ACTIVE);
                //Next two loops go through the rows and columns of each small board.
                for(int k = 0; k < 3; k++) {
                    for(int l = 0; l < 3; l++){
                        BorderPane bp = new BorderPane();
                        Button b = new Button("-");
                        //Adds a new TicTacToe listener to the button with the corresponding row and column numbers (i, j, k, l)
                        //of the BigBoard and SmallBoard coordinates and a reference to the button object itself.
                        b.setOnAction(new TicTacToeListener(i, j, k, l, b));
                        b.setStyle(TRANSPARENT);
                        bp.setCenter(b);
                        buttons.add(b);
                        //Adds the BorderPane to a specific grid location (row=k, column=l) in the GridPane.
                        grids[i][j].add(bp, l, k);
                    }
                }
            }
        }
    }

    /**
     * Returns a specific cell on the board to the user.
     * @param bigRow The row of the big board.
     * @param bigCol The column of the big board.
     * @param smallRow The row of the small board.
     * @param smallCol The column of the small board.
     * @return The Button node of the cell requested.
     */
    public Button getByCell(int bigRow, int bigCol, int smallRow, int smallCol) {
        GridPane pane = grids[bigRow][bigCol];
        for(Node curr : pane.getChildren()) {
            if(curr instanceof BorderPane && pane.getRowIndex(curr) == smallRow && pane.getColumnIndex(curr) == smallCol) {
                for(Node child : ((BorderPane) curr).getChildren()) {
                    if (child instanceof Button) {
                        return (Button) child;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Changes each of the 9 small boards to display the appropriate color.
     */
    private void displayAllColors() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                grids[i][j].getStyleClass().removeAll("in-active", "o-win", "x-win"); //Removes previous styles.
                if(bigBoard.smallBoards[i][j].getHasBeenWonBy() == 'O') {
                    grids[i][j].setStyle(O_WIN);
                } else if(bigBoard.smallBoards[i][j].getHasBeenWonBy() == 'X') {
                    grids[i][j].setStyle(X_WIN);
                } else {
                    grids[i][j].setStyle(INACTIVE);
                }
                //Sets button colors back to transparent.
                grids[i][j].getChildren()
                        .stream()
                        .filter(node -> node instanceof Button)
                        .forEach(node -> node.setStyle(TRANSPARENT));
            }
        }
    }

    /**
     * Checks to see if any cells need to be modified because of a recent win.
     * @param bigRow The row of the small board just played in.
     * @param bigCol The column of the small board just played in.
     */
    private void modifyForWins(int bigRow, int bigCol) {
        modifySmallWins(bigRow, bigCol);
        modifyBigWins(bigRow, bigCol);
    }

    /**
     * Checks to see if the most recently played in small board needs to be updated because of a win.
     * @param bigRow The row of the small board just played in.
     * @param bigCol The column of the small board just played in.
     */
    private void modifySmallWins(int bigRow, int bigCol) {
        if(bigBoard.smallBoards[bigRow][bigCol].getHasBeenWonBy() == '-') {
            if(bigBoard.smallBoards[bigRow][bigCol].hasOWon()) {
                setOWonColors(bigRow, bigCol);
                oInfo.setText("Player O has won board small board [" + bigRow + " , " + bigCol + "]!");
                bigBoard.smallBoards[bigRow][bigCol].setHasBeenWon('O');
            } else if (bigBoard.smallBoards[bigRow][bigCol].hasXWon()) {
                setXWonColors(bigRow, bigCol);
                xInfo.setText("Player X has won board small board [" + bigRow + " , " + bigCol + "]!");
                bigBoard.smallBoards[bigRow][bigCol].setHasBeenWon('X');
            }
        }
    }

    /**
     * Sets the colors of a small board if player 'O' has won.
     * @param bigRow The row of the small board just played in.
     * @param bigCol The column of the small board just played in.
     */
    private void setOWonColors(int bigRow, int bigCol) {
        grids[bigRow][bigCol].setStyle(O_WIN);
        grids[bigRow][bigCol].getChildren()
                .stream()
                .filter(node -> node instanceof Button)
                .forEach(node -> node.setStyle(TRANSPARENT));
    }

    /**
     * Sets the colors of a small board if player 'X' has won.
     * @param bigRow The row of the small board just played in.
     * @param bigCol The column of the small board just played in.
     */
    private void setXWonColors(int bigRow, int bigCol) {
        grids[bigRow][bigCol].setStyle(X_WIN);
        grids[bigRow][bigCol].getChildren()
                .stream()
                .filter(node -> node instanceof Button)
                .forEach(node -> node.setStyle(TRANSPARENT));
    }

    /**
     * Modifies the UI if there is a winner in the big board.
     * Ends the game if there is.
     * @param bigRow The row of the small board just played in.
     * @param bigCol The column of the small board just played in.
     */
    private void modifyBigWins(int bigRow, int bigCol) {
        if(bigBoard.hasXWon()) {
            message.setText("X has won!");
            setXWonColors(bigRow, bigCol);
            gameOver = true;
        } else if (bigBoard.hasOWon()) {
            message.setText("O has won!");
            setOWonColors(bigRow, bigCol);
            gameOver = true;
        }
    }

    /**
     * Similar to displayAllColors, but this one sets the color of the new board to play in as green.
     * All other boards, if not won by a player, are set to red.
     * @param bigRow The row number of the small board.
     * @param bigCol The column number of the small board.
     * @param smallRow The row number of the button.
     * @param smallCol The column number of the button.
     */
    private void resetColors(int bigRow, int bigCol, int smallRow, int smallCol) {
        if(bigBoard.smallBoards[bigRow][bigCol].getHasBeenWonBy() == 'O') {
            grids[bigRow][bigCol].setStyle(O_WIN);
        } else if(bigBoard.smallBoards[bigRow][bigCol].getHasBeenWonBy() == 'X') {
            grids[bigRow][bigCol].setStyle(X_WIN);
        } else {
            grids[bigRow][bigCol].setStyle(INACTIVE);
        }

        //Sets all buttons back to transparent backgrounds.
        grids[bigRow][bigCol].getChildren()
                .stream()
                .filter(node -> node instanceof Button)
                .forEach(node -> node.setStyle(TRANSPARENT));

        grids[smallRow][smallCol].setStyle(ACTIVE);
        grids[smallRow][smallCol].getChildren()
                .stream()
                .filter(node -> node instanceof Button)
                .forEach(node -> node.setStyle(TRANSPARENT));
    }

    /**
     * Inner class to create the EventHandler for the Buttons.
     */
    private class TicTacToeListener implements EventHandler<ActionEvent> {

        private int bigRow, bigCol, smallRow, smallCol;
        private Button source;

        /**
         * Constructor for the Listener.
         * @param bigRow The row location of the small board that contains the button.
         * @param bigCol The column location of the small board that contains the button.
         * @param smallRow The row location of the button.
         * @param smallCol The column location of the button.
         * @param source A reference to the button itself.
         */
        TicTacToeListener(int bigRow, int bigCol, int smallRow, int smallCol, Button source) {
            this.bigRow = bigRow;
            this.bigCol = bigCol;
            this.smallRow = smallRow;
            this.smallCol = smallCol;
            this.source = source;
        }

        /**
         * The method that is executed every time the button is pressed.
         * @param event The ActionEvent object corresponding to the event that was fired.
         */
        @Override public void handle(ActionEvent event) {
            Square cpu = new Square();
            //First checks to see if the square clicked in is active, if it's the human player's turn, and if the game is not over.
            if(grids[bigRow][bigCol].getStyle().equals(ACTIVE) && bigBoard.isUserMove() && !gameOver) {
                if(begin) {
                    displayAllColors();
                }
                if (source.getText().equals("-")) { //Checks to see if the click is in a valid location.
                    source.setText("X");
                    Square player = new Square();
                    player.setX(smallRow);
                    player.setY(smallCol);
                    bigBoard.smallBoards[bigRow][bigCol].placeMove(player, 'X'); //Assigns the player's move.
                    xInfo.setText("Player X moved to large board: [" + bigRow + " , " + bigCol + "]\n" +
                            "Player X moved to small board: [" + smallRow + " , " + smallCol + "]");

                    //Checks for wins and adjusts colors.
                    modifyForWins(bigRow, bigCol);
                    resetColors(bigRow, bigCol, smallRow, smallCol);

                    bigBoard.setUserMove(false);

                    if(!gameOver) { //If the game hasn't ended, make computer move.
                        cpu = bigBoard.smallBoards[smallRow][smallCol].findBestMove(bigBoard);

                        //Assigning old small locations to new region to pick valid big board
                        bigRow = smallRow;
                        bigCol = smallCol;
                        //Assigning new small board values from CPU choice
                        smallRow = cpu.getX();
                        smallCol = cpu.getY();
                        //Programmatically clicks the button corresponding to computer's choice.
                        getByCell(bigRow, bigCol, smallRow, smallCol).fire();
                        bigBoard.smallBoards[bigRow][bigCol].placeMove(cpu, 'O');
                        oInfo.setText("Player O moved to large board: [" + bigRow + " , " + bigCol + "]\n" +
                                "Player O moved to small board: [" + smallRow + " , " + smallCol + "]");
                        //Checks for wins and adjusts colors.
                        modifyForWins(bigRow, bigCol);
                        resetColors(bigRow, bigCol, smallRow, smallCol);
                    } else {
                        //Disables all buttons if game is over.
                        buttons.forEach(button -> button.setDisable(true));
                    }
                } else {
                    xInfo.setText("Spot already taken!");
                }
            } else if(!bigBoard.isUserMove() && !gameOver) { //Executed when the computer "presses" a button.
                source.setText("O");
                oInfo.setText("Player O moved to large board: [" + bigRow + " , " + bigCol + "]\n" +
                        "Player O moved to small board: [" + smallRow + " , " + smallCol + "]");

                //Checks for wins and adjusts colors.
                modifyForWins(bigRow, bigCol);
                resetColors(bigRow, bigCol, smallRow, smallCol);

                bigBoard.setUserMove(true);
            } else if(gameOver) {
                buttons.forEach(button -> button.setDisable(true));
            } else {
                xInfo.setText("Can't play there!");
            }
        }
    }
}
