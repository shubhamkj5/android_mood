package com.example.affectassessment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import android.widget.RadioGroup.OnCheckedChangeListener;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends Activity implements OnClickListener {

	private static final String AFFECTBUTTON_DATA_FILENAME = "AffectButtonData.txt";
	private static final String SPANE_NO_PHOTO_DATA_FILENAME = "SPANENoPhotoData.txt";
	private static final String SPANE_WITH_PHOTO_DATA_FILENAME = "SPANEWithPhotoData.txt";
	private static final String PANAS_LONG_WITH_PHOTO_DATA_FILENAME = "PANASLongWithPhotoData.txt";
	private static final String PANAS_SHORT_NO_PHOTO_DATA_FILENAME = "PANASShortNoPhotoData.txt";
	private static final String PAM_DATA_FILENAME = "PAMData.txt";

	Button btnReportMood, btnStatistics, btnExport, btnSend, btnSettings,
			btnAbout;

	SharedPreferences pref;

	SoundPool sp;
	int soundID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnReportMood = (Button) findViewById(R.id.buttonReportMood);
		btnStatistics = (Button) findViewById(R.id.buttonStatistics);
		btnExport = (Button) findViewById(R.id.buttonExportToPDF);
		btnSend = (Button) findViewById(R.id.buttonSendData);
		btnSettings = (Button) findViewById(R.id.buttonSettings);
		btnAbout = (Button) findViewById(R.id.buttonAbout);

		btnReportMood.setOnClickListener(this);
		btnStatistics.setOnClickListener(this);
		btnExport.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		btnSettings.setOnClickListener(this);
		btnAbout.setOnClickListener(this);

		pref = getSharedPreferences("settings", 0);
		
		setTheme();
		
		setSound();
	}

	private void setSound() {
		String settingSound = pref.getString("sound", "-1");
		if (settingSound.compareTo("-1") == 0) {
			SharedPreferences.Editor editor = pref.edit();
			editor.putString("sound", "1");
			editor.commit();
			
		}
		
		sp = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
		soundID = sp.load(this, R.raw.save_sound, 1);
	}

	@SuppressLint("NewApi")
	private void setTheme() {
		String settingChoice = pref.getString("choice", "-1");
		if (settingChoice.compareTo("-1") == 0) {
			SharedPreferences.Editor editor = pref.edit();
			editor.putString("choice", "1");
			editor.commit();
			settingChoice = "1";
			
		}

		if (settingChoice.compareTo("1") == 0) {
			Resources res = getResources();
			Drawable drawable = res
					.getDrawable(R.drawable.gradient_background1);
			LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
			mainLayout.setBackground(drawable);

			btnReportMood.setBackground(getResources().getDrawable(
					R.drawable.button_background1_effect));
			btnStatistics.setBackground(getResources().getDrawable(
					R.drawable.button_background1_effect));
			btnExport.setBackground(getResources().getDrawable(
					R.drawable.button_background1_effect));
			btnSend.setBackground(getResources().getDrawable(
					R.drawable.button_background1_effect));
			btnAbout.setBackground(getResources().getDrawable(
					R.drawable.button_background1_effect));
			btnSettings.setBackground(getResources().getDrawable(
					R.drawable.button_background1_effect));
		} else if (settingChoice.compareTo("2") == 0) {
			Resources res = getResources();
			Drawable drawable = res
					.getDrawable(R.drawable.gradient_background2);
			LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
			mainLayout.setBackground(drawable);

			btnReportMood.setBackground(getResources().getDrawable(
					R.drawable.button_background2_effect));
			btnStatistics.setBackground(getResources().getDrawable(
					R.drawable.button_background2_effect));
			btnExport.setBackground(getResources().getDrawable(
					R.drawable.button_background2_effect));
			btnSend.setBackground(getResources().getDrawable(
					R.drawable.button_background2_effect));
			btnAbout.setBackground(getResources().getDrawable(
					R.drawable.button_background2_effect));
			btnSettings.setBackground(getResources().getDrawable(
					R.drawable.button_background2_effect));
		} else if (settingChoice.compareTo("3") == 0) {
			Resources res = getResources();
			Drawable drawable = res
					.getDrawable(R.drawable.gradient_background3);
			LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
			mainLayout.setBackground(drawable);

			btnReportMood.setBackground(getResources().getDrawable(
					R.drawable.button_background3_effect));
			btnStatistics.setBackground(getResources().getDrawable(
					R.drawable.button_background3_effect));
			btnExport.setBackground(getResources().getDrawable(
					R.drawable.button_background3_effect));
			btnSend.setBackground(getResources().getDrawable(
					R.drawable.button_background3_effect));
			btnAbout.setBackground(getResources().getDrawable(
					R.drawable.button_background3_effect));
			btnSettings.setBackground(getResources().getDrawable(
					R.drawable.button_background3_effect));
		} else {
			Log.i("BUG", "setting choice is not correct");
		}
	}

	@Override
	public void onClick(View v) {
		Intent myIntent;

		String fileName;

		switch (v.getId()) {
		case R.id.buttonReportMood:
			Log.i("report clicked", "dcm");
			myIntent = new Intent(MainActivity.this.getApplicationContext(),
					ReportMoodActivity.class);
			startActivity(myIntent);
			break;
		case R.id.buttonStatistics:
			Log.i("statistics clicked", "vkl");
			myIntent = new Intent(MainActivity.this.getApplicationContext(),
					StatisticsActivity.class);
			startActivity(myIntent);
			break;
		case R.id.buttonExportToPDF:
			fileName = export(1);
			Toast.makeText(MainActivity.this,
					"Mood report created in phone storage", Toast.LENGTH_SHORT)
					.show();
			
			String settingSound = pref.getString("sound", "-1");
			if (settingSound.compareTo("1") == 0) {
				sp.play(soundID, 1, 1, 1, 0, 1);
			}
			
			openPDF(fileName);

			break;
		case R.id.buttonSendData:
			Toast.makeText(MainActivity.this, "Preparing data to send...",
					Toast.LENGTH_SHORT).show();
			fileName = export(2);

			break;
		case R.id.buttonSettings:
			showSettingsDialog();
			break;
		case R.id.buttonAbout:
			showAboutDialog();
			break;
		default:
			break;
		}

	}

	@SuppressLint("NewApi")
	private void showSettingsDialog() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.settings_dialog);
		// dialog.setTitle("Choose a theme");

		dialog.setCancelable(false);

		Button dialogButton = (Button) dialog
				.findViewById(R.id.dialogButtonSettingsOK);

		final RadioGroup radioGroupSettings = (RadioGroup) dialog
				.findViewById(R.id.radioGroupSettings);

		RadioButton radioBtnTheme1 = (RadioButton) dialog
				.findViewById(R.id.radioSettings1);
		RadioButton radioBtnTheme2 = (RadioButton) dialog
				.findViewById(R.id.radioSettings2);
		RadioButton radioBtnTheme3 = (RadioButton) dialog
				.findViewById(R.id.radioSettings3);

		String settingChoice = pref.getString("choice", "-1");

		Resources res = getResources();

		if (settingChoice.compareTo("1") == 0) {
			radioBtnTheme1.setChecked(true);
			radioBtnTheme2.setChecked(false);
			radioBtnTheme3.setChecked(false);

			Drawable drawable = res
					.getDrawable(R.drawable.gradient_background1);
			LinearLayout settingsLayout = (LinearLayout) dialog
					.findViewById(R.id.settingsLayout);
			settingsLayout.setBackground(drawable);
		} else if (settingChoice.compareTo("2") == 0) {
			radioBtnTheme2.setChecked(true);
			radioBtnTheme1.setChecked(false);
			radioBtnTheme3.setChecked(false);

			Drawable drawable = res
					.getDrawable(R.drawable.gradient_background2);
			LinearLayout settingsLayout = (LinearLayout) dialog
					.findViewById(R.id.settingsLayout);
			settingsLayout.setBackground(drawable);
		} else if (settingChoice.compareTo("3") == 0) {
			radioBtnTheme3.setChecked(true);
			radioBtnTheme2.setChecked(false);
			radioBtnTheme1.setChecked(false);

			Drawable drawable = res
					.getDrawable(R.drawable.gradient_background3);
			LinearLayout settingsLayout = (LinearLayout) dialog
					.findViewById(R.id.settingsLayout);
			settingsLayout.setBackground(drawable);
		} else {
			Log.i("BUGGGGG", "setting choice incorrect");
		}

		CheckBox checkBoxSound = (CheckBox) dialog.findViewById(R.id.checkBoxSound);
		
		String settingSound = pref.getString("sound", "-1");
		if (settingSound.compareTo("1") == 0) {
			checkBoxSound.setChecked(true);
		} else{
			checkBoxSound.setChecked(false);
		}
		
		checkBoxSound.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (((CheckBox) v).isChecked()){
					SharedPreferences.Editor editor = pref.edit();
					editor.putString("sound", "1");
					editor.commit();
					
					sp.play(soundID, 1, 1, 1, 0, 1);
				} else{
					SharedPreferences.Editor editor = pref.edit();
					editor.putString("sound", "0");
					editor.commit();
				}
				
			}

			
			
		});
		
		
		
		radioGroupSettings
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int id) {
						int radioButtonID, idx;
						View radioButton;

						SharedPreferences.Editor editor = pref.edit();

						switch (group.getId()) {
						case R.id.radioGroupSettings:
							radioButtonID = radioGroupSettings
									.getCheckedRadioButtonId();
							radioButton = radioGroupSettings
									.findViewById(radioButtonID);
							idx = radioGroupSettings.indexOfChild(radioButton);

							switch (idx) {
							case 0:
								editor.putString("choice", "1");
								editor.commit();
								break;
							case 1:
								editor.putString("choice", "2");
								editor.commit();
								break;
							case 2:
								editor.putString("choice", "3");
								editor.commit();
							default:
								break;
							}
							break;

						default:
							break;
						}

						String settingChoice = pref.getString("choice", "-1");

						if (settingChoice.compareTo("1") == 0) {
							Resources res = getResources();
							Drawable drawable = res
									.getDrawable(R.drawable.gradient_background1);
							LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
							mainLayout.setBackground(drawable);

							LinearLayout settingsLayout = (LinearLayout) dialog
									.findViewById(R.id.settingsLayout);
							settingsLayout.setBackground(drawable);

							btnReportMood
									.setBackground(getResources()
											.getDrawable(
													R.drawable.button_background1_effect));
							btnStatistics
									.setBackground(getResources()
											.getDrawable(
													R.drawable.button_background1_effect));
							btnExport.setBackground(getResources().getDrawable(
									R.drawable.button_background1_effect));
							btnSend.setBackground(getResources().getDrawable(
									R.drawable.button_background1_effect));
							btnAbout.setBackground(getResources().getDrawable(
									R.drawable.button_background1_effect));
							btnSettings
									.setBackground(getResources()
											.getDrawable(
													R.drawable.button_background1_effect));
						} else if (settingChoice.compareTo("2") == 0) {
							Resources res = getResources();
							Drawable drawable = res
									.getDrawable(R.drawable.gradient_background2);
							LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
							mainLayout.setBackground(drawable);

							LinearLayout settingsLayout = (LinearLayout) dialog
									.findViewById(R.id.settingsLayout);
							settingsLayout.setBackground(drawable);

							btnReportMood
									.setBackground(getResources()
											.getDrawable(
													R.drawable.button_background2_effect));
							btnStatistics
									.setBackground(getResources()
											.getDrawable(
													R.drawable.button_background2_effect));
							btnExport.setBackground(getResources().getDrawable(
									R.drawable.button_background2_effect));
							btnSend.setBackground(getResources().getDrawable(
									R.drawable.button_background2_effect));
							btnAbout.setBackground(getResources().getDrawable(
									R.drawable.button_background2_effect));
							btnSettings
									.setBackground(getResources()
											.getDrawable(
													R.drawable.button_background2_effect));
						} else if (settingChoice.compareTo("3") == 0) {
							Resources res = getResources();
							Drawable drawable = res
									.getDrawable(R.drawable.gradient_background3);
							LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
							mainLayout.setBackground(drawable);

							LinearLayout settingsLayout = (LinearLayout) dialog
									.findViewById(R.id.settingsLayout);
							settingsLayout.setBackground(drawable);

							btnReportMood
									.setBackground(getResources()
											.getDrawable(
													R.drawable.button_background3_effect));
							btnStatistics
									.setBackground(getResources()
											.getDrawable(
													R.drawable.button_background3_effect));
							btnExport.setBackground(getResources().getDrawable(
									R.drawable.button_background3_effect));
							btnSend.setBackground(getResources().getDrawable(
									R.drawable.button_background3_effect));
							btnAbout.setBackground(getResources().getDrawable(
									R.drawable.button_background3_effect));
							btnSettings
									.setBackground(getResources()
											.getDrawable(
													R.drawable.button_background3_effect));
						} else {
							Log.i("BUGGGG", "setting choice is not correct");
						}
					}

				});

		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

		dialog.show();

		dialog.getWindow().setAttributes(lp);
	}

	private void openPDF(String fileName) {
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath() + fileName);
		Intent target = new Intent(Intent.ACTION_VIEW);
		target.setDataAndType(Uri.fromFile(file), "application/pdf");
		target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

		Intent intent = Intent.createChooser(target, "Open File");
		try {
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(
					MainActivity.this,
					"It seems that you don't have any PDF viewer app. Please install one",
					Toast.LENGTH_LONG).show();
		}
	}

	@SuppressLint("NewApi")
	private void showAboutDialog() {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.info_dialog);

		// dialog.setTitle("About Mood Self-Assessment");

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		String settingChoice = pref.getString("choice", "-1");

		if (settingChoice.compareTo("1") == 0) {
			Resources res = getResources();
			Drawable drawable = res
					.getDrawable(R.drawable.gradient_background1);
			LinearLayout aboutLayout = (LinearLayout) dialog
					.findViewById(R.id.aboutLayout);
			aboutLayout.setBackground(drawable);
		} else if (settingChoice.compareTo("2") == 0) {
			Resources res = getResources();
			Drawable drawable = res
					.getDrawable(R.drawable.gradient_background2);
			LinearLayout aboutLayout = (LinearLayout) dialog
					.findViewById(R.id.aboutLayout);
			aboutLayout.setBackground(drawable);
		} else if (settingChoice.compareTo("3") == 0) {
			Resources res = getResources();
			Drawable drawable = res
					.getDrawable(R.drawable.gradient_background3);
			LinearLayout aboutLayout = (LinearLayout) dialog
					.findViewById(R.id.aboutLayout);
			aboutLayout.setBackground(drawable);
		}

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;

		dialog.show();

		dialog.getWindow().setAttributes(lp);
	}

	private String export(int type) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String currentDate = sdf.format(new Date());
		String FILE = "/[Mood Report][" + currentDate + "].pdf";

		try {
			Document document = new Document();
			boolean mExternalStorageAvailable = false;
			boolean mExternalStorageWriteable = false;
			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				// We can read and write the media
				mExternalStorageAvailable = mExternalStorageWriteable = true;
			} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
				// We can only read the media
				mExternalStorageAvailable = true;
				mExternalStorageWriteable = false;
			} else {
				// Something else is wrong. It may be one of many other states,
				// but all we need
				// to know is we can neither read nor write
				mExternalStorageAvailable = mExternalStorageWriteable = false;
			}
			String file = null;
			if (mExternalStorageWriteable) {
				file = Environment.getExternalStorageDirectory().getPath()
						+ FILE;
			}

			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			Paragraph p;

			Font titleFont = new Font(Font.FontFamily.HELVETICA, 23, 1,
					BaseColor.RED);
			p = new Paragraph("Summary of your mood in the last 30 days\n",
					titleFont);
			document.add(p);
			p = new Paragraph("\n", titleFont);
			document.add(p);

			Font headingFont = new Font(Font.FontFamily.HELVETICA, 17, 1,
					BaseColor.BLUE);

			p = new Paragraph("1/ AffectButton", headingFont);
			document.add(p);
			p = new Paragraph(readDataAffectButton());
			document.add(p);

			p = new Paragraph("\n", headingFont);
			document.add(p);
			p = new Paragraph("2/ SPANE", headingFont);
			document.add(p);
			p = new Paragraph(readDataSPANE(SPANE_NO_PHOTO_DATA_FILENAME));
			document.add(p);

			p = new Paragraph("\n", headingFont);
			document.add(p);
			p = new Paragraph("3/ SPANE (with photo)", headingFont);
			document.add(p);
			p = new Paragraph(readDataSPANE(SPANE_WITH_PHOTO_DATA_FILENAME));
			document.add(p);

			p = new Paragraph("\n", headingFont);
			document.add(p);
			p = new Paragraph("4/ PANAS", headingFont);
			document.add(p);
			p = new Paragraph(readDataPANAS());
			document.add(p);

			p = new Paragraph("\n", headingFont);
			document.add(p);
			p = new Paragraph("5/ PANAS (short form)", headingFont);
			document.add(p);
			p = new Paragraph(readDataPANASShort());
			document.add(p);

			p = new Paragraph("\n", headingFont);
			document.add(p);
			p = new Paragraph("6/ PAM", headingFont);
			document.add(p);
			p = new Paragraph(readDataPAM());
			document.add(p);

			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Start email intent
		if (type == 2) {
			String emailAddress[] = { "lmk19922000@gmail.com" };
			String emailSubject = "This my mood report created today";
			String emailMessage = "Please find the report in the attachment";

			Uri fileURI = Uri.fromFile(new File(Environment
					.getExternalStorageDirectory().getPath() + FILE));

			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					emailAddress);
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					emailSubject);
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					emailMessage);
			emailIntent.putExtra(Intent.EXTRA_STREAM, fileURI);
			emailIntent.setType("message/rfc822");
			startActivity(emailIntent);
		}

		return FILE;
	}

	private String readDataPAM() {
		StringBuilder str = new StringBuilder("");

		int countAfraid = 0, countTense = 0, countExcited = 0, countDelighted = 0, countFrustrated = 0, countAngry = 0, countHappy = 0, countGlad = 0, countMiserable = 0, countSad = 0, countCalm = 0, countSatisfied = 0, countGloomy = 0, countTired = 0, countSleepy = 0, countSerene = 0;

		String[] strSplit;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		Date today = new Date();
		Date startDate = new Date(today.getTime() - (long) 30 * 24 * 3600
				* 1000);
		Date tempDate;

		try {
			InputStream inputStream = getApplicationContext().openFileInput(
					PAM_DATA_FILENAME);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";

				while ((receiveString = bufferedReader.readLine()) != null) {
					strSplit = receiveString.split(",");
					try {
						tempDate = sdf.parse(strSplit[0] + "," + strSplit[1]);
						if (tempDate.compareTo(startDate) > 0) {
							str.append(strSplit[0] + "," + strSplit[1] + ": ");

							if (Integer.parseInt(strSplit[2]) == 1) {
								countAfraid++;
								str.append("Afraid");
							} else if (Integer.parseInt(strSplit[2]) == 2) {
								countTense++;
								str.append("Tense");
							} else if (Integer.parseInt(strSplit[2]) == 3) {
								countExcited++;
								str.append("Excited");
							} else if (Integer.parseInt(strSplit[2]) == 4) {
								countDelighted++;
								str.append("Delighted");
							} else if (Integer.parseInt(strSplit[2]) == 5) {
								countFrustrated++;
								str.append("Frustrated");
							} else if (Integer.parseInt(strSplit[2]) == 6) {
								countAngry++;
								str.append("Angry");
							} else if (Integer.parseInt(strSplit[2]) == 7) {
								countHappy++;
								str.append("Happy");
							} else if (Integer.parseInt(strSplit[2]) == 8) {
								countGlad++;
								str.append("Glad");
							} else if (Integer.parseInt(strSplit[2]) == 9) {
								countMiserable++;
								str.append("Miserable");
							} else if (Integer.parseInt(strSplit[2]) == 10) {
								countSad++;
								str.append("Sad");
							} else if (Integer.parseInt(strSplit[2]) == 11) {
								countCalm++;
								str.append("Calm");
							} else if (Integer.parseInt(strSplit[2]) == 12) {
								countSatisfied++;
								str.append("Satisfied");
							} else if (Integer.parseInt(strSplit[2]) == 13) {
								countGloomy++;
								str.append("Gloomy");
							} else if (Integer.parseInt(strSplit[2]) == 14) {
								countTired++;
								str.append("Tired");
							} else if (Integer.parseInt(strSplit[2]) == 15) {
								countSleepy++;
								str.append("Sleepy");
							} else if (Integer.parseInt(strSplit[2]) == 16) {
								countSerene++;
								str.append("Serene");
							}

							if (strSplit.length > 3) {
								str.append(", " + "Note: " + strSplit[3] + "\n");
							} else {
								str.append("\n");
							}
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					str.append("\n");
				}

				inputStream.close();
			}
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}

		str.append("Number of Afraid     : " + countAfraid + "\n");
		str.append("Number of Tense      : " + countTense + "\n");
		str.append("Number of Excited    : " + countExcited + "\n");
		str.append("Number of Delighted  : " + countDelighted + "\n");
		str.append("Number of Frustrated : " + countFrustrated + "\n");
		str.append("Number of Angry      : " + countAngry + "\n");
		str.append("Number of Happy      : " + countHappy + "\n");
		str.append("Number of Glad       : " + countGlad + "\n");
		str.append("Number of Miserable  : " + countMiserable + "\n");
		str.append("Number of Sad        : " + countSad + "\n");
		str.append("Number of Calm       : " + countCalm + "\n");
		str.append("Number of Satisfied  : " + countSatisfied + "\n");
		str.append("Number of Gloomy     : " + countGloomy + "\n");
		str.append("Number of Tired      : " + countTired + "\n");
		str.append("Number of Sleepy     : " + countSleepy + "\n");
		str.append("Number of Serene     : " + countSerene);

		return str.toString();
	}

	private String readDataPANASShort() {
		StringBuilder str = new StringBuilder("");

		int countUpset = 0, countHostile = 0, countAlert = 0, countAshamed = 0, countInspired = 0, countNervous = 0, countDetermined = 0, countAttentive = 0, countAfraid = 0, countActive = 0;

		float avgUpset = 0, avgHostile = 0, avgAlert = 0, avgAshamed = 0, avgInspired = 0, avgNervous = 0, avgDetermined = 0, avgAttentive = 0, avgAfraid = 0, avgActive = 0;

		int numEntry = 0;

		String[] strSplit;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		Date today = new Date();
		Date startDate = new Date(today.getTime() - (long) 30 * 24 * 3600
				* 1000);
		Date tempDate;

		try {
			InputStream inputStream = getApplicationContext().openFileInput(
					PANAS_SHORT_NO_PHOTO_DATA_FILENAME);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";

				while ((receiveString = bufferedReader.readLine()) != null) {
					strSplit = receiveString.split(",");
					try {
						tempDate = sdf.parse(strSplit[0] + "," + strSplit[1]);
						if (tempDate.compareTo(startDate) > 0) {
							str.append(strSplit[0] + "," + strSplit[1] + ": ");
							for (int i = 2; i < 21; i++) {
								str.append(strSplit[i] + "(" + strSplit[i + 1]
										+ ")");
								if (i != 20) {
									str.append(", ");
								}
								i++;
							}
							if (strSplit.length > 22) {
								str.append(", " + "Note: " + strSplit[22]
										+ "\n");
							} else {
								str.append("\n");
							}

							numEntry++;
							countUpset = countUpset
									+ Integer.parseInt(strSplit[3]);
							countHostile = countHostile
									+ Integer.parseInt(strSplit[5]);
							countAlert = countAlert
									+ Integer.parseInt(strSplit[7]);
							countAshamed = countAshamed
									+ Integer.parseInt(strSplit[9]);
							countInspired = countInspired
									+ Integer.parseInt(strSplit[11]);
							countNervous = countNervous
									+ Integer.parseInt(strSplit[13]);
							countDetermined = countDetermined
									+ Integer.parseInt(strSplit[15]);
							countAttentive = countAttentive
									+ Integer.parseInt(strSplit[17]);
							countActive = countActive
									+ Integer.parseInt(strSplit[21]);
							countAfraid = countAfraid
									+ Integer.parseInt(strSplit[19]);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					str.append("\n");
				}

				inputStream.close();
			}
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}

		if (numEntry > 0) {
			avgUpset = (float) countUpset / numEntry;
			avgHostile = (float) countHostile / numEntry;
			avgAlert = (float) countAlert / numEntry;
			avgAshamed = (float) countAshamed / numEntry;
			avgInspired = (float) countInspired / numEntry;
			avgNervous = (float) countNervous / numEntry;
			avgDetermined = (float) countDetermined / numEntry;
			avgAttentive = (float) countAttentive / numEntry;
			avgActive = (float) countActive / numEntry;
			avgAfraid = (float) countAfraid / numEntry;
		}

		DecimalFormat df = new DecimalFormat("#,###,##0.00");

		str.append("Average of Upset      : "
				+ Double.parseDouble(df.format(avgUpset)) + "\n");
		str.append("Average of Hostile    : "
				+ Double.parseDouble(df.format(avgHostile)) + "\n");
		str.append("Average of Alert      : "
				+ Double.parseDouble(df.format(avgAlert)) + "\n");
		str.append("Average of Ashamed    : "
				+ Double.parseDouble(df.format(avgAshamed)) + "\n");
		str.append("Average of Inspired   : "
				+ Double.parseDouble(df.format(avgInspired)) + "\n");
		str.append("Average of Nervous    : "
				+ Double.parseDouble(df.format(avgNervous)) + "\n");
		str.append("Average of Determined : "
				+ Double.parseDouble(df.format(avgDetermined)) + "\n");
		str.append("Average of Attentive  : "
				+ Double.parseDouble(df.format(avgAttentive)) + "\n");
		str.append("Average of Active     : "
				+ Double.parseDouble(df.format(avgActive)) + "\n");
		str.append("Average of Afraid     : "
				+ Double.parseDouble(df.format(avgAfraid)));

		return str.toString();
	}

	private String readDataPANAS() {
		StringBuilder str = new StringBuilder("");

		int countInterested = 0, countDistressed = 0, countExcited = 0, countUpset = 0, countStrong = 0, countGuilty = 0, countScared = 0, countHostile = 0, countEnthusiastic = 0, countProud = 0, countIrritable = 0, countAlert = 0, countAshamed = 0, countInspired = 0, countNervous = 0, countDetermined = 0, countAttentive = 0, countJittery = 0, countActive = 0, countAfraid = 0;

		double avgInterested = 0, avgDistressed = 0, avgExcited = 0, avgUpset = 0, avgStrong = 0, avgGuilty = 0, avgScared = 0, avgHostile = 0, avgEnthusiastic = 0, avgProud = 0, avgIrritable = 0, avgAlert = 0, avgAshamed = 0, avgInspired = 0, avgNervous = 0, avgDetermined = 0, avgAttentive = 0, avgJittery = 0, avgActive = 0, avgAfraid = 0;

		int numEntry = 0;

		String[] strSplit;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		Date today = new Date();
		Date startDate = new Date(today.getTime() - (long) 30 * 24 * 3600
				* 1000);
		Date tempDate;

		try {
			InputStream inputStream = getApplicationContext().openFileInput(
					PANAS_LONG_WITH_PHOTO_DATA_FILENAME);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";

				while ((receiveString = bufferedReader.readLine()) != null) {
					strSplit = receiveString.split(",");
					try {
						tempDate = sdf.parse(strSplit[0] + "," + strSplit[1]);
						if (tempDate.compareTo(startDate) > 0) {
							str.append(strSplit[0] + "," + strSplit[1] + ": ");
							for (int i = 2; i < 41; i++) {
								str.append(strSplit[i] + "(" + strSplit[i + 1]
										+ ")");
								if (i != 40) {
									str.append(", ");
								}
								i++;
							}
							if (strSplit.length > 42) {
								str.append(", " + "Note: " + strSplit[42]
										+ "\n");
							} else {
								str.append("\n");
							}

							numEntry++;
							countInterested = countInterested
									+ Integer.parseInt(strSplit[3]);
							countDistressed = countDistressed
									+ Integer.parseInt(strSplit[5]);
							countExcited = countExcited
									+ Integer.parseInt(strSplit[7]);
							countUpset = countUpset
									+ Integer.parseInt(strSplit[9]);
							countStrong = countStrong
									+ Integer.parseInt(strSplit[11]);
							countGuilty = countGuilty
									+ Integer.parseInt(strSplit[13]);
							countScared = countScared
									+ Integer.parseInt(strSplit[15]);
							countHostile = countHostile
									+ Integer.parseInt(strSplit[17]);
							countEnthusiastic = countEnthusiastic
									+ Integer.parseInt(strSplit[19]);
							countProud = countProud
									+ Integer.parseInt(strSplit[21]);
							countIrritable = countIrritable
									+ Integer.parseInt(strSplit[23]);
							countAlert = countAlert
									+ Integer.parseInt(strSplit[25]);
							countAshamed = countAshamed
									+ Integer.parseInt(strSplit[27]);
							countInspired = countInspired
									+ Integer.parseInt(strSplit[29]);
							countNervous = countNervous
									+ Integer.parseInt(strSplit[31]);
							countDetermined = countDetermined
									+ Integer.parseInt(strSplit[33]);
							countAttentive = countAttentive
									+ Integer.parseInt(strSplit[35]);
							countJittery = countJittery
									+ Integer.parseInt(strSplit[37]);
							countActive = countActive
									+ Integer.parseInt(strSplit[39]);
							countAfraid = countAfraid
									+ Integer.parseInt(strSplit[41]);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					str.append("\n");
				}

				inputStream.close();
			}
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}

		if (numEntry > 0) {
			avgInterested = (double) countInterested / numEntry;
			avgDistressed = (double) countDistressed / numEntry;
			avgExcited = (double) countExcited / numEntry;
			avgUpset = (double) countUpset / numEntry;
			avgStrong = (double) countStrong / numEntry;
			avgGuilty = (double) countGuilty / numEntry;
			avgScared = (double) countScared / numEntry;
			avgHostile = (double) countHostile / numEntry;
			avgEnthusiastic = (double) countEnthusiastic / numEntry;
			avgProud = (double) countProud / numEntry;
			avgIrritable = (double) countIrritable / numEntry;
			avgAlert = (double) countAlert / numEntry;
			avgAshamed = (double) countAshamed / numEntry;
			avgInspired = (double) countInspired / numEntry;
			avgNervous = (double) countNervous / numEntry;
			avgDetermined = (double) countDetermined / numEntry;
			avgAttentive = (double) countAttentive / numEntry;
			avgJittery = (double) countJittery / numEntry;
			avgActive = (double) countActive / numEntry;
			avgAfraid = (double) countAfraid / numEntry;
		}

		DecimalFormat df = new DecimalFormat("#,###,##0.00");

		str.append("Average of Interested   : "
				+ Double.parseDouble(df.format(avgInterested)) + "\n");
		str.append("Average of Distressed   : "
				+ Double.parseDouble(df.format(avgDistressed)) + "\n");
		str.append("Average of Excited      : "
				+ Double.parseDouble(df.format(avgExcited)) + "\n");
		str.append("Average of Upset        : "
				+ Double.parseDouble(df.format(avgUpset)) + "\n");
		str.append("Average of Strong       : "
				+ Double.parseDouble(df.format(avgStrong)) + "\n");
		str.append("Average of Guilty       : "
				+ Double.parseDouble(df.format(avgGuilty)) + "\n");
		str.append("Average of Scared       : "
				+ Double.parseDouble(df.format(avgScared)) + "\n");
		str.append("Average of Hostile      : "
				+ Double.parseDouble(df.format(avgHostile)) + "\n");
		str.append("Average of Enthusiastic : "
				+ Double.parseDouble(df.format(avgEnthusiastic)) + "\n");
		str.append("Average of Proud        : "
				+ Double.parseDouble(df.format(avgProud)) + "\n");
		str.append("Average of Irritable    : "
				+ Double.parseDouble(df.format(avgIrritable)) + "\n");
		str.append("Average of Alert        : "
				+ Double.parseDouble(df.format(avgAlert)) + "\n");
		str.append("Average of Ashamed      : "
				+ Double.parseDouble(df.format(avgAshamed)) + "\n");
		str.append("Average of Inspired     : "
				+ Double.parseDouble(df.format(avgInspired)) + "\n");
		str.append("Average of Nervous      : "
				+ Double.parseDouble(df.format(avgNervous)) + "\n");
		str.append("Average of Determined   : "
				+ Double.parseDouble(df.format(avgDetermined)) + "\n");
		str.append("Average of Attentive    : "
				+ Double.parseDouble(df.format(avgAttentive)) + "\n");
		str.append("Average of Jittery      : "
				+ Double.parseDouble(df.format(avgJittery)) + "\n");
		str.append("Average of Active       : "
				+ Double.parseDouble(df.format(avgActive)) + "\n");
		str.append("Average of Afraid       : "
				+ Double.parseDouble(df.format(avgAfraid)));

		return str.toString();
	}

	private String readDataSPANE(String fileName) {
		StringBuilder str = new StringBuilder("");

		int countPositive = 0, countNegative = 0, countGood = 0, countBad = 0, countPleasant = 0, countUnpleasant = 0, countHappy = 0, countSad = 0, countAfraid = 0, countJoyful = 0, countAngry = 0, countContented = 0;

		double avgPositive = 0, avgNegative = 0, avgGood = 0, avgBad = 0, avgPleasant = 0, avgUnpleasant = 0, avgHappy = 0, avgSad = 0, avgAfraid = 0, avgJoyful = 0, avgAngry = 0, avgContented = 0;

		int numEntry = 0;

		String[] strSplit;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		Date today = new Date();
		Date startDate = new Date(today.getTime() - (long) 30 * 24 * 3600
				* 1000);
		Date tempDate;

		try {
			InputStream inputStream = getApplicationContext().openFileInput(
					fileName);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";

				while ((receiveString = bufferedReader.readLine()) != null) {
					strSplit = receiveString.split(",");
					try {
						tempDate = sdf.parse(strSplit[0] + "," + strSplit[1]);
						if (tempDate.compareTo(startDate) > 0) {
							str.append(strSplit[0] + "," + strSplit[1] + ": ");
							for (int i = 2; i < 25; i++) {
								str.append(strSplit[i] + "(" + strSplit[i + 1]
										+ ")");
								if (i != 24) {
									str.append(", ");
								}
								i++;
							}
							if (strSplit.length > 26) {
								str.append(", " + "Note: " + strSplit[26]
										+ "\n");
							} else {
								str.append("\n");
							}

							numEntry++;
							countPositive = countPositive
									+ Integer.parseInt(strSplit[3]);
							countNegative = countNegative
									+ Integer.parseInt(strSplit[5]);
							countGood = countGood
									+ Integer.parseInt(strSplit[7]);
							countBad = countBad + Integer.parseInt(strSplit[9]);
							countPleasant = countPleasant
									+ Integer.parseInt(strSplit[11]);
							countUnpleasant = countUnpleasant
									+ Integer.parseInt(strSplit[13]);
							countHappy = countHappy
									+ Integer.parseInt(strSplit[15]);
							countSad = countSad
									+ Integer.parseInt(strSplit[17]);
							countAfraid = countAfraid
									+ Integer.parseInt(strSplit[19]);
							countJoyful = countJoyful
									+ Integer.parseInt(strSplit[21]);
							countAngry = countAngry
									+ Integer.parseInt(strSplit[23]);
							countContented = countContented
									+ Integer.parseInt(strSplit[25]);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					str.append("\n");
				}

				inputStream.close();
			}
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}

		if (numEntry > 0) {
			avgPositive = (double) countPositive / numEntry;
			avgNegative = (double) countNegative / numEntry;
			avgGood = (double) countGood / numEntry;
			avgBad = (double) countBad / numEntry;
			avgPleasant = (double) countPleasant / numEntry;
			avgUnpleasant = (double) countUnpleasant / numEntry;
			avgHappy = (double) countHappy / numEntry;
			avgSad = (double) countSad / numEntry;
			avgAfraid = (double) countAfraid / numEntry;
			avgJoyful = (double) countJoyful / numEntry;
			avgAngry = (double) countAngry / numEntry;
			avgContented = (double) countContented / numEntry;
		}

		DecimalFormat df = new DecimalFormat("#,###,##0.00");

		str.append("Average of Positive   : "
				+ Double.parseDouble(df.format(avgPositive)) + "\n");
		str.append("Average of Negative   : "
				+ Double.parseDouble(df.format(avgNegative)) + "\n");
		str.append("Average of Good       : "
				+ Double.parseDouble(df.format(avgGood)) + "\n");
		str.append("Average of Bad        : "
				+ Double.parseDouble(df.format(avgBad)) + "\n");
		str.append("Average of Pleasant   : "
				+ Double.parseDouble(df.format(avgPleasant)) + "\n");
		str.append("Average of Unpleasant : "
				+ Double.parseDouble(df.format(avgUnpleasant)) + "\n");
		str.append("Average of Happy      : "
				+ Double.parseDouble(df.format(avgHappy)) + "\n");
		str.append("Average of Sad        : "
				+ Double.parseDouble(df.format(avgSad)) + "\n");
		str.append("Average of Afraid     : "
				+ Double.parseDouble(df.format(avgAfraid)) + "\n");
		str.append("Average of Joyful     : "
				+ Double.parseDouble(df.format(avgJoyful)) + "\n");
		str.append("Average of Angry      : "
				+ Double.parseDouble(df.format(avgAngry)) + "\n");
		str.append("Average of Contented  : "
				+ Double.parseDouble(df.format(avgContented)));

		return str.toString();
	}

	private String readDataAffectButton() {
		StringBuilder str = new StringBuilder("");

		int countAngry = 0, countHappy = 0, countFrustrated = 0, countContent = 0, countSad = 0, countRelaxed = 0, countUpset = 0, countSurprised = 0;

		String[] strSplit;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		Date today = new Date();
		Date startDate = new Date(today.getTime() - (long) 30 * 24 * 3600
				* 1000);
		Date tempDate;

		try {
			InputStream inputStream = getApplicationContext().openFileInput(
					AFFECTBUTTON_DATA_FILENAME);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";

				while ((receiveString = bufferedReader.readLine()) != null) {
					strSplit = receiveString.split(",");
					try {
						tempDate = sdf.parse(strSplit[0] + "," + strSplit[1]);
						if (tempDate.compareTo(startDate) > 0) {
							if (strSplit.length > 6) {
								str.append(strSplit[0] + "," + strSplit[1]
										+ ": " + strSplit[2] + ", " + "Note: "
										+ strSplit[6] + "\n");
							} else {
								str.append(strSplit[0] + "," + strSplit[1]
										+ ": " + strSplit[2] + "\n");
							}
							if (strSplit[2].compareTo("Angry") == 0) {
								countAngry++;
							} else if (strSplit[2].compareTo("Happy") == 0) {
								countHappy++;
							} else if (strSplit[2].compareTo("Frustrated") == 0) {
								countFrustrated++;
							} else if (strSplit[2].compareTo("Content") == 0) {
								countContent++;
							} else if (strSplit[2].compareTo("Relaxed") == 0) {
								countRelaxed++;
							} else if (strSplit[2].compareTo("Sad") == 0) {
								countSad++;
							} else if (strSplit[2].compareTo("Upset") == 0) {
								countUpset++;
							} else if (strSplit[2].compareTo("Surprised") == 0) {
								countSurprised++;
							}
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}

				}

				inputStream.close();
			}
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}

		str.append("\n");
		str.append("Number of Angry      : " + countAngry + "\n");
		str.append("Number of Happy      : " + countHappy + "\n");
		str.append("Number of Frustrated : " + countFrustrated + "\n");
		str.append("Number of Content    : " + countContent + "\n");
		str.append("Number of Relaxed    : " + countRelaxed + "\n");
		str.append("Number of Sad        : " + countSad + "\n");
		str.append("Number of Upset      : " + countUpset + "\n");
		str.append("Number of Surprised  : " + countSurprised);

		return str.toString();
	}
}
