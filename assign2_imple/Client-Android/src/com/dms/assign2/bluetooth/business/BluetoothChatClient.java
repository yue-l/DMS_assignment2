package com.dms.assign2.bluetooth.business;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;

import com.dms.assign2.bluetooth.alertdialog.AlertDialogDevicesFound;
import com.dms.assign2.bluetooth.broadcast.EventsBluetoothReceiver;
import com.dms.assign2.bluetooth.communication.BluetoothComunicationThread;
import com.dms.assign2.bluetooth.interfaces.OnBluetoothDeviceSelectedListener;
import com.dms.assign2.bluetooth.interfaces.OnConnectionBluetoothListener;
import com.dms.assign2.bluetooth.interfaces.OnSearchBluetoothListener;
import com.dms.assign2.bluetooth.manager.BluetoothManager;
import com.dms.assign2.bluetooth.task.BluetoothClientTask;
import com.dms.assign2.bluetooth.task.BluetoothServiceTask;

/**
 * 
 * @author yl
 */
public class BluetoothChatClient implements OnConnectionBluetoothListener,
		OnBluetoothDeviceSelectedListener, OnSearchBluetoothListener {

	private Context context;
	private Handler handler;

	private BluetoothManager bluetoothManager;
	private BluetoothComunicationThread bluetoothComunication;
	private AlertDialogDevicesFound alertDialogDevicesFound;
	private EventsBluetoothReceiver eventsBluetoothReceiver;

	public BluetoothChatClient(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;

		bluetoothManager = new BluetoothManager();
		alertDialogDevicesFound = new AlertDialogDevicesFound(context, this);
		eventsBluetoothReceiver = new EventsBluetoothReceiver(context, this);
	}

	public void registerFilter() {
		eventsBluetoothReceiver.registerFilters();
	}

	public void unregisterFilter() {
		eventsBluetoothReceiver.unregisterFilters();
	}

	public void startFoundDevices() {
		stopCommucanition();

		eventsBluetoothReceiver.showProgress();
		bluetoothManager.getBluetoothAdapter().startDiscovery();
	}

	public void startClient(BluetoothDevice bluetoothDevice) {
		BluetoothClientTask bluetoothClientTask = new BluetoothClientTask(
				context, this);
		bluetoothClientTask.execute(bluetoothDevice);
	}

	public void startServer() {
		BluetoothServiceTask bluetoothServiceTask = new BluetoothServiceTask(
				context, this);
		bluetoothServiceTask.execute(bluetoothManager.getBluetoothAdapter());
	}

	public void starCommunication(BluetoothSocket bluetoothSocket) {
		bluetoothComunication = new BluetoothComunicationThread(context, handler);
		bluetoothComunication.setBluetoothSocket(bluetoothSocket);
		bluetoothComunication.start();
	}

	public void stopCommucanition() {
		if (bluetoothComunication != null) {
			bluetoothComunication.stopComunication();
		}
	}

	public boolean sendMessage(String message) {
		if (bluetoothComunication != null) {
			return bluetoothComunication.sendMessageByBluetooth(message);
		} else {
			return false;
		}
	}

	public BluetoothManager getBluetoothManager() {
		return bluetoothManager;
	}

	@Override
	public void onBluetoothDeviceSelected(BluetoothDevice bluetoothDevice) {
		startClient(bluetoothDevice);
	}

	@Override
	public void onConnectionBluetooth(BluetoothSocket bluetoothSocket) {
		starCommunication(bluetoothSocket);
	}

	@Override
	public void onSearchBluetooth(List<BluetoothDevice> devicesFound) {
		alertDialogDevicesFound.settingsAlertDialog(devicesFound);
	}
}