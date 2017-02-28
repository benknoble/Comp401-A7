package a7tests.zburk;

import static org.junit.Assert.*;

import a7.*;

import org.junit.Test;

public class A7Test {

    @Test
    public void testRegionUnion() {
        Coordinate aTopLeft = new Coordinate(0, 0);
        Coordinate aBottomRight = new Coordinate(10, 10);
        Region aRegion = new RegionImpl(aTopLeft, aBottomRight);

        Coordinate bTopLeft = new Coordinate(5, 5);
        Coordinate bBottomRight = new Coordinate(12, 12);
        Region bRegion = new RegionImpl(bTopLeft, bBottomRight);

        Region r1 = aRegion.union(bRegion);

        assertTrue(compareCoordinates(r1.getUpperLeft(), new Coordinate(0, 0)));
        assertTrue(compareCoordinates(r1.getLowerRight(), new Coordinate(12, 12)));

        Coordinate cTopLeft = new Coordinate(2, 4);
        Coordinate cBottomRight = new Coordinate(6, 8);
        Region cRegion = new RegionImpl(cTopLeft, cBottomRight);

        Coordinate dTopLeft = new Coordinate(1, 5);
        Coordinate dBottomRight = new Coordinate(7, 4);
        Region dRegion = new RegionImpl(dTopLeft, dBottomRight);

        Region r2 = cRegion.union(dRegion);

        assertTrue(compareCoordinates(r2.getUpperLeft(), new Coordinate(1, 4)));
        assertTrue(compareCoordinates(r2.getLowerRight(), new Coordinate(7, 8)));

        Region r3 = aRegion.union(null);
        assertEquals(r3, aRegion);
    }

    @Test
    public void testUnionWithSameSize() {
        Coordinate aTopLeft = new Coordinate(0, 0);
        Coordinate aBottomRight = new Coordinate(10, 10);
        Region aRegion = new RegionImpl(aTopLeft, aBottomRight);

        Coordinate bTopLeft = new Coordinate(0, 0);
        Coordinate bBottomRight = new Coordinate(10, 10);
        Region bRegion = new RegionImpl(bTopLeft, bBottomRight);

        Region r1 = aRegion.union(bRegion);
        assertTrue(compareCoordinates(r1.getUpperLeft(), aRegion.getUpperLeft()));
        assertTrue(compareCoordinates(r1.getLowerRight(), aRegion.getLowerRight()));
    }

    @Test
    public void testUnionAdjacent() {
        Coordinate aTopLeft = new Coordinate(0, 0);
        Coordinate aBottomRight = new Coordinate(5, 5);
        Region aRegion = new RegionImpl(aTopLeft, aBottomRight);

        Coordinate bTopLeft = new Coordinate(5, 0);
        Coordinate bBottomRight = new Coordinate(10, 5);
        Region bRegion = new RegionImpl(bTopLeft, bBottomRight);

        Region r1 = aRegion.union(bRegion);
        assertTrue(compareCoordinates(r1.getUpperLeft(), new Coordinate(0, 0)));
        assertTrue(compareCoordinates(r1.getLowerRight(), new Coordinate(10, 5)));
    }

    @Test
    public void testUnionOverlap() {
        Coordinate aTopLeft = new Coordinate(2, 0);
        Coordinate aBottomRight = new Coordinate(5, 5);
        Region aRegion = new RegionImpl(aTopLeft, aBottomRight);

        Coordinate bTopLeft = new Coordinate(0, 0);
        Coordinate bBottomRight = new Coordinate(10, 5);
        Region bRegion = new RegionImpl(bTopLeft, bBottomRight);

        Region r1 = aRegion.union(bRegion);
        assertTrue(compareCoordinates(r1.getUpperLeft(), new Coordinate(0, 0)));
        assertTrue(compareCoordinates(r1.getLowerRight(), new Coordinate(10, 5)));
    }

    @Test
    public void testRegionIntersect() {
        Coordinate aTopLeft = new Coordinate(0, 0);
        Coordinate aBottomRight = new Coordinate(10, 10);
        Region aRegion = new RegionImpl(aTopLeft, aBottomRight);

        Coordinate bTopLeft = new Coordinate(5, 5);
        Coordinate bBottomRight = new Coordinate(12, 12);
        Region bRegion = new RegionImpl(bTopLeft, bBottomRight);

        try {
            Region r1 = aRegion.intersect(bRegion);

            assertTrue(compareCoordinates(r1.getUpperLeft(), new Coordinate(5, 5)));
            assertTrue(compareCoordinates(r1.getLowerRight(), new Coordinate(10, 10)));

        } catch (NoIntersectionException e) {
            fail();
        }

        Coordinate cTopLeft = new Coordinate(2, 4);
        Coordinate cBottomRight = new Coordinate(6, 8);
        Region cRegion = new RegionImpl(cTopLeft, cBottomRight);

        Coordinate dTopLeft = new Coordinate(1, 5);
        Coordinate dBottomRight = new Coordinate(7, 4);
        Region dRegion = new RegionImpl(dTopLeft, dBottomRight);

        try {
            Region r2 = cRegion.intersect(dRegion);

            assertTrue(compareCoordinates(r2.getUpperLeft(), new Coordinate(2, 4)));
            assertTrue(compareCoordinates(r2.getLowerRight(), new Coordinate(6, 5)));

        } catch (NoIntersectionException e) {
            fail();
        }

        Coordinate eTopLeft = new Coordinate(1, 0);
        Coordinate eBottomRight = new Coordinate(3, 2);
        Region eRegion = new RegionImpl(eTopLeft, eBottomRight);

        Coordinate fTopLeft = new Coordinate(2, 1);
        Coordinate fBottomRight = new Coordinate(4, 3);
        Region fRegion = new RegionImpl(fTopLeft, fBottomRight);

        try {
            Region r3 = eRegion.intersect(fRegion);

            assertTrue(compareCoordinates(r3.getUpperLeft(), new Coordinate(2, 1)));
            assertTrue(compareCoordinates(r3.getLowerRight(), new Coordinate(3, 2)));

        } catch (NoIntersectionException e) {
            fail();
        }

        try {
            aRegion.intersect(null);

            fail("Should throw IllegalArgumentException");
        } catch (NoIntersectionException e) {
        } catch (IllegalArgumentException e) {
            fail("Null should throw IllegalArgumentException");
        }

        Coordinate gTopLeft = new Coordinate(0, 0);
        Coordinate gBottomRight = new Coordinate(1, 1);
        Region gRegion = new RegionImpl(gTopLeft, gBottomRight);

        Coordinate hTopLeft = new Coordinate(2, 2);
        Coordinate hBottomRight = new Coordinate(3, 3);
        Region hRegion = new RegionImpl(hTopLeft, hBottomRight);

        try {
            gRegion.intersect(hRegion);

            fail("Should throw NoIntersectionException");
        } catch (NoIntersectionException e) {
        } catch (IllegalArgumentException e) {
            fail("no intersection should not throw IllegalArgumentException");
        }
    }

    @Test
    public void testRegionIntersectWithSameSize() {
        Coordinate aTopLeft = new Coordinate(0, 0);
        Coordinate aBottomRight = new Coordinate(10, 10);
        Region aRegion = new RegionImpl(aTopLeft, aBottomRight);

        Coordinate bTopLeft = new Coordinate(0, 0);
        Coordinate bBottomRight = new Coordinate(10, 10);
        Region bRegion = new RegionImpl(bTopLeft, bBottomRight);

        try {
            Region r1 = aRegion.intersect(bRegion);

            assertTrue(compareCoordinates(r1.getUpperLeft(), new Coordinate(0, 0)));
            assertTrue(compareCoordinates(r1.getLowerRight(), new Coordinate(10, 10)));

        } catch (NoIntersectionException e) {
            fail();
        }
    }

    private boolean compareCoordinates(Coordinate c1, Coordinate c2) {
        return c1.getX() == c2.getX() && c1.getY() == c2.getY();
    }

}
