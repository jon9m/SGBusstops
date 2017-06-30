package com.mmks.sgbusstops.activity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mmks.sgbusstops.R;
import com.mmks.sgbusstops.beans.BusStop;
import com.mmks.sgbusstops.service.BusDAO;
import com.mmks.sgbusstops.service.MyLocationListener;

public class NearestLocationsMapActivity extends Activity {

	BusDAO busDAO;	
	LatLng STOP_LOC;	
	List nearestLocations;
	private GoogleMap map;
	private Context context;
	Map locationsMap = new HashMap();
	private MyLocationListener locationListener;
	static LatLng DEF_LOC = new LatLng(1.2911783783, 103.861895421);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearest_locations_map);
		context = this;
		
		locationListener = MyLocationListener.getInstance(this);
		
		busDAO = BusDAO.getBusDAO(this);
		nearestLocations = busDAO.findNearestLocations(locationListener.getLongitude(), locationListener.getLatitude());

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.nearlocations)).getMap();
		if (map != null) {
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			map.setMyLocationEnabled(true);	
			
			//Market title click
			map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
		        @Override
		        public void onInfoWindowClick(Marker marker) {
		        	BusStop stop = (BusStop) locationsMap.get(marker.getId());
					if (stop != null) {
						List serviceIds = stop.getServiceNos();
						List services = busDAO.getBusServices((String[]) serviceIds.toArray());
						Bundle b = new Bundle();
						b.putSerializable("busStop", stop);
						b.putSerializable("services", (Serializable) services);
						Intent i = new Intent(NearestLocationsMapActivity.this,BusStopServicesActivity.class);
						i.putExtras(b);
						startActivity(i);
					}
		        }
		    });
		}
		
		locationsMap.clear();
		for(int i=0; i<nearestLocations.size(); i++){
			BusStop stop = (BusStop) nearestLocations.get(i);
			Marker marker = map.addMarker(new MarkerOptions().position(
					new LatLng(Double.parseDouble(stop.getLatit()), Double.parseDouble(stop.getLongit()))).title(
					stop.toString()));
			marker.setSnippet(getResources().getString(R.string.tapbusservices));
			locationsMap.put(marker.getId(), stop);
		}
				
		STOP_LOC = new LatLng(locationListener.getLatitude(),
				locationListener.getLongitude());
		Marker marker = map.addMarker(new MarkerOptions()
				.position(STOP_LOC)
				.title(getResources().getString(R.string.you))
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		marker.showInfoWindow();

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(STOP_LOC, 21));
		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nearest_locations_map, menu);
		return true;
	}
}
