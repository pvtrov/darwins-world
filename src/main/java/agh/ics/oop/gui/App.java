package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import agh.ics.oop.InputParameters;

import java.io.FileNotFoundException;
import java.io.IOException;


// PRZED ODDANIEM:
// todo sprawdzic czy nie ma komentarzy nie potrzenych
// todo uładnic komentarze
// todo dodać wyjatki
// todo dodac tetsy ze 2
// todo sprawdzic prywatnosci w klasach

public class App extends Application implements IMapObserver{
    ImageStorage images = new ImageStorage();
    VBox leftVboxMaps;
    VBox leftVboxCharts;
    VBox rightVboxMaps;
    VBox rightVboxCharts;
    private HBox mainHbox = new HBox();
    private InputParameters inputParameters;
    private GridPane gridPaneLeft;
    private GridPane gridPaneRight;
    private WorldMap map;
    private World world ;
    private Stage stage;
    private CreatingWorld engineLeft;
    private CreatingWorld engineRight;
    private Object MapDrawings;
    private int widthWorld;
    private int heightWorld;
    private int initialNumberOfAnimals;
    private int startAnimalEnergy;
    private int moveAnimalEnergy;
    private int plantKcal;
    private int jungleRatioo;
    private boolean leftMagic;
    private boolean rightMagic;
    private boolean isLeftAnimalTracked = false;
    private boolean isRightAnimalTracked = false;
    private boolean makeLeft;
    private Animal trackedLeftAnimal;
    private Animal trackedRightAnimal;

    public App() throws FileNotFoundException {
    }

    public static void main(String[] args){
        Application.launch(App.class);
    }

    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(mainHbox, 1800, 900);
        scene.setFill(Color.web("DB6B97"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Darwin's World simulation");
        primaryStage.setOnCloseRequest((event -> {
            try {
                engineLeft.darwinWorld.averageStatistics();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                engineRight.darwinWorld.averageStatistics();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }));
        primaryStage.show();
    }


    @Override
    public void updateHBox(World world, Boolean isLeft) throws Exception {
        Platform.runLater(() -> {
            if (isLeft){
                Button leftStop = new Button("stop");
                leftStop.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
                Button leftStart = new Button("start");
                leftStart.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
                Button stopTracking = new Button("stop tracking");
                stopTracking.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
                HBox buttons = new HBox(15, leftStart, leftStop, stopTracking);
                buttons.setBackground(new Background(new BackgroundFill(Color.web("DB6B97"), CornerRadii.EMPTY, Insets.EMPTY)));
                buttons.setAlignment(Pos.CENTER);


                stopTracking.setOnAction((event -> {
                    isLeftAnimalTracked = false;
                }));

                leftStop.setOnAction((event -> {
                    engineLeft.isRunning = false;
                }));

                leftStart.setOnAction((event -> {
                    engineLeft.isRunning = true;
                }));

                leftVboxCharts.getChildren().clear();
                leftVboxMaps.getChildren().clear();
                try {
                    leftVboxMaps.getChildren().add(createMapDrawing(world, true));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (isLeftAnimalTracked ){
                    VBox leftTracking = Statistics.giveMeAnimalStatistics(trackedLeftAnimal);
                    leftVboxMaps.getChildren().add(Statistics.giveMeStatistics(world, inputParameters.magicLeft));
                    leftVboxMaps.getChildren().add(buttons);
                    leftVboxCharts.getChildren().add(leftTracking);
                    leftVboxMaps.setSpacing(20);
                }else{
                    leftVboxMaps.getChildren().add(Statistics.giveMeStatistics(world, inputParameters.magicLeft));
                    leftVboxMaps.getChildren().add(buttons);
                    leftVboxMaps.setSpacing(20);
                }


            }else{
                Button rightStop = new Button("stop");
                rightStop.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
                Button rightStart = new Button("start");
                rightStart.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
                Button stopTracking = new Button("stop tracking");
                stopTracking.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));

                HBox buttons = new HBox(15, rightStart, rightStop, stopTracking);
                buttons.setBackground(new Background(new BackgroundFill(Color.web("DB6B97"), CornerRadii.EMPTY, Insets.EMPTY)));
                buttons.setAlignment(Pos.CENTER);


                stopTracking.setOnAction((event -> {
                    isRightAnimalTracked = false;
                }));

                rightStop.setOnAction((event -> {
                    engineRight.isRunning = false;
                }));
                rightStart.setOnAction((event -> {
                    engineRight.isRunning = true;
                }));

                rightVboxCharts.getChildren().clear();
                rightVboxMaps.getChildren().clear();
                try {
                    rightVboxMaps.getChildren().add(createMapDrawing(world, false));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (isRightAnimalTracked){
                    VBox rightTracking = Statistics.giveMeAnimalStatistics(trackedRightAnimal);
                    rightVboxMaps.getChildren().add(Statistics.giveMeStatistics(world, inputParameters.magicRight));
                    rightVboxMaps.getChildren().add(buttons);
                    rightVboxCharts.getChildren().add(rightTracking);
                    rightVboxMaps.setSpacing(20);
                }else{
                    rightVboxMaps.getChildren().add(Statistics.giveMeStatistics(world, inputParameters.magicRight));
                    rightVboxMaps.getChildren().add(buttons);
                    rightVboxMaps.setSpacing(20);
                }
            }
        });
    }


    public void makeMeTheWorld(InputParameters inputParameters) throws Exception {
        int moveDelay = 500;
        World leftWorld = new World(inputParameters, true);
        World rightWorld = new World(inputParameters, false);

        engineLeft = new CreatingWorld(leftWorld, moveDelay, this, true, inputParameters.magicLeft);
        engineRight = new CreatingWorld(rightWorld, moveDelay, this, false, inputParameters.magicRight);

        gridPaneLeft = createMapDrawing(engineLeft.darwinWorld, true);
        gridPaneLeft.setPrefSize(500, 500);

        gridPaneRight = createMapDrawing(engineRight.darwinWorld, false);
        gridPaneRight.setPrefSize(500, 500);


        rightVboxMaps = new VBox(gridPaneRight, Statistics.giveMeStatistics(engineRight.darwinWorld, inputParameters.magicRight));
        rightVboxMaps.setSpacing(20);
        rightVboxMaps.setBackground(new Background(new BackgroundFill(Color.web("DB6B97"), CornerRadii.EMPTY, Insets.EMPTY)));
        rightVboxCharts = new VBox();
        rightVboxCharts.setPrefSize(300, 800);
        rightVboxCharts.setBackground(new Background(new BackgroundFill(Color.web("DB6B97"), CornerRadii.EMPTY, Insets.EMPTY)));

        leftVboxMaps = new VBox(gridPaneLeft, Statistics.giveMeStatistics(engineLeft.darwinWorld, inputParameters.magicLeft));
        leftVboxMaps.setSpacing(20);
        leftVboxMaps.setBackground(new Background(new BackgroundFill(Color.web("DB6B97"), CornerRadii.EMPTY, Insets.EMPTY)));
        leftVboxCharts = new VBox();
        leftVboxCharts.setPrefSize(300, 800);
        leftVboxCharts.setBackground(new Background(new BackgroundFill(Color.web("DB6B97"), CornerRadii.EMPTY, Insets.EMPTY)));

        // todo zrobic vboxy z wykresami jak juz zrobie wykresy
        Thread engineLeftThread = new Thread(engineLeft);
        Thread engineRightThread = new Thread(engineRight);

        mainHbox.getChildren().clear();
        mainHbox.getChildren().add(new HBox(leftVboxCharts, leftVboxMaps));
        mainHbox.getChildren().add(new HBox(rightVboxCharts, rightVboxMaps));

        engineLeftThread.start();
        engineRightThread.start();
    }


    public void showInitForm(){
        GridPane initForm = prepareInitForm();
        mainHbox.getChildren().clear();
        mainHbox.getChildren().add(initForm);
        mainHbox.setBackground(new Background(new BackgroundFill(Color.web("DB6B97"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @Override
    public void init() throws Exception {
        Label sayHi = new Label("Do you wanna create a world?");
        sayHi.setFont(new Font("Verdana", 50));
        sayHi.setTextFill(Color.web("121212"));

        Label LetsBoom = new Label("Let's BLOOOOW the Universe!");
        LetsBoom.setFont(new Font("Verdana", 30));
        LetsBoom.setTextFill(Color.web("121212"));

        Button bomb = new Button();
        ImageView bombView = new ImageView(images.bombImage);
        bomb.setBackground(new Background(new BackgroundFill(Color.web("DB6B97"), CornerRadii.EMPTY, Insets.EMPTY)));
        bomb.setGraphic(bombView);
        bomb.setOnAction((event -> {
            showInitForm();
        }));

        VBox gameStart = new VBox(sayHi, LetsBoom, bomb);
        gameStart.setBackground(new Background(new BackgroundFill(Color.web("DB6B97"), CornerRadii.EMPTY, Insets.EMPTY)));
        gameStart.setSpacing(50);
        gameStart.setAlignment(Pos.CENTER);

        mainHbox.getChildren().clear();
        mainHbox.getChildren().add(gameStart);
        mainHbox.setAlignment(Pos.CENTER);

    }

    public GridPane prepareInitForm(){
        GridPane inputDataTaker = new GridPane();
        inputDataTaker.setPadding(new Insets(20, 20, 20, 20));
        inputDataTaker.setAlignment(Pos.CENTER);
        inputDataTaker.setHgap(20);
        inputDataTaker.setVgap(20);
        inputDataTaker.setBackground(new Background(new BackgroundFill(Color.web("FFB5B5"), CornerRadii.EMPTY, Insets.EMPTY)));

        Label widthLabel = new Label("Give me width of your new world: ");
        widthLabel.setFont(new Font("Verdana", 20));
        TextField width = new TextField();
        width.setPrefColumnCount(25);
        width.setPromptText("+int only");
        width.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        width.getText();
        GridPane.setConstraints(widthLabel, 0, 0);
        GridPane.setConstraints(width, 1, 0);
        inputDataTaker.getChildren().add(width);
        inputDataTaker.getChildren().add(widthLabel);

        Label heightLabel = new Label("Give me height of your new world: ");
        heightLabel.setFont(new Font("Verdana", 20));
        TextField height = new TextField();
        height.setPromptText("+int only");
        height.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        height.setPrefColumnCount(20);
        GridPane.setConstraints(heightLabel, 0, 1);
        GridPane.setConstraints(height, 1, 1);
        inputDataTaker.getChildren().add(height);
        inputDataTaker.getChildren().add(heightLabel);

        Label initialAnimal = new Label("How many animals should be at the beginning? ");
        initialAnimal.setFont(new Font("Verdana", 20));
        TextField animal = new TextField();
        animal.setPromptText("+int, but not bigger than width*height!");
        animal.setPrefColumnCount(20);
        animal.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        GridPane.setConstraints(initialAnimal, 0, 2);
        GridPane.setConstraints(animal, 1, 2);
        inputDataTaker.getChildren().add(animal);
        inputDataTaker.getChildren().add(initialAnimal);

        Label startEnergyLabel = new Label("Give me start energy of Adam and Eve: ");
        startEnergyLabel.setFont(new Font("Verdana", 20));
        TextField startEnergy = new TextField();
        startEnergy.setPromptText("+int only");
        startEnergy.setPrefColumnCount(20);
        startEnergy.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        GridPane.setConstraints(startEnergyLabel, 0, 3);
        GridPane.setConstraints(startEnergy, 1, 3);
        inputDataTaker.getChildren().add(startEnergy);
        inputDataTaker.getChildren().add(startEnergyLabel);

        Label moveEnergyLabel = new Label("Give me energy that each animal will lose everyday: ");
        moveEnergyLabel.setFont(new Font("Verdana", 20));
        TextField moveEnergy = new TextField();
        moveEnergy.setPromptText("+int only");
        moveEnergy.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        moveEnergy.setPrefColumnCount(20);
        GridPane.setConstraints(moveEnergyLabel, 0, 4);
        GridPane.setConstraints(moveEnergy, 1, 4);
        inputDataTaker.getChildren().add(moveEnergy);
        inputDataTaker.getChildren().add(moveEnergyLabel);

        Label plantEnergyLabel = new Label("How many kcal each plant have? ");
        plantEnergyLabel.setFont(new Font("Verdana", 20));
        TextField plantEnergy = new TextField();
        plantEnergy.setPromptText("+int only");
        plantEnergy.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        plantEnergy.setPrefColumnCount(20);
        GridPane.setConstraints(plantEnergyLabel, 0, 5);
        GridPane.setConstraints(plantEnergy, 1, 5);
        inputDataTaker.getChildren().add(plantEnergy);
        inputDataTaker.getChildren().add(plantEnergyLabel);

        Label jungleRatioLabel = new Label("Give me percent of jungle size: ");
        jungleRatioLabel.setFont(new Font("Verdana", 20));
        TextField jungleRatio = new TextField();
        jungleRatio.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        jungleRatio.setPromptText("+int only");
        jungleRatio.setPrefColumnCount(20);
        GridPane.setConstraints(jungleRatioLabel, 0, 6);
        GridPane.setConstraints(jungleRatio, 1, 6);
        inputDataTaker.getChildren().add(jungleRatio);
        inputDataTaker.getChildren().add(jungleRatioLabel);

        Label leftMagicLabel = new Label("Left map should be...? ");
        leftMagicLabel.setFont(new Font("Verdana", 20));
        ComboBox leftMap = new ComboBox();
        leftMap.setPrefSize(100, 20);
        leftMap.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        leftMap.getItems().add("Normal");
        leftMap.getItems().add("Magic");
        leftMap.getSelectionModel().selectFirst();
        GridPane.setConstraints(leftMagicLabel, 0, 7);
        GridPane.setConstraints(leftMap, 1, 7);
        inputDataTaker.getChildren().add(leftMagicLabel);
        inputDataTaker.getChildren().add(leftMap);

        Label rightMagicLabel = new Label("Right map should be...?");
        rightMagicLabel.setFont(new Font("Verdana", 20));
        ComboBox rightMap = new ComboBox();
        rightMap.setPrefSize(100, 20);
        rightMap.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        rightMap.getItems().add("Normal");
        rightMap.getItems().add("Magic");
        rightMap.getSelectionModel().selectFirst();
        GridPane.setConstraints(rightMagicLabel, 0, 8);
        GridPane.setConstraints(rightMap, 1, 8);
        inputDataTaker.getChildren().add(rightMagicLabel);
        inputDataTaker.getChildren().add(rightMap);

        Button submitButton = new Button("Let's begin!");
        submitButton.setPrefSize(100, 20);
        submitButton.setBackground(new Background(new BackgroundFill(Color.web("FED1EF"), CornerRadii.EMPTY, Insets.EMPTY)));
        GridPane.setConstraints(submitButton, 1, 9);
        inputDataTaker.getChildren().add(submitButton);

        submitButton.setOnAction((event -> {
            widthWorld = Integer.parseInt(width.getText());
            heightWorld = Integer.parseInt(height.getText());
            startAnimalEnergy = Integer.parseInt(startEnergy.getText());
            moveAnimalEnergy = Integer.parseInt(moveEnergy.getText());
            plantKcal  = Integer.parseInt(plantEnergy.getText());
            jungleRatioo = Integer.parseInt(jungleRatio.getText());
            initialNumberOfAnimals = Integer.parseInt(animal.getText());


            if (leftMap.getValue() == "Magic"){
                leftMagic = true;
            }else leftMagic = false;
            if (rightMap.getValue() == "Magic"){
                rightMagic = true;
            }else rightMagic = false;

            inputParameters = new InputParameters(widthWorld, heightWorld, initialNumberOfAnimals, startAnimalEnergy, moveAnimalEnergy, plantKcal, jungleRatioo, leftMagic, rightMagic);

            try {
                makeMeTheWorld(inputParameters);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }));

        return inputDataTaker;
    }


    private GridPane createMapDrawing(World world, Boolean isLeft) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        int numberOfColumns = inputParameters.getWidthWorld();
        int numberOfRows = inputParameters.getHeightWorld();
        int poleWidth = 500/numberOfColumns;
        int poleHeight = 500/numberOfRows;


        for (int i = 0; i < numberOfColumns; i++){
            ColumnConstraints columnConstraints = new ColumnConstraints(poleWidth);
            columnConstraints.setPercentWidth(100.0 / numberOfColumns);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < numberOfRows; i++){
            RowConstraints rowConstraints = new RowConstraints(poleHeight);
            rowConstraints.setPercentHeight(100.0/numberOfRows);
            gridPane.getRowConstraints().add(rowConstraints);
        }
        for (int x = 0; x < numberOfColumns; x++){
            for (int y = 0; y < numberOfRows; y++) {
                StackPane stack = new StackPane();
                Field field = world.fields.get(new Vector2d(x, y));
                if (field.isJungle) {
                    stack.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                } else stack.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                if (field.animals.size() > 0) {
                    ImageView imageView = animalColor(field.animals.peek());
                    imageView.setFitHeight(poleHeight);
                    imageView.setFitWidth(poleWidth);
                    stack.getChildren().add(imageView);
                    stack.setOnMouseClicked((event -> {
                        if (isLeft){
                            if (!engineLeft.isRunning){
                                isLeftAnimalTracked = true;
                                trackedLeftAnimal = field.animals.peek();
                                Scene animalStatistic = new Scene(Statistics.giveMeAnimalGenotype(field.animals.peek()));
                                Stage animalStage = new Stage();
                                animalStage.setTitle("Tracked animal genotype: ");
                                animalStage.setScene(animalStatistic);
                                animalStage.show();
                            }
                        }else{
                            if(!engineRight.isRunning){
                                isRightAnimalTracked = true;
                                trackedRightAnimal = field.animals.peek();
                                Scene animalStatistic = new Scene(Statistics.giveMeAnimalGenotype(field.animals.peek()));
                                Stage animalStage = new Stage();
                                animalStage.setTitle("Tracked animal genotype: ");
                                animalStage.setScene(animalStatistic);
                                animalStage.show();
                            }
                        }
                    }));
                } else if (field.isPlantGrown) {
                    ImageView imageView = new ImageView(images.bambooImage);
                    imageView.setFitWidth(poleWidth);
                    imageView.setFitHeight(poleHeight);
                    stack.getChildren().add(imageView);
                }
                GridPane.setHalignment(stack, HPos.CENTER);
                gridPane.add(stack, x, y);
            }
        }
        return gridPane;
    }

    public ImageView animalColor(Animal animal){
        int energy = animal.getEnergy();
        int allEnergy = inputParameters.startAnimalEnergy;
        Image imageToColor = images.animalImage;
        ImageView imageViewToColor = new ImageView(imageToColor);
        ColorAdjust effect0_25 = new ColorAdjust();
        effect0_25.setContrast(-0.5);
        ColorAdjust effect25_50 = new ColorAdjust();
        effect25_50.setContrast(0.0);
        ColorAdjust effect50_75 = new ColorAdjust();
        effect50_75.setContrast(0.5);
        ColorAdjust effect75_100 = new ColorAdjust();
        effect75_100.setContrast(1.0);

        if (energy >= 0 && energy < allEnergy/4){
            imageViewToColor.setEffect(effect0_25);
            return imageViewToColor;
        }else if (energy >= allEnergy/4 && energy < allEnergy/2){
            imageViewToColor.setEffect(effect25_50);
            return imageViewToColor;
        }else if (energy >= allEnergy/2 && energy < 3*allEnergy/4){
            imageViewToColor.setEffect(effect50_75);
            return imageViewToColor;
        }else {
            imageViewToColor.setEffect(effect75_100);
            return imageViewToColor;
        }
    }
}
