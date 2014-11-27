package com.example.technicise.customlinechart;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class MainActivity extends Activity {

    private GraphicalView mChart;

    CustomSeekBar SeekBar;

    private float totalSpan = 99;

    private float blueSpan = 33;
    private float greenSpan = 33;
    private float yellowSpan = 33;


    private ArrayList<ProgressItem> progressItemList;
    private ProgressItem mProgressItem;
    String selectedSeries;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openChart();

        SeekBar = ((CustomSeekBar) findViewById(R.id.seekBar0));

        initDataToSeekbar();
    }

    private void initDataToSeekbar() {

        progressItemList = new ArrayList<ProgressItem>();

        // blue span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (blueSpan / totalSpan) * 100;
        mProgressItem.color = R.color.blue;
        progressItemList.add(mProgressItem);

        // green span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = ((greenSpan / totalSpan) * 100);
        mProgressItem.color = R.color.green;
        progressItemList.add(mProgressItem);

        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (yellowSpan / totalSpan) * 100;
        mProgressItem.color = R.color.yellow;
        progressItemList.add(mProgressItem);

        SeekBar.initData(progressItemList);
        SeekBar.invalidate();
    }

    private void openChart(){

        int count = 5;
        Date[] dt = new Date[5];
        for(int i=0;i<count;i++){
            GregorianCalendar gc = new GregorianCalendar(2014, 10, i+1);
            dt[i] = gc.getTime();
        }

        float [] visits = {(float) 20.50, (float) 25.75,(float) 25.5,(float) 15.5,(float) 35.5};

        // Creating TimeSeries for Visits
        TimeSeries visitsSeries = new TimeSeries("BMR Rate");

        // Adding data to Visits Series
        for(int i=0;i<dt.length;i++){
            visitsSeries.add(dt[i], visits[i]);



        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        // Adding Visits Series to the dataset
        dataset.addSeries(visitsSeries);

        // Creating XYSeriesRenderer to customize visitsSeries
        XYSeriesRenderer visitsRenderer = new XYSeriesRenderer();
        visitsRenderer.setColor(Color.WHITE);
        visitsRenderer.setPointStyle(PointStyle.SQUARE);

        visitsRenderer.setFillPoints(true);
        visitsRenderer.setLineWidth(3);
        visitsRenderer.setDisplayChartValues(true);
        visitsRenderer.setChartValuesTextSize(20);


        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

        multiRenderer.setChartTitle("Body Mass Index (BMI)");
        multiRenderer.setChartTitleTextSize(20);
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setPointSize(4);
        multiRenderer.setLabelsColor(Color.WHITE);
        multiRenderer.setGridColor(Color.WHITE);
        multiRenderer.setMarginsColor(Color.parseColor("#54D66A"));
        multiRenderer.setYLabelsColor(0,Color.WHITE);
        multiRenderer.setXLabelsColor(Color.WHITE);
        multiRenderer.setAxesColor(Color.TRANSPARENT);
        multiRenderer.setLabelsTextSize(15);

        multiRenderer.setShowGrid(true);
        multiRenderer.setShowAxes(true);
        multiRenderer.setShowGridX(false);
        multiRenderer.setShowGridY(true);
        multiRenderer.setShowLabels(true);

        multiRenderer.setXTitle("Days");
        multiRenderer.setYTitle("Count");
        multiRenderer.setZoomButtonsVisible(true);



        // Adding visitsRenderer and viewsRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(visitsRenderer);
        //	multiRenderer.addSeriesRenderer(viewsRenderer);

        // Getting a reference to LinearLayout of the MainActivity Layout
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);

        // Creating a Time Chart
        mChart = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, multiRenderer, "dd-MM-yyyy");

        multiRenderer.setClickEnabled(true);
        multiRenderer.setSelectableBuffer(15);

        // Setting a click event listener for the graph
        mChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Format formatter = new SimpleDateFormat("dd-MM-yyyy");

                SeriesSelection seriesSelection = mChart.getCurrentSeriesAndPoint();

                if (seriesSelection != null) {
                    int seriesIndex = seriesSelection.getSeriesIndex();

                    selectedSeries="BMR Rate";
                    if(seriesIndex==0)
                        selectedSeries = "BMR Rate";
                    else
                        selectedSeries = "BMR Rate";

                    // Getting the clicked Date ( x value )
                    long clickedDateSeconds = (long) seriesSelection.getXValue();
                    Date clickedDate = new Date(clickedDateSeconds);
                    String strDate = formatter.format(clickedDate);

                    // Getting the y value
                 //   int amount = (int) seriesSelection.getValue();
                    float amount = (float) seriesSelection.getValue();

                    // Displaying Toast Message
                    Toast.makeText(
                            getBaseContext(),
                            selectedSeries + " on "  + strDate + " : " + amount ,
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        // Adding the Line Chart to the LinearLayout
        chartContainer.addView(mChart);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}