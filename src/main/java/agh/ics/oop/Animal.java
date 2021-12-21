package agh.ics.oop;

// zwierze moze wchodzic tam gdzie jest trawa i inne zwierzeta
// wtedy zachodzą odpowiednie operacje

import java.util.ArrayList;
import java.util.Random;

public class Animal extends InputParameters {
    private Vector2d positionOnTheMap;
    private MapDirections orientation;
    private int timeLeftToDeath;
    private DNA genotype;
    private int numberOfKids;
    private int numberOfDescendants = 0;
    private int dayOfDeath = 0;
    Random random = new Random();

    // todo dodac jeszcze zwiaezraki po smierci i narodzniach ale to jak bedzie mapa,
    // todo dodac ruch do przodu jak ogarne mape
    // todo dodac sobie to ze adam i ewa nie moga pojawic sie na tym samym polu


    // constructors
    public Animal(Vector2d positionOnTheMap) {       // tworzenie adama i ewy
        this.positionOnTheMap = positionOnTheMap;
        this.orientation = MapDirections.getRandom();
        this.timeLeftToDeath = getInitialEnergy();
        this.genotype = new DNA();
    }

    public Animal(Animal firstParent, Animal secondParent){     // tworzenie dziecka
        this.positionOnTheMap = new Vector2d(firstParent.positionOnTheMap.x, firstParent.positionOnTheMap.y);
        this.orientation = MapDirections.getRandom();
        this.timeLeftToDeath = firstParent.timeLeftToDeath/4 + secondParent.timeLeftToDeath/4;
        this.genotype = new DNA(firstParent, secondParent);
    }


    // getters
    public MapDirections getOrientation(){
        return this.orientation;
    }


    public Vector2d getPositionOnTheMap(){
        return this.positionOnTheMap;
    }


    public int getTimeLeftToDeath(){
        return this.timeLeftToDeath;
    }


    public DNA getGenotype(){
        return this.genotype;
    }


    // living
    public void move(int[] genes){
        for (int gene : genes){
            switch (gene) {
                case 0:
                    Vector2d newPosition = this.positionOnTheMap.add(this.orientation.toUnitVector());
                    Vector2d oldPosition = this.positionOnTheMap;
                    positionChange(oldPosition, newPosition);
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
                    Vector2d newPositionB = this.positionOnTheMap.add(this.orientation.toUnitVector());
                    Vector2d oldPositionB = this.positionOnTheMap;
                    postionChange(oldPositionB, newPositionB);
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

    public void isDying(int day){       // todo dodac martwe zwierzaki
        if (this.timeLeftToDeath <= 0){
            this.death(day);
        }
    }

    public void madeNewBaby(){
        this.numberOfKids += 1;
        this.numberOfDescendants += 1;
    }


    public void death(int day){
        this.dayOfDeath = day;
    }

    public Animal makingNewBaby(Animal secondParent){
        Animal child = new Animal(this, secondParent);
        this.timeLeftToDeath = (int) (0.75 * this.timeLeftToDeath);
        secondParent.timeLeftToDeath = (int) (0.75 * secondParent.timeLeftToDeath);
        this.madeNewBaby(); secondParent.madeNewBaby();
        return child;
    }


    // to statistics
    public int getNumberOfKids(){
        return this.numberOfKids;
    }

    public int getNumberOfDescendants(){
        return this.numberOfDescendants;
    }

    public int getDayOfDeath(){
        return this.dayOfDeath;
    }
}
