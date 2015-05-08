package com.dms.assign2.bluetooth.task;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;

import com.dms.assign2.bluetooth.communication.BluetoothConnectService;
import com.dms.assign2.bluetooth.interfaces.OnConnectionBluetoothListener;
import com.dms.assign2.bluetooth.util.BluetoothToast;
import com.dms.client.R;

/**
 * 
 * @author yl
 */
public class BluetoothServiceTask extends
		AsyncTask<BluetoothAdapter, Void, BluetoothSocket> {

	private Context context;
	private ProgressDialog progressDialog;

	private BluetoothToast toastUtil;
	private BluetoothConnectService bluetoothService;
	private OnConnectionBluetoothListener onBluetoothListener;

	public BluetoothServiceTask(Context context,
			OnConnectionBluetoothListener onBluetoothListener) {
		this.context = context;
		this.onBluetoothListener = onBluetoothListener;

		toastUtil = new BluetoothToast(context);
		bluetoothService = new BluetoothConnectService();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context,
				context.getString(R.string.waiting),
				context.getString(R.string.msg_waiting_connection));
	}

	@Override
	protected BluetoothSocket doInBackground(
			BluetoothAdapter... bluetoothAdapter) {
		return bluetoothService.startServer(bluetoothAdapter[0]);
	}

	@Override
	protected void onPostExecute(BluetoothSocket bluetoothSocket) {
		super.onPostExecute(bluetoothSocket);

		closeDialog();

		if (bluetoothSocket != null) {
			onBluetoothListener.onConnectionBluetooth(bluetoothSocket);
		} else {
			toastUtil.showToast(context.getString(R.string.connection_failed));
		}
	}

	private void closeDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

}