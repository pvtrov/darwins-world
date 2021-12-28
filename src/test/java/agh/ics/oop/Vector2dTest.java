package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Vector2dTest {
    Vector2d v1 = new Vector2d(-2, 4);
    Vector2d v2 = new Vector2d(2, 1);
    Vector2d v3 = new Vector2d(4, -2);
    Vector2d v4 = new Vector2d(8, 4);

    @Test
    public void precedesTest(){
        assertFalse(v1.precedes(v2));
        assertFalse(v3.precedes(v1));
        assertTrue(v4.precedes(v2));
    }

    @Test
    public void followsTest(){
        assertTrue(v3.follows(v4));
        assertTrue(v2.follows(v4));
        assertFalse(v2.follows(v1));
    }

    @Test
    public void addTest(){
        assertEquals(v3.add(v2), new Vector2d(6, -1));
        assertEquals(v2.add(v4), new Vector2d(10, 5));
        assertEquals(v1.add(v2), new Vector2d(0, 5));
    }

    @Test
    public void subtractTest(){
        assertEquals(v3.subtract(v4), new Vector2d(-4, -6));
        assertEquals(v2.subtract(v1), new Vector2d(4, -3));
        assertEquals(v4.subtract(v3), new Vector2d(4, 6));
    }



}
