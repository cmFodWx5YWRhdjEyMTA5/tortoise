package com.android.kreators.tortoise.fragment.history;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.model.activity.ActivityItem;
import com.android.kreators.tortoise.model.activity.ActivityItemGroup;
import com.android.kreators.tortoise.model.history.HistoryGroup;
import com.android.kreators.tortoise.model.history.HistoryItem;
import com.android.kreators.tortoise.net.RetrofitBuilder;
import com.android.nobug.core.BaseFragment;
import com.android.nobug.util.CalculationUtil;
import com.android.nobug.util.ObjectUtil;
import com.android.nobug.util.StringUtil;
import com.android.nobug.util.log;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rrobbie on 29/11/2017.
 */

public class HistoryTotalFragment extends BaseFragment implements Callback {

    private LineChart chart;
    private TextView balanceField;
    private TextView savingBonusField;

    private String today;
    private long seq;

    private final int LEGEND_COUNT = 4;
    private final int X_COUNT = 28;

    //  ====================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_history_total;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        chart = (LineChart) mView.findViewById(R.id.chart);

        balanceField = (TextView) mView.findViewById(R.id.balanceField);
        savingBonusField = (TextView) mView.findViewById(R.id.savingBonusField);

    }

    @Override
    public void setProperties() {
        super.setProperties();

        request();
    }

    @Override
    public void configureListener() {
        super.configureListener();


    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        seq = args.getLong(IntentProperty.SEQ);
    }

    //  =====================================================================================

    private void request() {
        try {
            today = StringUtil.getTodayDate();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            cal.add(Calendar.DATE, -1);
            String end_date = simpleDateFormat.format(cal.getTime());

            cal.setTime(date);
            cal.add(Calendar.DATE, -28);
            String start_date = simpleDateFormat.format(cal.getTime());

            RetrofitBuilder.withUnsafe(getActivity()).getTotalHistory(seq, start_date, end_date).enqueue(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTextField(double balance, double bonus) {
        balanceField.setText( (balance + "") );
        savingBonusField.setText( bonus + "" );
    }

    //  =====================================================================================

    private void update(Response response) {
        try {
            HistoryGroup historyGroup = (HistoryGroup) response.body();
            ArrayList<HistoryItem> items = historyGroup.data.step_daily_list;
            double balance = historyGroup.data.start_balance.getBalance();
            double bonus = historyGroup.data.start_balance.sum_saving_bonus_daily;
            double max = 0f;
            ArrayList<Entry> entries = new ArrayList<Entry>();
            int size = items.size();

            for (int i=0; i<size; i++) {
                balance+= items.get(i).getBalance();
                bonus+= items.get(i).saving_bonus_daily;

                if( balance > max )
                    max = balance;

                entries.add(new Entry(i, (float) balance));
            }

            setTextField( balance, bonus );
            createLineChart(0f, (float) max);
            setData(entries);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createLineChart(float min, float max) {
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);

        LimitLine ll2 = new LimitLine(0f, max + "");
        ll2.setLineWidth(1f);
        ll2.setLineColor(Color.WHITE);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);

        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        if( !CalculationUtil.getOverNumber(max, 10) )
            max = 10;

        if( !CalculationUtil.getUnderNumber(min, -10) )
            min = -10;

        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(max);
        leftAxis.setAxisMinimum(min);
        leftAxis.setLabelCount(2);
        leftAxis.setTextColor(Color.WHITE);

        rightAxis.setEnabled(false);
        leftAxis.setDrawLabels(true);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);

        chart.getLegend().setForm(Legend.LegendForm.EMPTY);
    }

    private void setData(ArrayList<Entry> values) {
        LineDataSet set;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set = (LineDataSet)chart.getData().getDataSetByIndex(0);
            set.setValues(values);
            set.setDrawFilled(true);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set = new LineDataSet(values, null);
            set.setColor(Color.WHITE);
            set.setDrawCircles(false);
            set.setLineWidth(2f);
            set.setValueTextSize(0f);
            set.setDrawFilled(true);

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set);
            LineData data = new LineData(dataSets);
            chart.setData(data);
        }
        chart.animateX(1000);
    }

    //  ====================================================================================

    @Override
    public void onResponse(Call call, Response response) {
        update(response);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        log.show( "on failure : " + call.request().url().toString() + " / " + t.getLocalizedMessage() );
    }




}
