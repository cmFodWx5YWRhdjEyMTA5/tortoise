package com.android.kreators.tortoise.activity.menu;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.adapter.recyclerview.SettingsItemAdapter;
import com.android.kreators.tortoise.adapter.recyclerview.holder.setting.SettingBTypeHolder;
import com.android.kreators.tortoise.constants.BrowserURL;
import com.android.kreators.tortoise.factory.IntentFactory;
import com.android.kreators.tortoise.model.setting.SettingsModeltem;
import com.android.nobug.activity.PendingActivity;
import com.android.nobug.listener.RecyclerItemClickListener;
import com.android.nobug.manager.ActivityManager;
import com.android.nobug.manager.auth.AuthManager;
import com.android.nobug.util.AlertUtil;
import com.android.nobug.util.DeviceInfoUtil;

import java.util.ArrayList;

/**
 * Created by rrobbie on 2017. 4. 21..
 */

public class SettingsActivity extends PendingActivity implements AdapterView.OnItemClickListener {


    private RecyclerView recyclerView;
    private SettingsItemAdapter settingsItemAdapter;
    private ArrayList<SettingsModeltem> settingItems;

    //  =====================================================================================

    private final Integer[] menus = {
            R.string.preference,
            R.string.daily_step,
            R.string.daily_deposit,
            R.string.bank_information,
            R.string.pending_transfer,
            R.string.set_unlock_pattern,
            R.string.change_password,
            R.string.privacy_and_sharing,
            R.string.notifications,
            R.string.support,
            R.string.faq,
            R.string.send_feedback,
            R.string.assessment,
            R.string.introduce,
            R.string.about_tortoise,
            R.string.terms_and_conditions,
            R.string.privacy_policy,
            R.string.open_source_license,
            R.string.version,
            R.string.sign_out
    };

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.activity_settings;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        setupItems();

        settingsItemAdapter = new SettingsItemAdapter();
        settingsItemAdapter.setItems(settingItems);
        recyclerView.setAdapter(settingsItemAdapter);
    }

    @Override
    public void configureListener() {
        super.configureListener();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        int id = menus[position];

                        if( id == R.string.notifications || id == R.string.privacy_and_sharing ) {
                            SettingBTypeHolder holder = (SettingBTypeHolder) recyclerView.findViewHolderForAdapterPosition(position);
                            holder.switchButton.setChecked( !holder.switchButton.isChecked() );
                        } else {
                            selectItem(position);
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {}
                })
        );
    }

    //  ======================================================================================

    private void setupItems() {
        settingItems = new ArrayList<>();
        int size = menus.length;

        for (int i =0; i<size; i++) {
            SettingsModeltem item = new SettingsModeltem();
            int id = menus[i];
            item.title = getString(id);

            switch (id) {
                case R.string.preference:     item.type = SettingsItemAdapter.VIEW_TYPE_D;     break;
                case R.string.daily_step:   item.type = SettingsItemAdapter.VIEW_TYPE_A;    break;
                case R.string.daily_deposit: item.type = SettingsItemAdapter.VIEW_TYPE_A;   break;
                case R.string.bank_information: item.type = SettingsItemAdapter.VIEW_TYPE_A;    break;
                case R.string.pending_transfer: item.type = SettingsItemAdapter.VIEW_TYPE_A;    break;
                case R.string.change_password: item.type = SettingsItemAdapter.VIEW_TYPE_A;    break;
                case R.string.set_unlock_pattern:  item.type = SettingsItemAdapter.VIEW_TYPE_A;   break;
                case R.string.privacy_and_sharing: item.type = SettingsItemAdapter.VIEW_TYPE_B;     break;
                case R.string.notifications:   item.type = SettingsItemAdapter.VIEW_TYPE_B;     break;

                case R.string.support:     item.type = SettingsItemAdapter.VIEW_TYPE_D;     break;
                case R.string.faq:   item.type = SettingsItemAdapter.VIEW_TYPE_A;   break;
                case R.string.send_feedback:   item.type = SettingsItemAdapter.VIEW_TYPE_A;     break;
                case R.string.assessment:    item.type = SettingsItemAdapter.VIEW_TYPE_A;   break;

                case R.string.introduce:     item.type = SettingsItemAdapter.VIEW_TYPE_D;     break;
                case R.string.about_tortoise:  item.type = SettingsItemAdapter.VIEW_TYPE_A;    break;
                case R.string.terms_and_conditions:    item.type = SettingsItemAdapter.VIEW_TYPE_A;     break;
                case R.string.privacy_policy:  item.type = SettingsItemAdapter.VIEW_TYPE_A;    break;
                case R.string.open_source_license:  item.type = SettingsItemAdapter.VIEW_TYPE_A;   break;
                case R.string.version:
                    item.type = SettingsItemAdapter.VIEW_TYPE_C;
                    item.description = DeviceInfoUtil.getAppVersion(this);
                    break;
                case R.string.sign_out:     item.type = SettingsItemAdapter.VIEW_TYPE_A;     break;
            }
            settingItems.add( item );
        }
    }

    private void signOut() {
        String message = getContext().getString(R.string.signout_message);

        AlertUtil.alertNegative(this, null, message, null, null, true, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                AuthManager.getInstance().signOut(getContext());
                ActivityManager.getInstance().reset(SettingsActivity.this);
                IntentFactory.intro(getContext());
                finish();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private void changePassword() {
        IntentFactory.changePassword(this);
    }

    private void browser(String title, String url) {
        IntentFactory.browser(this, title, url);
    }

    private void patternLock() {
        IntentFactory.setPatternActivity(this);
    }

    //  ======================================================================================

    private void selectItem(int position) {
        Integer item = menus[position];

        switch (item) {
            case R.string.daily_step:   break;
            case R.string.daily_deposit:    break;
            case R.string.bank_information:     break;
            case R.string.pending_transfer:     break;
            case R.string.change_password: changePassword();    break;
            case R.string.set_unlock_pattern: patternLock();  break;
            case R.string.privacy_and_sharing:      break;
            case R.string.notifications:        break;

            case R.string.faq:   browser(getString(R.string.faq), BrowserURL.FAQ);   break;
            case R.string.send_feedback:        break;
            case R.string.assessment:       break;

            case R.string.about_tortoise:   browser(getString(R.string.about_tortoise), BrowserURL.ABOUT_TORTOISE);    break;
            case R.string.terms_and_conditions:     browser(getString(R.string.terms_and_conditions), BrowserURL.TERMS_AND_CONDITIONS);    break;
            case R.string.privacy_policy:   browser(getString(R.string.privacy_policy), BrowserURL.PRIVACY_POLICY);     break;
            case R.string.open_source_license:  browser(getString(R.string.open_source_license), BrowserURL.OPEN_SOURCE_LICENCE);    break;
            case R.string.sign_out:     signOut();      break;
        }
    }

    //  ======================================================================================

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            selectItem(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
