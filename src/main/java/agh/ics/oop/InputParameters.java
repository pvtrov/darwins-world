package agh.ics.oop;


public class InputParameters {
    public int heightWorld;
    public int widthWorld;
    public int startAnimalEnergy;
    public int initialNumberOfAnimals;
    public int initialNumberOfPlants;
    public int moveAnimalEnergy ;
    public int plantKcal;
    public int jungleRatioo;
    public int jungleHeight;
    public int jungleWidth;
    public boolean magicLeft;
    public boolean magicRight;
    int allFields; int jungleFields;
    public int moveDelay;



    // constructors
    public InputParameters(int widthWorld, int heightWorld, int initialNumberOfAnimals, int startAnimalEnergy, int moveAnimalEnergy, int plantKcal, int jungleRatioo, boolean magicLeft, boolean magicRight, int moveDelay){
        this.heightWorld = heightWorld;
        this.widthWorld = widthWorld;
        this.startAnimalEnergy = startAnimalEnergy;
        this.moveAnimalEnergy = moveAnimalEnergy;
        this.plantKcal = plantKcal;
        this.jungleRatioo = jungleRatioo;
        this.jungleHeight = jungleCounting();
        this.jungleWidth = getJungleWidth();
        this.initialNumberOfAnimals = initialNumberOfAnimals;
        this.initialNumberOfPlants = 0;
        this.magicLeft = magicLeft;
        this.magicRight = magicRight;
        this.moveDelay = moveDelay;
    }

    public InputParameters() {
    }

    // getters
    public int getHeightWorld(){
        return this.heightWorld;
    }

    public int getWidthWorld(){
        return this.widthWorld;
    }

    public int getInitialNumberOfAnimals(){
        return initialNumberOfAnimals;
    }

    public int getPlantKcal(){
        return this.plantKcal;
    }

    public int getJungleHeight(){
        return jungleCounting();
    }

    public int getJungleWidth(){
        jungleHeight = jungleCounting();
        return (jungleRatioo *allFields/100) / jungleHeight;
    }

    // counting jungle parameters
    public int jungleCounting(){
        allFields = this.heightWorld * this.widthWorld;
        jungleFields = this.jungleRatioo *allFields/100;
        jungleHeight = (int) Math.sqrt(jungleFields* this.heightWorld / this.widthWorld);
        return jungleHeight;
    }

}

