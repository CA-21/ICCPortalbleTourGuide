package com.yuco;

import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Button;

public class LanguageSelectionView extends Activity{//AppBase {
	private static final String LANGUAGE = "language";
	private static int lang = 0;
	/** Called when the activity is first created. */
	private LayoutInflater mInflater;
	static Button buttons[];
	static Button exit;

	private static final String TAG = "yuco";
	private boolean pmOn;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*
		 * File tempFile = Environment.getExternalStorageDirectory();

		// for archos only************************
		File files[] = null;
		File folder = null;
		folder = new File(tempFile.getPath() +// "/Video");
		 "/Video/"+ "ZoneS2" + "/" + "0");
		files = folder.listFiles();
		
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(files[0].getPath())));*/
		/*startActivity(new  Intent("android.intent.action.LAUNCH_AVOS",
				Uri.parse("ACTION:video_internal")));*/
		
		//startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=DTPP1xm5nc4")));
		setContentView(R.layout.main);
		buttons = new Button[5];
		buttons[0] = (Button) findViewById(R.id.Button01);
		buttons[1] = (Button) findViewById(R.id.Button02);
		buttons[2] = (Button) findViewById(R.id.Button03);
		buttons[3] = (Button) findViewById(R.id.Button04);
		buttons[4] = (Button) findViewById(R.id.Button05);
		for(int i = 0 ; i < 5 ; i++)
		{
			buttons[i].setFocusable(false);
		}

//		exit = (Button) findViewById(R.id.HomeButton);
//		exit.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//				 PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
//				 wl.acquire();
//				  // ..screen will stay on during this section..
//				 wl.release();
//
//			}
//		});

		

		buttons[0].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					finish();
					Intent i = new Intent();
					i.setClass(LanguageSelectionView.this,
							ZoneSelectionView.class);
					i.putExtra(LANGUAGE, 0);

					startActivity(i);
				} catch (Exception ex) {
				}
			}
		});
		buttons[1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					finish();
					Intent i = new Intent();
					i.setClass(LanguageSelectionView.this,
							ZoneSelectionView.class);
					i.putExtra(LANGUAGE, 1);
					
					startActivity(i);
				} catch (Exception ex) {
				}
			}
		});
		buttons[2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					finish();
					Intent i = new Intent();
					i.setClass(LanguageSelectionView.this,
							ZoneSelectionView.class);
					i.putExtra(LANGUAGE, 2);
					startActivity(i);
				} catch (Exception ex) {
				}
			}
		});
		buttons[3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					finish();
//					Intent intent = new Intent("android.intent.action.LAUNCH_AVOS",
//							Uri.parse("ACTION:sound_settings"));
					Intent i = new Intent();
					i.setClass(LanguageSelectionView.this,
							ZoneSelectionView.class);
					i.putExtra(LANGUAGE, 3);
					startActivity(i);
				} catch (Exception ex) {
				}
			}
		});
		buttons[4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					finish();
					Intent i = new Intent();
					i.setClass(LanguageSelectionView.this,
							ZoneSelectionView.class);
					i.putExtra(LANGUAGE, 4);
					startActivity(i);
				} catch (Exception ex) {
				}
			}
		});
	}

	public void onStart() {
		super.onStart();
		Log.v(TAG, "onStart");
	}

	public void onResume() {
		super.onResume();
		Log.v(TAG, "onResume");
	}

	public void onPause() {
		super.onPause();
		Log.v(TAG, "onPause");
	}

	public void onStop() {
		super.onStop();
		Log.v(TAG, "onStop");
	}

	public void onRestart() {
		super.onRestart();
		Log.v(TAG, "onReStart");
	}

	protected void onDestroy() {
		super.onDestroy();
		
		

	}
	@Override
	public void onBackPressed()
	{
		
	
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		return false;
	}
	

}