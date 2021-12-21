package agh.ics.oop;

// trawa nie moze sie pojawic tam gdzie jest juz zwierze i inna trawa

public class Plant extends InputParameters {
    public Field field;
    public Vector2d position;
    public int kcal;

    public Plant(Vector2d position){     // sadzenie nowej trawy
        this.position = position;
        this.kcal = getPlantKcal();
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public String toString(){return "*";}

}
