package agh.ics.oop.gui;

import agh.ics.oop.CreatingWorld;
import agh.ics.oop.Vector2d;
import agh.ics.oop.World;
import agh.ics.oop.WorldMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileNotFoundException;

public class App  extends Application{
    private GridPane gridPane ;
    private WorldMap map;
    private World darwinWorld ;
    private Stage stage;
    private CreatingWorld engine;

    public static void main(String[] args){
        Application.launch(App.class);
    }

    public void start(Stage primaryStage) throws Exception {
        int noOfRows = 5;
        int noOfCols = 6;

        primaryStage.setTitle("FX GridPane Example");
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        for (int i = 0; i < noOfCols; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(50); // width in pixels
    		columnConstraints.setPercentWidth(100.0 / noOfCols); // percentage of total width
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < noOfRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / noOfRows);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < noOfCols; i++) {
            for (int j = 0; j < noOfRows; j++) {
                String text;
                if (i == 0 && j == 0) text = "x / y";
                else if (i == 0) text = "" + j;
                else if (j == 0) text = "" + i;
                else text = "*";
                Label label = new Label(text);
                GridPane.setHalignment(label, HPos.CENTER);
                gridPane.add(label, i, j);
            }
        }

        Scene scene = new Scene(gridPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() {
        System.out.println("cos2");
        int moveDelay = 500;
        map = new WorldMap(5, 5, 2, 2);
        engine = new CreatingWorld(map,  moveDelay);
        System.out.println("ygy");
        Thread engineThread = new Thread(engine);

        engineThread.start();
    }

    public void drawMap() {
        Platform.runLater(() -> {
            gridPane.getChildren().clear();
            this.gridPane = new GridPane();
            drawFrame();
//            try {
//                drawObjects();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            gridPane.setGridLinesVisible(true);
            stage.setScene(new Scene(gridPane,400,400));
            stage.show();
        });

    }

    private void drawFrame(){
        Vector2d upperRight = darwinWorld.map.getMapUpperRight();
        Vector2d lowerLeft = new Vector2d(0, 0);
        Label label = new Label("y/x");
        gridPane.add(label,0,0);
        gridPane.getColumnConstraints().add(new ColumnConstraints(20));
        gridPane.getRowConstraints().add(new RowConstraints(20));
        GridPane.setHalignment(label, HPos.CENTER);

        int i = lowerLeft.x;
        int position = 1;
        while(i < upperRight.x + 1){
            Label number = new Label(Integer.toString(i));
            gridPane.add(number, position, 0);
            gridPane.getColumnConstraints().add(new ColumnConstraints(35));
            GridPane.setHalignment(number, HPos.CENTER);
            position++;
            i++;
        }

        i = upperRight.y;
        position = 1;
        while(i >= lowerLeft.y){
            Label number = new Label(Integer.toString(i));
            gridPane.add(number, 0,  position);
            gridPane.getRowConstraints().add(new RowConstraints(35));
            GridPane.setHalignment(number, HPos.CENTER);
            position++;
            i--;
        }
    }


}
