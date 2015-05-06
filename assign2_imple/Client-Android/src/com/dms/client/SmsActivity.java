package com.dms.client;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * a sms and a possible mms sending client. MMS sending still is mainly done by
 * default ACTION_SENDTO
 * 
 * @author yl
 *
 */
public class SmsActivity extends Activity {

	private EditText phoneNumber;
	private EditText smsBody;
	private Button smsManagerBtn;
	private Button smsSendToBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_activity);

		phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		smsBody = (EditText) findViewById(R.id.smsBody);
		smsManagerBtn = (Button) findViewById(R.id.smsManager);
		smsSendToBtn = (Button) findViewById(R.id.smsSIntent);

		smsManagerBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				sendSmsByManager();
			}
		});

		smsSendToBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				sendSmsBySIntent();
			}
		});

	}

	public void sendSmsByManager() {
		try {
			// Get the default instance of the SmsManager
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phoneNumber.getText().toString(), null,
					smsBody.getText().toString(), null, null);
			Toast.makeText(getApplicationContext(),
					"Your sms has successfully sent!", Toast.LENGTH_LONG)
					.show();
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(), "Your sms has failed...",
					Toast.LENGTH_LONG).show();
			ex.printStackTrace();
		}
	}

	public void sendSmsBySIntent() {
		Uri uri = Uri.parse("smsto:" + phoneNumber.getText().toString());

		Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
		smsSIntent.putExtra("sms_body", smsBody.getText().toString());
		try {
			startActivity(smsSIntent);
		} catch (Exception ex) {
			Toast.makeText(SmsActivity.this, "Your sms has failed...",
					Toast.LENGTH_LONG).show();
			ex.printStackTrace();
		}
	}

}
