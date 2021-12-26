package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

// todo cos tu nie dziala ale co rasz blizej

// PRZED ODDANIEM:
// todo sprawdzic czy nie ma komentarzy nie potrzenych
// todo uładnic komentarze
// todo dodać wyjatki
// todo dodac tetsy ze 2
// todo sprawdzic prywatnosci w klasach

public class App extends Application{
    private GridPane gridPane ;
    private WorldMap map;
    private World world ;
    private Stage stage;
    private CreatingWorld engine;

    public static void main(String[] args){
        Application.launch(App.class);
    }

    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(gridPane, world.map.getWidth()*100, world.map.getHeight()*100);
        movingOnTheMap(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        int moveDelay = 500;
        world = new World();
        map = new WorldMap(world.map.getWidth(), world.map.getHeight(), world.map.getJungleHeight(), world.map.getJungleWidth());
        engine = new CreatingWorld(map, world, moveDelay);
        gridPane = createMapDrawing();
        Thread engineThread = new Thread(engine);

        engineThread.start();
    }

    public GridPane movingOnTheMap(GridPane gridPane) throws Exception {
        int numberOfColumns = world.map.getWidth();
        int numberOfRows = world.map.getHeight();

        for (int x = 0; x < numberOfColumns; x++){
            for (int y = 0; y < numberOfRows; y++){
                Field field = world.fields.get(new Vector2d(x, y));
                FileInputStream input = new FileInputStream(giveMeImage(field));
                javafx.scene.image.Image image = new Image(input);
                ImageView imageView = new ImageView(image);
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
        int numberOfColumns = world.map.getWidth();
        int numberOfRows = world.map.getHeight();

        for (int i = 0; i < numberOfColumns; i++){
            ColumnConstraints columnConstraints = new ColumnConstraints(50);
            columnConstraints.setPercentWidth(100.0 / numberOfColumns);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < numberOfRows; i++){
            RowConstraints rowConstraints = new RowConstraints(50);
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
