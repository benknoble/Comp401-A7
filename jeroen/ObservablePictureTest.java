package a7test;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import org.junit.Test;

import a7.*;

public class ObservablePictureTest {

	@Test
	public void JeroensA7Test() {

		/*UNC Chapel Hill COMP401 assignment 7 jUnit tests
		 * by Jeroen. www.jersoe.com
		 * 
		 * Let's start this testing adventure by explaining what this test is up to.
		 * See http://imgur.com/9Pj6pNT and fear my 1337 paint skills.
		 * Now that that's clear, we'll create some instances of Coordinates, 
		 * Regions, Observers, and so on...*/

		ObservablePicture p = new ObservablePictureImpl(new PictureImpl(10,10));

		Coordinate c1 = new Coordinate(0,0);
		Coordinate c2 = new Coordinate(4,4);
		Coordinate c3 = new Coordinate(4,1);
		Coordinate c4 = new Coordinate(7,3);
		Coordinate c5 = new Coordinate(8,3);
		Coordinate c6 = new Coordinate(6,8);
		Coordinate c7 = new Coordinate(1,8);	
		Coordinate c8 = new Coordinate(3,6);

		Region r1 = new RegionImpl(c1,c2);
		Region r2 = new RegionImpl(c3,c4);
		Region r3 = new RegionImpl(c5,c6);
		Region r4 = new RegionImpl(c7,c8);

		TestROIObserver o1 = new TestROIObserverImpl();
		TestROIObserver o2 = new TestROIObserverImpl();
		TestROIObserver o3 = new TestROIObserverImpl();
		TestROIObserver o4 = new TestROIObserverImpl();

		p.registerROIObserver(o1, r1);
		p.registerROIObserver(o2, r2);
		p.registerROIObserver(o3, r3);
		p.registerROIObserver(o4, r4);

		/*Let's first change the pixel at (0,0). Observer 1 should get notified.*/
		p.setPixel(c1, randomPixel());
		assertEquals("ROIObserver o1 didn't get called, but a pixel in it's region was changed.", o1.getLastRegion(),r1);
		assertEquals("ROIObserver o1 did not get called with the correct parameters.", o1.getLastPicture(),p);

		/*Let's change a pixel that's in r1 and r2*/
		p.setPixel(4,3, randomPixel());
		assertEquals("ROIObserver o1 didn't get called, but a pixel in it's region was changed.", o1.getLastRegion(),r1);
		assertEquals("ROIObserver o2 didn't get called, but a pixel in it's region was changed.", o2.getLastRegion(),r2);

		/*Let's see which observers are interested in Region r3. That should be o2 and o3.*/
		assertTrue("findROIObservers() broken.", Arrays.asList(p.findROIObservers(r3)).contains(o2));
		assertTrue("findROIObservers() broken.", Arrays.asList(p.findROIObservers(r3)).contains(o3));

		/*Suspend notifying*/
		p.suspendObservable();

		/*Now make some changed to a pixel at 2,7, and at 8,5.*/
		p.setPixel(2, 7, randomPixel());
		p.setPixel(8, 5, randomPixel());

		/*Now the smallest region that contains all changed pixels since we suspended notifying
		 * runs from 5,2 to 8,7. o2, and o3 should get notified once we resume, and the region
		 * passed on to them should be equal to the intersect of their region of interest, and 
		 * the smallest region that contains all changed pixels. This means that for o3, this is
		 * a region from 6,5 to 8,7 and for o4 from 2,6 to 3,7.*/
		p.resumeObservable();
		assertEquals("resumeObserverable() didn't notifiy observers with the right region",o3.getLastRegion().getLeft(),6);
		assertEquals("resumeObserverable() didn't notifiy observers with the right region",o3.getLastRegion().getTop(),5);
		assertEquals("resumeObserverable() didn't notifiy observers with the right region",o3.getLastRegion().getRight(),8);
		assertEquals("resumeObserverable() didn't notifiy observers with the right region",o3.getLastRegion().getBottom(),7);

		assertEquals("resumeObserverable() didn't notifiy observers with the right region",o4.getLastRegion().getLeft(),2);
		assertEquals("resumeObserverable() didn't notifiy observers with the right region",o4.getLastRegion().getTop(),6);
		assertEquals("resumeObserverable() didn't notifiy observers with the right region",o4.getLastRegion().getRight(),3);
		assertEquals("resumeObserverable() didn't notifiy observers with the right region",o4.getLastRegion().getBottom(),7);

		/*Unregister all observers that are watching the top half of the picture*/
		p.unregisterROIObservers(new RegionImpl(c1, new Coordinate(9,4)));

		/*The only registered observer at this point should be o4*/
		assertTrue("unregisterROIObservers failed",Arrays.asList(p.findROIObservers(new RegionImpl(c1,new Coordinate(9,9)))).size()==1);
		assertEquals("unregisterROIObservers failed",p.findROIObservers(new RegionImpl(c1,new Coordinate(9,9)))[0],o4);
	}

	/*Returns a random pixel*/
	private static Pixel randomPixel(){
		return new ColorPixel(Math.random(), Math.random(),Math.random());
	}
}
