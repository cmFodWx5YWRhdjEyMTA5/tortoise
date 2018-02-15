package com.android.kreators.tortoise.fragment.info.goal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.fragment.info.BaseInfoFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rrobbie on 20/12/2017.
 */

public class GoalStepFragment extends BaseInfoFragment {

    private LineChart chart;
    private EditText editField;
    private TextView guideField;

    //  ======================================================================================

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

        if (args != null) {
            //  position = args.getInt(IntentProperty.POSITION, 0);
        }
    }

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_info_goal_step;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        chart = (LineChart) mView.findViewById(R.id.chart);
        editField = (EditText) mView.findViewById(R.id.editField);
        guideField = (TextView) mView.findViewById(R.id.guideField);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        createLineChart();
    }

    @Override
    public void configureListener() {
        super.configureListener();


    }

    //  ======================================================================================

    private void createLineChart() {
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);

        LimitLine ll2 = new LimitLine(0f, "");
        ll2.setLineWidth(1f);
        ll2.setLineColor(Color.BLACK);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);

        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelCount(6, true);

        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(2.500f);
        leftAxis.setAxisMinimum(0f);

        rightAxis.setEnabled(false);

        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);

        final HashMap<Integer, String> numMap = new HashMap<>();

        numMap.put(0, "0~1000");
        numMap.put(1, "2500");
        numMap.put(2, "5000");
        numMap.put(3, "7500");
        numMap.put(4, "10000");

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return numMap.get((int)(value/0.8f));
            }
        });

        chart.getLegend().setForm(Legend.LegendForm.EMPTY);

        // add data
        setData();
        chart.animateXY(1500, 1500);
    }

    private void setData() {
        ArrayList<Entry> values = new ArrayList<Entry>();
        values.add(new Entry(0, 0.002f));
        values.add(new Entry(1, 0.021f));
        values.add(new Entry(2, 0.390f));
        values.add(new Entry(3, 1.067f));
        values.add(new Entry(4, 2.500f));

        LineDataSet set;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set = (LineDataSet)chart.getData().getDataSetByIndex(0);
            set.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set = new LineDataSet(values, null);
            set.setColor(Color.BLACK);
            set.setDrawCircles(false);
            set.setLineWidth(1f);
            set.setValueTextSize(0f);

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set);
            LineData data = new LineData(dataSets);
            chart.setData(data);
        }
    }

}
