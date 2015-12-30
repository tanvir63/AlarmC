package com.DomainModel;

import java.sql.Date;

import android.R.string;
import android.widget.TimePicker;

public class Alarm {
	TimePicker time;
	boolean isActive;
	boolean isAlways;
	boolean isNever;
	Date alarmStartDate;
	Date alarmEndDate;
	int ringToneId;
	boolean isVibrationEnabled;
	double snoozeDurationMinuates;
	string note;
}
