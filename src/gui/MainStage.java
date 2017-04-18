package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Ultimate Tic Tac Toe Main JavaFX class.
 * This is the Application class to display the TicTacToeScene.
 * Artificial Intelligence.
 * April 19th, 2017.
 * @author Matthew Gimbut
 * @author Michael Moscariello
 */
public class MainStage extends Application {

    public MainStage(){ }

    /**
     * The required start method for JavaFX in the class for the main stage.
     * @param primaryStage
     * @throws Exception
     */
    @Override public void start(Stage primaryStage) throws Exception {
        TicTacToeScene ttc = new TicTacToeScene();
        Scene mainScene = new Scene(ttc);
        mainScene.getStylesheets().add("gui/style.css");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
