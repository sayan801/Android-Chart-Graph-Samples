package com.example.technicise.chartprototypeandroidplot;

import android.graphics.Color;
import android.graphics.Paint;
import com.androidplot.ui.SeriesRenderer;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.XYPlot;

/**
 * A LineAndPointFormatter with the addition of paint to be used to "stroke" vertices.
 */
class MyLineAndPointFormatter extends LineAndPointFormatter
{
    private Paint strokePaint;

    /**
     * Some quick and dirty hard-coded params
     */
    public MyLineAndPointFormatter()
    {
        super(Color.GRAY, Color.GRAY, null, null);
        strokePaint = new Paint();
        strokePaint.setColor(Color.WHITE);
        strokePaint.setStrokeWidth(15);
        strokePaint.setStyle(Paint.Style.FILL);
       // strokePaint.setAntiAlias(true);
    }

    public Paint getStrokePaint()
    {
        return strokePaint;
    }

    @Override
    public Class<? extends SeriesRenderer> getRendererClass()
    {
        return MyLineAndPointRenderer.class;
    }

    @Override
    public SeriesRenderer getRendererInstance(XYPlot plot)
    {
        return new MyLineAndPointRenderer(plot);
    }
}
