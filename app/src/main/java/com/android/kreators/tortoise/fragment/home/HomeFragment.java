package com.android.kreators.tortoise.fragment.home;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.constants.property.IntentProperty;
import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.manager.BalanceManager;
import com.android.kreators.tortoise.manager.CacheManager;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.kreators.tortoise.model.balance.BalanceItemGroup;
import com.android.kreators.tortoise.model.bank.BankItem;
import com.android.kreators.tortoise.model.bank.BankItemGroup;
import com.android.kreators.tortoise.model.friend.FriendGroup;
import com.android.kreators.tortoise.model.step.BaseStepItem;
import com.android.kreators.tortoise.net.RetrofitBuilder;
import com.android.kreators.tortoise.view.hexagon.HexagonBalanceViewer;
import com.android.nobug.util.ObjectUtil;
import com.android.nobug.util.PreferenceUtil;
import com.android.nobug.util.log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends PermissionFragment implements OnMapReadyCallback, Callback {


    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private HexagonBalanceViewer hexagonBalanceViewer;
    private LocationManager locationManager;

    private long user_seq = 0;
    private boolean isProgressed = false;

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        hexagonBalanceViewer = (HexagonBalanceViewer) mView.findViewById(R.id.hexagonViewer);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        if( checkPermission() ) {
            mapFragment.getMapAsync(this);
        }

        request();
    }

    @Override
    public void configureListener() {
        super.configureListener();

    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

        try {
            user_seq = args.getLong(IntentProperty.SEQ);
            UserCacheItem userCacheItem = (UserCacheItem) args.getSerializable(IntentProperty.FACEBOOK_ITEM);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  ======================================================================================

    public void setProgress() {
        if( isProgressed ) {
            hexagonBalanceViewer.setDuration( BalanceManager.getInstance().getAccelerationTime(getActivity()) );
            hexagonBalanceViewer.setProgress();
        }
    }

    @Override
    protected void granted() {
        super.granted();

        mapFragment.getMapAsync(this);
    }

    //  ======================================================================================

    private void setupLocation() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, mLocationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void request() {
        RetrofitBuilder.withUnsafe(getActivity()).getBalance(user_seq).enqueue(this);
    }

    private void requestBank(BalanceItemGroup balanceItemGroup) {
        BaseStepItem currentStepItem = PreferenceFactory.getCurrentStepItem(getActivity());
        currentStepItem.deposit_goal = balanceItemGroup.data.step.deposit_goal;
        currentStepItem.step_goal = balanceItemGroup.data.step.step_goal;
        PreferenceFactory.setCurrentStepItem(getActivity(), currentStepItem);

        RetrofitBuilder.withUnsafe(getActivity()).getBankItem(user_seq).enqueue(this);
    }

    private void updateSteps(BankItemGroup bankItemGroup) {
        BankItem tortoiseBankItem = bankItemGroup.data.get(0);
        BankItem bankItem = bankItemGroup.data.get(1);
        CacheManager.getInstance().setTortoiseBankItem(tortoiseBankItem);
        CacheManager.getInstance().setBankItem(bankItem);
        hexagonBalanceViewer.setDuration( BalanceManager.getInstance().getAccelerationTime(getActivity()) );
        hexagonBalanceViewer.setProgress();
        isProgressed = true;
    }

    //  ======================================================================================

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if( checkPermission() ) {
            setupLocation();
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            LatLng mapCoordinate = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCoordinate, 16.0f));
            locationManager.removeUpdates(mLocationListener);
        }

        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    private void update(Response response) {
        try {
            if( response.body() instanceof BalanceItemGroup) {
                BalanceItemGroup balanceItemGroup = (BalanceItemGroup) response.body();
                CacheManager.getInstance().setBalanceItem(balanceItemGroup.data);
                requestBank(balanceItemGroup);
            } else {
                BankItemGroup bankItemGroup = (BankItemGroup) response.body();
                updateSteps(bankItemGroup);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  ======================================================================================

    @Override
    public void onResponse(Call call, Response response) {
        update(response);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        log.show( "on failure : " + call.request().url().toString() + " / " + t.getLocalizedMessage() );
    }



}
