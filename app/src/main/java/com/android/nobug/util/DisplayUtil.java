package com.android.nobug.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayUtil {

    public static int getDeviceWidth(Context context) {
        return getDeviceSize(context).x;
    }

    public static int getDeviceHeight(Context context) {
        return getDeviceSize(context).y;
    }

    private static Point getDeviceSize(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();

        if (Build.VERSION.SDK_INT > 13) {
            display.getSize(point);
        } else {
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        return point;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
        final int width = options.outWidth;

        int inSampleSize = 1;

        if (width > reqWidth) {
            inSampleSize = width / reqWidth;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap cropToSquare(Bitmap bitmap){
        if(  bitmap != null ) {
            int width  = bitmap.getWidth();
            int height = bitmap.getHeight();
            int newWidth = (height > width) ? width : height;
            int newHeight = (height > width)? height - ( height - width) : height;
            int cropW = (width - height) / 2;
            cropW = (cropW < 0)? 0: cropW;
            int cropH = (height - width) / 2;
            cropH = (cropH < 0)? 0: cropH;
            return Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);
        }

        return null;
    }

    public static int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public static Bitmap rotate(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

            try {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != converted) {
                    bitmap.recycle();
                    bitmap = converted;
                }
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static float getMapScale(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return (int)(displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 2.0f;
    }

    //  =======================================================================================

    public static void setCenterToolbar(@NonNull final Toolbar toolbar) {
        final CharSequence title = toolbar.getTitle();
        final ArrayList<View> outViews = new ArrayList<>(1);
        toolbar.findViewsWithText(outViews, title, View.FIND_VIEWS_WITH_TEXT);
        if (!outViews.isEmpty()) {
            final TextView titleView = (TextView) outViews.get(0);
            titleView.setGravity(Gravity.CENTER);
            final Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) titleView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            toolbar.requestLayout();
        }
    }

    //  =======================================================================================

    public static void progress(RelativeLayout view, int w, int d, int p){
        try {
            int width = (w/d * (p));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void progressTime(RelativeLayout view, int w, int max, int current){
        try {
            double value = (double) current / max;
            double width = w * value;
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int)width, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  =======================================================================================

    public static void showKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    public static void hideKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }




}
