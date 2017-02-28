package a7tests.kmp;

import a7.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class A7GraderTests {

    @Test
    public void basicRegionTest() {
        Region r = new RegionImpl(new Coordinate(1, 3), new Coordinate(7, 10));

        assertEquals("Incorrect getLeft()", 1, r.getLeft());
        assertEquals("Incorrect getRight()", 7, r.getRight());
        assertEquals("Incorrect getTop()", 3, r.getTop());
        assertEquals("Incorrect getBottom()", 10, r.getBottom());
        assertEquals("Incorrect getUpperLeft()", 1, r.getUpperLeft().getX());
        assertEquals("Incorrect getUpperLeft()", 3, r.getUpperLeft().getY());
        assertEquals("Incorrect getLowerRight()", 7, r.getLowerRight().getX());
        assertEquals("Incorrect getLowerRight()", 10, r.getLowerRight().getY());

        r = new RegionImpl(new Coordinate(7, 10), new Coordinate(1, 3));

        assertEquals("Incorrect getLeft()", 1, r.getLeft());
        assertEquals("Incorrect getRight()", 7, r.getRight());
        assertEquals("Incorrect getTop()", 3, r.getTop());
        assertEquals("Incorrect getBottom()", 10, r.getBottom());
        assertEquals("Incorrect getUpperLeft()", 1, r.getUpperLeft().getX());
        assertEquals("Incorrect getUpperLeft()", 3, r.getUpperLeft().getY());
        assertEquals("Incorrect getLowerRight()", 7, r.getLowerRight().getX());
        assertEquals("Incorrect getLowerRight()", 10, r.getLowerRight().getY());

        r = new RegionImpl(new Coordinate(1, 10), new Coordinate(7, 3));

        assertEquals("Incorrect getLeft()", 1, r.getLeft());
        assertEquals("Incorrect getRight()", 7, r.getRight());
        assertEquals("Incorrect getTop()", 3, r.getTop());
        assertEquals("Incorrect getBottom()", 10, r.getBottom());
        assertEquals("Incorrect getUpperLeft()", 1, r.getUpperLeft().getX());
        assertEquals("Incorrect getUpperLeft()", 3, r.getUpperLeft().getY());
        assertEquals("Incorrect getLowerRight()", 7, r.getLowerRight().getX());
        assertEquals("Incorrect getLowerRight()", 10, r.getLowerRight().getY());

        r = new RegionImpl(new Coordinate(7, 3), new Coordinate(1, 10));

        assertEquals("Incorrect getLeft()", 1, r.getLeft());
        assertEquals("Incorrect getRight()", 7, r.getRight());
        assertEquals("Incorrect getTop()", 3, r.getTop());
        assertEquals("Incorrect getBottom()", 10, r.getBottom());
        assertEquals("Incorrect getUpperLeft()", 1, r.getUpperLeft().getX());
        assertEquals("Incorrect getUpperLeft()", 3, r.getUpperLeft().getY());
        assertEquals("Incorrect getLowerRight()", 7, r.getLowerRight().getX());
        assertEquals("Incorrect getLowerRight()", 10, r.getLowerRight().getY());

    }

    @Test
    public void basicRegionIntersectionTest() {
        Coordinate a = new Coordinate(0, 0);
        new Coordinate(5, 0);
        Coordinate c = new Coordinate(10, 0);
        new Coordinate(0, 5);
        Coordinate e = new Coordinate(5, 5);
        Coordinate f = new Coordinate(10, 5);
        Coordinate g = new Coordinate(0, 10);
        Coordinate h = new Coordinate(5, 10);
        Coordinate i = new Coordinate(10, 10);

        Region ae = new RegionImpl(a, e);
        Region ce = new RegionImpl(c, e);
        Region ge = new RegionImpl(g, e);
        Region ie = new RegionImpl(i, e);

        Region af = new RegionImpl(a, f);
        Region gf = new RegionImpl(g, f);

        Region ah = new RegionImpl(a, h);
        Region ch = new RegionImpl(c, h);

        Region ai = new RegionImpl(a, i);

        Coordinate ul = new Coordinate(2, 2);
        Coordinate lr = new Coordinate(7, 7);
        Coordinate ur = new Coordinate(7, 2);
        Coordinate ll = new Coordinate(2, 7);

        Region test_region = new RegionImpl(ul, lr);

        Coordinate mid_right = new Coordinate(7, 5);
        Coordinate mid_bottom = new Coordinate(5, 7);
        try {
            assertTrue(compareRegions(test_region.intersect(ae), new RegionImpl(ul, e)));
            assertTrue(compareRegions(test_region.intersect(ce), new RegionImpl(ur, e)));
            assertTrue(compareRegions(test_region.intersect(ge), new RegionImpl(ll, e)));
            assertTrue(compareRegions(test_region.intersect(ie), new RegionImpl(lr, e)));

            assertTrue(compareRegions(test_region.intersect(af), new RegionImpl(ul, mid_right)));
            assertTrue(compareRegions(test_region.intersect(gf), new RegionImpl(ll, mid_right)));

            assertTrue(compareRegions(test_region.intersect(ah), new RegionImpl(ul, mid_bottom)));
            assertTrue(compareRegions(test_region.intersect(ch), new RegionImpl(ur, mid_bottom)));

            assertTrue(compareRegions(test_region.intersect(ai), test_region));

            // And the other way
            assertTrue(compareRegions(ae.intersect(test_region), new RegionImpl(ul, e)));
            assertTrue(compareRegions(ce.intersect(test_region), new RegionImpl(ur, e)));
            assertTrue(compareRegions(ge.intersect(test_region), new RegionImpl(ll, e)));
            assertTrue(compareRegions(ie.intersect(test_region), new RegionImpl(lr, e)));

            assertTrue(compareRegions(af.intersect(test_region), new RegionImpl(ul, mid_right)));
            assertTrue(compareRegions(gf.intersect(test_region), new RegionImpl(ll, mid_right)));

            assertTrue(compareRegions(ah.intersect(test_region), new RegionImpl(ul, mid_bottom)));
            assertTrue(compareRegions(ch.intersect(test_region), new RegionImpl(ur, mid_bottom)));

            assertTrue(compareRegions(ai.intersect(test_region), test_region));

        } catch (NoIntersectionException nie) {
            fail("Unexpected NoIntersectionException");
        }

    }

    @Test
    public void basicRegionUnionTest() {
        Coordinate a = new Coordinate(0, 0);
        new Coordinate(5, 0);
        Coordinate c = new Coordinate(10, 0);
        new Coordinate(0, 5);
        Coordinate e = new Coordinate(5, 5);
        Coordinate f = new Coordinate(10, 5);
        Coordinate g = new Coordinate(0, 10);
        Coordinate h = new Coordinate(5, 10);
        Coordinate i = new Coordinate(10, 10);

        Region ae = new RegionImpl(a, e);
        Region ce = new RegionImpl(c, e);
        Region ge = new RegionImpl(g, e);
        Region ie = new RegionImpl(i, e);

        Region af = new RegionImpl(a, f);
        Region gf = new RegionImpl(g, f);

        Region ah = new RegionImpl(a, h);
        Region ch = new RegionImpl(c, h);

        Region ai = new RegionImpl(a, i);

        Coordinate ul = new Coordinate(2, 2);
        Coordinate lr = new Coordinate(7, 7);
        Coordinate ur = new Coordinate(7, 2);
        Coordinate ll = new Coordinate(2, 7);

        Region test_region = new RegionImpl(ul, lr);

        new Coordinate(7, 5);
        new Coordinate(5, 7);

        assertTrue(compareRegions(test_region.union(ae), new RegionImpl(lr, a)));
        assertTrue(compareRegions(test_region.union(ce), new RegionImpl(ll, c)));
        assertTrue(compareRegions(test_region.union(ge), new RegionImpl(ur, g)));
        assertTrue(compareRegions(test_region.union(ie), new RegionImpl(ul, i)));

        assertTrue(compareRegions(test_region.union(af),
                new RegionImpl(a, new Coordinate(f.getX(), test_region.getBottom()))));
        assertTrue(compareRegions(test_region.union(gf),
                new RegionImpl(g, new Coordinate(f.getX(), test_region.getTop()))));

        assertTrue(compareRegions(test_region.union(ah),
                new RegionImpl(a, new Coordinate(test_region.getRight(), h.getY()))));
        assertTrue(compareRegions(test_region.union(ch),
                new RegionImpl(c, new Coordinate(test_region.getLeft(), h.getY()))));

        assertTrue(compareRegions(test_region.union(ai), ai));

        // And the other way

        assertTrue(compareRegions(ae.union(test_region), new RegionImpl(lr, a)));
        assertTrue(compareRegions(ce.union(test_region), new RegionImpl(ll, c)));
        assertTrue(compareRegions(ge.union(test_region), new RegionImpl(ur, g)));
        assertTrue(compareRegions(ie.union(test_region), new RegionImpl(ul, i)));

        assertTrue(compareRegions(af.union(test_region),
                new RegionImpl(a, new Coordinate(f.getX(), test_region.getBottom()))));
        assertTrue(compareRegions(gf.union(test_region),
                new RegionImpl(g, new Coordinate(f.getX(), test_region.getTop()))));

        assertTrue(compareRegions(ah.union(test_region),
                new RegionImpl(a, new Coordinate(test_region.getRight(), h.getY()))));
        assertTrue(compareRegions(ch.union(test_region),
                new RegionImpl(c, new Coordinate(test_region.getLeft(), h.getY()))));

        assertTrue(compareRegions(ai.union(test_region), ai));
    }

    @Test
    public void nullIntersectionTest() {
        Region r = new RegionImpl(new Coordinate(0, 0), new Coordinate(5, 5));
        try {
            r.intersect(null);
            fail("Expected intersection with null to throw NoIntersectionException");
        } catch (NoIntersectionException e) {
        }
    }

    @Test
    public void nullUnionTest() {
        Region r = new RegionImpl(new Coordinate(0, 0), new Coordinate(5, 5));
        assertTrue(compareRegions(r.union(null), r));
    }

    @Test
    public void noIntersectionTest() {
        Region r1 = new RegionImpl(new Coordinate(0, 0), new Coordinate(1, 1));
        Region r2 = new RegionImpl(new Coordinate(2, 2), new Coordinate(3, 3));

        try {
            r1.intersect(r2);
            fail("Expected NoIntersectionException");
        } catch (NoIntersectionException e) {
        }

        try {
            r2.intersect(r1);
            fail("Expected NoIntersectionException");
        } catch (NoIntersectionException e) {
        }
    }

    @Test
    public void simpleObserverTest() {
        TestROIObserver tro = new TestROIObserver();
        Picture p = new PictureImpl(10, 10);
        Region r = new RegionImpl(new Coordinate(2, 2), new Coordinate(4, 4));
        ObservablePicture op = new ObservablePictureImpl(p);
        op.registerROIObserver(tro, r);

        Coordinate hit_coord = new Coordinate(3, 3);
        Coordinate miss_coord = new Coordinate(0, 0);

        op.setPixel(hit_coord, new ColorPixel(0.5, 0.5, 0.5));
        assertEquals(1, tro.getHitCount());
        RegionHit hit = tro.getHits()[0];
        assertTrue(compareRegions(new RegionImpl(hit_coord, hit_coord), hit));
        assertEquals(op, hit.getHitPicture());

        tro.reset();

        op.setPixel(miss_coord, new ColorPixel(0.5, 0.5, 0.5));
        assertEquals(0, tro.getHitCount());

        p.setPixel(hit_coord, new ColorPixel(0.5, 0.5, 0.5));
        assertEquals(0, tro.getHitCount());
    }

    @Test
    public void twoOverlappingObserversTest() {
        TestROIObserver tro_a = new TestROIObserver();
        TestROIObserver tro_b = new TestROIObserver();

        Picture p = new PictureImpl(10, 10);
        ObservablePicture op = new ObservablePictureImpl(p);

        Region roi_a = new RegionImpl(new Coordinate(2, 2), new Coordinate(4, 4));
        Region roi_b = new RegionImpl(new Coordinate(4, 4), new Coordinate(6, 6));

        op.registerROIObserver(tro_a, roi_a);
        op.registerROIObserver(tro_b, roi_b);

        Coordinate a_hit_coord = new Coordinate(3, 3);
        Coordinate b_hit_coord = new Coordinate(5, 5);
        Coordinate ab_hit_coord = new Coordinate(4, 4);

        Pixel pix = new ColorPixel(0.5, 0.5, 0.5);

        op.setPixel(a_hit_coord, pix);
        assertEquals(1, tro_a.getHitCount());
        assertEquals(0, tro_b.getHitCount());
        tro_a.reset();

        op.setPixel(b_hit_coord, pix);
        assertEquals(1, tro_b.getHitCount());
        assertEquals(0, tro_a.getHitCount());
        tro_b.reset();

        op.setPixel(ab_hit_coord, pix);
        assertEquals(1, tro_b.getHitCount());
        assertEquals(1, tro_a.getHitCount());
    }

    @Test
    public void sameObserverManyRegionsTest() {
        ObservablePicture op = new ObservablePictureImpl(new PictureImpl(11, 11));
        Pixel pix = new ColorPixel(0.5, 0.5, 0.5);

        Region r1 = new RegionImpl(new Coordinate(0, 2), new Coordinate(8, 6));
        Region r2 = new RegionImpl(new Coordinate(2, 4), new Coordinate(10, 8));
        Region r3 = new RegionImpl(new Coordinate(4, 0), new Coordinate(6, 10));

        TestROIObserver tro = new TestROIObserver();

        op.registerROIObserver(tro, r1);
        op.registerROIObserver(tro, r2);
        op.registerROIObserver(tro, r3);

        Coordinate miss = new Coordinate(1, 1);
        Coordinate r1_hit = new Coordinate(1, 3);
        Coordinate r2_hit = new Coordinate(9, 7);
        Coordinate r3_hit = new Coordinate(5, 1);
        Coordinate r1_r2_hit = new Coordinate(7, 5);
        Coordinate r1_r3_hit = new Coordinate(5, 3);
        Coordinate r2_r3_hit = new Coordinate(5, 7);
        Coordinate all_hit = new Coordinate(5, 5);

        op.setPixel(miss, pix);
        assertEquals(0, tro.getHitCount());
        op.setPixel(r1_hit, pix);
        assertEquals(1, tro.getHitCount());
        tro.reset();
        op.setPixel(r2_hit, pix);
        assertEquals(1, tro.getHitCount());
        tro.reset();
        op.setPixel(r3_hit, pix);
        assertEquals(1, tro.getHitCount());
        tro.reset();
        op.setPixel(r1_r2_hit, pix);
        assertEquals(2, tro.getHitCount());
        tro.reset();
        op.setPixel(r1_r3_hit, pix);
        assertEquals(2, tro.getHitCount());
        tro.reset();
        op.setPixel(r2_r3_hit, pix);
        assertEquals(2, tro.getHitCount());
        tro.reset();
        op.setPixel(all_hit, pix);
        assertEquals(3, tro.getHitCount());
        tro.reset();
    }

    @Test
    public void unregisterByRegionTest() {
        ObservablePicture op = new ObservablePictureImpl(new PictureImpl(11, 11));
        Pixel pix = new ColorPixel(0.5, 0.5, 0.5);

        Region r1 = new RegionImpl(new Coordinate(0, 2), new Coordinate(8, 6));
        Region r2 = new RegionImpl(new Coordinate(2, 4), new Coordinate(10, 8));
        Region r3 = new RegionImpl(new Coordinate(4, 0), new Coordinate(6, 10));

        TestROIObserver tro1 = new TestROIObserver();
        TestROIObserver tro2 = new TestROIObserver();
        TestROIObserver tro3 = new TestROIObserver();

        op.registerROIObserver(tro1, r1);
        op.registerROIObserver(tro2, r2);
        op.registerROIObserver(tro3, r3);

        new Coordinate(1, 1);
        new Coordinate(1, 3);
        new Coordinate(9, 7);
        new Coordinate(5, 1);
        new Coordinate(7, 5);
        new Coordinate(5, 3);
        new Coordinate(5, 7);
        Coordinate all_hit = new Coordinate(5, 5);

        op.setPixel(all_hit, pix);
        assertEquals(1, tro1.getHitCount());
        assertEquals(1, tro2.getHitCount());
        assertEquals(1, tro3.getHitCount());
        tro1.reset();
        tro2.reset();
        tro3.reset();

        op.unregisterROIObservers(new RegionImpl(new Coordinate(0, 0), new Coordinate(10, 1)));
        op.setPixel(all_hit, pix);
        assertEquals(1, tro1.getHitCount());
        assertEquals(1, tro2.getHitCount());
        assertEquals(0, tro3.getHitCount());
        tro1.reset();
        tro2.reset();
        tro3.reset();

        op.unregisterROIObservers(new RegionImpl(all_hit, all_hit));
        op.setPixel(all_hit, pix);
        assertEquals(0, tro1.getHitCount());
        assertEquals(0, tro2.getHitCount());
        assertEquals(0, tro3.getHitCount());
        tro1.reset();
        tro2.reset();
        tro3.reset();
    }

    @Test
    public void unregisterByROIObserverTest() {
        ObservablePicture op = new ObservablePictureImpl(new PictureImpl(11, 11));
        Pixel pix = new ColorPixel(0.5, 0.5, 0.5);

        Region r1 = new RegionImpl(new Coordinate(0, 2), new Coordinate(8, 6));
        Region r2 = new RegionImpl(new Coordinate(2, 4), new Coordinate(10, 8));
        Region r3 = new RegionImpl(new Coordinate(4, 0), new Coordinate(6, 10));

        TestROIObserver tro12 = new TestROIObserver();
        TestROIObserver tro3 = new TestROIObserver();

        op.registerROIObserver(tro12, r1);
        op.registerROIObserver(tro12, r2);
        op.registerROIObserver(tro3, r3);

        new Coordinate(1, 1);
        new Coordinate(1, 3);
        new Coordinate(9, 7);
        new Coordinate(5, 1);
        new Coordinate(7, 5);
        new Coordinate(5, 3);
        new Coordinate(5, 7);
        Coordinate all_hit = new Coordinate(5, 5);

        op.setPixel(all_hit, pix);
        assertEquals(2, tro12.getHitCount());
        assertEquals(1, tro3.getHitCount());
        tro12.reset();
        tro3.reset();

        op.unregisterROIObserver(tro12);
        op.setPixel(all_hit, pix);
        assertEquals(0, tro12.getHitCount());
        assertEquals(1, tro3.getHitCount());
        tro12.reset();
        tro3.reset();

        op.unregisterROIObserver(tro3);
        op.setPixel(all_hit, pix);
        assertEquals(0, tro12.getHitCount());
        assertEquals(0, tro3.getHitCount());
    }

    @Test
    public void findROIObserverTest() {
        ObservablePicture op = new ObservablePictureImpl(new PictureImpl(11, 11));
        new ColorPixel(0.5, 0.5, 0.5);

        Region r1 = new RegionImpl(new Coordinate(0, 2), new Coordinate(8, 6));
        Region r2 = new RegionImpl(new Coordinate(2, 4), new Coordinate(10, 8));
        Region r3 = new RegionImpl(new Coordinate(4, 0), new Coordinate(6, 10));

        TestROIObserver tro1 = new TestROIObserver();
        TestROIObserver tro2 = new TestROIObserver();
        TestROIObserver tro3 = new TestROIObserver();

        op.registerROIObserver(tro1, r1);
        op.registerROIObserver(tro2, r2);
        op.registerROIObserver(tro3, r3);

        Coordinate miss = new Coordinate(1, 1);
        Coordinate r1_hit = new Coordinate(1, 3);
        Coordinate r2_hit = new Coordinate(9, 7);
        Coordinate r3_hit = new Coordinate(5, 1);
        Coordinate r1_r2_hit = new Coordinate(7, 5);
        Coordinate r1_r3_hit = new Coordinate(5, 3);
        Coordinate r2_r3_hit = new Coordinate(5, 7);
        Coordinate all_hit = new Coordinate(5, 5);

        ROIObserver[] found = op.findROIObservers(new RegionImpl(miss, miss));
        assertNotNull(found);
        assertEquals(0, found.length);

        found = op.findROIObservers(new RegionImpl(r1_hit, r1_hit));
        assertNotNull(found);
        assertEquals(1, found.length);
        assertEquals(tro1, found[0]);

        found = op.findROIObservers(new RegionImpl(r2_hit, r2_hit));
        assertNotNull(found);
        assertEquals(1, found.length);
        assertEquals(tro2, found[0]);

        found = op.findROIObservers(new RegionImpl(r3_hit, r3_hit));
        assertNotNull(found);
        assertEquals(1, found.length);
        assertEquals(tro3, found[0]);

        found = op.findROIObservers(new RegionImpl(r1_r2_hit, r1_r2_hit));
        assertNotNull(found);
        assertEquals(2, found.length);
        assertTrue(findInArray(found, tro1));
        assertTrue(findInArray(found, tro2));

        found = op.findROIObservers(new RegionImpl(r1_r3_hit, r1_r3_hit));
        assertNotNull(found);
        assertEquals(2, found.length);
        assertTrue(findInArray(found, tro1));
        assertTrue(findInArray(found, tro3));

        found = op.findROIObservers(new RegionImpl(r2_r3_hit, r2_r3_hit));
        assertNotNull(found);
        assertEquals(2, found.length);
        assertTrue(findInArray(found, tro2));
        assertTrue(findInArray(found, tro3));

        found = op.findROIObservers(new RegionImpl(all_hit, all_hit));
        assertNotNull(found);
        assertEquals(3, found.length);
        assertTrue(findInArray(found, tro1));
        assertTrue(findInArray(found, tro2));
        assertTrue(findInArray(found, tro3));

    }

    @Test
    public void basicSuspendResumeTest() {
        ObservablePicture op = new ObservablePictureImpl(new PictureImpl(11, 11));
        Pixel pix = new ColorPixel(0.5, 0.5, 0.5);

        Region roi_region = new RegionImpl(new Coordinate(2, 4), new Coordinate(6, 8));
        TestROIObserver tro = new TestROIObserver();

        op.registerROIObserver(tro, roi_region);

        Coordinate a = new Coordinate(4, 2);
        Coordinate b = new Coordinate(8, 2);
        Coordinate c = new Coordinate(4, 6);
        Coordinate d = new Coordinate(8, 6);

        op.suspendObservable();
        op.setPixel(a, pix);
        op.setPixel(b, pix);
        op.resumeObservable();
        assertEquals(0, tro.getHitCount());

        op.suspendObservable();
        op.setPixel(b, pix);
        op.setPixel(d, pix);
        op.resumeObservable();
        assertEquals(0, tro.getHitCount());

        op.suspendObservable();
        op.setPixel(a, pix);
        op.setPixel(b, pix);
        op.setPixel(c, pix);
        op.setPixel(d, pix);
        op.resumeObservable();
        assertEquals(1, tro.getHitCount());
        RegionHit hit = tro.getHits()[0];
        assertEquals(4, hit.getTop());
        assertEquals(6, hit.getRight());
        assertEquals(6, hit.getBottom());
        assertEquals(4, hit.getLeft());
        tro.reset();

        op.suspendObservable();
        op.setPixel(c, pix);
        op.resumeObservable();
        assertEquals(1, tro.getHitCount());
        hit = tro.getHits()[0];
        assertEquals(6, hit.getTop());
        assertEquals(4, hit.getRight());
        assertEquals(6, hit.getBottom());
        assertEquals(4, hit.getLeft());
        tro.reset();

    }

    @Test
    public void advancedSuspendResumeTest() {
        ObservablePicture op = new ObservablePictureImpl(new PictureImpl(11, 11));
        Pixel pix = new ColorPixel(0.5, 0.5, 0.5);

        Region r1 = new RegionImpl(new Coordinate(0, 2), new Coordinate(8, 6));
        Region r2 = new RegionImpl(new Coordinate(2, 4), new Coordinate(10, 8));
        Region r3 = new RegionImpl(new Coordinate(4, 0), new Coordinate(6, 10));

        TestROIObserver tro1 = new TestROIObserver();
        TestROIObserver tro2 = new TestROIObserver();
        TestROIObserver tro3 = new TestROIObserver();

        op.registerROIObserver(tro1, r1);
        op.registerROIObserver(tro2, r2);
        op.registerROIObserver(tro3, r3);

        new Coordinate(1, 1);
        new Coordinate(1, 3);
        new Coordinate(9, 7);
        new Coordinate(5, 1);
        new Coordinate(7, 5);
        new Coordinate(5, 3);
        new Coordinate(5, 7);
        Coordinate all_hit = new Coordinate(5, 5);

        RegionHit hit;

        op.suspendObservable();
        op.setPixel(all_hit, pix);
        op.resumeObservable();

        assertEquals(1, tro1.getHitCount());
        hit = tro1.getHits()[0];
        assertEquals(5, hit.getTop());
        assertEquals(5, hit.getRight());
        assertEquals(5, hit.getBottom());
        assertEquals(5, hit.getLeft());

        assertEquals(1, tro2.getHitCount());
        hit = tro2.getHits()[0];
        assertEquals(5, hit.getTop());
        assertEquals(5, hit.getRight());
        assertEquals(5, hit.getBottom());
        assertEquals(5, hit.getLeft());

        assertEquals(1, tro3.getHitCount());
        hit = tro3.getHits()[0];
        assertEquals(5, hit.getTop());
        assertEquals(5, hit.getRight());
        assertEquals(5, hit.getBottom());
        assertEquals(5, hit.getLeft());

        tro1.reset();
        tro2.reset();
        tro3.reset();

        op.suspendObservable();
        op.setPixel(0, 0, pix);
        op.setPixel(10, 10, pix);
        op.resumeObservable();

        assertEquals(1, tro1.getHitCount());
        hit = tro1.getHits()[0];
        assertEquals(r1.getTop(), hit.getTop());
        assertEquals(r1.getRight(), hit.getRight());
        assertEquals(r1.getBottom(), hit.getBottom());
        assertEquals(r1.getLeft(), hit.getLeft());

        assertEquals(1, tro2.getHitCount());
        hit = tro2.getHits()[0];
        assertEquals(r2.getTop(), hit.getTop());
        assertEquals(r2.getRight(), hit.getRight());
        assertEquals(r2.getBottom(), hit.getBottom());
        assertEquals(r2.getLeft(), hit.getLeft());

        assertEquals(1, tro3.getHitCount());
        hit = tro3.getHits()[0];
        assertEquals(r3.getTop(), hit.getTop());
        assertEquals(r3.getRight(), hit.getRight());
        assertEquals(r3.getBottom(), hit.getBottom());
        assertEquals(r3.getLeft(), hit.getLeft());

        tro1.reset();
        tro2.reset();
        tro3.reset();

        op.suspendObservable();
        op.setPixel(1, 2, pix);
        op.setPixel(9, 3, pix);
        op.resumeObservable();

        assertEquals(1, tro1.getHitCount());
        hit = tro1.getHits()[0];
        assertEquals(2, hit.getTop());
        assertEquals(r1.getRight(), hit.getRight());
        assertEquals(3, hit.getBottom());
        assertEquals(1, hit.getLeft());

        assertEquals(0, tro2.getHitCount());

        assertEquals(1, tro3.getHitCount());
        hit = tro3.getHits()[0];
        assertEquals(2, hit.getTop());
        assertEquals(r3.getRight(), hit.getRight());
        assertEquals(3, hit.getBottom());
        assertEquals(r3.getLeft(), hit.getLeft());
    }

    @Test
    public void sameObserverDifferentObservablesTest() {
        ObservablePicture op1 = new ObservablePictureImpl(new PictureImpl(10, 10));
        ObservablePicture op2 = new ObservablePictureImpl(new PictureImpl(10, 10));

        Region r1 = new RegionImpl(new Coordinate(0, 0), new Coordinate(5, 5));
        Region r2 = new RegionImpl(new Coordinate(4, 4), new Coordinate(9, 9));

        TestROIObserver tro = new TestROIObserver();

        Pixel pix = new ColorPixel(0.5, 0.5, 0.5);

        op1.registerROIObserver(tro, r1);
        op2.registerROIObserver(tro, r2);

        op1.setPixel(5, 5, pix);
        assertEquals(1, tro.getHitCount());
        RegionHit hit = tro.getHits()[0];
        assertEquals(op1, hit.getHitPicture());
        tro.reset();

        op2.setPixel(5, 5, pix);
        assertEquals(1, tro.getHitCount());
        hit = tro.getHits()[0];
        assertEquals(op2, hit.getHitPicture());
        tro.reset();

        op1.setPixel(0, 0, pix);
        op2.setPixel(9, 9, pix);
        assertEquals(2, tro.getHitCount());
        hit = tro.getHits()[0];
        assertEquals(op1, hit.getHitPicture());
        assertEquals(0, hit.getTop());
        assertEquals(0, hit.getBottom());
        assertEquals(0, hit.getLeft());
        assertEquals(0, hit.getRight());
        hit = tro.getHits()[1];
        assertEquals(op2, hit.getHitPicture());
        assertEquals(9, hit.getTop());
        assertEquals(9, hit.getBottom());
        assertEquals(9, hit.getLeft());
        assertEquals(9, hit.getRight());

    }

    @Test
    public void chainedObservableTest() {
        Picture pa = new PictureImpl(10, 10);
        ObservablePicture opa = new ObservablePictureImpl(pa);

        TestROIObserver tro_a = new TestROIObserver();
        opa.registerROIObserver(tro_a, new RegionImpl(new Coordinate(4, 4), new Coordinate(6, 6)));

        Picture pb = new SubPictureImpl(opa, 4, 4, 3, 3);
        ObservablePicture opb = new ObservablePictureImpl(pb);
        TestROIObserver tro_b = new TestROIObserver();
        opb.registerROIObserver(tro_b, new RegionImpl(new Coordinate(1, 1), new Coordinate(1, 1)));

        Pixel pix = new ColorPixel(0.5, 0.5, 0.5);

        opb.setPixel(1, 1, pix);
        assertEquals(1, tro_a.getHitCount());
        RegionHit hit = tro_a.getHits()[0];
        assertEquals(5, hit.getTop());
        assertEquals(5, hit.getBottom());
        assertEquals(5, hit.getLeft());
        assertEquals(5, hit.getRight());

        assertEquals(1, tro_b.getHitCount());
        hit = tro_b.getHits()[0];
        assertEquals(1, hit.getTop());
        assertEquals(1, hit.getBottom());
        assertEquals(1, hit.getLeft());
        assertEquals(1, hit.getRight());
    }

    private boolean findInArray(ROIObserver[] found, ROIObserver observer) {
        for (ROIObserver o : found) {
            if (o == observer) {
                return true;
            }
        }
        return false;
    }

    private boolean compareRegions(Region a, Region b) {
        return ((a.getTop() == b.getTop()) && (a.getBottom() == b.getBottom()) && (a.getLeft() == b.getLeft())
                && (a.getRight() == b.getRight()));
    }

}
