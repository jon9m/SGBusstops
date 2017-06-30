package com.mmks.sgbusstops.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.mmks.sgbusstops.R;
import com.mmks.sgbusstops.service.BusDAO;
import com.mmks.sgbusstops.service.MyLocationListener;

public class HomeActivity extends Activity {
	
	private MyLocationListener locationListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		BusDAO.getBusDAO(this);
		
		// Get current location
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);		
		boolean gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		locationListener = MyLocationListener.getInstance(this);
		try {
			if (locationManager != null) {
				if(gps_enabled){
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,locationListener);
				}else{
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10,locationListener);
				}
			}
		} catch (Exception e) {

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public void showBusStopsView(View v) {
		Intent i = new Intent(HomeActivity.this, BusStopsActivity.class);
		startActivity(i);
	}

	public void showNearLocations(View v) {
		Intent i = new Intent(HomeActivity.this, NearestLocationsMapActivity.class);
		startActivity(i);
	}
	
	public void showAllBusServices(View v) {
		Intent i = new Intent(HomeActivity.this, AllBusServicesActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	public void showAbout(View v) {
		Intent i = new Intent(HomeActivity.this, About.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	public void showSettings(View v) {
		Intent i = new Intent(HomeActivity.this, SettingsActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}