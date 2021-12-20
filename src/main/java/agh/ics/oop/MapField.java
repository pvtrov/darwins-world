package agh.ics.oop;

// tutaj sprawdzam co jest na danym polu, i skierowywuje do odpoweidnich dzialan


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;

public class MapField implements IWorldMap{
    private HashMap<Vector2d, Plant> plantsOnTheMapField;
    private LinkedHashMap<Vector2d, Animal> animalsOnTheMapField;

    public void addingPlants(Vector2d position, Plant plant){
        plantsOnTheMapField.put(position, plant);
    }

    public void addingAnimals(Vector2d position, Animal animal){
        animalsOnTheMapField.put(position, animal);
    }

    public boolean canPlacePlant(Vector2d position){
        return !isOccupied(position);
    }

    public boolean canPlaceAnimal(Vector2d position){
        return animalsOnTheMapField.get(position) == null;
    }

    public boolean isOccupied(Vector2d position){
        return plantsOnTheMapField.get(position) == null && animalsOnTheMapField.get(position) == null;
    }


}
