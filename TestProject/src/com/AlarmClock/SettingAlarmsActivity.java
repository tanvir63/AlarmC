package com.AlarmClock;

import android.app.Activity;
import android.os.Bundle;

public class SettingAlarmsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.settingalarms_screen);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}