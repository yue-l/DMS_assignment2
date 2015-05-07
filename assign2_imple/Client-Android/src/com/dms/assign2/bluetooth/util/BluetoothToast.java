package com.dms.assign2.bluetooth.util;

import android.content.Context;
import android.widget.Toast;

/**
 * for debug bluetooth errors
 * 
 * @author yl
 */
public class BluetoothToast {

	private Toast toast;
	private Context context;

	public BluetoothToast(Context context) {
		this.context = context;
	}

	public void showToast(String msg) {
		if (toast != null) {
			toast.setText(msg);
		} else {
			toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		}
		toast.show();
	}

	public void closeToast() {
		if (toast != null) {
			toast.cancel();
		}
	}

}