package com.android.kreators.tortoise.fragment.history;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.manager.BalanceManager;
import com.android.kreators.tortoise.model.activity.ActivityItem;
import com.android.kreators.tortoise.model.activity.ActivityItemGroup;
import com.android.kreators.tortoise.model.step.BaseStepItem;
import com.android.kreators.tortoise.net.RetrofitBuilder;
import com.android.nobug.core.BaseFragment;
import com.android.nobug.util.CalculationUtil;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rrobbie on 29/11/2017.
 */

public class HistoryDailyFragment extends BaseFragment implements Callback {

    private LineChart chart;

    private TextView balanceField;
    private TextView depositField;
    private TextView withdrawField;
    private TextView apyField;

    private String today;

    private long seq;

    private final int LEGEND_COUNT = 4;
    private final int HOUR_COUNT = 24;

    //  ====================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_history_daily;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        chart = (LineChart) mView.findViewById(R.id.chart);

        balanceField = (TextView) mView.findViewById(R.id.balanceField);
        depositField = (TextView) mView.findViewById(R.id.depositField);
        withdrawField = (TextView) mView.findViewById(R.id.withdrawField);
        apyField = (TextView) mView.findViewById(R.id.apyField);

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
        today = StringUtil.getTodayDate();
        RetrofitBuilder.withUnsafe(getActivity()).getDailyHistory(seq, today, today, 0, 1000).enqueue(this);
    }

    private void setTextField(double deposit, double withdraw) {
        depositField.setText( deposit + "" );
        withdrawField.setText( withdraw + "" );
        balanceField.setText( (deposit + withdraw + "") );
        BaseStepItem item = PreferenceFactory.getCurrentStepItem(getActivity());
        apyField.setText( (BalanceManager.getInstance().getInterestRate(item.step).interest_rate + "") );
    }

    //  =====================================================================================

    private void update(Response response) {
        try {
            ActivityItemGroup activityItemGroup = (ActivityItemGroup) response.body();
            HashMap<Integer, Double> map = new HashMap<>();

            double deposit = 0d;
            double withdraw = 0d;

            for (ActivityItem item : activityItemGroup.data) {
                int hour = StringUtil.getDate(item.target_date, "yyyy-MM-dd HH:mm").get(Calendar.HOUR_OF_DAY);
                double total = item.deposit - item.withdraw;
                deposit+=item.deposit;
                withdraw+=item.withdraw;

                if( map.containsKey(hour) ) {
                    double balance = map.get(hour) + total;
                    map.put(hour, balance);
                } else {
                    map.put(hour, total);
                }
            }

            setTextField(deposit, withdraw);

            ArrayList<Double> values = new ArrayList<Double>(map.values());
            ArrayList<Integer> keys = new ArrayList<Integer>(map.keySet());
            ArrayList<Entry> entries = new ArrayList<Entry>();

            float valueTotal = 0f;
            float before = 0f;
            int keyCount = 0;
            float max = 0f;
            float min = 0f;

            for (int i = 0; i < HOUR_COUNT; i++) {
                int key = (keyCount < keys.size()) ? keys.get(keyCount) : -1;
                Entry entry;

                if( i == key ) {
                    valueTotal+= values.get(keyCount);
                    entry = new Entry(key, valueTotal );
                    before = valueTotal;
                    keyCount++;
                    if( valueTotal > max ) max = valueTotal;
                    if( valueTotal < min ) min = valueTotal;
                } else {
                    entry = new Entry(i, before);
                }

                entries.add(entry);
            }

            createLineChart(min, max);
            setData(entries);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createLineChart(float min, float max) {
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);

        if( !CalculationUtil.getOverNumber(max, 10) )
            max = 10;

        if( !CalculationUtil.getUnderNumber(min, -10) )
            min = -10;

        LimitLine ll2 = new LimitLine(0f, max + "");
        ll2.setLineWidth(1f);
        ll2.setLineColor(Color.WHITE);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);

        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

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
