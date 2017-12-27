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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import android.app.ActionBar;
import android.view.MenuItem;

public class PANASLongWithPhotoActivity extends Activity implements
		OnClickListener, OnSeekBarChangeListener {

	private static final String PANAS_LONG_WITH_PHOTO_DATA_FILENAME = "PANASLongWithPhotoData.txt";

	Button btnSave, btnNote, btnShare;

	SeekBar seekBarInterested, seekBarDistressed, seekBarExcited, seekBarUpset,
			seekBarStrong, seekBarGuilty, seekBarScared, seekBarHostile,
			seekBarEnthusiastic, seekBarProud, seekBarIrritable, seekBarAlert,
			seekBarAshamed, seekBarInspired, seekBarNervous, seekBarDetermined,
			seekBarAttentive, seekBarJittery, seekBarActive, seekBarAfraid;

	int progressInterested = 2, progressDistressed = 2, progressExcited = 2,
			progressUpset = 2, progressStrong = 2, progressGuilty = 2,
			progressScared = 2, progressHostile = 2, progressEnthusiastic = 2,
			progressProud = 2, progressIrritable = 2, progressAlert = 2,
			progressAshamed = 2, progressInspired = 2, progressNervous = 2,
			progressDetermined = 2, progressAttentive = 2, progressJittery = 2,
			progressActive = 2, progressAfraid = 2;

	ImageView imvInterested, imvDistressed, imvExcited, imvUpset, imvStrong,
			imvGuilty, imvScared, imvHostile, imvEnthusiastic, imvProud,
			imvIrritable, imvAlert, imvAshamed, imvInspired, imvNervous,
			imvDetermined, imvAttentive, imvJittery, imvActive, imvAfraid;

	String note = "";

	SoundPool sp;
	int soundID;
	
	SharedPreferences pref;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_panas_long_with_photo);

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
		btnSave = (Button) findViewById(R.id.buttonSavePANASLongWithPhoto);
		btnNote = (Button) findViewById(R.id.buttonNotePANASLongWithPhoto);
		btnShare = (Button) findViewById(R.id.buttonSharePANASLongWithPhoto);

		btnSave.setOnClickListener(this);
		btnNote.setOnClickListener(this);
		btnShare.setOnClickListener(this);

		seekBarInterested = (SeekBar) findViewById(R.id.seekBarPANASLongInterested);
		seekBarDistressed = (SeekBar) findViewById(R.id.seekBarPANASLongDistressed);
		seekBarExcited = (SeekBar) findViewById(R.id.seekBarPANASLongExcited);
		seekBarUpset = (SeekBar) findViewById(R.id.seekBarPANASLongUpset);
		seekBarStrong = (SeekBar) findViewById(R.id.seekBarPANASLongStrong);
		seekBarGuilty = (SeekBar) findViewById(R.id.seekBarPANASLongGuilty);
		seekBarScared = (SeekBar) findViewById(R.id.seekBarPANASLongScared);
		seekBarHostile = (SeekBar) findViewById(R.id.seekBarPANASLongHostile);
		seekBarEnthusiastic = (SeekBar) findViewById(R.id.seekBarPANASLongEnthusiastic);
		seekBarProud = (SeekBar) findViewById(R.id.seekBarPANASLongProud);
		seekBarIrritable = (SeekBar) findViewById(R.id.seekBarPANASLongIrritable);
		seekBarAlert = (SeekBar) findViewById(R.id.seekBarPANASLongAlert);
		seekBarAshamed = (SeekBar) findViewById(R.id.seekBarPANASLongAshamed);
		seekBarInspired = (SeekBar) findViewById(R.id.seekBarPANASLongInspired);
		seekBarNervous = (SeekBar) findViewById(R.id.seekBarPANASLongNervous);
		seekBarDetermined = (SeekBar) findViewById(R.id.seekBarPANASLongDetermined);
		seekBarAttentive = (SeekBar) findViewById(R.id.seekBarPANASLongAttentive);
		seekBarJittery = (SeekBar) findViewById(R.id.seekBarPANASLongJittery);
		seekBarActive = (SeekBar) findViewById(R.id.seekBarPANASLongActive);
		seekBarAfraid = (SeekBar) findViewById(R.id.seekBarPANASLongAfraid);

		seekBarInterested.setOnSeekBarChangeListener(this);
		seekBarDistressed.setOnSeekBarChangeListener(this);
		seekBarExcited.setOnSeekBarChangeListener(this);
		seekBarUpset.setOnSeekBarChangeListener(this);
		seekBarStrong.setOnSeekBarChangeListener(this);
		seekBarGuilty.setOnSeekBarChangeListener(this);
		seekBarScared.setOnSeekBarChangeListener(this);
		seekBarHostile.setOnSeekBarChangeListener(this);
		seekBarEnthusiastic.setOnSeekBarChangeListener(this);
		seekBarProud.setOnSeekBarChangeListener(this);
		seekBarIrritable.setOnSeekBarChangeListener(this);
		seekBarAlert.setOnSeekBarChangeListener(this);
		seekBarAshamed.setOnSeekBarChangeListener(this);
		seekBarInspired.setOnSeekBarChangeListener(this);
		seekBarNervous.setOnSeekBarChangeListener(this);
		seekBarDetermined.setOnSeekBarChangeListener(this);
		seekBarAttentive.setOnSeekBarChangeListener(this);
		seekBarJittery.setOnSeekBarChangeListener(this);
		seekBarActive.setOnSeekBarChangeListener(this);
		seekBarAfraid.setOnSeekBarChangeListener(this);

		imvInterested = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoInterested);
		imvDistressed = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoDistressed);
		imvExcited = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoExcited);
		imvUpset = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoUpset);
		imvStrong = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoStrong);
		imvGuilty = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoGuilty);
		imvScared = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoScared);
		imvHostile = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoHostile);
		imvEnthusiastic = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoEnthusiastic);
		imvProud = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoProud);
		imvIrritable = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoIrritable);
		imvAlert = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoAlert);
		imvAshamed = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoAshamed);
		imvInspired = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoInspired);
		imvNervous = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoNervous);
		imvDetermined = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoDetermined);
		imvAttentive = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoAttentive);
		imvJittery = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoJittery);
		imvActive = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoActive);
		imvAfraid = (ImageView) findViewById(R.id.imageViewPANASLongWithPhotoAfraid);
		
		sp = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
		soundID = sp.load(this, R.raw.save_sound, 1);
		
		pref = getSharedPreferences("settings", 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonSavePANASLongWithPhoto:
			saveData();
			break;
		case R.id.buttonSharePANASLongWithPhoto:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "I am feeling  right now!");
			sendIntent.putExtra(Intent.EXTRA_SUBJECT, "My mood today.");
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, "Share with your friends"));
			
			break;
		case R.id.buttonNotePANASLongWithPhoto:
			displayNoteDialog();
			break;
		}

	}

	@SuppressLint("SimpleDateFormat")
	private void saveData() {
		String currentDateAndTime, data;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		currentDateAndTime = sdf.format(new Date());

		data = currentDateAndTime + "," + "interested" + ","
				+ String.valueOf(progressInterested+1) + "," + "distressed" + ","
				+ String.valueOf(progressDistressed+1) + "," + "excited" + ","
				+ String.valueOf(progressExcited+1) + "," + "upset" + ","
				+ String.valueOf(progressUpset+1) + "," + "strong" + ","
				+ String.valueOf(progressStrong+1) + "," + "guilty" + ","
				+ String.valueOf(progressGuilty+1) + "," + "scared" + ","
				+ String.valueOf(progressScared+1) + "," + "hostile" + ","
				+ String.valueOf(progressHostile+1) + "," + "enthusiastic" + ","
				+ String.valueOf(progressEnthusiastic+1) + "," + "proud" + ","
				+ String.valueOf(progressProud+1) + "," + "irritable" + ","
				+ String.valueOf(progressIrritable+1) + "," + "alert" + ","
				+ String.valueOf(progressAlert+1) + "," + "ashamed" + ","
				+ String.valueOf(progressAshamed+1) + "," + "inspired" + ","
				+ String.valueOf(progressInspired+1) + "," + "nervous" + ","
				+ String.valueOf(progressNervous+1) + "," + "determined" + ","
				+ String.valueOf(progressDetermined+1) + "," + "attentive" + ","
				+ String.valueOf(progressAttentive+1) + "," + "jittery" + ","
				+ String.valueOf(progressJittery+1) + "," + "active" + ","
				+ String.valueOf(progressActive+1) + "," + "afraid" + ","
				+ String.valueOf(progressAfraid+1) + "," + note + "\n";

		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					openFileOutput(PANAS_LONG_WITH_PHOTO_DATA_FILENAME,
							Context.MODE_APPEND));
			outputStreamWriter.append(data);
			outputStreamWriter.close();
		} catch (IOException e) {

		}
		
		Toast.makeText(PANASLongWithPhotoActivity.this,"Saved", Toast.LENGTH_SHORT).show();
		
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
	public void onProgressChanged(SeekBar skb, int val, boolean fromUser) {
		switch (skb.getId()) {
		case R.id.seekBarPANASLongInterested:
			progressInterested = val;
			switch (val) {
			case 0:
				imvInterested.setImageResource(R.drawable.panas_interested1);
				break;
			case 1:
				imvInterested.setImageResource(R.drawable.panas_interested2);
				break;
			case 2:
				imvInterested.setImageResource(R.drawable.panas_interested3);
				break;
			case 3:
				imvInterested.setImageResource(R.drawable.panas_interested4);
				break;
			case 4:
				imvInterested.setImageResource(R.drawable.panas_interested5);
				break;
			}
			break;

		case R.id.seekBarPANASLongDistressed:
			progressDistressed = val;
			switch (val) {
			case 0:
				imvDistressed.setImageResource(R.drawable.panas_distressed1);
				break;
			case 1:
				imvDistressed.setImageResource(R.drawable.panas_distressed2);
				break;
			case 2:
				imvDistressed.setImageResource(R.drawable.panas_distressed3);
				break;
			case 3:
				imvDistressed.setImageResource(R.drawable.panas_distressed4);
				break;
			case 4:
				imvDistressed.setImageResource(R.drawable.panas_distressed5);
				break;
			}
			break;

		case R.id.seekBarPANASLongExcited:
			progressExcited = val;
			switch (val) {
			case 0:
				imvExcited.setImageResource(R.drawable.panas_excited1);
				break;
			case 1:
				imvExcited.setImageResource(R.drawable.panas_excited2);
				break;
			case 2:
				imvExcited.setImageResource(R.drawable.panas_excited3);
				break;
			case 3:
				imvExcited.setImageResource(R.drawable.panas_excited4);
				break;
			case 4:
				imvExcited.setImageResource(R.drawable.panas_excited5);
				break;
			}
			break;

		case R.id.seekBarPANASLongUpset:
			progressUpset = val;
			switch (val) {
			case 0:
				imvUpset.setImageResource(R.drawable.panas_upset1);
				break;
			case 1:
				imvUpset.setImageResource(R.drawable.panas_upset2);
				break;
			case 2:
				imvUpset.setImageResource(R.drawable.panas_upset3);
				break;
			case 3:
				imvUpset.setImageResource(R.drawable.panas_upset4);
				break;
			case 4:
				imvUpset.setImageResource(R.drawable.panas_upset5);
				break;
			}
			break;

		case R.id.seekBarPANASLongStrong:
			progressStrong = val;
			switch (val) {
			case 0:
				imvStrong.setImageResource(R.drawable.panas_strong1);
				break;
			case 1:
				imvStrong.setImageResource(R.drawable.panas_strong2);
				break;
			case 2:
				imvStrong.setImageResource(R.drawable.panas_strong3);
				break;
			case 3:
				imvStrong.setImageResource(R.drawable.panas_strong4);
				break;
			case 4:
				imvStrong.setImageResource(R.drawable.panas_strong5);
				break;
			}
			break;

		case R.id.seekBarPANASLongGuilty:
			progressGuilty = val;
			switch (val) {
			case 0:
				imvGuilty.setImageResource(R.drawable.panas_guilty1);
				break;
			case 1:
				imvGuilty.setImageResource(R.drawable.panas_guilty2);
				break;
			case 2:
				imvGuilty.setImageResource(R.drawable.panas_guilty3);
				break;
			case 3:
				imvGuilty.setImageResource(R.drawable.panas_guilty4);
				break;
			case 4:
				imvGuilty.setImageResource(R.drawable.panas_guilty5);
				break;
			}
			break;

		case R.id.seekBarPANASLongScared:
			progressScared = val;
			switch (val) {
			case 0:
				imvScared.setImageResource(R.drawable.panas_scared1);
				break;
			case 1:
				imvScared.setImageResource(R.drawable.panas_scared2);
				break;
			case 2:
				imvScared.setImageResource(R.drawable.panas_scared3);
				break;
			case 3:
				imvScared.setImageResource(R.drawable.panas_scared4);
				break;
			case 4:
				imvScared.setImageResource(R.drawable.panas_scared5);
				break;
			}
			break;

		case R.id.seekBarPANASLongHostile:
			progressHostile = val;
			switch (val) {
			case 0:
				imvHostile.setImageResource(R.drawable.panas_hostile1);
				break;
			case 1:
				imvHostile.setImageResource(R.drawable.panas_hostile2);
				break;
			case 2:
				imvHostile.setImageResource(R.drawable.panas_hostile3);
				break;
			case 3:
				imvHostile.setImageResource(R.drawable.panas_hostile4);
				break;
			case 4:
				imvHostile.setImageResource(R.drawable.panas_hostile5);
				break;
			}
			break;

		case R.id.seekBarPANASLongEnthusiastic:
			progressEnthusiastic = val;
			switch (val) {
			case 0:
				imvEnthusiastic
						.setImageResource(R.drawable.panas_enthusiastic1);
				break;
			case 1:
				imvEnthusiastic
						.setImageResource(R.drawable.panas_enthusiastic2);
				break;
			case 2:
				imvEnthusiastic
						.setImageResource(R.drawable.panas_enthusiastic3);
				break;
			case 3:
				imvEnthusiastic
						.setImageResource(R.drawable.panas_enthusiastic4);
				break;
			case 4:
				imvEnthusiastic
						.setImageResource(R.drawable.panas_enthusiastic5);
				break;
			}
			break;

		case R.id.seekBarPANASLongProud:
			progressProud = val;
			switch (val) {
			case 0:
				imvProud.setImageResource(R.drawable.panas_proud1);
				break;
			case 1:
				imvProud.setImageResource(R.drawable.panas_proud2);
				break;
			case 2:
				imvProud.setImageResource(R.drawable.panas_proud3);
				break;
			case 3:
				imvProud.setImageResource(R.drawable.panas_proud4);
				break;
			case 4:
				imvProud.setImageResource(R.drawable.panas_proud5);
				break;
			}
			break;

		case R.id.seekBarPANASLongIrritable:
			progressIrritable = val;
			switch (val) {
			case 0:
				imvIrritable.setImageResource(R.drawable.panas_irritable1);
				break;
			case 1:
				imvIrritable.setImageResource(R.drawable.panas_irritable2);
				break;
			case 2:
				imvIrritable.setImageResource(R.drawable.panas_irritable3);
				break;
			case 3:
				imvIrritable.setImageResource(R.drawable.panas_irritable4);
				break;
			case 4:
				imvIrritable.setImageResource(R.drawable.panas_irritable5);
				break;
			}
			break;

		case R.id.seekBarPANASLongAlert:
			progressAlert = val;
			switch (val) {
			case 0:
				imvAlert.setImageResource(R.drawable.panas_alert1);
				break;
			case 1:
				imvAlert.setImageResource(R.drawable.panas_alert2);
				break;
			case 2:
				imvAlert.setImageResource(R.drawable.panas_alert3);
				break;
			case 3:
				imvAlert.setImageResource(R.drawable.panas_alert4);
				break;
			case 4:
				imvAlert.setImageResource(R.drawable.panas_alert5);
				break;
			}
			break;

		case R.id.seekBarPANASLongAshamed:
			progressAshamed = val;
			switch (val) {
			case 0:
				imvAshamed.setImageResource(R.drawable.panas_ashamed1);
				break;
			case 1:
				imvAshamed.setImageResource(R.drawable.panas_ashamed2);
				break;
			case 2:
				imvAshamed.setImageResource(R.drawable.panas_ashamed3);
				break;
			case 3:
				imvAshamed.setImageResource(R.drawable.panas_ashamed4);
				break;
			case 4:
				imvAshamed.setImageResource(R.drawable.panas_ashamed5);
				break;
			}
			break;

		case R.id.seekBarPANASLongInspired:
			progressInspired = val;
			switch (val) {
			case 0:
				imvInspired.setImageResource(R.drawable.panas_inspired1);
				break;
			case 1:
				imvInspired.setImageResource(R.drawable.panas_inspired2);
				break;
			case 2:
				imvInspired.setImageResource(R.drawable.panas_inspired3);
				break;
			case 3:
				imvInspired.setImageResource(R.drawable.panas_inspired4);
				break;
			case 4:
				imvInspired.setImageResource(R.drawable.panas_inspired5);
				break;
			}
			break;

		case R.id.seekBarPANASLongNervous:
			progressNervous = val;
			switch (val) {
			case 0:
				imvNervous.setImageResource(R.drawable.panas_nervous1);
				break;
			case 1:
				imvNervous.setImageResource(R.drawable.panas_nervous2);
				break;
			case 2:
				imvNervous.setImageResource(R.drawable.panas_nervous3);
				break;
			case 3:
				imvNervous.setImageResource(R.drawable.panas_nervous4);
				break;
			case 4:
				imvNervous.setImageResource(R.drawable.panas_nervous5);
				break;
			}
			break;

		case R.id.seekBarPANASLongDetermined:
			progressDetermined = val;
			switch (val) {
			case 0:
				imvDetermined.setImageResource(R.drawable.panas_determined1);
				break;
			case 1:
				imvDetermined.setImageResource(R.drawable.panas_determined2);
				break;
			case 2:
				imvDetermined.setImageResource(R.drawable.panas_determined3);
				break;
			case 3:
				imvDetermined.setImageResource(R.drawable.panas_determined4);
				break;
			case 4:
				imvDetermined.setImageResource(R.drawable.panas_determined5);
				break;
			}
			break;

		case R.id.seekBarPANASLongAttentive:
			progressAttentive = val;
			switch (val) {
			case 0:
				imvAttentive.setImageResource(R.drawable.panas_attentive1);
				break;
			case 1:
				imvAttentive.setImageResource(R.drawable.panas_attentive2);
				break;
			case 2:
				imvAttentive.setImageResource(R.drawable.panas_attentive3);
				break;
			case 3:
				imvAttentive.setImageResource(R.drawable.panas_attentive4);
				break;
			case 4:
				imvAttentive.setImageResource(R.drawable.panas_attentive5);
				break;
			}
			break;

		case R.id.seekBarPANASLongJittery:
			progressJittery = val;
			switch (val) {
			case 0:
				imvJittery.setImageResource(R.drawable.panas_jittery1);
				break;
			case 1:
				imvJittery.setImageResource(R.drawable.panas_jittery2);
				break;
			case 2:
				imvJittery.setImageResource(R.drawable.panas_jittery3);
				break;
			case 3:
				imvJittery.setImageResource(R.drawable.panas_jittery4);
				break;
			case 4:
				imvJittery.setImageResource(R.drawable.panas_jittery5);
				break;
			}
			break;

		case R.id.seekBarPANASLongActive:
			progressActive = val;
			switch (val) {
			case 0:
				imvActive.setImageResource(R.drawable.panas_active1);
				break;
			case 1:
				imvActive.setImageResource(R.drawable.panas_active2);
				break;
			case 2:
				imvActive.setImageResource(R.drawable.panas_active3);
				break;
			case 3:
				imvActive.setImageResource(R.drawable.panas_active4);
				break;
			case 4:
				imvActive.setImageResource(R.drawable.panas_active5);
				break;
			}
			break;

		case R.id.seekBarPANASLongAfraid:
			progressAfraid = val;
			switch (val) {
			case 0:
				imvAfraid.setImageResource(R.drawable.panas_afraid1);
				break;
			case 1:
				imvAfraid.setImageResource(R.drawable.panas_afraid2);
				break;
			case 2:
				imvAfraid.setImageResource(R.drawable.panas_afraid3);
				break;
			case 3:
				imvAfraid.setImageResource(R.drawable.panas_afraid4);
				break;
			case 4:
				imvAfraid.setImageResource(R.drawable.panas_afraid5);
				break;
			}
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {

	}
}
