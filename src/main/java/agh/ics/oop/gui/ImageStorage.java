package agh.ics.oop.gui;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageStorage {

    public Image bambooImage;
    public Image animalImage;
    public Image bombImage;

    public ImageStorage() throws FileNotFoundException {
        bambooImage = new Image(new FileInputStream("src/main/resources/plant-min.png"), 30, 30, true, true);
        animalImage = new Image(new FileInputStream("src/main/resources/tiger-min.png"), 30, 30, true, true);
        bombImage = new Image(new FileInputStream("src/main/resources/bomb-png-46589.png"), 100, 100, true, true);
    }

}
