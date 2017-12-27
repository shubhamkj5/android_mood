package com.example.affectassessment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class SPANENoPhotoStatActivity {
	
	private static final String SPANE_NO_PHOTO_DATA_FILENAME = "SPANENoPhotoData.txt";
	
	int countPositive = 0, countNegative = 0, countGood = 0, countBad = 0,
			countPleasant = 0, countUnpleasant = 0, countHappy = 0,
			countSad = 0, countAfraid = 0, countJoyful = 0, countAngry = 0,
			countContented = 0;
	
	double avgPositive = 0, avgNegative = 0, avgGood = 0, avgBad = 0,
			avgPleasant = 0, avgUnpleasant = 0, avgHappy = 0,
			avgSad = 0, avgAfraid = 0, avgJoyful = 0, avgAngry = 0,
			avgContented = 0;
	
	int numEntry = 0;
	
	double xMin = 0, xMax = 8, yMin = 0, yMax = 5;
	
	Context ctx;

	// Execute chart
	public Intent execute(Context context) {
		ctx = context;
		
		readData();
		
		String[] titles = new String[] {"Your mood in the last 30 days"};
		
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		
		List<double[]> values = new ArrayList<double[]>();
		values.add(new double[] { Double.parseDouble(df.format(avgPositive)), Double.parseDouble(df.format(avgNegative)), Double.parseDouble(df.format(avgGood)), Double.parseDouble(df.format(avgBad)),
				Double.parseDouble(df.format(avgPleasant)), Double.parseDouble(df.format(avgUnpleasant)), Double.parseDouble(df.format(avgHappy)),
				Double.parseDouble(df.format(avgSad)), Double.parseDouble(df.format(avgAfraid)), Double.parseDouble(df.format(avgJoyful)), Double.parseDouble(df.format(avgAngry)),
				Double.parseDouble(df.format(avgContented))});
		
		int[] colors = new int[] { Color.rgb(255, 90, 201) };
		XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
		renderer.setOrientation(Orientation.HORIZONTAL);
		
		int[] margins = {30, 65, 50, 10};
		
		renderer.setMargins(margins);
		
		setChartSettings(renderer, "",
				"", "", xMin, xMax, yMin, yMax, Color.RED, Color.RED);
		
		renderer.setBarSpacing(0.5);
		renderer.setBarWidth(30);
		
		renderer.addXTextLabel(1, "Positive");
		renderer.addXTextLabel(2, "Negative");
		renderer.addXTextLabel(3, "Good");
		renderer.addXTextLabel(4, "Bad");
		renderer.addXTextLabel(5, "Pleasant");
		renderer.addXTextLabel(6, "Unpleasant");
		renderer.addXTextLabel(7, "Happy");
		renderer.addXTextLabel(8, "Sad");
		renderer.addXTextLabel(9, "Afraid");
		renderer.addXTextLabel(10, "Joyful");
		renderer.addXTextLabel(11, "Angry");
		renderer.addXTextLabel(12, "Contented");
		
		renderer.setXLabels(0);
		renderer.setYLabels(10);
		
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer seriesRenderer = renderer
					.getSeriesRendererAt(i);
			seriesRenderer.setDisplayChartValues(false);
		}
		return ChartFactory.getBarChartIntent(context,
				buildBarDataset(titles, values), renderer, Type.DEFAULT);
	}

	/**
	 * Builds a bar multiple series renderer to use the provided colors.
	 * 
	 * @param colors
	 *            the series renderers colors
	 * @return the bar multiple series renderer
	 */
	protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setLabelsTextSize(30);
		//renderer.setXLabelsPadding(50);
		renderer.setYLabelsPadding(35);
		renderer.setLegendTextSize(30);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	/**
	 * Sets a few of the series renderer settings.
	 * 
	 * @param renderer
	 *            the renderer to set the properties to
	 * @param title
	 *            the chart title
	 * @param xTitle
	 *            the title for the X axis
	 * @param yTitle
	 *            the title for the Y axis
	 * @param xMin
	 *            the minimum value on the X axis
	 * @param xMax
	 *            the maximum value on the X axis
	 * @param yMin
	 *            the minimum value on the Y axis
	 * @param yMax
	 *            the maximum value on the Y axis
	 * @param axesColor
	 *            the axes color
	 * @param labelsColor
	 *            the labels color
	 */
	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	/**
	 * Builds a bar multiple series dataset using the provided values.
	 * 
	 * @param titles
	 *            the series titles
	 * @param values
	 *            the values
	 * @return the XY multiple bar dataset
	 */
	protected XYMultipleSeriesDataset buildBarDataset(String[] titles,
			List<double[]> values) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}
		return dataset;
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
			InputStream inputStream = ctx.openFileInput(SPANE_NO_PHOTO_DATA_FILENAME);

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
			avgPositive = (double)countPositive/numEntry;
			avgNegative = (double)countNegative/numEntry;
			avgGood = (double)countGood/numEntry;
			avgBad = (double)countBad/numEntry;
			avgPleasant = (double)countPleasant/numEntry;
			avgUnpleasant = (double)countUnpleasant/numEntry;
			avgHappy = (double)countHappy/numEntry;
			avgSad = (double)countSad/numEntry;
			avgAfraid = (double)countAfraid/numEntry;
			avgJoyful = (double)countJoyful/numEntry;
			avgAngry = (double)countAngry/numEntry;
			avgContented = (double)countContented/numEntry;
		}
	}
}
