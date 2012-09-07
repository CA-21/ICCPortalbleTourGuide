package com.yuco;

import java.io.File;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Used for finding the battery level of an Android-based phone.
 * 
 * @author Mihai Fonoage
 * 
 */
public class ControlViewActivity extends AppBase {

	/** Called when the activity is first created. */
	private TextView batterLevel;
	float BackLightValue = 0.5f; // dummy default value
	private static String TAG = "ControlViewActivity";
	int accoutnType = USER;

	@Override
	/**
	 * Called when the current activity is first created.
	 */
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
		try{
		setContentView(R.layout.battery);
		}catch(Exception ex)
		{
			Log.d(TAG,ex.getMessage());
		}
		extras = getIntent().getExtras();
		if (extras != null)
			accoutnType = extras.getInt(ACCOUNT);

		LinearLayout ll = (LinearLayout) this
				.findViewById(R.id.battery_view_layout);
		batterLevel = (TextView) this.findViewById(R.id.batteryLevel);

		Button exit = (Button) this.findViewById(R.id.battery_view_back);

		exit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					Bundle bundle = new Bundle();
					bundle.putBoolean("restart", false);
					Intent intent = new Intent();
					intent.putExtras(bundle);
					setResult(RESULT_OK, intent);
					finish();
				} catch (Exception ex) {
					Log.v(TAG, ex.getMessage());
				}
			}

		});

		Button restart = (Button) this.findViewById(R.id.battery_view_exit);

		restart.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				try {
					Bundle bundle = new Bundle();
					bundle.putBoolean("restart", true);
					Intent intent = new Intent();
					intent.putExtras(bundle);
					setResult(RESULT_OK, intent);
					finish();
				} catch (Exception ex) {
					Log.v(TAG, ex.getMessage());
				}
			}

		});
		

		batteryLevel();
		/*int SysBackLightValue = 125;
		try {

			SysBackLightValue = android.provider.Settings.System.getInt(
					getContentResolver(),
					android.provider.Settings.System.SCREEN_BRIGHTNESS);
		} catch (Exception ex) {
			Log.v("UpdateBrightness", ex.getMessage());
		}*/
		SeekBar BackLightControl = (SeekBar) findViewById(R.id.backlightcontrol);
//		float value = (float)((SysBackLightValue / 255.0) * 100);*/

//		BackLightControl.setProgress((int) value);
		final TextView BackLightSetting = (TextView) findViewById(R.id.backlightsetting);
//		BackLightSetting.setText(String.valueOf(value)+"%");*/
		Button UpdateSystemSetting = (Button) findViewById(R.id.updatesystemsetting);

		UpdateSystemSetting.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					if (BackLightValue < 0.1)
						BackLightValue = (float) 0.1;
					int SysBackLightValue = (int) (BackLightValue * 255);

					android.provider.Settings.System.putInt(
							getContentResolver(),
							android.provider.Settings.System.SCREEN_BRIGHTNESS,
							SysBackLightValue);
				} catch (Exception ex) {
					Log.v("UpdateBrightness", ex.getMessage());
				}

			}
		});
		Button install_but = (Button) this.findViewById(R.id.battery_view_install);
		install_but.setVisibility(View.GONE);
		Button quit = (Button) this.findViewById(R.id.battery_view_quit);
		quit.setVisibility(View.GONE);
		if (accoutnType == ADMIN) {
/*			Button install_but = new Button(this, null, android.R.attr.buttonStyleSmall);
			install_but.setText("Install Update Application");

			ll.addView(install_but);*/
			install_but.setVisibility(View.VISIBLE);
			install_but.setOnClickListener(new Button.OnClickListener() {

				
				public void onClick(View arg0) {
					try {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setDataAndType(Uri.fromFile(new File(Environment
								.getExternalStorageDirectory().getPath()
								+ "/PortableTourGuide.apk")),
								"application/vnd.android.package-archive");
						startActivity(intent);
					} catch (Exception ex) {

					}

				}
			});
			
			quit.setVisibility(View.VISIBLE);
			quit.setOnClickListener(new View.OnClickListener() {

				
				public void onClick(View arg0) {

					try {
						Bundle bundle = new Bundle();
						bundle.putBoolean("quit", true);
						Intent intent = new Intent();
						intent.putExtras(bundle);
						setResult(RESULT_OK, intent);
						finish();
					} catch (Exception ex) {
						Log.v(TAG, ex.getMessage());
					}
				}

			});
			Button reboot = new Button(this);
			reboot.setText("Reboot");
			ll.addView(reboot);
			reboot.setOnClickListener(new View.OnClickListener() {

				
				public void onClick(View arg0) {

					try {
						Intent i = new Intent();
						i.setAction(Intent.ACTION_REBOOT);
						sendBroadcast(i);
					} catch (Exception ex) {
						Log.v(TAG, ex.getMessage());
					}
				}

			});
			Button shutdown = new Button(this);
			shutdown.setText("shutdown");
			ll.addView(shutdown);
			shutdown.setOnClickListener(new View.OnClickListener() {

				
				public void onClick(View arg0) {

					try {
						Intent i = new Intent();
						i.setAction(Intent.ACTION_REBOOT);
						sendBroadcast(i);
					} catch (Exception ex) {
						Log.v(TAG, ex.getMessage());
					}
				}

			});


		}

		BackLightControl
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					public void onProgressChanged(SeekBar arg0, int arg1,
							boolean arg2) {
						// TODO Auto-generated method stub
						BackLightValue = (float) arg1 ;
						BackLightSetting
								.setText(String.valueOf(BackLightValue)+"%");

						WindowManager.LayoutParams layoutParams = getWindow()
								.getAttributes();
						layoutParams.screenBrightness = BackLightValue;
						getWindow().setAttributes(layoutParams);

					}

					public void onStartTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}

					public void onStopTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}
				});
	}



	private void batteryLevel() {
		BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				context.unregisterReceiver(this);
				int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,
						-1);
				int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
				int level = -1;
				if (rawlevel >= 0 && scale > 0) {
					level = (rawlevel * 100) / scale;
				}
				batterLevel.setText("Battery Level Remaining: " + level + "%");
			}
		};
		IntentFilter batteryLevelFilter = new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(batteryLevelReceiver, batteryLevelFilter);
	}

}