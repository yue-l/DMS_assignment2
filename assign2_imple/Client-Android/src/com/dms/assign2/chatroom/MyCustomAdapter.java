package com.dms.assign2.chatroom;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dms.client.R;

public class MyCustomAdapter extends BaseAdapter {
	private ArrayList<String> mListItems;
	private LayoutInflater mLayoutInflater;

	public MyCustomAdapter(Context context, ArrayList<String> arrayList) {
		mListItems = arrayList;
		mLayoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mListItems.size();
	}

	@Override
	public Object getItem(int i) {
		return null;
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		if (view == null) {
			view = mLayoutInflater.inflate(R.layout.list_item, null);
		}
		String stringItem = mListItems.get(position);
		if (stringItem != null) {
			TextView itemName = (TextView) view
					.findViewById(R.id.list_item_text_view);
			if (itemName != null) {
				itemName.setText(stringItem);
			}
		}
		return view;
	}
}