package fr.univ_amu.iut.exercice3;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

public class Joueur {
    public static final Joueur NOIR = new Joueur("assets/noirgrand.png");
    public static final Joueur BLANC = new Joueur("assets/blancgrand.png");
    public static final Joueur PERSONNE = new Joueur("assets/vide.png");

    private Image image;
    private IntegerProperty score;

    Joueur(String fileName) {
        image = new Image(fileName);
        score = new SimpleIntegerProperty(0);

    }

    public static void initialiserScores() {
        BLANC.initialiserScore();
        NOIR.initialiserScore();
    }

    private void initialiserScore() {
        score.set(0);
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public int getScore() {
        return score.get();

    }

    private void setScore(int score) {
        this.score.set(score);
    }

    public void decrementerScore() {
        score.set(score.get() - 1);

    }

    public void incrementerScore() {
        score.set(score.get() + 1);
    }

    public Image getImage() {
        return image;
    }

    public Joueur suivant() {
        if (this == BLANC) {
            return NOIR;
        } else {
            return BLANC;
        }
    }
}