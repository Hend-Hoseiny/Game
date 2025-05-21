package controller;

import java.io.IOException;
import java.util.ArrayList;

import engine.Game;
import exception.SplitOutOfRangeException;
import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.card.Card;
import view.myView;

public class myController {
    public String getHumanName() {
        return humanName;
    }

    public Game getGame() {
        return game;
    }

    private Stage primaryStage;
    private myView v;
    private String humanName;
    private Game game;

    public void setControls() {
        setStartControls();
        setWelcomeControls();
    }

    public myController(Stage primaryStage, myView v, Scene mainScene) throws IOException {
        this.primaryStage = primaryStage;
        this.v = v;
        game = new Game("");
    }

    private void setStartControls() {
        Button start = v.getStart();
        setClickableButton(start, start.isDisabled());
        start.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (!start.isDisabled()) {
                    primaryStage.setScene(v.getWelcomeScene());
                    primaryStage.setFullScreenExitHint("");
                    primaryStage.setFullScreen(false);
                    primaryStage.show();
                }
            }
        });
    }

    private void setWelcomeControls() {
        TextField name = v.getHumanNameText();
        Button ok = v.getOk();
        ArrayList<Circle> icons = v.getIconChoices();

        ok.setOnMouseEntered(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (!ok.isDisabled()) {
                    ok.setCursor(Cursor.HAND);
                }
            }
        });
        ok.setOnMouseExited(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                ok.setCursor(Cursor.DEFAULT);
            }
        });

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
                    v.getHumanName().setText(humanName);

                    primaryStage.setScene(v.getMainScene());
                    primaryStage.setFullScreenExitHint("");
                    primaryStage.setResizable(false);
                    primaryStage.setFullScreen(false);
                    primaryStage.show();

                    try {
                        v.initializaBoard();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    setMainControls();
                    game = v.getGame();
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

    private void setMainControls() {
        game = v.getGame();
        for (int i = 0; i < v.getHumanCards().size(); i++) {
            ImageView curr = v.getHumanCards().get(i);
            Card card = game.getPlayers().get(0).getHand().get(i);
            curr.setOnMouseEntered(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if (v.getCurrentPlayerIndex() == 0) {
                        curr.setCursor(Cursor.HAND);
                        v.getMessage().setText(card.getDescription());
                        v.getMessage().setVisible(true);
                        keepLabel(v.getMessage());
                        fadeOutLabel(v.getMessage());
                    }
                }
            });
            curr.setOnMouseExited(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if (curr.getCursor() == Cursor.HAND) {
                        curr.setCursor(Cursor.DEFAULT);
                        v.getMessage().setVisible(false);
                    }
                }
            });
            curr.setOnMouseClicked(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if (v.getCurrentPlayerIndex() == 0) {
                        curr.setCursor(Cursor.HAND);
                        if (curr.getEffect() != null && curr.getEffect() instanceof DropShadow)
                            deSelectCard(curr);
                        else {
                            for (int j = 0; j < v.getHumanCards().size(); j++) {
                                deSelectCard(v.getHumanCards().get(j));
                            }
                            selectCard(curr);
                        }
                    }
                }
            });
        }

        for (int i = 0; i < 100; i++) {
            Circle c = v.getTrackCells().get(i);
            c.setOnMouseEntered(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if (v.getCurrentPlayerIndex() == 0 && c.getFill() instanceof ImagePattern) {
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
            c.setOnMouseClicked(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if (v.getCurrentPlayerIndex() == 0 && c.getFill() instanceof ImagePattern) {
                        c.setCursor(Cursor.HAND);
                        if (c.getStrokeWidth() == 1.5)
                            c.setStrokeWidth(0);
                        else {
                            c.setStroke(Color.WHITE);
                            c.setStrokeWidth(1.5);
                        }
                    }
                }
            });
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Circle c = v.getSafeZones().get(i).get(j);
                c.setOnMouseEntered(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        if (v.getCurrentPlayerIndex() == 0 && c.getFill() instanceof ImagePattern) {
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
                c.setOnMouseClicked(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        if (v.getCurrentPlayerIndex() == 0 && c.getFill() instanceof ImagePattern) {
                            c.setCursor(Cursor.HAND);
                            if (c.getStrokeWidth() == 1.5)
                                c.setStrokeWidth(0);
                            else {
                                c.setStroke(Color.WHITE);
                                c.setStrokeWidth(1.5);
                            }
                        }
                    }
                });

                Button split = v.getSplit();
                split.setOnMouseEntered(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        if (v.getCurrentPlayerIndex() == 0) {
                            split.setCursor(Cursor.HAND);
                        }
                    }
                });
                split.setOnMouseExited(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        split.setCursor(Cursor.DEFAULT);
                    }
                });
                split.setOnMouseClicked(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        if (v.getCurrentPlayerIndex() == 0) {
                            split.setCursor(Cursor.HAND);
                            try{
                                int d = Integer.parseInt(v.getSplitText().getText());
                                v.getGame().editSplitDistance(d);;
                            }
                            catch(SplitOutOfRangeException e){
                                Stage exceptionStage = new Stage();
                                StackPane eRoot = new StackPane();
                                Label l = new Label(e.getMessage());
                                eRoot.getChildren().add(l);
                                Scene scene = new Scene(eRoot,500,500);
                                exceptionStage.setScene(scene);
                                exceptionStage.show();
                            }
                            catch(Exception e){
                                Stage exceptionStage = new Stage();
                                StackPane eRoot = new StackPane();
                                Label l = new Label(e.getMessage());
                                eRoot.getChildren().add(l);
                                Scene scene = new Scene(eRoot,500,500);
                                exceptionStage.setScene(scene);
                                exceptionStage.show();
                            }
                        }
                    }
                });

                Button play = v.getPlay();
                play.setOnMouseEntered(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        if (v.getCurrentPlayerIndex() == 0) {
                            play.setCursor(Cursor.HAND);
                        }
                    }
                });
                play.setOnMouseExited(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        play.setCursor(Cursor.DEFAULT);
                    }
                });
                play.setOnMouseClicked(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        if (v.getCurrentPlayerIndex() == 0) {
                            play.setCursor(Cursor.HAND);
                            ArrayList<Integer> marblesIndices = getSelectedMarbles();
                            int cardIndex = getSelectedCard();
                        }
                    }
                });
            }
        }

    }

    private void selectCard(ImageView curr) {
        DropShadow borderEffect = new DropShadow();
        borderEffect.setColor(Color.WHITE);
        borderEffect.setWidth(20); // Border thickness
        borderEffect.setHeight(20);
        borderEffect.setSpread(0.5); // Makes it look more like a border
        curr.setEffect(borderEffect);
    }

    private ArrayList<Integer> getSelectedMarbles() {
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < v.getTrackCells().size(); i++) {
            if (v.getTrackCells().get(i).getStrokeWidth() == 1.5)
                res.add(i);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (v.getHomeZones().get(i).get(j).getStrokeWidth() == 1.5)
                    res.add(i);
            }
        }
        return res;
    }

    private int getSelectedCard(){
        int res=-1;
        for(int i=0 ; i<v.getHumanCards().size() ; i++){
            if(v.getHumanCards().get(i).getEffect()!=null && v.getHumanCards().get(i).getEffect() instanceof DropShadow)
                res=i;
        }
        return res;
    }

    private void deSelectCard(ImageView curr) {
        curr.setEffect(null);
    }

    private void fadeOutLabel(Label label) {
        FadeTransition fade = new FadeTransition(Duration.seconds(3), label);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(event -> label.setVisible(false));
        fade.play();
    }

    private void keepLabel(Label label) {
        FadeTransition fade = new FadeTransition(Duration.seconds(3), label);
        fade.setFromValue(1.0);
        fade.setToValue(1.0);
        fade.setOnFinished(event -> label.setVisible(true));
        fade.play();
    }

    private void setClickableButton(Button b, boolean disabled) {
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
