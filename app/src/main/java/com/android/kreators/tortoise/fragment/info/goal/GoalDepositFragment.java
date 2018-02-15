package com.android.kreators.tortoise.fragment.info.goal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.fragment.info.BaseInfoFragment;
import com.android.kreators.tortoise.view.hexagon.Hexagon;
import com.android.kreators.tortoise.view.keyboard.NumberKeyboard;
import com.android.nobug.util.AlertUtil;

/**
 * Created by rrobbie on 20/12/2017.
 */

public class GoalDepositFragment extends BaseInfoFragment implements View.OnClickListener {

    private TextView descriptionField;
    private TextView editField;
    private TextView whyField;

    private Hexagon hexagon;

    private NumberKeyboard numberKeyboard;

    private boolean isSlideUp;

    private int DURATION = 200;

    //  ======================================================================================

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

        if (args != null) {
            //  position = args.getInt(IntentProperty.POSITION, 0);
        }
    }

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_info_goal_deposit;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        hexagon = (Hexagon) mView.findViewById(R.id.hexagon);

        descriptionField = (TextView) mView.findViewById(R.id.descriptionField);
        editField = (TextView) mView.findViewById(R.id.editField);
        whyField = (TextView) mView.findViewById(R.id.whyField);

        numberKeyboard = (NumberKeyboard) mView.findViewById(R.id.numberKeyboard);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        setUpSliding();
    }

    @Override
    public void configureListener() {
        super.configureListener();

        whyField.setOnClickListener(this);
    }

    //  =====================================================================================

    private void setUpSliding() {
        hexagon.setGoal();

        numberKeyboard.setVisibility(View.INVISIBLE);

        numberKeyboard.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                numberKeyboard.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                slide();
            }
        });
    }

    private void slide() {
        if( isSlideUp ) {
            slideDown();
        } else {
            slideUp();
        }
    }

    private void why() {
        String message = getString(R.string.why_5_description);
        AlertUtil.alert(getActivity(), null, message);
    }

    public void slideUp(){
        numberKeyboard.setVisibility(View.VISIBLE);
        numberKeyboard.animate().translationYBy(numberKeyboard.getHeight()).translationY(0).setDuration(DURATION);
        isSlideUp = true;
    }

    public void slideDown(){
        numberKeyboard.animate().translationYBy(0).translationY(numberKeyboard.getHeight()).setDuration(DURATION);
        isSlideUp = false;
    }

    //  =====================================================================================

    public void update(String value) {
        editField.setText(value);
    }

    //  ======================================================================================

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.whyField:
                why();
                break;

            case R.id.editField:
                slideUp();
                break;
        }

    }



}
