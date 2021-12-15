package agh.ics.oop;

import java.util.ArrayList;
import java.util.Random;

public class Animal {
    private Vector2d positionOnTheMap;
    private MapDirections orientation;
    private int timeLeftToDeath;
//    private final DNA genotype;
    private ArrayList<Animal> kids;
    private ArrayList<Animal> descendants;
    private int dayOfDeath;

    // dodac jeszcze zwiaezraki po smierci i narodzniach ale to jak bedzie mapa,
    // dodac ruch do przodu jak ogarne mape
    // rozmnazanie dodac


    public Animal(int timeLeftToDeath) {       // tworzenie adama i ewy
        Random random = new Random();
        int x; int y;
        x = random.nextInt();
        y = random.nextInt();
        this.positionOnTheMap = new Vector2d(x, y);
        this.orientation = MapDirections.EAST;
        this.timeLeftToDeath = timeLeftToDeath;
//        this.genotype = new DNA();
        this.kids = new ArrayList<Animal>();
        this.descendants = new ArrayList<Animal>();
    }

    public Animal(Animal firstParent, Animal secondParent){     // tworzenie dziecka
        this.positionOnTheMap = new Vector2d(firstParent.positionOnTheMap.x, firstParent.positionOnTheMap.y);
        this.orientation = MapDirections.NORTH;
        this.timeLeftToDeath = timeLeftToDeath;
//        this.genotype = new DNA(firstParent.genotype, secondParent.genotype);
    }

    public int getDayOfDeath(){
        return this.dayOfDeath;
    }

    public MapDirections getOrientation(){
        return this.orientation;
    }

    public Vector2d getPositionOnTheMap(){
        return this.positionOnTheMap;
    }

    public int getTimeLeftToDeath(){
        return this.timeLeftToDeath;
    }

//    public DNA getGenotype(){
//        return this.genotype;
//    }

    public void move(int[] genes){
        for (int gene : genes){
            switch (gene) {
                case 0:
                    Vector2d newPosition = this.positionOnTheMap.add(this.orientation.toUnitVector());
                case 1:
                    this.orientation = this.orientation.next();
                    break;
                case 2:
                    this.orientation = this.orientation.next().next();
                    break;
                case 3:
                    this.orientation = this.orientation.next().next().next();
                    break;
                case 4:
                    Vector2d newPositionB = this.positionOnTheMap.subtract(this.orientation.toUnitVector());
                case 5:
                    this.orientation = this.orientation.next().next().next().next().next();
                    break;
                case 6:
                    this.orientation = this.orientation.next().next().next().next().next().next();
                    break;
                case 7:
                    this.orientation = this.orientation.next().next().next().next().next().next().next();
            }
        }
    }

    public void eatingPlant(Plant plant){
        this.timeLeftToDeath = this.timeLeftToDeath + plant.kcal;
    }

    public void isDying(int day){       // dodac martwe zwierzaki
        if (this.timeLeftToDeath <= 0){
            this.death(day);

        }
    }

    public void death(int day){
        this.dayOfDeath = day;
    }

}
