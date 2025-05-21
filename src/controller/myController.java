package controller;

import java.util.ArrayList;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import view.myView;

public class myController {
    public String getHumanName() {
        return humanName;
    }

    private Stage primaryStage;
    private myView v;
    private String humanName;

    public void setControls() {
        setStartControls();
        setWelcomeControls();
    }

    public myController(Stage primaryStage, myView v) {
        this.primaryStage = primaryStage;
        this.v = v;
    }

    public void setStartControls() {
        Button start = v.getStart();
        setClickableButton(start, start.isDisabled());
        start.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (!start.isDisabled()) {
                    primaryStage.setScene(v.getWelcomeScene());
                    // primaryStage.show();
                    primaryStage.setFullScreen(false);
                    primaryStage.setFullScreenExitHint("");
                }
            }
        });
    }

    public void setWelcomeControls() {
        TextField name = v.getHumanNameText();
        Button ok = v.getOk();
        ArrayList<Circle> icons = v.getIconChoices();
        setClickableButton(ok, ok.isDisabled());
        ok.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (!ok.isDisabled()) {
                    humanName = name.getText();
                    String iconID = "cpu";
                    for (int i = 0; i < icons.size(); i++) {
                        if (icons.get(i).getStroke() == Color.WHITE)
                            iconID = "icon" + (i + 1);
                    }
                    Image image = new Image("file:resources/images/" + iconID + ".png");
                    v.setHumanIconImage(image);

                    primaryStage.setFullScreen(true);
                    primaryStage.setScene(v.getMainScene());
                    primaryStage.setFullScreenExitHint("");
                    primaryStage.setFullScreen(false);
                    Scene s = primaryStage.getScene();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    primaryStage.setScene(s);
                    primaryStage.setFullScreen(true);
                    // primaryStage.show();
                }
            }
        });
        for (int i = 0; i < icons.size(); i++) {
            Circle c = icons.get(i);
            setClickableCircle(c, false);
            c.setOnMouseClicked(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if (c.getStrokeWidth() == 3.0) {
                        c.setStrokeWidth(0);
                        ok.setDisable(true);
                    } else {
                        for (int j = 0; j < icons.size(); j++)
                            icons.get(j).setStrokeWidth(0);
                        c.setStroke(Color.WHITE);
                        c.setStrokeWidth(3.0);
                        if (name.getText().length() > 0)
                            ok.setDisable(false);
                        else
                            ok.setDisable(true);
                    }
                }
            });
        }

        name.setOnKeyReleased(new EventHandler<Event>() {
            boolean chosen = false;

            @Override
            public void handle(Event event) {
                if (name.getText().length() > 0) {
                    for (int i = 0; i < icons.size(); i++) {
                        if (icons.get(i).getStrokeWidth() == 3.0)
                            chosen = true;
                    }
                    ok.setDisable(!chosen);
                } else {
                    ok.setDisable(true);
                }
            }
        });
    }

    public void setClickableButton(Button b, boolean disabled) {
        b.setOnMouseEntered(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (!disabled) {
                    b.setCursor(Cursor.HAND);
                }
            }
        });
        b.setOnMouseExited(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                b.setCursor(Cursor.DEFAULT);
            }
        });
    }

    public void setClickableCircle(Circle c, boolean disabled) {
        c.setOnMouseEntered(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (!disabled) {
                    c.setCursor(Cursor.HAND);
                }
            }
        });
        c.setOnMouseExited(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                c.setCursor(Cursor.DEFAULT);
            }
        });
    }
}
