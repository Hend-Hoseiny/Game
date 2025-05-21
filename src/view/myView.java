package view;

import java.io.IOException;
import java.util.ArrayList;

import engine.Game;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.Colour;
import model.card.Card;
import model.card.standard.Standard;
import model.card.wild.Burner;
import model.card.wild.Saver;

class Pair {
    private int x;
    private int y;

    protected Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected int getX() {
        return x;
    }

    protected int getY() {
        return y;
    }
}

public class myView {
    public ArrayList<Colour> getColourOrder() {
        return colourOrder;
    }

    private Game game;
    private ArrayList<Colour> colourOrder = new ArrayList<Colour>();
    private ArrayList<Integer> x_coordinates = new ArrayList<Integer>();

    public Game getGame() {
        return game;
    }

    private ArrayList<Integer> y_coordinates = new ArrayList<Integer>();
    private ArrayList<Circle> trackCells = new ArrayList<Circle>();
    private ArrayList<ArrayList<Circle>> safeZones = new ArrayList<ArrayList<Circle>>();
    private ArrayList<ArrayList<Circle>> homeZones = new ArrayList<ArrayList<Circle>>();
    private ArrayList<ImageView> humanCards = new ArrayList<ImageView>();
    private ArrayList<Image> handImages = new ArrayList<Image>();
    private AnchorPane root = new AnchorPane();
    private AnchorPane startRoot = new AnchorPane();

    public Scene getStartScene() {
        return startScene;
    }

    private AnchorPane welcomeRoot = new AnchorPane();
    private Scene mainScene;
    private Scene welcomeScene;
    private Scene startScene;
    private final Pair[] cardsStartLocation = new Pair[4];
    private Image humanIconImage = new Image("file:resources/images/cpu.png");;
    private ImageView deck;
    private ImageView firePit;
    private Label humanName = new Label();
    private Label message = new Label();
    private ArrayList<Label> stateLabel = new ArrayList<Label>();
    private ArrayList<Circle> icons = new ArrayList<Circle>();
    private ArrayList<Circle> iconChoices = new ArrayList<Circle>();
    private Button play = new Button();
    private Button split = new Button();

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    private Button start = new Button();
    private Button how = new Button();
    private Button settings = new Button();
    private Button exit = new Button();
    private Button ok = new Button();
    private TextField humanNameText = new TextField();

    public myView() {
        setSceneStart();
        setSceneMain();
        setSceneWelcome();
    }

    private void set_coordinates() {
        int x = 553;
        int y = 447;
        int step = 20;
        int i = 0;
        for (; i <= 4; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            y -= step;
        }
        y += step;
        x -= step;
        for (; i <= 12; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            x -= step;
        }
        x += step;
        y -= step;
        for (; i <= 15; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            y -= step;
        }
        y += step;
        x -= step;
        for (; i <= 21; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            x -= step;
        }
        x += step;
        y -= step;
        for (; i <= 25; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            y -= step;
        }
        y += step;
        x += step;
        for (; i <= 31; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            x += step;
        }
        x -= step;
        y -= step;
        for (; i <= 34; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            y -= step;
        }
        y += step;
        x += step;
        for (; i <= 42; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            x += step;
        }
        x -= step;
        y -= step;
        for (; i <= 46; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            y -= step;
        }
        y += step;
        x += step;
        for (; i <= 50; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            x += step;
        }
        x -= step;
        y += step;
        for (; i <= 54; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            y += step;
        }
        y -= step;
        x += step;
        for (; i <= 62; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            x += step;
        }
        x -= step;
        y += step;
        for (; i <= 65; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            y += step;
        }
        y -= step;
        x += step;
        for (; i <= 71; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            x += step;
        }
        x -= step;
        y += step;
        for (; i <= 75; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            y += step;
        }
        y -= step;
        x -= step;
        for (; i <= 81; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            x -= step;
        }
        x += step;
        y += step;
        for (; i <= 84; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            y += step;
        }
        y -= step;
        x -= step;
        for (; i <= 92; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            x -= step;
        }
        x += step;
        y += step;
        for (; i <= 96; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            y += step;
        }
        y -= step;
        x -= step;
        for (; i <= 99; i++) {
            x_coordinates.add(x);
            y_coordinates.add(y);
            x -= step;
        }
        cardsStartLocation[0] = new Pair(527, 478);
        cardsStartLocation[1] = new Pair(450, 478);
        cardsStartLocation[2] = new Pair(366, 478);
        cardsStartLocation[3] = new Pair(289, 478);

    }

    private void setTrackCells() {
        for (int i = 0; i < 100; i++) {
            Circle c = new Circle(10);
            c.setStyle("-fx-fill: #d5ab69");
            c.setCenterX(x_coordinates.get(i));
            c.setCenterY(y_coordinates.get(i));
            c.setStrokeWidth(1);
            c.setStroke(Color.BLACK);
            trackCells.add(c);
        }
    }

    private void setSafeZones() {
        int x;
        int y;
        ArrayList<Circle> curr = new ArrayList<Circle>();

        x = x_coordinates.get(98);
        y = y_coordinates.get(98);
        for (int i = 0; i < 4; i++) {
            y -= 20;
            Circle c = new Circle(10);
            c.setStyle("-fx-fill: #d5ab69");
            c.setCenterX(x);
            c.setCenterY(y);
            c.setStrokeWidth(1);
            c.setStroke(Color.BLACK);
            curr.add(c);
        }
        safeZones.add(curr);

        curr = new ArrayList<Circle>();
        x = x_coordinates.get(23);
        y = y_coordinates.get(23);
        for (int i = 0; i < 4; i++) {
            x += 20;
            Circle c = new Circle(10);
            c.setStyle("-fx-fill: #d5ab69");
            c.setCenterX(x);
            c.setCenterY(y);
            c.setStrokeWidth(1);
            c.setStroke(Color.BLACK);
            curr.add(c);
        }
        safeZones.add(curr);

        curr = new ArrayList<Circle>();
        x = x_coordinates.get(48);
        y = y_coordinates.get(48);
        for (int i = 0; i < 4; i++) {
            y += 20;
            Circle c = new Circle(10);
            c.setStyle("-fx-fill: #d5ab69");
            c.setCenterX(x);
            c.setCenterY(y);
            c.setStrokeWidth(1);
            c.setStroke(Color.BLACK);
            curr.add(c);
        }
        safeZones.add(curr);

        curr = new ArrayList<Circle>();
        x = x_coordinates.get(73);
        y = y_coordinates.get(73);
        for (int i = 0; i < 4; i++) {
            x -= 20;
            Circle c = new Circle(10);
            c.setStyle("-fx-fill: #d5ab69");
            c.setCenterX(x);
            c.setCenterY(y);
            c.setStrokeWidth(1);
            c.setStroke(Color.BLACK);
            curr.add(c);
        }
        safeZones.add(curr);

    }

    private void setHomeZones() {
        Circle c;
        ArrayList<Circle> curr = new ArrayList<Circle>();
        c = new Circle(894, 429, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        c = new Circle(862, 404, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        c = new Circle(894, 378, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        c = new Circle(928, 404, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        homeZones.add(curr);

        curr = new ArrayList<Circle>();
        c = new Circle(311, 424, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        c = new Circle(280, 399, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        c = new Circle(311, 373, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        c = new Circle(345, 399, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        homeZones.add(curr);

        curr = new ArrayList<Circle>();
        c = new Circle(306, 144, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        c = new Circle(274, 119, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        c = new Circle(306, 93, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        c = new Circle(340, 119, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        homeZones.add(curr);

        curr = new ArrayList<Circle>();
        c = new Circle(889, 142, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        c = new Circle(857, 117, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        c = new Circle(889, 91, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        c = new Circle(923, 117, 10);
        c.setStyle("-fx-fill: #d5ab69");
        curr.add(c);
        homeZones.add(curr);

    }

    private void setHumanCards() {
        for (int i = 0; i < 4; i++) {
            ImageView card = new ImageView(handImages.get(i));
            humanCards.add(card);
        }
    }

    private void arrangeHumanCards() {
        int x = cardsStartLocation[(humanCards.size() - 1)].getX();
        int y = cardsStartLocation[(humanCards.size() - 1)].getY();
        for (int i = 0; i < humanCards.size(); i++) {
            ImageView curr = humanCards.get(i);
            int w = 155;
            int h = 195;
            fixCardLayout(curr, x, y, w, h);
            x += 161;
        }
    }

    private void fixCardLayout(ImageView curr, int x, int y, int w, int h) {
        curr.setFitWidth(w);
        curr.setFitHeight(h);
        curr.setX(x);
        curr.setY(y);
        double centerX = curr.getX() + w / 2;
        double centerY = curr.getY() + h / 2;
        curr.xProperty()
                .bind(root.widthProperty().multiply((centerX / 1200.0))
                        .subtract(curr.fitWidthProperty().divide(2)));
        curr.yProperty()
                .bind(root.heightProperty().multiply((centerY / 675.0))
                        .subtract(curr.fitHeightProperty().divide(2)));
        curr.fitWidthProperty().bind(root.widthProperty().multiply(w / 1200.0));
        curr.fitHeightProperty().bind(root.heightProperty().multiply(h / 675.0));
        curr.setPreserveRatio(true);
    }

    private void fixCircleLayout(Circle c, AnchorPane root) {
        int x = (int) c.getCenterX();
        int y = (int) c.getCenterY();
        int r = (int) c.getRadius();
        c.centerXProperty().bind(root.widthProperty().multiply(x / 1200.0));
        c.centerYProperty().bind(root.heightProperty().multiply(y / 675.0));
        c.radiusProperty().bind(root.heightProperty().multiply(r / 675.0));
    }

    private void fixLabelLayout(Label l, double x, double y) {
        l.setStyle("-fx-background-color: #d5ab69; " +
                "-fx-background-radius: 15px; " +
                "-fx-padding: 5px 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-stroke-fill: #4b8c83; " +
                "-fx-stroke-width: 0.75; " +
                "-fx-font-family: 'Fantasy'; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill:rgb(105, 71, 44);");
        DoubleProperty leftMarginProp = new SimpleDoubleProperty();
        leftMarginProp.bind(root.widthProperty().multiply(x / 1200.0));
        leftMarginProp.addListener((obs, oldVal, newVal) -> {
            AnchorPane.setLeftAnchor(l, newVal.doubleValue());
        });
        DoubleProperty topMarginProp = new SimpleDoubleProperty();
        topMarginProp.bind(root.heightProperty().multiply(y / 675.0));
        topMarginProp.addListener((obs, oldVal, newVal) -> {
            AnchorPane.setTopAnchor(l, newVal.doubleValue());
        });
        l.setLayoutX(x);
        l.setLayoutY(y);
        l.setPrefHeight(50);
    }

    private void fixButtonLayout(Button b, double x, double y) {
        double xProp = x / 1200.0;
        double yProp = y / 675.0;
        double widthProp = 120 / 1200.0;
        double heightProp = 80 / 675;

        DoubleProperty leftAnchor = new SimpleDoubleProperty();
        DoubleProperty topAnchor = new SimpleDoubleProperty();
        DoubleProperty buttonWidthProp = new SimpleDoubleProperty();
        DoubleProperty buttonHeightProp = new SimpleDoubleProperty();

        leftAnchor.bind(root.widthProperty().multiply(xProp));
        topAnchor.bind(root.heightProperty().multiply(yProp));
        buttonWidthProp.bind(root.widthProperty().multiply(widthProp));
        buttonHeightProp.bind(root.heightProperty().multiply(heightProp));

        leftAnchor.addListener((obs, oldVal, newVal) -> {
            AnchorPane.setLeftAnchor(b, newVal.doubleValue());
        });

        topAnchor.addListener((obs, oldVal, newVal) -> {
            AnchorPane.setTopAnchor(b, newVal.doubleValue());
        });

        buttonWidthProp.addListener((obs, oldVal, newVal) -> {
            b.setPrefWidth(newVal.doubleValue());
        });

        buttonHeightProp.addListener((obs, oldVal, newVal) -> {
            b.setPrefHeight(newVal.doubleValue());
        });
        b.setPrefWidth(120);
        b.setPrefHeight(80);
        AnchorPane.setLeftAnchor(b, x);
        AnchorPane.setTopAnchor(b, y);
    }

    private void fixButtonLayoutWelcome(Button b, double x, double y, AnchorPane root) {
        double xProp = x / 1200.0;
        double yProp = y / 675.0;
        double widthProp = 323 / 1200.0;
        double heightProp = 62 / 675;

        DoubleProperty leftAnchor = new SimpleDoubleProperty();
        DoubleProperty topAnchor = new SimpleDoubleProperty();
        DoubleProperty buttonWidthProp = new SimpleDoubleProperty();
        DoubleProperty buttonHeightProp = new SimpleDoubleProperty();

        leftAnchor.bind(root.widthProperty().multiply(xProp));
        topAnchor.bind(root.heightProperty().multiply(yProp));
        buttonWidthProp.bind(root.widthProperty().multiply(widthProp));
        buttonHeightProp.bind(root.heightProperty().multiply(heightProp));

        leftAnchor.addListener((obs, oldVal, newVal) -> {
            AnchorPane.setLeftAnchor(b, newVal.doubleValue());
        });

        topAnchor.addListener((obs, oldVal, newVal) -> {
            AnchorPane.setTopAnchor(b, newVal.doubleValue());
        });

        buttonWidthProp.addListener((obs, oldVal, newVal) -> {
            b.setPrefWidth(newVal.doubleValue());
        });

        buttonHeightProp.addListener((obs, oldVal, newVal) -> {
            b.setPrefHeight(newVal.doubleValue());
        });
        b.setPrefWidth(323);
        b.setPrefHeight(62);
        AnchorPane.setLeftAnchor(b, x);
        AnchorPane.setTopAnchor(b, y);
        b.setStyle("-fx-background-color: transparent;");
    }

    public void setSceneMain() {
        set_coordinates();
        setTrackCells();
        setSafeZones();
        setHomeZones();
        for (int i = 0; i < 100; i++) {
            Circle c = trackCells.get(i);
            fixCircleLayout(c, root);
            root.getChildren().add(c);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Circle c = safeZones.get(i).get(j);
                fixCircleLayout(c, root);
                root.getChildren().add(c);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Circle c = homeZones.get(i).get(j);
                fixCircleLayout(c, root);
                c.setStrokeWidth(1);
                c.setStroke(Color.BLACK);
                root.getChildren().add(c);
            }
        }

        Image cardBack = new Image("file:resources/images/cardBack.png");
        for (int i = 0; i < 4; i++) {
            handImages.add(cardBack);
        }
        setHumanCards();
        arrangeHumanCards();
        for (int i = 0; i < humanCards.size(); i++) {
            root.getChildren().add(humanCards.get(i));
        }

        deck = new ImageView(cardBack);
        fixCardLayout(deck, 37, 519, 128, 136);
        firePit = new ImageView();
        fixCardLayout(firePit, 543, 191, 110, 150);
        root.getChildren().addAll(deck, firePit);

        Image cpuImage = new Image("file:resources/images/cpu.png");
        Circle icon1 = new Circle(1005, 485, 52);
        fixCircleLayout(icon1, root);
        icon1.setFill(new ImagePattern(humanIconImage));
        icons.add(icon1);
        Circle icon2 = new Circle(206, 480, 52);
        fixCircleLayout(icon2, root);
        icon2.setFill(new ImagePattern(cpuImage));
        icons.add(icon2);
        Circle icon3 = new Circle(191, 62, 52);
        fixCircleLayout(icon3, root);
        icon3.setFill(new ImagePattern(cpuImage));
        icons.add(icon3);
        Circle icon4 = new Circle(1018, 64, 52);
        fixCircleLayout(icon4, root);
        icon4.setFill(new ImagePattern(cpuImage));
        icons.add(icon4);
        root.getChildren().addAll(icon1, icon2, icon3, icon4);

        fixLabelLayout(humanName, 975, 528);
        Label cpu1Name = new Label("CPU 1");
        fixLabelLayout(cpu1Name, 165, 519);
        Label cpu2Name = new Label("CPU 2");
        fixLabelLayout(cpu2Name, 155, 109);
        Label cpu3Name = new Label("CPU 3");
        fixLabelLayout(cpu3Name, 996, 103);
        root.getChildren().addAll(humanName, cpu1Name, cpu2Name, cpu3Name);

        message.setStyle("-fx-background-color: #d5ab69; " +
                "-fx-background-radius: 15px; " +
                "-fx-padding: 5px 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-font-family: 'Fantasy'; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill:rgb(105, 71, 44);");
        message.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            double labelWidth = newBounds.getWidth();
            double leftAnchor = (root.getWidth() - labelWidth) / 2;
            AnchorPane.setLeftAnchor(message, leftAnchor);
        });
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            double labelWidth = message.getLayoutBounds().getWidth();
            double leftAnchor = (newVal.doubleValue() - labelWidth) / 2;
            AnchorPane.setLeftAnchor(message, leftAnchor);
        });
        message.setVisible(false);
        AnchorPane.setTopAnchor(message, 24.0);
        message.setPrefWidth(Region.USE_COMPUTED_SIZE);
        message.setMaxWidth(Region.USE_COMPUTED_SIZE);
        root.getChildren().add(message);

        Label current1 = new Label("Current");
        fixLabelLayout(current1, 975, 573);
        stateLabel.add(current1);
        Label current2 = new Label("Current");
        fixLabelLayout(current2, 165, 564);
        stateLabel.add(current2);
        Label current3 = new Label("Current");
        fixLabelLayout(current3, 155, 154);
        stateLabel.add(current3);
        Label current4 = new Label("Current");
        fixLabelLayout(current4, 996, 149);
        stateLabel.add(current4);
        root.getChildren().addAll(current1, current2, current3, current4);

        Image playButtonImage = new Image("file:resources/images/play.png");
        ImageView imageView = new ImageView(playButtonImage);
        imageView.setFitWidth(120);
        imageView.setFitHeight(80);
        play.setGraphic(imageView);
        play.setStyle("-fx-background-color: transparent;");
        fixButtonLayout(play, 1043, 182);
        Image splitButtonImage = new Image("file:resources/images/split.png");
        imageView = new ImageView(splitButtonImage);
        imageView.setFitWidth(120);
        imageView.setFitHeight(80);
        split.setGraphic(imageView);
        split.setStyle("-fx-background-color: transparent;");
        fixButtonLayout(split, 1045, 283);
        root.getChildren().addAll(play, split);

        Image backGroundMain = new Image("file:resources/images/backGroundMain.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                backGroundMain,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1200, 675, true, true, true, true));
        root.setBackground(new Background(backgroundImage));
        mainScene = new Scene(root, 1200.0, 675.0);
    }

    public void initializaBoard() throws IOException {
        icons.get(0).setFill(new ImagePattern(humanIconImage));
        String s = humanName.getText();
        game = new Game(s);
        for (int i = 0; i < game.getPlayers().size(); i++) {
            Colour curr = game.getPlayers().get(i).getColour();
            colourOrder.add(curr);
        }
        initializeHomeZones(colourOrder);

        for (int i = 0; i < 4; i++) {
            Image image = new Image(getCardURL(game.getPlayers().get(0).getHand().get(i)));
            humanCards.get(i).setImage(image);
        }

        for (int i = 0; i < 4; i++) {
            stateLabel.get(i).setVisible(false);
        }
        stateLabel.get(getPlayerIndex(game.getActivePlayerColour())).setVisible(true);
        stateLabel.get(getPlayerIndex(game.getActivePlayerColour())).setText("Current");
        stateLabel.get(getPlayerIndex(game.getNextPlayerColour())).setVisible(true);
        stateLabel.get(getPlayerIndex(game.getNextPlayerColour())).setText("Next");

    }

    public int getCurrentPlayerIndex(){
        return getPlayerIndex(game.getActivePlayerColour());
    }

    private int getPlayerIndex(Colour c) {
        for (int i = 0; i < game.getPlayers().size(); i++) {
            if (game.getPlayers().get(i).getColour() == c)
                return i;
        }
        return -1;
    }

    private void initializeHomeZones(ArrayList<Colour> colourOrder) {
        for (int i = 0; i < 4; i++) {
            String curr = colourOrder.get(i).toString();
            Image image = new Image("file:resources/images/" + curr + ".png");
            for (int j = 0; j < 4; j++) {
                homeZones.get(i).get(j).setFill(new ImagePattern(image));
            }
        }
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

    public Scene getWelcomeScene() {
        return welcomeScene;
    }

    public void setSceneStart() {
        fixButtonLayoutWelcome(start, 441, 336, startRoot);
        fixButtonLayoutWelcome(how, 441, 418, startRoot);
        fixButtonLayoutWelcome(settings, 441, 496, startRoot);
        fixButtonLayoutWelcome(exit, 441, 577, startRoot);

        startRoot.getChildren().addAll(start, how, settings, exit);
        Image backGroundWelcome = new Image("file:resources/images/start.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                backGroundWelcome,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1200, 675, true, true, true, true));
        startRoot.setBackground(new Background(backgroundImage));
        startScene = new Scene(startRoot, 1200, 675);
    }

    public void setSceneWelcome() {
        Label prompt = new Label("Please choose a name and an icon");
        prompt.setStyle("-fx-background-color: #d5ab69; " +
                "-fx-background-radius: 20px; " +
                "-fx-padding: 5px 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-font-family: 'Fantasy'; " +
                "-fx-font-size: 65px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill:rgb(105, 71, 44);");
        prompt.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            double labelWidth = newBounds.getWidth();
            double leftAnchor = (welcomeRoot.getWidth() - labelWidth) / 2;
            AnchorPane.setLeftAnchor(prompt, leftAnchor);
        });
        prompt.widthProperty().addListener((obs, oldVal, newVal) -> {
            double labelWidth = prompt.getLayoutBounds().getWidth();
            double leftAnchor = (newVal.doubleValue() - labelWidth) / 2;
            AnchorPane.setLeftAnchor(prompt, leftAnchor);
        });
        AnchorPane.setTopAnchor(prompt, 70.0);
        prompt.setPrefWidth(Region.USE_COMPUTED_SIZE);
        prompt.setMaxWidth(Region.USE_COMPUTED_SIZE);
        welcomeRoot.getChildren().add(prompt);

        Image iconImage1 = new Image("file:resources/images/icon1.png");
        Circle c = new Circle(894, 326, 66);
        c.setFill(new ImagePattern(iconImage1));
        fixCircleLayout(c, welcomeRoot);
        iconChoices.add(c);
        welcomeRoot.getChildren().add(c);
        Image iconImage2 = new Image("file:resources/images/icon2.png");
        c = new Circle(1042, 326, 66);
        c.setFill(new ImagePattern(iconImage2));
        fixCircleLayout(c, welcomeRoot);
        iconChoices.add(c);
        welcomeRoot.getChildren().add(c);
        Image iconImage3 = new Image("file:resources/images/icon3.png");
        c = new Circle(894, 516, 66);
        c.setFill(new ImagePattern(iconImage3));
        fixCircleLayout(c, welcomeRoot);
        iconChoices.add(c);
        welcomeRoot.getChildren().add(c);
        Image iconImage4 = new Image("file:resources/images/icon4.png");
        c = new Circle(1042, 516, 66);
        c.setFill(new ImagePattern(iconImage4));
        fixCircleLayout(c, welcomeRoot);
        iconChoices.add(c);
        welcomeRoot.getChildren().add(c);

        humanNameText.setPromptText("Enter a name");
        humanNameText.setLayoutX(233);
        humanNameText.setLayoutY(311);
        humanNameText.setMinSize(381, 98);
        humanNameText.setStyle(
                "-fx-background-color: #d5ab69;" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-radius: 20;" +
                        "-fx-text-fill:rgba(190, 100, 52, 0.62);" +
                        "-fx-border-width: 2;" +
                        "-fx-padding: 5 15;" +
                        "-fx-font-family: 'Fantasy'; " +
                        "-fx-font-size: 30px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill:rgb(105, 60, 44);");
        welcomeRoot.getChildren().add(humanNameText);

        ok.setText("OK");
        ok.setPrefSize(100, 100);
        ok.setLayoutX(350);
        ok.setLayoutY(500);
        ok.setStyle(
                "-fx-background-color: #d5ab69;" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-radius: 20;" +
                        "-fx-text-fill:rgba(190, 100, 52, 0.62);" +
                        "-fx-border-width: 2;" +
                        "-fx-padding: 5 15;" +
                        "-fx-font-family: 'Fantasy'; " +
                        "-fx-font-size: 30px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill:rgb(105, 60, 44);");
        ok.setDisable(true);
        welcomeRoot.getChildren().add(ok);

        Image backGroundStart = new Image("file:resources/images/backgroundEmpty.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                backGroundStart,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1200, 675, true, true, true, true));
        welcomeRoot.setBackground(new Background(backgroundImage));
        welcomeScene = new Scene(welcomeRoot, 1200, 675);
    }

    public Image getHumanIconImage() {
        return humanIconImage;
    }

    public ArrayList<Circle> getIconChoices() {
        return iconChoices;
    }

    public Button getOk() {
        return ok;
    }

    public TextField getHumanNameText() {
        return humanNameText;
    }

    public void setHumanIconImage(Image humanIconImage) {
        this.humanIconImage = humanIconImage;
    }

    public ArrayList<Circle> getTrackCells() {
        return trackCells;
    }

    public ArrayList<ArrayList<Circle>> getSafeZones() {
        return safeZones;
    }

    public ArrayList<ArrayList<Circle>> getHomeZones() {
        return homeZones;
    }

    public ArrayList<ImageView> getHumanCards() {
        return humanCards;
    }

    public ArrayList<Image> getHandImages() {
        return handImages;
    }

    public AnchorPane getRoot() {
        return root;
    }

    public ImageView getDeck() {
        return deck;
    }

    public ImageView getFirePit() {
        return firePit;
    }

    public Label getHumanName() {
        return humanName;
    }

    public Label getMessage() {
        return message;
    }

    public ArrayList<Label> getStateLabel() {
        return stateLabel;
    }

    public ArrayList<Circle> getIcons() {
        return icons;
    }

    public Button getPlay() {
        return play;
    }

    public Button getSplit() {
        return split;
    }

    public Button getStart() {
        return start;
    }

    public AnchorPane getStartRoot() {
        return startRoot;
    }

    public AnchorPane getWelcomeRoot() {
        return welcomeRoot;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public Button getHow() {
        return how;
    }

    public Button getSettings() {
        return settings;
    }

    public Button getExit() {
        return exit;
    }

}
