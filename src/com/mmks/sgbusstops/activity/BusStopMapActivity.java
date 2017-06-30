package com.mmks.sgbusstops.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mmks.sgbusstops.R;
import com.mmks.sgbusstops.beans.BusStop;

public class BusStopMapActivity extends Activity {

	static LatLng STOP_LOC = new LatLng(1.2911783783, 103.861895421);
	private GoogleMap map;
	BusStop stop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busstopsmap);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			stop = (BusStop) extras.getSerializable("busstop");
			if (stop != null) {
				STOP_LOC = new LatLng(Double.parseDouble(stop.getLatit()),
						Double.parseDouble(stop.getLongit()));
			}
		}

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		if (map != null) {
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			map.setMyLocationEnabled(true);
			Marker marker = map.addMarker(new MarkerOptions().position(STOP_LOC).title(stop.getName()));
			marker.showInfoWindow();
		}

		// Move the camera instantly to stop with a zoom of 15.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(STOP_LOC, 21));
		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
}