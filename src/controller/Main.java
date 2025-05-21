package controller;

import java.lang.ModuleLayer.Controller;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.myView;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        myView v = new myView();
        myController c = new myController(primaryStage, v);
        c.setControls();

        // primaryStage.setScene(v.getMainScene());

        primaryStage.setScene(v.getStartScene());
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(false);
        primaryStage.show();
        // primaryStage.setFullScreen(false);
        // primaryStage.show();

    }
    
}
