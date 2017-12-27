package com.example.affectassessment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import android.app.ActionBar;
import android.view.MenuItem;

public class ReportMoodActivity extends Activity implements OnClickListener{

	Button btnAffectButton, btnSPANEWithPhoto, btnSPANENoPhoto,
	btnPANASShortNoPhoto, btnPANASLongWithPhoto, btnPAM;
	
	SharedPreferences pref;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_report_mood);
		
		btnAffectButton = (Button) findViewById(R.id.buttonAffectButton);
		btnSPANEWithPhoto = (Button) findViewById(R.id.buttonSPANEWithPhoto);
		btnSPANENoPhoto = (Button) findViewById(R.id.buttonSPANENoPhoto);
		btnPANASShortNoPhoto = (Button) findViewById(R.id.buttonPANASShortNoPhoto);
		btnPANASLongWithPhoto = (Button) findViewById(R.id.buttonPANASLongWithPhoto);
		btnPAM = (Button) findViewById(R.id.buttonPAM);
		
		btnAffectButton.setOnClickListener(this);
		btnSPANEWithPhoto.setOnClickListener(this);
		btnSPANENoPhoto.setOnClickListener(this);
		btnPANASShortNoPhoto.setOnClickListener(this);
		btnPANASLongWithPhoto.setOnClickListener(this);
		btnPAM.setOnClickListener(this);
		
		setTheme();
		
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
						MainActivity.class);
	        	myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item); 
	    }
	}
	
	@SuppressLint("NewApi")
	private void setTheme() {
		pref = getSharedPreferences("settings", 0);
		
		String settingChoice = pref.getString("choice", "-1");
		
		if (settingChoice.compareTo("1") == 0){
			Resources res = getResources();
			Drawable drawable = res.getDrawable(R.drawable.gradient_background1); 
			LinearLayout reportMoodLayout = (LinearLayout)findViewById(R.id.reportMoodLayout);
			reportMoodLayout.setBackground(drawable);
			
			btnAffectButton.setBackground(getResources().getDrawable(R.drawable.button_background1_effect));
			btnSPANEWithPhoto.setBackground(getResources().getDrawable(R.drawable.button_background1_effect));
			btnSPANENoPhoto.setBackground(getResources().getDrawable(R.drawable.button_background1_effect));
			btnPANASShortNoPhoto.setBackground(getResources().getDrawable(R.drawable.button_background1_effect));
			btnPANASLongWithPhoto.setBackground(getResources().getDrawable(R.drawable.button_background1_effect));
			btnPAM.setBackground(getResources().getDrawable(R.drawable.button_background1_effect));
			
		} else if (settingChoice.compareTo("2") == 0){
			Resources res = getResources();
			Drawable drawable = res.getDrawable(R.drawable.gradient_background2); 
			LinearLayout reportMoodLayout = (LinearLayout)findViewById(R.id.reportMoodLayout);
			reportMoodLayout.setBackground(drawable);
			
			btnAffectButton.setBackground(getResources().getDrawable(R.drawable.button_background2_effect));
			btnSPANEWithPhoto.setBackground(getResources().getDrawable(R.drawable.button_background2_effect));
			btnSPANENoPhoto.setBackground(getResources().getDrawable(R.drawable.button_background2_effect));
			btnPANASShortNoPhoto.setBackground(getResources().getDrawable(R.drawable.button_background2_effect));
			btnPANASLongWithPhoto.setBackground(getResources().getDrawable(R.drawable.button_background2_effect));
			btnPAM.setBackground(getResources().getDrawable(R.drawable.button_background2_effect));
		} else if (settingChoice.compareTo("3") == 0){
			Resources res = getResources();
			Drawable drawable = res.getDrawable(R.drawable.gradient_background3); 
			LinearLayout reportMoodLayout = (LinearLayout)findViewById(R.id.reportMoodLayout);
			reportMoodLayout.setBackground(drawable);
			
			btnAffectButton.setBackground(getResources().getDrawable(R.drawable.button_background3_effect));
			btnSPANEWithPhoto.setBackground(getResources().getDrawable(R.drawable.button_background3_effect));
			btnSPANENoPhoto.setBackground(getResources().getDrawable(R.drawable.button_background3_effect));
			btnPANASShortNoPhoto.setBackground(getResources().getDrawable(R.drawable.button_background3_effect));
			btnPANASLongWithPhoto.setBackground(getResources().getDrawable(R.drawable.button_background3_effect));
			btnPAM.setBackground(getResources().getDrawable(R.drawable.button_background3_effect));
		} else {
			Log.i("BUGGGG", "setting choice is not correct");
		}
	}
	
	@Override
	public void onClick(View v) {
		Intent myIntent;
		
		switch (v.getId()) {
		case R.id.buttonAffectButton:
			myIntent = new Intent(getApplicationContext(),
					AffectButtonActivity.class);
			startActivity(myIntent);
			break;
		case R.id.buttonSPANEWithPhoto:
			myIntent = new Intent(getApplicationContext(),
					SPANEWithPhotoActivity.class);
			startActivity(myIntent);
			break;
		case R.id.buttonSPANENoPhoto:
			myIntent = new Intent(getApplicationContext(),
					SPANENoPhotoActivity.class);
			startActivity(myIntent);
			break;
		case R.id.buttonPANASShortNoPhoto:
			myIntent = new Intent(getApplicationContext(),
					PANASShortNoPhotoActivity.class);
			startActivity(myIntent);
			break;
		case R.id.buttonPANASLongWithPhoto:
			myIntent = new Intent(getApplicationContext(),
					PANASLongWithPhotoActivity.class);
			startActivity(myIntent);
			break;
		case R.id.buttonPAM:
			myIntent = new Intent(getApplicationContext(),
					PAMActivity.class);
			startActivity(myIntent);
			break;
		default:
			break;
		}
		
	}

}
