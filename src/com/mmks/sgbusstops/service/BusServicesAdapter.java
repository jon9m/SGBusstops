package com.mmks.sgbusstops.service;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mmks.sgbusstops.R;
import com.mmks.sgbusstops.beans.BusService;

public class BusServicesAdapter extends ArrayAdapter<BusService> {

	private static LayoutInflater inflater;

	private Context context;
	private List<BusService> objects;

	public BusServicesAdapter(Context context, int resourceId,
			List<BusService> objects) {
		super(context, resourceId, objects);
		this.context = context;
		this.objects = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		inflater = (LayoutInflater) (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.serviceitem_layout, null);

		TextView serviceId = (TextView) vi.findViewById(R.id.serviceid);
		TextView fullName = (TextView) vi.findViewById(R.id.fullname);
		TextView serviceType = (TextView) vi.findViewById(R.id.servicetype);

		BusService service = objects.get(position);

		serviceId.setText(service.getServiceNo());
		fullName.setText(service.getFullName());
		
		String loop = "";
		String routeNos = service.getRoute();
		if ((routeNos != null) && (routeNos.trim().contains("1"))) {
			loop = "[LOOPING]";
		}
		serviceType.setText(service.getOperater().toUpperCase() + "   " + loop);

		return vi;
	}
}