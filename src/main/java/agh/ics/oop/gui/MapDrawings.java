//package agh.ics.oop.gui;
//
//import agh.ics.oop.Field;
//import agh.ics.oop.Vector2d;
//import agh.ics.oop.World;
//import javafx.geometry.HPos;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.RowConstraints;
//import javafx.scene.control.Label;
//
//
//import java.awt.*;
//import java.io.FileInputStream;
//
//public class MapDrawings {
//
//    World world;
//
//    public MapDrawings(World world) throws Exception {
//        this.world = world;
//        gridPane.setGridLinesVisible(true);
//        createMapDrawing();
//    }
//
//    private void createMapDrawing() throws Exception {
//        GridPane gridPane = new GridPane();
//        int numberOfColumns = world.map.getWidth();
//        int numberOfRows = world.map.getHeight();
//
//        for (int i = 0; i < numberOfColumns; i++){
//            ColumnConstraints columnConstraints = new ColumnConstraints(50);
//            columnConstraints.setPercentWidth(100.0 / numberOfColumns);
//            gridPane.getColumnConstraints().add(columnConstraints);
//        }
//
//        for (int i = 0; i < numberOfRows; i++){
//            RowConstraints rowConstraints = new RowConstraints();
//            rowConstraints.setPercentHeight(100.0/numberOfRows);
//            gridPane.getRowConstraints().add(rowConstraints);
//        }
//
//        for (int x = 0; x < numberOfColumns; x++){
//            for (int y = 0; y < numberOfRows; y++){
//                Field field = world.fields.get(new Vector2d(x, y));
//                String text;
//                if ( x == 0 && y == 0 ) {
//                    text = "x/y";
//                    Label label = new Label(text);
//                    GridPane.setHalignment(label, HPos.CENTER);
//                    gridPane.add(label, x, y);
//                }
//                else if (x == 0) {
//                    text = "" + y;
//                    Label label = new Label(text);
//                    GridPane.setHalignment(label, HPos.CENTER);
//                    gridPane.add(label, x, y);
//                }
//                else if (y == 0) {
//                    text = "" + x;
//                    Label label = new Label(text);
//                    GridPane.setHalignment(label, HPos.CENTER);
//                    gridPane.add(label, x, y);
//                }
//                else {
//                    FileInputStream input = new FileInputStream(giveMeImage(field));
//                    Image image = new Image(input);
//                    ImageView imageView = new ImageView(image);
//                    GridPane.setHalignment(imageView, HPos.CENTER);
//                    gridPane.add(imageView, x, y);
//                }
//            }
//        }
//    }
//
//    private String giveMeImage(Field field) throws Exception{
//        if (field.isEmpty()){
//            if (field.isJungle) {
//                return ("src/main/resources/jungle.png");
//            }else{
//                return "src/main/resources/savanna.jpg";
//            }
//        }else{
//            if (field.animals.isEmpty()){
//                return world.plants.get(field.fieldAddress).getPath();
//            }else return world.animals.get(field.fieldAddress).getPath();
//        }
//    }
//
//
//}
