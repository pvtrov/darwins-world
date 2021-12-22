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
    // todo zrobic usuwanie zwierzaokw
    
    public void removingDeadAnimal(Animal animal, Field field){
        darwinWorld.animals.remove(animal.getPositionOnTheMap(), animal);
        field.removingAnimals(animal);
    }

    public void removingEatenPlant(Plant plant, Field field){
        darwinWorld.plants.remove(plant.getPosition());
        field.removingPlants();
    }


    public void isSomethingThere(Vector2d position) {    // getting position and checking if and what is there
        int fieldAddress = darwinWorld.map.getFieldAddress(position);
        Field field = fields.get(fieldAddress);

        if (field.isPlantGrown && !field.animals.isEmpty()) {    // there are animals and plant
            eatPlant(plantKcal, field, position);
            if (field.animals.size() > 1) {
                Animal mother = field.animals.poll();
                Animal father = field.animals.poll();
                if (mother.getTimeLeftToDeath() >= initialEnergy / 2 && father.getTimeLeftToDeath() >= initialEnergy / 2) {
                    copulation(mother, father);
                }
        } else if (field.animals.size() > 1) {                     // there are animals and no plant
            Animal mother = field.animals.poll();
            Animal father = field.animals.poll();
            if (mother.getTimeLeftToDeath() >= initialEnergy / 2 && father.getTimeLeftToDeath() >= initialEnergy / 2) {
                copulation(mother, father);
            }
        }
        }
    }

    public void copulation(Animal mother, Animal father){
        int fieldAddress = darwinWorld.map.getFieldAddress(mother.getPositionOnTheMap());
        Field field = fields.get(fieldAddress);

        Animal child = mother.makingNewBaby(father);
        darwinWorld.animals.put(child.getPositionOnTheMap(), child);
        field.addingAnimals(child);
        field.addingAnimals(mother);
        field.addingAnimals(father);
    }

    public void eatPlant(int plantKcal, Field field, Vector2d position){
        Plant plant = darwinWorld.plants.get(position);
        Animal[] animalsAsAList = (Animal[]) field.animals.toArray();
        int counter = 1;
        int index = 0;
        while (animalsAsAList[index].getTimeLeftToDeath() == animalsAsAList[index+1].getTimeLeftToDeath()){
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
}
