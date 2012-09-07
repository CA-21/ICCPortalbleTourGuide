package com.yuco;

import java.lang.reflect.Array;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.widget.Toast;

public class ChapterViewClass extends AppBase {

	private Paint mPaint;
	private Paint mPaint2;
	//private static final String zones_title1[] = { "E", "E", "N", "N", "W",
	//		"W", "S", "S" };
	private static final String zones_title2[] = { "1", "2", "1", "2", "1",
			"2", "1", "2" };
	private Dialog mDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int c = 0;
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
		extras = getIntent().getExtras();
		debug = false;
		if (extras != null)
			position = extras.getInt(POSITION);
		if (extras != null)
			lang = extras.getInt(LANGUAGE);

		if (debug)
			Toast.makeText(
					ChapterViewClass.this,
					"Position " + position + "Language " + lang + "Capter"
							+ chapter, Toast.LENGTH_SHORT).show();

		// int[] numZoneBut = getResources().getIntArray(R.array.findZone);
		// int numBut = numZoneBut[position];
		@SuppressWarnings("deprecation")
		Integer[] chapterString = Chapters[position];
		int numBut = chapterString.length;

		setContentView(ChaptersLayout[position]);
		

		//if (lang == 2) 
		{
			useTypefalce = true;
		}

		ImageView mView = (ImageView) findViewById(R.id.ChapterImage01);
		// mSwitcher.setFactory(this);
		int mId = mImagesIds[position][lang];
		if (mView != null)
			mView.setImageResource(mId);
//		Typeface tf_tc = Typeface.createFromAsset(getAssets(),
//				"fonts/helveticaneue_light.otf");
//		Typeface tf_sc = Typeface.createFromAsset(getAssets(),
//				"fonts/helveticaneue_light.otf");
		Typeface tf_en = Typeface.createFromAsset(getAssets(),
				"fonts/helveticaneue_light.otf");
//		Typeface tf_jp = Typeface.createFromAsset(getAssets(),
//				"fonts/helveticaneue_light.otf");
//		Typeface[] tf = { tf_tc, tf_sc, tf_en, tf_jp,tf_jp };
		Typeface[] tf = { tf_en, tf_en, tf_en, tf_en ,tf_en};
		/*
		 * Display a text message with yes/no buttons and handle each message as
		 * well as the cancel action
		 */
		/*
		 * Button twoButtonsTitle = (Button) findViewById(R.id.HomeButton);
		 * twoButtonsTitle.setOnClickListener(new OnClickListener() { public
		 * void onClick(View v) { showDialog(0); } });
		 */
		/*
		 * <RelativeLayout android:layout_width="wrap_content"
		 * android:paddingRight="20dp" android:paddingTop="20dp"
		 * android:layout_height="wrap_content"> <Button
		 * android:layout_width="wrap_content"
		 * android:layout_height="wrap_content" android:id="@+id/HomeButton"
		 * android:textSize="22dp" android:text="Home"
		 * android:layout_alignParentRight="true" android:background="#00000000"
		 * /> </RelativeLayout>
		 */
		// exit = (Button) findViewById(R.id.HomeButton);
		ViewGroup al = (ViewGroup) findViewById(R.id.chapter_view_layout);
		
		exit = new Button(this);
		RelativeLayout rl = new RelativeLayout(this);
		rl.setPadding(0, 0,0, 0);
		if(debug)rl.setBackgroundColor(Color.argb(50, 100, 100, 100));
		String[] sReturn = getResources().getStringArray(R.array.home);
		exit.setBackgroundResource(R.drawable.but_home_l);
		exit.setPadding(40, 10, 10, 5);
		//if(debug)exit.setBackgroundColor(Color.argb(50, 100, 0,0));
		exit.setTextSize(sTextSize);
		exit.setText(sReturn[lang]);
		exit.setGravity(Gravity.BOTTOM);
		exit.setTextColor(Color.WHITE);
		exit.setHighlightColor(Color.BLUE);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(WC,
				WC);
		param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
		rl.addView(exit, param);
		
		al.addView(rl);

		if (exit != null) {
			/*
			 * exit.setOnTouchListener(new View.OnTouchListener() {
			 * 
			 * @Override public boolean onTouch(View arg0, MotionEvent arg1) {
			 * // TODO Auto-generated method stub MyCount counter = new
			 * MyCount(5000, 1000); counter.start(); counter.cancel();
			 * 
			 * return false; }
			 * 
			 * });
			 */
			exit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (!isLongPress)
						Return();
					else {
						//showDialog(0);

					}
				}

			});

		}
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

		// *****important part
		LinearLayout ll = new LinearLayout(this);
		ll.setPadding(20, 0, 0, 0);
		ll.setOrientation(LinearLayout.HORIZONTAL);
		TextView tv = new TextView(getBaseContext());
		tv.setText(zones_title[position]);
		tv.setTextSize(bTextSize);
		tv.setTextColor(Color.rgb(title_color[0],title_color[2],title_color[2]));
		if (useTypefalce)
			tv.setTypeface(tf_en);
		TextView tv2 = new TextView(getBaseContext());

		tv2.setText(zones_title2[position]);
		tv2.setTextSize(sTextSize);
		tv2.setTextColor(Color.rgb(title_color[0],title_color[2],title_color[2]));
		if (useTypefalce)
			tv2.setTypeface(tf_en);
		ll.addView(tv);
		ll.addView(tv2);
		RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(WC,
				WC);
		param2.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
		rl.addView(ll, param2);
		

		String[] chapters1 = getResources().getStringArray(
				Chapters[position][0]);

		Button Chapter1 = (Button) findViewById(R.id.Chapter1);
		Chapter1.setText(chapters1[lang]);
		if(!debug)Chapter1.setTextColor(Chapter1.getTextColors().withAlpha(0));

		String[] chapters2 = getResources().getStringArray(
				Chapters[position][1]);
		Button Chapter2 = (Button) findViewById(R.id.Chapter2);
		Chapter2.setText(chapters2[lang]);

		String[] chapters3 = getResources().getStringArray(
				Chapters[position][2]);
		Button Chapter3 = (Button) findViewById(R.id.Chapter3);
		Chapter3.setText(chapters3[lang]);

		String[] chapters4 = getResources().getStringArray(
				Chapters[position][3]);
		Button Chapter4 = (Button) findViewById(R.id.Chapter4);
		Chapter4.setText(chapters4[lang]);
		if (useTypefalce) {
			Chapter1.setTypeface(tf[lang]);
			Chapter2.setTypeface(tf[lang]);
			Chapter3.setTypeface(tf[lang]);
			Chapter4.setTypeface(tf[lang]);
		}
		if(!debug)
		{
			Chapter1.setTextColor(Chapter1.getTextColors().withAlpha(0));
			Chapter2.setTextColor(Chapter2.getTextColors().withAlpha(0));
			Chapter3.setTextColor(Chapter3.getTextColors().withAlpha(0));
			Chapter4.setTextColor(Chapter4.getTextColors().withAlpha(0));
			Chapter1.setBackgroundColor(Color.TRANSPARENT);
			Chapter2.setBackgroundColor(Color.TRANSPARENT);
			Chapter3.setBackgroundColor(Color.TRANSPARENT);
			Chapter4.setBackgroundColor(Color.TRANSPARENT);
		}
		if (numBut > 4) {
			String[] chapters5 = getResources().getStringArray(
					Chapters[position][4]);
			Button Chapter5 = (Button) findViewById(R.id.Chapter5);
			Chapter5.setText(chapters5[lang]);
			
			if(!debug)
				{
					Chapter5.setTextColor(Chapter5.getTextColors().withAlpha(0));
					Chapter5.setBackgroundColor(Color.TRANSPARENT);
				}
//			if (useTypefalce)
//				Chapter5.setTypeface(tf[lang]);
			Chapter5.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					chapter = 0;
					showDialog(DIALOG1_KEY);
					Intent intent = new Intent(ChapterViewClass.this,
							MediaPlayerClass.class);
					intent.putExtra(POSITION, position);
					intent.putExtra(PLAY_MODE, 4);
					intent.putExtra(LANGUAGE, lang);
					//startActivity( intent);
					//finish();
					//setResult(RESULT_OK, intent);
					//finish();
					onSetIntent(intent);
				}
			});
		}
		if (numBut > 5) {
			String[] chapters6 = getResources().getStringArray(
					Chapters[position][5]);
			Button Chapter6 = (Button) findViewById(R.id.Chapter6);
			Chapter6.setText(chapters6[lang]);
			if(!debug)
				{
					Chapter6.setTextColor(Chapter6.getTextColors().withAlpha(0));
					Chapter6.setBackgroundColor(Color.TRANSPARENT);
				}
//			if (useTypefalce)
//				Chapter6.setTypeface(tf[lang]);
			Chapter6.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					chapter = 0;
					showDialog(DIALOG1_KEY);
					Intent intent = new Intent(ChapterViewClass.this,
							MediaPlayerClass.class);
					intent.putExtra(POSITION, position);
					intent.putExtra(PLAY_MODE, 5);
					intent.putExtra(LANGUAGE, lang);
					//startActivity( intent);
					//finish();
					//setResult(RESULT_OK, intent);
					//finish();
					onSetIntent(intent);
				}
			});
		}
		if (numBut > 6) {
			String[] chapters7 = getResources().getStringArray(
					Chapters[position][6]);
			Button Chapter7 = (Button) findViewById(R.id.Chapter7);
			if(!debug)
				{
					Chapter7.setTextColor(Chapter7.getTextColors().withAlpha(0));
					Chapter7.setBackgroundColor(Color.TRANSPARENT);
				}
			Chapter7.setText(chapters7[lang]);
//			if (useTypefalce)
//				Chapter7.setTypeface(tf[lang]);
			Chapter7.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					chapter = 0;
					showDialog(DIALOG1_KEY);
					Intent intent = new Intent(ChapterViewClass.this,
							MediaPlayerClass.class);
					intent.putExtra(POSITION, position);
					intent.putExtra(PLAY_MODE, 6);
					intent.putExtra(LANGUAGE, lang);
					//startActivity( intent);
					//finish();
					//setResult(RESULT_OK, intent);
					//finish();
					onSetIntent(intent);
				}
			});

		}

		Chapter1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chapter = 0;
				showDialog(DIALOG1_KEY);
				Intent intent = new Intent(ChapterViewClass.this,
						MediaPlayerClass.class);
				intent.putExtra(POSITION, position);
				intent.putExtra(PLAY_MODE, 0);
				intent.putExtra(LANGUAGE, lang);
				//startActivity( intent);
				//finish();
				//setResult(RESULT_OK, intent);
				//finish();
				onSetIntent(intent);
			}
		});

		Chapter2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chapter = 0;
				showDialog(DIALOG1_KEY);
				Intent intent = new Intent(ChapterViewClass.this,
						MediaPlayerClass.class);
				intent.putExtra(POSITION, position);
				intent.putExtra(PLAY_MODE, 1);
				intent.putExtra(LANGUAGE, lang);
				//startActivity( intent);
				//finish();
				//setResult(RESULT_OK, intent);
				//finish();
				onSetIntent(intent);
			}
		});
		Chapter3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chapter = 0;
				showDialog(DIALOG1_KEY);
				Intent intent = new Intent(ChapterViewClass.this,
						MediaPlayerClass.class);
				intent.putExtra(POSITION, position);
				intent.putExtra(PLAY_MODE, 2);
				intent.putExtra(LANGUAGE, lang);
				//startActivity( intent);
				//finish();
				//setResult(RESULT_OK, intent);
				//finish();
				onSetIntent(intent);
			}
		});
		Chapter4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chapter = 0;
				showDialog(DIALOG1_KEY);

				Intent intent = new Intent(ChapterViewClass.this,
						MediaPlayerClass.class);
				intent.putExtra(POSITION, position);
				intent.putExtra(PLAY_MODE, 3);
				intent.putExtra(LANGUAGE, lang);
				//startActivity( intent);
				//finish();
				onSetIntent(intent);
			}
		});
		// *****important part

		/*
		 * Button b = new Button(this); b.setText("show the player");
		 * b.setOnClickListener(my_click_listener);
		 * 
		 * AbsoluteLayout v = (AbsoluteLayout)
		 * findViewById(R.id.chapter_view_layout);
		 * 
		 * v.addView(b);
		 */
	}

	/*
	 * private View.OnClickListener my_click_listener = new
	 * View.OnClickListener() { public void onClick(View v) { Log.d("Chapter",
	 * "button clicked."); } };
	 */
	protected void onSetIntent(Intent intent)
	{
		//showDialog(DIALOG1_KEY);
		//setResult(RESULT_OK, intent);
		//finish();
		startActivity( intent);
		finish();
		//dismissDialog(DIALOG1_KEY);
	
	}
	protected void Return() {
		try {

			Intent intent = new Intent();
			intent.setClass(ChapterViewClass.this, ZoneSelectionView.class);

			intent.putExtra(POSITION, position);
			intent.putExtra(PLAY_MODE, chapter);
			intent.putExtra(LANGUAGE, lang);
			startActivity( intent);
			finish();
			

		} catch (Exception ex) {

		}
	}

//	private Integer[] mImageE1Ids = { R.drawable.cbg_e11, R.drawable.cbg_e12,
//			R.drawable.cbg_e13, R.drawable.cbg_e14, };
//	private Integer[] mImageE2Ids = { R.drawable.cbg_e21, R.drawable.cbg_e22,
//			R.drawable.cbg_e23, R.drawable.cbg_e24, };
//	private Integer[] mImageN1Ids = { R.drawable.cbg_n11, R.drawable.cbg_n12,
//			R.drawable.cbg_n13, R.drawable.cbg_n14, };
//	private Integer[] mImageN2Ids = { R.drawable.cbg_n21, R.drawable.cbg_n22,
//			R.drawable.cbg_n23, R.drawable.cbg_n24, };
//	private Integer[] mImageW1Ids = { R.drawable.cbg_w11, R.drawable.cbg_w12,
//			R.drawable.cbg_w13, R.drawable.cbg_w14, };
//	private Integer[] mImageW2Ids = { R.drawable.cbg_w21, R.drawable.cbg_w22,
//			R.drawable.cbg_w23, R.drawable.cbg_w24, };
//	private Integer[] mImageS1Ids = { R.drawable.cbg_s11, R.drawable.cbg_s12,
//			R.drawable.cbg_s13, R.drawable.cbg_s14, };
//	private Integer[] mImageS2Ids = { R.drawable.cbg_s21, R.drawable.cbg_s22,
//			R.drawable.cbg_s23, R.drawable.cbg_s24, };
	private Integer[] mImageE1Ids = { 
			R.drawable.portable_tour_guide_interface_e1_02,
			R.drawable.portable_tour_guide_interface_e1_03,
			R.drawable.portable_tour_guide_interface_e1_01,
			R.drawable.portable_tour_guide_interface_e1_04,
			R.drawable.portable_tour_guide_interface_e1_04_
			};
	private Integer[] mImageE2Ids = { 
			R.drawable.portable_tour_guide_interface_e2_06,
			R.drawable.portable_tour_guide_interface_e2_07,
			R.drawable.portable_tour_guide_interface_e2_05,
			R.drawable.portable_tour_guide_interface_e2_08,
			R.drawable.portable_tour_guide_interface_e2_08_
			};
	private Integer[] mImageN1Ids = {
			R.drawable.portable_tour_guide_interface_n1_10,
			R.drawable.portable_tour_guide_interface_n1_11,
			R.drawable.portable_tour_guide_interface_n1_09,
			R.drawable.portable_tour_guide_interface_n1_12,
			R.drawable.portable_tour_guide_interface_n1_12_
			};
	private Integer[] mImageN2Ids = {
			R.drawable.portable_tour_guide_interface_n2_14,
			R.drawable.portable_tour_guide_interface_n2_15,
			R.drawable.portable_tour_guide_interface_n2_13,
			R.drawable.portable_tour_guide_interface_n2_16,
			R.drawable.portable_tour_guide_interface_n2_16_
			};
	private Integer[] mImageW1Ids = { 
			R.drawable.portable_tour_guide_interface_w1_26,
			R.drawable.portable_tour_guide_interface_w1_27,
			R.drawable.portable_tour_guide_interface_w1_25,
			R.drawable.portable_tour_guide_interface_w1_28,
			R.drawable.portable_tour_guide_interface_w1_28_,
			};
	private Integer[] mImageW2Ids = {
			R.drawable.portable_tour_guide_interface_w2_30,
			R.drawable.portable_tour_guide_interface_w2_31,
			R.drawable.portable_tour_guide_interface_w2_29,
			R.drawable.portable_tour_guide_interface_w2_32,
			R.drawable.portable_tour_guide_interface_w2_32_,
			};
	private Integer[] mImageS1Ids = {
			R.drawable.portable_tour_guide_interface_s1_18,
			R.drawable.portable_tour_guide_interface_s1_19,
			R.drawable.portable_tour_guide_interface_s1_17,
			R.drawable.portable_tour_guide_interface_s1_20,
			R.drawable.portable_tour_guide_interface_s1_20_,
			};
	private Integer[] mImageS2Ids = {
			R.drawable.portable_tour_guide_interface_s2_22,
			R.drawable.portable_tour_guide_interface_s2_23,
			R.drawable.portable_tour_guide_interface_s2_21,
			R.drawable.portable_tour_guide_interface_s2_24,
			R.drawable.portable_tour_guide_interface_s2_24_,
			};

	private Integer[][] mImagesIds = { mImageE1Ids, mImageE2Ids, mImageN1Ids,
			mImageN2Ids, mImageW1Ids, mImageW2Ids, mImageS1Ids, mImageS2Ids };

	// private Integer[] e1String = {
	// R.array.e11,
	// R.array.e12,
	// R.array.e13,
	// R.array.e14,
	// R.array.e15,
	// R.array.e16,
	// R.array.e17,
	// };
	// private Integer[] e2String = {
	// R.array.e21,
	// R.array.e22,
	// R.array.e23,
	// R.array.e24,
	// R.array.e25,
	// R.array.e26,
	// R.array.e27,
	// };
	// private Integer[] n1String = {
	// R.array.n11,
	// R.array.n12,
	// R.array.n13,
	// R.array.n14,
	// R.array.n15,
	// };
	// private Integer[] n2String = {
	// R.array.n21,
	// R.array.n22,
	// R.array.n23,
	// R.array.n24,
	// R.array.n25,
	// };
	// private Integer[] w1String = {
	// R.array.w11,
	// R.array.w12,
	// R.array.w13,
	// R.array.w14,
	// R.array.w15,
	// };
	// private Integer[] w2String = {
	// R.array.w21,
	// R.array.w22,
	// R.array.w23,
	// R.array.w24,
	// };
	// private Integer[] s1String = {
	// R.array.s11,
	// R.array.s12,
	// R.array.s13,
	// R.array.s14,
	// R.array.s15,
	// };
	// private Integer[] s2String = {
	// R.array.s21,
	// R.array.s22,
	// R.array.s23,
	// R.array.s24,
	// R.array.s25,
	// R.array.s26,
	// R.array.s27,
	// };
	// private Integer[][] directionString={
	// e1String,
	// e2String,
	// n1String,
	// n2String,
	// w1String,
	// w2String,
	// s1String,
	// s2String
	// };

	public View makeView() {
		// TODO Auto-generated method stub
		return null;
	}

}
