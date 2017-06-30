package com.mmks.sgbusstops.beans;

import java.io.Serializable;

public class BusRoute implements Serializable {

	private static final long serialVersionUID = -8752118643748498050L;

	private String serviceId;
	private String stops;
	private String longsNLangs;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getStops() {
		return stops;
	}

	public void setStops(String stops) {
		this.stops = stops;
	}

	public String getLongsNLangs() {
		return longsNLangs;
	}

	public void setLongsNLangs(String longsNLangs) {
		this.longsNLangs = longsNLangs;
	}
}
