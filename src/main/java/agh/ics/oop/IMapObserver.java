package agh.ics.oop;

import javafx.scene.layout.HBox;

public interface IMapObserver {

    void updateHBox(World world, Boolean isLeft) throws Exception;
}
