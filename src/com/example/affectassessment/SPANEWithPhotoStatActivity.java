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
import android.view.MenuItem;

import android.app.ActionBar;
import android.content.Intent;
import android.view.MenuItem;

public class SPANEWithPhotoStatActivity extends Activity {

	private static final String SPANE_WITH_PHOTO_DATA_FILENAME = "SPANEWithPhotoData.txt";

	/** Colors to be used for the pie slices. */
	private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,
			Color.RED, Color.CYAN, Color.YELLOW, Color.GRAY, Color.MAGENTA,
			Color.rgb(139, 69, 19), Color.rgb(255, 165, 0),
			Color.rgb(160, 132, 240), Color.rgb(0, 250, 154),
			Color.rgb(255, 218, 185) };

	/** The main series that will include all the data. */
	private CategorySeries mSeries = new CategorySeries("");

	/** The main renderer for the main dataset. */
	private DefaultRenderer mRenderer = new DefaultRenderer();

	/** The chart view that displays the data. */
	private GraphicalView mChartView;

	int countPositive = 0, countNegative = 0, countGood = 0, countBad = 0,
			countPleasant = 0, countUnpleasant = 0, countHappy = 0,
			countSad = 0, countAfraid = 0, countJoyful = 0, countAngry = 0,
			countContented = 0;
	
	float avgPositive = 0, avgNegative = 0, avgGood = 0, avgBad = 0,
			avgPleasant = 0, avgUnpleasant = 0, avgHappy = 0,
			avgSad = 0, avgAfraid = 0, avgJoyful = 0, avgAngry = 0,
			avgContented = 0;
	
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
		
		if (avgPositive>0.01){
			mSeries.add("Positive", Double.parseDouble(df.format(avgPositive)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[0]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgNegative>0.01){
			mSeries.add("Negative", Double.parseDouble(df.format(avgNegative)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[1]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgGood>0.01){
			mSeries.add("Good", Double.parseDouble(df.format(avgGood)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[2]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgBad>0.01){
			mSeries.add("Bad", Double.parseDouble(df.format(avgBad)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[3]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgPleasant>0.01){
			mSeries.add("Pleasant", Double.parseDouble(df.format(avgPleasant)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[4]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgUnpleasant>0.01){
			mSeries.add("Unpleasant", Double.parseDouble(df.format(avgUnpleasant)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[5]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgHappy>0.01){
			mSeries.add("Happy", Double.parseDouble(df.format(avgHappy)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[6]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgSad>0.01){
			mSeries.add("Sad", Double.parseDouble(df.format(avgSad)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[7]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgAfraid>0.01){
			mSeries.add("Afraid", Double.parseDouble(df.format(avgAfraid)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[8]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgJoyful>0.01){
			mSeries.add("Joyful", Double.parseDouble(df.format(avgJoyful)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[9]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgAngry>0.01){
			mSeries.add("Angry", Double.parseDouble(df.format(avgAngry)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[10]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (avgContented>0.01){
			mSeries.add("Contented", Double.parseDouble(df.format(avgContented)));
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[11]);
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
        
		Toast.makeText(SPANEWithPhotoStatActivity.this,
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
			InputStream inputStream = getApplicationContext().openFileInput(SPANE_WITH_PHOTO_DATA_FILENAME);

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
							countPositive = countPositive + Integer.parseInt(strSplit[3]);
							countNegative = countNegative + Integer.parseInt(strSplit[5]);
							countGood = countGood + Integer.parseInt(strSplit[7]);
							countBad = countBad + Integer.parseInt(strSplit[9]);
							countPleasant = countPleasant + Integer.parseInt(strSplit[11]);
							countUnpleasant = countUnpleasant + Integer.parseInt(strSplit[13]);
							countHappy = countHappy + Integer.parseInt(strSplit[15]);
							countSad = countSad + Integer.parseInt(strSplit[17]);
							countAfraid = countAfraid + Integer.parseInt(strSplit[19]);
							countJoyful = countJoyful + Integer.parseInt(strSplit[21]);
							countAngry = countAngry + Integer.parseInt(strSplit[23]);
							countContented = countContented + Integer.parseInt(strSplit[25]);
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
			avgPositive = (float)countPositive/numEntry;
			avgNegative = (float)countNegative/numEntry;
			avgGood = (float)countGood/numEntry;
			avgBad = (float)countBad/numEntry;
			avgPleasant = (float)countPleasant/numEntry;
			avgUnpleasant = (float)countUnpleasant/numEntry;
			avgHappy = (float)countHappy/numEntry;
			avgSad = (float)countSad/numEntry;
			avgAfraid = (float)countAfraid/numEntry;
			avgJoyful = (float)countJoyful/numEntry;
			avgAngry = (float)countAngry/numEntry;
			avgContented = (float)countContented/numEntry;
		}
	}
}
