package com.example.cellphone_API4;

import com.example.cellphone_API4.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectExperimentModeActivity extends Activity {
	private Bundle bundle;
	private Button buttonUpDown;
	private Button buttonDownUp;
	private Button buttonLeftRight;
	private Button buttonRightLeft;
	private TextView textUserId;
	private TextView textType;
	private TextView textSession;
	private TextView textStatus;
	private String userId;
	private String type;
	private String session;
	private boolean isButtonUpDownEnabled;
	private boolean isButtonDownUpEnabled;
	private boolean isButtonLeftRightEnabled;
	private boolean isButtonRightLeftEnabled;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_experiment_mode);
		initialize();
		setListener();
		getBundle();
		setButton();
		initializeText();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_setting_confirm, menu);
		return true;
	}

	private void initialize() {
		buttonUpDown = (Button) findViewById(R.id.buttonUpDown);
		buttonDownUp = (Button) findViewById(R.id.buttonDownUp);
		buttonLeftRight = (Button) findViewById(R.id.buttonLeftRight);
		buttonRightLeft = (Button) findViewById(R.id.buttonRightLeft);
		textUserId = (TextView) findViewById(R.id.textUserId);
		textType = (TextView) findViewById(R.id.textType);
		textSession = (TextView) findViewById(R.id.textSession);
		textStatus = (TextView) findViewById(R.id.textStatus);
	}
	
	public void initializeText(){
		textUserId.setText("User ID: " + userId);
		textType.setText("Type: " + type);
		textSession.setText("Session: " + session);
	}

	private void setButton() {
		Log.d("ExpType", "#" + isButtonUpDownEnabled);
		Log.d("ExpType", "#" + isButtonDownUpEnabled);
		Log.d("ExpType", "#" + isButtonLeftRightEnabled);
		Log.d("ExpType", "#" + isButtonRightLeftEnabled);
		if(!isButtonUpDownEnabled){
			buttonUpDown.setEnabled(false);
			buttonUpDown.setBackgroundColor(Color.DKGRAY);
		}
		if(!isButtonDownUpEnabled){
			buttonDownUp.setEnabled(false);
			buttonDownUp.setBackgroundColor(Color.DKGRAY);
		}
		if(!isButtonLeftRightEnabled){
			buttonLeftRight.setEnabled(false);
			buttonLeftRight.setBackgroundColor(Color.DKGRAY);
		}
		if(!isButtonRightLeftEnabled){
			buttonRightLeft.setEnabled(false);
			buttonRightLeft.setBackgroundColor(Color.DKGRAY);
		}
		if(!isButtonUpDownEnabled && !isButtonDownUpEnabled && !isButtonLeftRightEnabled && !isButtonRightLeftEnabled){
			textStatus.setText("全部實驗結束");
			textStatus.setTextColor(Color.rgb(255, 140, 0));
		}
	}
	
	private void setListener() {
		buttonUpDown.setOnClickListener(upDownBtnClickListener);
		buttonDownUp.setOnClickListener(downUpBtnClickListener);
		buttonLeftRight.setOnClickListener(leftRightBtnClickListener);
		buttonRightLeft.setOnClickListener(rightLeftBtnClickListener);
	}

	private Button.OnClickListener upDownBtnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			bundle.putString("Mode", "UpDown");
			Intent intent = new Intent();
			intent.setClass(SelectExperimentModeActivity.this, ExperimentUpDownActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			SelectExperimentModeActivity.this.finish();
		}
	};
	
	private Button.OnClickListener downUpBtnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			bundle.putString("Mode", "DownUp");
			Intent intent = new Intent();
			intent.setClass(SelectExperimentModeActivity.this, ExperimentUpDownActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			SelectExperimentModeActivity.this.finish();
		}
	};
	
	private Button.OnClickListener leftRightBtnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			bundle.putString("Mode", "LeftRight");
			Intent intent = new Intent();
			intent.setClass(SelectExperimentModeActivity.this, ExperimentLeftRightActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			SelectExperimentModeActivity.this.finish();
		}
	};
	
	private Button.OnClickListener rightLeftBtnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			bundle.putString("Mode", "RightLeft");
			Intent intent = new Intent();
			intent.setClass(SelectExperimentModeActivity.this, ExperimentLeftRightActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			SelectExperimentModeActivity.this.finish();
		}
	};

	private void getBundle() {
		bundle = this.getIntent().getExtras();
		userId = bundle.getString("UserId");
		type = bundle.getString("Type");
		session = bundle.getString("Session");
		isButtonUpDownEnabled = bundle.getBoolean("UpDown");
		isButtonDownUpEnabled = bundle.getBoolean("DownUp");
		isButtonLeftRightEnabled = bundle.getBoolean("LeftRight");
		isButtonRightLeftEnabled = bundle.getBoolean("RightLeft");
	}
}
