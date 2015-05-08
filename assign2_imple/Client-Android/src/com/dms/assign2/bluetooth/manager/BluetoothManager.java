package com.dms.assign2.bluetooth.manager;

import android.bluetooth.BluetoothAdapter;

/**
 * 
 * @author yl
 */
public class BluetoothManager {

	private BluetoothAdapter bluetoothAdapter;

	public BluetoothManager() {
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	public BluetoothAdapter getBluetoothAdapter() {
		return bluetoothAdapter;
	}

	public boolean verifySuportedBluetooth() {
		return (bluetoothAdapter != null) ? true : false;
	}

	public boolean isEnabledBluetooth() {
		return bluetoothAdapter.isEnabled();
	}

}