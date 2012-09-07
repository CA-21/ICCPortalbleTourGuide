package com.yuco;

/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;

import com.yuco.R.id;

public class MediaPlayerClass extends AppBase implements
		OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,
		OnVideoSizeChangedListener, SurfaceHolder.Callback,
		OnSeekCompleteListener, OnErrorListener, ViewSwitcher.ViewFactory {
	BroadcastReceiver mReceiver;
	private static final String TAG = "MediaPlayerDemo";
	private int mVideoWidth;
	private int mVideoHeight;
	private MediaPlayer mPlayer;
	// private MediaPlayer aPlayer;
	private TappableSurfaceView mPreview;
	private SurfaceHolder holder;

	private Bundle extras;
	private static final String POSITION = "position";
	private static final String PLAY_MODE = "PlayMode";
	private static final String LANGUAGE = "language";

	private int chapter;
	private boolean mIsVideoSizeKnown = false;
	private boolean mIsVideoReadyToBePlayed = false;
	private static final String zones[] = { "ZoneE1", "ZoneE2", "ZoneN1",
			"ZoneN2", "ZoneW1", "ZoneW2", "ZoneS1", "ZoneS2" };
	//private static final String zones_title1[] = { "E", "E", "N", "N", "W","W", "S", "S" };
	private static final String zones_title2[] = { "1/", "2/", "1/", "2/",
			"1/", "2/", "1/", "2/" };
	File videoFile;
	private ProgressBar mSeekBar = null;
	TextView tv, tv2, tv3;
	String[] mChapter;

	// MediaController mc = null;
	Button chapterBut = null;
	ToggleButton pauseBut = null;
	Button nextBut = null;
	Button prevBut = null;
	int currVideoIdx = 0;
	private long lastActionTime = 0L;
	protected boolean isPaused;

	protected View panel;
	private Button exit;
	private boolean haveFile;

	private TextView currtime;
	private int cmin;
	private int csec;
	private int rmin;
	private int rsec;
	private int visiblecount = 0;

	private boolean bClick;
	private ProgressDialog dialog;
	private int[] timecode;
	private RelativeLayout rl3;
	private boolean bAddPo = false;;
	private LinearLayout ll;
	private Long saveTime;
	private boolean bSleeped = false;

	/**
	 * 
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle icicle) {

		super.onCreate(icicle);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
		extras = getIntent().getExtras();
		if (extras != null) {
			lang = extras.getInt(LANGUAGE);
			chapter = extras.getInt(PLAY_MODE);

			position = extras.getInt(POSITION);
		}
		lastActionTime = 0;

		ImageView po = new ImageView(this);
		po.setImageResource(R.drawable.po);

		// debug = true;

		setContentView(R.layout.mediapalyer);

		dialog = new ProgressDialog(this);
		String[] title = getResources().getStringArray(R.array.loading_title);
		dialog.setTitle(title[lang]);
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);

		// Typeface tf_tc = Typeface.createFromAsset(getAssets(),
		// "fonts/helveticaneue_light.otf");
		// Typeface tf_sc = Typeface.createFromAsset(getAssets(),
		// "fonts/helveticaneue_light.otf");
		Typeface tf_en = Typeface.createFromAsset(getAssets(),
				"fonts/helveticaneue_light.otf");
		// Typeface tf_jp = Typeface.createFromAsset(getAssets(),
		// "fonts/helveticaneue_light.otf");
		// Typeface tf_kr = Typeface.createFromAsset(getAssets(),
		// "fonts/helveticaneue_light.otf");
		// Typeface[] tf = { tf_tc, tf_sc, tf_en, tf_jp ,tf_jp};
		Typeface[] tf = { tf_en, tf_en, tf_en, tf_en, tf_en };
		currtime = (TextView) findViewById(R.id.currtime);

		mPreview = (TappableSurfaceView) findViewById(R.id.video_surface);
		mPreview.addTapListener(onTap2);
		// tell app the current time to activate the timer
		lastActionTime = SystemClock.elapsedRealtime();

		panel = findViewById(R.id.panel);

		useTypefalce = true;
		timecode = getResources().getIntArray(TimeCodes[position]);

		CreateFileList();

		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		exit = new Button(this);
		RelativeLayout rl = new RelativeLayout(this);
		rl.setPadding(0, 0, 0, 0);
		if (debug)
			rl.setBackgroundColor(Color.argb(50, 100, 100, 100));
		String[] sReturn = getResources().getStringArray(R.array.home);
		exit.setBackgroundResource(R.drawable.but_home_l);

		exit.setPadding(40, 10, 10, 5);
		exit.setTextSize(sTextSize);
		exit.setText(sReturn[lang]);
		exit.setGravity(Gravity.BOTTOM);
		exit.setTextColor(Color.WHITE);
		exit.setHighlightColor(Color.BLUE);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(WC,
				WC);
		param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
		rl.addView(exit, param);

		((ViewGroup) panel).addView(rl);

		exit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				isPaused = true;
				if (mPlayer != null)
					mPlayer.pause();
				//showDialog(DIALOG1_KEY);
				Return();
			}

		});

		ll = new LinearLayout(this);
		ll.setPadding(20, 0, 0, 0);

		tv = new TextView(getBaseContext());// (TextView)
		// findViewById(R.id.TextView01);
		//james kong 20120316
		//change all title direction header to specfi language
		//***************************************************************************
		String[] zones_title;
		switch(lang)
		{
			case 0: 
				zones_title = getResources().getStringArray(R.array.cht_heading_dir);
			break;
			case 1:
				zones_title = getResources().getStringArray(R.array.chs_heading_dir);
			break;
			case 2:
				zones_title = getResources().getStringArray(R.array.eng_heading_dir);
			break;
			case 3:
				zones_title = getResources().getStringArray(R.array.jap_heading_dir);
			break;
			case 4:
				zones_title = getResources().getStringArray(R.array.kor_heading_dir);
			break;
			default:
				zones_title = getResources().getStringArray(R.array.cht_heading_dir);
			break;
		}
		//***************************************************************************
		tv.setText(zones_title[position]);
		tv.setTextSize(bTextSize);
		tv.setTextColor(Color.rgb(title_color[0], title_color[2],
				title_color[2]));
		if (useTypefalce)
			tv.setTypeface(tf_en);

		tv2 = new TextView(getBaseContext());// (TextView)
		// findViewById(R.id.TextView02);
		tv2.setText(zones_title2[position]);
		tv2.setTextSize(18);
		tv2.setTextColor(Color.rgb(title_color[0], title_color[2],
				title_color[2]));
		if (useTypefalce)
			tv2.setTypeface(tf_en);
		if (currVideoIdx < Chapters[position].length) {
			mChapter = getResources().getStringArray(
					Chapters[position][currVideoIdx]);
		}

		tv3 = new TextView(getBaseContext());// (TextView)

		tv3.setText(mChapter[lang]);
		tv3.setTextSize(18);
		tv3.setGravity(Gravity.BOTTOM);
		tv3.setTextColor(Color.rgb(title_color[0], title_color[2],
				title_color[2]));
		// if (useTypefalce)
		// tv3.setTypeface(tf[lang]);
		LinearLayout.LayoutParams LLparam = new LinearLayout.LayoutParams(WC,
				WC);

		RelativeLayout rl2 = new RelativeLayout(this);
		RelativeLayout.LayoutParams param3 = new RelativeLayout.LayoutParams(
				WC, 45);
		{

			rl2.setPadding(0, 0, 0, 0);
			param3.addRule(RelativeLayout.ALIGN_BOTTOM, 1);
			param3.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1);
			rl2.addView(tv3, param3);
			// rl2.addView(po,param3);
		}

		ll.addView(tv, LLparam);
		ll.addView(tv2, LLparam);
		ll.addView(rl2, LLparam);

		rl3 = new RelativeLayout(this);
		RelativeLayout.LayoutParams param4 = new RelativeLayout.LayoutParams(
				WC, 67);

		rl3.setPadding(0, 0, 0, 0);
		param4.addRule(RelativeLayout.ALIGN_BOTTOM, 1);
		param4.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1);
		rl3.addView(po, param4);
		// ll.addView(rl3, LLparam);

		RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(
				WC, WC);
		param2.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
		rl.addView(ll, param2);

		mSeekBar = (ProgressBar) findViewById(R.id.SeekBar);

		setProgress(mSeekBar.getProgress());
		setSecondaryProgress(mSeekBar.getSecondaryProgress());

		chapterBut = (Button) findViewById(id.Chapter);
		String[] cbutstr = getResources().getStringArray(R.array.chapter);
		tv2.setTypeface(tf_en);
		chapterBut.setText(cbutstr[lang]);
		chapterBut.setPadding(20, 0, 0, 0);

		chapterBut.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					if (mPlayer != null)
						mPlayer.pause();
					goToChapter();

				} catch (Exception ex) {
				}
			}
		});
		pauseBut = (ToggleButton) findViewById(id.Pause);
		pauseBut.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				visiblecount = 0;
				if (mPlayer.isPlaying()) {
					mPlayer.pause();
					pauseBut.setChecked(true);
				} else {
					pauseBut.setChecked(false);
					mPlayer.start();
					lastActionTime = SystemClock.elapsedRealtime();

				}
			}

		});
		pauseBut.setChecked(false);

		nextBut = (Button) findViewById(id.Next);
		nextBut.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//dialog.show();
				PlayNext();
				visiblecount = 0;
				lastActionTime = SystemClock.elapsedRealtime();
			}

		});
		prevBut = (Button) findViewById(id.Prev);
		prevBut.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				//dialog.show();
				PlayPrev();
				visiblecount = 0;
				lastActionTime = SystemClock.elapsedRealtime();

			}

		});

		getWindow().setFormat(PixelFormat.TRANSPARENT);
	}

	@Override
	protected void onStart() {

		// initialize receiver
		System.out.println("onCreate1 ");
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		mReceiver = new ScreenReceiver();
		registerReceiver(mReceiver, filter);
		System.out.println("onCreate ");
		
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		unregisterReceiver(mReceiver);
		super.onStop();
	}

	public String millsecConverter(int in) {
		int csec = (int) (in / 1000.0f) % 60;
		int cmin = (int) (in / 1000.0f) / 60;
		String stringSec = null;
		;
		String stringMin = null;
		if (csec < 10)
			stringSec = "0" + String.valueOf(csec);
		else
			stringSec = String.valueOf(csec);
		if (cmin < 10)
			stringMin = "0" + String.valueOf(cmin);
		else
			stringMin = String.valueOf(cmin);
		String ret = (stringMin + ":" + stringSec);
		return ret;
	}

	public void goToChapter() {
		showDialog(DIALOG1_KEY);
		Intent intent = new Intent();
		intent.setClass(MediaPlayerClass.this, ChapterViewClass.class);
		intent.putExtra(POSITION, position);
		intent.putExtra(LANGUAGE, lang);
		// startActivityForResult(intent, 0);
		startActivity(intent);
		finish();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		dismissDialog(DIALOG1_KEY);
		if (resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			if (extras != null) {

				chapter = extras.getInt(PLAY_MODE);
				lang = extras.getInt(LANGUAGE);
				position = extras.getInt(POSITION);
				currVideoIdx = chapter;

				// prepareChapter();
			}
		}

		/*
		 * Log.i(TAG, "MainDriver main-activity got result from sub-activity");
		 * 
		 * if (resultCode == Activity.RESULT_CANCELED) {Log.i(TAG,
		 * "WidgetActivity was cancelled or encountered an error. resultcode == result_cancelled"
		 * ); Log.i(TAG, "WidgetActivity was cancelled - data ="+ bundle); }
		 * 
		 * Log.i(TAG, "MainDriver main-activity got result from sub-activity");
		 */

	}

	private TappableSurfaceView.TapListener onTap2 = new TappableSurfaceView.TapListener() {
		public void onTap(MotionEvent event) {

			lastActionTime = SystemClock.elapsedRealtime();
			setPanelVisibility();
		}
	};

	// private TappableSurfaceView.TapListener onTap = new
	// TappableSurfaceView.TapListener() {
	// public void onTap(MotionEvent event) {
	//
	// screenSaver.setVisibility(View.GONE);
	// startTime = System.currentTimeMillis();
	// if(mPlayer)mPlayer.set
	// }
	// };

	private void setPanelVisibility() {

		if (panel.getVisibility() == View.VISIBLE) {
			panel.setVisibility(View.GONE);
			//

		} else {
			panel.setVisibility(View.VISIBLE);
			lastActionTime = SystemClock.elapsedRealtime();

		}
		// mc.show(10000);

	}

	// running timecode detec thread panel apperaance counter
	private Runnable onEverySecond = new Runnable() {
		public void run() {
			// when the screen is about to turn off
			/*if (ScreenReceiver.screenOff) {

				// this is the case when onPause() is called by the system due to a
				// screen state change
				Log.d("onEverySecond", "SCREEN TURNED OFF");

			} else {
				// this is when onPause() is called when the screen state has not
				// changed
				Log.d("onEverySecond", "this is when onPause() is called when the screen state has not changed ");

			}*/
			if (panel.getVisibility() == View.VISIBLE) {
				if (lastActionTime > 0
						&& SystemClock.elapsedRealtime() - lastActionTime > 5000) {
					clearPanels(false);

					setPanelVisibility();

				}

				/*
				 * if (visiblecount > 5) { visiblecount = 0;
				 * setPanelVisibility(); } else visiblecount++;
				 */
			}

			if (mPlayer != null) {
				int progress2 = mPlayer.getCurrentPosition();

				mSeekBar.setSecondaryProgress(progress2);

				int currPos = mPlayer.getCurrentPosition();
				String output = millsecConverter(currPos + 2);
				int sMax = mPlayer.getDuration();
				String output2 = millsecConverter((sMax - currPos + 2));
				currtime.setText(output);
				int currentVidTime = (mPlayer.getCurrentPosition());
				// String mTAG = "mPlayer";
				// Log.v(mTAG, "Time: " + currentVidTime);
				// mTAG = "mPreview";
				// Log.v(mTAG, "Time: " + currPos);

				if (currVideoIdx + 1 < timecode.length) {
					// add 0.5 sec to pre cjheck the time code
					if (currentVidTime > timecode[currVideoIdx + 1]) {

						switch (chapter) {
						case -1:

							currVideoIdx++;
							prepareChapter2();
							// if (currVideoIdx < Chapters[position].length) {
							// mChapter = getResources().getStringArray(
							// Chapters[position][currVideoIdx]);

							// tv3.setText(mChapter[lang]);
							Log.v(TAG, "chapter name" + mChapter[lang]);
							Log.v(TAG, "video index" + currVideoIdx);

							// mSeekBar.setProgress(mPlayer.getCurrentPosition());
							// }
							break;
						default:
							isPaused = true;
							Log.v(TAG, "currentVidTime" + currentVidTime);
							Log.v(TAG, "chapter name" + mChapter[lang]);
							Log.v(TAG, "video index" + currVideoIdx);
							if (mPlayer != null) {
								// **pause before go to chapter
								// media player in other thread can see the
								// extra frame
								mPlayer.pause();
							}
							goToChapter();

							break;
						}
					}
				}

			}

			if (!isPaused) {
				if (mPreview != null) {
					mPreview.postDelayed(onEverySecond, 100);

				}

			}
		}

		private void clearPanels(boolean b) {
			// TODO Auto-generated method stub

		}
	};

	// private Runnable updateTimer = new Runnable() {
	// public void run() {
	//			
	// Long spentTime = System.currentTimeMillis() - startTime;
	// // è¨ˆç®—ç›®å‰�å·²é�Žåˆ†é�˜æ•¸
	// Long minius = (spentTime / 1000) / 60;
	// // è¨ˆç®—ç›®å‰�å·²é�Žç§’æ•¸
	// Long seconds = (spentTime / 1000) % 60;
	// Log.v("Timer:","spentTime"+spentTime+"minus "+minius+"second "+seconds);
	// handler.postDelayed(this, 1000);
	// if(spentTime>1000*600)
	// {
	// screenSaver.setVisibility(View.VISIBLE);
	// //add overlay image
	// }
	// }
	// };
	// as function name
	void prepareChapter() {
		// int duration = mPlayer.getDuration();
		// float seekTarget = (float) duration
		// / (float) Chapters[position].length;
		if (currVideoIdx < timecode.length) {
			mPlayer.seekTo(0);// seek to prev time lime cause inaccurate - seek
			// to 0 before
			mPlayer.seekTo(timecode[currVideoIdx]);
			Log.v(TAG, "time code " + timecode[currVideoIdx]);
			int progress1 = (int) (timecode[currVideoIdx]);
			mSeekBar.setProgress(progress1);
		}
		if (currVideoIdx < Chapters[position].length) {
			mChapter = getResources().getStringArray(
					Chapters[position][currVideoIdx]);
			tv3.setText(mChapter[lang]);
		}

		if (!bAddPo) {
			if (lang == 0 || lang == 1)
				if (position == 2 && currVideoIdx == 1) {
					ll.addView(rl3);
					tv3.setText("");
					bAddPo = true;
				}
		} else {
			ll.removeView(rl3);
			bAddPo = false;
		}
	}

	void prepareChapter2() {
		if (chapter == -1) {
			// int duration = mPlayer.getDuration();
			// float seekTarget = (float) duration
			// / (float) Chapters[position].length;
			if (currVideoIdx < timecode.length) {
				// mPlayer.seekTo(timecode[currVideoIdx] );
				int progress1 = (int) (timecode[currVideoIdx]);
				mSeekBar.setProgress(progress1);
			}
			if (currVideoIdx < Chapters[position].length) {
				mChapter = getResources().getStringArray(
						Chapters[position][currVideoIdx]);
				tv3.setText(mChapter[lang]);
				Log.v(TAG, mChapter[lang]);
			}

			if (!bAddPo) {
				if (lang == 0 || lang == 1)
					if (position == 2 && currVideoIdx == 1) {
						ll.addView(rl3);
						tv3.setText("");
						bAddPo = true;
					}
			} else {
				ll.removeView(rl3);
				bAddPo = false;
			}
		} else {
			goToChapter();
		}
	}

	protected boolean PlayNext() {// ****
		// TODO Auto-generated method stub
		int temp = currVideoIdx;
		currVideoIdx++;
		if (currVideoIdx >= 0 && currVideoIdx < timecode.length
				&& chapter == -1) {
			if (mPlayer != null) {

				prepareChapter();
			}

			return true;
		} else if (chapter >= 0) {
			if (mPlayer != null)
				mPlayer.pause();
			goToChapter();
			return false;
		} else// if(currVideoIdx == fileList.size()-1)
		{
			currVideoIdx = temp;
			isPaused = true;
			// mPlayer.stop();
			if (mPlayer != null)
				mPlayer.pause();
			Return();
			return false;
		}
	}

	protected boolean PlayPrev() {
		// TODO Auto-generated method stub
		int temp = currVideoIdx;
		currVideoIdx--;
		if (currVideoIdx >= 0 && currVideoIdx < timecode.length
				&& chapter == -1) {
			if (mPlayer != null) {

				prepareChapter();
			}

			return true;
		} else if (chapter >= 0) {
			if (mPlayer != null)
				mPlayer.pause();
			goToChapter();
			return false;
		} else// if(currVideoIdx == fileList.size()-1)
		{
			currVideoIdx = temp;
			isPaused = true;
			// mPlayer.stop();
			if (mPlayer != null)
				mPlayer.pause();
			Return();
			return false;
		}
		/*
		 * if (currVideoIdx >= 0 && currVideoIdx < Chapters[position].length &&
		 * chapter == -1) { if (mPlayer != null) {
		 * 
		 * // int duration = mPlayer.getDuration(); // float seekTarget =
		 * (float) duration // / (float) Chapters[position].length; //
		 * mPlayer.seekTo((int) (currVideoIdx * (seekTarget))); // mChapter =
		 * getResources().getStringArray( // Chapters[position][currVideoIdx]);
		 * // tv3.setText(mChapter[lang]); // int progress1 = (int)
		 * (timecode[currVideoIdx]) ; // mSeekBar.setProgress(progress1); int
		 * duration = mPlayer.getDuration(); float seekTarget = (float) duration
		 * / (float) Chapters[position].length; if (currVideoIdx <
		 * timecode.length) mPlayer.seekTo(timecode[currVideoIdx] ); //
		 * mPlayer.seekTo((int) (currVideoIdx * (seekTarget))); mChapter =
		 * getResources().getStringArray( Chapters[position][currVideoIdx]);
		 * tv3.setText(mChapter[lang]);
		 * 
		 * if (!bAddPo) { if (lang == 0 || lang == 1 ) if( position == 2 &&
		 * currVideoIdx == 1) { ll.addView(rl3); tv3.setText(""); bAddPo = true;
		 * } } else { ll.removeView(rl3); bAddPo = false; } return true; } }
		 * else// if(currVideoIdx == fileList.size()-1) { currVideoIdx = temp;
		 * isPaused = true; // mPlayer.stop(); Return(); return false; } return
		 * false;
		 */

	}

	private void Return() {
		try {

			/*
			 * Intent intent = new Intent();
			 * intent.setClass(MediaPlayerClass.this, ZoneSelectionView.class);
			 * 
			 * intent.putExtra(POSITION, position); intent.putExtra(LANGUAGE,
			 * lang); setResult(RESULT_OK, intent); finish();
			 */
			Intent intent = new Intent();
			intent.setClass(MediaPlayerClass.this, ZoneSelectionView.class);

			intent.putExtra(POSITION, position);
			intent.putExtra(LANGUAGE, lang);
			// setResult(RESULT_OK, intent);
			startActivity(intent);
			finish();

		} catch (Exception ex) {

		}
	}

	private void CreateFileList() {

		try {
			position = extras.getInt(POSITION);
			File files[] = null;
			File folder = null;
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				File tempFile = Environment.getExternalStorageDirectory();

				// for archos only************************
				folder = new File(tempFile.getPath() + // "/Video");
						"/Video/" + zones[position] + "/" + lang);// new

				files = folder.listFiles();

				for (int i = files.length - 1; i >= 0; i--) {
					if (files[i].getPath().endsWith("mp4")
							|| files[i].getPath().endsWith("mov")
							|| files[i].getPath().endsWith("avi")
							|| files[i].getPath().endsWith("wmv")
							|| files[i].getPath().endsWith("flv")) {
						videoFile = files[i];

					}
				}

			}
		} catch (Exception e) {
			Log.v(TAG, "CreateFileList " + e.getMessage());
			isPaused = true;
			// mPlayer.stop();
			Return();
		}
		if (videoFile != null) {
			haveFile = true;

		} else {

			Toast.makeText(MediaPlayerClass.this,
					"Media File Not found,Please check SD card",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (haveFile && chapter == -1) {
			currVideoIdx = 0;// fileList.size() - 1;
		} else if (haveFile && chapter > -1)
			currVideoIdx = chapter;

	}

	private void playVideo() {

		try {
			if (mPlayer == null) {
				mPlayer = new MediaPlayer();
				mPlayer.setScreenOnWhilePlaying(true);
			} else {
				// mPlayer.stop();
				mPlayer.reset();
			}

			mPlayer.setDataSource(videoFile.getPath());
			mPlayer.setDisplay(holder);
			mPlayer.prepare();
			mPlayer.setOnErrorListener(this);
			mPlayer.setOnSeekCompleteListener(this);
			mPlayer.setOnCompletionListener(this);
			mPlayer.setOnBufferingUpdateListener(this);
			mPlayer.setOnPreparedListener(this);

			mPlayer.setOnVideoSizeChangedListener(this);
			mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			// mPlayer.setWakeMode(getApplication(), CONTEXT_RESTRICTED);
			mPlayer.setVolume(100, 100);
			mSeekBar.setMax(mPlayer.getDuration());

			int progress1 = (int) (timecode[currVideoIdx]);
			mSeekBar.setSecondaryProgress(progress1);
			mSeekBar.setProgress(progress1);
			if (currVideoIdx < Chapters[position].length) {
				mChapter = getResources().getStringArray(
						Chapters[position][currVideoIdx]);

				tv3.setText(mChapter[lang]);
			}

			if (!bAddPo) {
				if (lang == 0 || lang == 1)
					if (position == 2 && currVideoIdx == 1) {
						ll.addView(rl3);
						tv3.setText("");
						bAddPo = true;
					}
			} else {
				ll.removeView(rl3);
				bAddPo = false;
			}

			if (currVideoIdx >= 0 && currVideoIdx < timecode.length
					&& chapter != -1) {
				if (mPlayer != null) {
					mPlayer.seekTo((int) (timecode[currVideoIdx]));

				}
			}

		} catch (Throwable t) {
			Log.e(TAG, "Exception in media prep", t);
			goBlooey(t);
			isPaused = true;
			// mPlayer.stop();
			Return();
		}

	}

	private void goBlooey(Throwable t) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Exception!").setMessage(t.toString())
				.setPositiveButton("OK", null).show();
	}

	public void onBufferingUpdate(MediaPlayer arg0, int percent) {
		Log.d(TAG, "onBufferingUpdate percent:" + percent);

	}

	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		Log.v(TAG, "onVideoSizeChanged called");
		if (width == 0 || height == 0) {
			Log.e(TAG, "invalid video width(" + width + ") or height(" + height
					+ ")");
			return;
		}
		mIsVideoSizeKnown = true;
		mVideoWidth = width;
		mVideoHeight = height;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	public void onPrepared(MediaPlayer mediaplayer) {
		mIsVideoReadyToBePlayed = true;
		Log.d(TAG, "onPrepared called");
		mVideoWidth = mPlayer.getVideoWidth();
		mVideoHeight = mPlayer.getVideoHeight();

		if (mVideoWidth != 0 && mVideoHeight != 0 && mIsVideoReadyToBePlayed
				&& mIsVideoSizeKnown && haveFile) {
			// holder.setFixedSize(mVideoWidth, mVideoHeight);

			// player.start();

			TextView remaintime = (TextView) findViewById(R.id.remaintime);
			String output = millsecConverter(mPlayer.getDuration());
			Log.d("MPlayer", "output duration " + output);

			remaintime.setText(output);
			startVideoPlayback();
		} else {
			isPaused = true;
			// mPlayer.stop();
			Return();
		}
		// dialog.dismiss();

	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
		Log.d(TAG, "surfaceChanged called");

	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		Log.d(TAG, "surfaceDestroyed called");
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated called");
		playVideo();
		// ********
		// TO-DO
		// dismissDialog(DIALOG1_KEY);
	}

	@Override
	protected void onPause() {
		// when the screen is about to turn off
		if (ScreenReceiver.screenOff) {

			// this is the case when onPause() is called by the system due to a
			// screen state change
			Log.d(TAG, "SCREEN TURNED OFF");

		} else {
			// this is when onPause() is called when the screen state has not
			// changed
			System.out
					.println("this is when onPause() is called when the screen state has not changed ");

		}
		super.onPause();

		isPaused = true;
		mPlayer.pause();
		pauseBut.setChecked(true);
		// dialog.show();

	}

	@Override
	protected void onResume() {
		// only when screen turns on
		if (!ScreenReceiver.screenOff) {
			// this is when onResume() is called due to a screen state change
			Log.d(TAG, "SCREEN TURNED ON");
		} else {
			// this is when onResume() is called when the screen state has not
			// changed
			System.out
					.println(" this is when onResume() is called when the screen state has not changed ");
		}
		super.onResume();

		isPaused = false;
		mPreview.postDelayed(onEverySecond, 100);

		startVideoPlayback();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPreview.removeTapListener(onTap2);
		isPaused = true;
		releaseMediaPlayer();
		Log.v(TAG, "onDestroy");
//		doCleanUp();
//		
	}

	private void releaseMediaPlayer() {
		Log.v(TAG, "releaseMediaPlayer");
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;

		}

	}

	private void doCleanUp() {
		Log.v(TAG, "doCleanUp");
		mVideoWidth = 0;
		mVideoHeight = 0;
		mIsVideoReadyToBePlayed = false;
		mIsVideoSizeKnown = false;
	}

	private void startVideoPlayback() {
		Log.v(TAG, "startVideoPlayback");
		holder.setFixedSize(mVideoWidth, mVideoHeight);
		if (mPlayer != null && !mPlayer.isPlaying()) {
			isPaused = false;
			mPlayer.start();
			pauseBut.setChecked(false);
		}
		// dialog.dismiss();

	}

	public void onCompletion(MediaPlayer mp) {
		Log.v(TAG, "onCompletion");
		// TODO Auto-generated method stub
		// dialog.show();
		if (chapter == -1)
			Return();
		else
			goToChapter();

	}

	public void setOnPreparedListener(MediaPlayer mp) {
		dialog.dismiss();
	}

	public void onSeekComplete(MediaPlayer mp) {
		// TODO Auto-generated method stub

		// mPlayer.start();
		dialog.dismiss();

	}

	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		Log.d(TAG, "mPlayer :" + mp.toString());
		return false;
	}

	public View makeView() {
		Log.v(TAG, "makeView");
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public View makeView() {
	// // TODO Auto-generated method stub
	// return null;
	// }

}
