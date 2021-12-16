package agh.ics.oop;


import java.util.Arrays;

public class World {

    public static void main(String[] args) {
        int[] arg = new int[]{1, 4, 2, 7};
        Animal animal = new Animal();
        Animal animal1 = new Animal();
        System.out.println(animal.getPositionOnTheMap());
        System.out.println(animal1.getPositionOnTheMap());
        System.out.println(Arrays.toString(animal1.getGenotype().getIntGenotype()));
        System.out.println(Arrays.toString(animal.getGenotype().getIntGenotype()));
        Animal babyAnimal = new Animal(animal, animal1);
        System.out.println(babyAnimal.getTimeLeftToDeath());
        System.out.println(Arrays.toString(babyAnimal.getGenotype().getIntGenotype()));
    }
}
