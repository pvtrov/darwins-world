package agh.ics.oop;

// tutaj sprawdzam co jest na danym polu, i skierowywuje do odpoweidnich dzialan


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;

public class Field implements IWorldMap{
    private Vector2d fieldAddress;
    private boolean isPlantGrown = false;
    private PriorityQueue<Animal> animals = new PriorityQueue<>((x, y) -> x.getTimeLeftToDeath() > y.getTimeLeftToDeath() ? 1 : -1);


    public void addingPlants(){
        isPlantGrown = true;
    }

    public Field(Vector2d fieldAddress, boolean isPlantGrown) {
        this.fieldAddress = fieldAddress;
        this.isPlantGrown = isPlantGrown;
    }

    public void addingAnimals(Animal animal){
        animals.add(animal);
    }

    @Override
    public boolean canPlacePlant(Vector2d position) {
        return false;
    }

    @Override
    public boolean canPlaceAnimal(Vector2d position) {
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }
}
