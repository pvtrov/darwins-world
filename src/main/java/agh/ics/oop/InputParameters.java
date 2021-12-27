package agh.ics.oop;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class InputParameters {
    int heightWorld;
    int widthWorld;
    int startAnimalEnergy;
    int initialNumberOfAnimals = 5;
    int initialNumberOfPlants = 10;
    int moveAnimalEnergy ;
    int plantKcal;
    int jungleRatioo;  // pobieram w procentach
    int jungleHeight;
    int jungleWidth;


    public InputParameters(int widthWorld, int heightWorld, int startAnimalEnergy, int moveAnimalEnergy, int plantKcal, int jungleRatioo){
        this.heightWorld = heightWorld;
        this.widthWorld = widthWorld;
        this.startAnimalEnergy = startAnimalEnergy;
        this.moveAnimalEnergy = moveAnimalEnergy;
        this.plantKcal = plantKcal;
        this.jungleRatioo = jungleRatioo;
        this.jungleHeight = jungleCounting();
        this.jungleWidth = getJungleWidth();
        this.initialNumberOfAnimals = 5;
        this.initialNumberOfPlants = 10;
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

