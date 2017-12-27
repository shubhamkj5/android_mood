package com.example.affectassessment;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import android.app.ActionBar;
import android.view.MenuItem;

public class PANASShortNoPhotoActivity extends Activity implements
		OnClickListener {

	private static final String PANAS_SHORT_NO_PHOTO_DATA_FILENAME = "PANASShortNoPhotoData.txt";

	Button btnSave, btnNote, btnShare;

	SeekBar seekBarUpset, seekBarHostile, seekBarAlert, seekBarAshamed,
			seekBarInspired, seekBarNervous, seekBarDetermined,
			seekBarAttentive, seekBarActive, seekBarAfraid;

	String note = "";

	SoundPool sp;
	int soundID;
	
	SharedPreferences pref;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_panas_short_no_photo);

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
	
	private void initializeCompnentView() {
		btnSave = (Button) findViewById(R.id.buttonSavePANASShortNoPhoto);
		btnNote = (Button) findViewById(R.id.buttonNotePANASShortNoPhoto);
		btnShare = (Button) findViewById(R.id.buttonSharePANASShortNoPhoto);

		btnSave.setOnClickListener(this);
		btnNote.setOnClickListener(this);
		btnShare.setOnClickListener(this);

		seekBarUpset = (SeekBar) findViewById(R.id.seekBarPANASShortUpset);
		seekBarHostile = (SeekBar) findViewById(R.id.seekBarPANASShortHostile);
		seekBarAlert = (SeekBar) findViewById(R.id.seekBarPANASShortAlert);
		seekBarAshamed = (SeekBar) findViewById(R.id.seekBarPANASShortAshamed);
		seekBarInspired = (SeekBar) findViewById(R.id.seekBarPANASShortInspired);
		seekBarNervous = (SeekBar) findViewById(R.id.seekBarPANASShortNervous);
		seekBarDetermined = (SeekBar) findViewById(R.id.seekBarPANASShortDetermined);
		seekBarAttentive = (SeekBar) findViewById(R.id.seekBarPANASShortAttentive);
		seekBarActive = (SeekBar) findViewById(R.id.seekBarPANASShortActive);
		seekBarAfraid = (SeekBar) findViewById(R.id.seekBarPANASShortAfraid);
		
		sp = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
		soundID = sp.load(this, R.raw.save_sound, 1);
		
		pref = getSharedPreferences("settings", 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonSavePANASShortNoPhoto:
			saveData();
			break;
		case R.id.buttonSharePANASShortNoPhoto:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "I am feeling  right now!");
			sendIntent.putExtra(Intent.EXTRA_SUBJECT, "My mood today.");
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, "Share with your friends"));
			
			break;
		case R.id.buttonNotePANASShortNoPhoto:
			displayNoteDialog();
			break;
		}

	}

	@SuppressLint("SimpleDateFormat")
	private void saveData() {
		String currentDateAndTime, data;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		currentDateAndTime = sdf.format(new Date());

		data = currentDateAndTime + "," + "upset" + ","
				+ String.valueOf(seekBarUpset.getProgress() + 1) + ","
				+ "hostile" + ","
				+ String.valueOf(seekBarHostile.getProgress() + 1) + ","
				+ "alert" + ","
				+ String.valueOf(seekBarAlert.getProgress() + 1) + ","
				+ "ashamed" + ","
				+ String.valueOf(seekBarAshamed.getProgress() + 1) + ","
				+ "inspired" + ","
				+ String.valueOf(seekBarInspired.getProgress() + 1) + ","
				+ "nervous" + ","
				+ String.valueOf(seekBarNervous.getProgress() + 1) + ","
				+ "determined" + ","
				+ String.valueOf(seekBarDetermined.getProgress() + 1) + ","
				+ "attentive" + ","
				+ String.valueOf(seekBarAttentive.getProgress() + 1) + ","
				+ "active" + ","
				+ String.valueOf(seekBarActive.getProgress() + 1) + ","
				+ "afraid" + ","
				+ String.valueOf(seekBarAfraid.getProgress() + 1) + "," + note
				+ "\n";

		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					openFileOutput(PANAS_SHORT_NO_PHOTO_DATA_FILENAME,
							Context.MODE_APPEND));
			outputStreamWriter.append(data);
			outputStreamWriter.close();
		} catch (IOException e) {

		}
		
		Toast.makeText(PANASShortNoPhotoActivity.this,"Saved", Toast.LENGTH_SHORT).show();
		
		String settingSound = pref.getString("sound", "-1");
		if (settingSound.compareTo("1") == 0) {
			sp.play(soundID, 1, 1, 1, 0, 1);
		}
		
		Intent myIntent = new Intent(getApplicationContext(),
				MainActivity.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(myIntent);
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
}
