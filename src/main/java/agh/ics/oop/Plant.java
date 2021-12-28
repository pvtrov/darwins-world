package agh.ics.oop;


public class Plant extends InputParameters implements IMapElement{
    public boolean isInJungle;
    public Vector2d position;
    public int kcal;


    // constructor
    public Plant(Vector2d position, boolean isInJungle){
        this.position = position;
        this.kcal = getPlantKcal();
        this.isInJungle = isInJungle;
    }

    // getter
    public Vector2d getPosition(){
        return this.position;
    }

    @Override
    public Vector2d getPositionOnTheMap() {
        return null;
    }

}
