package com.android.kreators.tortoise.fragment.home;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.manager.CacheManager;
import com.android.kreators.tortoise.model.step.BaseStepItem;
import com.android.nobug.core.BaseFragment;

/**
 * Created by rrobbie on 2017. 9. 27..
 */

public class TestHomeFragment extends PermissionFragment {

    protected EditText stepField;
    protected EditText stepGoalField;
    protected EditText depositField;
    protected EditText depositGoalField;

    protected Button invalidateButton;

    protected int editStep;
    protected int editStepGoal;
    protected double editDeposit;
    protected double editDepositGoal;

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_home;
    }


    @Override
    public void createChildren() {
        super.createChildren();

/*
        stepField = (EditText) mView.findViewById(R.id.stepField);
        stepGoalField = (EditText) mView.findViewById(R.id.stepGoalField);
        depositField = (EditText) mView.findViewById(R.id.depositField);
        depositGoalField = (EditText) mView.findViewById(R.id.depositGoalField);
        invalidateButton = (Button) mView.findViewById(R.id.invalidateButton);
*/
    }

    @Override
    public void configureListener() {
        super.configureListener();

        /*
        invalidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editStep = Integer.parseInt(stepField.getText().toString());
                editStepGoal = Integer.parseInt(stepGoalField.getText().toString());
                editDepositGoal = Double.parseDouble(depositGoalField.getText().toString());

                BaseStepItem item = CacheManager.getInstance().getCurrentItem(getActivity());
                item.step = editStep;
                item.step_goal = editStepGoal;
                item.deposit_goal = editDepositGoal;

                CacheManager.getInstance().save(getActivity(), item);

                clickInvalidateButton();
            }
        });
        */
    }

    protected void clickInvalidateButton() {
        //  TODO

    }


}
