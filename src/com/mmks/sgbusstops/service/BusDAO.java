package com.mmks.sgbusstops.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import com.mmks.sgbusstops.beans.BusRoute;
import com.mmks.sgbusstops.beans.BusService;
import com.mmks.sgbusstops.beans.BusStop;

public class BusDAO {
	private static SQLiteDatabase sqlLitedb;
	private BusDatabase busDatabase;
	private static BusDAO busDAO;
	private Context context;

	public BusDAO(Context context) {
		this.context = context;
		try {
			busDatabase = new BusDatabase(context);
			sqlLitedb = busDatabase.getReadableDatabase();			
		} catch (Exception e) {
			Log.d("EXCEPTION ", e.getMessage());
		}
	}

	public static BusDAO getBusDAO(Context context) {
		if (busDAO == null) {
			busDAO = new BusDAO(context);
		}
		return busDAO;
	}
	
	public List findNearestLocations(double longit, double latit){
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		float range = preferences.getFloat("loc_range", 0.006f);
				
		double cuadras = (double)(0.006) ;
		
		if(range != 0){
			cuadras = range;
		}
		
		List busStops = new ArrayList();
		
		StringBuffer sql = new StringBuffer("select ")
		.append(BusSchemaContract.BusStopsEntry.TABLE_NAME)
		.append(".")
		.append(BusSchemaContract.BusStopsEntry.COL_STOPID)
		.append(", ")
		.append(BusSchemaContract.BusStopsEntry.TABLE_NAME)
		.append(".")
		.append(BusSchemaContract.BusStopsEntry.COL_NAME)
		.append(", ")
		.append(BusSchemaContract.BusStopsEntry.TABLE_NAME)
		.append(".")
		.append(BusSchemaContract.BusStopsEntry.COL_LONGIT)
		.append(", ")
		.append(BusSchemaContract.BusStopsEntry.TABLE_NAME)
		.append(".")
		.append(BusSchemaContract.BusStopsEntry.COL_LATIT)
		.append(", ")
		.append(BusSchemaContract.BusNumbersAtStop.TABLE_NAME)
		.append(".")
		.append(BusSchemaContract.BusNumbersAtStop.COL_SERVICES + " ")
		.append(" from ")
		.append(BusSchemaContract.BusNumbersAtStop.TABLE_NAME)
		.append(", ")
		.append(BusSchemaContract.BusStopsEntry.TABLE_NAME)
		.append(" where ")		
		.append(" ( ABS(CAST(")
		.append(BusSchemaContract.BusStopsEntry.COL_LATIT)
		.append(" as DOUBLE))) ")
		.append(" < (CAST(" + (latit + cuadras) + " as DOUBLE )) AND ")
		.append(" ( ABS(CAST(")
		.append(BusSchemaContract.BusStopsEntry.COL_LATIT)
		.append(" as DOUBLE))) ")
		.append(" > (CAST(" + (latit - cuadras) + " as DOUBLE )) AND ")
		.append(" ( ABS(CAST(")
		.append(BusSchemaContract.BusStopsEntry.COL_LONGIT)
		.append(" as DOUBLE))) ")
		.append(" < (CAST(" + (longit + cuadras)+ " as DOUBLE )) AND ")
		.append(" ( ABS(CAST(")
		.append(BusSchemaContract.BusStopsEntry.COL_LONGIT )
		.append(" as DOUBLE))) ")
		.append(" > (CAST(" + (longit - cuadras) + " as DOUBLE ))")		
		.append(" AND ")
		.append(BusSchemaContract.BusNumbersAtStop.TABLE_NAME)
		.append(".")
		.append(BusSchemaContract.BusNumbersAtStop.COL_STOPID)
		.append(" = ")
		.append(BusSchemaContract.BusStopsEntry.TABLE_NAME).append(".")
		.append(BusSchemaContract.BusStopsEntry.COL_STOPID);			
		//.append(" LIMIT 20");
		
		Cursor cursor = sqlLitedb.rawQuery(sql.toString(), null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			BusStop stop = new BusStop();
			stop.setStopId(cursor.getString(0));
			stop.setName(cursor.getString(1));
			stop.setLongit(String.valueOf(cursor.getDouble(2)));
			stop.setLatit(String.valueOf(cursor.getDouble(3)));
						
			String services = cursor.getString(4);
			if (services != null) {
				String[] arr = services.split(",");
				stop.setServiceNos(Arrays.asList(arr));
			}			
			busStops.add(stop);
			cursor.moveToNext();
		}		
		return busStops;
	}
	
	public List<BusRoute> getBusServiceRoute(String serviceId) {
		List<BusRoute> services = new ArrayList<BusRoute>();
		StringBuffer sql = new StringBuffer("select * from ")
				.append(BusSchemaContract.BusServiceRoutes.TABLE_NAME)
				.append(" where ")
				.append(BusSchemaContract.BusServiceRoutes.COL_SERVICENO)
				.append("= ?");

		Cursor cursor = sqlLitedb.rawQuery(sql.toString(),
				new String[] { serviceId });
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			BusRoute route = new BusRoute();
			route.setServiceId(cursor.getString(0));
			route.setStops(cursor.getString(1));
			route.setLongsNLangs(cursor.getString(2));
			services.add(route);
			cursor.moveToNext();
		}
		return services;
	}
	
	public boolean updateDatabse(){
		Log.i(MsgLogger.LOG, "####### DATABASE UPDATE ######");
		FileReaderHelper fileReaderHelper = new FileReaderHelper();
		fileReaderHelper.updateBusServices(context, sqlLitedb, BusDatabase.SQL_INSERTALL_SERVICES_ENTRY);
		fileReaderHelper.updateBusServiceRoutes(context, sqlLitedb, BusDatabase.SQL_INSERTALL_BUS_SERVICES_ROUTES);
		
		return true;
	}
	
	public List<BusService> getAllBusService() {
		List<BusService> services = new ArrayList<BusService>();
		StringBuffer sql = new StringBuffer("select * from ")
				.append(BusSchemaContract.BusServices.TABLE_NAME);

		Cursor cursor = sqlLitedb.rawQuery(sql.toString(), null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			BusService service = new BusService();
			service.setServiceNo(cursor.getString(0));
			service.setOperater(cursor.getString(1));
			service.setRoute(cursor.getString(2));
			service.setType(cursor.getString(3));
			service.setFullName(cursor.getString(4));
			service.setFromName(cursor.getString(5));
			service.setToName(cursor.getString(6));
			services.add(service);
			cursor.moveToNext();
		}
		return services;
	}

	public List<BusStop> getAllBuseServices(String text) {
		List<BusStop> buses = new ArrayList<BusStop>();

		StringBuffer sql = new StringBuffer("select ");
		sql.append(BusSchemaContract.BusStopsEntry.TABLE_NAME)
				.append(".")
				.append(BusSchemaContract.BusStopsEntry.COL_STOPID + ", ")
				.append(BusSchemaContract.BusStopsEntry.TABLE_NAME)
				.append(".")
				.append(BusSchemaContract.BusStopsEntry.COL_NAME + ", ")
				.append(BusSchemaContract.BusStopsEntry.TABLE_NAME)
				.append(".")
				.append(BusSchemaContract.BusStopsEntry.COL_LATIT + ", ")
				.append(BusSchemaContract.BusStopsEntry.TABLE_NAME)
				.append(".")
				.append(BusSchemaContract.BusStopsEntry.COL_LONGIT + ", ")
				.append(BusSchemaContract.BusNumbersAtStop.TABLE_NAME)
				.append(".")
				.append(BusSchemaContract.BusNumbersAtStop.COL_SERVICES + " ")
				.append(" from ")
				.append(BusSchemaContract.BusNumbersAtStop.TABLE_NAME)
				.append(", ")
				.append(BusSchemaContract.BusStopsEntry.TABLE_NAME)
				// .append(" where (")
				.append(" where ")
				/*
				 * .append(BusSchemaContract.BusNumbersAtStop.TABLE_NAME)
				 * .append
				 * (".").append(BusSchemaContract.BusStopsEntry.COL_STOPID)
				 * .append(" like UPPER('%" + text.trim().toUpperCase() + "%')")
				 * .append(" or ")
				 * .append(BusSchemaContract.BusStopsEntry.TABLE_NAME
				 * ).append(".")
				 * .append(BusSchemaContract.BusStopsEntry.COL_NAME)
				 * .append(" like UPPER('%" + text.trim().toUpperCase() + "%')")
				 * .append(") and ")
				 */
				.append(BusSchemaContract.BusNumbersAtStop.TABLE_NAME)
				.append(".")
				.append(BusSchemaContract.BusNumbersAtStop.COL_STOPID)
				.append(" = ")
				.append(BusSchemaContract.BusStopsEntry.TABLE_NAME).append(".")
				.append(BusSchemaContract.BusStopsEntry.COL_STOPID);
		// .append(" limit 20");

		Cursor cursor = sqlLitedb.rawQuery(sql.toString(), null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			BusStop stop = new BusStop();
			stop.setStopId(cursor.getString(0));
			stop.setName(cursor.getString(1));
			stop.setLatit(cursor.getString(2));
			stop.setLongit(cursor.getString(3));

			String services = cursor.getString(4);
			if (services != null) {
				String[] arr = services.split(",");
				stop.setServiceNos(Arrays.asList(arr));
			}
			buses.add(stop);
			cursor.moveToNext();
		}
		return buses;
	}

	public BusService getBusService(String serviceId) {
		BusService service = new BusService();
		StringBuffer sql = new StringBuffer("select * from ")
				.append(BusSchemaContract.BusServices.TABLE_NAME)
				.append(" where ")
				.append(BusSchemaContract.BusServices.COL_SERVICENO)
				.append("='").append(serviceId.trim()).append("'");

		Cursor cursor = sqlLitedb.rawQuery(sql.toString(), null);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			service.setServiceNo(cursor.getString(0));
			service.setOperater(cursor.getString(1));
			service.setRoute(cursor.getString(2));
			service.setType(cursor.getString(3));
			service.setFullName(cursor.getString(4));
			service.setFromName(cursor.getString(5));
			service.setToName(cursor.getString(6));
		}
		return service;
	}

	public List<BusService> getBusServices(String[] serviceIds) {
		List<BusService> services = new ArrayList<BusService>();
		StringBuffer sql = new StringBuffer("select * from ")
				.append(BusSchemaContract.BusServices.TABLE_NAME)
				.append(" where ")
				.append(BusSchemaContract.BusServices.COL_SERVICENO)
				.append(" in ( ").append(makePlaceholders(serviceIds.length))
				.append(" )");

		Cursor cursor = sqlLitedb.rawQuery(sql.toString(), serviceIds);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			BusService service = new BusService();
			service.setServiceNo(cursor.getString(0));
			service.setOperater(cursor.getString(1));
			service.setRoute(cursor.getString(2));
			service.setType(cursor.getString(3));
			service.setFullName(cursor.getString(4));
			service.setFromName(cursor.getString(5));
			service.setToName(cursor.getString(6));

			services.add(service);
			cursor.moveToNext();
		}
		return services;
	}

	private String makePlaceholders(int len) {
		if (len < 1) {
			throw new RuntimeException("No placeholders");
		} else {
			StringBuilder sb = new StringBuilder(len * 2 - 1);
			sb.append("?");
			for (int i = 1; i < len; i++) {
				sb.append(",?");
			}
			return sb.toString();
		}
	}

	public List getAllServicesAtStop() {
		List buses = new ArrayList();

		Cursor cursor = sqlLitedb.query(
				BusSchemaContract.BusNumbersAtStop.TABLE_NAME, new String[] {
						BusSchemaContract.BusNumbersAtStop.COL_SERVICES,
						BusSchemaContract.BusNumbersAtStop.COL_STOPID }, null,
				null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			//System.out.println(cursor.getString(0) + cursor.getString(1));
			buses.add(cursor.getString(0) + " : " + cursor.getString(1));
			cursor.moveToNext();
		}

		return buses;
	}

	public static void addBus(String id, String name, String lot, String lat) {
		ContentValues values = new ContentValues();
		values.put(BusSchemaContract.BusStopsEntry.COL_STOPID, id);
		values.put(BusSchemaContract.BusStopsEntry.COL_NAME, name);
		values.put(BusSchemaContract.BusStopsEntry.COL_LONGIT, lot);
		values.put(BusSchemaContract.BusStopsEntry.COL_LATIT, lat);
		sqlLitedb.insert(BusSchemaContract.BusStopsEntry.TABLE_NAME, null,
				values);
	}

	public void close() {
		busDatabase.close();
		sqlLitedb.close();
	}
}
