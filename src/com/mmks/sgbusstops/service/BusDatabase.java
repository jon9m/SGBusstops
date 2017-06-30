package com.mmks.sgbusstops.service;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mmks.sgbusstops.service.BusSchemaContract.BusNumbersAtStop;
import com.mmks.sgbusstops.service.BusSchemaContract.BusServiceRoutes;
import com.mmks.sgbusstops.service.BusSchemaContract.BusServices;
import com.mmks.sgbusstops.service.BusSchemaContract.BusStopsEntry;

public class BusDatabase extends SQLiteOpenHelper {

	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 3;
	public static final String DATABASE_NAME = "bussgdb.db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ", ";
	
	private Context context;

	private static final String SQL_CREATE_BUSSTOPS = "CREATE TABLE "
			+ BusStopsEntry.TABLE_NAME + " (" + BusStopsEntry.COL_STOPID
			+ TEXT_TYPE + " NOT NULL" + COMMA_SEP + BusStopsEntry.COL_NAME
			+ TEXT_TYPE + " NOT NULL" + COMMA_SEP + BusStopsEntry.COL_LATIT
			+ TEXT_TYPE + " NOT NULL" + COMMA_SEP + BusStopsEntry.COL_LONGIT
			+ TEXT_TYPE + " NOT NULL)";
	
	private static final String SQL_CREATE_BUS_NOS_AT_STOPS = "CREATE TABLE "
			+ BusNumbersAtStop.TABLE_NAME + " (" + BusNumbersAtStop.COL_STOPID
			+ TEXT_TYPE + " NOT NULL" + COMMA_SEP + BusNumbersAtStop.COL_SERVICES
			+ TEXT_TYPE + " NOT NULL)";
	
	private static final String SQL_CREATE_BUSSERVICES = "CREATE TABLE "
			+ BusServices.TABLE_NAME + " (" + BusServices.COL_SERVICENO
			+ TEXT_TYPE + " NOT NULL" + COMMA_SEP + BusServices.COL_OPERATOR
			+ TEXT_TYPE + " NOT NULL" + COMMA_SEP + BusServices.COL_ROUTES
			+ TEXT_TYPE + " NOT NULL" + COMMA_SEP + BusServices.COL_TYPE			
			+ TEXT_TYPE + " NOT NULL" + COMMA_SEP + BusServices.COL_FULLNAME
			+ TEXT_TYPE + " NOT NULL" + COMMA_SEP + BusServices.COL_FROMNAME
			+ TEXT_TYPE + " NOT NULL" + COMMA_SEP + BusServices.COL_TONAME
			+ TEXT_TYPE + " NOT NULL)";
	
	private static final String SQL_CREATE_BUS_SERVICE_ROUTES = "CREATE TABLE "
			+ BusServiceRoutes.TABLE_NAME + " (" + BusServiceRoutes.COL_SERVICENO
			+ TEXT_TYPE + " NOT NULL" + COMMA_SEP + BusServiceRoutes.COL_STOPS
			+ TEXT_TYPE + " NOT NULL" + COMMA_SEP + BusServiceRoutes.COL_ROUTE
			+ TEXT_TYPE + " NOT NULL)";
	
	private static final String SQL_CLEARALL_BUSSTOPS_ENTRY = "DELETE FROM " + BusStopsEntry.TABLE_NAME;	
	private static final String SQL_CLEARALL_BUS_NOS_AT_STOPS_ENTRY = "DELETE FROM " + BusNumbersAtStop.TABLE_NAME;	
	private static final String SQL_CLEARALL_BUS_SERVICES = "DELETE FROM " + BusServices.TABLE_NAME;
	private static final String SQL_CLEARALL_BUS_SERVICE_ROUTE = "DELETE FROM " + BusServiceRoutes.TABLE_NAME;
	
	public static final String SQL_INSERTALL_BUSSTOPS_ENTRY = "INSERT INTO " + BusStopsEntry.TABLE_NAME + " values(?,?,?,?)";
	public static final String SQL_INSERTALL_BUSNOS_AT_STOPS_ENTRY = "INSERT INTO " + BusNumbersAtStop.TABLE_NAME + " values(?,?)";
	public static final String SQL_INSERTALL_SERVICES_ENTRY = "INSERT INTO " + BusServices.TABLE_NAME + " values(?,?,?,?,?,?,?)";
	public static final String SQL_INSERTALL_BUS_SERVICES_ROUTES = "INSERT INTO " + BusServiceRoutes.TABLE_NAME + " values(?,?,?)";
	
	

	public BusDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	public void onCreate(SQLiteDatabase db) {
		
		//System.out.println("####### CREATE DATABASE AND UPDATE ######");
		
		db.execSQL(SQL_CREATE_BUSSTOPS);
		db.execSQL(SQL_CLEARALL_BUSSTOPS_ENTRY);		

		db.execSQL(SQL_CREATE_BUS_NOS_AT_STOPS);
		db.execSQL(SQL_CLEARALL_BUS_NOS_AT_STOPS_ENTRY);

		db.execSQL(SQL_CREATE_BUSSERVICES);
		db.execSQL(SQL_CLEARALL_BUS_SERVICES);
		
		db.execSQL(SQL_CREATE_BUS_SERVICE_ROUTES);
		db.execSQL(SQL_CLEARALL_BUS_SERVICE_ROUTE);

		FileReaderHelper fileReaderHelper = new FileReaderHelper();
		fileReaderHelper.updateBusstops(context, db, SQL_INSERTALL_BUSSTOPS_ENTRY);
		fileReaderHelper.updateBusNosAtstops(context, db, SQL_INSERTALL_BUSNOS_AT_STOPS_ENTRY);
		
		//Start DB Service
		Intent msgIntent = new Intent(context, DBService.class);		
		context.startService(msgIntent);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}