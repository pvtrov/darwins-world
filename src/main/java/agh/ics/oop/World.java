package agh.ics.oop;


import java.util.*;

public class World implements IPositionChangeObserver {
    public InputParameters inputParameters;
    Random random = new Random();
    public ArrayList<IMapElement> mapElements = new ArrayList<>();
    public ArrayList<Animal> animals = new ArrayList<>();
    public HashMap<Vector2d, Plant> plants = new HashMap<>();
    public WorldMap map;
    public ArrayList<Animal> deadAnimals = new ArrayList<>();
    public HashMap<Vector2d, Field> fields;
    public ArrayList<Vector2d> fieldsForPlantsJungle = new ArrayList<>();
    public ArrayList<Vector2d> fieldsForPlantsSavanna = new ArrayList<>();
    public ArrayList<Vector2d> fieldsForAnimals = new ArrayList<>();


    public World(InputParameters inputParameters) {
        this.inputParameters = inputParameters;
        this.map = new WorldMap(inputParameters.getWidthWorld(), inputParameters.getHeightWorld(), inputParameters.getJungleWidth(), inputParameters.getJungleHeight());
        makingFieldsArrays();
        this.placingPlantsAtTheBegin();
        this.placingAdamAndEva();
    }

    public World() {
    }

    public ArrayList<IMapElement> getMapElements(){
        return mapElements;
    }


    public void makingFieldsArrays(){
        fields = new HashMap<>( inputParameters.heightWorld * inputParameters.widthWorld);
        Vector2d jungleLowerLeft = map.jungleCountingLowerLeft();
        Vector2d jungleUpperRight = map.jungleCountingUpperRight();
        for (int i = 0; i < inputParameters.getWidthWorld(); i++) {
            for (int j = 0; j < inputParameters.getHeightWorld(); j++) {
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
        int numberOfPlantsToJungle = inputParameters.getInitialNumberOfPlants()/2;
        int numberOfPlantsToSavanna = inputParameters.getInitialNumberOfPlants()/2;

        while (numberOfPlantsToJungle > 0){
            int x = random.nextInt(fieldsForPlantsJungle.size());
            Vector2d fieldAddress = fieldsForPlantsJungle.get(x);
            Field field = fields.get(fieldAddress);
            if (field.canPlacePlant(fieldAddress)){
                Plant plant = new Plant(fieldAddress, true);
                field.addingPlants();
                plants.put(plant.getPosition(), plant);
                fieldsForPlantsJungle.remove(fieldAddress);
                mapElements.add(plant);
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
                mapElements.add(plant);
                numberOfPlantsToSavanna--;
            }
        }
    }

    public void placingAdamAndEva(){
        int numberOfAnimal = inputParameters.getInitialNumberOfAnimals();
        while (numberOfAnimal > 0){
            int x = random.nextInt(fields.size());
            Vector2d fieldAddress = fieldsForAnimals.get(x);
            Field field = fields.get(fieldAddress);
            if (field.canPlaceAnimal(fieldAddress)){
                Animal animal = new Animal(fieldAddress, inputParameters);
                animal.addObserver(this);
                field.addingAnimals(animal);
                animals.add(animal);
                mapElements.add(animal);
                numberOfAnimal --;
            }
        }
    }


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal movedAnimal = null;
        for(Animal animal : animals){
            if (animal.getOldPosition() == oldPosition && animal.getPositionOnTheMap() == newPosition){
                movedAnimal = animal;
                break;
            }
        }
        Field fieldToRemove = fields.get(oldPosition);
        fieldToRemove.animals.remove(movedAnimal);
        Field fieldToAdd = fields.get(newPosition);
        fieldToAdd.animals.add(movedAnimal);
    }
}
