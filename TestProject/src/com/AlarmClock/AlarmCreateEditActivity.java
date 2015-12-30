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
	private EditText nameEdit;
	private EditText codeEdit;
	private EditText addressEdit;
	private EditText contactEdit;
	private EditText telEdit;
	private EditText mobileEdit;
	private Button saveButton;
	private int whichOperation;

	private String clientName;
	private String clientCode;
	private String address;
	private String contactName;
	private String telephone;
	private String mobile;
	private Client client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_client_create_edit);

		Log.i("DREG", "Chacha vatija");
		whichOperation = getIntent().getIntExtra(Constants.KEY_OPERATION, Constants.INSERT_OPERATION);
		titleView = (TextView) findViewById(R.id.titleView);
		nameEdit = (EditText) findViewById(R.id.nameEdit);
		codeEdit = (EditText) findViewById(R.id.codeEdit);
		addressEdit = (EditText) findViewById(R.id.addressEdit);
		contactEdit = (EditText) findViewById(R.id.contactEdit);
		telEdit = (EditText) findViewById(R.id.telEdit);
		mobileEdit = (EditText) findViewById(R.id.mobileEdit);
		saveButton = (Button) findViewById(R.id.saveButton);
		saveButton.setOnClickListener(this);

		if (whichOperation == Constants.INSERT_OPERATION) {
			client = new Client();
			titleView.setText("Create New Client");
		} else {
			titleView.setText("Edit Client");

			client = (Client) getIntent().getSerializableExtra(Constants.KEY_CLIENT_INFO);
			nameEdit.setText(client.getClientName());
			codeEdit.setText(client.getClientCode());
			addressEdit.setText(client.getAddress());
			contactEdit.setText(client.getClientName());
			telEdit.setText(client.getTel());
			mobileEdit.setText(client.getMobile());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saveButton:
			createUpdateClient();
			break;

		default:
			break;
		}

	}

	private void createUpdateClient() {
		clientName = nameEdit.getText().toString();
		clientCode = codeEdit.getText().toString();
		address = addressEdit.getText().toString();
		contactName = contactEdit.getText().toString();
		telephone = telEdit.getText().toString();
		mobile = mobileEdit.getText().toString();

		client.setClientCode(clientCode);
		client.setClientName(nameEdit.getText().toString());
		client.setAddress(address);
		client.setContactName(contactName);
		client.setTel(telephone);
		client.setMobile(mobile);

		if (whichOperation == Constants.INSERT_OPERATION) {
			createNewClient(client);
		} else {
			updateClient(client);
		}
	}

	private void updateClient(Client client) {
		if (isAllInputGiven()) {
			boolean isUpdated = DatabaseAccessor.updateClient(client);
			if (isUpdated) {
				Toast.makeText(AlarmCreateEditActivity.this,
						"Updated client susscessfully", Toast.LENGTH_SHORT)
						.show();
				finish();
			} else {
				Toast.makeText(AlarmCreateEditActivity.this,
						"Failed to update client", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(AlarmCreateEditActivity.this,
					"Please fill all manadatory fields", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void createNewClient(Client client) {
		if (isAllInputGiven()) {
			boolean isInserted = DatabaseAccessor.insertClient(client);
			if (isInserted) {
				Toast.makeText(AlarmCreateEditActivity.this,
						"Created new client susscessfully", Toast.LENGTH_SHORT)
						.show();
				finish();
			} else {
				Toast.makeText(AlarmCreateEditActivity.this,
						"Failed to create new client", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			Toast.makeText(AlarmCreateEditActivity.this,
					"Please fill all manadatory fields", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private boolean isAllInputGiven() {
		if ((clientName.length() > 0) && (clientCode.length() > 0)
				&& (address.length() > 0) && (contactName.length() > 0)) {
			if (telephone.length() == 0) {
				telephone = "";
			}

			if (mobile.length() == 0) {
				mobile = "";
			}
			return true;
		}
		return false;
	}
}
