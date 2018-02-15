package com.android.nobug.webview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.nobug.constant.webview.MymeType;
import com.android.nobug.constant.webview.Protocol;

public class BrowserClient extends WebViewClient {

    private final String IMAGE = "image";
    private final String M3U8 = ".m3u8";
    private final String MARKET_FORMAT = "market://details?id=%s";

    protected Context mContext;

    //  ========================================================================================

    public BrowserClient(Context context) {
        mContext = context;
    }

    //  ========================================================================================

    protected void loadIntent(String url) {
        try {
            Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            if (mContext.getPackageManager().resolveActivity(intent, 0) == null) {
                Uri uri = Uri.parse(String.format(MARKET_FORMAT, intent.getPackage()));
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } else {
                mContext.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void loadRtsp(String url) {
        try {
            mContext.startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse(url)) );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void loadTel(String url) {
        try {
            mContext.startActivity(new Intent(Intent.ACTION_DEFAULT, Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void loadMymeType(String url, String mimeType) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(url), mimeType);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void loadM3u8(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(url), MymeType.MPEG);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  =======================================================================================

    private void showHide(WebView view, int start, int end) {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(view, View.ALPHA, start, end);
        alphaAnimation.start();
    }

    //  =======================================================================================

    @Override
    public void onPageFinished(WebView view, final String url) {
        super.onPageFinished(view, url);
        showHide(view, 0, 1);
    }

    @Override
    public void onPageStarted(WebView view, final String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        showHide(view, 1, 0);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        String lowerCase = url.toLowerCase();
        if (lowerCase.startsWith(Protocol.INTENT)) {
            loadIntent(url);
        } else if (lowerCase.contains(Protocol.RTSP)) {
            loadRtsp(url);
        } else if (lowerCase.startsWith(Protocol.TEL)) {
            loadTel(url);
        } else if (lowerCase.startsWith(Protocol.HTTP) || lowerCase.startsWith(Protocol.HTTPS)) {
            if (lowerCase.contains(M3U8)) {
                loadM3u8(url);
                return true;
            }

            try {
                String fileExtension = MimeTypeMap.getSingleton().getFileExtensionFromUrl(url);
                if (fileExtension != null) {
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
                    if (mimeType != null && mimeType.contains(IMAGE)) {
                        loadMymeType(url, mimeType);
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            view.loadUrl(url);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            try {
                mContext.startActivity(intent);
            } catch (Exception e) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }
        return true;
    }

}