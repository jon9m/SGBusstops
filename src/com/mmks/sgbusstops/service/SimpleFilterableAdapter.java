package com.mmks.sgbusstops.service;

import java.util.List;

import com.mmks.sgbusstops.beans.BusStop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class SimpleFilterableAdapter<ObjectType> extends
		FilterableAdapter<ObjectType, String> {

	Context context;
	int resourceId;
	List<BusStop> objects;

	public SimpleFilterableAdapter(Context context, int resourceId,
			List<ObjectType> objects) {
		super(context, resourceId, objects);

		this.context = context;
		this.resourceId = resourceId;
		this.objects = (List<BusStop>) objects;
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
		String repr = ((BusStop) object).toString().toLowerCase();

		if (repr.contains(constraint.trim().toLowerCase())) {
			return true;
		}
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//Override to customize list view
		return super.getView(position, convertView, parent);

	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

}