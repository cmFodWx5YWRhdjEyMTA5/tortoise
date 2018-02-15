package com.android.nobug.activity;

import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.android.kreators.tortoise.R;
import com.android.nobug.constant.property.BaseIntentProperty;
import com.android.nobug.webview.Browser;
import com.android.nobug.webview.IBrowserClientEvent;

public class BrowserActivity extends PendingActivity implements IBrowserClientEvent {

    protected Browser browser;
    protected String title;

    //  =========================================================================================

    @Override
    public void onBackPressed() {
        historyBack();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home: {
                historyBack();
                return false;
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_browser;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        browser = (Browser) findViewById(R.id.browser);
        browser.setEventListener(this);

        if (getIntent() != null) {
            loadUrl(getIntent().getStringExtra(BaseIntentProperty.URL));
            title = getIntent().getStringExtra(BaseIntentProperty.TITLE);

            if (title != null) {
                getSupportActionBar().setTitle(title);
            }
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (browser != null) {
                ((ViewGroup) browser.getParent()).removeView(browser);
                browser.removeAllViews();
                browser.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    //  =========================================================================================

    @Override
    public void onReceivedTitle(WebView webview, String webviewTitle) {
        if (title == null) {
            getSupportActionBar().setTitle(webviewTitle);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //  ========================================================================================

    protected void historyBack() {
        if (browser.canGoBack()) {
            browser.goBack();
            return;
        }

        super.onBackPressed();
    }

    private void loadUrl(String url) {
        if (url != null) {
            browser.loadUrl(url);
        }
    }

}
