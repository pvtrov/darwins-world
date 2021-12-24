package agh.ics.oop;

// trawa nie moze sie pojawic tam gdzie jest juz zwierze i inna trawa

public class Plant extends InputParameters implements IMapElement{
    public boolean isInJungle;
    public Field field;
    public Vector2d position;
    public int kcal;

    public Plant(Vector2d position, boolean isInJungle){     // sadzenie nowej trawy
        this.position = position;
        this.kcal = getPlantKcal();
        this.isInJungle = isInJungle;
    }
    public Vector2d getPosition(){
        return this.position;
    }

    public String toString(){return "*";}

    @Override
    public String getPath() {
        return "src/main/resources/plant.jpg";
    }

    @Override
    public Vector2d getPositionOnTheMap() {
        return null;
    }
}
