package agh.ics.oop;

public class InputParameters {
    int height = 10;
    int width = 10;
    int startEnergy = 30;
    int initialNumberOfAnimals = 3;
    int initialNumberOfPlants = 4;
    int moveEnergy = 2;
    int plantKcal = 5;
    int jungleRatio = 25;  // pobieram w procentach
    int jungleHeight;
    int jungleWidth;

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getStartEnergy(){
        return startEnergy;
    }

    public int getInitialNumberOfAnimals(){
        return initialNumberOfAnimals;
    }

    public int getInitialNumberOfPlants(){
        return initialNumberOfPlants;
    }

    public int getMoveEnergy(){
        return moveEnergy;
    }

    public int getPlantKcal(){
        return plantKcal;
    }

    public int getJungleRatio(){
        return jungleRatio;
    }

    public int getJungleHeight(){
        return jungleCounting();
    }

    public int getJungleWidth(){
        jungleHeight = jungleCounting();
        return (jungleRatio*allFields/100) / jungleHeight;
    }

    int allFields; int jungleFields;
    // oblicznie jungli
    public int jungleCounting(){
        allFields = height * width;  // b, a
        jungleFields = jungleRatio*allFields/100;  // A
        jungleHeight = (int) Math.sqrt(jungleFields*height/width);
        return jungleHeight;
    }
}

