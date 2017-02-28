package a7tests.jeroen;

import static org.junit.Assert.*;

import org.junit.Test;

import a7.*;

public class RegionImplTest {

	/*
	 * UNC Chapel Hill COMP401 assignment 7 jUnit tests by Jeroen.
	 * 
	 * This tests intersect, getters, and union
	 */

	@Test
	public void getterTest() {
		Coordinate c1 = new Coordinate(1, 2);
		Coordinate c2 = new Coordinate(9, 10);
		Region r1 = new RegionImpl(c1, c2);
		Region r2 = new RegionImpl(c2, c1);

		assertTrue("getBottom broken", r1.getBottom() == r2.getBottom());
		assertTrue("getTop broken", r1.getTop() == r2.getTop());
		assertTrue("getLeft broken", r1.getLeft() == r2.getLeft());
		assertTrue("getRight broken", r1.getRight() == r2.getRight());
		assertTrue("getUpperLeft broken", isSameCoordinate(r1.getUpperLeft(), r2.getUpperLeft()));
		assertTrue("getLowerRight broken", isSameCoordinate(r1.getLowerRight(), r2.getLowerRight()));
	}

	@Test
	public void intersectTest() {
		Coordinate c1 = new Coordinate(0, 0);
		Coordinate c2 = new Coordinate(2, 2);
		Coordinate c3 = new Coordinate(2, 2);
		Coordinate c4 = new Coordinate(4, 4);

		Region r1 = new RegionImpl(c1, c2);
		Region r2 = new RegionImpl(c3, c4);
		Region r3;

		try {
			r3 = r1.intersect(r2);
			assertEquals("Intersect broken", r3.getLeft(), 2);
		} catch (NoIntersectionException e) {
			fail("Intersect threw NoIntersectionException but Regions overlap.");
		}

		try {
			Coordinate c5 = new Coordinate(6, 6);

			Region r4 = new RegionImpl(c1, c4);
			Region r5 = new RegionImpl(c2, c5);
			Region r6;

			r6 = r5.intersect(r4);

			assertEquals("Intersect broken", r6.getUpperLeft().getX(), 2);
			assertEquals("Intersect broken", r6.getUpperLeft().getY(), 2);
			assertEquals("Intersect broken", r6.getLowerRight().getX(), 4);
			assertEquals("Intersect broken", r6.getLowerRight().getY(), 4);

		} catch (NoIntersectionException e) {
			fail("Intersect threw NoIntersectionException but Regions overlap.");
		}

		try {
			Coordinate c6 = new Coordinate(0, 2);
			Coordinate c7 = new Coordinate(4, 6);
			Coordinate c8 = new Coordinate(2, 0);
			Coordinate c9 = new Coordinate(6, 4);

			Region r7 = new RegionImpl(c6, c7);
			Region r8 = new RegionImpl(c8, c9);
			Region r9;
			r9 = r8.intersect(r7);

			assertEquals("Intersect broken", r9.getUpperLeft().getX(), 2);
			assertEquals("Intersect broken", r9.getUpperLeft().getY(), 2);
			assertEquals("Intersect broken", r9.getLowerRight().getX(), 4);
			assertEquals("Intersect broken", r9.getLowerRight().getY(), 4);
		} catch (NoIntersectionException e) {
			fail("Intersect threw NoIntersectionException but Regions overlap.");
		}
	}

	@Test
	public void unionTest() {
		Coordinate c1 = new Coordinate(0, 0);
		Coordinate c2 = new Coordinate(2, 2);
		Coordinate c3 = new Coordinate(2, 2);
		Coordinate c4 = new Coordinate(4, 4);

		Region r1 = new RegionImpl(c1, c2);
		Region r2 = new RegionImpl(c3, c4);
		Region r3 = r2.union(r1);

		assertEquals("Union broken", r3.getLeft(), 0);
		assertEquals("Union broken", r3.getRight(), 4);
		assertEquals("Union broken", r3.getTop(), 0);
		assertEquals("Union broken", r3.getBottom(), 4);

		Coordinate c5 = new Coordinate(2, 2);
		Coordinate c6 = new Coordinate(6, 6);
		Coordinate c7 = new Coordinate(10, 10);
		Coordinate c8 = new Coordinate(20, 20);

		Region r4 = new RegionImpl(c5, c6);
		Region r5 = new RegionImpl(c7, c8);
		Region r6 = r4.union(r5);

		assertEquals("Union broken", r6.getLeft(), 2);
		assertEquals("Union broken", r6.getRight(), 20);
		assertEquals("Union broken", r6.getTop(), 2);
		assertEquals("Union broken", r6.getBottom(), 20);
	}

	@Test
	public void IntersectTest2() {
		/* Let's dive deeper into the intersect method */

		Coordinate c1 = new Coordinate(0, 0);
		Coordinate c2 = new Coordinate(2, 2);
		Coordinate c3 = new Coordinate(3, 0);
		Coordinate c4 = new Coordinate(5, 2);
		Coordinate c5 = new Coordinate(5, 5);
		Coordinate c6 = new Coordinate(3, 3);
		Coordinate c7 = new Coordinate(2, 5);
		Coordinate c8 = new Coordinate(0, 3);

		Region r1 = new RegionImpl(c1, c2);
		Region r2 = new RegionImpl(c3, c4);
		Region r3 = new RegionImpl(c5, c6);
		Region r4 = new RegionImpl(c7, c8);

		/* no intersection */
		try {
			r1.intersect(r2);
			fail("Didn't throw NoIntersectionException");
		} catch (NoIntersectionException e) {
		}

		try {
			r1.intersect(r3);
			fail("Didn't throw NoIntersectionException");
		} catch (NoIntersectionException e) {
		}

		try {
			r1.intersect(r4);
			fail("Didn't throw NoIntersectionException");
		} catch (NoIntersectionException e) {
		}

		try {
			r2.intersect(r1);
			fail("Didn't throw NoIntersectionException");
		} catch (NoIntersectionException e) {
		}

		try {
			r2.intersect(r3);
			fail("Didn't throw NoIntersectionException");
		} catch (NoIntersectionException e) {
		}

		try {
			r2.intersect(r4);
			fail("Didn't throw NoIntersectionException");
		} catch (NoIntersectionException e) {
		}

		try {
			r3.intersect(r2);
			fail("Didn't throw NoIntersectionException");
		} catch (NoIntersectionException e) {
		}

		try {
			r3.intersect(r1);
			fail("Didn't throw NoIntersectionException");
		} catch (NoIntersectionException e) {
		}

		try {
			r3.intersect(r4);
			fail("Didn't throw NoIntersectionException");
		} catch (NoIntersectionException e) {
		}

		try {
			r4.intersect(r2);
			fail("Didn't throw NoIntersectionException");
		} catch (NoIntersectionException e) {
		}

		try {
			r4.intersect(r1);
			fail("Didn't throw NoIntersectionException");
		} catch (NoIntersectionException e) {
		}

		try {
			r4.intersect(r3);
			fail("Didn't throw NoIntersectionException");
		} catch (NoIntersectionException e) {
		}

		/* Now check if it works when they do overlap */
		Region r5 = new RegionImpl(c2, c6);
		Coordinate c9 = new Coordinate(3, 2);
		Coordinate c10 = new Coordinate(2, 3);

		try {
			assertTrue("Intersect doesn't work", isSameRegion(r1.intersect(r5), new RegionImpl(c2, c2)));
		} catch (NoIntersectionException e) {
			fail("Threw NoIntersectionException");
		}

		try {
			assertTrue("Intersect doesn't work", isSameRegion(r5.intersect(r1), new RegionImpl(c2, c2)));
		} catch (NoIntersectionException e) {
			fail("Threw NoIntersectionException");
		}

		try {
			assertTrue("Intersect doesn't work", isSameRegion(r2.intersect(r5), new RegionImpl(c9, c9)));
		} catch (NoIntersectionException e) {
			fail("Threw NoIntersectionException");
		}

		try {
			assertTrue("Intersect doesn't work", isSameRegion(r5.intersect(r2), new RegionImpl(c9, c9)));
		} catch (NoIntersectionException e) {
			fail("Threw NoIntersectionException");
		}

		try {
			assertTrue("Intersect doesn't work", isSameRegion(r3.intersect(r5), new RegionImpl(c6, c6)));
		} catch (NoIntersectionException e) {
			fail("Threw NoIntersectionException");
		}

		try {
			assertTrue("Intersect doesn't work", isSameRegion(r5.intersect(r3), new RegionImpl(c6, c6)));
		} catch (NoIntersectionException e) {
			fail("Threw NoIntersectionException");
		}

		try {
			assertTrue("Intersect doesn't work", isSameRegion(r4.intersect(r5), new RegionImpl(c10, c10)));
		} catch (NoIntersectionException e) {
			fail("Threw NoIntersectionException");
		}

		try {
			assertTrue("Intersect doesn't work", isSameRegion(r5.intersect(r4), new RegionImpl(c10, c10)));
		} catch (NoIntersectionException e) {
			fail("Threw NoIntersectionException");
		}
	}

	/* Helper method to determine if two coordinates are the same. */
	public static boolean isSameCoordinate(Coordinate c1, Coordinate c2) {
		if (c1.getX() == c2.getX() && c1.getY() == c2.getY()) {
			return true;
		} else {
			return false;
		}
	}

	/* Helper method to see if two regions have the same dimensions */
	public static boolean isSameRegion(Region r1, Region r2) {
		if (r1.getLeft() == r2.getLeft() && r1.getRight() == r2.getRight() && r1.getTop() == r2.getTop()
				&& r1.getBottom() == r2.getBottom()) {
			return true;
		} else {
			return false;
		}
	}
}
