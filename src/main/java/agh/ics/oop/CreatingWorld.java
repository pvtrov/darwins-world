package agh.ics.oop;

import java.util.*;

public class CreatingWorld extends InputParameters {
    private World darwinWorld;
    private List<Field> fields = new ArrayList<>();
    Random random = new Random();


    public void createWorld(){
        World darwinWorld = new World(getWidth(), getHeight(), getJungleWidth(), getJungleHeight());

    }

    // todo gdy ogarne jungle to zmienic wymiary tego do drukowania
    public void placingPlantsAtTheBegin(){
        int x; int y;

        for (int i = getInitialNumberOfPlants(); i > 0; i--){
            do {
                x = random.nextInt(getWidth());
                y = random.nextInt(getHeight());
            }while (field.canPlacePlant(new Vector2d(x, y)));
            Plant plant = new Plant(new Vector2d(x, y));
            f.put(plant.getPosition(), plant);
        }
    }

    public void placingAdamAndEva(){
        int x; int y;

        for (int i = getInitialNumberOfAnimals(); i > 0; i--){
            do {
                x = random.nextInt(getWidth());
                y = random.nextInt(getHeight());
            }while (field.canPlaceAnimal(new Vector2d(x, y)));
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
