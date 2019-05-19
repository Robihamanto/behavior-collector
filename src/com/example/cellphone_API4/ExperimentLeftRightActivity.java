package com.example.cellphone_API4;

import com.example.cellphone_API4.R;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ExperimentLeftRightActivity extends FragmentActivity implements
		OnTouchListener, SensorEventListener {

	protected float[] orientationValues;
	private TextView textOriX;
	private TextView textOriY;
	private TextView textOriZ;
	private TextView textCount;
	private TextView textSpecificNum;
	private SensorManager sensorMgt;
	private HorizontialListView myList;
	private Sensor accelerometer;
	private Sensor magnetometer;
	private Bundle bundle;
	private int sessionCount;
	private int numCount;
	private int flickCount;
	private String userId;
	private String session;
	private String mode;
	private String type;
	private int specificNum;
	private ArrayList<String> numStrList;
	private int[][] specifiedNumMetrix;
	private SpecificNumFileReader numFileReader = new SpecificNumFileReader();
	private int totalSessionNum;
	private int totalSpecificNum;
	private FileWriter txtFileWriter;
	private float[] mGravity;
	private float[] mGeomagnetic;

	ArrayList<Integer> List = new ArrayList<Integer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_experiment_left_right);
		initialize();

		getBundle();
		getTotalSessionNum(mode);
		getSpecifiedNumMetrix(mode);
		getTotalSpecificNum(mode);
		initializeText();
		setListener();

		if (mode.equals("LeftRight")) {
			showExperimentStartDialog(myList, 0);
		} else if (mode.equals("RightLeft")) {
			showExperimentStartDialog(myList, numStrList.size());
		}
	}

	private void getTotalSessionNum(String mode) {
		totalSessionNum = numFileReader.getTotalSessionNum(mode);
	}

	private void getSpecifiedNumMetrix(String mode) {
		specifiedNumMetrix = numFileReader.getSpecificNumMetrix(mode);
	}

	private void getTotalSpecificNum(String mode) {
		totalSpecificNum = numFileReader.getTotalSpecificNum(mode);
	}

	private void initializeText() {
		sessionCount = Integer.parseInt(session);
		numCount = 1;
		flickCount = 0;
		specificNum = specifiedNumMetrix[sessionCount - 1][numCount - 1];
		textCount.setText("SESSION: " + sessionCount + "  NUM: " + numCount
				+ "  FLICK: " + flickCount);
		textSpecificNum.setText(Integer.toString(specificNum));
	}

	private void setListener() {
		numStrList = getNumStringList();

		myList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.num_item_horizontial_list_view, numStrList));

		myList.setOnTouchListener(this);
		myList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				long currentTime = System.currentTimeMillis();
				long currentNanoTime = System.nanoTime();

				if (mode.equals("LeftRight")) {
					myList.setSelection(0); // �^�쳻�ݩΩ���
				} else if (mode.equals("RightLeft")) {
					myList.setSelection(numStrList.size());
				}

				String flickRec, oriRec, touchRec;
				flickRec = userId + "," + type + "," + mode + ","
						+ sessionCount + "," + numCount + "," + specificNum
						+ "," + numStrList.get(arg2) + "," + flickCount
						+ ",click," + currentTime + "," + currentNanoTime + ",";

				oriRec = "0,0,0,";

				touchRec = "0,0,0,0";

				Log.d("kizax1", flickRec + oriRec + touchRec);

				writerTask(flickRec + oriRec + touchRec);

				if (numCount == totalSpecificNum
						&& sessionCount == totalSessionNum) {
					// showExperimentEndDialog();

					if (mode.equals("LeftRight")) {
						Toast.makeText(ExperimentLeftRightActivity.this,
								"Reading level - from left to right end of the experiment", Toast.LENGTH_LONG).show();
						bundle.putBoolean("LeftRight", false);
					} else if (mode.equals("RightLeft")) {
						Toast.makeText(ExperimentLeftRightActivity.this,
								"Reading level - from right to left end of the experiment", Toast.LENGTH_LONG).show();
						bundle.putBoolean("RightLeft", false);
					}
					Intent intent = new Intent();
					intent.setClass(ExperimentLeftRightActivity.this,
							SelectExperimentModeActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
					ExperimentLeftRightActivity.this.finish();
				}

				flickCount = 0;
				if (numCount == totalSpecificNum) {
					numCount = 1;
					sessionCount++;
					// textCount.setBackgroundColor(Color.RED);
					showMsgDialog();
				} else {

					numCount++;

				}
				Log.d("kizax", "NUMCOUNT: " + numCount);
				if (sessionCount <= totalSessionNum) {
					specificNum = specifiedNumMetrix[sessionCount - 1][numCount - 1];
					textCount.setText("SESSION: " + sessionCount + "  NUM: "
							+ numCount + "  FLICK: " + flickCount);
					textSpecificNum.setText(Integer.toString(specificNum));
				} else {
					textCount.setText("End of the experiment");
					textSpecificNum.setText("??");
				}
			}
		});
		Log.d("kizax", "2");
	}

	private void initialize() {
		textOriX = (TextView) findViewById(R.id.textOriX);
		textOriY = (TextView) findViewById(R.id.textOriY);
		textOriZ = (TextView) findViewById(R.id.textOriZ);
		textCount = (TextView) findViewById(R.id.textCount);
		textSpecificNum = (TextView) findViewById(R.id.textSpecificNum);
		myList = (HorizontialListView) findViewById(R.id.horizontiallistview);

		String txtFileName = String.format(
				"%1$tYY~%1$tb%1$td_%1$tk-%1$tM-%1$tS_cellphone_API4.csv",
				System.currentTimeMillis());

		File SDCardpath = Environment.getExternalStorageDirectory();
		File myDataPath = new File(SDCardpath.getAbsolutePath()
				+ "/expData/expData_" + txtFileName);

		if (!myDataPath.getParentFile().exists()) {
			myDataPath.getParentFile().mkdirs();
			try {
				myDataPath.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			txtFileWriter = new FileWriter(myDataPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getBundle() {
		bundle = this.getIntent().getExtras();
		userId = bundle.getString("UserId");
		session = bundle.getString("Session");
		mode = bundle.getString("Mode");
		type = bundle.getString("Type");
	}

	public ArrayList<String> getNumStringList() {
		int totalNum = 39; // �W�L��flicking�|���t
		ArrayList<String> numStringList = new ArrayList<String>();

		String numStr;
		for (int i = 1; i <= totalNum; i++) {
			if (i < 10) {
				numStr = "0" + Integer.toString(i);
			} else {
				numStr = Integer.toString(i);
			}
			numStringList.add(numStr);
		}

		return numStringList;
	}

	private void setOriText(float[] orientationValues) {
		String format = "%1$s %2$-10s: ";
		Log.d("ori", "orix: " + orientationValues[1]);
		textOriX.setText(String.format(format, "X", "(pitch)")
				+ orientationValues[1]);
		Log.d("ori", "oriy: " + orientationValues[2]);
		textOriY.setText(String.format(format, "Y", "(roll)")
				+ orientationValues[2]);
		Log.d("ori", "oriz: " + orientationValues[0]);
		textOriZ.setText(String.format(format, "Z", "(azimuth)")
				+ orientationValues[0]);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.d("kizax1", "TOUCH");
		if (orientationValues != null) {
			String flickRec, oriRec, touchRec;
			long currentTime = System.currentTimeMillis();
			long currentNanoTime = System.nanoTime();
			float rawX = event.getRawX();
			float rawY = event.getRawY();
			float touchPressure = event.getPressure();
			float touchSize = event.getSize();
			double azimut = Math.toDegrees(orientationValues[0]);
			double pitch = Math.toDegrees(orientationValues[1]);
			double roll = Math.toDegrees(orientationValues[2]);
			
			long flickStartTime = 0, flickEndTime = 0;

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:

				flickStartTime = currentTime;
				
				flickCount++;

				setOriText(orientationValues);

				flickRec = userId + "," + type + "," + mode + ","
						+ sessionCount + "," + numCount + "," + specificNum
						+ "," + "-1" + "," + flickCount + ",down,"
						+ currentTime + "," + currentNanoTime + ",";

				oriRec = pitch + "," + roll + "," + azimut + ",";

				touchRec = Float.toString(rawX) + "," + Float.toString(rawY)
						+ "," + Float.toString(touchPressure) + ","
						+ Float.toString(touchSize);
				Log.d("kizax1", flickRec + oriRec + touchRec);

				writerTask(flickRec + oriRec + touchRec);

				break;
			case MotionEvent.ACTION_MOVE:

				setOriText(orientationValues);

				flickRec = userId + "," + type + "," + mode + ","
						+ sessionCount + "," + numCount + "," + specificNum
						+ "," + "-1" + "," + flickCount + ",move,"
						+ currentTime + "," + currentNanoTime + ",";

				oriRec = pitch + "," + roll + "," + azimut + ",";

				touchRec = Float.toString(rawX) + "," + Float.toString(rawY)
						+ "," + Float.toString(touchPressure) + ","
						+ Float.toString(touchSize);
				Log.d("kizax1", flickRec + oriRec + touchRec);

				writerTask(flickRec + oriRec + touchRec);
				break;
			case MotionEvent.ACTION_UP:
				
				flickEndTime = currentTime;
				
				if (flickEndTime - flickStartTime <= 0){
					
				}
				
				setOriText(orientationValues);

				flickRec = userId + "," + type + "," + mode + ","
						+ sessionCount + "," + numCount + "," + specificNum
						+ "," + "-1" + "," + flickCount + ",up," + currentTime
						+ "," + currentNanoTime + ",";

				oriRec = pitch + "," + roll + "," + azimut + ",";

				touchRec = Float.toString(rawX) + "," + Float.toString(rawY)
						+ "," + Float.toString(touchPressure) + ","
						+ Float.toString(touchSize);

				writerTask(flickRec + oriRec + touchRec);

				textCount.setText("SESSION: " + sessionCount + "  NUM: "
						+ numCount + "  FLICK: " + flickCount);
				textSpecificNum.setText(Integer.toString(specificNum));
				break;
			}
		} else {
			textOriX.setText("sensor�ثe�S����s");
			textOriY.setText("sensor�ثe�S����s");
			textOriZ.setText("sensor�ثe�S����s");
		}
		return super.onTouchEvent(event);

	}

	private void showMsgDialog() {
		TempStopDialog dialog = new TempStopDialog();
		dialog.show(getSupportFragmentManager(), null);
	}

	private void showExperimentEndDialog() {
		ExperimentEndDialog dialog = new ExperimentEndDialog();
		dialog.show(getSupportFragmentManager(), null);
	}

	private void showConnectionMsgDialog(String msg) {
		ConnectionMsgDialog dialog = new ConnectionMsgDialog(msg);
		dialog.show(getSupportFragmentManager(), null);
	}

	private void showExperimentStartDialog(
			HorizontialListView horizontialListView, int position) {
		ExperimentStartDialog dialog = new ExperimentStartDialog(
				horizontialListView, position);
		dialog.show(getSupportFragmentManager(), null);

	}

	private void writerTask(String record) {
		WriterThread connectThread = new WriterThread(txtFileWriter, record);
		connectThread.start();
	}

	@Override
	public void onResume() {
		sensorMgt = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = sensorMgt.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnetometer = sensorMgt.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensorMgt.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_FASTEST);
		sensorMgt.registerListener(this, magnetometer,
				SensorManager.SENSOR_DELAY_FASTEST);
		super.onResume();
	}

	@Override
	public void onDestroy() {
		try {
			txtFileWriter.flush();
			txtFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sensorMgt.unregisterListener(this);
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_experiment, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("����").setMessage("�T�w���}����?")
				.setPositiveButton("�T�w", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						finish();
					}
				})
				.setNegativeButton("����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				}).show();
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		long currentTime = System.currentTimeMillis();
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			mGravity = event.values;
		}
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			mGeomagnetic = event.values;
		}
		if (mGravity != null && mGeomagnetic != null) {
			float R[] = new float[9];
			float I[] = new float[9];
			boolean success = SensorManager.getRotationMatrix(R, I, mGravity,
					mGeomagnetic);
			if (success) {
				float orientation[] = new float[3];
				SensorManager.getOrientation(R, orientation);

				orientationValues = orientation;
				// double azimut = Math.toDegrees(orientation[0]);
				// double pitch = Math.toDegrees(orientation[1]);
				// double roll = Math.toDegrees(orientation[2]);

				// String flickRec = " , , , , , , , ,";
				// String oriRec = " , , ,";
				// String touchRec = " , , , ,";
				// String oriSensorRec = currentTime + "," + pitch + "," + roll
				// + "," + azimut;
				//
				// writerTask(flickRec + oriRec + touchRec + oriSensorRec);
			}
		}
	}
}
