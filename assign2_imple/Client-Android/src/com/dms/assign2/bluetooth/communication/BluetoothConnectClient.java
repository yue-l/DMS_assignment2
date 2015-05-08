package com.dms.assign2.bluetooth.communication;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

/**
 * Bluetooth server setup
 * 
 * @author yl
 */
public class BluetoothConnectClient {

	private final UUID MEU_UUID_PC = UUID
			.fromString("0a041121-f0e3-1000-8000-00805F9B34FB");

	public BluetoothSocket conectedBluetooth(BluetoothDevice device) {
		BluetoothSocket bluetoothSocket = null;

		try {
			bluetoothSocket = device
					.createRfcommSocketToServiceRecord(MEU_UUID_PC);
			bluetoothSocket.connect();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return bluetoothSocket;
	}

}