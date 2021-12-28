package agh.ics.oop;

// 1. stworzenie swiata
// 2. start symulacji
// 3. usuwanie martwych zwierzat
// 4. ruch zwierzat
// 5. jedzenie
// 6. rozmanzanie  -> 5 i 6 zawiera sie w funkcji isSomethingThere
// 7. dodanie nowych roslin

// oprozc tego
// todo zrobic caly frontend
// todo dodac statystyki



import agh.ics.oop.gui.App;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// prawa to mur, lewa sie zawija

public class CreatingWorld implements Runnable, IMapObserver{
    public InputParameters inputParameters;     // this class is my engine
    Random random = new Random();
    List<int[]> statisticsArray = new ArrayList<int[]>();
    public final boolean isThisMapMagic;
    private final boolean isLeftMap;
    private WorldMap map;
    public World darwinWorld;
    private App application;
    private int moveDaily;
    private ArrayList<Animal> animals;
    private int day = 1;
    private int magic = 0;
    private ArrayList<IMapObserver> observers;
    public boolean isRunning = true;
    public boolean startApp = true;


    public CreatingWorld (World world, int moveDaily, App application, boolean isLeftMap, boolean isThisMapMagic){
        this.isThisMapMagic = isThisMapMagic;
        this.application = application;
        this.isLeftMap = isLeftMap;
        this.darwinWorld = world;
        this.inputParameters = darwinWorld.inputParameters;
        this.map = darwinWorld.map;
        this.moveDaily = moveDaily;
        this.observers = new ArrayList<>();
        this.animals = darwinWorld.animals;
        addObserver(application);

    }

    public int countDay(int day){
        return day += 1;
    }

    public int numberOfMagic(int magic){
        return magic += 1;
    }

    public void day() throws Exception {
        checkingForDeadAnimals(day);
        if (isThisMapMagic && darwinWorld.animals.size() > 0 && darwinWorld.animals.size() <= 5 && numberOfMagic(magic) < 4){
            makingMagic(darwinWorld.animals.get(0), 5);
            darwinWorld.magicHappened();
            magic = numberOfMagic(magic);
            System.out.println("magic happened");
        }else if(isThisMapMagic && darwinWorld.animals.size() == 0 && numberOfMagic(magic) < 4){
            Animal newAnimal = new Animal(new Vector2d(0, 0), inputParameters);
            newAnimal.addObserver(darwinWorld);
            makingMagic(newAnimal, 4);
            darwinWorld.magicHappened();
            magic = numberOfMagic(magic);
            System.out.println("magic happened");
        }
        movingAnimals();
        checkingWhatOnFieldsIs();
        growingPlants();
        day = countDay(day);
        darwinWorld.nextDay();
//        updateStatistics(darwinWorld);
        darwinWorld.updateStatistics();
        update();
        System.out.println(day);
    }

    public void update(){
        observers.forEach(observer -> {
            try {
                observer.updateHBox(this.darwinWorld, isLeftMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void run() {
        while (startApp){
            try {
                sleep();
                day();
                Thread.sleep(moveDaily);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sleep() throws InterruptedException {
        while (!isRunning){
            Thread.sleep(1000);
        }
    }

    public void growingPlants(){
        int x; int y;
        if (darwinWorld.fieldsForPlantsJungle.size() != 0){
            x = random.nextInt(darwinWorld.fieldsForPlantsJungle.size());
            Vector2d vector = darwinWorld.fieldsForPlantsJungle.get(x);
            Field field = darwinWorld.fields.get(vector);
            if (field.canPlacePlant(vector)){
                Plant plant = new Plant(vector, true);
                field.addingPlants();
                darwinWorld.plants.put(vector, plant);
                darwinWorld.mapElements.add(plant);
                darwinWorld.fieldsForPlantsJungle.remove(vector);
                System.out.println("plant just has grown up in the jungle ");
            }
        }
        if(darwinWorld.fieldsForPlantsSavanna.size() != 0){
            y = random.nextInt(darwinWorld.fieldsForPlantsSavanna.size());
            Vector2d vector = darwinWorld.fieldsForPlantsSavanna.get(y);
            Field field = darwinWorld.fields.get(vector);
            if (field.canPlacePlant(vector)){
                Plant plant = new Plant(vector, false);
                field.addingPlants();
                darwinWorld.plants.put(vector, plant);
                darwinWorld.mapElements.add(plant);
                darwinWorld.fieldsForPlantsSavanna.remove(vector);
                System.out.println("plant just has grown up in the savanna ");
            }
        }
    }

    public void updateStatistics(World world){
        int[] newDayStatistic = {world.day, world.getNumberOfLivingAnimals(), world.getNumberOfPlants(),(int) world.getAverageOfEnergy(),
                (int) world.getAverageOfNumberOfKids(), world.getAverageOfLivingTime()};
        statisticsArray.add(newDayStatistic);
    }

    public void checkingWhatOnFieldsIs(){
        for (Field field : darwinWorld.fields.values()) {
            isSomethingThere(field);
        }
    }

    public void makingMagic(Animal animal, int counter){
        while (counter > 0) {
            int x = random.nextInt(darwinWorld.fieldsForAnimals.size());
            Vector2d newAnimalPosition = darwinWorld.fieldsForAnimals.get(x);
            Field field = darwinWorld.fields.get(newAnimalPosition);
            if (field.canPlacePlant(newAnimalPosition)) {
                Animal newAnimal = new Animal(newAnimalPosition, inputParameters);
                newAnimal.addObserver(darwinWorld);
                newAnimal.genotype = animal.genotype;
                field.addingAnimals(newAnimal);
                darwinWorld.animals.add(newAnimal);
                darwinWorld.mapElements.add(newAnimal);
                counter--;
            }
        }
    }

    public void checkingForDeadAnimals(int day){
        ArrayList<Animal> deadAnimals = new ArrayList<>();
        if (animals.size() > 0) {
            for (Animal animal : animals) {
                if (animal.isDying()) {
                    removingDeadAnimal(animal);
                    animal.death(day);
                    deadAnimals.add(animal);
                }
            }
            for (Animal deadAnimal : deadAnimals) {
                animals.remove(deadAnimal);
            }
        }else {
            System.out.println("there's no living animals");
        }
    }

    public void removingDeadAnimal(Animal animal){
        Field field = darwinWorld.fields.get(animal.getPositionOnTheMap());
        field.removingAnimals(animal);
        if (field.animals.isEmpty()){
            darwinWorld.fieldsForAnimals.add(field.fieldAddress);
        }
        darwinWorld.mapElements.remove(animal);
        darwinWorld.deadAnimals.add(animal);
        System.out.println("dead animal removed");
        animal.removeObserver(darwinWorld);
    }

    public void removingEatenPlant(Plant plant, Field field){
        darwinWorld.plants.remove(plant.getPosition());
        field.removingPlants();
        darwinWorld.mapElements.remove(plant);
        if (plant.isInJungle){
            darwinWorld.fieldsForPlantsJungle.add(plant.getPosition());
        }else{
            darwinWorld.fieldsForPlantsSavanna.add(plant.getPosition());
        }
    }

    public void isSomethingThere(Field field) {         // getting position and checking if and what is there
        if (field.isPlantGrown && !field.animals.isEmpty()) {    // there are animals and plant
            eatPlant(inputParameters.plantKcal, field, field.fieldAddress);
            if (field.animals.size() > 1) {
                Animal mother = field.animals.poll();
                Animal father = field.animals.poll();
                if (mother.getEnergy() >= inputParameters.startAnimalEnergy / 2 && father.getEnergy() >= inputParameters.startAnimalEnergy / 2) {
                    copulation(mother, father);
                }
            }
        } else if (field.animals.size() > 1) {                     // there are animals but no plant
            Animal mother = field.animals.poll();
            Animal father = field.animals.poll();
            if (mother.getEnergy() >= inputParameters.startAnimalEnergy / 2 && father.getEnergy() >= inputParameters.startAnimalEnergy / 2) {
                copulation(mother, father);
            }
        }
    }

    public void copulation(Animal mother, Animal father){
        Field field = darwinWorld.fields.get(mother.getPositionOnTheMap());
        darwinWorld.addKidToSum();
        Animal child = mother.makingNewBaby(father);
        child.addObserver(darwinWorld);
        darwinWorld.animals.add(child);
        darwinWorld.mapElements.add(child);
        field.addingAnimals(father);
        field.addingAnimals(child);
        field.addingAnimals(mother);
        System.out.println("new baby has born!");
    }

    public void eatPlant(int plantKcal, Field field, Vector2d position){
        Plant plant = darwinWorld.plants.get(position);
        ArrayList<Animal> eatingAnimals = calculateAnimalsToEatPlant(field);
        int numberOfAnimals = eatingAnimals.size();
        int kcalForAnimal = plantKcal/numberOfAnimals;
        for (Animal animal : eatingAnimals){
            animal.eatingPlant(kcalForAnimal);
        }
        removingEatenPlant(plant, field);
        System.out.println("plant was eaten");
    }

    public void movingAnimals(){
        if (isLeftMap){
            for (Animal animal : animals){
                animal.move(animal.getGenotype().getIntGenotype(), true);
                animal.loseEnergy();
            }
        }else {
            for (Animal animal : animals) {
                animal.move(animal.getGenotype().getIntGenotype(), false);
                animal.loseEnergy();
            }
        }
    }

    private ArrayList<Animal> calculateAnimalsToEatPlant(Field field){
        ArrayList<Animal> animalsWhichWillEatThePlant = new ArrayList<>();
        Animal firstAnimal = field.animals.poll();
        animalsWhichWillEatThePlant.add(firstAnimal);
        while (!field.animals.isEmpty() && field.animals.peek().getEnergy() == firstAnimal.getEnergy()){
            firstAnimal = field.animals.poll();
            animalsWhichWillEatThePlant.add(firstAnimal);
        }
        field.animals.addAll(animalsWhichWillEatThePlant);
        return animalsWhichWillEatThePlant;
    }

    public void addObserver(IMapObserver observer){
        this.observers.add(observer);
    }


    @Override
    public void updateHBox(World world, Boolean isLeft) throws Exception {

    }
}
