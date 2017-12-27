package com.example.affectassessment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
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

public class PANASShortNoPhotoStatActivity extends Activity{

	private static final String PANAS_SHORT_NO_PHOTO_DATA_FILENAME = "PANASShortNoPhotoData.txt";
	
	/** Colors to be used for the pie slices. */
	private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,
			Color.RED, Color.CYAN, Color.YELLOW, Color.GRAY, Color.MAGENTA,
			Color.rgb(139, 69, 19), Color.rgb(255, 165, 0),
			Color.rgb(160, 132, 240)};

	/** The main series that will include all the data. */
	private CategorySeries mSeries = new CategorySeries("");

	/** The main renderer for the main dataset. */
	private DefaultRenderer mRenderer = new DefaultRenderer();

	/** The chart view that displays the data. */
	private GraphicalView mChartView;

	int countUpset = 0, countHostile = 0, countAlert = 0, countAshamed = 0,
			countInspired = 0, countNervous = 0, countDetermined = 0,
			countAttentive = 0, countAfraid = 0, countActive = 0;
	
	float avgUpset = 0, avgHostile = 0, avgAlert = 0, avgAshamed = 0,
			avgInspired = 0, avgNervous = 0, avgDetermined = 0,
			avgAttentive = 0, avgAfraid = 0, avgActive = 0;
	
	int numEntry = 0;

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
		
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		
		if (avgUpset>0.01){
			mSeries.add("Upset", Double.parseDouble(df.format(avgUpset)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[0]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgHostile>0.01){
			mSeries.add("Hostile", Double.parseDouble(df.format(avgHostile)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[1]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgAlert>0.01){
			mSeries.add("Alert", Double.parseDouble(df.format(avgAlert)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[2]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgAshamed>0.01){
			mSeries.add("Ashamed", Double.parseDouble(df.format(avgAshamed)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[3]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgInspired>0.01){
			mSeries.add("Inspired", Double.parseDouble(df.format(avgInspired)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[4]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgNervous>0.01){
			mSeries.add("Nervous", Double.parseDouble(df.format(avgNervous)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[5]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgDetermined>0.01){
			mSeries.add("Determined", Double.parseDouble(df.format(avgDetermined)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[6]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgAttentive>0.01){
			mSeries.add("Attentive", Double.parseDouble(df.format(avgAttentive)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[7]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgActive>0.01){
			mSeries.add("Active", Double.parseDouble(df.format(avgActive)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[9]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgAfraid>0.01){
			mSeries.add("Afraid", Double.parseDouble(df.format(avgAfraid)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[8]);
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
        
		Toast.makeText(PANASShortNoPhotoStatActivity.this,
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
			InputStream inputStream = getApplicationContext().openFileInput(PANAS_SHORT_NO_PHOTO_DATA_FILENAME);

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
							numEntry++;
							countUpset = countUpset + Integer.parseInt(strSplit[3]);
							countHostile = countHostile + Integer.parseInt(strSplit[5]);
							countAlert = countAlert + Integer.parseInt(strSplit[7]);
							countAshamed = countAshamed + Integer.parseInt(strSplit[9]);
							countInspired = countInspired + Integer.parseInt(strSplit[11]);
							countNervous = countNervous + Integer.parseInt(strSplit[13]);
							countDetermined = countDetermined + Integer.parseInt(strSplit[15]);
							countAttentive = countAttentive + Integer.parseInt(strSplit[17]);
							countActive = countActive + Integer.parseInt(strSplit[21]);
							countAfraid = countAfraid + Integer.parseInt(strSplit[19]);
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
		
		if (numEntry>0){
			avgUpset = (float)countUpset/numEntry;
			avgHostile = (float)countHostile/numEntry;
			avgAlert = (float)countAlert/numEntry;
			avgAshamed = (float)countAshamed/numEntry;
			avgInspired = (float)countInspired/numEntry;
			avgNervous = (float)countNervous/numEntry;
			avgDetermined = (float)countDetermined/numEntry;
			avgAttentive = (float)countAttentive/numEntry;
			avgActive = (float)countActive/numEntry;
			avgAfraid = (float)countAfraid/numEntry;
		}
	}
}
