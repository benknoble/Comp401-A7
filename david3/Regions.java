package a7tests.david3;

import static org.junit.Assert.*;

import org.junit.Test;

import a7.*;

public class Regions {

    private static Coordinate c00 = new Coordinate(0, 0);
    private static Coordinate c11 = new Coordinate(1, 1);
    private static Coordinate c12 = new Coordinate(1, 2);
    private static Coordinate c22 = new Coordinate(2, 2);
    private static Coordinate c23 = new Coordinate(2, 3);
    private static Coordinate c33 = new Coordinate(3, 3);
    private static Coordinate c30 = new Coordinate(3, 0);
    private static Coordinate c03 = new Coordinate(0, 3);
    private static Coordinate c21 = new Coordinate(2, 1);
    private static Coordinate c13 = new Coordinate(1, 3);

    @Test
    public void testConstruction() {
	try {
	    new RegionImpl(null, c00);
	    fail("null coordA didn't throw");
	} catch (IllegalArgumentException e) {
	}
	try {
	    new RegionImpl(c00, null);
	    fail("null coordB didn't throw");
	} catch (IllegalArgumentException e) {
	}
	try {
	    new RegionImpl(c00, c33);
	} catch (IllegalArgumentException e) {
	    fail("did throw");
	}
    }

    @Test
    public void testGetUpperLeft() {
	Region[] regions = new Region[] { new RegionImpl(c00, c33), new RegionImpl(c33, c00), new RegionImpl(c11, c22),
		new RegionImpl(c22, c11), new RegionImpl(c12, c23), new RegionImpl(c23, c12), new RegionImpl(c30, c03),
		new RegionImpl(c03, c30), new RegionImpl(c21, c12), new RegionImpl(c12, c21), new RegionImpl(c22, c13),
		new RegionImpl(c13, c22) };
	Coordinate[] expected = new Coordinate[] { c00, c00, c11, c11, c12, c12, c00, c00, c11, c11, c12, c12 };
	assertEquals("impromper test conditions", regions.length, expected.length);
	for (int i = 0; i < regions.length && i < expected.length; i++) {
	    assertEquals("x coordinates not equal", regions[i].getUpperLeft().getX(), expected[i].getX());
	    assertEquals("y coordinates not equal", regions[i].getUpperLeft().getY(), expected[i].getY());
	}
    }

    @Test
    public void testGetLowerRight() {
	Region[] regions = new Region[] { new RegionImpl(c00, c33), new RegionImpl(c33, c00), new RegionImpl(c11, c22),
		new RegionImpl(c22, c11), new RegionImpl(c12, c23), new RegionImpl(c23, c12), new RegionImpl(c30, c03),
		new RegionImpl(c03, c30), new RegionImpl(c21, c12), new RegionImpl(c12, c21), new RegionImpl(c22, c13),
		new RegionImpl(c13, c22) };
	Coordinate[] expected = new Coordinate[] { c33, c33, c22, c22, c23, c23, c33, c33, c22, c22, c23, c23 };
	assertEquals("impromper test conditions", regions.length, expected.length);
	for (int i = 0; i < regions.length && i < expected.length; i++) {
	    assertEquals("x coordinates not equal", regions[i].getLowerRight().getX(), expected[i].getX());
	    assertEquals("y coordinates not equal", regions[i].getLowerRight().getY(), expected[i].getY());
	}
    }

    @Test
    public void testGetTop() {
	Region[] regions = new Region[] { new RegionImpl(c00, c33), new RegionImpl(c33, c00), new RegionImpl(c11, c22),
		new RegionImpl(c22, c11), new RegionImpl(c12, c23), new RegionImpl(c23, c12), new RegionImpl(c30, c03),
		new RegionImpl(c03, c30), new RegionImpl(c21, c12), new RegionImpl(c12, c21), new RegionImpl(c22, c13),
		new RegionImpl(c13, c22) };
	int[] expected = new int[] { 0, 0, 1, 1, 2, 2, 0, 0, 1, 1, 2, 2 };
	assertEquals("impromper test conditions", regions.length, expected.length);
	for (int i = 0; i < regions.length && i < expected.length; i++) {
	    assertEquals("top incorrect", regions[i].getTop(), expected[i]);
	}
    }

    @Test
    public void testGetBottom() {
	Region[] regions = new Region[] { new RegionImpl(c00, c33), new RegionImpl(c33, c00), new RegionImpl(c11, c22),
		new RegionImpl(c22, c11), new RegionImpl(c12, c23), new RegionImpl(c23, c12), new RegionImpl(c30, c03),
		new RegionImpl(c03, c30), new RegionImpl(c21, c12), new RegionImpl(c12, c21), new RegionImpl(c22, c13),
		new RegionImpl(c13, c22) };
	int[] expected = new int[] { 3, 3, 2, 2, 3, 3, 3, 3, 2, 2, 3, 3 };
	assertEquals("impromper test conditions", regions.length, expected.length);
	for (int i = 0; i < regions.length && i < expected.length; i++) {
	    assertEquals("bottom incorrect", regions[i].getBottom(), expected[i]);
	}
    }

    @Test
    public void testGetLeft() {
	Region[] regions = new Region[] { new RegionImpl(c00, c33), new RegionImpl(c33, c00), new RegionImpl(c11, c22),
		new RegionImpl(c22, c11), new RegionImpl(c12, c23), new RegionImpl(c23, c12), new RegionImpl(c30, c03),
		new RegionImpl(c03, c30), new RegionImpl(c21, c12), new RegionImpl(c12, c21), new RegionImpl(c22, c13),
		new RegionImpl(c13, c22) };
	int[] expected = new int[] { 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1 };
	assertEquals("impromper test conditions", regions.length, expected.length);
	for (int i = 0; i < regions.length && i < expected.length; i++) {
	    assertEquals("left incorrect", regions[i].getLeft(), expected[i]);
	}
    }

    @Test
    public void testGetRight() {
	Region[] regions = new Region[] { new RegionImpl(c00, c33), new RegionImpl(c33, c00), new RegionImpl(c11, c22),
		new RegionImpl(c22, c11), new RegionImpl(c12, c23), new RegionImpl(c23, c12), new RegionImpl(c30, c03),
		new RegionImpl(c03, c30), new RegionImpl(c21, c12), new RegionImpl(c12, c21), new RegionImpl(c22, c13),
		new RegionImpl(c13, c22) };
	int[] expected = new int[] { 3, 3, 2, 2, 2, 2, 3, 3, 2, 2, 2, 2 };
	assertEquals("impromper test conditions", regions.length, expected.length);
	for (int i = 0; i < regions.length && i < expected.length; i++) {
	    assertEquals("right incorrect", regions[i].getRight(), expected[i]);
	}
    }

    @Test
    public void testIntersect() {
	Region big = new RegionImpl(c00, c33);
	try {
	    assertTrue("region not equal to intersection with self", regionsEqual(big, big.intersect(big)));
	} catch (NoIntersectionException e) {
	    fail("intersecting regions threw");
	}

	try {
	    big.intersect(null);
	    fail("null argument didn't throw");
	} catch (NoIntersectionException e) {
	}

	Region tiny = new RegionImpl(c22, c33);
	try {
	    tiny.intersect(new RegionImpl(c00, c11));
	    fail("non intersecting regions didn't throw");
	} catch (NoIntersectionException e) {
	}

	try {
	    assertTrue("intersection of region inside region isn't inner region",
		    regionsEqual(tiny, big.intersect(tiny)));
	    assertTrue("intersection of region inside region isn't inner region",
		    regionsEqual(tiny, tiny.intersect(big)));
	} catch (NoIntersectionException e) {
	    fail("intersecting regions threw");
	}

	Region r = new RegionImpl(c00, c22);
	Region s = new RegionImpl(c11, c33);
	try {
	    assertTrue("intersection  not equal to expectation",
		    regionsEqual(new RegionImpl(c11, c22), r.intersect(s)));
	} catch (NoIntersectionException e) {
	    fail("intersecting regions threw");
	}
    }

    @Test
    public void testUnion() {
	Region big = new RegionImpl(c00, c33);
	assertTrue("region U null doesn't equal itself", regionsEqual(big, big.union(null)));
	assertTrue("region U itself doesn't equal itself", regionsEqual(big, big.union(big)));

	Region tiny = new RegionImpl(c22, c33);
	assertTrue("outer U inner doesn't equal outer", regionsEqual(big, big.union(tiny)));
	assertTrue("outer U inner doesn't equal outer", regionsEqual(big, tiny.union(big)));
	Region r = new RegionImpl(c00, c22);
	Region s = new RegionImpl(c11, c33);
	assertTrue("00->22 U 11->33 doesn't equal 00->33", regionsEqual(big, r.union(s)));
	assertTrue("00->22 U 11->33 doesn't equal 00->33", regionsEqual(big, s.union(r)));
    }

    static boolean regionsEqual(Region a, Region b) {
	return a.getUpperLeft().getX() == b.getUpperLeft().getX() && a.getUpperLeft().getY() == b.getUpperLeft().getY()
		&& a.getLowerRight().getX() == b.getLowerRight().getX()
		&& a.getLowerRight().getY() == b.getLowerRight().getY();
    }
}
