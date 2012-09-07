package com.yuco;

import java.io.File;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.provider.*;

public class AppBase extends Activity {
	protected final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	protected ProgressDialog mDialog1;
	protected ProgressDialog mDialog2;
	protected boolean useTypefalce = false;
	protected static final int DIALOG1_KEY = 1;
	protected static final int DIALOG2_KEY = 2;
	protected static final int bTextSize = 40;
	protected static final int sTextSize = 18;
	protected Bundle extras;
	protected Button exit;
	protected static final String POSITION = "position";
	protected static final String PLAY_MODE = "PlayMode";
	protected static final String LANGUAGE = "language";
	protected static final String ACCOUNT = "Account";
	protected static final int ADMIN = 0;
	protected static final int USER = 1;
	protected static final String TAG = "AppBase";
	private static final int STATIC_INTEGER_VALUE = 0;
	private static final String PUBLIC_STATIC_STRING_IDENTIFIER = null;
	protected int lang = 0;
	protected int position;
	protected static int chapter = -1;
	protected boolean isLongPress;
	EditText password;
	protected int mSecond;
	boolean debug = false;
	private int pressedCount = 0;
	protected boolean keyPressed;
	int title_color[] = { 51, 51, 51 };
	// private Handler mHandler = new Handler();

	public static final String PREFS_NAME = "MyPrefsFile";
	protected Long startTime;
	protected Handler handler = new Handler();
	protected TappableView screenSaver;
	private AppBase ACTIVITY;
	private Object RESTART_INTENT;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		/*
		 * extras = getIntent().getExtras(); debug = false; if (extras != null)
		 * { if (extras.getInt("DEBUG") > 0) { debug = true; } else { debug =
		 * false; } }
		 */
		 ACTIVITY = this;
		  RESTART_INTENT = PendingIntent.getActivity(this.getBaseContext(), 0, new Intent(getIntent()), getIntent().getFlags());
		  
		//
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
		//
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		// debug = true;
		isLongPress = false;

		// extras = getIntent().getExtras();
		screenSaver = new TappableView(this);
		screenSaver.addTapListener(onTap);
		// AbsoluteLayout al = (AbsoluteLayout)
		// findViewById(R.id.AbsoluteLayout01);
		// if(al!=null)al.addView(screenSaver);
		screenSaver.setVisibility(View.GONE);
		startTime = System.currentTimeMillis();
		// è¨­å®šå®šæ™‚è¦�åŸ·è¡Œçš„æ–¹æ³•
		handler.removeCallbacks(updateTimer);
		// è¨­å®šDelayçš„æ™‚é–“
		handler.postDelayed(updateTimer, 1000);

	}
	

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		View al = findViewById(R.id.AbsoluteLayout01);
		if (al != null)
			((ViewGroup) al).addView(screenSaver);
	}

	protected void tggleDebug() {
		debug = !debug;
	}

	protected TappableView.TapListener onTap = new TappableView.TapListener() {
		public void onTap(MotionEvent event) {

			screenSaver.setVisibility(View.GONE);
			startTime = System.currentTimeMillis();
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:

			LayoutInflater factory = LayoutInflater.from(this);
			final View textEntryView = factory.inflate(
					R.layout.alert_dialog_text_entry, null);
			password = (EditText) textEntryView
					.findViewById(R.id.password_edit);
			password.setText("");
			return new AlertDialog.Builder(this)
					// .setIcon(R.drawable.alert_dialog_icon)
					.setTitle("Please enter password").setView(textEntryView)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

									/* User clicked OK so do some stuff */
									isLongPress = false;

									if (password != null) {
										String s = password.getText()
												.toString();
										String pw = getString(R.string.password);
										if (s.equals(pw)) {
											try {
												Intent intent = new Intent(
														AppBase.this,
														ControlViewActivity.class);
												intent.putExtra(ACCOUNT,USER);

												startActivityForResult(intent,
														0);
											} catch (Exception ex) {
												Log.v("AppBase", ex
														.getMessage());
											}
											password.setText("");
											/*Intent intent = new Intent();
											intent.setClass(AppBase.this, LanguageSelectionView.class);
											startActivity(intent);
											finish();*/
										}
										/*if (s.equals("restart")) {
											AlarmManager mgr = (AlarmManager)ACTIVITY.getSystemService(Context.ALARM_SERVICE);
											mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, (PendingIntent) RESTART_INTENT);
											System.exit(2);
										}*/
										else if(s.equals("2838"))
										{
											try {
												Intent intent = new Intent(
														AppBase.this,
														ControlViewActivity.class);
												intent.putExtra(ACCOUNT,ADMIN);

												startActivityForResult(intent,
														0);
											} catch (Exception ex) {
												Log.v("AppBase", ex
														.getMessage());
											}
											password.setText("");
//											exit();
//											UIHelper.killApp(true);

										}else if(s.equals("yuco123"))
										{
											Intent settingsActivity = new Intent(getBaseContext(),
	                                                Preferences.class);
											startActivity(settingsActivity);
											password.setText(""); 
										}
										/*else if (s.equals("install")) {
											try {
												Intent intent = new Intent(
														Intent.ACTION_VIEW);
												intent
														.setDataAndType(
																Uri
																		.fromFile(new File(
																				Environment
																						.getExternalStorageDirectory()
																						.getPath()
																						+ "/PortableTourGuide.apk")),
																"application/vnd.android.package-archive");
												startActivity(intent);
											} catch (Exception ex) {

											}

										} else if (s.equals("battery")) {
											try {
												Intent intent = new Intent(
														AppBase.this,
														ControlViewActivity);
												intent.putExtra(POSITION,
														position);
												intent.putExtra(PLAY_MODE, -1);
												intent.putExtra(LANGUAGE, lang);

												startActivityForResult(intent,
														0);
											} catch (Exception ex) {
												Log.v("AppBase", ex
														.getMessage());
											}

										}*/
										else{
											password.setText("");
										}
									}
									// exit();
								}
								
							}).setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									password.setText("");
									/* User clicked cancel so do some stuff */
									isLongPress = false;
								}
							}).create();
		case DIALOG1_KEY: {
			ProgressDialog dialog = new ProgressDialog(this);
			String[] title = getResources().getStringArray(
					R.array.loading_title);
			// String[] desc =
			// getResources().getStringArray(R.array.loading_desc);
			dialog.setTitle(title[lang]);
			// dialog.setMessage(desc[lang]);
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			return dialog;
		}
			/*
			 * case DIALOG2_KEY: { ProgressDialog dialog = new
			 * ProgressDialog(this); String[] desc =
			 * getResources().getStringArray(R.array.loading_desc);
			 * dialog.setMessage(desc[lang]); dialog.setIndeterminate(true);
			 * dialog.setCancelable(true); return dialog; }
			 */

		}
		return null;

	}

	private void Return() {
		try {
			/*
			 * Intent resultIntent = new Intent();
			 * resultIntent.putExtra(POSITION, position);
			 * resultIntent.putExtra(PLAY_MODE, chapter);
			 * resultIntent.putExtra(LANGUAGE, lang);
			 * setResult(Activity.RESULT_OK, resultIntent); finish();
			 */
			finish();
			Intent intent = new Intent();
			intent.setClass(this, ZoneSelectionView.class);

			intent.putExtra(POSITION, position);
			intent.putExtra(PLAY_MODE, chapter);
			intent.putExtra(LANGUAGE, lang);
			startActivity(intent);

		} catch (Exception ex) {

		}
	}

	protected void exit() {
		try {
			finish();

		} catch (Exception ex) {

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					if(extras.getBoolean("restart"))
					{
						Intent intent = new Intent();
						intent.setClass(AppBase.this, LanguageSelectionView.class);
						startActivity(intent);
						finish();
					}
					if(extras.getBoolean("quit"))
					{
						exit();
						UIHelper.killApp(true);
					}
				}
			}
		}
	}

	// private long mStartTime = 0L;
	// protected OnTouchListener ButtonTouchListener = new OnTouchListener() {
	//
	// public boolean onTouch(View v, MotionEvent event) {
	// // TODO Auto-generated method stub
	// switch (event.getAction()) {
	//
	// case MotionEvent.ACTION_DOWN: {
	// // Ã¦Å’â€°Ã¤Â½ï¿½Ã¤Âºâ€¹Ã¤Â»Â¶Ã¥ï¿½â€˜Ã§â€�Å¸Ã¥ï¿½Å½Ã¦â€°Â§Ã¨Â¡Å’Ã¤Â»Â£Ã§Â ï¿½Ã§Å¡â€žÃ¥Å’ÂºÃ¥Å¸Å¸
	//
	// if (mStartTime == 0L) {
	// mStartTime = System.currentTimeMillis();
	// mHandler.removeCallbacks(mUpdateTimeTask);
	// mHandler.postDelayed(mUpdateTimeTask, 100);
	// }
	//
	// break;
	// }
	// case MotionEvent.ACTION_MOVE: {
	// // Ã§Â§Â»Ã¥Å Â¨Ã¤Âºâ€¹Ã¤Â»Â¶Ã¥ï¿½â€˜Ã§â€�Å¸Ã¥ï¿½Å½Ã¦â€°Â§Ã¨Â¡Å’Ã¤Â»Â£Ã§Â ï¿½Ã§Å¡â€žÃ¥Å’ÂºÃ¥Å¸Å¸
	// break;
	// }
	// case MotionEvent.ACTION_UP: {
	// mStartTime = 0L;
	// mHandler.removeCallbacks(mUpdateTimeTask);
	// break;
	// }
	//
	// default:
	//
	// break;
	// }
	// return false;
	// }
	// };

	@Override
	protected void onStop() {
		super.onStop();

		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		// editor.putBoolean("silentMode", mSilentMode);

		// Commit the edits!
		editor.commit();
		handler.removeCallbacks(updateTimer);
		Log.v(TAG, "onStop");
	}

	protected void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy");
		screenSaver.removeTapListener(onTap);

	}

	@Override
	public void onBackPressed() {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.v("onKeyDown", "key: " + keyCode);
		return false;
	}

	// private Runnable mUpdateTimeTask = new Runnable() {
	// public void run() {
	// final long start = mStartTime;
	// long millis = SystemClock.uptimeMillis() - start;
	// int seconds = (int) (millis / 1000);
	// int minutes = seconds / 60;
	// seconds = seconds % 60;
	//
	// if (seconds == 10) {
	// showDialog(0);
	// }
	// mHandler.postAtTime(this, start
	// + (((minutes * 60) + seconds + 1) * 1000));
	//
	// }
	// };
	// å›ºå®šè¦�åŸ·è¡Œçš„æ–¹æ³•
	private Runnable updateTimer = new Runnable() {
		public void run() {

			Long spentTime = System.currentTimeMillis() - startTime;
			// è¨ˆç®—ç›®å‰�å·²é�Žåˆ†é�˜æ•¸
			Long minius = (spentTime / 1000) / 60;
			// è¨ˆç®—ç›®å‰�å·²é�Žç§’æ•¸
			Long seconds = (spentTime / 1000) % 60;
			//Log.d("Timer:", "spentTime" + spentTime + "minus " + minius
			//		+ "second " + seconds);
			handler.postDelayed(this, 1000);
			if (spentTime > 1000 * 600) {
				screenSaver.setVisibility(View.VISIBLE);
				// add overlay image
			}
		}
	};
	/*
	 * protected Runnable onEverySecond = new Runnable() { public void run() {
	 * if (keyPressed) { pressedCount++; Toast.makeText(AppBase.this,
	 * "pressed count" + String.valueOf(pressedCount),
	 * Toast.LENGTH_SHORT).show(); } } };
	 */
	public Integer[] E1Chapter = { R.array.e11, R.array.e12, R.array.e13,
			R.array.e14, R.array.e15, R.array.e16, R.array.e17 };
	public Integer[] E2Chapter = { R.array.e21, R.array.e22, R.array.e23,
			R.array.e24, R.array.e25, R.array.e26, R.array.e27 };
	public Integer[] N1Chapter = { R.array.n11, R.array.n12, R.array.n13,
			R.array.n14, R.array.n15 };
	public Integer[] N2Chapter = { R.array.n21, R.array.n22, R.array.n23,
			R.array.n24, R.array.n25 };
	public Integer[] W1Chapter = { R.array.w11, R.array.w12, R.array.w13,
			R.array.w14, R.array.w15 };
	public Integer[] W2Chapter = { R.array.w21, R.array.w22, R.array.w23,
			R.array.w24 };
	public Integer[] S1Chapter = { R.array.s11, R.array.s12, R.array.s13,
			R.array.s14, R.array.s15 };
	public Integer[] S2Chapter = { R.array.s21, R.array.s22, R.array.s23,
			R.array.s24, R.array.s25, R.array.s26, R.array.s27 };
	public Integer[] ChaptersLayout = { R.layout.chapter_e1,
			R.layout.chapter_e2, R.layout.chapter_n1, R.layout.chapter_n2,
			R.layout.chapter_w1, R.layout.chapter_w2, R.layout.chapter_s1,
			R.layout.chapter_s2 };
	public Integer[][] Chapters = { E1Chapter, E2Chapter, N1Chapter, N2Chapter,
			W1Chapter, W2Chapter, S1Chapter, S2Chapter };

	public Integer[] TimeCodes = { R.array.timecodeE1, R.array.timecodeE2,
			R.array.timecodeN1, R.array.timecodeN2, R.array.timecodeW1,
			R.array.timecodeW2, R.array.timecodeS1, R.array.timecodeS2 };
};
