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

    private GridPane[][] grids;
    private Button[][] buttons;

    public TicTacToeScene() {
        buttons = new Button[9][9];
        grids = new GridPane[3][3];

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(
                "gui\\tictactoe.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Error or something");
        }

        try {
            Button butt = (Button) getByCell(0, 1, 1, 1);
            butt.fire();
        } catch (Exception e) {
            e.printStackTrace();
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
        });

        this.setCenter(anchor);
    }

    void removeContents() {
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

    void resetGrid() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                for(int k = 0; k < 3; k++) {
                    for(int l = 0; l < 3; l++){
                        BorderPane bp = new BorderPane();
                        Button b = new Button("-");
                        b.setOnAction(new TicTacToeListener(i, j, k, l, b));
                        b.getStyleClass().add("button-transparent");
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
            System.out.println("once");
            if(GridPane.getRowIndex(curr) == smallRow && GridPane.getColumnIndex(curr) == smallCol) {
                return curr;
            }
        }
        return null;
    }

    private class TicTacToeListener implements EventHandler<ActionEvent> {

        private int bigRow, bigCol, smallRow, smallCol;
        private Button source;

        public TicTacToeListener(int bigRow, int bigCol, int smallRow, int smallCol, Button source) {
            this.bigRow = bigRow;
            this.bigCol = bigCol;
            this.smallRow = smallRow;
            this.smallCol = smallCol;
            this.source = source;
        }

        @Override
        public void handle(ActionEvent event) {
            source.setText("X");
            System.out.println("You should be in large grid row: " + bigRow + " col: " + bigCol);
            System.out.println("You should be in small grid row: " + smallRow + " col: " + smallCol);
        }
    }
}
