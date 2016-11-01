package a7test;

import a7.*;

public interface TestROIObserver extends ROIObserver {

	public ObservablePicture getLastPicture();
	public Region getLastRegion();
}
