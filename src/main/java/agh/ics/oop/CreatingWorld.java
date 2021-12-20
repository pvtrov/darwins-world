package agh.ics.oop;

import java.util.Random;

public class CreatingWorld extends InputParameters {
    public MapField mapField;
    Random random = new Random();


    public void placingPlantsAtTheBegin(){
        int x; int y;

        for (int i = initialNumberOfPlants; i > 0; i++){
            do {
                x = random.nextInt(getWidth());
                y = random.nextInt(getHeight());
            }while (mapField.canPlacePlant(new Vector2d(x, y)));
            Plant plant = new Plant(new Vector2d(x, y));
            mapField.addingPlants(plant.getPosition(), plant);
        }
    }

    public void placingAdamAndEva(){
        int x; int y;

        for (int i = initialNumberOfPlants; i > 0; i++){
            do {
                x = random.nextInt(getWidth());
                y = random.nextInt(getHeight());
            }while (mapField.canPlaceAnimal(new Vector2d(x, y)));
            Animal animal = new Animal(new Vector2d(x, y));
            mapField.addingAnimals(animal.getPositionOnTheMap(), animal);
        }
    }
}
