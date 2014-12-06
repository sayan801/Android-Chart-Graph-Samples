package com.example.technicise.chartprototypeandroidplot;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import com.androidplot.Plot;
import com.androidplot.ui.AnchorPosition;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.YLayoutStyle;
import com.androidplot.xy.*;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Date;

/**
 * A straightforward example of using AndroidPlot to plot some data.
 */
public class SimpleXYPlotActivity extends Activity
{

    private XYPlot mySimpleXYPlot;

    @Override
    public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.simple_xy_plot_example);
            // initialize our XYPlot reference:
            mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

            //customixing the plot ui
            mySimpleXYPlot.setBorderStyle(Plot.BorderStyle.NONE, null, null);
            mySimpleXYPlot.setPlotMargins(0, 0, 0, 0);
            mySimpleXYPlot.setPlotPadding(0, 0, 0, 0);
            mySimpleXYPlot.setGridPadding(20, 20, 20, 20);

            mySimpleXYPlot.setBackgroundColor(Color.GREEN);
            mySimpleXYPlot.getGraphWidget().setSize(new SizeMetrics(
                    0, SizeLayoutType.FILL,
                    0, SizeLayoutType.FILL));

            //background of the outer layer
            mySimpleXYPlot.getGraphWidget().getBackgroundPaint().setColor(Color.GREEN);

            //background of the chart
            mySimpleXYPlot.getGraphWidget().getGridBackgroundPaint().setColor(Color.GREEN);

            //domain and origin values
            mySimpleXYPlot.getGraphWidget().getDomainLabelPaint().setColor(Color.WHITE);
            mySimpleXYPlot.getGraphWidget().getRangeLabelPaint().setColor(Color.WHITE);


            mySimpleXYPlot.getGraphWidget().getRangeOriginLabelPaint().setColor(Color.YELLOW);
            mySimpleXYPlot.getGraphWidget().getDomainOriginLabelPaint().setColor(Color.YELLOW);

            //the border lines of both range and origin
            mySimpleXYPlot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.GREEN);
            mySimpleXYPlot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.WHITE);

            //rulers of both axis
            mySimpleXYPlot.getGraphWidget().getRangeGridLinePaint().setColor(Color.GREEN);
            mySimpleXYPlot.getGraphWidget().getDomainGridLinePaint().setColor(Color.WHITE);


            Number[] days =   { 1  , 2   , 3   , 4   , 5   , 6   , 7 };
            Number[] values = { 30, 20, 50, 60, 70, 40, 90 };
            // Create a couple arrays of y-values to plot:

            //Number[] values = { 21.5, 21, 21, 21, 15 };
            final String[] mMonths = new String[] {
                    "1/22","1/23", "1/24","1/25", "1/26"
            };
//
            // Turn the above arrays into XYSeries':
//            XYSeries series1 = new SimpleXYSeries(
//                    Arrays.asList(values),          // SimpleXYSeries takes a List so turn our array into a List
//                    SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
//                    "Series1");                             // Set the display title of the series

            // Turn the above arrays into XYSeries':
            XYSeries series1 = new SimpleXYSeries(
                    Arrays.asList(days),
                    Arrays.asList(values),
                    "");
            // Create a formatter to use for drawing a series using LineAndPointRenderer
            // and configure it from xml:
            LineAndPointFormatter series1Format = new LineAndPointFormatter();
            series1Format.setPointLabelFormatter(new PointLabelFormatter());
            series1Format.configure(getApplicationContext(),
                    R.xml.line_point_formatter_with_plf1);
            // add a new series' to the xyplot:
            mySimpleXYPlot.addSeries(series1, series1Format);



        }
}