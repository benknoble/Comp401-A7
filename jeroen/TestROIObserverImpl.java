package a7tests.jeroen;

import a7.*;

public class TestROIObserverImpl implements TestROIObserver {
	private ObservablePicture _lastPicture;
	private Region _lastRegion;
	private Region _priorLastRegion;
	
	@Override
	/*Outputs some text and sets the _lastPicture and _lastRegion fields to parameters
	 * for jUnit testing.*/
	public void notify(ObservablePicture picture, Region changed_region) {
		System.out.println("Hey you guys, "+this+" here! I just got a call from "
				+picture+"."); 
		System.out.println("Something happened in the region with coordinates "
				+changed_region.getLeft()+","+changed_region.getTop()+
				" and "+changed_region.getRight()+","+changed_region.getBottom());
		_lastPicture=picture;
		_priorLastRegion=_lastRegion;
		_lastRegion=changed_region;
	}
	
	/*Returns the picture from the last notification*/
	public ObservablePicture getLastPicture(){
		return _lastPicture;
	}
	
	/*Returns the region from the last notification*/
	public Region getLastRegion(){
		return _lastRegion;
	}

	@Override
	public Region getPriorLastRegion() {
		return _priorLastRegion;
	}
}
