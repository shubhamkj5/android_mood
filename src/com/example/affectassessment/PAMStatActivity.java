package com.example.affectassessment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import android.app.ActionBar;
import android.content.Intent;
import android.view.MenuItem;

public class PAMStatActivity extends Activity{

	private static final String PAM_DATA_FILENAME = "PAMData.txt";
	
	/** Colors to be used for the pie slices. */
	private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,
			Color.RED, Color.CYAN, Color.YELLOW, Color.GRAY, Color.MAGENTA,
			Color.rgb(139, 69, 19), Color.rgb(255, 165, 0),
			Color.rgb(160, 132, 240), Color.rgb(0, 250, 154),
			Color.rgb(255, 218, 185), Color.rgb(49, 79, 79), Color.rgb(106, 90, 205), Color.rgb(0, 100, 0), Color.rgb(205, 92, 92) };

	/** The main series that will include all the data. */
	private CategorySeries mSeries = new CategorySeries("");

	/** The main renderer for the main dataset. */
	private DefaultRenderer mRenderer = new DefaultRenderer();

	/** The chart view that displays the data. */
	private GraphicalView mChartView;

	int countAfraid = 0, countTense = 0, countExcited = 0, countDelighted = 0, countFrustrated = 0,
			countAngry = 0, countHappy = 0, countGlad = 0, countMiserable = 0, 
			countSad = 0,  countCalm = 0, countSatisfied = 0, countGloomy = 0, countTired = 0,countSleepy = 0, 
			countSerene = 0;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.xy_chart);
		
		readData();
		
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setStartAngle(180);
		mRenderer.setDisplayValues(true);

		SimpleSeriesRenderer renderer;
		
		if (countAfraid>0){
			mSeries.add("Afraid", countAfraid);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[0]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countTense>0){
			mSeries.add("Tense", countTense);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[1]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countExcited>0){
			mSeries.add("Excited", countExcited);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[2]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countDelighted>0){
			mSeries.add("Delighted", countDelighted);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[3]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countFrustrated>0){
			mSeries.add("Frustrated", countFrustrated);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[4]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countAngry>0){
			mSeries.add("Angry", countAngry);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[5]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countHappy>0){
			mSeries.add("Happy", countHappy);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[6]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countGlad>0){
			mSeries.add("Glad", countGlad);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[7]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countMiserable>0){
			mSeries.add("Miserable", countMiserable);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[8]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countSad>0){
			mSeries.add("Sad", countSad);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[9]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countCalm>0){
			mSeries.add("Calm", countCalm);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[10]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countSatisfied>0){
			mSeries.add("Satisfied", countSatisfied);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[11]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countGloomy>0){
			mSeries.add("Gloomy", countGloomy);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[12]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countTired>0){
			mSeries.add("Tired", countTired);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[13]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countSleepy>0){
			mSeries.add("Sleepy", countSleepy);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[14]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countSerene>0){
			mSeries.add("Serene", countSerene);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[15]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		mRenderer.setLabelsTextSize(30);
		mRenderer.setLabelsColor(Color.BLACK);
		
		mRenderer.setLegendTextSize(30);
		mRenderer.setChartTitle("Your mood in the last 30 days");
		mRenderer.setChartTitleTextSize(40);
		
		if (mChartView == null) {
			LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
			mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
			mRenderer.setClickEnabled(true);
			mChartView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					SeriesSelection seriesSelection = mChartView
							.getCurrentSeriesAndPoint();
					if (seriesSelection == null) {
						//Toast.makeText(AffectButtonStatActivity.this,"No chart element selected", Toast.LENGTH_SHORT).show();
					} else {
						for (int i = 0; i < mSeries.getItemCount(); i++) {
							mRenderer.getSeriesRendererAt(i).setHighlighted(
									i == seriesSelection.getPointIndex());
						}
						mChartView.repaint();
						//Toast.makeText(AffectButtonStatActivity.this,"Chart data point index "+ seriesSelection.getPointIndex()+ " selected" + " point value="+ seriesSelection.getValue(),Toast.LENGTH_SHORT).show();
					}
				}
			});
			layout.addView(mChartView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		} else {
			mChartView.repaint();
		}
		
		ActionBar ab = getActionBar(); 
        ab.setDisplayHomeAsUpEnabled(true);
        
		Toast.makeText(PAMStatActivity.this,
				"Tip: You can use your fingers to move the chart or zoom in and zoom out it", Toast.LENGTH_LONG)
				.show();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	        	Intent myIntent;
	        	myIntent = new Intent(getApplicationContext(),
						StatisticsActivity.class);
	        	myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(myIntent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item); 
	    }
	}
	
	@SuppressLint("SimpleDateFormat")
	private void readData() {
		String[] strSplit;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		Date today = new Date();
		Date startDate = new Date(today.getTime() - (long) 30 * 24 * 3600
				* 1000);
		Date tempDate;
		
		try {
			InputStream inputStream = getApplicationContext().openFileInput(PAM_DATA_FILENAME);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";

				while ((receiveString = bufferedReader.readLine()) != null) {
					strSplit = receiveString.split(",");
					try {
						tempDate = sdf.parse(strSplit[0]+"," +strSplit[1]);
						if (tempDate.compareTo(startDate) > 0){
							if (Integer.parseInt(strSplit[2]) == 1){
								countAfraid++;
							} else if (Integer.parseInt(strSplit[2]) == 2){
								countTense++;
							} else if (Integer.parseInt(strSplit[2]) == 3){
								countExcited++;
							} else if (Integer.parseInt(strSplit[2]) == 4){
								countDelighted++;
							} else if (Integer.parseInt(strSplit[2]) == 5){
								countFrustrated++;
							} else if (Integer.parseInt(strSplit[2]) == 6){
								countAngry++;
							} else if (Integer.parseInt(strSplit[2]) == 7){
								countHappy++;
							} else if (Integer.parseInt(strSplit[2]) == 8){
								countGlad++;
							} else if (Integer.parseInt(strSplit[2]) == 9){
								countMiserable++;
							} else if (Integer.parseInt(strSplit[2]) == 10){
								countSad++;
							} else if (Integer.parseInt(strSplit[2]) == 11){
								countCalm++;
							} else if (Integer.parseInt(strSplit[2]) == 12){
								countSatisfied++;
							} else if (Integer.parseInt(strSplit[2]) == 13){
								countGloomy++;
							} else if (Integer.parseInt(strSplit[2]) == 14){
								countTired++;
							} else if (Integer.parseInt(strSplit[2]) == 15){
								countSleepy++;
							} else if (Integer.parseInt(strSplit[2]) == 16){
								countSerene++;
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
	}
}
