package com.mmks.sgbusstops.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mmks.sgbusstops.R;
import com.mmks.sgbusstops.beans.BusService;
import com.mmks.sgbusstops.beans.BusStop;
import com.mmks.sgbusstops.service.BusDAO;
import com.mmks.sgbusstops.service.BusServicesAdapter;

public class BusStopServicesActivity extends Activity {

	List serviceNums = new ArrayList();
	BusDAO busDAO = BusDAO.getBusDAO(this);
	BusStop busStop;
	List<BusService> services = new ArrayList();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busstopservices);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {		    
		    busStop = (BusStop) extras.getSerializable("busStop");
		    services =  (List<BusService>) extras.getSerializable("services");
		    TextView tw = (TextView) findViewById(R.id.servicemsg);
		    serviceNums = busStop.getServiceNos();		    
		    tw.setText(busStop.toString());
		}
		
		ListView lv = (ListView) findViewById(R.id.bus_services);
		lv.setTextFilterEnabled(true);
		lv.setFastScrollEnabled(true);

		BusServicesAdapter arrayAdapter = new BusServicesAdapter(this,android.R.layout.simple_list_item_1, services);
		lv.setAdapter(arrayAdapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {				
				ListView lv2 = (ListView) findViewById(R.id.bus_services);
				BusService currService = (BusService) lv2.getItemAtPosition(position);
				
				BusService selService = busDAO.getBusService(currService.getServiceNo());				
				Bundle b = new Bundle();
	        	b.putSerializable("busService", selService);
	        	b.putSerializable("busStop", busStop);
				Intent i = new Intent(BusStopServicesActivity.this, BusServiceActivity.class);
	        	i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	i.putExtras(b);
	        	startActivity(i);
			}
		});
	}

	public void showBusStopMap(View v){
		Intent i = new Intent(BusStopServicesActivity.this,BusStopMapActivity.class);
		Bundle b = new Bundle();
		b.putSerializable("busstop", busStop);
		i.putExtras(b);
		startActivity(i);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity2, menu);
		return true;
	}	
}
