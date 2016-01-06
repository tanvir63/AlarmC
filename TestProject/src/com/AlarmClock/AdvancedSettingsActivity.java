package com.AlarmClock;

import com.utils.Constants;
import com.utils.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

public class AdvancedSettingsActivity extends Activity {
	private CheckBox isFlashLightOnCheckBoxView;
	private CheckBox showAlarmNotificationCheckBoxView;
	private CheckBox IsAutoSnoozeOnCheckBoxView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advanced_settings);
		
		boolean isFlashLightOn=Utils.getBoolValue(this,Constants.FLASH_LIGHT,false);
		boolean showAlarmNotification= Utils.getBoolValue(this,Constants.SHOW_ALARM_NOTIFICATION,false);
		boolean IsAutoSnoozeOn= Utils.getBoolValue(this,Constants.IS_AUTO_SNOOZE_ON,false);
		
		isFlashLightOnCheckBoxView = (CheckBox) findViewById(R.id.isFlashLightOnCheckBox);
		isFlashLightOnCheckBoxView.setChecked(isFlashLightOn);
		showAlarmNotificationCheckBoxView = (CheckBox) findViewById(R.id.showAlarmNotificationCheckBox);
		showAlarmNotificationCheckBoxView.setChecked(showAlarmNotification);
		IsAutoSnoozeOnCheckBoxView = (CheckBox) findViewById(R.id.autoSnoozeLayoutCheckBox);
		IsAutoSnoozeOnCheckBoxView.setChecked(IsAutoSnoozeOn);
	}

	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        isFlashLightOnCheckBoxView.setOnCheckedChangeListener(new CheckedChangeListener(this,Constants.FLASH_LIGHT));
        showAlarmNotificationCheckBoxView.setOnCheckedChangeListener(new CheckedChangeListener(this,Constants.SHOW_ALARM_NOTIFICATION));
        IsAutoSnoozeOnCheckBoxView.setOnCheckedChangeListener(new CheckedChangeListener(this,Constants.IS_AUTO_SNOOZE_ON));
    }
	
	public class CheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        String checkBoxName;
        Context context;
        public CheckedChangeListener(Context context,String checkBoxName) {
            this.checkBoxName = checkBoxName;
            this.context=context;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        	Utils.putBoolValue(context,checkBoxName,isChecked);
        }
    }
}
