package com.AlarmClock;
import com.DataBase.*;
import java.util.ArrayList;
import java.util.List;

import com.DomainModel.TestClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingAlarmsActivity extends Activity {
	private Activity context;
	private List<TestClass> items;
	private ListViewAdapter adapter;
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.settingalarms_screen);
			List<TestClass> tests=DatabaseAccessor.getTestList();
//			context = this;
//
//			items = new ArrayList<TestClass>();
//			adapter = new ListViewAdapter(context,items);
//			//listView = (ListView) findViewById(R.id.listView);
//			listView.setAdapter(adapter);
//			setListItem();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void setListItem() {
		items = com.DataBase.DatabaseAccessor.getTestList();
		adapter.notifyDataSetChanged();
	}
}

class ListViewAdapter extends com.DataBase.AbstractListViewAdapter<TestClass> {
	
	public ListViewAdapter(Context context, List<TestClass> items) {
		super(context,items);
	}

	public View getView(final int position, View convertView,
			ViewGroup parent) {return null;}
//		ViewHolder holder= new ViewHolder();
//		if (convertView == null) {
//			convertView = mInflater
//					.inflate(R.layout.client_container, null);
//			holder = new ViewHolder();
//
//			holder.codeView = (TextView) convertView
//					.findViewById(R.id.codeView);
//			holder.nameView = (TextView) convertView
//					.findViewById(R.id.nameView);
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//		final TestClass client = (TestClass)items.get(position);
//		holder.codeView.setText(client.getClientCode());
//		holder.nameView.setText(client.getClientName());
//		holder.addressView.setText(client.getAddress());
//		holder.telView.setText(client.getTel());
//		holder.mobileView.setText(client.getMobile());
//		holder.deleteView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				showAlertForDelete(client.getClientID());
//			}
//		});
//		holder.editView.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				Client client = (Client)items.get(position);
//				bundle = new Bundle();
//				bundle.putString(Constants.KEY_WHAT_OPERATION,
//						Constants.KEY_EDIT_CLIENT);
//				bundle.putInt(Constants.KEY_ID, client.getClientID());
//				bundle.putString(Constants.KEY_CLIENT_CODE,
//						client.getClientCode());
//				bundle.putString(Constants.KEY_CLIENT_NAME,
//						client.getClientName());
//				bundle.putString(Constants.KEY_ADDRESS, client.getAddress());
//				bundle.putString(Constants.KEY_CONTACT_NAME,
//						client.getContactName());
//				bundle.putString(Constants.KEY_TEL, client.getTel());
//				bundle.putString(Constants.KEY_MOBILE, client.getMobile());
//				bundle.putInt(Constants.KEY_STATUS, client.getStatus());
//				bundle.putString(Constants.KEY_PRICE, client.getPrice());
//
//				pushViewController(new ClientEditDeleteController(
//						getTabRootManager(), bundle));
//			}
//		});
//		if (client.getStatus() == 1) {
//			holder.statusView.setText("Active");
//		} else {
//			holder.statusView.setText("Inactive");
//		}
//
//		return convertView;
//	}
//
//	class ViewHolder {
//		TextView codeView;
//		TextView nameView;
//		TextView addressView;
//		TextView telView;
//		TextView mobileView;
//		TextView statusView;
//		ImageButton deleteView;
//		ImageView editView;
//	}
//
//
//	AlertDialog.Builder builder = new AlertDialog.Builder(context);
//	builder.setIcon(R.drawable.icon_question);
//	builder.setTitle("Delete client ?");
//	builder.setMessage("Do you want to delete this client ?");
//	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//		@Override
//		public void onClick(DialogInterface dialog, int arg1) {
//			dialog.cancel();
//			boolean isDelete = DatabaseAccessor.deleteClient(Integer
//					.toString(id));
//			if (isDelete) {
//				Toast.makeText(context, "Delete succssessfully",
//						Toast.LENGTH_SHORT).show();
//			}
//			setListItem();
//		}
//	});
//	builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//
//		@Override
//		public void onClick(DialogInterface dialog, int arg1) {
//			dialog.cancel();
//
//		}
//	});
//
//	AlertDialog alert = builder.create();
//	alert.show();

}


 