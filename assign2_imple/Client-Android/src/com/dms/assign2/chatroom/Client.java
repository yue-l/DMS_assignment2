package com.dms.assign2.chatroom;

import android.util.Log;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	private String serverMessage;
	public static String SERVERIP;
	public static final int SERVERPORT = 8083;
	private OnMessageReceived mMessageListener = null;
	private boolean mRun = false;

	PrintWriter out;
	BufferedReader in;

	public Client(OnMessageReceived listener) {
		mMessageListener = listener;

	}

	public void sendMessage(String message) {
		if (out != null && !out.checkError()) {
			out.println(message);
			out.flush();
		}
	}

	public void stopClient() {
		mRun = false;
	}

	public void run() {

		mRun = true;

		try {
			InetAddress serverAddr = InetAddress.getByName(SERVERIP);
			Log.e("serverAddr", serverAddr.toString());
			Log.e("TCP Client", "C: Connecting...");
			Socket socket = new Socket(serverAddr, SERVERPORT);
			Log.e("TCP Server IP", SERVERIP);
			try {
				out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())), true);
				Log.e("TCP Client", "C: Sent.");
				Log.e("TCP Client", "C: Done.");
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				while (mRun) {
					serverMessage = in.readLine();

					if (serverMessage != null && mMessageListener != null) {
						mMessageListener.messageReceived(serverMessage);
					}
					serverMessage = null;
				}
				Log.e("RESPONSE FROM SERVER", "S: Received Message: '"
						+ serverMessage + "'");

			} catch (Exception e) {
				Log.e("TCP", "S: Error", e);
			} finally {
				socket.close();
			}
		} catch (Exception e) {

			Log.e("TCP", "C: Error", e);
		}
	}

	public interface OnMessageReceived {
		public void messageReceived(String message);
	}
}