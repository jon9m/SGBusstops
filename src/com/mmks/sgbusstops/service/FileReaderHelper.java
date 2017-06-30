package com.mmks.sgbusstops.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mmks.sgbusstops.R;

public class FileReaderHelper {	
	
	public void updateBusServiceRoutes(Context context, SQLiteDatabase db, String sql) {
		try {
			SQLiteStatement stmt = db.compileStatement(sql);

			String[] files = context.getAssets().list("");
			AssetManager assetManager = context.getAssets();

			for (int i = 0; i < files.length; i++) {
				if (files[i].endsWith(".json")) {
					
					JSONParser parser = new JSONParser();
					Object obj = null;
					try {
						obj = parser.parse(new InputStreamReader(assetManager.open(files[i])));
					} catch (Exception e) {
						e.printStackTrace();
					}

					JSONObject jsonObject = (JSONObject) obj;

					if (jsonObject != null) {
						int size = jsonObject.size();
						for (int j = 0; j < size; j++) {							
							stmt.clearBindings();
							stmt.bindString(1,files[i].substring(0,files[i].indexOf(".")));
							JSONObject jsonObject2 = (JSONObject) jsonObject.get(j + 1+ "");
							
							String stops = jsonObject2.get("stops").toString();
							String route = jsonObject2.get("route").toString();							
							if (!(stops.equals("[]")) && (!route.equals("[]"))) {
								stmt.bindString(2, stops);
								stmt.bindString(3, route);
								stmt.execute();
							}
						}
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void updateBusstops(Context context, SQLiteDatabase db, String sql) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.busstops)));
			String strLine = "";
			StringTokenizer st = null;
			int lineNumber = 0, tokenNumber = 0;
			
			SQLiteStatement stmt = db.compileStatement(sql);
			
			//******************************************
			
			JSONParser parser = new JSONParser();
			Object obj = null;
			try {
				obj = parser.parse(new InputStreamReader(context.getResources().openRawResource(R.raw.busstops)));
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (obj != null) {
				JSONArray jsonArray = (JSONArray) obj;
				if (jsonArray.size() > 0) {
					int size = jsonArray.size();
					for (int j = 0; j < size; j++) {
						JSONObject busStopObject = (JSONObject) jsonArray.get(j);
						stmt.clearBindings();
						stmt.bindString(1, (String) busStopObject.get("no"));
						stmt.bindString(2, (String) busStopObject.get("name"));
						stmt.bindString(3, (String) busStopObject.get("lat"));
						stmt.bindString(4, (String) busStopObject.get("lng"));
						stmt.execute();
					}					
				}
			}
			
			//******************************************
			
			

			/*while ((strLine = br.readLine()) != null) {
				lineNumber++;

				st = new StringTokenizer(strLine, ":");
				while (st.hasMoreTokens()) {
					tokenNumber++;
					String value = st.nextToken();
					//System.out.println("Line # " + lineNumber + ", Token # " + tokenNumber + ", Token : " + value);

					String[] arr = value.split(",");
					
					stmt.clearBindings();
					stmt.bindString(1, arr[3]);
					stmt.bindString(2, arr[2]);
					stmt.bindString(3, arr[0]);
					stmt.bindString(4, arr[1]);
					stmt.execute();							
				}
				//stmt.executeBatch();
				
				tokenNumber = 0;				
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
					
		}
	}

	
	public void updateBusNosAtstops(Context context, SQLiteDatabase db, String sql) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.busnosatbusstops)));
			String strLine = "";
			StringTokenizer st = null;
			int lineNumber = 0, tokenNumber = 0;
			
			SQLiteStatement stmt = db.compileStatement(sql);

			while ((strLine = br.readLine()) != null) {
				lineNumber++;

				st = new StringTokenizer(strLine, ":");
				while (st.hasMoreTokens()) {
					tokenNumber++;
					String value = st.nextToken();
					//System.out.println("Line # " + lineNumber + ", Token # " + tokenNumber + ", Token : " + value);

					String[] arr = value.split("-");
					
					stmt.clearBindings();
					stmt.bindString(1, arr[0]);
					stmt.bindString(2, arr[1]);

					stmt.execute();							
				}
				
				tokenNumber = 0;				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
	}
	
	public void updateBusServices(Context context, SQLiteDatabase db, String sql) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.busservices)));
			String strLine = "";
			StringTokenizer st = null;
			int lineNumber = 0, tokenNumber = 0;
			
			SQLiteStatement stmt = db.compileStatement(sql);

			while ((strLine = br.readLine()) != null) {
				lineNumber++;

				st = new StringTokenizer(strLine, ":");
				while (st.hasMoreTokens()) {
					tokenNumber++;
					String value = st.nextToken();
					//System.out.println("Line # " + lineNumber + ", Token # " + tokenNumber + ", Token : " + value);

					String[] arr = value.split(",");
					
					stmt.clearBindings();
					stmt.bindString(1, arr[1]); //Service No
					stmt.bindString(2, arr[2]); //Operator
					stmt.bindString(3, arr[3]); //Route
					stmt.bindString(4, arr[4]); //Type
					stmt.bindString(5, arr[0]); //Full Name
					
					if ((arr[0] != null) && (arr[0].contains("-"))) {
						stmt.bindString(6, arr[0].split("-")[0]); //From Name
						stmt.bindString(7, arr[0].split("-")[1]); //To Name	
					}else{
						stmt.bindString(6, arr[0]); //From Name
						stmt.bindString(7, arr[0]); //To Name
					}									

					stmt.execute();							
				}
				
				tokenNumber = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
	}
}
