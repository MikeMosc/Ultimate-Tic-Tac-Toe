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
    private final String INACTIVE = "-fx-background-color: rgb(255, 59, 71);";
    private final String ACTIVE = "-fx-background-color: rgb(0, 255, 74);";
    private final String TRANSPARENT = "-fx-background-color: transparent;";

    private GridPane[][] grids;

    TicTacToeScene() {
        grids = new GridPane[3][3];
        begin = true;

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
                        grids[i][j].add(bp, l, k);
                    }
                }
            }
        }
    }

    public Node getByCell(int bigRow, int bigCol, int smallRow, int smallCol) {
        GridPane pane = grids[bigRow][bigCol];
        for(Node curr : pane.getChildren()) {
            if(curr instanceof BorderPane && pane.getRowIndex(curr) == smallRow && pane.getColumnIndex(curr) == smallCol) {
                for(Node child : ((BorderPane) curr).getChildren()) {
                    if (child instanceof Button) {
                        return child;
                    }
                }
            }
        }
        return null;
    }

    private void setAllInactive() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                grids[i][j].getStyleClass().removeAll("in-active", "o-win", "x-win");
                grids[i][j].setStyle(INACTIVE);
            }
        }
    }

    private void checkForGlobalWin() {

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
            if(grids[bigRow][bigCol].getStyle().equals(ACTIVE)) {
                if(begin) {
                    setAllInactive();
                }
                if (source.getText().equals("-")) {
                    source.setText("X");
                    info.setText("large grid row: " + bigRow + " col: " + bigCol + "\n" +
                            "small grid row: " + smallRow + " col: " + smallCol);

                    grids[bigRow][bigCol].setStyle(INACTIVE);
                    grids[bigRow][bigCol].getChildren()
                            .stream()
                            .filter(node -> node instanceof Button)
                            .forEach(node -> node.setStyle(TRANSPARENT));

                    grids[smallRow][smallCol].setStyle(ACTIVE);
                    grids[smallRow][smallCol].getChildren()
                            .stream()
                            .filter(node -> node instanceof Button)
                            .forEach(node -> node.setStyle(TRANSPARENT));

                } else {
                    info.setText("Spot already taken!");
                }
            } else {
                info.setText("Can't play there!");
            }
        }
    }
}
