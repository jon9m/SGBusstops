package com.mmks.sgbusstops.activity;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.mmks.sgbusstops.R;
import com.mmks.sgbusstops.beans.BusStop;
import com.mmks.sgbusstops.service.BusDAO;
import com.mmks.sgbusstops.service.SimpleFilterableAdapter;

public class BusStopsActivity extends Activity {

	private BusDAO busDAO;
	private List busCache;
	SimpleFilterableAdapter<String> arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busstops);
		
		busDAO = BusDAO.getBusDAO(this);
		ListView lv = (ListView) findViewById(R.id.listView1);	
		busCache = busDAO.getAllBuseServices("a");	

		lv.setTextFilterEnabled(true);
		lv.setFastScrollEnabled(true);

		arrayAdapter = new SimpleFilterableAdapter<String>(this,
				android.R.layout.simple_list_item_1, busCache);
		lv.setAdapter(arrayAdapter);
		
		EditText textView = (EditText) findViewById(R.id.name);
		textView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				BusStopsActivity.this.arrayAdapter.getFilter().filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				ListView lv2 = (ListView) findViewById(R.id.listView1);
				BusStop stop = (BusStop) lv2.getItemAtPosition(position);
				
				if(stop != null){
					List serviceIds = stop.getServiceNos();
					List services = busDAO.getBusServices((String[]) serviceIds.toArray());

					Bundle b = new Bundle();
					b.putSerializable("busStop", stop);
					b.putSerializable("services", (Serializable) services);
					Intent i = new Intent(BusStopsActivity.this,BusStopServicesActivity.class);
					i.putExtras(b);
					startActivity(i);
				}
			}
		});				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void clear(View v) {
		((EditText) findViewById(R.id.name)).setText("");
	}
}