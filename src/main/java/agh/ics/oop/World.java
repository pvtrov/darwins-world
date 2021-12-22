package agh.ics.oop;


import agh.ics.oop.gui.App;
import javafx.application.Application;

import java.util.*;

public class World extends InputParameters {
    Random random = new Random();
    public LinkedHashMap<Vector2d, Animal> animals;
    public HashMap<Vector2d, Plant> plants;
    public WorldMap map;
    public ArrayList<Field> fields = new ArrayList<>();


    public World(int width, int height, int jungleWidth, int jungleHeight){
        // todo tu zrobic tak zeby bylka mapka
        createMap();
        placingPlantsAtTheBegin();
        placingAdamAndEva();


    }

    private void createMap(){
    }
    // todo zmienic rozstawianie animali
    public void placingPlantsAtTheBegin(){
        int x; int y;
        for (int i = getInitialNumberOfPlants(); i > 0; i--){
            Field field;
            do {
                x = random.nextInt(getWidth());
                y = random.nextInt(getHeight());
                int fieldAddress = map.getFieldAddress(new Vector2d(x, y));
                field = fields.get(fieldAddress-1);
            }while (field.canPlacePlant(new Vector2d(x, y)));
            Plant plant = new Plant(new Vector2d(x, y));
            field.addingPlants();
            plants.put(plant.getPosition(), plant);
        }
    }

    public void placingAdamAndEva(){
        int x; int y;
        for (int i = getInitialNumberOfAnimals(); i > 0; i--){
            Field field;
            do {
                x = random.nextInt(getWidth());
                y = random.nextInt(getHeight());
                int fieldAddress = map.getFieldAddress(new Vector2d(x, y));
                field = fields.get(fieldAddress-1);
            }while (field.canPlaceAnimal(new Vector2d(x, y)));
            Animal animal = new Animal(new Vector2d(x, y));
            field.addingAnimals(animal);
            animals.put(animal.getPositionOnTheMap(), animal);
        }
    }


}
