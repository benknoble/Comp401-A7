package a7tests.brooksmt;

import org.junit.Test;
import a7.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by brookstownsend on 11/1/16.
 */

public class A7Test {

    @Test
    public void regionConstructorTest() {
	Coordinate a = new Coordinate(3, 3);
	Coordinate b = new Coordinate(5, 5);
	Region c = new RegionImpl(a, b);
	assertEquals(3, c.getTop());
	assertEquals(5, c.getBottom());
	assertEquals(5, c.getLowerRight().getX());
	assertEquals(5, c.getLowerRight().getY());
	assertEquals(3, c.getUpperLeft().getX());
	assertEquals(3, c.getUpperLeft().getY());

	Coordinate a2 = new Coordinate(6, 4);
	Coordinate b2 = new Coordinate(4, 5);
	Region c2 = new RegionImpl(a2, b2);
	assertEquals(4, c2.getTop());
	assertEquals(5, c2.getBottom());
	assertEquals(6, c2.getLowerRight().getX());
	assertEquals(5, c2.getLowerRight().getY());
	assertEquals(4, c2.getUpperLeft().getX());
	assertEquals(4, c2.getUpperLeft().getY());

	try {
	    Coordinate a3 = new Coordinate(0, -1);
	    Coordinate b3 = new Coordinate(4, 5);
	    new RegionImpl(a3, b3);
	} catch (IllegalArgumentException iae) {
	    System.out.println("Negative coordinate caught");
	} catch (RuntimeException e) {
	    fail("Negative Coordinate was not caught, got runtime exception");
	}

	try {
	    Coordinate a4 = null;
	    Coordinate b4 = new Coordinate(1, 1);
	    new RegionImpl(a4, b4);
	} catch (IllegalArgumentException iae) {
	    System.out.println("Null coordinate in Region caught");
	} catch (NullPointerException ne) {
	    fail("Generic null pointer exception generated for null coordinate");
	}

    }

}
