package com.mmks.sgbusstops.activity;

import com.mmks.sgbusstops.R;
import com.mmks.sgbusstops.R.id;
import com.mmks.sgbusstops.R.layout;
import com.mmks.sgbusstops.R.menu;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		context = this;

		SeekBar locControl = (SeekBar) findViewById(R.id.nearlocationseek);
		TextView rangeVal = (TextView) findViewById(R.id.txtrangeval);
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		float range = preferences.getFloat("loc_range", 0.006f);
		locControl.setProgress((int) (range * 1000));
		rangeVal.setText(locControl.getProgress()*100 + " meters");

		locControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				progressChanged = progress;
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
				SharedPreferences.Editor edit = preferences.edit();
				TextView rangeVal = (TextView) findViewById(R.id.txtrangeval);
				rangeVal.setText("" + progressChanged);
				rangeVal.setText(progressChanged*100 + " meters");
				
				float range = (float) ((float) progressChanged / (float) 1000);

				//Toast.makeText(SettingsActivity.this, "seek bar progress:" + range, Toast.LENGTH_SHORT).show();

				if (range != 0) {
					edit.putFloat("loc_range", range);
					edit.commit();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
}
