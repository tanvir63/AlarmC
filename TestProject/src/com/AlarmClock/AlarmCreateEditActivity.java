package com.AlarmClock;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;

import com.utils.Constants;
import com.utils.TimePickerFragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.DataBase.DatabaseAccessor;
import com.DomainModel.Alarm;

public class AlarmCreateEditActivity extends Activity implements
		OnClickListener {

	private TextView titleView;
	private Alarm alarm;
	private TimePicker alarmTimePicker;
	private int hour;
	private int minute;
	private Context context;
	private RadioGroup radioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit_alarm);
		titleView = (TextView) findViewById(R.id.titleView);
		// alarm = new Alarm();
		titleView.setText("Create New Alarm");
		setCurrentTimeOnView();
		context = this;
		createAlarmRepeatDialog();
		createListOfRingTonesDialog();
	}

	// display current time
	private void setCurrentTimeOnView() {
		// tvDisplayTime = (TextView) findViewById(R.id.tvTime);
		alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);

		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		// set current time into timepicker
		alarmTimePicker.setCurrentHour(hour);
		alarmTimePicker.setCurrentMinute(minute);

	}

	private void createAlarmRepeatDialog() {
		// Context context=this;
		Button button;
		button = (Button) findViewById(R.id.buttonAlarmRepeatDialog);

		// add button listener
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// custom dialog
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.repeat_alarm_options);
				dialog.setTitle("Repeat Options");

				radioGroup = (RadioGroup) findViewById(R.id.repeatOptionGroup);
				
				//AddActionListenerToRadioButton(radioGroup);

				// set the custom dialog components - text, image and button
				// TextView text = (TextView) dialog.findViewById(R.id.text);
				// text.setText("Android custom dialog example!");
				// ImageView image = (ImageView)
				// dialog.findViewById(R.id.image);
				// image.setImageResource(R.drawable.ic_launcher);
				//
				// Button dialogButton = (Button)
				// dialog.findViewById(R.id.dialogButtonOK);
				// // if button is clicked, close the custom dialog
				// dialogButton.setOnClickListener(new OnClickListener() {
				// @Override
				// public void onClick(View v) {
				// dialog.dismiss();
				// }
				// });
				//
				dialog.show();
			}
		});
	}

	private void createListOfRingTonesDialog() {
		// Context context=this;
		Button button;
		button = (Button) findViewById(R.id.buttonShowRingTonesDialog);

		// add button listener
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// custom dialog
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.list_of_musics);
				dialog.setTitle("List Of Musics");

				RadioGroup ringToneGroup = (RadioGroup) findViewById(R.id.ringToneGroup);
				
				dialog.show();
			}
		});
	}

	
	private void AddActionListenerToRadioButton(RadioGroup group)
	{
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
	    {
	        public void onCheckedChanged(RadioGroup group, int checkedId) {
//	            // checkedId is the RadioButton selected
//	            //RadioButton rb=(RadioButton)findViewById(checkedId);
//	            //Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
//	            
	        	//int selectedRadioButtonId=group.getCheckedRadioButtonId();
//	    		RadioButton timeRangeRadioButton=(RadioButton) findViewById(R.id.DateRange);
//	    		RadioButton selectedRadioButton=(RadioButton) findViewById(checkedId);
//	    		if(selectedRadioButton.equals(timeRangeRadioButton))
//	    		{
//	    			RelativeLayout layout;
//	    			layout = (RelativeLayout) findViewById(R.id.TestLayout);
//	    			layout.setVisibility(RelativeLayout.GONE);
//	    		}
	        	//Log.i("DREG", "inside");
	        }
	    });
		
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


	public void showAlarmTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		// newFragment.show(FragmentActivity.getSupportFragmentManager(),
		// "timePicker");
	}
}
