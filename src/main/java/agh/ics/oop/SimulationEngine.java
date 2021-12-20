package agh.ics.oop;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;

public class SimulationEngine implements IWorldMap{
    public IWorldMap map;
    public Vector2d position;
    public HashMap<Vector2d, Plant> plantsOnTheMapField;
    public LinkedHashMap<Vector2d, PriorityQueue<Animal>> animalsOnTheMapField;






    @Override
    public boolean canPlacePlant(Vector2d position) {
        return false;
    }

    @Override
    public boolean canPlaceAnimal(Vector2d position) {
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }
}
