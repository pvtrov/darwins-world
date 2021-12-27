package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.Field;
import agh.ics.oop.IMapElement;
import agh.ics.oop.World;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {


    public static VBox giveMeStatistics(Animal animal){
        Text genotype = new Text();
        Text kids = new Text();
        Text descendants = new Text();
        Text death = new Text();

        genotype.setText("Genotype of this animal is " + animal.getGenotypeToStatistic());
        genotype.setFont(javafx.scene.text.Font.font("Verdana", 25));
        kids.setText("This animal has " + animal.getNumberOfKids() + " kids");
        kids.setFont(javafx.scene.text.Font.font("Verdana", 25));
        descendants.setText("This animal has " + animal.getNumberOfDescendants() + " descendants");
        descendants.setFont(javafx.scene.text.Font.font("Verdana", 25));
        death.setFont(Font.font("Verdana", 25));
        death.setText("This animal died in " + animal.getDayOfDeath() + " day of his life");


        VBox statistic = new VBox(genotype, kids, descendants, death);
        statistic.setAlignment(Pos.CENTER);
        statistic.setSpacing(20);
        return statistic;
    }


}
