package com.mmks.sgbusstops.activity;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mmks.sgbusstops.R;
import com.mmks.sgbusstops.beans.BusRoute;
import com.mmks.sgbusstops.beans.BusService;
import com.mmks.sgbusstops.beans.BusStop;
import com.mmks.sgbusstops.service.BusDAO;

public class BusRouteMapActivity extends Activity {

	static LatLng STOP_LOC = new LatLng(1.2911783783, 103.861895421);
	private GoogleMap map;
	BusService service;
	BusDAO busDAO;
	BusStop busStop;
	LatLng CURR_LOC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_route_map);
		
		PolylineOptions redLine = new PolylineOptions().width(2).color(Color.RED).width(5);
		PolylineOptions mgntLine = new PolylineOptions().width(2).color(Color.MAGENTA).width(5);

		busDAO = BusDAO.getBusDAO(this);		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			service = (BusService) extras.getSerializable("busService");
			busStop = (BusStop) extras.getSerializable("busStop");
			if(service != null){
				setTitle(service.getServiceNo() + "-" + service.getFullName());
			}
		}

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.routemap)).getMap();
		if (map != null) {
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			map.setMyLocationEnabled(true);
			
			if (busStop != null) {
				CURR_LOC = new LatLng(Double.parseDouble(busStop.getLatit()),
						Double.parseDouble(busStop.getLongit()));
				
				Marker marker = map.addMarker(new MarkerOptions().position(CURR_LOC).title(busStop.getName()));
				marker.showInfoWindow();
			}

			if (service != null) {
				List routeLocs = busDAO.getBusServiceRoute(service.getServiceNo());
				for (int i = 0; i < routeLocs.size(); i++) {
					BusRoute routeTemp = (BusRoute) routeLocs.get(i);					
					if (routeTemp != null) {
						try {
							JSONArray obj = (JSONArray) new JSONParser().parse(routeTemp.getLongsNLangs());
							if (obj != null) {
								for (int j = 0; j < obj.size(); j++) {									
									
									Object object = obj.get(j);
									
									if (object != null) {
										String arr[] = ((String) object).split(",");
										STOP_LOC = new LatLng(
												Double.parseDouble(arr[0]),
												Double.parseDouble(arr[1]));
										
										if ((i == 0) && (j == 0)) {
											map.addMarker(new MarkerOptions()
													.position(STOP_LOC).title(service.getFullName())
													.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
										} else {
											if ((i == 0) && (j == (obj.size() - 1))) {
												map.addMarker(new MarkerOptions()
														.position(STOP_LOC).title(service.getFullName())
														.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
											}
										}
										if (i == 0) {
											redLine.add(STOP_LOC);
										} else {
											mgntLine.add(STOP_LOC);
										}
									}									
								}																	
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}				
				map.addPolyline(redLine);
				map.addPolyline(mgntLine);				
			} else {
				Marker marker = map.addMarker(new MarkerOptions().position(STOP_LOC).title(service.getFullName()));
			}
			// Move the camera instantly to stop with a zoom of 15.
			if(CURR_LOC == null){
				CURR_LOC = STOP_LOC;
			}			
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(CURR_LOC, 21));
			// Zoom in, animating the camera.
			map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		}				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bus_route_map, menu);
		return true;
	}
}
