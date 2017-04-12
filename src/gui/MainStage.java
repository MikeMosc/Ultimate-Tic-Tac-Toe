package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainStage extends Application {

    public MainStage(){

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TicTacToeScene ttc = new TicTacToeScene();
        Scene mainScene = new Scene(ttc);
        mainScene.getStylesheets().add("gui/style.css");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
