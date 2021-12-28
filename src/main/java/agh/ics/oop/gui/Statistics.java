package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Statistics {


    // givers of statistics
    public static VBox giveMeAnimalGenotype(Animal animal){
        Text genotype = new Text();
        genotype.setText("Genotype of this animal is " + animal.getGenotypeToStatistic());
        genotype.setFont(javafx.scene.text.Font.font("Verdana", 25));

        VBox animalGenotype = new VBox(genotype);
        animalGenotype.setMinWidth(200); animalGenotype.setMinHeight(100);
        animalGenotype.setBackground(new Background(new BackgroundFill(Color.web("E2C2B9"), CornerRadii.EMPTY, Insets.EMPTY)));
        animalGenotype.setAlignment(Pos.CENTER);
        return animalGenotype;
    }

    public static VBox giveMeAnimalStatistics(Animal animal){
        Text title = new Text();
        Text kids = new Text();
        Text descendants = new Text();
        Text death = new Text();
        Text alive = new Text();

        title.setText("Tracked animal statistics of life: ");
        title.setFont(Font.font("Verdana", 20));
        kids.setText("This animal has " + animal.getNumberOfKids() + " kids");
        kids.setFont(javafx.scene.text.Font.font("Verdana", 15));
        descendants.setText("This animal has " + animal.getNumberOfDescendants() + " descendants");
        descendants.setFont(javafx.scene.text.Font.font("Verdana", 15));
        death.setFont(Font.font("Verdana", 15));
        death.setText("This animal died in " + animal.getDayOfDeath() + " day of his life");
        alive.setText("Tracked animal is still alive!");
        alive.setFont(Font.font("Verdana", 15));


        if (Integer.valueOf(animal.getDayOfDeath()) == 0){
            VBox animalStatistic = new VBox(title, kids, descendants, alive);
            animalStatistic.setAlignment(Pos.CENTER);
            animalStatistic.setBackground(new Background(new BackgroundFill(Color.web("E2C2B9"), CornerRadii.EMPTY, Insets.EMPTY)));
            animalStatistic.setSpacing(20);
            return animalStatistic;
        }else { VBox animalStatistic = new VBox(title, kids, descendants, death);
            animalStatistic.setAlignment(Pos.CENTER);
            animalStatistic.setBackground(new Background(new BackgroundFill(Color.web("E2C2B9"), CornerRadii.EMPTY, Insets.EMPTY)));
            animalStatistic.setSpacing(20);
            return animalStatistic;}


    }

    public static VBox giveMeStatistics(World world, Boolean isThisMapMagic){
        Text title = new Text();
        Text numberOfAnimals = new Text();
        Text numberOfDeadAnimals = new Text();
        Text numberOfPlants = new Text();
        Text genotypDomin = new Text();
        Text averageOfEnergy = new Text();
        Text avrageOfLiving = new Text();
        Text averageOfKids = new Text();
        Text magicToUse = new Text();

        title.setText("Statistics: ");
        title.setFont(Font.font("Verdana", 20));
        magicToUse.setText("Left "+ world.getMagicNumber() + " magic tricks to use!");
        magicToUse.setFont(Font.font("Verdana", 17));
        numberOfAnimals.setText("Number of living animals is " + world.getNumberOfLivingAnimals());
        numberOfAnimals.setFont(Font.font("Verdana", 17));
        numberOfDeadAnimals.setText("Number of dead animals is " + world.getNumberOfDeadAnimals());
        numberOfDeadAnimals.setFont(Font.font("Verdana", 17));
        numberOfPlants.setText("Number of plants " + world.getNumberOfPlants());
        numberOfPlants.setFont(Font.font("Verdana", 17));
        genotypDomin.setText("Dominant genotype is " + world.getDominantGenotype());
        genotypDomin.setFont(Font.font("Verdana", 17));
        averageOfEnergy.setText("Average of energy " + world.getAverageOfEnergy());
        averageOfEnergy.setFont(Font.font("Verdana", 17));
        avrageOfLiving.setText("Average of living time " + world.getAverageOfLivingTime());
        avrageOfLiving.setFont(Font.font("Verdana", 17));
        averageOfKids.setFont(Font.font("Verdana", 17));
        averageOfKids.setText("Average of kids is " + world.getAverageOfNumberOfKids());

        if (isThisMapMagic ){
            VBox statistics = new VBox(title, magicToUse, numberOfAnimals, numberOfDeadAnimals, numberOfPlants, genotypDomin, avrageOfLiving, averageOfEnergy, averageOfKids);
            statistics.setAlignment(Pos.CENTER);
            statistics.setBackground(new Background(new BackgroundFill(Color.web("E2C2B9"), CornerRadii.EMPTY, Insets.EMPTY)));
            statistics.setSpacing(15);
            return statistics;
        }else{
            VBox statistics = new VBox(title, numberOfAnimals,numberOfDeadAnimals, numberOfPlants, genotypDomin, avrageOfLiving, averageOfEnergy, averageOfKids);
            statistics.setAlignment(Pos.CENTER);
            statistics.setBackground(new Background(new BackgroundFill(Color.web("E2C2B9"), CornerRadii.EMPTY, Insets.EMPTY)));
            statistics.setSpacing(15);
            return statistics;
        }
    }

}
