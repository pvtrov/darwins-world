package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapDirectionsTest {

    @Test
    public void mapDirectionTest(){
        Assertions.assertEquals(MapDirections.EAST, MapDirections.NORTHEAST.next());
        Assertions.assertEquals(MapDirections.NORTHEAST, MapDirections.NORTH.next());
        Assertions.assertEquals(MapDirections.SOUTH, MapDirections.SOUTHEAST.next());
        Assertions.assertEquals(MapDirections.SOUTHWEST, MapDirections.SOUTH.next());
    }
}
