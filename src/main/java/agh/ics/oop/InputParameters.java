package agh.ics.oop;

public class InputParameters {
    int height = 7;
    int width = 7;
    int startEnergy = 100;
    int initialNumberOfAnimals = 15;
    int initialNumberOfPlants = 20;
    int moveEnergy = 10;
    int plantKcal = 20;
    int jungleRatio = 30;  // pobieram w procentach
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

