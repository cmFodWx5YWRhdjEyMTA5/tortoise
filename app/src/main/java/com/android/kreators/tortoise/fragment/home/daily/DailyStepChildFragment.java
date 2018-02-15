package com.android.kreators.tortoise.fragment.home.daily;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.model.step.chart.UserStepGroupData;
import com.android.kreators.tortoise.model.step.chart.UserStepItem;
import com.android.nobug.core.BaseFragment;
import com.android.nobug.util.StringUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by rrobbie on 05/12/2017.
 */

public class DailyStepChildFragment extends BaseFragment {

    private int position = 0;
    private UserStepGroupData item;
    private BarChart barChart;

    private int WEEK_COUNT = 7;

    //  ====================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_step_child;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

        try {
            position = (Integer) args.get(IntentProperty.POSITION);
            item = (UserStepGroupData) args.getSerializable(DailyStepChildFragment.class.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  ====================================================================================

    @Override
    public void createChildren() {
        super.createChildren();

        barChart = (BarChart) mView.findViewById(R.id.chart);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        createBarChart();
    }

    //  ====================================================================================

    private void setBarData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList list = new ArrayList<String>();

        int start = position * WEEK_COUNT;
        int end = start + WEEK_COUNT;

        ArrayList<UserStepItem> items = new ArrayList<>(item.stepList.subList(start, end));

        for (int i=0; i<WEEK_COUNT; i++) {
            UserStepItem item = items.get(i);

            try {
                Calendar calendar = StringUtil.getDate(item.target_date, "yyyy-MM-dd");
                String date = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
                list.add( date );
            } catch (Exception e) {
                e.printStackTrace();
            }

            yVals1.add(new BarEntry(i, item.step));
        }

        BarDataSet dataSet;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            dataSet = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            dataSet.setValues(yVals1);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            dataSet = new BarDataSet(yVals1, "");
            dataSet.setColors(ContextCompat.getColor(getActivity(), R.color.colorAccent));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(dataSet);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.color_212121));
            data.setBarWidth(0.5f);

            barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(list));
            barChart.setData(data);
        }
    }

    private void createBarChart() {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        final XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setCenterAxisLabels(false);

        barChart.getAxisLeft().setAxisMaximum(item.max + 100);
        barChart.getAxisLeft().setAxisMinimum(0);

        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisLeft().setDrawAxisLine(false);

        LimitLine ll2 = new LimitLine(0f, "");
        ll2.setLineWidth(1f);
        ll2.setLineColor(ContextCompat.getColor(getActivity(), R.color.color_757575));
        barChart.getAxisLeft().addLimitLine(ll2);

        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        xAxis.setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);

        setBarData();

        barChart.animateY(1000);
    }


}
