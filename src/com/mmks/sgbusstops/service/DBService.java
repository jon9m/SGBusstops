package com.mmks.sgbusstops.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

public class DBService extends Service {

	BusDAO busDAO;
	Context context;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		context = this;

		new DoBackGroundClass().execute(context);

		// Stop Service
		// stopService(new Intent(DBService.this, DBService.class));		

		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO for communication return IBinder implementation
		return null;
	}

	private class DoBackGroundClass extends AsyncTask {

		@Override
		protected Object doInBackground(Object... arg0) {
			Context context = (Context) (arg0[0]);
			if (busDAO == null) {
				busDAO = BusDAO.getBusDAO(context);
				busDAO.updateDatabse();
			}
			publishProgress(null);
			return null;
		}

		@Override
		protected void onProgressUpdate(Object... values) {
			super.onProgressUpdate(values);
			Toast.makeText(context, "In Progress", Toast.LENGTH_LONG);
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			Toast.makeText(context, "Done!", Toast.LENGTH_LONG);
			stopSelf();
		}
	}
}