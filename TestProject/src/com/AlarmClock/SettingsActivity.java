package com.AlarmClock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends Activity {
	private Button showAlarmsButton;
	private Button advancedSetttingsButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.settings_screen);
			
			showAlarmsButton = (Button) findViewById(R.id.Alarms);
			showAlarmsButton.setOnClickListener(new View.OnClickListener() {
			    @Override
				public void onClick(View v) {
			    	Intent intent = new Intent( SettingsActivity.this, SettingAlarmsActivity.class);
					startActivity(intent);
			    }
			});
			
			advancedSetttingsButton = (Button) findViewById(R.id.ShowAdvancedSettingsButton);
			advancedSetttingsButton.setOnClickListener(new View.OnClickListener() {
			    @Override
				public void onClick(View v) {
			    	Intent intent = new Intent( SettingsActivity.this, AdvancedSettingsActivity.class);
					startActivity(intent);
			    }
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}