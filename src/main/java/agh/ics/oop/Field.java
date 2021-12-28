package agh.ics.oop;

import java.util.Comparator;
import java.util.PriorityQueue;

// this class is responsible for each position on the map

public class Field implements IWorldMap{
    public Vector2d fieldAddress;
    public boolean isPlantGrown = false;
    public boolean isJungle = false;
    public PriorityQueue<Animal> animals = new PriorityQueue<>(Comparator.comparingInt(Animal::getEnergy));


    // constructor
    public Field(Vector2d fieldAddress, boolean isPlantGrown, boolean isJungle) {
        this.isJungle = isJungle;
        this.fieldAddress = fieldAddress;
        this.isPlantGrown = isPlantGrown;
    }

    // methods
    public void addingAnimals(Animal animal){
        this.animals.add(animal);
    }

    public void removingAnimals(Animal animal){
        this.animals.remove(animal);
    }

    public void addingPlants(){
        isPlantGrown = true;
    }

    public void removingPlants(){
        isPlantGrown = false;
    }

    @Override
    public boolean canPlacePlant(Vector2d fieldAddress) {
        return isPlantGrown == false && animals.isEmpty();
    }

    @Override
    public boolean canPlaceAnimal(Vector2d fieldAddress) {
        return animals.isEmpty();
    }

}
