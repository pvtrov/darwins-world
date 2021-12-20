package agh.ics.oop;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;
import java.util.Random;
import agh.ics.oop.MapField;

public class CreatingWorld extends InputParameters {
    public HashMap<Vector2d, Plant> plantsOnTheMap = new HashMap<>();
    public LinkedHashMap<Vector2d, Animal> animalsOnTheMap = new LinkedHashMap<>();
    public MapField mapField;
    Random random = new Random();


    // todo gdy ogarne jungle to zmienic wymiary tego do drukowania
    public void placingPlantsAtTheBegin(){
        int x; int y;

        for (int i = getInitialNumberOfPlants(); i > 0; i--){
            do {
                x = random.nextInt(getWidth());
                y = random.nextInt(getHeight());
            }while (mapField.canPlacePlant(new Vector2d(x, y)));
            Plant plant = new Plant(new Vector2d(x, y));
            plantsOnTheMap.put(plant.getPosition(), plant);
        }
    }

    public void placingAdamAndEva(){
        int x; int y;

        for (int i = getInitialNumberOfAnimals(); i > 0; i--){
            do {
                x = random.nextInt(getWidth());
                y = random.nextInt(getHeight());
            }while (mapField.canPlaceAnimal(new Vector2d(x, y)));
            Animal animal = new Animal(new Vector2d(x, y));
            animalsOnTheMap.put(animal.getPositionOnTheMap(), animal);
        }
    }

    public void removingDeadAnimal(Animal animal){
        animalsOnTheMap.remove(animal.getPositionOnTheMap(), animal);
    }

    public void removingEatenPlant(Plant plant){
        plantsOnTheMap.remove(plant.getPosition());
    }
}
