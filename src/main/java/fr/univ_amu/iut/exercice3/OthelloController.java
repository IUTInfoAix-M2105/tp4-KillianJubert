package fr.univ_amu.iut.exercice3;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.util.Optional;


public class OthelloController {
    @FXML
    private StatusBar statusBar;

    @FXML
    private Othellier othellier;

    @FXML
    private MenuItem mNew;
    @FXML
    private MenuItem mQuit;

    @FXML
    void initialize() {
        othellier.joueurCourantProperty().addListener(observable -> {
            if (othellier.getJoueurCourant() == Joueur.PERSONNE) {
                afficheDialogFinDePartie();
            }
        });
        statusBar.joueurCourantProperty().bind(othellier.joueurCourantProperty());
        mNew.setOnAction(event -> {
            actionMenuJeuNouveau();
        });
        mQuit.setOnAction(event -> {
            actionMenuJeuQuitter();
        });
    }

    void setStageAndSetupListeners(Stage stage) {
        stage.setOnCloseRequest(event -> {
            actionMenuJeuQuitter();
            event.consume();
        });
    }

    private void afficheDialogFinDePartie() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        int pointsGagnant = Joueur.NOIR.getScore();
        String nomGagnant = "Noir";
        if (Joueur.BLANC.getScore() > Joueur.NOIR.getScore()) {
            nomGagnant = "Blanc";
            pointsGagnant = Joueur.BLANC.getScore();
        }
        message.setContentText(String.format("Le joueur %s a gagné avec %d points.", nomGagnant, pointsGagnant));
        message.showAndWait();

    }

    @FXML
    public void actionMenuJeuQuitter() {
        Alert message = new Alert(Alert.AlertType.CONFIRMATION);
        message.setContentText("êtes vous sûr de quitter ?");
        Optional<ButtonType> result = message.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    @FXML
    public void actionMenuJeuNouveau() {
        Alert message = new Alert(Alert.AlertType.CONFIRMATION);
        message.setContentText("Voulez-vous créer une nouvelle partie ?");
        Optional<ButtonType> result = message.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            othellier.nouvellePartie();
        }

    }
}