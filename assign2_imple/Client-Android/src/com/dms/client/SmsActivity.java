package com.dms.client;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SmsActivity extends Activity implements OnClickListener {

	private EditText phone_number, msg_content;
	private Button btnSendSMS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_activity);
		intializeUIelements();
	}

	private void intializeUIelements() {
		phone_number = (EditText) findViewById(R.id.ETphoneNumber);
		msg_content = (EditText) findViewById(R.id.ETmsgcontent);
		btnSendSMS = (Button) findViewById(R.id.BtnsendSMS);
		btnSendSMS.setOnClickListener(this);
	}

	protected void sendSMSMessage() {
		Log.i("Send SMS", "");
		
		String phoneNo = phone_number.getText().toString();
		String message = msg_content.getText().toString();

		try {
			SmsManager smsManager = SmsManager.getDefault();
//			String SENT = "SMS SENT";
//			PendingIntent sentPI;
//			sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);
//			smsManager.sendTextMessage(phoneNo, null, message, sentPI, null);
			smsManager.sendTextMessage(phoneNo, null, message, null, null);
			Toast.makeText(getApplicationContext(), "SMS sent.",
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"SMS faild, please try again.", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.BtnsendSMS:
			sendSMSMessage();
			break;
		}
	}
}
