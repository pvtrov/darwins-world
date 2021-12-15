package agh.ics.oop;

import java.util.Random;

public class Plant {
    public Vector2d position;
    public int kcal;

    public Plant(){     // sazdenie nowej trawy
        Random random = new Random();
        int x; int y;
        x = random.nextInt();
        y = random.nextInt();
        this.position = new Vector2d(x, y);
        this.kcal = kcal;
    }

    public Vector2d getPosition(){
        return this.position;
    }
}
