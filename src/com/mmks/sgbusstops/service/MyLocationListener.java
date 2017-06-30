package com.mmks.sgbusstops.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MyLocationListener implements LocationListener {

	Context context;
	private double longitude;
	private double latitude;
	private static MyLocationListener myLocationListener;	

	private MyLocationListener(Context context) {
		super();
		this.context = context;
	}
	
	public static MyLocationListener getInstance(Context context) {
		if (myLocationListener == null) {
			myLocationListener = new MyLocationListener(context);
		}
		return myLocationListener;
	}

	@Override
	public void onLocationChanged(Location loc) {
		//Toast.makeText(context, "Location changed: Lat: " + loc.getLatitude() + " Lng: "
		//				+ loc.getLongitude(), Toast.LENGTH_SHORT).show();

		longitude = loc.getLongitude();
		latitude = loc.getLatitude();
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
