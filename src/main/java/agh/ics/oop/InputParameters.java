package agh.ics.oop;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class InputParameters {
    public int heightWorld;
    public int widthWorld;
    public int startAnimalEnergy;
    public int initialNumberOfAnimals;
    public int initialNumberOfPlants;
    public int moveAnimalEnergy ;
    public int plantKcal;
    public int jungleRatioo;  // pobieram w procentach
    public int jungleHeight;
    public int jungleWidth;
    public boolean magicLeft;
    public boolean magicRight;


    public InputParameters(int widthWorld, int heightWorld, int initialNumberOfAnimals, int startAnimalEnergy, int moveAnimalEnergy, int plantKcal, int jungleRatioo, boolean magicLeft, boolean magicRight){
        this.heightWorld = heightWorld;
        this.widthWorld = widthWorld;
        this.startAnimalEnergy = startAnimalEnergy;
        this.moveAnimalEnergy = moveAnimalEnergy;
        this.plantKcal = plantKcal;
        this.jungleRatioo = jungleRatioo;
        this.jungleHeight = jungleCounting();
        this.jungleWidth = getJungleWidth();
        this.initialNumberOfAnimals = initialNumberOfAnimals;
        this.initialNumberOfPlants = 0;
        this.magicLeft = magicLeft;
        this.magicRight = magicRight;
    }

    public InputParameters() {
    }

    public int getHeightWorld(){
        return this.heightWorld;
    }

    public int getWidthWorld(){
        return this.widthWorld;
    }

    public int getStartEnergy(){
        return this.startAnimalEnergy;
    }

    public int getInitialNumberOfAnimals(){
        return initialNumberOfAnimals;
    }

    public int getInitialNumberOfPlants(){
        return initialNumberOfPlants;
    }

    public int getMoveAnimalEnergy(){
        return this.moveAnimalEnergy;
    }

    public int getPlantKcal(){
        return this.plantKcal;
    }

    public int getJungleRatioo(){
        return this.jungleRatioo;
    }

    public int getJungleHeight(){
        return jungleCounting();
    }

    public int getJungleWidth(){
        jungleHeight = jungleCounting();
        return (jungleRatioo *allFields/100) / jungleHeight;
    }

    int allFields; int jungleFields;
    // oblicznie jungli
    public int jungleCounting(){
        allFields = this.heightWorld * this.widthWorld;  // b, a
        jungleFields = this.jungleRatioo *allFields/100;  // A
        jungleHeight = (int) Math.sqrt(jungleFields* this.heightWorld / this.widthWorld);
        return jungleHeight;
    }
}

