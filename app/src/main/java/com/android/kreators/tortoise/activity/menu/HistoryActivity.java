package com.android.kreators.tortoise.activity.menu;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.factory.FragmentFactory;
import com.android.nobug.activity.PendingActivity;
import com.android.nobug.core.BaseFragment;
import com.android.nobug.view.viewpager.adapter.FragmentAdapter;

import java.util.ArrayList;

public class HistoryActivity extends PendingActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_history;
    }

    //  ======================================================================================

    @Override
    public void createChildren() {
        super.createChildren();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        try {
            long seq = getIntent().getLongExtra(IntentProperty.SEQ, 0);

            ArrayList<BaseFragment> fragments = FragmentFactory.getHistoryFragments(seq);
            fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager(), fragments);
            viewPager.setAdapter(fragmentAdapter);
            viewPager.setOffscreenPageLimit(fragmentAdapter.getCount());
            tabLayout.setupWithViewPager(viewPager);

            tabLayout.getTabAt(0).setText(R.string.today);
            tabLayout.getTabAt(1).setText(R.string.total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void configureListener() {
        super.configureListener();


    }

    //  ======================================================================================

    /*

    private void setBarData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        yVals1.add(new BarEntry(0, 2000));
        yVals1.add(new BarEntry(1, 2000));
        yVals1.add(new BarEntry(2, 2000));
        yVals1.add(new BarEntry(3, 2000));
        yVals1.add(new BarEntry(4, 2000));
        yVals1.add(new BarEntry(5, 2000));
        yVals1.add(new BarEntry(6, 2000));

        BarDataSet dataSet;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            dataSet = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            dataSet.setValues(yVals1);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            dataSet = new BarDataSet(yVals1, "");
            dataSet.setColors(Color.YELLOW);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(dataSet);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.5f);

            ArrayList list = new ArrayList<String>();
            list.add( "7/11" );
            list.add( "7/12" );
            list.add( "7/13" );
            list.add( "7/14" );
            list.add( "7/15" );
            list.add( "7/16" );
            list.add( "7/17" );

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

        barChart.getAxisLeft().setAxisMaximum(10000f);
        barChart.getAxisLeft().setAxisMinimum(0f);

        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        xAxis.setDrawGridLines(false);

        setBarData();

        barChart.animateXY(2000, 2000);

    }

    //  =====================================================================================

    private void createLineChart() {
        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);

        LimitLine ll2 = new LimitLine(0f, "");
        ll2.setLineWidth(1f);
        ll2.setLineColor(Color.BLACK);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);

        YAxis leftAxis = mChart.getAxisLeft();
        YAxis rightAxis = mChart.getAxisRight();
        XAxis xAxis = mChart.getXAxis();
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

        mChart.getLegend().setForm(Legend.LegendForm.EMPTY);

        // add data
        setData();

        mChart.animateXY(1500, 1500);
    }

    private void setData() {
        ArrayList<Entry> values = new ArrayList<Entry>();
        values.add(new Entry(0, 0.002f));
        values.add(new Entry(1, 0.021f));
        values.add(new Entry(2, 0.390f));
        values.add(new Entry(3, 1.067f));
        values.add(new Entry(4, 2.500f));

        LineDataSet set;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set = new LineDataSet(values, null);
            set.setColor(Color.BLACK);
            set.setDrawCircles(false);
            set.setLineWidth(1f);
            set.setValueTextSize(0f);

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set);

            LineData data = new LineData(dataSets);

            mChart.setData(data);
        }
    }
    */

    //  ======================================================================================



}
