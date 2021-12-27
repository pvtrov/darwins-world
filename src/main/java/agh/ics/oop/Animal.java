package agh.ics.oop;

// zwierze moze wchodzic tam gdzie jest trawa i inne zwierzeta
// wtedy zachodzą odpowiednie operacje

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Animal implements IPositionChangeObserver, IMapElement{
    private InputParameters inputParameters;
    private Vector2d oldPosition;
    private Vector2d positionOnTheMap;
    private MapDirections orientation;
    private int energy;
    private DNA genotype;
    private int numberOfKids;
    private int numberOfDescendants = 0;
    private int dayOfDeath = 0;
    Random random = new Random();
    private ArrayList<IPositionChangeObserver> observers;


    // constructors
    public Animal(Vector2d positionOnTheMap, InputParameters inputParameters) {       // creating Adam and Eve
        this.inputParameters = inputParameters;
        this.positionOnTheMap = positionOnTheMap;
        this.orientation = MapDirections.getRandom();
        this.energy = inputParameters.startAnimalEnergy;
        this.genotype = new DNA();
        this.observers = new ArrayList<>();
        this.oldPosition = null;
    }

    public Animal(Animal firstParent, Animal secondParent, InputParameters inputParameters){     // creating new child
        this.inputParameters = inputParameters;
        this.positionOnTheMap = new Vector2d(firstParent.positionOnTheMap.x, firstParent.positionOnTheMap.y);
        this.orientation = MapDirections.getRandom();
        this.energy = firstParent.energy /4 + secondParent.energy /4;
        this.genotype = new DNA(firstParent, secondParent);
        this.observers = new ArrayList<>();
        this.oldPosition = null;
    }


    // getters
    public MapDirections getOrientation(){
        return this.orientation;
    }

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


    // living
    public void move(int[] genes, boolean isLeft){
        int x = random.nextInt(32);
        int gene = genes[x];
        switch (gene) {
            case 0:
                if (isLeft){
//                    Vector2d newPosition =
                }else{
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

                }else{
                    Vector2d newPositionB = this.positionOnTheMap.add(this.orientation.toUnitVector());
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



    public void eatingPlant(int kcal){
        this.energy = this.energy + kcal;
    }

    public boolean isDying(){      // animal will die anyway after move if his energy is less than moveAnimalEnergy
        return this.energy < inputParameters.moveAnimalEnergy;
    }

    private void madeNewBaby(){
        this.numberOfKids += 1;
        this.numberOfDescendants += 1;
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

    // to statistics
    public String getNumberOfKids(){
        return String.valueOf(this.numberOfKids);
    }

    public String getGenotypeToStatistic(){
        return Arrays.toString(this.genotype.getIntGenotype());
    }

    public String getNumberOfDescendants(){
        return String.valueOf(this.numberOfDescendants);
    }

    public String getDayOfDeath(){
        return String.valueOf(this.dayOfDeath);
    }


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }

    @Override
    public String getPath() {
        int initialEnergy = inputParameters.startAnimalEnergy;
        int zero = 0;
        int twenty = initialEnergy / 5;
        int forty = 2 * initialEnergy / 5;
        int sixty = 3 * initialEnergy / 5;
        int eighty = 4 * initialEnergy / 5;

        Animal animal = (Animal) this;
        int energyLevel = animal.getEnergy();
        try {
            if (zero <= energyLevel && energyLevel <= twenty) {
                return "src/main/resources/panda_0-20.png";
            } else if (twenty < energyLevel && energyLevel <= forty) {
                return "src/main/resources/panda_21-40.png";
            } else if (forty < energyLevel && energyLevel <= sixty) {
                return "src/main/resources/panda_41-60.png";
            } else if (sixty < energyLevel && energyLevel <= eighty) {
                return "src/main/resources/panda_61-80.png";
            } else if (eighty < energyLevel) {
                return "src/main/resources/panda_81-100.png";
            }
        }catch (Exception exception){
            return "there is a problem with animal color";
        }
        return "";
    }
}
