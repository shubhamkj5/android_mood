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

public class PANASLongWithPhotoStatActivity {

	private static final String PANAS_LONG_WITH_PHOTO_DATA_FILENAME = "PANASLongWithPhotoData.txt";

	int countInterested = 0, countDistressed = 0, countExcited = 0,
			countUpset = 0, countStrong = 0, countGuilty = 0, countScared = 0,
			countHostile = 0, countEnthusiastic = 0, countProud = 0,
			countIrritable = 0, countAlert = 0, countAshamed = 0,
			countInspired = 0, countNervous = 0, countDetermined = 0,
			countAttentive = 0, countJittery = 0, countActive = 0,
			countAfraid = 0;

	double avgInterested = 0, avgDistressed = 0, avgExcited = 0, avgUpset = 0, avgStrong = 0, avgGuilty = 0,
			avgScared = 0, avgHostile = 0, avgEnthusiastic = 0, avgProud = 0, avgIrritable = 0, avgAlert = 0, avgAshamed = 0,
			avgInspired = 0, avgNervous = 0, avgDetermined = 0,
			avgAttentive = 0, avgJittery = 0, avgActive = 0, avgAfraid = 0;

	int numEntry = 0;

	double xMin = 0, xMax = 8, yMin = 0, yMax = 5;

	Context ctx;

	// Execute chart
	public Intent execute(Context context) {
		ctx = context;

		readData();

		String[] titles = new String[] { "Your mood in the last 30 days" };

		DecimalFormat df = new DecimalFormat("#,###,##0.00");

		List<double[]> values = new ArrayList<double[]>();
		values.add(new double[] { Double.parseDouble(df.format(avgInterested)),
				Double.parseDouble(df.format(avgDistressed)),
				Double.parseDouble(df.format(avgExcited)),
				Double.parseDouble(df.format(avgUpset)),
				Double.parseDouble(df.format(avgStrong)),
				Double.parseDouble(df.format(avgGuilty)),
				Double.parseDouble(df.format(avgScared)),
				Double.parseDouble(df.format(avgHostile)),
				Double.parseDouble(df.format(avgEnthusiastic)),
				Double.parseDouble(df.format(avgProud)),
				Double.parseDouble(df.format(avgIrritable)),
				Double.parseDouble(df.format(avgAlert)),
				Double.parseDouble(df.format(avgAshamed)),
				Double.parseDouble(df.format(avgInspired)),
				Double.parseDouble(df.format(avgNervous)),
				Double.parseDouble(df.format(avgDetermined)),
				Double.parseDouble(df.format(avgAttentive)),
				Double.parseDouble(df.format(avgJittery)),
				Double.parseDouble(df.format(avgActive)),
				Double.parseDouble(df.format(avgAfraid))});

		int[] colors = new int[] { Color.rgb(79, 73, 252) };
		XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
		renderer.setOrientation(Orientation.HORIZONTAL);

		int[] margins = {30, 65, 50, 10};
		
		renderer.setMargins(margins);
		
		setChartSettings(renderer, "", "", "", xMin, xMax, yMin, yMax,
				Color.RED, Color.RED);

		renderer.setBarSpacing(0.5);
		renderer.setBarWidth(30);	
		
		renderer.addXTextLabel(1, "Interested");
		renderer.addXTextLabel(2, "Distressed");
		renderer.addXTextLabel(3, "Excited");
		renderer.addXTextLabel(4, "Upset");
		renderer.addXTextLabel(5, "Strong");
		renderer.addXTextLabel(6, "Guilty");
		renderer.addXTextLabel(7, "Scared");
		renderer.addXTextLabel(8, "Hostile");
		renderer.addXTextLabel(9, "Enthusiastic");
		renderer.addXTextLabel(10, "Proud");
		renderer.addXTextLabel(11, "Irritalble");
		renderer.addXTextLabel(12, "Alert");
		renderer.addXTextLabel(13, "Ashamed");
		renderer.addXTextLabel(14, "Inspired");
		renderer.addXTextLabel(15, "Nervous");
		renderer.addXTextLabel(16, "Determined");
		renderer.addXTextLabel(17, "Attentive");
		renderer.addXTextLabel(18, "Jittery");
		renderer.addXTextLabel(19, "Active");
		renderer.addXTextLabel(20, "Afraid");

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
			InputStream inputStream = ctx
					.openFileInput(PANAS_LONG_WITH_PHOTO_DATA_FILENAME);

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
							numEntry++;
							countInterested = countInterested + Integer.parseInt(strSplit[3]);
							countDistressed = countDistressed + Integer.parseInt(strSplit[5]);
							countExcited = countExcited + Integer.parseInt(strSplit[7]);
							countUpset = countUpset + Integer.parseInt(strSplit[9]);
							countStrong = countStrong + Integer.parseInt(strSplit[11]);
							countGuilty = countGuilty + Integer.parseInt(strSplit[13]);
							countScared = countScared + Integer.parseInt(strSplit[15]);
							countHostile = countHostile + Integer.parseInt(strSplit[17]);
							countEnthusiastic = countEnthusiastic + Integer.parseInt(strSplit[19]);
							countProud = countProud + Integer.parseInt(strSplit[21]);
							countIrritable = countIrritable + Integer.parseInt(strSplit[23]);
							countAlert = countAlert + Integer.parseInt(strSplit[25]);
							countAshamed = countAshamed + Integer.parseInt(strSplit[27]);
							countInspired = countInspired + Integer.parseInt(strSplit[29]);
							countNervous = countNervous + Integer.parseInt(strSplit[31]);
							countDetermined = countDetermined + Integer.parseInt(strSplit[33]);
							countAttentive = countAttentive + Integer.parseInt(strSplit[35]);
							countJittery = countJittery + Integer.parseInt(strSplit[37]);
							countActive = countActive + Integer.parseInt(strSplit[39]);
							countAfraid = countAfraid + Integer.parseInt(strSplit[41]);
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

		if (numEntry > 0) {
			avgInterested = (double)countInterested/numEntry;
			avgDistressed = (double)countDistressed/numEntry;
			avgExcited = (double)countExcited/numEntry;
			avgUpset = (double)countUpset/numEntry;
			avgStrong = (double)countStrong/numEntry;
			avgGuilty = (double)countGuilty/numEntry;
			avgScared = (double)countScared/numEntry;
			avgHostile = (double)countHostile/numEntry;
			avgEnthusiastic = (double)countEnthusiastic/numEntry;
			avgProud = (double)countProud/numEntry;
			avgIrritable = (double)countIrritable/numEntry;
			avgAlert = (double)countAlert/numEntry;
			avgAshamed = (double)countAshamed/numEntry;
			avgInspired = (double)countInspired/numEntry;
			avgNervous = (double)countNervous/numEntry;
			avgDetermined = (double)countDetermined/numEntry;
			avgAttentive = (double)countAttentive/numEntry;
			avgJittery = (double)countJittery/numEntry;
			avgActive = (double)countActive/numEntry;
			avgAfraid = (double)countAfraid/numEntry;
		}
	}
}
