package agh.ics.oop;

// tutaj sprawdzam co jest na danym polu, i skierowywuje do odpoweidnich dzialan


import java.util.PriorityQueue;

public class Field implements IWorldMap{
    public Vector2d fieldAddress;
    public boolean isPlantGrown = false;
    public boolean isJungle = false;
    public PriorityQueue<Animal> animals = new PriorityQueue<>((x, y) -> x.getEnergy() > y.getEnergy() ? 1 : -1);


    public void addingPlants(){
        isPlantGrown = true;
    }

    public void removingPlants(){
        isPlantGrown = false;
    }

    public Field(Vector2d fieldAddress, boolean isPlantGrown, boolean isJungle) {
        this.isJungle = isJungle;
        this.fieldAddress = fieldAddress;
        this.isPlantGrown = isPlantGrown;
    }

    public void addingAnimals(Animal animal){
        animals.add(animal);
    }


    public boolean isEmpty(){
        return isPlantGrown == false && animals.isEmpty();
    }

    public void removingAnimals(Animal animal){
        animals.remove(animal);
    }

    @Override
    public boolean canPlacePlant(Vector2d fieldAddress) {
        return isPlantGrown == false && animals.isEmpty();
    }

    @Override
    public boolean canPlaceAnimal(Vector2d fieldAddress) {
        return animals.isEmpty();
    }

    @Override
    public boolean isOccupied(Vector2d fieldAddress) {
        return !animals.isEmpty();
    }
}
