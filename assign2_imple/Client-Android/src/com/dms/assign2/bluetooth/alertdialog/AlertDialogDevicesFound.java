package com.dms.assign2.bluetooth.alertdialog;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.dms.assign2.bluetooth.business.IBusinessLogic.OnBluetoothDeviceSelectedListener;
import com.dms.client.R;

/**
 * 
 * @author Marcus Pimenta
 * @email mvinicius.pimenta@gmail.com
 * @date 16:46:04 01/06/2013
 */
public class AlertDialogDevicesFound extends AlertDialogGeneric implements
		OnClickListener {

	private List<BluetoothDevice> devicesFound;
	private OnBluetoothDeviceSelectedListener onBluetoothDeviceSelectedListener;

	public AlertDialogDevicesFound(Context context,
			OnBluetoothDeviceSelectedListener onBluetoothDeviceSelectedListener) {
		super(context);

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

}