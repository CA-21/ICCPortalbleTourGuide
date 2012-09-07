/**
 * 
 */
package com.yuco;

import java.io.DataOutputStream;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;


import com.yuco.mygallery;
/**
 * @author james.kong
 * 
 */
public class ZoneSelectionView extends AppBase {

	/**
	 * 
	 */
	// ProgressDialog mDialog1;
	// ProgressDialog mDialog2;
	//
	// private static final int DIALOG1_KEY = 1;
	// private static final int DIALOG2_KEY = 2;

	private static final String POSITION = "position";
	private static final String PLAY_MODE = "PlayMode";
	private int playAll = -1;

	private static final String LANGUAGE = "language";

	private static final int LOCAL_VIDEO = 4;
	private static final String zone[] = { "ZoneE1", "ZoneE2", "ZoneN1",
			"ZoneN2", "ZoneW1", "ZoneW2", "ZoneS1", "ZoneS2" };
	private int selected;
	mygallery g;
	Button leftButton;
	Button rightButton;

	private Bundle extras = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
		setContentView(R.layout.zone_view);

		Typeface tf_tc = Typeface.createFromAsset(getAssets(),
				"fonts/helveticaneue_light.otf");
		Typeface tf_sc = Typeface.createFromAsset(getAssets(),
				"fonts/helveticaneue_light.otf");
		Typeface tf_en = Typeface.createFromAsset(getAssets(),
				"fonts/helveticaneue_light.otf");
		Typeface tf_jp = Typeface.createFromAsset(getAssets(),
				"fonts/helveticaneue_light.otf");
		Typeface[] tf = { tf_tc, tf_sc, tf_en, tf_jp };

		extras = getIntent().getExtras();
		if (extras != null)
			position = extras.getInt(POSITION);
		if (extras != null)
			lang = extras.getInt(LANGUAGE);
		// if(lang==2)
		{
			useTypefalce = true;
		}
		ImageView _bg_image_v = (ImageView)findViewById(R.id.ImageView01);
		Drawable dr;
		switch(lang)
		{
		case 0:
			 dr = this.getResources().getDrawable(R.drawable.gallery_bg_1);
			_bg_image_v.setBackgroundDrawable(dr);
		break;
		case 1:
			dr = this.getResources().getDrawable(R.drawable.gallery_bg_2);
			_bg_image_v.setBackgroundDrawable(dr);
		break;
		case 2:
			dr = this.getResources().getDrawable(R.drawable.gallery_bg_3);
			_bg_image_v.setBackgroundDrawable(dr);
		break;
		case 3:
			dr = this.getResources().getDrawable(R.drawable.gallery_bg_4);
			_bg_image_v.setBackgroundDrawable(dr);
		break;
		case 4:
			dr = this.getResources().getDrawable(R.drawable.gallery_bg_5);
			_bg_image_v.setBackgroundDrawable(dr);
		break;
		}
		Button InvisibleButton = (Button) findViewById(R.id.InvisibleButton);
		leftButton = (Button) findViewById(R.id.LeftButton);
		rightButton = (Button) findViewById(R.id.RightButton);

		// leftButton.setVisibility(-1);
		// InvisibleButton.setVisibility(-1);
		InvisibleButton.setOnLongClickListener(new OnLongClickListener() {

			
			public boolean onLongClick(View v) {
				showDialog(0);
				return false;
			}

		});
		// InvisibleButton.setOnTouchListener(ButtonTouchListener);

		String[] title = getResources().getStringArray(R.array.tourguide);
		ViewGroup al = (ViewGroup) findViewById(R.id.AbsoluteLayout01);
		// exit = new Button(this);
		RelativeLayout rl = new RelativeLayout(this);
		rl.setPadding(0, 0, 0, 0);
		if (debug)
			rl.setBackgroundColor(Color.argb(50, 100, 100, 100));

		/*
		 * String[] sReturn = getResources().getStringArray(R.array.home);
		 * exit.setBackgroundResource(R.drawable.but_home_l);
		 * exit.setPadding(40, 0, 0, 0); exit.setTextSize(sTextSize);
		 * exit.setText(sReturn[lang]); exit.setTextColor(Color.WHITE);
		 * exit.setHighlightColor(Color.BLUE); RelativeLayout.LayoutParams param
		 * = new RelativeLayout.LayoutParams(WC, WC);
		 * param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1); rl.addView(exit,
		 * param);
		 */
		al.addView(rl);
		LinearLayout ll = new LinearLayout(this);
		ll.setPadding(20, 0, 0, 0);
		ll.setOrientation(LinearLayout.HORIZONTAL);
		TextView tv = new TextView(getBaseContext());
		tv.setText(title[lang]);
		tv.setTextSize(bTextSize);
		tv.setTextColor(Color.rgb(title_color[0], title_color[2],
				title_color[2]));
		if (useTypefalce)
			tv.setTypeface(tf_en);
		ll.addView(tv);
		RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(
				WC, WC);
		param2.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
		rl.addView(ll, param2);

		if (useTypefalce)
			tv.setTypeface(tf_en);
		/*
		 * InvisibleButton.setOnClickListener(new OnClickListener() { public
		 * void onClick(View v) { // runRootCommand("reboot"); showDialog(0); }
		 * });
		 */

		// Reference the Gallery view
		g = (mygallery) findViewById(R.id.gallery);
		if (g != null) {
			// Set the adapter to our custom adapter (below)
			ImageAdapter adaptor = new ImageAdapter(this);
			adaptor.setImageIdTarget(lang );
			g.setAdapter(adaptor);
			

			// Set a item click listener, and just Toast the clicked position
			if (position > 0
					&& position < ((ImageAdapter) g.getAdapter()).getImages()) {
				g.setSelection(position, true);
				selected = position;
				switchButton();
			}

			g.setOnItemSelectedListener(new OnItemSelectedListener() {

				public void onItemSelected(AdapterView parent, View v,
						int position, long id) {
					// TODO Auto-generated method stub
					// Toast.makeText(ZoneSelectionView.this, zone[position],
					// Toast.LENGTH_SHORT).show();
					selected = position;

					switchButton();
				}

				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}

			});

			g.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView parent, View v,
						int position, long id) {

					if (selected == position) {

						showDialog(DIALOG1_KEY);
						if (debug)
							Toast.makeText(ZoneSelectionView.this,
									"Playing " + zone[position] + " Videos",
									Toast.LENGTH_SHORT).show();

						Intent intent = new Intent(ZoneSelectionView.this,
								MediaPlayerClass.class);
						intent.putExtra(POSITION, position);
						intent.putExtra(PLAY_MODE, playAll);
						intent.putExtra(LANGUAGE, lang);

						startActivity(intent);
						finish();
						// startActivityForResult(intent, 0);
						// dismissDialog(DIALOG1_KEY);
					}

				}

			});
		}
		leftButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				selected--;
				try {
					if (selected < 0) {
						selected = 0;
					}
					if (selected >= 0
							&& selected < ((ImageAdapter) g.getAdapter())
									.getImages()) {

						g.setSelection(selected, true);
						position = selected;
						switchButton();

					}
				} catch (Exception e) {
					Toast.makeText(ZoneSelectionView.this, e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		rightButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				selected++;
				try {

					selected %= ((ImageAdapter) g.getAdapter()).getImages();

					if (selected >= 0
							&& selected < ((ImageAdapter) g.getAdapter())
									.getImages()) {
						position = selected;
						g.setSelection(selected, true);

					}
				} catch (Exception e) {
					Toast.makeText(ZoneSelectionView.this, e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		/*
		 * g.setOnItemLongClickListener(new OnItemLongClickListener() {
		 * 
		 * 
		 * @Override public boolean onItemLongClick(AdapterView parent, View v,
		 * int position, long id) { Toast.makeText(ZoneSelectionView.this,
		 * "Playing "+zone[position]+" Videos", Toast.LENGTH_SHORT).show();
		 * Intent intent = new Intent(ZoneSelectionView.this,
		 * MediaPlayerClass.class); intent.putExtra(MEDIA, position);
		 * startActivity(intent); // TODO Auto-generated method stub return
		 * false; }
		 * 
		 * });
		 */

		// We also want to show context menu for longpressed items in the
		// gallery
		registerForContextMenu(g);
	}

	protected void switchButton() {
		// TODO Auto-generated method stub
		switch (selected) {
		case 0:
			leftButton.setVisibility(-1);
			break;
		case 7:
			rightButton.setVisibility(-1);
			break;
		default:
			leftButton.setVisibility(1);
			rightButton.setVisibility(1);
			break;

		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// menu.add(R.string.gallery_2_text);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		Toast.makeText(this, "Longpress: " + info.position, Toast.LENGTH_SHORT)
				.show();

		return true;
	}

	// ********************
	public boolean runRootCommand(String command) {
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(command + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			Log.d("*** DEBUG ***", "Unexpected error - Here is what I know: "
					+ e.getMessage());
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
				// nothing
			}
		}
		return true;
	}

	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		int imageIdTarget = 0;
		public void setImageIdTarget(int i )
		{
			
			imageIdTarget = i;
		}
		public ImageAdapter(Context c) {
			
			mContext = c;
			// See res/values/attrs.xml for the <declare-styleable> that defines
			// Gallery1.
			TypedArray a = obtainStyledAttributes(R.styleable.Gallery1);
			mGalleryItemBackground = a.getResourceId(
					R.styleable.Gallery1_android_galleryItemBackground, 0);
			a.recycle();
		}

		public int getCount() {
			switch(imageIdTarget)
			{
				case 0:
					return mImageIds1.length;
					
				case 1:
					return mImageIds2.length;
					
				case 2:
					return mImageIds3.length;
					
				
				case 3:
					return mImageIds4.length;
					
				
				case 4:
					return mImageIds5.length;
					
				
				default:
					return mImageIds1.length;
					
				
			}
			
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);
			switch(imageIdTarget)
			{
				case 0:
					i.setImageResource(mImageIds1[position]);
					break;
				case 1:
					i.setImageResource(mImageIds2[position]);
					break;
				case 2:
					i.setImageResource(mImageIds3[position]);
					break;
				
				case 3:
					i.setImageResource(mImageIds4[position]);
					break;
				
				case 4:
					i.setImageResource(mImageIds5[position]);
					break;
				
				default:
					i.setImageResource(mImageIds1[position]);
					break;
				
			}
			
			i.setScaleType(ImageView.ScaleType.FIT_CENTER);

			i.setLayoutParams(new mygallery.LayoutParams(250, 250));
			i.setDrawingCacheEnabled(true);
			i.setBackgroundColor(0xFF000000);

			// The preferred Gallery item background
			// i.setBackgroundResource(mGalleryItemBackground);

			return i;
		}

		public int getImages() {
			switch(imageIdTarget)
			{
				case 0:
					return mImageIds1.length;
				case 1:
					return mImageIds2.length;
				
				case 2:
					return mImageIds3.length;
				
				case 3:
					return mImageIds4.length;
				
				case 4:
					return mImageIds5.length;
				
				default:
					return mImageIds1.length;
				
			}
		}

		private Context mContext;

		private Integer[] mImageIds1 = { R.drawable.bg_09, R.drawable.bg_10,
				R.drawable.bg_11, R.drawable.bg_12, R.drawable.bg_13,
				R.drawable.bg_14, R.drawable.bg_15, R.drawable.bg_16,

		};
		private Integer[] mImageIds2 = { R.drawable.bg_09_cs, R.drawable.bg_10_cs,
				R.drawable.bg_11_cs, R.drawable.bg_12_cs, R.drawable.bg_13_cs,
				R.drawable.bg_14_cs, R.drawable.bg_15_cs, R.drawable.bg_16_cs,

		};
		private Integer[] mImageIds3 = { R.drawable.bg_09, R.drawable.bg_10,
				R.drawable.bg_11, R.drawable.bg_12, R.drawable.bg_13,
				R.drawable.bg_14, R.drawable.bg_15, R.drawable.bg_16,

		};
		private Integer[] mImageIds4 = { R.drawable.bg_09, R.drawable.bg_10,
				R.drawable.bg_11, R.drawable.bg_12, R.drawable.bg_13,
				R.drawable.bg_14, R.drawable.bg_15, R.drawable.bg_16,

		};
		private Integer[] mImageIds5 = { R.drawable.bg_09, R.drawable.bg_10,
				R.drawable.bg_11, R.drawable.bg_12, R.drawable.bg_13,
				R.drawable.bg_14, R.drawable.bg_15, R.drawable.bg_16,

		};
		

	}

	// @Override
	// protected Dialog onCreateDialog(int id) {
	// switch (id) {
	// case DIALOG1_KEY: {
	// ProgressDialog dialog = new ProgressDialog(this);
	// dialog.setTitle("Indeterminate");
	// dialog.setMessage("Please wait while loading...");
	// dialog.setIndeterminate(true);
	// dialog.setCancelable(true);
	// return dialog;
	// }
	// case DIALOG2_KEY: {
	// ProgressDialog dialog = new ProgressDialog(this);
	// dialog.setMessage("Please wait while loading...");
	// dialog.setIndeterminate(true);
	// dialog.setCancelable(true);
	// return dialog;
	// }
	// }
	// return null;
	// }
}
