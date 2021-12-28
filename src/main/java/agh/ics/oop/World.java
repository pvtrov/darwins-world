package agh.ics.oop;

import agh.ics.oop.gui.SaveFile;
import java.io.IOException;
import java.util.*;

public class World implements IPositionChangeObserver {
    public InputParameters inputParameters;
    private boolean isLeft;
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
    public int sumOfNumberOfKids = 0;
    public int magic = 3;
    public int day = 0;
    public SaveFile saveFile;
    public int allAnimals = 0;
    public int allPlants = 0;
    public int allEnergy = 0;
    public int allKids = 0;
    public int allLifeTime = 0;


    // constructor
    public World(InputParameters inputParameters, Boolean isLeft) throws IOException {
        this.isLeft = isLeft;
        this.inputParameters = inputParameters;
        this.map = new WorldMap(inputParameters.getWidthWorld(), inputParameters.getHeightWorld(), inputParameters.getJungleWidth(), inputParameters.getJungleHeight());
        makingFieldsArrays();
        this.placingPlantsAtTheBegin();
        this.placingAdamAndEva();
        saveFile = new SaveFile(inputParameters, isLeft);
    }

    // methods for creating the world
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
        int numberOfPlantsToJungle = inputParameters.initialNumberOfPlants/2;
        int numberOfPlantsToSavanna = inputParameters.initialNumberOfPlants/2;

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

    // methods operating living in the world
    public void addKidToSum(){
        sumOfNumberOfKids += 1;
    }

    public void magicHappened(){
        this.magic --;
    }

    public void nextDay(){
        this.day += 1;
    }

    // update
    public void updateStatistics() throws IOException {
        allAnimals += getNumberOfLivingAnimals();
        allPlants += getNumberOfPlants();
        allEnergy += getAverageOfEnergy();
        allKids += getAverageOfNumberOfKids();
        allLifeTime += getAverageOfLivingTime();
        saveFile.updateFile(day, getNumberOfLivingAnimals(), getNumberOfPlants(), (int) getAverageOfEnergy(),(int) getAverageOfNumberOfKids(), getNumberOfLivingAnimals(), isLeft);
    }

    public void averageStatistics() throws IOException {
        int endAnimal = allAnimals/day;
        int endPlant = allPlants/day;
        int endEnergy = allEnergy/day;
        int endKids = allKids/day;
        int endLife = allLifeTime/day;
        saveFile.updateFile(day, endAnimal, endPlant, endEnergy, endKids, endLife, isLeft);
    }

    // getters
    public int getNumberOfLivingAnimals(){
        return animals.size();
    }

    public int getNumberOfPlants(){
        return plants.size();
    }

    public int getDominantGenotype(){
        int[] genes = {0, 0, 0, 0, 0, 0, 0, 0};
        for (Animal animal : animals){
            genes[animal.getDominantGen()] += 1;
        }
        IntSummaryStatistics geness = Arrays.stream(genes).summaryStatistics();
        int max = geness.getMax();
        for (int i = 0; i < genes.length; i++){
            if (genes[i] == max){
                return i;
            }
        }
        return 0;
    }

    public double getAverageOfEnergy(){
        int sum = 0;
        if (animals.size() > 0) {
            for (Animal animal : animals) {
                sum += animal.getEnergy();
            }
            double average = (double) sum / animals.size();
            double roundOff = Math.round(average*100.0)/100.0;
            return roundOff;
        }else return 0;
    }

    public int getAverageOfLivingTime(){
        int sum = 0;
        if (deadAnimals.size() > 0){
            for (Animal deadAnimal : deadAnimals){
                sum += Double.parseDouble(deadAnimal.getDayOfDeath());
            }return sum/deadAnimals.size();
        }else return 0;
    }

    public int getNumberOfDeadAnimals(){
        return this.deadAnimals.size();
    }

    public double getAverageOfNumberOfKids(){
        if (animals.size() > 0 || deadAnimals.size() > 0) {
            double average = (double) sumOfNumberOfKids/(animals.size() + deadAnimals.size());
            double roundOff = Math.round(average *100.0)/100.0;
            return roundOff;
        } else return 0;
    }

    public int getMagicNumber(){
        return magic;
    }

    // observers
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
