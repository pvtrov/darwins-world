package agh.ics.oop;

public class InputParameters {
    int height = 10;
    int width = 10;
    int initialEnergy = 30;
    int initialNumberOfAnimals = 3;
    int initialNumberOfPlants = 4;
    int lostEnergy = 2;
    int plantKcal = 5;
    int jungleRatio;
    int jungleHeight;
    int jungleWidth;

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public int getInitialEnergy(){
        return initialEnergy;
    }

    public int getInitialNumberOfAnimals(){
        return initialNumberOfAnimals;
    }

    public int getInitialNumberOfPlants(){
        return initialNumberOfPlants;
    }

    public int getLostEnergy(){
        return lostEnergy;
    }

    public int getPlantKcal(){
        return plantKcal;
    }

    public int getJungleRatio(){
        return jungleRatio;
    }

}

