package com.mmks.sgbusstops.service;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mmks.sgbusstops.R;
import com.mmks.sgbusstops.beans.BusService;
import com.mmks.sgbusstops.beans.BusStop;

public class SimpleFilterableBusServiceAdapter<ObjectType> extends
		FilterableAdapter<ObjectType, String> {

	Context context;
	int resourceId;
	List<BusService> objects;

	public SimpleFilterableBusServiceAdapter(Context context, int resourceId,
			List<BusService> servicesCache) {
		super(context, resourceId, (List<ObjectType>) servicesCache);

		this.context = context;
		this.resourceId = resourceId;
		this.objects = (List<BusService>) servicesCache;
	}

	@Override
	protected String prepareFilter(CharSequence seq) {

		/*
		 * The object we return here will be passed to passesFilter() as
		 * constraint.* This method is called only once per filter run. The same
		 * constraint is* then used to decide upon all objects in the data set.
		 */

		return seq.toString();
	}

	@Override
	protected boolean passesFilter(ObjectType object, String constraint) {
		String repr = ((BusService) object).toString().toLowerCase();

		if (repr.contains(constraint.trim().toLowerCase())) {
			return true;
		}
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.serviceitem_layout, null);

		TextView serviceId = (TextView) vi.findViewById(R.id.serviceid);
		TextView fullName = (TextView) vi.findViewById(R.id.fullname);
		TextView serviceType = (TextView) vi.findViewById(R.id.servicetype);
		
		BusService service = (BusService) getItem(position);

		serviceId.setText(service.getServiceNo());
		fullName.setText(service.getFullName());

		String loop = "";
		String routeNos = service.getRoute();
		if ((routeNos != null) && (routeNos.trim().contains("1"))) {
			loop = context.getResources().getString(R.string.looping);
		}
		serviceType.setText(service.getOperater().toUpperCase() + "   " + loop);
		return vi;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
}