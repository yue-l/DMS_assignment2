package com.dms.client;

import com.dms.client.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @author Yue
 *
 */
public class ChatRoomInputActivity extends Activity {
	private Button chatRoomBtn;
	private Button smsBtn;
	private Button nfcBtn;
	private EditText serverIpText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		serverIpText = (EditText) findViewById(R.id.editText1);
		chatRoomBtn = (Button) findViewById(R.id.chatRmBtn);
		chatRoomBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String ip = serverIpText.getText().toString();
				Client.SERVERIP = ip;
				Intent intent = new Intent(getBaseContext(),
						ChatRoomActivity.class);
				startActivity(intent);
			}
		});

		smsBtn = (Button) findViewById(R.id.smsBtn);
		smsBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getBaseContext(), SmsActivity.class);
				startActivity(intent);
			}
		});
		nfcBtn = (Button) findViewById(R.id.nfcBtn);
		nfcBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), NdefActivity.class);
				startActivity(intent);
			}
		});
	}

}
