package com.dms.assign2.bluetooth.alertdialog;

import java.util.List;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.dms.assign2.bluetooth.interfaces.OnBluetoothDeviceSelectedListener;
import com.dms.client.R;

/**
 * 
 * @author yl
 */
public class AlertDialogDevicesFound implements OnClickListener {

	protected Context context;
	protected AlertDialog.Builder alert;

	private List<BluetoothDevice> devicesFound;
	private OnBluetoothDeviceSelectedListener onBluetoothDeviceSelectedListener;

	public AlertDialogDevicesFound(Context context,
			OnBluetoothDeviceSelectedListener onBluetoothDeviceSelectedListener) {
		this.context = context;
		alert = new AlertDialog.Builder(context);

		this.onBluetoothDeviceSelectedListener = onBluetoothDeviceSelectedListener;
	}

	public void settingsAlertDialog(List<BluetoothDevice> devicesFound) {

		this.devicesFound = devicesFound;

		String[] devices = new String[devicesFound.size()];

		for (int i = 0; i < devicesFound.size(); i++) {
			devices[i] = devicesFound.get(i).getName();
		}

		alert.setTitle(context.getString(R.string.devices_found));
		alert.setItems(devices, this);

		showAlertDialog();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		onBluetoothDeviceSelectedListener
				.onBluetoothDeviceSelected(devicesFound.get(which));
	}

	private void showAlertDialog() {
		alert.create().show();
	}

	private void cancelAlertDialog() {
		alert.create().cancel();
	}
}