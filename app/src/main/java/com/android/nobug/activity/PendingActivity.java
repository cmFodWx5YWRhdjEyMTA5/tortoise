package com.android.nobug.activity;

import android.os.Bundle;

import com.android.kreators.tortoise.R;
import com.android.nobug.core.BaseActivity;

public class PendingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.right_in, R.anim.alpha_fixed);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.alpha_fixed, R.anim.right_out);
    }

}
