package com.example.affectassessment;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import android.app.ActionBar;
import android.view.MenuItem;

public class PAMActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	private static final String PAM_DATA_FILENAME = "PAMData.txt";

	Button btnSave, btnNote, btnShare, btnLoadMore;

	ScrollableGridView gv;

	Integer[] imageIDs = { R.drawable.pam_afraid1, R.drawable.pam_tense1,
			R.drawable.pam_excited1, R.drawable.pam_delighted1,
			R.drawable.pam_frustrated1, R.drawable.pam_angry1,
			R.drawable.pam_happy1, R.drawable.pam_glad1,
			R.drawable.pam_miserable1, R.drawable.pam_sad1,
			R.drawable.pam_calm1, R.drawable.pam_satisfied1,
			R.drawable.pam_gloomy1, R.drawable.pam_tired1,
			R.drawable.pam_sleepy1, R.drawable.pam_serene1 };

	Point pointToGetSize;

	int currentPos = -1;

	ImageAdapter adapter; // adapter to provide images for GridView

	String note = "";

	SoundPool sp;
	int soundID;
	
	SharedPreferences pref;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pam);

		initializeCompnentView();
		
		ActionBar ab = getActionBar(); 
        ab.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	        	Intent myIntent;
	        	myIntent = new Intent(getApplicationContext(),
						ReportMoodActivity.class);
	        	myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item); 
	    }
	}
	
	@SuppressLint("NewApi")
	private void initializeCompnentView() {
		btnSave = (Button) findViewById(R.id.buttonSavePAM);
		btnNote = (Button) findViewById(R.id.buttonNotePAM);
		btnShare = (Button) findViewById(R.id.buttonSharePAM);
		btnLoadMore = (Button) findViewById(R.id.buttonLoadMorePAM);

		btnSave.setOnClickListener(this);
		btnNote.setOnClickListener(this);
		btnShare.setOnClickListener(this);
		btnLoadMore.setOnClickListener(this);

		gv = (ScrollableGridView) findViewById(R.id.gridViewPAM);

		adapter = new ImageAdapter(this);
		gv.setAdapter(adapter);
		gv.setExpanded(true);
		gv.setOnItemClickListener(this);

		Display display = getWindowManager().getDefaultDisplay();
		pointToGetSize = new Point();
		display.getSize(pointToGetSize);
		
		sp = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
		soundID = sp.load(this, R.raw.save_sound, 1);
		
		pref = getSharedPreferences("settings", 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonSavePAM:
			saveData();
			break;
		case R.id.buttonSharePAM:
			String moodName = "";
			
			if (currentPos == 0){
				moodName = "Afraid";
			} else if (currentPos == 1){
				moodName = "Tense";
			} else if (currentPos == 2){
				moodName = "Excited";
			} else if (currentPos == 3){
				moodName = "Delighted";
			} else if (currentPos == 4){
				moodName = "Frustrated";
			} else if (currentPos == 5){
				moodName = "Angry";
			} else if (currentPos == 6){
				moodName = "Happy";
			} else if (currentPos == 7){
				moodName = "Glad";
			} else if (currentPos == 8){
				moodName = "Miserable";
			} else if (currentPos == 9){
				moodName = "Sad";
			} else if (currentPos == 10){
				moodName = "Calm";
			} else if (currentPos == 11){
				moodName = "Satisfied";
			} else if (currentPos == 12){
				moodName = "Gloomy";
			} else if (currentPos == 13){
				moodName = "Tired";
			} else if (currentPos == 14){
				moodName = "Sleepy";
			} else if (currentPos == 15){
				moodName = "Serene";
			}
			
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "I am feeling " + moodName + " right now!");
			sendIntent.putExtra(Intent.EXTRA_SUBJECT, "My mood today.");
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, "Share with your friends"));
			
			break;
		case R.id.buttonNotePAM:
			displayNoteDialog();
			break;
		case R.id.buttonLoadMorePAM:
			changeImageSet();
			currentPos = -1;
			adapter.notifyDataSetChanged();
			break;
		}
	}

	@SuppressLint("SimpleDateFormat")
	private void saveData() {
		
		if (currentPos == -1){
			Toast.makeText(PAMActivity.this,"Please select one photo", Toast.LENGTH_SHORT).show();
			return;
		}
		
		String currentDateAndTime, data;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		currentDateAndTime = sdf.format(new Date());

		data = currentDateAndTime + "," + String.valueOf(currentPos + 1) + ","
				+ note + "\n";

		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					openFileOutput(PAM_DATA_FILENAME, Context.MODE_APPEND));
			outputStreamWriter.append(data);
			outputStreamWriter.close();
		} catch (IOException e) {

		}

		Toast.makeText(PAMActivity.this,"Saved", Toast.LENGTH_SHORT).show();
		
		String settingSound = pref.getString("sound", "-1");
		if (settingSound.compareTo("1") == 0) {
			sp.play(soundID, 1, 1, 1, 0, 1);
		}
		
		Intent myIntent = new Intent(getApplicationContext(),
				MainActivity.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(myIntent);
	}

	private void changeImageSet() {
		Random ran = new Random();
		int num;

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[0] = R.drawable.pam_afraid1;
		} else if (num == 1) {
			imageIDs[0] = R.drawable.pam_afraid2;
		} else {
			imageIDs[0] = R.drawable.pam_afraid3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[1] = R.drawable.pam_tense1;
		} else if (num == 1) {
			imageIDs[1] = R.drawable.pam_tense2;
		} else {
			imageIDs[1] = R.drawable.pam_tense3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[2] = R.drawable.pam_excited1;
		} else if (num == 1) {
			imageIDs[2] = R.drawable.pam_excited2;
		} else {
			imageIDs[2] = R.drawable.pam_excited3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[3] = R.drawable.pam_delighted1;
		} else if (num == 1) {
			imageIDs[3] = R.drawable.pam_delighted2;
		} else {
			imageIDs[3] = R.drawable.pam_delighted3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[4] = R.drawable.pam_frustrated1;
		} else if (num == 1) {
			imageIDs[4] = R.drawable.pam_frustrated2;
		} else {
			imageIDs[4] = R.drawable.pam_frustrated3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[5] = R.drawable.pam_angry1;
		} else if (num == 1) {
			imageIDs[5] = R.drawable.pam_angry2;
		} else {
			imageIDs[5] = R.drawable.pam_angry3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[6] = R.drawable.pam_happy1;
		} else if (num == 1) {
			imageIDs[6] = R.drawable.pam_happy2;
		} else {
			imageIDs[6] = R.drawable.pam_happy3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[7] = R.drawable.pam_glad1;
		} else if (num == 1) {
			imageIDs[7] = R.drawable.pam_glad2;
		} else {
			imageIDs[7] = R.drawable.pam_glad3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[8] = R.drawable.pam_miserable1;
		} else if (num == 1) {
			imageIDs[8] = R.drawable.pam_miserable2;
		} else {
			imageIDs[8] = R.drawable.pam_miserable3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[9] = R.drawable.pam_sad1;
		} else if (num == 1) {
			imageIDs[9] = R.drawable.pam_sad2;
		} else {
			imageIDs[9] = R.drawable.pam_sad3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[10] = R.drawable.pam_calm1;
		} else if (num == 1) {
			imageIDs[10] = R.drawable.pam_calm2;
		} else {
			imageIDs[10] = R.drawable.pam_calm3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[11] = R.drawable.pam_satisfied1;
		} else if (num == 1) {
			imageIDs[11] = R.drawable.pam_satisfied2;
		} else {
			imageIDs[11] = R.drawable.pam_satisfied3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[12] = R.drawable.pam_gloomy1;
		} else if (num == 1) {
			imageIDs[12] = R.drawable.pam_gloomy2;
		} else {
			imageIDs[12] = R.drawable.pam_gloomy3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[13] = R.drawable.pam_tired1;
		} else if (num == 1) {
			imageIDs[13] = R.drawable.pam_tired2;
		} else {
			imageIDs[13] = R.drawable.pam_tired3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[14] = R.drawable.pam_sleepy1;
		} else if (num == 1) {
			imageIDs[14] = R.drawable.pam_sleepy2;
		} else {
			imageIDs[14] = R.drawable.pam_sleepy3;
		}

		num = ran.nextInt(3);
		if (num == 0) {
			imageIDs[15] = R.drawable.pam_serene1;
		} else if (num == 1) {
			imageIDs[15] = R.drawable.pam_serene2;
		} else {
			imageIDs[15] = R.drawable.pam_serene3;
		}
	}

	@SuppressLint("NewApi")
	private void displayNoteDialog() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.prompt);
		
		dialog.setCancelable(false);
		
		final EditText userInput = (EditText) dialog
				.findViewById(R.id.editTextDialogUserInput);
		
		Button dialogButtonOK = (Button) dialog
				.findViewById(R.id.buttonPromptOK);
		// if button is clicked, close the custom dialog
		dialogButtonOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				note = userInput.getText().toString();
				dialog.dismiss();
			}
		});
		
		Button dialogButtonCancel = (Button) dialog
				.findViewById(R.id.buttonPromptCancel);
		// if button is clicked, close the custom dialog
		dialogButtonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		String settingChoice = pref.getString("choice", "-1");
		
		if (settingChoice.compareTo("1") == 0){
			Resources res = getResources();
			Drawable drawable = res.getDrawable(R.drawable.gradient_background1); 
			LinearLayout promptLayout = (LinearLayout)dialog.findViewById(R.id.promptLayout);
			promptLayout.setBackground(drawable);
		} else if (settingChoice.compareTo("2") == 0){
			Resources res = getResources();
			Drawable drawable = res.getDrawable(R.drawable.gradient_background2); 
			LinearLayout promptLayout = (LinearLayout)dialog.findViewById(R.id.promptLayout);
			promptLayout.setBackground(drawable);
		} else if (settingChoice.compareTo("3") == 0){
			Resources res = getResources();
			Drawable drawable = res.getDrawable(R.drawable.gradient_background3); 
			LinearLayout promptLayout = (LinearLayout)dialog.findViewById(R.id.promptLayout);
			promptLayout.setBackground(drawable);
		}
			
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(dialog.getWindow().getAttributes());
	    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
	    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
	    
		dialog.show();
		
		dialog.getWindow().setAttributes(lp);
	}

	public class ImageAdapter extends BaseAdapter {
		private Context context;

		public ImageAdapter(Context c) {
			context = c;
		}

		// ---returns the number of images---
		public int getCount() {
			return imageIDs.length;
		}

		// ---returns the ID of an item---
		public Object getItem(int position) {
			return position;
		}

		// ---returns the ID of an item---
		public long getItemId(int position) {
			return position;
		}

		// ---returns an ImageView view---
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(context);
				imageView.setLayoutParams(new GridView.LayoutParams(
						pointToGetSize.x / 4, pointToGetSize.x / 4));

				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

				imageView.setPadding(6, 6, 6, 6);
			} else {
				imageView = (ImageView) convertView;
			}
			imageView.setImageResource(imageIDs[position]);

			if (position == currentPos) {
				imageView.setBackgroundResource(R.drawable.list_selector);
			} else {
				imageView.setBackgroundResource(0);
			}

			return imageView;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		currentPos = position;
		adapter.notifyDataSetChanged();
	}
}
