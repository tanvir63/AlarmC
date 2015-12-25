package com.DataBase;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AbstractListViewAdapter<T> extends BaseAdapter {
	protected LayoutInflater mInflater;
	protected Context context;
	List<T> items;
	public AbstractListViewAdapter(Context context, List<T> items) {
		mInflater = LayoutInflater.from(context);
		this.context = context;
		this.items=items;
	}

	public abstract View getView(final int position, View convertView, ViewGroup parent) ;
	

	public long getItemId(int position) {
		return 0;
	}

	public int getCount() {
		return items.size();
	}

	public T getItem(int position) {
		return items.get(position);
	}
}
