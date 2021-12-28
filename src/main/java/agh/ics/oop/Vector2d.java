package agh.ics.oop;

import java.util.Objects;

public class Vector2d {
    public int x;
    public int y;


    // constructor
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean precedes(Vector2d other){
        return (other.x <= this.x && other.y <= this.y);
    }

    boolean follows(Vector2d other){
        return (other.x >= this.x && other.y >= this.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object Vector2d){
        Vector2d other = (Vector2d) Vector2d;
        return (other.x == this.x && other.y == this.y);
    }

}
