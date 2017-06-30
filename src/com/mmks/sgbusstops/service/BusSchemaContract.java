package com.mmks.sgbusstops.service;

import android.provider.BaseColumns;

public final class BusSchemaContract {

	public BusSchemaContract() {
	}

	public static abstract class BusStopsEntry implements BaseColumns {
		public static final String TABLE_NAME = "BUS_STOPS";
		public static final String COL_STOPID = "STOPID";
		public static final String COL_NAME = "NAME";
		public static final String COL_LATIT = "LATIT";
		public static final String COL_LONGIT = "LONGIT";
	}

	public static abstract class BusNumbersAtStop implements BaseColumns {
		public static final String TABLE_NAME = "BUS_NUMS_STOPS";
		public static final String COL_STOPID = "STOPID";
		public static final String COL_SERVICES = "SERVICES";
	}

	public static abstract class BusServices implements BaseColumns {
		public static final String TABLE_NAME = "BUS_SERVICES";
		public static final String COL_SERVICENO = "SERVICENO";
		public static final String COL_OPERATOR = "OPERATOR";
		public static final String COL_ROUTES = "ROUTES";
		public static final String COL_TYPE = "TYPE";
		public static final String COL_FULLNAME = "FULLNAME";
		public static final String COL_FROMNAME = "FROMNAME";
		public static final String COL_TONAME = "TONAME";
	}

	public static abstract class BusServiceRoutes implements BaseColumns {
		public static final String TABLE_NAME = "BUS_SERVICE_ROUTES";
		public static final String COL_SERVICENO = "SERVICENO";
		public static final String COL_STOPS = "STOPS";
		public static final String COL_ROUTE = "ROUTE";
	}
}