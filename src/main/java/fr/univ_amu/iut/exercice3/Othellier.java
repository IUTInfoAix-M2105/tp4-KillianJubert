package fr.univ_amu.iut.exercice3;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;


public class Othellier extends GridPane {


    private static final Point2D[] directions = {
            new Point2D(1, 0),
            new Point2D(1, 1),
            new Point2D(0, 1),
            new Point2D(-1, 1),
            new Point2D(-1, 0),
            new Point2D(-1, -1),
            new Point2D(0, -1),
            new Point2D(1, -1)
    };
    private final EventHandler<ActionEvent> caseListener = event -> {
        Case c = (Case) event.getSource();
        if (estPositionJouable(c)) {
            capturer(c);
            tourSuivant();
        }
    };
    private int taille;
    private Case[][] cases;
    private ObjectProperty<Joueur> joueurCourant = new SimpleObjectProperty<>(Joueur.NOIR);


    public Othellier() {
        int taille = 8;
        this.taille = taille;
        cases = new Case[taille][taille];
        remplirOthelier();
        adapterLesLignesEtColonnes();
        nouvellePartie();
    }

    public ObjectProperty<Joueur> joueurCourantProperty() {
        return joueurCourant;
    }

    private void remplirOthelier() {
        for (int x = 0; x < cases.length; ++x) {
            for (int y = 0; y < cases[0].length; ++y) {
                cases[x][y] = new Case(x, y);
                this.add(cases[x][y], x, y);
                cases[x][y].setOnAction(caseListener);

            }
        }
    }

    private void adapterLesLignesEtColonnes() {
        for (int i = 0; i < taille; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHgrow(Priority.ALWAYS);
            getColumnConstraints().add(column);
        }

        for (int i = 0; i < taille; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            getRowConstraints().add(row);
        }
    }

    private void positionnerPionsDebutPartie() {
        cases[3][3].setPossesseur(Joueur.BLANC);
        Joueur.BLANC.incrementerScore();

        cases[4][3].setPossesseur(Joueur.NOIR);
        Joueur.NOIR.incrementerScore();

        cases[3][4].setPossesseur(Joueur.NOIR);
        Joueur.NOIR.incrementerScore();

        cases[4][4].setPossesseur(Joueur.BLANC);
        Joueur.BLANC.incrementerScore();
    }


    public void nouvellePartie() {
        joueurCourant.set(Joueur.NOIR);
        Joueur.initialiserScores();
        vider();
        positionnerPionsDebutPartie();
    }

    private boolean peutJouer() {
        return casesJouables().size() > 0;
    }

    private List<Case> casesJouables() {
        List<Case> jouables = new ArrayList<Case>();
        for (Case[] aCase : cases) {
            for (Case value : aCase) {
                if (value.getPossesseur() == Joueur.PERSONNE && estPositionJouable(value)) jouables.add(value);
            }
        }
        return jouables;
    }

    private List<Case> casesCapturable(Case caseSelectionnee) {
        List<Case> casesCapturable = new ArrayList<>();

        for (Point2D direction : directions) {
            casesCapturable.addAll(casesCapturable(caseSelectionnee, direction));
        }

        return casesCapturable;
    }

    private List<Case> casesCapturable(Case caseSelectionnee, Point2D direction) {
        List<Case> casesCapturable = new ArrayList<>();

        int indiceLigne = caseSelectionnee.getLigne() + (int) direction.getY();
        int indiceColonne = caseSelectionnee.getColonne() + (int) direction.getX();

        while (estIndicesValides(indiceLigne, indiceColonne)) {
            if (cases[indiceLigne][indiceColonne].getPossesseur() != joueurCourant.get().suivant())
                break;

            casesCapturable.add(cases[indiceLigne][indiceColonne]);

            indiceLigne += direction.getY();
            indiceColonne += direction.getX();
        }

        if (estIndicesValides(indiceLigne, indiceColonne) &&
                cases[indiceLigne][indiceColonne].getPossesseur() == joueurCourant.get())
            return casesCapturable;
        return new ArrayList<>();
    }

    private boolean estIndicesValides(int indiceLigne, int indiceColonne) {
        return estIndiceValide(indiceLigne) && estIndiceValide(indiceColonne);
    }

    private boolean estIndiceValide(int indice) {
        return indice < taille && indice >= 0;
    }

    private boolean estPositionJouable(Case caseSelectionnee) {
        return casesCapturable(caseSelectionnee).size() > 0 && caseSelectionnee.getPossesseur() == Joueur.PERSONNE;
    }

    private void capturer(Case caseCapturee) {
        List<Case> aCapturer = casesCapturable(caseCapturee);
        caseCapturee.setPossesseur(joueurCourant.get());
        joueurCourant.get().incrementerScore();
        for (Case c : aCapturer) {
            c.getPossesseur().decrementerScore();
            c.setPossesseur(joueurCourant.get());
            c.getPossesseur().incrementerScore();
        }

    }

    public Joueur getJoueurCourant() {
        return joueurCourant.get();
    }

    private void tourSuivant() {
        joueurCourant.set(joueurCourant.get().suivant());
        if (!peutJouer()) {
            joueurCourant.set(joueurCourant.get().suivant());
            if (!peutJouer()) {
                joueurCourant.set(Joueur.PERSONNE);
            }
        }
    }

    private void vider() {
        for (Case[] aCase : cases) {
            for (Case value : aCase) {
                value.setPossesseur(Joueur.PERSONNE);
            }
        }
    }
}