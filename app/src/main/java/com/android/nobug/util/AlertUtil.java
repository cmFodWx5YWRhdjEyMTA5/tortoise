package com.android.nobug.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.android.kreators.tortoise.R;

public class AlertUtil {

    private static ProgressDialog progressDialog;

    //  ========================================================================================

    public static void alertStyle(Context context, int style) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, style);

        builder.setPositiveButton(R.string.alert_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setTitle("test");
        builder.setMessage("message");

        AlertDialog dialog = builder.create();
        show(context, dialog);
    }

    public static void alert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Alert_DialogTheme);

        builder.setPositiveButton(R.string.alert_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setTitle(title);
        builder.setMessage(message);

        AlertDialog dialog = builder.create();
        show(context, dialog);
    }

    public static void alert(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Alert_DialogTheme);

        builder.setPositiveButton(R.string.alert_confirm, listener);

        builder.setTitle(title);
        builder.setMessage(message);

        AlertDialog dialog = builder.create();
        show(context, dialog);
    }

    public static void alertPositive(Context context, String title, String message,
                                     String confirm, boolean canceled, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Alert_DialogTheme);

        if( title != null ) {
            builder.setTitle(title);
        }

        builder.setMessage(message).setCancelable(false);
        String onText = confirm != null ? confirm : context.getString(R.string.alert_confirm);
        builder.setPositiveButton(onText, listener);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(canceled);
        show(context, dialog);
    }

    public static void alertNegative(Context context, String title, String message,
                                     String confirm, String cancel, boolean canceled,
                                     DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Alert_DialogTheme);

        if( title != null ) {
            builder.setTitle(title);
        }

        builder.setMessage(message).setCancelable(false);
        String okText = confirm != null ? confirm : context.getString(R.string.alert_confirm);
        String cancelText = cancel != null ? cancel : context.getString(R.string.alert_cancel);
        builder.setPositiveButton(okText, okListener);

        DialogInterface.OnClickListener onCancelListener = cancelListener;

        if( cancelListener == null ) {
            onCancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            };
        }

        builder.setNegativeButton(cancelText, onCancelListener);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(canceled);
        dialog.setCanceledOnTouchOutside(canceled);
        show(context, dialog);
    }

    private static void show(Context context, AlertDialog dialog) {
        try {
            if ( (context != null) && (!dialog.isShowing()) ) {
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  ========================================================================================

    public static void showProgressDialog(Context context) {
        try {
            showProgressDialog(context, context.getString(R.string.default_loading_comment), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showProgressDialog(Context context, String value, boolean canceled) {
        dismissProgressDialog();

        try {
            progressDialog = new ProgressDialog(context, R.style.Alert_DialogTheme);
            progressDialog.setCanceledOnTouchOutside(canceled);
            progressDialog.setCancelable(canceled);
            progressDialog.setMessage(value != null ? value : context.getString(R.string.default_loading_comment));
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void dismissProgressDialog() {
        try {
            if (progressDialog != null) {
                if( progressDialog.isShowing() ) {
                    progressDialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

