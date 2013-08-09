package com.wsd.android.gps;

import android.location.Location;

public interface GPSTrackerImplementation {
	public void onLocationChange(GPSTracker tracker, Location location);
}
