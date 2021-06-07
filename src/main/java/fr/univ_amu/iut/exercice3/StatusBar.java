package fr.univ_amu.iut.exercice3;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class StatusBar extends BorderPane implements Initializable {
    private static final String MESSAGE_TOUR_NOIR = "Noir joue !";
    private static final String MESSAGE_TOUR_BLANC = "Blanc joue !";
    private static final String SCORE_NOIR = "Noir : ";
    private static final String SCORE_BLANC = "Blanc : ";
    private static final String MESSAGE_TOUR_FIN_PARTIE = "Partie Terminée";

    @FXML
    private Label messageScoreNoir;

    @FXML
    private Label messageScoreBlanc;

    @FXML
    private Label messageTourDeJeu;

    private ObjectProperty<Joueur> joueurCourant = new SimpleObjectProperty<>(Joueur.NOIR);

    public StatusBar() {
        System.out.println("test");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fr/univ_amu/iut/exercice3/StatusBarView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createBinding();
    }

    private void createBinding() {
        messageScoreNoir.textProperty().bind(Bindings.concat(SCORE_NOIR, Joueur.NOIR.scoreProperty().asString()));
        messageScoreBlanc.textProperty().bind(Bindings.concat(SCORE_BLANC, Joueur.BLANC.scoreProperty().asString()));
        messageTourDeJeu.textProperty().bind(Bindings.when(joueurCourant.isEqualTo(Joueur.NOIR)).then(MESSAGE_TOUR_NOIR)
                .otherwise(Bindings.when(joueurCourant.isEqualTo(Joueur.BLANC)).then(MESSAGE_TOUR_BLANC)
                        .otherwise(MESSAGE_TOUR_FIN_PARTIE)));
    }

    public Joueur getJoueurCourant() {
        return joueurCourant.get();
    }

    public void setJoueurCourant(Joueur joueurCourant) {
        this.joueurCourant.set(joueurCourant);
    }

    public ObjectProperty<Joueur> joueurCourantProperty() {
        return joueurCourant;
    }


}