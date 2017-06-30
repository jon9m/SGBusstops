package com.mmks.sgbusstops.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.mmks.sgbusstops.R;
import com.mmks.sgbusstops.beans.BusService;
import com.mmks.sgbusstops.service.BusDAO;
import com.mmks.sgbusstops.service.SimpleFilterableBusServiceAdapter;

public class AllBusServicesActivity extends Activity {

	private BusDAO busDAO;
	private List<BusService> servicesCache;
	SimpleFilterableBusServiceAdapter<String> arrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_bus_services);		
		
		busDAO = BusDAO.getBusDAO(this);
		ListView lv = (ListView) findViewById(R.id.all_services_view);	
		servicesCache = busDAO.getAllBusService();	

		lv.setTextFilterEnabled(true);
		lv.setFastScrollEnabled(true);

		arrayAdapter = new SimpleFilterableBusServiceAdapter<String>(this, android.R.layout.simple_list_item_1, servicesCache);
		lv.setAdapter(arrayAdapter);
		
		EditText textView = (EditText) findViewById(R.id.all_servicename);
		textView.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				AllBusServicesActivity.this.arrayAdapter.getFilter().filter(cs);
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

				ListView lv2 = (ListView) findViewById(R.id.all_services_view);
				BusService service = (BusService) lv2.getItemAtPosition(position);				
				
				if (service != null) {				
					Bundle b = new Bundle();										
					b.putSerializable("busService", service);					
					Intent i = new Intent(AllBusServicesActivity.this, BusServiceActivity.class);
					i.putExtras(b);
					startActivity(i);
				}
			}
		});
		
		/*lv.setOnScrollListener(new OnScrollListener() {			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				view.requestLayout();
			}			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {				
				view.requestLayout();
			}
		});*/
		
		/*lv.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return (event.getAction() == MotionEvent.ACTION_CANCEL);
			}
		});*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.all_bus_services, menu);
		return true;
	}

	public void clearAllserviceText(View v) {
		((EditText) findViewById(R.id.all_servicename)).setText("");
	}
}
