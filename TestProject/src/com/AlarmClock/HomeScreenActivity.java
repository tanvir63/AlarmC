package com.AlarmClock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreenActivity extends Activity {
	private Button settingsButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		settingsButton = (Button) findViewById(R.id.button_setting);
		settingsButton.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	Intent intent = new Intent(HomeScreenActivity.this, SettingsActivity.class);
				startActivity(intent);
		    }
		});
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
}