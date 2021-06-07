package fr.univ_amu.iut.exercice3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OthelloMain extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/fr/univ_amu/iut/exercice3/OthelloView.fxml"));
        System.out.println(loader);
        loader.load();
        OthelloController controller = loader.getController();
        System.out.println(controller);
        controller.setStageAndSetupListeners(primaryStage);

        Scene scene = new Scene(loader.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Othello");
        primaryStage.show();
    }
}