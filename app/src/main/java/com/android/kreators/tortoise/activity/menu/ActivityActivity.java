package com.android.kreators.tortoise.activity.menu;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.adapter.ActivityAdapter;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.model.activity.ActivityItemGroup;
import com.android.kreators.tortoise.net.RetrofitBuilder;
import com.android.nobug.activity.PendingActivity;
import com.android.nobug.util.log;
import com.android.nobug.view.listview.BaseListView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityActivity extends PendingActivity implements Callback, AbsListView.OnScrollListener {

    private BaseListView listView;
    private ActivityAdapter activityAdapter;
    private ProgressBar progressBar;

    private RequestManager mGlideRequestManager;

    private long seq;
    private int offset = 0;
    private int count = 10;

    //  =====================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_activity;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        mGlideRequestManager = Glide.with(getContext());

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (BaseListView) findViewById(R.id.listView);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        try {
            seq = getIntent().getLongExtra(IntentProperty.SEQ, 0);
            request();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void configureListener() {
        super.configureListener();

        listView.setOnScrollListener(this);
    }

    //  =====================================================================================

    private void request() {
        RetrofitBuilder.withUnsafe(this).getTransaction(seq, offset, count).enqueue(this);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void update(Response response) {
        try {
            progressBar.setVisibility(View.INVISIBLE);

            ActivityItemGroup item = (ActivityItemGroup) response.body();

            if( activityAdapter == null ) {
                activityAdapter = new ActivityAdapter(this, mGlideRequestManager, item.data);
                listView.setAdapter(activityAdapter);
            } else {
                activityAdapter.addAll(item.data);
            }

            offset = activityAdapter.getCount();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  =====================================================================================

    @Override
    public void onResponse(Call call, Response response) {
        update(response);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        log.show( "on failure : " + call.request().url().toString() + " / " + t.getLocalizedMessage() );
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int threshold = 1;
        int itemCount = listView.getCount();

        if (scrollState == SCROLL_STATE_IDLE) {
            if( listView.getLastVisiblePosition() >= (count - threshold) ) {
                if( itemCount < count )
                    return;

                if( itemCount % count == 0 ) {
                    request();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}

}
