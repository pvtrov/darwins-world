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

import java.util.LinkedHashMap;
import java.util.Random;

public class CreatingWorld extends InputParameters implements Runnable{     // this class is my engine
    Random random = new Random();
    private WorldMap map;
    private World darwinWorld;
    private App application;
    private int moveDaily;
    private LinkedHashMap<Vector2d, Animal> animals;


    public CreatingWorld(WorldMap map, int moveDaily){
        this.darwinWorld = new World();
        this.map = darwinWorld.map;
        this.moveDaily = moveDaily;
        this.animals = darwinWorld.animals;
    }

    public void day(){
        try {
            Thread.sleep(moveDaily);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkingForDeadAnimals();
        movingAnimals();
        checkingWhatOnFieldsIs();
        growingPlants();
    }

    public void growingPlants(){
        int counterJungle = 1; int counterSavanna = 1;
        int x; int y;
        while (counterJungle > 0){
            x = random.nextInt(darwinWorld.fieldsForPlantsJungle.size());
            Vector2d vector = darwinWorld.fieldsForPlantsJungle.get(x);
            Field field = darwinWorld.fields.get(vector);
            if (field.canPlacePlant(vector)){
                Plant plant = new Plant(vector, true);
                counterJungle -- ;
                field.addingPlants();
                darwinWorld.plants.put(vector, plant);
                darwinWorld.mapElements.add(plant);
                darwinWorld.fieldsForPlantsJungle.remove(vector);
            }
        }
        while (counterSavanna > 0){
            y = random.nextInt(darwinWorld.fieldsForPlantsSavanna.size());
            Vector2d vector = darwinWorld.fieldsForPlantsSavanna.get(y);
            Field field = darwinWorld.fields.get(vector);
            if (field.canPlacePlant(vector)){
                Plant plant = new Plant(vector, false);
                counterSavanna--;
                field.addingPlants();
                darwinWorld.plants.put(vector, plant);
                darwinWorld.mapElements.add(plant);
                darwinWorld.fieldsForPlantsSavanna.remove(vector);
            }
        }
    }

    public void checkingWhatOnFieldsIs(){
        for (Field field : darwinWorld.fields.values()) {
            isSomethingThere(field);
        }
    }

    public void checkingForDeadAnimals(){
        for (Animal animal : animals.values()){
            if(animal.isDying()){
                removingDeadAnimal(animal);
            }
        }
    }

    public void removingDeadAnimal(Animal animal){
        int fieldAddress = darwinWorld.map.getFieldAddress(animal.getPositionOnTheMap());
        animal.removeObserver(darwinWorld);
        Field field = darwinWorld.fields.get(fieldAddress);
        darwinWorld.animals.remove(animal.getPositionOnTheMap(), animal);
        field.removingAnimals(animal);
        darwinWorld.mapElements.remove(animal);
        darwinWorld.deadAnimals.add(animal);
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
        } else if (field.animals.size() > 1) {                     // there are animals but no plant
            Animal mother = field.animals.poll();
            Animal father = field.animals.poll();
            if (mother.getEnergy() >= startEnergy / 2 && father.getEnergy() >= startEnergy / 2) {
                copulation(mother, father);
            }
        }
        }
    }

    public void copulation(Animal mother, Animal father){
        int fieldAddress = darwinWorld.map.getFieldAddress(mother.getPositionOnTheMap());
        Field field = darwinWorld.fields.get(fieldAddress);

        Animal child = mother.makingNewBaby(father);
        child.addObserver(darwinWorld);
        darwinWorld.animals.put(child.getPositionOnTheMap(), child);
        darwinWorld.mapElements.add(child);
        field.addingAnimals(child);
        field.addingAnimals(mother);
        field.addingAnimals(father);
    }


    public void eatPlant(int plantKcal, Field field, Vector2d position){
        Plant plant = darwinWorld.plants.get(position);
        Animal[] animalsAsAList = (Animal[]) field.animals.toArray();
        int counter = 1;
        int index = 0;
        while (animalsAsAList[index].getEnergy() == animalsAsAList[index+1].getEnergy()){
            counter += 1;
            index += 1;
        }
        int kcalForOneAnimal = plantKcal/counter;
        int indexSnd = 0;
        while (counter > 0) {
            animalsAsAList[indexSnd].eatingPlant(kcalForOneAnimal);
            counter --;
        }
        removingEatenPlant(plant, field);
    }


    public void movingAnimals(){
        for (Animal animal : animals.values()){
            animal.move(animal.getGenotype().getIntGenotype());
            animal.loseEnergy();
        }
    }


    public void run() {
        while (true){
            day();
        }
    }
}
