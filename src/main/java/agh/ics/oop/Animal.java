package agh.ics.oop;

// zwierze moze wchodzic tam gdzie jest trawa i inne zwierzeta
// wtedy zachodzą odpowiednie operacje

import java.util.ArrayList;
import java.util.Random;

public class Animal extends InputParameters {
    private Vector2d positionOnTheMap;
    private MapDirections orientation;
    private int energy;
    private DNA genotype;
    private int numberOfKids;
    private int numberOfDescendants = 0;
    private int dayOfDeath = 0;
    Random random = new Random();
    private ArrayList<IPositionChangeObserever> observers;


    // constructors
    public Animal(Vector2d positionOnTheMap) {       // tworzenie adama i ewy
        this.positionOnTheMap = positionOnTheMap;
        this.orientation = MapDirections.getRandom();
        this.energy = getStartEnergy();
        this.genotype = new DNA();
        this.observers = new ArrayList<>();
    }

    public Animal(Animal firstParent, Animal secondParent){     // tworzenie dziecka
        this.positionOnTheMap = new Vector2d(firstParent.positionOnTheMap.x, firstParent.positionOnTheMap.y);
        this.orientation = MapDirections.getRandom();
        this.energy = firstParent.energy /4 + secondParent.energy /4;
        this.genotype = new DNA(firstParent, secondParent);
        this.observers = new ArrayList<>();
    }


    // getters
    public MapDirections getOrientation(){
        return this.orientation;
    }


    public Vector2d getPositionOnTheMap(){
        return this.positionOnTheMap;
    }


    public int getEnergy(){
        return this.energy;
    }


    public DNA getGenotype(){
        return this.genotype;
    }


    // living
    public void move(int[] genes){
        int x = random.nextInt(32);
        int gene = genes[x];
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
                positionChange(oldPositionB, newPositionB);
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


    public void eatingPlant(int kcal){
        this.energy = this.energy + kcal;
    }

    public boolean isDying(){      // animal will die anyway after move if his energy is less than lostenergy
        return this.energy < moveEnergy;
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
        this.energy = (int) (0.75 * this.energy);
        secondParent.energy = (int) (0.75 * secondParent.energy);
        this.madeNewBaby(); secondParent.madeNewBaby();
        return child;
    }

    // for observers
    public void addObserver(IPositionChangeObserever observer){
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserever observer){
        this.observers.remove(observer);
    }

    private void positionChange(Vector2d oldPosition, Vector2d newPosition){
        observers.forEach(observer -> observer.positionChanged(oldPosition, newPosition));
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
