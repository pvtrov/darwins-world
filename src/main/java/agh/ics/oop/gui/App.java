package agh.ics.oop.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App  extends Application{
    private GridPane gridPane ;
    private AbstractWorldMap abstractWorldMap;
    private Stage stage;
    private SimulationEngine engine;


    public void drawMap() {
        Platform.runLater(() -> {
            gridPane.getChildren().clear();
            this.gridPane = new GridPane();
            drawFrame();
            try {
                drawObjects();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            gridPane.setGridLinesVisible(true);
            stage.setScene(new Scene(gridPane,400,400));
            stage.show();
        });

    }



    public void start(Stage primaryStage) throws Exception {
        int moveDelay = 500;
        gridPane = new GridPane();
        stage = primaryStage;
        Object[] args = getParameters().getRaw().toArray();
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map = new GrassField(10);
        abstractWorldMap = (AbstractWorldMap) map;
        //IWorldMap map = new RectangularMap(10,10);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        engine = new SimulationEngine(directions, map, positions, this, moveDelay);
        init();
    }
}
