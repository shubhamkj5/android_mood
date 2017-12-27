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
import android.widget.Toast;

import android.app.ActionBar;
import android.view.MenuItem;

public class StatisticsActivity extends Activity implements OnClickListener {

	Button btnAffectButton, btnSPANEWithPhoto, btnSPANENoPhoto,
			btnPANASShortNoPhoto, btnPANASLongWithPhoto, btnPAM;

	SharedPreferences pref;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_statistics);

		btnAffectButton = (Button) findViewById(R.id.buttonAffectButtonStat);
		btnSPANEWithPhoto = (Button) findViewById(R.id.buttonSPANEWithPhotoStat);
		btnSPANENoPhoto = (Button) findViewById(R.id.buttonSPANENoPhotoStat);
		btnPANASShortNoPhoto = (Button) findViewById(R.id.buttonPANASShortNoPhotoStat);
		btnPANASLongWithPhoto = (Button) findViewById(R.id.buttonPANASLongWithPhotoStat);
		btnPAM = (Button) findViewById(R.id.buttonPAMStat);

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

		if (settingChoice.compareTo("1") == 0) {
			Resources res = getResources();
			Drawable drawable = res
					.getDrawable(R.drawable.gradient_background1);
			LinearLayout statisticsLayout = (LinearLayout) findViewById(R.id.statisticsLayout);
			statisticsLayout.setBackground(drawable);

			btnAffectButton.setBackground(getResources().getDrawable(
					R.drawable.button_background1_effect));
			btnSPANEWithPhoto.setBackground(getResources().getDrawable(
					R.drawable.button_background1_effect));
			btnSPANENoPhoto.setBackground(getResources().getDrawable(
					R.drawable.button_background1_effect));
			btnPANASShortNoPhoto.setBackground(getResources().getDrawable(
					R.drawable.button_background1_effect));
			btnPANASLongWithPhoto.setBackground(getResources().getDrawable(
					R.drawable.button_background1_effect));
			btnPAM.setBackground(getResources().getDrawable(
					R.drawable.button_background1_effect));
		} else if (settingChoice.compareTo("2") == 0) {
			Resources res = getResources();
			Drawable drawable = res
					.getDrawable(R.drawable.gradient_background2);
			LinearLayout statisticsLayout = (LinearLayout) findViewById(R.id.statisticsLayout);
			statisticsLayout.setBackground(drawable);

			btnAffectButton.setBackground(getResources().getDrawable(
					R.drawable.button_background2_effect));
			btnSPANEWithPhoto.setBackground(getResources().getDrawable(
					R.drawable.button_background2_effect));
			btnSPANENoPhoto.setBackground(getResources().getDrawable(
					R.drawable.button_background2_effect));
			btnPANASShortNoPhoto.setBackground(getResources().getDrawable(
					R.drawable.button_background2_effect));
			btnPANASLongWithPhoto.setBackground(getResources().getDrawable(
					R.drawable.button_background2_effect));
			btnPAM.setBackground(getResources().getDrawable(
					R.drawable.button_background2_effect));
		} else if (settingChoice.compareTo("3") == 0) {
			Resources res = getResources();
			Drawable drawable = res
					.getDrawable(R.drawable.gradient_background3);
			LinearLayout statisticsLayout = (LinearLayout) findViewById(R.id.statisticsLayout);
			statisticsLayout.setBackground(drawable);

			btnAffectButton.setBackground(getResources().getDrawable(
					R.drawable.button_background3_effect));
			btnSPANEWithPhoto.setBackground(getResources().getDrawable(
					R.drawable.button_background3_effect));
			btnSPANENoPhoto.setBackground(getResources().getDrawable(
					R.drawable.button_background3_effect));
			btnPANASShortNoPhoto.setBackground(getResources().getDrawable(
					R.drawable.button_background3_effect));
			btnPANASLongWithPhoto.setBackground(getResources().getDrawable(
					R.drawable.button_background3_effect));
			btnPAM.setBackground(getResources().getDrawable(
					R.drawable.button_background3_effect));
		} else {
			Log.i("BUGGGG", "setting choice is not correct");
		}
	}

	@Override
	public void onClick(View v) {
		Intent myIntent;

		switch (v.getId()) {
		case R.id.buttonAffectButtonStat:
			myIntent = new Intent(getApplicationContext(),
					AffectButtonStatActivity.class);
			startActivity(myIntent);
			break;
		case R.id.buttonSPANEWithPhotoStat:
			myIntent = new Intent(getApplicationContext(),
					SPANEWithPhotoStatActivity.class);
			startActivity(myIntent);
			break;
		case R.id.buttonPANASShortNoPhotoStat:
			myIntent = new Intent(getApplicationContext(),
					PANASShortNoPhotoStatActivity.class);
			startActivity(myIntent);
			break;
		case R.id.buttonSPANENoPhotoStat:
			myIntent = (new SPANENoPhotoStatActivity())
					.execute(getApplicationContext());
			startActivity(myIntent);
			Toast.makeText(StatisticsActivity.this,
					"Tip: You can use your fingers to move the chart or zoom in and zoom out it", Toast.LENGTH_LONG)
					.show();
			break;

		case R.id.buttonPANASLongWithPhotoStat:
			myIntent = (new PANASLongWithPhotoStatActivity())
					.execute(getApplicationContext());
			Toast.makeText(StatisticsActivity.this,
					"Tip: You can use your fingers to move the chart or zoom in and zoom out it", Toast.LENGTH_LONG)
					.show();
			startActivity(myIntent);
			break;
		case R.id.buttonPAMStat:
			myIntent = new Intent(getApplicationContext(),
					PAMStatActivity.class);
			startActivity(myIntent);
			break;
		default:
			break;
		}
	}
}
