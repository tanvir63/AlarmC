package com.AlarmClock;

import java.io.Serializable;
import java.util.Arrays;

import com.utils.Constants;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.DataBase.DatabaseAccessor;
import com.DomainModel.Alarm;

public class AlarmCreateEditActivity extends Activity implements
		OnClickListener {

	private TextView titleView;
	private Alarm alarm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addeditalarm);
		titleView = (TextView) findViewById(R.id.titleView);
//			alarm = new Alarm();
//			titleView.setText("Create New Client");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.saveButton:
//			createUpdateClient();
//			break;

		default:
			break;
		}
	}

	private void createUpdateClient() {
//		clientName = nameEdit.getText().toString();
//		clientCode = codeEdit.getText().toString();
//		address = addressEdit.getText().toString();
//		contactName = contactEdit.getText().toString();
//		telephone = telEdit.getText().toString();
//		mobile = mobileEdit.getText().toString();
//
//		client.setClientCode(clientCode);
//		client.setClientName(nameEdit.getText().toString());
//		client.setAddress(address);
//		client.setContactName(contactName);
//		client.setTel(telephone);
//		client.setMobile(mobile);
//
//		if (whichOperation == Constants.INSERT_OPERATION) {
//			createNewClient(client);
//		} else {
//			updateClient(client);
//		}
	}

	private void updateClient(Alarm alarm) {
//		if (isAllInputGiven()) {
//			boolean isUpdated = DatabaseAccessor.updateClient(client);
//			if (isUpdated) {
//				Toast.makeText(AlarmCreateEditActivity.this,
//						"Updated client susscessfully", Toast.LENGTH_SHORT)
//						.show();
//				finish();
//			} else {
//				Toast.makeText(AlarmCreateEditActivity.this,
//						"Failed to update client", Toast.LENGTH_SHORT).show();
//			}
//		} else {
//			Toast.makeText(AlarmCreateEditActivity.this,
//					"Please fill all manadatory fields", Toast.LENGTH_SHORT)
//					.show();
//		}
	}

	private void createNewClient(Alarm alarm) {
//		if (isAllInputGiven()) {
//			boolean isInserted = DatabaseAccessor.insertClient(alarm);
//			if (isInserted) {
//				Toast.makeText(AlarmCreateEditActivity.this,
//						"Created new client susscessfully", Toast.LENGTH_SHORT)
//						.show();
//				finish();
//			} else {
//				Toast.makeText(AlarmCreateEditActivity.this,
//						"Failed to create new client", Toast.LENGTH_SHORT)
//						.show();
//			}
//		} else {
//			Toast.makeText(AlarmCreateEditActivity.this,
//					"Please fill all manadatory fields", Toast.LENGTH_SHORT)
//					.show();
//		}
	}

	private boolean isAllInputGiven() {
//		if ((clientName.length() > 0) && (clientCode.length() > 0)
//				&& (address.length() > 0) && (contactName.length() > 0)) {
//			if (telephone.length() == 0) {
//				telephone = "";
//			}
//
//			if (mobile.length() == 0) {
//				mobile = "";
//			}
//			return true;
//		}
		return false;
	}
}
