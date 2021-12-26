package agh.ics.oop;

// 1. stworzenie swiata
// 2. start symulacji
// 3. usuwanie martwych zwierzat
// 4. ruch zwierzat
// 5. jedzenie
// 6. rozmanzanie  -> 5 i 6 zawiera sie w funkcji isSomethingThere
// 7. dodanie nowych roslin

// oprozc tego
// todo zrobic dwie mapy
// todo zrobic caly frontend
// todo dodac statystyki
// todo zrobic to z ta mapa jebana zeby sie nie ruszaly albo teleportowaly
// todo zrobic kolorki ale to w sumie na koniec

import agh.ics.oop.gui.App;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

public class CreatingWorld extends InputParameters implements Runnable{     // this class is my engine
    Random random = new Random();
    private WorldMap map;
    private World darwinWorld;
    private App application;
    private int moveDaily;
    private ArrayList<Animal> animals;
    private int day = 1;


    public CreatingWorld(WorldMap map, World world, int moveDaily){
        this.darwinWorld = world;
        this.map = darwinWorld.map;
        this.moveDaily = moveDaily;
        this.animals = darwinWorld.animals;
    }

    public int countDay(int day){
        return day += 1;
    }

    public void day(){
        try {
            Thread.sleep(moveDaily);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkingForDeadAnimals(day);
        movingAnimals();
        checkingWhatOnFieldsIs();
        growingPlants();
        day = countDay(day);
        System.out.println(day);
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
                System.out.println("just plant has grown up in the jungle ");
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
                System.out.println("just plant has grown up in the savanna ");
            }
        }
    }

    public void checkingWhatOnFieldsIs(){
        for (Field field : darwinWorld.fields.values()) {
            isSomethingThere(field);
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
        animal.removeObserver(darwinWorld);
        Field field = darwinWorld.fields.get(animal.getPositionOnTheMap());
        field.removingAnimals(animal);
        darwinWorld.mapElements.remove(animal);
        darwinWorld.deadAnimals.add(animal);
        System.out.println("dead animal removed");
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
            eatPlant(plantKcal, field, field.fieldAddress);
            if (field.animals.size() > 1) {
                Animal mother = field.animals.poll();
                Animal father = field.animals.poll();
                if (mother.getEnergy() >= startEnergy / 2 && father.getEnergy() >= startEnergy / 2) {
                    copulation(mother, father);
                }
            }
        } else if (field.animals.size() > 1) {                     // there are animals but no plant
            Animal mother = field.animals.poll();
            Animal father = field.animals.poll();
            if (mother.getEnergy() >= startEnergy / 2 && father.getEnergy() >= startEnergy / 2) {
                copulation(mother, father);
            }
        }
    }
    // todo pq chujowo sortuje, naprawic to
    public void copulation(Animal mother, Animal father){
        Field field = darwinWorld.fields.get(mother.getPositionOnTheMap());

        Animal child = mother.makingNewBaby(father);
        child.addObserver(darwinWorld);
        darwinWorld.animals.add(child);
        darwinWorld.mapElements.add(child);
        field.addingAnimals(father);
        field.addingAnimals(child);
        field.addingAnimals(mother);
        System.out.println("just new baby was made!");
    }


    public void eatPlant(int plantKcal, Field field, Vector2d position){
        Plant plant = darwinWorld.plants.get(position);
        ArrayList<Animal> eatingAnimals = makeArray(field);
        int numberOfAnimals = eatingAnimals.size();
        int kcalForAnimal = plantKcal/numberOfAnimals;
        for (Animal animal : eatingAnimals){
            animal.eatingPlant(kcalForAnimal);
        }
        removingEatenPlant(plant, field);
        System.out.println("plant was eaten");
    }


    public void movingAnimals(){
        for (Animal animal : animals){
            animal.move(animal.getGenotype().getIntGenotype());
            animal.loseEnergy();
        }
    }


    public void run() {
        while (true){
            day();
        }
    }

    private ArrayList<Animal> makeArray(Field field){
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
}
