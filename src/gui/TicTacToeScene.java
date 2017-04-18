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

public class TicTacToeScene extends BorderPane {

    @FXML private AnchorPane anchor;
    @FXML private GridPane bigBoardGrid;
    @FXML private Label message;
    @FXML private Button newGame;
    @FXML private GridPane small00;
    @FXML private GridPane small01;
    @FXML private GridPane small02;
    @FXML private GridPane small10;
    @FXML private GridPane small11;
    @FXML private GridPane small12;
    @FXML private GridPane small20;
    @FXML private GridPane small21;
    @FXML private GridPane small22;
    @FXML private Label info;
    private boolean begin;
    private boolean gameOver;
    private final String INACTIVE = "-fx-background-color: rgb(255, 59, 71);";
    private final String ACTIVE = "-fx-background-color: rgb(0, 255, 74);";
    private final String X_WIN = "-fx-background-color: rgb(73, 132, 226);";
    private final String O_WIN = "-fx-background-color: rgb(255, 242, 77);";
    private final String TRANSPARENT = "-fx-background-color: transparent;";

    private GridPane[][] grids;
    private ArrayList<Button> buttons;

    private BigBoard bigBoard;

    TicTacToeScene() {
        grids = new GridPane[3][3];
        begin = true;
        gameOver = false;
        bigBoard = new BigBoard();
        buttons = new ArrayList<>();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(
                "gui\\tictactoe.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Error or something");
        }
    }

    @FXML
    void initialize() {
        grids[0][0] = small00;
        grids[0][1] = small01;
        grids[0][2] = small02;
        grids[1][0] = small10;
        grids[1][1] = small11;
        grids[1][2] = small12;
        grids[2][0] = small20;
        grids[2][1] = small21;
        grids[2][2] = small22;

        resetGrid();

        newGame.setOnAction(event -> {
            removeContents();
            resetGrid();
            info.setText("");
            buttons.forEach(button -> button.setDisable(false));
            //TODO Reset back end grids and shit
            message.setText("Ultimate Tic Tac Toe");
        });

        this.setCenter(anchor);
    }

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

    private void resetGrid() {
        buttons.clear();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                grids[i][j].getStyleClass().removeAll("in-active", "o-win", "x-win");
                grids[i][j].setStyle(ACTIVE);
                for(int k = 0; k < 3; k++) {
                    for(int l = 0; l < 3; l++){
                        BorderPane bp = new BorderPane();
                        Button b = new Button("-");
                        b.setOnAction(new TicTacToeListener(i, j, k, l, b));
                        b.setStyle(TRANSPARENT);
                        bp.setCenter(b);
                        buttons.add(b);
                        grids[i][j].add(bp, l, k);
                    }
                }
            }
        }
    }

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

    private void displayAllColors() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                grids[i][j].getStyleClass().removeAll("in-active", "o-win", "x-win");
                if(bigBoard.smallBoards[i][j].getHasBeenWonBy() == 'O') {
                    grids[i][j].setStyle(O_WIN);
                } else if(bigBoard.smallBoards[i][j].getHasBeenWonBy() == 'X') {
                    grids[i][j].setStyle(X_WIN);
                } else {
                    grids[i][j].setStyle(INACTIVE);
                }
                grids[i][j].getChildren()
                        .stream()
                        .filter(node -> node instanceof Button)
                        .forEach(node -> node.setStyle(TRANSPARENT));
            }
        }
    }

    private void modifyForWins(int bigRow, int bigCol) {
        modifySmallWins(bigRow, bigCol);
        modifyBigWins(bigRow, bigCol);
    }

    private void modifySmallWins(int bigRow, int bigCol) {
        if(bigBoard.smallBoards[bigRow][bigCol].getHasBeenWonBy() == '-') {
            if(bigBoard.smallBoards[bigRow][bigCol].hasOWon()) {
                setOWonColors(bigRow, bigCol);
                bigBoard.smallBoards[bigRow][bigCol].setHasBeenWon('O');
            } else if (bigBoard.smallBoards[bigRow][bigCol].hasXWon()) {
                setXWonColors(bigRow, bigCol);
                bigBoard.smallBoards[bigRow][bigCol].setHasBeenWon('X');
            }
        }
    }

    private void setOWonColors(int bigRow, int bigCol) {
        grids[bigRow][bigCol].setStyle(O_WIN);
        grids[bigRow][bigCol].getChildren()
                .stream()
                .filter(node -> node instanceof Button)
                .forEach(node -> node.setStyle(TRANSPARENT));
    }

    private void setXWonColors(int bigRow, int bigCol) {
        grids[bigRow][bigCol].setStyle(X_WIN);
        grids[bigRow][bigCol].getChildren()
                .stream()
                .filter(node -> node instanceof Button)
                .forEach(node -> node.setStyle(TRANSPARENT));
    }

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

    private void resetColors(int bigRow, int bigCol, int smallRow, int smallCol) {
        if(bigBoard.smallBoards[bigRow][bigCol].getHasBeenWonBy() == 'O') {
            grids[bigRow][bigCol].setStyle(O_WIN);
        } else if(bigBoard.smallBoards[bigRow][bigCol].getHasBeenWonBy() == 'X') {
            grids[bigRow][bigCol].setStyle(X_WIN);
        } else {
            grids[bigRow][bigCol].setStyle(INACTIVE);
        }
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

    private class TicTacToeListener implements EventHandler<ActionEvent> {

        private int bigRow, bigCol, smallRow, smallCol;
        private Button source;

        TicTacToeListener(int bigRow, int bigCol, int smallRow, int smallCol, Button source) {
            this.bigRow = bigRow;
            this.bigCol = bigCol;
            this.smallRow = smallRow;
            this.smallCol = smallCol;
            this.source = source;
        }

        @Override
        public void handle(ActionEvent event) {
            Square cpu = new Square();
            if(grids[bigRow][bigCol].getStyle().equals(ACTIVE) && bigBoard.isUserMove() && !gameOver) {
                if(begin) {
                    displayAllColors();
                }
                if (source.getText().equals("-")) {
                    source.setText("X");
                    Square player = new Square();
                    player.setX(smallRow);
                    player.setY(smallCol);
                    bigBoard.smallBoards[bigRow][bigCol].placeMove(player, 'X');
                    info.setText("large grid row: " + bigRow + " col: " + bigCol + "\n" +
                            "small grid row: " + smallRow + " col: " + smallCol);

                    modifyForWins(bigRow, bigCol);
                    resetColors(bigRow, bigCol, smallRow, smallCol);

                    bigBoard.setUserMove(false);

                    if(!gameOver) {
                        cpu = bigBoard.smallBoards[smallRow][smallCol].findBestMove(bigBoard);

                        //Assigning old small locations to new region to pick valid big board
                        bigRow = smallRow;
                        bigCol = smallCol;
                        //Assigning new small board values from CPU choice
                        smallRow = cpu.getX();
                        smallCol = cpu.getY();
                        getByCell(bigRow, bigCol, smallRow, smallCol).fire();
                        bigBoard.smallBoards[bigRow][bigCol].placeMove(cpu, 'O');
                        modifyForWins(bigRow, bigCol);
                        resetColors(bigRow, bigCol, smallRow, smallCol);
                    } else {
                        buttons.forEach(button -> button.setDisable(true));
                    }
                } else {
                    info.setText("Spot already taken!");
                }
            } else if(!bigBoard.isUserMove() && !gameOver) {
                source.setText("O");
                info.setText("large grid row: " + bigRow + " col: " + bigCol + "\n" +
                        "small grid row: " + smallRow + " col: " + smallCol);

                modifyForWins(bigRow, bigCol);
                resetColors(bigRow, bigCol, smallRow, smallCol);

                bigBoard.setUserMove(true);
            } else if(gameOver) {
                buttons.forEach(button -> button.setDisable(true));
            } else {
                info.setText("Can't play there!");
            }
        }
    }
}
