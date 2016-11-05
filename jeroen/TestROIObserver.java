package a7tests.jeroen;

import a7.*;

public interface TestROIObserver extends ROIObserver {

	public ObservablePicture getLastPicture();
	public Region getLastRegion();
	public Region getPriorLastRegion();
}
