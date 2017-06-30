package com.mmks.sgbusstops.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BusStop implements Serializable{
	private static final long serialVersionUID = 2662447352446875465L;
	private String stopId = "";
	private String name = "";
	private String latit = "";
	private String longit = "";
	private List<String> serviceNos = new ArrayList<String>();

	public String getStopId() {
		return stopId;
	}

	public void setStopId(String stopId) {
		this.stopId = stopId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLatit() {
		return latit;
	}

	public void setLatit(String latit) {
		this.latit = latit;
	}

	public String getLongit() {
		return longit;
	}

	public void setLongit(String longit) {
		this.longit = longit;
	}

	public List<String> getServiceNos() {
		return serviceNos;
	}

	public void setServiceNos(List<String> serviceNos) {
		this.serviceNos = serviceNos;
	}

	@Override
	public String toString() {
		return stopId + " : " + name;
	}		
}