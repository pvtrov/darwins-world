package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import agh.ics.oop.InputParameters;

import java.io.FileInputStream;
import java.rmi.dgc.VMID;


// todo cos tu nie dziala ale co rasz blizej

// PRZED ODDANIEM:
// todo sprawdzic czy nie ma komentarzy nie potrzenych
// todo uładnic komentarze
// todo dodać wyjatki
// todo dodac tetsy ze 2
// todo sprawdzic prywatnosci w klasach

public class App extends Application{
    private VBox mainVbox = new VBox();
    private InputParameters inputParameters;
    private GridPane gridPane ;
    private WorldMap map;
    private World world ;
    private Stage stage;
    private CreatingWorld engine;
    private Object MapDrawings;
    private int widthWorld;
    private int heightWorld;
    private int startAnimalEnergy;
    private int moveAnimalEnergy;
    private int plantKcal;
    private int jungleRatioo;

    public static void main(String[] args){
        Application.launch(App.class);
    }

    public void start(Stage primaryStage) throws Exception {
        HBox leftHbox = new HBox();
        HBox rightHbox = new HBox();
//        movingOnTheMap(gridPane, world);
//        mainVbox.getChildren().clear();
//        mainVbox.getChildren().add(gridPane);
//        mainVbox.getChildren().clear();
//        mainVbox.getChildren().add(gridPane);
        Scene scene = new Scene(mainVbox, widthWorld*100, heightWorld*100);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public void makeMeTheWorld(InputParameters inputParameters) throws Exception {
        int moveDelay = 1000;
        world = new World(inputParameters);
        engine = new CreatingWorld(world, moveDelay, false);
        gridPane = createMapDrawing();
        Thread engineThread = new Thread(engine);

        engineThread.start();
    }


    @Override
    public void init() throws Exception {
        GridPane startGridPane = programStart();
        mainVbox.getChildren().add(startGridPane);

    }

    public GridPane programStart(){
        GridPane inputDataTaker = new GridPane();
        inputDataTaker.setPadding(new Insets(20, 20, 20, 20));
        inputDataTaker.setHgap(10);
        inputDataTaker.setVgap(10);

        TextField width = new TextField("Give me width of your new world, e.g 10");
        width.setPrefColumnCount(15);
        width.getText();
        GridPane.setConstraints(width, 0, 0);
        inputDataTaker.getChildren().add(width);

        TextField height = new TextField("Give me height of your new world, e.g 10");
        height.setPrefColumnCount(15);
        GridPane.setConstraints(height, 0, 1);
        inputDataTaker.getChildren().add(height);

        TextField startEnergy = new TextField("How many energy Adam and Eve should have? +int only");
        startEnergy.setPrefColumnCount(15);
        GridPane.setConstraints(startEnergy, 0, 2);
        inputDataTaker.getChildren().add(startEnergy);

        TextField moveEnergy = new TextField("Tell me, how much energy animal should lose everyday? +int only");
        moveEnergy.setPrefColumnCount(15);
        GridPane.setConstraints(moveEnergy, 0, 3);
        inputDataTaker.getChildren().add(moveEnergy);

        TextField plantEnergy = new TextField("How do think, how many energy each plant has? +int only please");
        plantEnergy.setPrefColumnCount(15);
        GridPane.setConstraints(plantEnergy, 1, 0);
        inputDataTaker.getChildren().add(plantEnergy);

        TextField jungleRatio = new TextField("in percent, how much jungle should be? +int only");
        jungleRatio.setPrefColumnCount(15);
        GridPane.setConstraints(jungleRatio, 1, 1);
        inputDataTaker.getChildren().add(jungleRatio);

        Button submitButton = new Button("let's begin");
        GridPane.setConstraints(submitButton, 1, 2);
        inputDataTaker.getChildren().add(submitButton);

        submitButton.setOnAction((event -> {
            widthWorld = Integer.parseInt(width.getText());
            heightWorld = Integer.parseInt(height.getText());
            startAnimalEnergy = Integer.parseInt(startEnergy.getText());
            moveAnimalEnergy = Integer.parseInt(moveEnergy.getText());
            plantKcal  = Integer.parseInt(plantEnergy.getText());
            jungleRatioo = Integer.parseInt(jungleRatio.getText());

            inputParameters = new InputParameters(widthWorld, heightWorld, startAnimalEnergy, moveAnimalEnergy, plantKcal, jungleRatioo);

            try {
                makeMeTheWorld(inputParameters);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }));

        return inputDataTaker;
    }

    public void startEngine() throws Exception {
//        int moveDelay = 1000;
//        world = new World();
//        map = new WorldMap(world.map.getWidthWorld(), world.map.getHeightWorld(), world.map.getJungleHeight(), world.map.getJungleWidth());
//        engine = new CreatingWorld(map, world, moveDelay);
//        gridPane = createMapDrawing();
//        Thread engineThread = new Thread(engine);
//
//        engineThread.start();
    }




    // todo dodac zeby button dawal statystyki
    public GridPane movingOnTheMap(GridPane gridPane, World world) throws Exception {
        int numberOfColumns = world.inputParameters.getWidthWorld();
        int numberOfRows = world.inputParameters.getHeightWorld();

        for (int x = 0; x < numberOfColumns; x++){
            for (int y = 0; y < numberOfRows; y++){
                Field field = world.fields.get(new Vector2d(x, y));
                FileInputStream input = new FileInputStream(giveMeImage(field));
                javafx.scene.image.Image image = new Image(input);
                ImageView imageView = new ImageView(image);
                if (!field.animals.isEmpty()){
                    imageView.setPickOnBounds(true);
                    imageView.setOnMouseClicked((event -> {
                        Scene tempScene = new Scene(GuiElementBox.giveMeStatistics(field.animals.peek()));
                        Stage animalStage = new Stage();
                        animalStage.setScene(tempScene);
                        animalStage.show();
                    }));
//                    Button button = new Button();
//                    button.setGraphic(imageView);
//                    button.setOnAction((event -> {
//                        Scene tempScene = new Scene(GuiElementBox.giveMeStatistics(field.animals.peek()));
//                        Stage animalStage = new Stage();
//                        animalStage.setScene(tempScene);
//                        animalStage.show();
//                    }));
//                    imageView.setFitHeight(80.0);
//                    imageView.setFitWidth(80.0);
//                    GridPane.setHalignment(button, HPos.CENTER);
//                    gridPane.add(button, x, y);
                }
                imageView.setFitHeight(100.0);
                imageView.setFitWidth(100.0);
                GridPane.setHalignment(imageView, HPos.CENTER);
                gridPane.add(imageView, x, y);
            }
        }
        return gridPane;
    }

    private GridPane createMapDrawing() throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        int numberOfColumns = inputParameters.getWidthWorld();
        int numberOfRows = inputParameters.getHeightWorld();

        for (int i = 0; i < numberOfColumns; i++){
            ColumnConstraints columnConstraints = new ColumnConstraints(50);
            columnConstraints.setPercentWidth(100.0 / numberOfColumns);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < numberOfRows; i++){
            RowConstraints rowConstraints = new RowConstraints(100);
            rowConstraints.setPercentHeight(100.0/numberOfRows);
            gridPane.getRowConstraints().add(rowConstraints);
        }
        return gridPane;
    }

    private String giveMeImage(Field field) throws Exception{
        if (field.isEmpty()){
            if (field.isJungle) {
                return ("src/main/resources/jungle.png");
            }else{
                return "src/main/resources/savanna.png";
            }
        }else{
            if (field.animals.isEmpty()){
                return world.plants.get(field.fieldAddress).getPath();
            }else return field.animals.peek().getPath();
        }
    }



}
