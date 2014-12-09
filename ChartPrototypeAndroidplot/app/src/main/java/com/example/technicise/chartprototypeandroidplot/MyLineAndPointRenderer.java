package com.example.technicise.chartprototypeandroidplot;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.util.Pair;
import com.androidplot.util.ValPixConverter;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.LineAndPointRenderer;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.PointLabeler;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import java.util.ArrayList;

/**
 * A LineAndPointRenderer that can stroke vertices.
 */
class MyLineAndPointRenderer extends LineAndPointRenderer<MyLineAndPointFormatter>
{

    public MyLineAndPointRenderer(XYPlot plot)
    {
        super(plot);
    }

    /**
     * Overridden draw method to get the "vertex stroke" effect.  99% of this is copy/pasted from
     * the super class' implementation.
     * @param canvas
     * @param plotArea
     * @param series
     * @param formatter
     */
    @Override
    protected void drawSeries(Canvas canvas, RectF plotArea, XYSeries series, LineAndPointFormatter formatter)
    {

        PointF thisPoint;
        PointF lastPoint = null;
        PointF firstPoint = null;
        Paint linePaint = formatter.getLinePaint();

        Path path = null;
        ArrayList<Pair<PointF, Integer>> points = new ArrayList<Pair<PointF, Integer>>(series.size());
        for (int i = 0; i < series.size(); i++)
        {
            Number y = series.getY(i);
            Number x = series.getX(i);
            Log.d("x",x+"");
            Log.d("y",y.toString());
            if (y != null && x != null)
                {
                    thisPoint = ValPixConverter.valToPix(
                            x,
                            y,
                            plotArea,
                            getPlot().getCalculatedMinX(),
                            getPlot().getCalculatedMaxX(),
                            getPlot().getCalculatedMinY(),
                            getPlot().getCalculatedMaxY());
                    points.add(new Pair<PointF, Integer>(thisPoint, i));
                }
            else
                {
                    thisPoint = null;
                }

            if(linePaint != null && thisPoint != null)
            {

                // record the first point of the new Path
                if(firstPoint == null) {
                    path = new Path();
                    firstPoint = thisPoint;
                    // create our first point at the bottom/x position so filling
                    // will look good
                    path.moveTo(firstPoint.x, firstPoint.y);
                }

                if(lastPoint != null) {
                    appendToPath(path, thisPoint, lastPoint);
                }

                lastPoint = thisPoint;
            }
            else
            {
                if(lastPoint != null)
                {
                    renderPath(canvas, plotArea, path, firstPoint, lastPoint, formatter);
                }
                firstPoint = null;
                lastPoint = null;
            }
        }
        if(linePaint != null && firstPoint != null)
        {
            renderPath(canvas, plotArea, path, firstPoint, lastPoint, formatter);
        }

        Paint vertexPaint = formatter.getVertexPaint();
        Paint strokePaint = ((MyLineAndPointFormatter)formatter).getStrokePaint();
        PointLabelFormatter plf = formatter.getPointLabelFormatter();
        if (vertexPaint != null || plf != null)
        {
            for (Pair<PointF, Integer> p : points)
            {
                Log.d(",p.first.toString()",p.second.toString());
                PointLabeler pointLabeler = formatter.getPointLabeler();

                // if vertexPaint is available, draw vertex:
                if(vertexPaint != null)
                {
                    canvas.drawPoint(p.first.x, p.first.y, vertexPaint);
                }

                // if stroke is available, draw stroke:
                if(strokePaint != null)
                {
                    // you'll probably want to make the radius a configurable parameter
                    // instead of hard-coded like it is here.
                    Log.d("p.first.y", String.valueOf(p.first.y));
                    Log.d("p.first.x", String.valueOf(p.first.x));
                    //canvas.drawCircle(p.first.x, p.first.y, 14, strokePaint);
                    canvas.drawRect(p.first.x-25,p.first.y-20,p.first.x+25,p.first.y+20,strokePaint);

                    Paint paintDrawText = new Paint();
                    paintDrawText.setColor(Color.BLACK);
                    paintDrawText.setTextSize(30);
                    canvas.drawText(series.getY(p.second).toString(),p.first.x-18,p.first.y+10,paintDrawText);
                }

                // if textPaint and pointLabeler are available, draw point's text label:
                if(plf != null && pointLabeler != null)
                {
                        canvas.drawText(pointLabeler.getLabel(series, p.second), p.first.x + plf.hOffset, p.first.y + plf.vOffset, plf.getTextPaint());
                }
            }
        }
    }
}