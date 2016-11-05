package a7tests.jeroen;

import static org.junit.Assert.*;
import org.junit.Test;
import a7.*;

public class ObservablePictureTest2 {

    /*This test is created by following the description and expected output of Assignment 7
     * at http://www.cs.unc.edu/~kmp/comp401fall16/assignments/a7/fall16-a7.html under
     * "How ROIObserver Should Work".*/
    
    @Test
    public void howROIObserverShouldWorkTest(){
    	
    	//Suppose we have a single ObservablePicture object P that is 10 pixels wide and 10 pixels tall.
      	ObservablePicture p = new ObservablePictureImpl(new PictureImpl(10,10));
    	
      	//Suppose ROIObserver A is registered as an observer of P with the region of interest (1,1)->(5,5).
    	TestROIObserver a = new TestROIObserverImpl();
    	
    	//Suppose ROIObserver B is registered as an observer of P twice; once with the region of interest 
    	//(0,0)->(3,3) and once with the region (2,2)->(7,7).
    	TestROIObserver b = new TestROIObserverImpl();
    	
    	p.registerROIObserver(a, new RegionImpl(new Coordinate(1,1),new Coordinate(5,5)));
    	p.registerROIObserver(b, new RegionImpl(new Coordinate(0,0),new Coordinate(3,3)));
    	p.registerROIObserver(b, new RegionImpl(new Coordinate(2,2),new Coordinate(7,7)));
    	
    	/*If we have not called suspendObservable() on P, then each call to setPixel will cause 
    	 * any registered observers to be notified if the coordinates of the pixel intersect 
    	 * with the registered region of interest. In this case, the second argument to the 
    	 * notification method will be a region that encompasses the single changed pixel. So, 
    	 * for example, if we call setPixel on P at position (1,1), then A will be notified once
    	 *  with the region (1,1)->(1,1) as the second argument to the notification method and B 
    	 *  will also be notified once, again with the region (1,1)->(1,1) as the second 
    	 *  argument to the notification method.*/	
    	p.setPixel(1, 1, randomPixel());
    	assertEquals("Test 1 failed", a.getLastRegion().getTop(),1);
    	assertEquals("Test 1 failed", a.getLastRegion().getBottom(),1);
    	assertEquals("Test 1 failed", a.getLastRegion().getLeft(),1);
    	assertEquals("Test 1 failed", a.getLastRegion().getRight(),1);
    	assertEquals("Test 2 failed", b.getLastRegion().getTop(),1);
    	assertEquals("Test 2 failed", b.getLastRegion().getBottom(),1);
    	assertEquals("Test 2 failed", b.getLastRegion().getLeft(),1);
    	assertEquals("Test 2 failed", b.getLastRegion().getRight(),1);
    	
    	/*Similarly, if we call setPixel on P at position (2,2), then A will be notified once and 
    	 * B will be notified twice because the changed pixel is within both regions of interest that
    	 * B was registered with. In all three cases, the second argument to the notification method
    	 * is the region (2,2)->(2,2).*/	    	
    	p.setPixel(2, 2, randomPixel());
    	assertEquals("Test 3 failed", a.getLastRegion().getTop(),2);
    	assertEquals("Test 3 failed", a.getLastRegion().getBottom(),2);
    	assertEquals("Test 3 failed", a.getLastRegion().getLeft(),2);
    	assertEquals("Test 3 failed", a.getLastRegion().getRight(),2);
    	assertEquals("Test 3 failed", b.getLastRegion().getTop(),2);
    	assertEquals("Test 3 failed", b.getLastRegion().getBottom(),2);
    	assertEquals("Test 3 failed", b.getLastRegion().getLeft(),2);
    	assertEquals("Test 3 failed", b.getLastRegion().getRight(),2);
    	assertEquals("Test 3 failed", b.getPriorLastRegion().getTop(),2);
    	assertEquals("Test 3 failed", b.getPriorLastRegion().getBottom(),2);
    	assertEquals("Test 3 failed", b.getPriorLastRegion().getLeft(),2);
    	assertEquals("Test 3 failed", b.getPriorLastRegion().getRight(),2);
    	
    	/*If we call setPixel on P at position (8,8), no one is notified because that pixel position is 
    	 * outside of the registered regions of interest.*/
    	p.setPixel(8, 8, randomPixel());
    	assertEquals("Test 4 failed", a.getLastRegion().getTop(),2);
    	assertEquals("Test 4 failed", a.getLastRegion().getBottom(),2);
    	assertEquals("Test 4 failed", a.getLastRegion().getLeft(),2);
    	assertEquals("Test 4 failed", a.getLastRegion().getRight(),2);
    	assertEquals("Test 4 failed", b.getLastRegion().getTop(),2);
    	assertEquals("Test 4 failed", b.getLastRegion().getBottom(),2);
    	assertEquals("Test 4 failed", b.getLastRegion().getLeft(),2);
    	assertEquals("Test 4 failed", b.getLastRegion().getRight(),2);
    	assertEquals("Test 4 failed", b.getPriorLastRegion().getTop(),2);
    	assertEquals("Test 4 failed", b.getPriorLastRegion().getBottom(),2);
    	assertEquals("Test 4 failed", b.getPriorLastRegion().getLeft(),2);
    	assertEquals("Test 4 failed", b.getPriorLastRegion().getRight(),2);
    	
    	
    	/*Things are a bit trickier if we use suspendObservable() and resumeObservable(). In this case, 
    	 * the ObservablePicture object needs to keep track of the smallest region that encompasses all 
    	 * pixel changes between the time the suspendObservable() is called and the time when 
    	 * resumeObservable() is called. At the time that resumeObservable() is called, all registered 
    	 * observers with a region of interest that intersects the region of changes will be notified.
    	 * In this case, the second argument to the notification method is the intersection of the 
    	 * region encompassing the pixel changes and the registered region of interest.
    	 * For example, suppose we call suspendObservable() and then use setPixel() on P to change the 
    	 * following pixel positions: (2,1), (3,2), and (4,4).*/
    	p.suspendObservable();
    	p.setPixel(2, 1, randomPixel());
    	p.setPixel(3, 2, randomPixel());
    	p.setPixel(4, 4, randomPixel());
    	
    	/*At this point, the changed region is encompassed by the region (2,1)->(4,4). Now if we call 
    	 * resumeObservable(), A should be notified with region (2,1)->(4,4) because that is the 
    	 * intersection of the region that changed and its region of interest, namely (1,1)->(5,5). In 
    	 * this case, the entire encompassing region just happens to be within the region of interest 
    	 * for A.*/
    	p.resumeObservable();
    	assertEquals("Test 5 failed", a.getLastRegion().getTop(),1);
    	assertEquals("Test 5 failed", a.getLastRegion().getBottom(),4);
    	assertEquals("Test 5 failed", a.getLastRegion().getLeft(),2);
    	assertEquals("Test 5 failed", a.getLastRegion().getRight(),4);
    	
    	/*B, however, should be notified twice; once with the region (2,1)->(3,3) as the intersection 
    	 * of the changed region and the region of interest (0,0)->(3,3) and once with the region (2,2)->(4,4) 
    	 * as the intersection of the changed region and the region of interest (2,2)->(7,7).*/
    	assertEquals("Test 6 failed", b.getPriorLastRegion().getTop(),1);
    	assertEquals("Test 6 failed", b.getPriorLastRegion().getBottom(),3);
    	assertEquals("Test 6 failed", b.getPriorLastRegion().getLeft(),2);
    	assertEquals("Test 6 failed", b.getPriorLastRegion().getRight(),3);
    	assertEquals("Test 6 failed", b.getLastRegion().getTop(),2);
    	assertEquals("Test 6 failed", b.getLastRegion().getBottom(),4);
    	assertEquals("Test 6 failed", b.getLastRegion().getLeft(),2);
    	assertEquals("Test 6 failed", b.getLastRegion().getRight(),4);
    }

    /* Returns a random pixel */
    private static Pixel randomPixel() {
	return new ColorPixel(Math.random(), Math.random(), Math.random());
    }
}
