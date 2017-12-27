package com.example.affectassessment;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ActionBar;
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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SPANEWithPhotoActivity extends Activity implements
		OnClickListener, OnCheckedChangeListener {
	
	private static final String SPANE_WITH_PHOTO_DATA_FILENAME = "SPANEWithPhotoData.txt";
	
	Button btnSave, btnNote, btnShare;

	RadioGroup radioGroupPositive, radioGroupNegative, radioGroupGood,
			radioGroupBad, radioGroupPleasant, radioGroupUnpleasant,
			radioGroupHappy, radioGroupSad, radioGroupAfraid, radioGroupJoyful,
			radioGroupAngry, radioGroupContented;

	ImageView imvPositive, imvNegative, imvGood, imvBad, imvPleasant, imvUnpleasant, imvHappy, imvSad, imvAfraid, imvJoyful, imvAngry, imvContented;
	
	String note = "";
	
	SoundPool sp;
	int soundID;
	
	SharedPreferences pref;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_spane_with_photo);

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
		btnSave = (Button) findViewById(R.id.buttonSaveSPANEWithPhoto);
		btnNote = (Button) findViewById(R.id.buttonNoteSPANEWithPhoto);
		btnShare = (Button) findViewById(R.id.buttonShareSPANEWithPhoto);

		btnSave.setOnClickListener(this);
		btnNote.setOnClickListener(this);
		btnShare.setOnClickListener(this);

		radioGroupPositive = (RadioGroup) findViewById(R.id.radioGroupSPANEWithPhotoPositive);
		radioGroupNegative = (RadioGroup) findViewById(R.id.radioGroupSPANEWithPhotoNegative);
		radioGroupGood = (RadioGroup) findViewById(R.id.radioGroupSPANEWithPhotoGood);
		radioGroupBad = (RadioGroup) findViewById(R.id.radioGroupSPANEWithPhotoBad);
		radioGroupPleasant = (RadioGroup) findViewById(R.id.radioGroupSPANEWithPhotoPleasant);
		radioGroupUnpleasant = (RadioGroup) findViewById(R.id.radioGroupSPANEWithPhotoUnpleasant);
		radioGroupHappy = (RadioGroup) findViewById(R.id.radioGroupSPANEWithPhotoHappy);
		radioGroupSad = (RadioGroup) findViewById(R.id.radioGroupSPANEWithPhotoSad);
		radioGroupAfraid = (RadioGroup) findViewById(R.id.radioGroupSPANEWithPhotoAfraid);
		radioGroupJoyful = (RadioGroup) findViewById(R.id.radioGroupSPANEWithPhotoJoyful);
		radioGroupAngry = (RadioGroup) findViewById(R.id.radioGroupSPANEWithPhotoAngry);
		radioGroupContented = (RadioGroup) findViewById(R.id.radioGroupSPANEWithPhotoContented);

		radioGroupPositive.setOnCheckedChangeListener(this);
		radioGroupNegative.setOnCheckedChangeListener(this);
		radioGroupGood.setOnCheckedChangeListener(this);
		radioGroupBad.setOnCheckedChangeListener(this);
		radioGroupPleasant.setOnCheckedChangeListener(this);
		radioGroupUnpleasant.setOnCheckedChangeListener(this);
		radioGroupHappy.setOnCheckedChangeListener(this);
		radioGroupSad.setOnCheckedChangeListener(this);
		radioGroupAfraid.setOnCheckedChangeListener(this);
		radioGroupJoyful.setOnCheckedChangeListener(this);
		radioGroupAngry.setOnCheckedChangeListener(this);
		radioGroupContented.setOnCheckedChangeListener(this);

		imvPositive = (ImageView) findViewById(R.id.imageViewSPANEWithPhotoPositive);
		imvNegative = (ImageView) findViewById(R.id.imageViewSPANEWithPhotoNegative);
		imvGood = (ImageView) findViewById(R.id.imageViewSPANEWithPhotoGood);
		imvBad = (ImageView) findViewById(R.id.imageViewSPANEWithPhotoBad);
		imvPleasant = (ImageView) findViewById(R.id.imageViewSPANEWithPhotoPleasant);
		imvUnpleasant = (ImageView) findViewById(R.id.imageViewSPANEWithPhotoUnpleasant);
		imvHappy = (ImageView) findViewById(R.id.imageViewSPANEWithPhotoHappy);
		imvSad = (ImageView) findViewById(R.id.imageViewSPANEWithPhotoSad);
		imvAfraid = (ImageView) findViewById(R.id.imageViewSPANEWithPhotoAfraid);
		imvJoyful = (ImageView) findViewById(R.id.imageViewSPANEWithPhotoJoyful);
		imvAngry = (ImageView) findViewById(R.id.imageViewSPANEWithPhotoAngry);
		imvContented = (ImageView) findViewById(R.id.imageViewSPANEWithPhotoContented);
		
		sp = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
		soundID = sp.load(this, R.raw.save_sound, 1);
		
		pref = getSharedPreferences("settings", 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonSaveSPANEWithPhoto:
			saveData();
			break;
		case R.id.buttonShareSPANEWithPhoto:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "I am feeling  right now!");
			sendIntent.putExtra(Intent.EXTRA_SUBJECT, "My mood today.");
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, "Share with your friends"));
			
			break;
		case R.id.buttonNoteSPANEWithPhoto:
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
					getApplicationContext().openFileOutput(SPANE_WITH_PHOTO_DATA_FILENAME,
							Context.MODE_APPEND));
			outputStreamWriter.append(data);
			outputStreamWriter.close();
		} catch (IOException e) {

		}
		
		Toast.makeText(SPANEWithPhotoActivity.this,"Saved", Toast.LENGTH_SHORT).show();
		
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

	@Override
	public void onCheckedChanged(RadioGroup group, int id) {
		int radioButtonID, idx;
		View radioButton;

		switch (group.getId()) {
		case R.id.radioGroupSPANEWithPhotoPositive:
			radioButtonID = radioGroupPositive.getCheckedRadioButtonId();
			radioButton = radioGroupPositive.findViewById(radioButtonID);
			idx = radioGroupPositive.indexOfChild(radioButton);

			switch (idx) {
			case 0:
				imvPositive.setImageResource(R.drawable.spane_positive1);
				break;
			case 1:
				imvPositive.setImageResource(R.drawable.spane_positive2);
				break;
			case 2:
				imvPositive.setImageResource(R.drawable.spane_positive3);
				break;
			case 3:
				imvPositive.setImageResource(R.drawable.spane_positive4);
				break;
			case 4:
				imvPositive.setImageResource(R.drawable.spane_positive5);
				break;
			}
			break;
			
		case R.id.radioGroupSPANEWithPhotoNegative:
			radioButtonID = radioGroupNegative.getCheckedRadioButtonId();
			radioButton = radioGroupNegative.findViewById(radioButtonID);
			idx = radioGroupNegative.indexOfChild(radioButton);

			switch (idx) {
			case 0:
				imvNegative.setImageResource(R.drawable.spane_negative1);
				break;
			case 1:
				imvNegative.setImageResource(R.drawable.spane_negative2);
				break;
			case 2:
				imvNegative.setImageResource(R.drawable.spane_negative3);
				break;
			case 3:
				imvNegative.setImageResource(R.drawable.spane_negative4);
				break;
			case 4:
				imvNegative.setImageResource(R.drawable.spane_negative5);
				break;
			}
			break;
			
		case R.id.radioGroupSPANEWithPhotoGood:
			radioButtonID = radioGroupGood.getCheckedRadioButtonId();
			radioButton = radioGroupGood.findViewById(radioButtonID);
			idx = radioGroupGood.indexOfChild(radioButton);

			switch (idx) {
			case 0:
				imvGood.setImageResource(R.drawable.spane_good1);
				break;
			case 1:
				imvGood.setImageResource(R.drawable.spane_good2);
				break;
			case 2:
				imvGood.setImageResource(R.drawable.spane_good3);
				break;
			case 3:
				imvGood.setImageResource(R.drawable.spane_good4);
				break;
			case 4:
				imvGood.setImageResource(R.drawable.spane_good5);
				break;
			}
			break;
			
		case R.id.radioGroupSPANEWithPhotoBad:
			radioButtonID = radioGroupBad.getCheckedRadioButtonId();
			radioButton = radioGroupBad.findViewById(radioButtonID);
			idx = radioGroupBad.indexOfChild(radioButton);

			switch (idx) {
			case 0:
				imvBad.setImageResource(R.drawable.spane_bad1);
				break;
			case 1:
				imvBad.setImageResource(R.drawable.spane_bad2);
				break;
			case 2:
				imvBad.setImageResource(R.drawable.spane_bad3);
				break;
			case 3:
				imvBad.setImageResource(R.drawable.spane_bad4);
				break;
			case 4:
				imvBad.setImageResource(R.drawable.spane_bad5);
				break;
			}
			break;
			
		case R.id.radioGroupSPANEWithPhotoPleasant:
			radioButtonID = radioGroupPleasant.getCheckedRadioButtonId();
			radioButton = radioGroupPleasant.findViewById(radioButtonID);
			idx = radioGroupPleasant.indexOfChild(radioButton);

			switch (idx) {
			case 0:
				imvPleasant.setImageResource(R.drawable.spane_pleasant1);
				break;
			case 1:
				imvPleasant.setImageResource(R.drawable.spane_pleasant2);
				break;
			case 2:
				imvPleasant.setImageResource(R.drawable.spane_pleasant3);
				break;
			case 3:
				imvPleasant.setImageResource(R.drawable.spane_pleasant4);
				break;
			case 4:
				imvPleasant.setImageResource(R.drawable.spane_pleasant5);
				break;
			}
			break;
			
		case R.id.radioGroupSPANEWithPhotoUnpleasant:
			radioButtonID = radioGroupUnpleasant.getCheckedRadioButtonId();
			radioButton = radioGroupUnpleasant.findViewById(radioButtonID);
			idx = radioGroupUnpleasant.indexOfChild(radioButton);

			switch (idx) {
			case 0:
				imvUnpleasant.setImageResource(R.drawable.spane_unpleasant1);
				break;
			case 1:
				imvUnpleasant.setImageResource(R.drawable.spane_unpleasant2);
				break;
			case 2:
				imvUnpleasant.setImageResource(R.drawable.spane_unpleasant3);
				break;
			case 3:
				imvUnpleasant.setImageResource(R.drawable.spane_unpleasant4);
				break;
			case 4:
				imvUnpleasant.setImageResource(R.drawable.spane_unpleasant5);
				break;
			}
			break;
			
		case R.id.radioGroupSPANEWithPhotoHappy:
			radioButtonID = radioGroupHappy.getCheckedRadioButtonId();
			radioButton = radioGroupHappy.findViewById(radioButtonID);
			idx = radioGroupHappy.indexOfChild(radioButton);

			switch (idx) {
			case 0:
				imvHappy.setImageResource(R.drawable.spane_happy1);
				break;
			case 1:
				imvHappy.setImageResource(R.drawable.spane_happy2);
				break;
			case 2:
				imvHappy.setImageResource(R.drawable.spane_happy3);
				break;
			case 3:
				imvHappy.setImageResource(R.drawable.spane_happy4);
				break;
			case 4:
				imvHappy.setImageResource(R.drawable.spane_happy5);
				break;
			}
			break;
			
		case R.id.radioGroupSPANEWithPhotoSad:
			radioButtonID = radioGroupSad.getCheckedRadioButtonId();
			radioButton = radioGroupSad.findViewById(radioButtonID);
			idx = radioGroupSad.indexOfChild(radioButton);

			switch (idx) {
			case 0:
				imvSad.setImageResource(R.drawable.spane_sad1);
				break;
			case 1:
				imvSad.setImageResource(R.drawable.spane_sad2);
				break;
			case 2:
				imvSad.setImageResource(R.drawable.spane_sad3);
				break;
			case 3:
				imvSad.setImageResource(R.drawable.spane_sad4);
				break;
			case 4:
				imvSad.setImageResource(R.drawable.spane_sad5);
				break;
			}
			break;
			
		case R.id.radioGroupSPANEWithPhotoAfraid:
			radioButtonID = radioGroupAfraid.getCheckedRadioButtonId();
			radioButton = radioGroupAfraid.findViewById(radioButtonID);
			idx = radioGroupAfraid.indexOfChild(radioButton);

			switch (idx) {
			case 0:
				imvAfraid.setImageResource(R.drawable.spane_afraid1);
				break;
			case 1:
				imvAfraid.setImageResource(R.drawable.spane_afraid2);
				break;
			case 2:
				imvAfraid.setImageResource(R.drawable.spane_afraid3);
				break;
			case 3:
				imvAfraid.setImageResource(R.drawable.spane_afraid4);
				break;
			case 4:
				imvAfraid.setImageResource(R.drawable.spane_afraid5);
				break;
			}
			break;
			
		case R.id.radioGroupSPANEWithPhotoJoyful:
			radioButtonID = radioGroupJoyful.getCheckedRadioButtonId();
			radioButton = radioGroupJoyful.findViewById(radioButtonID);
			idx = radioGroupJoyful.indexOfChild(radioButton);

			switch (idx) {
			case 0:
				imvJoyful.setImageResource(R.drawable.spane_joyful1);
				break;
			case 1:
				imvJoyful.setImageResource(R.drawable.spane_joyful2);
				break;
			case 2:
				imvJoyful.setImageResource(R.drawable.spane_joyful3);
				break;
			case 3:
				imvJoyful.setImageResource(R.drawable.spane_joyful4);
				break;
			case 4:
				imvJoyful.setImageResource(R.drawable.spane_joyful5);
				break;
			}
			break;
			
		case R.id.radioGroupSPANEWithPhotoAngry:
			radioButtonID = radioGroupAngry.getCheckedRadioButtonId();
			radioButton = radioGroupAngry.findViewById(radioButtonID);
			idx = radioGroupAngry.indexOfChild(radioButton);

			switch (idx) {
			case 0:
				imvAngry.setImageResource(R.drawable.spane_angry1);
				break;
			case 1:
				imvAngry.setImageResource(R.drawable.spane_angry2);
				break;
			case 2:
				imvAngry.setImageResource(R.drawable.spane_angry3);
				break;
			case 3:
				imvAngry.setImageResource(R.drawable.spane_angry4);
				break;
			case 4:
				imvAngry.setImageResource(R.drawable.spane_angry5);
				break;
			}
			break;
			
		case R.id.radioGroupSPANEWithPhotoContented:
			radioButtonID = radioGroupContented.getCheckedRadioButtonId();
			radioButton = radioGroupContented.findViewById(radioButtonID);
			idx = radioGroupContented.indexOfChild(radioButton);

			switch (idx) {
			case 0:
				imvContented.setImageResource(R.drawable.spane_contented1);
				break;
			case 1:
				imvContented.setImageResource(R.drawable.spane_contented2);
				break;
			case 2:
				imvContented.setImageResource(R.drawable.spane_contented3);
				break;
			case 3:
				imvContented.setImageResource(R.drawable.spane_contented4);
				break;
			case 4:
				imvContented.setImageResource(R.drawable.spane_contented5);
				break;
			}
			break;

		}
	}
}
