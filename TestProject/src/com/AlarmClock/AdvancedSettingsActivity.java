package com.AlarmClock;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class AdvancedSettingsActivity extends Activity implements
		OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advanced_settings);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.saveButton:
		// createUpdateClient();
		// break;

		default:
			break;
		}
	}
}
