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
import android.widget.RadioGroup;
import android.widget.Toast;

import android.app.ActionBar;
import android.view.MenuItem;

public class SPANENoPhotoActivity extends Activity implements OnClickListener {

	private static final String SPANE_NO_PHOTO_DATA_FILENAME = "SPANENoPhotoData.txt";
	
	Button btnSave, btnNote, btnShare;

	RadioGroup radioGroupPositive, radioGroupNegative, radioGroupBad,
			radioGroupGood, radioGroupPleasant, radioGroupUnpleasant,
			radioGroupHappy, radioGroupSad, radioGroupAfraid, radioGroupJoyful,
			radioGroupAngry, radioGroupContented;
	
	String note = "";
	
	SoundPool sp;
	int soundID;
	
	SharedPreferences pref;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_spane_no_photo);

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
		btnSave = (Button) findViewById(R.id.buttonSaveSPANENoPhoto);
		btnNote = (Button) findViewById(R.id.buttonNoteSPANENoPhoto);
		btnShare = (Button) findViewById(R.id.buttonShareSPANENoPhoto);

		btnSave.setOnClickListener(this);
		btnNote.setOnClickListener(this);
		btnShare.setOnClickListener(this);

		radioGroupPositive = (RadioGroup) findViewById(R.id.radioGroupPositive);
		radioGroupNegative = (RadioGroup) findViewById(R.id.radioGroupNegative);
		radioGroupGood = (RadioGroup) findViewById(R.id.radioGroupGood);
		radioGroupBad = (RadioGroup) findViewById(R.id.radioGroupBad);
		radioGroupPleasant = (RadioGroup) findViewById(R.id.radioGroupPleasant);
		radioGroupUnpleasant = (RadioGroup) findViewById(R.id.radioGroupUnpleasant);
		radioGroupSad = (RadioGroup) findViewById(R.id.radioGroupSad);
		radioGroupHappy = (RadioGroup) findViewById(R.id.radioGroupHappy);
		radioGroupAfraid = (RadioGroup) findViewById(R.id.radioGroupAfraid);
		radioGroupJoyful = (RadioGroup) findViewById(R.id.radioGroupJoyful);
		radioGroupAngry = (RadioGroup) findViewById(R.id.radioGroupAngry);
		radioGroupContented = (RadioGroup) findViewById(R.id.radioGroupContented);
		
		sp = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
		soundID = sp.load(this, R.raw.save_sound, 1);
		
		pref = getSharedPreferences("settings", 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonSaveSPANENoPhoto:
			saveData();
			
			break;
		case R.id.buttonShareSPANENoPhoto:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "I am feeling  right now!");
			sendIntent.putExtra(Intent.EXTRA_SUBJECT, "My mood today.");
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, "Share with your friends"));
			
			break;
		case R.id.buttonNoteSPANENoPhoto:
			displayNoteDialog();
			break;
		}
	}

	@SuppressLint("SimpleDateFormat")
	private void saveData() {
		int radioButtonID, idx;
		View radioButton;
		String data, currentDateAndTime;
		
		radioButtonID = radioGroupPositive.getCheckedRadioButtonId();
		radioButton = radioGroupPositive.findViewById(radioButtonID);
		idx = radioGroupPositive.indexOfChild(radioButton);
		data = "positive" + "," + String.valueOf(idx+1) + ",";
		
		radioButtonID = radioGroupNegative.getCheckedRadioButtonId();
		radioButton = radioGroupNegative.findViewById(radioButtonID);
		idx = radioGroupNegative.indexOfChild(radioButton);
		data = data + "negative" + "," + String.valueOf(idx+1) + ",";
		
		radioButtonID = radioGroupGood.getCheckedRadioButtonId();
		radioButton = radioGroupGood.findViewById(radioButtonID);
		idx = radioGroupGood.indexOfChild(radioButton);
		data = data + "good" + "," + String.valueOf(idx+1) + ",";
		
		radioButtonID = radioGroupBad.getCheckedRadioButtonId();
		radioButton = radioGroupBad.findViewById(radioButtonID);
		idx = radioGroupBad.indexOfChild(radioButton);
		data = data + "bad" + "," + String.valueOf(idx+1) + ",";
		
		radioButtonID = radioGroupPleasant.getCheckedRadioButtonId();
		radioButton = radioGroupPleasant.findViewById(radioButtonID);
		idx = radioGroupPleasant.indexOfChild(radioButton);
		data = data + "pleasant" + "," + String.valueOf(idx+1) + ",";
		
		radioButtonID = radioGroupUnpleasant.getCheckedRadioButtonId();
		radioButton = radioGroupUnpleasant.findViewById(radioButtonID);
		idx = radioGroupUnpleasant.indexOfChild(radioButton);
		data = data + "unpleasant" + "," + String.valueOf(idx+1) + ",";
		
		radioButtonID = radioGroupHappy.getCheckedRadioButtonId();
		radioButton = radioGroupHappy.findViewById(radioButtonID);
		idx = radioGroupHappy.indexOfChild(radioButton);
		data = data + "happy" + "," + String.valueOf(idx+1) + ",";
		
		radioButtonID = radioGroupSad.getCheckedRadioButtonId();
		radioButton = radioGroupSad.findViewById(radioButtonID);
		idx = radioGroupSad.indexOfChild(radioButton);
		data = data + "sad" + "," + String.valueOf(idx+1) + ",";
		
		radioButtonID = radioGroupAfraid.getCheckedRadioButtonId();
		radioButton = radioGroupAfraid.findViewById(radioButtonID);
		idx = radioGroupAfraid.indexOfChild(radioButton);
		data = data + "afraid" + "," + String.valueOf(idx+1) + ",";
		
		radioButtonID = radioGroupJoyful.getCheckedRadioButtonId();
		radioButton = radioGroupJoyful.findViewById(radioButtonID);
		idx = radioGroupJoyful.indexOfChild(radioButton);
		data = data + "joyful" + "," + String.valueOf(idx+1) + ",";
		
		radioButtonID = radioGroupAngry.getCheckedRadioButtonId();
		radioButton = radioGroupAngry.findViewById(radioButtonID);
		idx = radioGroupAngry.indexOfChild(radioButton);
		data = data + "angry" + "," + String.valueOf(idx+1) + ",";
		
		radioButtonID = radioGroupContented.getCheckedRadioButtonId();
		radioButton = radioGroupContented.findViewById(radioButtonID);
		idx = radioGroupContented.indexOfChild(radioButton);
		data = data + "contented" + "," + String.valueOf(idx+1) + ",";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		currentDateAndTime = sdf.format(new Date());
		
		data = currentDateAndTime + "," + data + note + "\n";
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					openFileOutput(SPANE_NO_PHOTO_DATA_FILENAME,
							Context.MODE_APPEND));
			outputStreamWriter.append(data);
			outputStreamWriter.close();
		} catch (IOException e) {

		}
	
		Toast.makeText(SPANENoPhotoActivity.this,"Saved", Toast.LENGTH_SHORT).show();
		
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
