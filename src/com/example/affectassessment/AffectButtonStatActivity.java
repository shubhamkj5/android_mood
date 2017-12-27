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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import android.app.ActionBar;
import android.content.Intent;
import android.view.MenuItem;

public class AffectButtonStatActivity extends Activity {

	private static final String AFFECTBUTTON_DATA_FILENAME = "AffectButtonData.txt";

	/** Colors to be used for the pie slices. */
	private static int[] COLORS = new int[] { Color.GREEN,
			Color.BLUE, Color.RED, Color.CYAN, Color.YELLOW,
			Color.GRAY, Color.MAGENTA, Color.rgb(139, 69, 19) };

	/** The main series that will include all the data. */
	private CategorySeries mSeries = new CategorySeries("");

	/** The main renderer for the main dataset. */
	private DefaultRenderer mRenderer = new DefaultRenderer();

	/** The chart view that displays the data. */
	private GraphicalView mChartView;

	int countAngry = 0, countHappy = 0, countFrustrated = 0, countContent = 0,
			countSad = 0, countRelaxed = 0, countUpset = 0, countSurprised = 0;

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
		
		if (countAngry>0){
			mSeries.add("Angry", countAngry);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[0]);
			mRenderer.addSeriesRenderer(renderer);
		}

		if (countHappy>0){
			mSeries.add("Happy", countHappy);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[1]);
			mRenderer.addSeriesRenderer(renderer);
		}

		if (countFrustrated>0){
			mSeries.add("Frustrated", countFrustrated);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[2]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countContent>0){
			mSeries.add("Content", countContent);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[3]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countSad > 0){
			mSeries.add("Sad", countSad);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[4]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countRelaxed>0){
			mSeries.add("Relaxed", countRelaxed);
			renderer = new SimpleSeriesRenderer();
			renderer.setColor(COLORS[5]);
			mRenderer.addSeriesRenderer(renderer);
		}
		
		if (countUpset>0){
		mSeries.add("Upset", countUpset);
		renderer = new SimpleSeriesRenderer();
		renderer.setColor(COLORS[6]);
		mRenderer.addSeriesRenderer(renderer);
		}
		
		if(countSurprised>0){
		mSeries.add("Surprised", countSurprised);
		renderer = new SimpleSeriesRenderer();
		renderer.setColor(COLORS[7]);
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
        
		Toast.makeText(AffectButtonStatActivity.this,
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
			InputStream inputStream = getApplicationContext().openFileInput(AFFECTBUTTON_DATA_FILENAME);

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
							if (strSplit[2].compareTo("Angry") == 0){
								countAngry++;
							} else if (strSplit[2].compareTo("Happy") == 0){
								countHappy++;
							} else if (strSplit[2].compareTo("Frustrated") == 0){
								countFrustrated++;
							} else if (strSplit[2].compareTo("Content") == 0){
								countContent++;
							} else if (strSplit[2].compareTo("Relaxed") == 0){
								countRelaxed++;
							} else if (strSplit[2].compareTo("Sad") == 0){
								countSad++;
							} else if (strSplit[2].compareTo("Upset") == 0){
								countUpset++;
							} else if (strSplit[2].compareTo("Surprised") == 0){
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

	}
}
