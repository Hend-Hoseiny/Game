package controller;

import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;

import engine.Game;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.card.Card;
import model.card.Deck;
import model.card.standard.Standard;
import model.card.wild.Burner;
import model.card.wild.Saver;
import view.myView;

public class Main extends Application {
    private Game game;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // myView v = new myView();
        // myController c = new myController(primaryStage, v, v.getMainScene());
        // c.setControls();

        // primaryStage.setScene(v.getStartScene());
        // primaryStage.setResizable(false);
        // primaryStage.setFullScreen(false);
        // primaryStage.show();

        Deck.loadCardPool(null, null);
        ArrayList<Card> c = Deck.getDeck();
        System.out.println(c);
        // StackPane s = new StackPane();
        // ImageView v = new ImageView(new Image(getCardURL(c.get(0))));
        // s.getChildren().add(v);
        // Scene sc = new Scene(s,1000,600);
        // primaryStage.setScene(sc);
        // primaryStage.show();

    }

    private String getCardURL(Card c) {
        if (c instanceof Standard) {
            int rank = ((Standard) c).getRank();
            String suit = ((Standard) c).getSuit().toString();
            suit.toLowerCase();
            suit += "s";
            if (rank == 11)
                return "file:resources/images/jack of " + suit + ".png";
            if (rank == 12)
                return "file:resources/images/queen of " + suit + ".png";
            if (rank == 13)
                return "file:resources/images/king of " + suit + ".png";
            return "file:resources/images/" + rank + " of " + suit + ".png";
        } else if (c instanceof Burner) {
            return "file:resources/images/burner.png";
        } else if (c instanceof Saver) {
            return "file:resources/images/saver.png";
        } else
            return ("");
    }

}
