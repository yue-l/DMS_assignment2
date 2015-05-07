package com.dms.assign2.bluetooth.interfaces;

import java.util.List;

import android.bluetooth.BluetoothDevice;

public interface OnSearchBluetoothListener {
	public abstract void onSearchBluetooth(List<BluetoothDevice> devicesFound);
}
