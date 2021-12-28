package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ToUnitVectorTest {


    @Test
    public void toUnitVectorTest(){
        assertEquals(MapDirections.SOUTHEAST.toUnitVector(), new Vector2d(1, -1));
        assertEquals(MapDirections.NORTH.toUnitVector(), new Vector2d(0, 1));
    }
}
