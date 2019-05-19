package com.example.cellphone_API4;

import com.example.cellphone_API4.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingConfirmationActivity extends Activity {

	private Bundle bundle;
	private TextView textUserId;
	private TextView textType;
	private TextView textSession;
	private Button buttonConfirm;
	private String userId;
	private String type;
	private String session;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_confirmation);
		initialize();
		setListener();
		getBundle();
		initializeText();
	}

	public void initializeText(){
		textUserId.setText("User ID: " + userId);
		textType.setText("Type: " + type);
		textSession.setText("Session: " + session);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_setting_confirm, menu);
		return true;
	}

	private void initialize() {
		textUserId = (TextView) findViewById(R.id.textUserId);
		textType = (TextView) findViewById(R.id.textType);
		textSession = (TextView) findViewById(R.id.textSession);
		buttonConfirm = (Button) findViewById(R.id.buttonConfirm);

	}

	private void setListener() {
		buttonConfirm.setOnClickListener(buttonConfirmClickListener);
	}

	private Button.OnClickListener buttonConfirmClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			bundle.putBoolean("UpDown", true);
			bundle.putBoolean("DownUp", false);
			bundle.putBoolean("LeftRight", false);
			bundle.putBoolean("RightLeft", false);
			Intent intent = new Intent();
			intent.setClass(SettingConfirmationActivity.this, SelectExperimentModeActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			SettingConfirmationActivity.this.finish();
		}
	};

	private void getBundle() {
		bundle = this.getIntent().getExtras();
		userId = bundle.getString("UserId");
		type = bundle.getString("Type");
		session = bundle.getString("Session");
	}

}
