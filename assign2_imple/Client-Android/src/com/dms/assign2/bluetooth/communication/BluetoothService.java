package com.dms.assign2.bluetooth.communication;

import java.io.IOException;
import java.util.UUID;

import com.dms.assign2.bluetooth.activity.BluetoothActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

/**
 * 
 * @author Marcus Pimenta
 * @email mvinicius.pimenta@gmail.com
 * 01/11/2012 13:05:56 
 */
public class BluetoothService{
	 
	 private final String NAME_SERVICE_BT = "bluetooth";
	 private final UUID ID_CONECTION = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); 

	 public BluetoothSocket startServer(BluetoothAdapter adaptador) {
		 BluetoothSocket bluetoothSocket = null;
		 
		 try {
			 BluetoothServerSocket serverSocket = adaptador.listenUsingRfcommWithServiceRecord(NAME_SERVICE_BT, ID_CONECTION); 
			 bluetoothSocket = serverSocket.accept(BluetoothActivity.BT_TIMER_VISIBLE * 1000); 
			 
		 } catch (IOException e) { 
			 e.printStackTrace();
		 }
		 
		 return bluetoothSocket;
	 }
	 
}