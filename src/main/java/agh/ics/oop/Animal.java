package agh.ics.oop;

import java.util.*;

public class Animal implements IPositionChangeObserver, IMapElement{
    private final InputParameters inputParameters;
    private Vector2d oldPosition;
    private Animal mother;
    private Animal father;
    private Vector2d positionOnTheMap;
    private MapDirections orientation;
    private int energy;
    public DNA genotype;
    private int numberOfKids;
    private int numberOfDescendants = 0;
    private int dayOfDeath = 0;
    Random random = new Random();
    private ArrayList<IPositionChangeObserver> observers;


    // constructors
    public Animal(Vector2d positionOnTheMap, InputParameters inputParameters) {       // creating Adam and Eve
        this.inputParameters = inputParameters;
        this.father = null;
        this.mother = null;
        this.positionOnTheMap = positionOnTheMap;
        this.orientation = MapDirections.getRandom();
        this.energy = inputParameters.startAnimalEnergy;
        this.genotype = new DNA();
        this.observers = new ArrayList<>();
        this.oldPosition = null;
    }

    public Animal(Animal firstParent, Animal secondParent, InputParameters inputParameters){     // creating new child
        this.mother = firstParent;
        this.father = secondParent;
        this.inputParameters = inputParameters;
        this.positionOnTheMap = new Vector2d(firstParent.positionOnTheMap.x, firstParent.positionOnTheMap.y);
        this.orientation = MapDirections.getRandom();
        this.energy = firstParent.energy /4 + secondParent.energy /4;
        this.genotype = new DNA(firstParent, secondParent);
        this.observers = new ArrayList<>();
        this.oldPosition = null;
    }


    // getters or for statistics
    @Override
    public Vector2d getPositionOnTheMap(){
        return this.positionOnTheMap;
    }

    public int getEnergy(){
        return this.energy;
    }

    public DNA getGenotype(){
        return this.genotype;
    }

    public Vector2d getOldPosition(){
        return this.oldPosition;
    }

    public String getNumberOfKids(){
        return String.valueOf(this.numberOfKids);
    }

    public String getGenotypeToStatistic(){
        String stringGenotype = "";
        for (int i : this.genotype.getIntGenotype()){
            stringGenotype = stringGenotype + Integer.toString(i);
        }
        return stringGenotype;
    }

    public String getNumberOfDescendants(){
        return String.valueOf(this.numberOfDescendants);
    }

    public String getDayOfDeath(){
        return String.valueOf(this.dayOfDeath);
    }

    public int getDominantGen(){
        int[] intGenotype = this.genotype.getIntGenotype();
        int gens[] = {0, 0, 0, 0, 0, 0, 0, 0};
        for (int gen : intGenotype){
            gens[gen] += 1;
        }
        IntSummaryStatistics genss = Arrays.stream(gens).summaryStatistics();
        int max = genss.getMax();
        for (int i = 0; i < gens.length; i++){
            if (max == gens[i]){
                return i;
            }
        }
        return 0;
    }


    // living
    public void move(int[] genes, boolean isLeft){
        int x = random.nextInt(32);
        int gene = genes[x];
        switch (gene) {
            case 0:
                if (isLeft) {
                    Vector2d newPosition = countPositionToTeleport(this.positionOnTheMap.add(this.orientation.toUnitVector()));
                    Vector2d oldPosition = this.positionOnTheMap;
                    this.oldPosition = oldPosition;
                    this.positionOnTheMap = newPosition;
                    positionChange(oldPosition, newPosition);
                    break;
                } else {
                    Vector2d newPosition = this.positionOnTheMap.add(this.orientation.toUnitVector());
                    if (newPosition.precedes(new Vector2d(0, 0)) && newPosition.follows(new Vector2d(inputParameters.getWidthWorld()-1, inputParameters.getHeightWorld()-1))){
                        Vector2d oldPosition = this.positionOnTheMap;
                        this.oldPosition = oldPosition;
                        this.positionOnTheMap = newPosition;
                        positionChange(oldPosition, newPosition);
                        break;
                    }
                }
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
                if (isLeft){
                    Vector2d newPositionB = countPositionToTeleport(this.positionOnTheMap.subtract(this.orientation.toUnitVector()));
                    Vector2d oldPosition = this.positionOnTheMap;
                    this.oldPosition = oldPosition;
                    this.positionOnTheMap = newPositionB;
                    positionChange(oldPosition, newPositionB);
                    break;
                }else{
                    Vector2d newPositionB = this.positionOnTheMap.subtract(this.orientation.toUnitVector());
                    if (newPositionB.precedes(new Vector2d(0, 0)) && newPositionB.follows(new Vector2d(inputParameters.getWidthWorld()-1, inputParameters.getHeightWorld()-1))){
                        Vector2d oldPosition = this.positionOnTheMap;
                        this.oldPosition = oldPosition;
                        this.positionOnTheMap = newPositionB;
                        positionChange(oldPosition, newPositionB);
                        break;
                    }
                }
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

    public Vector2d countPositionToTeleport(Vector2d position){
        if (position.follows(new Vector2d(inputParameters.getWidthWorld()-1, inputParameters.getHeightWorld()-1)) && position.precedes(new Vector2d(0, 0))){
            return position;
        }else{
            int x; int y;
            if (position.x  > inputParameters.getWidthWorld()-1){
                x = 0;
            }else if(position.x < 0) { x = inputParameters.getHeightWorld()-1; }
            else x = position.x;

            if (position.y > inputParameters.getHeightWorld()-1){
                y = 0;
            }else if(position.y < 0){ y = inputParameters.getHeightWorld()-1;}
            else y = position.y;

            return new Vector2d(x, y);
        }
    }

    public void eatingPlant(int kcal){
        this.energy = this.energy + kcal;
    }

    public boolean isDying(){      // animal will die anyway after move if his energy is less than moveAnimalEnergy
        return this.energy <= 0;
    }

    private void madeNewBaby(){
        this.numberOfKids += 1;
        updateNumberGrandKids(this);
    }

    private void updateNumberGrandKids(Animal parent){
        parent.numberOfDescendants ++;
        if (parent.mother != null){
            updateNumberGrandKids(parent.mother);
        }
        if (parent.father != null){
            updateNumberGrandKids(parent.father);
        }
    }

    public void death(int day){
        this.dayOfDeath = day;
    }

    public Animal makingNewBaby(Animal secondParent){
        Animal child = new Animal(this, secondParent, inputParameters);
        this.energy = (int) (0.75 * this.energy);
        secondParent.energy = (int) (0.75 * secondParent.energy);
        this.madeNewBaby(); secondParent.madeNewBaby();
        return child;
    }

    public void loseEnergy(){
        this.energy = this.energy - inputParameters.moveAnimalEnergy;
    }


    // for observers
    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }

    private void positionChange(Vector2d oldPosition, Vector2d newPosition){
        observers.forEach(observer -> observer.positionChanged(oldPosition, newPosition));
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }

}
