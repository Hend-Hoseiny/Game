package controller;

import java.io.IOException;
import java.util.ArrayList;

import engine.Game;
import exception.ActionException;
import exception.GameException;
import exception.InvalidCardException;
import exception.InvalidMarbleException;
import exception.SplitOutOfRangeException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Colour;
import model.card.Card;
import model.player.CPU;
import model.player.Marble;
import model.player.Player;
import view.myView;

class Tuple {
    private int x;
    private int y;
    private boolean isHome;

    protected Tuple(int x, int y, boolean isHome) {
        this.x = x;
        this.y = y;
        this.isHome = isHome;
    }

    protected int getX() {
        return x;
    }

    protected int getY() {
        return y;
    }
}

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
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Circle c = v.getHomeZones().get(i).get(j);
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
        }

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
                    try {
                        int d = Integer.parseInt(v.getSplitText().getText());
                        v.getGame().editSplitDistance(d);
                    } catch (SplitOutOfRangeException e) {
                        displayExceptionRoutine(e);
                    } catch (Exception e) {
                        displayExceptionRoutine(e);
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
                    ArrayList<Integer> trackIndices = getSelectedMarblesTrack();
                    ArrayList<Tuple> selectedMarblesHome = getSelectedMarblesHome();
                    ArrayList<Tuple> selectedMarblesSafe = getSelectedMarblesSafe();
                    int cardIndex = getSelectedCard();
                    Player player = v.getGame().getPlayers().get(0);
                    ArrayList<Card> hand = player.getHand();
                    for (int i = 0; i < trackIndices.size(); i++) {
                        try {
                            player.selectMarble(
                                    v.getGame().getBoard().getTrack().get(trackIndices.get(i)).getMarble());
                        } catch (InvalidMarbleException e) {
                            displayExceptionRoutine(e);
                            deSelectCells();
                        } catch (Exception e) {
                            e.printStackTrace();
                            deSelectCells();
                        }
                    }

                    for (int i = 0; i < selectedMarblesHome.size(); i++) {
                        try {
                            player.selectMarble(v.getGame().getPlayers().get(selectedMarblesHome.get(i).getX())
                                    .getMarbles().get(selectedMarblesHome.get(i).getY()));
                        } catch (InvalidMarbleException e) {
                            displayExceptionRoutine(e);
                            deSelectCells();
                        } catch (Exception e) {
                            e.printStackTrace();
                            deSelectCells();
                        }
                    }
                    for (int i = 0; i < selectedMarblesSafe.size(); i++) {
                        try {
                            player.selectMarble(
                                    v.getGame().getBoard().getSafeZones().get(selectedMarblesSafe.get(i).getX())
                                            .getCells().get(selectedMarblesSafe.get(i).getY()).getMarble());
                        } catch (InvalidMarbleException e) {
                            displayExceptionRoutine(e);
                            deSelectCells();
                        } catch (Exception e) {
                            e.printStackTrace();
                            deSelectCells();
                        }
                    }

                    try {
                        player.selectCard(hand.get(cardIndex));
                    } catch (InvalidCardException e) {
                        displayExceptionRoutine(e);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        player.play();
                    } catch (ActionException e) {
                        displayExceptionRoutine(e);
                        deSelectCells();
                    } catch (InvalidMarbleException e) {
                        displayExceptionRoutine(e);
                        deSelectCells();
                    } catch (GameException e) {
                        displayExceptionRoutine(e);
                        deSelectCells();
                    } catch (Exception e) {
                        e.printStackTrace();
                        deSelectCells();
                    }

                    ArrayList<Integer> safeZonesIndices = new ArrayList<Integer>();
                    ArrayList<Integer> safeZoneLocation = new ArrayList<Integer>();

                    for (int i = 0; i < selectedMarblesSafe.size(); i++) {
                        safeZonesIndices.add(selectedMarblesSafe.get(i).getX());
                        safeZoneLocation.add(selectedMarblesSafe.get(i).getY());
                    }

                    int selectedMarbleSizes = trackIndices.size() + safeZonesIndices.size();
                    // v.updateBoard(cardIndex, trackIndices, safeZonesIndices, safeZoneLocation,
                    // selectedMarbleSizes);
                    v.updateBoardFinal(cardIndex);
                    // System.out.println("Game: " +
                    // v.getGame().getPlayers().get(0).getSelectedCard().getName());
                    v.getGame().endPlayerTurn();
                    triggerCPUPlays();
                }
            }
        });


    }

    private void triggerCPUPlays() {
        runCPUPlay(1);
    }

    private void runCPUPlay(int cpuIndex) {
        if (cpuIndex >= 4)
            return;
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            CPU cpu = (CPU) v.getGame().getPlayers().get(cpuIndex);
            try {
                cpu.play();
            } catch (GameException ex) {
                displayExceptionRoutine(ex);
            }

            int cardIndex = cpu.getHand().indexOf(cpu.getSelectedCard());
            ArrayList<Marble> selectedMarbles = cpu.returnSelectedMarbles();
            // v.updateBoard(
            // cardIndex,
            // new ArrayList<>(),
            // new ArrayList<>(),
            // new ArrayList<>(),
            // 0);
            v.updateBoardFinal(cardIndex);

            v.getGame().endPlayerTurn();

            runCPUPlay(cpuIndex + 1);
        });
        pause.play();
    }

    private void displayExceptionRoutine(Exception e) {
        Stage exceptionStage = new Stage();
        StackPane eRoot = new StackPane();
        Image bgImage = new Image("file:resources/images/parchment.jpg");

        // Set up BackgroundSize to cover (zoom) the image to fit the pane
        BackgroundSize bgs = new BackgroundSize(
                100, 100, // width and height as percentages
                true, true, // treat width and height as percentage
                false, true // do not contain, but cover (zoom to fill)
        );
        BackgroundImage bgi = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                bgs);

        eRoot.setBackground(new Background(bgi));

        Label l = new Label(e.getMessage());
        try {
            l.setFont(Font.font("Papyrus", 36));
        } catch (Exception e1) {
            l.setFont(Font.font("Serif", 36));
        }
        l.setTextFill(Color.SADDLEBROWN);
        l.setLayoutX(40);
        l.setLayoutY(100);

        eRoot.getChildren().add(l);
        Scene scene = new Scene(eRoot, 500, 500);
        exceptionStage.setScene(scene);
        exceptionStage.show();
    }

    private void selectCard(ImageView curr) {
        DropShadow borderEffect = new DropShadow();
        borderEffect.setColor(Color.WHITE);
        borderEffect.setWidth(20); // Border thickness
        borderEffect.setHeight(20);
        borderEffect.setSpread(0.5); // Makes it look more like a border
        curr.setEffect(borderEffect);
    }

    private void deSelectCells() {
        for (int i = 0; i < 100; i++) {
            Circle c = v.getTrackCells().get(i);
            c.setStrokeWidth(0);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Circle c = v.getSafeZones().get(i).get(j);
                c.setStrokeWidth(0);

            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Circle c = v.getHomeZones().get(i).get(j);
                c.setStrokeWidth(0);

            }
        }
    }

    private ArrayList<Tuple> getSelectedMarblesHome() {
        ArrayList<Tuple> res = new ArrayList<Tuple>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (v.getHomeZones().get(i).get(j).getStrokeWidth() == 1.5)
                    res.add(new Tuple(i, j, true));
            }
        }
        return res;
    }

    private ArrayList<Tuple> getSelectedMarblesSafe() {
        ArrayList<Tuple> res = new ArrayList<Tuple>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (v.getSafeZones().get(i).get(j).getStrokeWidth() == 1.5)
                    res.add(new Tuple(i, j, false));
            }
        }
        return res;
    }

    private ArrayList<Integer> getSelectedMarblesTrack() {
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < v.getTrackCells().size(); i++) {
            if (v.getTrackCells().get(i).getStrokeWidth() == 1.5)
                res.add(i);
        }
        return res;
    }

    private int getSelectedCard() {
        int res = -1;
        for (int i = 0; i < v.getHumanCards().size(); i++) {
            if (v.getHumanCards().get(i).getEffect() != null
                    && v.getHumanCards().get(i).getEffect() instanceof DropShadow)
                return i;
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
