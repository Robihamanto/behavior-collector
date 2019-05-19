package com.example.cellphone_API4;


import com.example.cellphone_API4.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity {

	private EditText editUserId;
	private Spinner spinnerType;
	private EditText editSession;
	private ArrayAdapter<String> adapterType;
	private Button buttonEnter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialize();
		setListener();
		adapterType = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, new String[] { "Sitting", "Standing" });
		spinnerType.setAdapter(adapterType);
		
	}

	private void initialize() {
		editUserId = (EditText) findViewById(R.id.editUserId);
		spinnerType = (Spinner) findViewById(R.id.spinnerType);
		editSession = (EditText) findViewById(R.id.editSession);
		buttonEnter = (Button) findViewById(R.id.buttonEnter);
	}
	
	private void setListener() {
		buttonEnter.setOnClickListener(buttonEnterClickListener);
		
	}

	private Button.OnClickListener buttonEnterClickListener = new Button.OnClickListener(){
		@Override
		public void onClick(View v){
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, SettingConfirmationActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("UserId", editUserId.getText().toString());
			bundle.putString("Type", spinnerType.getSelectedItem().toString());
			bundle.putString("Session", editSession.getText().toString());
			intent.putExtras(bundle);
			startActivity(intent);
			MainActivity.this.finish();
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

    
}
