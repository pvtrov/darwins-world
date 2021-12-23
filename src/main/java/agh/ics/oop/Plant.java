package agh.ics.oop;

// trawa nie moze sie pojawic tam gdzie jest juz zwierze i inna trawa

public class Plant extends InputParameters {
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

}
