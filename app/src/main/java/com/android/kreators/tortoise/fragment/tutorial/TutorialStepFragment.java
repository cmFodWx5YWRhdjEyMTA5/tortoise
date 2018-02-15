package com.android.kreators.tortoise.fragment.tutorial;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.constants.EventType;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.event.IndexEvent;
import com.android.kreators.tortoise.factory.IntentFactory;
import com.android.nobug.core.BaseFragment;
import com.android.nobug.event.provider.BusProvider;
import com.android.nobug.util.DeprecationUtil;
import com.android.nobug.util.DisplayUtil;
import com.android.nobug.util.log;
import com.bumptech.glide.Glide;

/**
 * Created by rrobbie on 2017. 4. 18..
 */

public class TutorialStepFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout container;
    private LinearLayout layer;
    private ImageView imageView;
    private TextView titleField;
    private TextView descriptionField;
    private Button createAccountButton;
    private Button signInButton;
    private LottieAnimationView animationView;

    private int position = 0;

    //  =======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_tutorial_step;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        container = (RelativeLayout) mView.findViewById(R.id.container);
        layer = (LinearLayout) mView.findViewById(R.id.layer);
        imageView = (ImageView) mView.findViewById(R.id.imageView);
        animationView = (LottieAnimationView) mView.findViewById(R.id.animationView);
        titleField = (TextView) mView.findViewById(R.id.titleField);
        descriptionField = (TextView) mView.findViewById(R.id.descriptionField);

        createAccountButton = (Button) mView.findViewById(R.id.createAccountButton);
        signInButton = (Button) mView.findViewById(R.id.signInButton);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        update();
    }

    @Override
    public void configureListener() {
        super.configureListener();

        createAccountButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }

    //  =======================================================================================

    private void update() {
        if( getArguments() != null ) {
            position = getArguments().getInt(IntentProperty.POSITION);
            int image = getArguments().getInt(IntentProperty.IMAGE);
            int background = getArguments().getInt(IntentProperty.BACKGROUND);
            String title = getArguments().getString(IntentProperty.TITLE);
            String descrption = getArguments().getString(IntentProperty.DESCRIPTION);

            if( position == 0 ) {
                imageView.setVisibility(View.INVISIBLE);
                animationView.setVisibility(View.VISIBLE);
                createAccountButton.setVisibility(View.VISIBLE);
                signInButton.setVisibility(View.VISIBLE);

                animationView.setAnimation("Image.json");
                animationView.loop(true);
                animationView.playAnimation();
            } else if( position == 5 ) {
                layer.setVisibility(View.VISIBLE);
                createAccountButton.setVisibility(View.VISIBLE);
            }

            DeprecationUtil.setBackground(getActivity(), container, background);
            Glide.with(getActivity()).load( image ).into(imageView);
            titleField.setText(title);
            descriptionField.setText(descrption);
        }
    }

    //  =======================================================================================

    private void createAccount() {
        if( position == 0 ) {
            BusProvider.getInstance().post(new IndexEvent(EventType.EMAIL_SIGN_UP, 1));
        } else {
            IntentFactory.createAccount(getActivity());
        }
    }

    private void signIn() {
        IntentFactory.signIn(getActivity());
    }

    //  =======================================================================================

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createAccountButton:
                createAccount();
                break;

            case R.id.signInButton:
                signIn();
                break;
        }
    }




}
