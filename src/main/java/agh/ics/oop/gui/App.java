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
//    MapDrawings mapDrawings;
    private Object MapDrawings;

    public static void main(String[] args){
        Application.launch(App.class);
    }

    public void start(Stage primaryStage) throws Exception {
        gridPane = createMapDrawing();
        Scene scene = new Scene(gridPane, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() {
        System.out.println("cos2");
        int moveDelay = 500;
        world = new World();
        map = new WorldMap(5, 5, 2, 2);
        engine = new CreatingWorld(map,  moveDelay);
        System.out.println("ygy");
        Thread engineThread = new Thread(engine);

        engineThread.start();
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

        for (int x = 0; x < numberOfColumns; x++){
            for (int y = 0; y < numberOfRows; y++){
                Field field = world.fields.get(new Vector2d(x, y));
                String text;
                if ( x == 0 && y == 0 ) {
                    text = "x/y";
                    Label label = new Label(text);
                    GridPane.setHalignment(label, HPos.CENTER);
                    gridPane.add(label, x, y);
                }
                else if (x == 0) {
                    text = "" + y;
                    Label label = new Label(text);
                    GridPane.setHalignment(label, HPos.CENTER);
                    gridPane.add(label, x, y);
                }
                else if (y == 0) {
                    text = "" + x;
                    Label label = new Label(text);
                    GridPane.setHalignment(label, HPos.CENTER);
                    gridPane.add(label, x, y);
                }
                else {
                    FileInputStream input = new FileInputStream(giveMeImage(field));
                    javafx.scene.image.Image image = new Image(input);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(100.0);
                    imageView.setFitWidth(100.0);
                    GridPane.setHalignment(imageView, HPos.CENTER);
                    gridPane.add(imageView, x, y);
                }
            }
        }
        return gridPane;
    }

    private String giveMeImage(Field field) throws Exception{
        if (field.isEmpty()){
            if (field.isJungle) {
                return ("src/main/resources/jungle.png");
            }else{
                return "src/main/resources/savanna.jpg";
            }
        }else{
            if (field.animals.isEmpty()){
                return world.plants.get(field.fieldAddress).getPath();
            }else return world.animals.get(field.fieldAddress).getPath();
        }
    }
}
