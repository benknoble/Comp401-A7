package a7tests.david3;

import static org.junit.Assert.*;

import org.junit.Test;

import a7.*;

import java.util.*;

public class ObservablePictures {

    private static Coordinate c00 = new Coordinate(0, 0);
    private static Coordinate c22 = new Coordinate(2, 2);
    private static Coordinate c33 = new Coordinate(3, 3);
    private static Region big = new RegionImpl(c00, c33);
    private static Region tiny = new RegionImpl(c22, c33);

    private static ROIObserver obsA = new ROIObserver() {
	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
	    if (picture == null || changed_region == null)
		fail("params null");
	}
    };

    private static ROIObserver obsB = new ROIObserver() {
	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
	    if (picture == null || changed_region == null)
		fail("params null");
	}
    };

    private static ROIObserver observableObserver = new ROIObserver() {
	@Override
	public void notify(ObservablePicture picture, Region changed_region) {
	    System.out.println("observer notified");
	}
    };

    @Test
    public void testRegisterROIObserver() {
	ObservablePicture p = new ObservablePictureImpl(new PictureImpl(3, 3));
	try {
	    p.registerROIObserver(null, big);
	    fail("didn't throw for null observer");
	} catch (IllegalArgumentException e) {
	}

	try {
	    p.registerROIObserver(obsA, null);
	    fail("didn't throw for null region");
	} catch (IllegalArgumentException e) {
	}

	try {
	    p.registerROIObserver(obsA, big);
	    assertTrue("observer not registered",
		    p.findROIObservers(big).length == 1 && p.findROIObservers(big)[0] == obsA);
	} catch (Exception e) {
	    fail("legal registration threw error: " + e.getMessage());
	}
    }

    @Test
    public void testUnregisterROIObservers() {
	ObservablePicture p = new ObservablePictureImpl(new PictureImpl(3, 3));
	p.registerROIObserver(obsA, big);
	p.registerROIObserver(obsB, big);

	try {
	    p.unregisterROIObservers(null);
	    fail("didn't throw for null region");
	} catch (IllegalArgumentException e) {
	}

	try {
	    p.unregisterROIObservers(tiny);
	} catch (Exception e) {
	    fail("legal unregistration of unregistered observer threw: " + e.getMessage());
	}

	try {
	    p.unregisterROIObservers(big);
	    assertTrue("obsA and obsB not unregistered", p.findROIObservers(big).length == 0);
	} catch (Exception e) {
	    fail("legal unregistration of multiple observers by region threw error: " + e.getMessage());
	}
    }

    @Test
    public void testUnregisterROIObserver() {
	ObservablePicture p = new ObservablePictureImpl(new PictureImpl(3, 3));
	p.registerROIObserver(obsA, big);
	p.registerROIObserver(obsA, tiny);

	try {
	    p.unregisterROIObserver(null);
	    fail("didn't throw for null observer");
	} catch (IllegalArgumentException e) {
	}

	try {
	    p.unregisterROIObserver(obsB);
	} catch (IllegalArgumentException e) {
	    fail("legal unregistration of unregistered observer threw: " + e.getMessage());
	}

	try {
	    p.unregisterROIObserver(obsA);
	    assertTrue("obsA not unregistered",
		    p.findROIObservers(big).length == 0 && p.findROIObservers(tiny).length == 0);
	} catch (Exception e) {
	    fail("legal unregistration of observer threw error: " + e.getMessage());
	}
    }

    @Test
    public void testFindROIObservers() {
	ObservablePicture p = new ObservablePictureImpl(new PictureImpl(3, 3));
	p.registerROIObserver(obsA, big);
	p.registerROIObserver(obsA, tiny);
	p.registerROIObserver(obsB, big);

	try {
	    p.findROIObservers(null);
	    fail("didn't throw for null region");
	} catch (IllegalArgumentException e) {
	}

	try {
	    ROIObserver[] r = p.findROIObservers(big);
	    assertTrue("observers not found",
		    r != null && r.length == 3 && Arrays.asList(r).contains(obsA) && Arrays.asList(r).contains(obsB));
	} catch (Exception e) {
	    fail("finding observers threw: " + e.getMessage());
	}

	try {
	    ROIObserver[] r = p.findROIObservers(tiny);
	    assertTrue("observer not found",
		    r != null && r.length == 3 && Arrays.asList(r).contains(obsA) && Arrays.asList(r).contains(obsB));
	} catch (Exception e) {
	    fail("finding observers threw: " + e.getMessage());
	}
    }

    @Test
    public void testSuspendObservable() {
	ObservablePicture p = new ObservablePictureImpl(new PictureImpl(3, 3));
	p.registerROIObserver(observableObserver, tiny);
	p.suspendObservable();
	p.setPixel(c22, new GrayPixel(.1));
	// Check for no output "observer notified"
    }

    @Test
    public void testResumeObservable() {
	ObservablePicture p = new ObservablePictureImpl(new PictureImpl(3, 3));
	p.registerROIObserver(observableObserver, tiny);
	p.suspendObservable();
	p.setPixel(c22, new GrayPixel(.1));
	p.resumeObservable();
	// check for output "observer notified"
    }

}
