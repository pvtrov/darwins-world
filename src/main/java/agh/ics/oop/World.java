package agh.ics.oop;


import java.util.*;

public class World extends InputParameters implements IPositionChangeObserever{
    Random random = new Random();
    public LinkedHashMap<Vector2d, Animal> animals = new LinkedHashMap<>();
    public HashMap<Vector2d, Plant> plants = new HashMap<>();
    public WorldMap map;
    public HashMap<Vector2d, Field> fields = new HashMap<>(getHeight()*getWidth());
    public ArrayList<Animal> deadAnimals = new ArrayList<>();
    public ArrayList<Vector2d> fieldsForPlantsJungle = new ArrayList<>();
    public ArrayList<Vector2d> fieldsForPlantsSavanna = new ArrayList<>();
    public ArrayList<Vector2d> fieldsForAnimals = new ArrayList<>();


    public World(){
        this.map = new WorldMap(getWidth(), getHeight(), getJungleWidth(), getJungleHeight());
        makingFieldsArrays();
        this.placingPlantsAtTheBegin();
        this.placingAdamAndEva();
    }


    public void makingFieldsArrays(){
        Vector2d jungleLowerLeft = map.jungleCountingLowerLeft();
        Vector2d jungleUpperRight = map.jungleCountingUpperRight();
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                Vector2d vector = new Vector2d(i, j);
                if (vector.precedes(jungleLowerLeft) && vector.follows(jungleUpperRight)) {
                    fields.put(new Vector2d(i, j), new Field(new Vector2d(i, j), false, true));
                    fieldsForPlantsJungle.add(vector);
                } else {
                    fields.put(new Vector2d(i, j), new Field(new Vector2d(i, j), false, false));
                    fieldsForPlantsSavanna.add(vector);
                }
                fieldsForAnimals.add(vector);
            }
        }
    }

    public void placingPlantsAtTheBegin(){
        int numberOfPlantsToJungle = getInitialNumberOfPlants()/2;
        int numberOfPlantsToSavanna = getInitialNumberOfPlants()/2;

        while (numberOfPlantsToJungle > 0){
            int x = random.nextInt(fieldsForPlantsJungle.size());
            Vector2d fieldAddress = fieldsForPlantsJungle.get(x);
            Field field = fields.get(fieldAddress);
            if (field.canPlacePlant(fieldAddress)){
                Plant plant = new Plant(fieldAddress, true);
                field.addingPlants();
                plants.put(plant.getPosition(), plant);
                fieldsForPlantsJungle.remove(fieldAddress);
                numberOfPlantsToJungle --;
            }
        }
        while (numberOfPlantsToSavanna > 0) {
            int x = random.nextInt(fieldsForPlantsSavanna.size());
            Vector2d fieldAddress = fieldsForPlantsSavanna.get(x);
            Field field = fields.get(fieldAddress);
            if (field.canPlacePlant(fieldAddress)) {
                Plant plant = new Plant(fieldAddress, false);
                field.addingPlants();
                plants.put(plant.getPosition(), plant);
                fieldsForPlantsSavanna.remove(fieldAddress);
                numberOfPlantsToSavanna--;
            }
        }
    }

    public void placingAdamAndEva(){
        int numberOfAnimal = getInitialNumberOfAnimals();
        while (numberOfAnimal > 0){
            int x = random.nextInt(fields.size());
            Vector2d fieldAddress = fieldsForAnimals.get(x);
            Field field = fields.get(fieldAddress);
            if (field.canPlaceAnimal(fieldAddress)){
                Animal animal = new Animal(fieldAddress);
                field.addingAnimals(animal);
                animals.put(fieldAddress, animal);
                fieldsForAnimals.remove(fieldAddress);
                numberOfAnimal --;
            }
        }
    }


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal movedAnimal = animals.get(oldPosition);
        animals.remove(oldPosition, movedAnimal);
        animals.put(newPosition, movedAnimal);
    }
}
