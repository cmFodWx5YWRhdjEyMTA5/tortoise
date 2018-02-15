package com.android.kreators.tortoise.view.hexagon;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.callback.IFloatPointCallback;
import com.android.kreators.tortoise.constants.property.NumberProperty;
import com.android.kreators.tortoise.manager.BalanceManager;
import com.android.kreators.tortoise.manager.CacheManager;
import com.android.kreators.tortoise.model.balance.BalanceItem;
import com.android.kreators.tortoise.model.bank.BankItem;
import com.android.kreators.tortoise.model.step.BaseStepItem;
import com.android.nobug.core.BaseRelativeLayout;
import com.android.nobug.util.DisplayUtil;
import com.android.nobug.util.StringUtil;
import com.android.nobug.util.log;

public class HexagonBalanceViewer extends BaseRelativeLayout {

    private Hexagon hexagon;
    private Button button;
    private TextView stepField;
    private TextView balanceField;

    //  ====================================================================================

    public HexagonBalanceViewer(Context context) {
        super(context);
    }

    public HexagonBalanceViewer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HexagonBalanceViewer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //  ====================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.component_hexagon_balance_child;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        hexagon = (Hexagon) mView.findViewById(R.id.hexagon);
        button = (Button) mView.findViewById(R.id.button);
        stepField = (TextView) mView.findViewById(R.id.stepField);
        balanceField = (TextView) mView.findViewById(R.id.balanceField);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        hexagon.setIFloatPointCallback(floatPointCallback);
    }

    @Override
    public void configureListener() {
        super.configureListener();


    }

    //  ======================================================================================

    private void startAnimation(double before, final double after) {
        balanceField.setLayerType(LAYER_TYPE_HARDWARE, null);
        ValueAnimator animator = new  ValueAnimator();
        animator.setObjectValues(before, after);

        final int intSize = 1 + String.valueOf((int)after).length();
        int doubleSize = NumberProperty.BALANCE_FIELD_COUNT - intSize;
        final String format = "$%." + doubleSize + "f";

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                double number = (double) animation.getAnimatedValue();
                String num = String.format(format , number);
                balanceField.setText( num );
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                String num = String.format(format , after);
                int len = num.length();
                Spannable spanText = new SpannableString(num);
                int colorStart = intSize + 3;
                int sizeStart = intSize + 1;
                spanText.setSpan(new AbsoluteSizeSpan(28, true), sizeStart, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spanText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_bdbdbd)), colorStart, num.length(), 0);
                balanceField.setText(spanText);
                balanceField.setLayerType(LAYER_TYPE_NONE, null);
            }
        });

        animator.setEvaluator(new TypeEvaluator<Double>() { // problem here
            @Override
            public Double evaluate(float fraction, Double startValue, Double endValue) {
                return  (startValue + (double)((endValue - startValue) * fraction));
            }
        });
        animator.setDuration(Hexagon.DURATION);
        animator.start();
    }

    private IFloatPointCallback floatPointCallback = new IFloatPointCallback() {
        @Override
        public void update(float x, float y) {
            float stepX = x - stepField.getMeasuredWidth() / 2 + hexagon.getX();
            float stepY = y - stepField.getMeasuredHeight() / 2 + hexagon.getY();

            float buttonX = stepX - stepField.getMeasuredWidth();
            float buttonY = y - button.getMeasuredHeight() / 2 + hexagon.getY();

            stepField.setX( stepX );
            stepField.setY( stepY );

            button.setX( buttonX );
            button.setY( buttonY );
        }
    };

    //  ======================================================================================

    public void setProgressColor(int value) {
        hexagon.setProgressColor(value);
    }

    public void setBaseColor(int value) {
        hexagon.setBaseColor(value);
    }

    public void setBorderThickness(int value) {
        hexagon.setBorderThickness(value);
    }

    public void setDuration(int value) {
        Hexagon.DURATION = value;
        hexagon.setDuration(value);
    }

    public void setProgress() {
        BaseStepItem item = CacheManager.getInstance().getCurrentItem(mContext);
        BankItem bankItem = CacheManager.getInstance().getBankItem();

        if( bankItem.balance < item.deposit_goal ) {
            hexagon.setProgress(0, item.step_goal);
            hexagon.setBalanceEmpty();
        } else {
            //  BalanceItem balanceItem = CacheManager.getInstance().getBalanceItem();

            if( item.step >= item.step_goal && (item.step_goal != 0) ) {
                hexagon.setProgress(0, item.step_goal);
                hexagon.setGoal();
            } else {
                hexagon.setProgress(item.step, item.step_goal);
            }
        }

        if( stepField != null ) {
            stepField.setText(StringUtil.toNumFormat(item.step));
            double after = BalanceManager.getInstance().getEstimatedBalance(mContext);
            startAnimation( item.balance, after );
        }
    }

    public void setBalance() {
        if( stepField != null ) {
            BaseStepItem item = CacheManager.getInstance().getCurrentItem(mContext);
            double after = BalanceManager.getInstance().getEstimatedBalance(mContext);
            startAnimation( item.balance, after );
        }
    }




}
