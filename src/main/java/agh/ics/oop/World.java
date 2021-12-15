package agh.ics.oop;


public class World {

    public static void main(String[] args) {
        int[] arg = new int[]{1, 4, 2, 7};
        Animal animal = new Animal(30);
        System.out.println(animal.getOrientation());
        System.out.println(animal.getPositionOnTheMap());
        System.out.println(animal.getTimeLeftToDeath());
        animal.move(arg);
        System.out.println(animal.getOrientation());
        System.out.println(animal.getPositionOnTheMap());
    }
}
