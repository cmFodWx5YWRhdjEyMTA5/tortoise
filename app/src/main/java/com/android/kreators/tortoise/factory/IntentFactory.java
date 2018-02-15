package com.android.kreators.tortoise.factory;

import android.content.Context;
import android.content.Intent;

import com.android.kreators.tortoise.activity.auth.ChangePasswordActivity;
import com.android.kreators.tortoise.activity.auth.CreateAccountActivity;
import com.android.kreators.tortoise.activity.auth.ForgotPasswordActivity;
import com.android.kreators.tortoise.activity.auth.SignInActivity;
import com.android.kreators.tortoise.activity.auth.SignUpPlusActivity;
import com.android.kreators.tortoise.activity.home.AddDepositActivity;
import com.android.kreators.tortoise.activity.home.HomeActivity;
import com.android.kreators.tortoise.activity.info.BankInfoActivity;
import com.android.kreators.tortoise.activity.info.GoalInfoActivity;
import com.android.kreators.tortoise.activity.info.UserInfoActivity;
import com.android.kreators.tortoise.activity.intro.IntroActivity;
import com.android.kreators.tortoise.activity.menu.ActivityActivity;
import com.android.kreators.tortoise.activity.menu.DepositActivity;
import com.android.kreators.tortoise.activity.menu.HistoryActivity;
import com.android.kreators.tortoise.activity.menu.MyAccountActivity;
import com.android.kreators.tortoise.activity.menu.SettingsActivity;
import com.android.kreators.tortoise.activity.menu.WearableActivity;
import com.android.kreators.tortoise.activity.myaccount.PendingTransferActivity;
import com.android.kreators.tortoise.activity.myaccount.TransferToBankActivity;
import com.android.kreators.tortoise.activity.setting.pattern.SetPatternActivity;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.model.auth.AuthItem;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.kreators.tortoise.model.auth.sns.FacebookItem;
import com.android.kreators.tortoise.model.myaccount.pendingtransfer.PendingTransferItemGoup;
import com.android.nobug.activity.BrowserActivity;
import com.android.nobug.constant.property.BaseIntentProperty;

public class IntentFactory {

    //  ======================================================================================

    public static void intro(Context context) {
        Intent intent = new Intent(context, IntroActivity.class);
        context.startActivity(intent);
    }

    public static void home(Context context, AuthItem item) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(IntentProperty.AUTH_ITEM, item);
        context.startActivity(intent);
    }

    public static void signIn(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        context.startActivity(intent);
    }

    public static void snsSignUp(Context context, FacebookItem item) {
        Intent intent = new Intent(context, SignUpPlusActivity.class);
        intent.putExtra(IntentProperty.FACEBOOK_ITEM, item);
        context.startActivity(intent);
    }

    public static void createAccount(Context context) {
        Intent intent = new Intent(context, CreateAccountActivity.class);
        context.startActivity(intent);
    }

    public static void myAccount(Context context, AuthItem item) {
        Intent intent = new Intent(context, MyAccountActivity.class);
        intent.putExtra(IntentProperty.AUTH_ITEM, item);
        context.startActivity(intent);
    }

    public static void pendingToTransfer(Context context, AuthItem authItem, PendingTransferItemGoup pendingTransferItem) {
        Intent intent = new Intent(context, PendingTransferActivity.class);
        intent.putExtra(IntentProperty.AUTH_ITEM, authItem);
        intent.putExtra(IntentProperty.PENDING_ITEM, pendingTransferItem);
        context.startActivity(intent);
    }

    public static void transferToBank(Context context, AuthItem item, double balance) {
        Intent intent = new Intent(context, TransferToBankActivity.class);
        intent.putExtra(IntentProperty.AUTH_ITEM, item);
        intent.putExtra(IntentProperty.BALANCE, balance);
        context.startActivity(intent);
    }

    public static void history(Context context, long seq) {
        Intent intent = new Intent(context, HistoryActivity.class);
        intent.putExtra(IntentProperty.SEQ, seq);
        context.startActivity(intent);
    }

    public static void deposit(Context context) {
        Intent intent = new Intent(context, DepositActivity.class);
        context.startActivity(intent);
    }

    public static void addDeposit(Context context) {
        Intent intent = new Intent(context, AddDepositActivity.class);
        context.startActivity(intent);
    }

    public static void activity(Context context, long seq) {
        Intent intent = new Intent(context, ActivityActivity.class);
        intent.putExtra(IntentProperty.SEQ, seq);
        context.startActivity(intent);
    }

    public static void wearable(Context context) {
        Intent intent = new Intent(context, WearableActivity.class);
        context.startActivity(intent);
    }

    public static void settings(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    public static void forgotPassword(Context context) {
        Intent intent = new Intent(context, ForgotPasswordActivity.class);
        context.startActivity(intent);
    }

    public static void changePassword(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(intent);
    }

    public static void browser(Context context, String title, String url) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(BaseIntentProperty.TITLE, title);
        intent.putExtra(BaseIntentProperty.URL, url);
        context.startActivity(intent);
    }

    public static void setPatternActivity(Context context) {
        Intent intent = new Intent(context, SetPatternActivity.class);
        context.startActivity(intent);
    }

    //  =====================================================================================

    public static void userInfo(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    public static void bankInfo(Context context) {
        Intent intent = new Intent(context, BankInfoActivity.class);
        context.startActivity(intent);
    }

    public static void goalInfo(Context context) {
        Intent intent = new Intent(context, GoalInfoActivity.class);
        context.startActivity(intent);
    }

}



