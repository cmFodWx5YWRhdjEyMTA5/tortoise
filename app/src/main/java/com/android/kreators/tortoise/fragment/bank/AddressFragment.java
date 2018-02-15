package com.android.kreators.tortoise.fragment.bank;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.adapter.AddressAdapter;
import com.android.kreators.tortoise.adapter.recyclerview.PlaceAutoCompleteAdapter;
import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.fragment.info.BaseInfoFragment;
import com.android.kreators.tortoise.model.auth.AuthUserItem;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.kreators.tortoise.model.place.PlaceItem;
import com.android.nobug.util.ObjectUtil;
import com.android.nobug.util.log;
import com.android.nobug.view.listview.BaseListView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddressFragment extends BaseInfoFragment implements
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,AdapterView.OnItemClickListener {

    protected TextInputLayout address1InputLayout;
    protected TextInputLayout address2InputLayout;

    protected EditText address1Field;
    protected EditText address2Field;

    protected BaseListView listView;
    protected AddressAdapter adapter;

    protected GoogleApiClient googleApiClient;
    protected Geocoder geocoder;

    //  =====================================================================================

    @Override
        public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
        public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    //  ======================================================================================

    @Override
    public int getLayoutContentView() {
        return R.layout.fragment_info_user_address;
    }

    @Override
    public void createChildren() {
        super.createChildren();

        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        listView = (BaseListView) mView.findViewById(R.id.listView);
    }

    @Override
    public void setProperties() {
        super.setProperties();

        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        buildClient();

        AutocompleteFilter typeFilter =
                new AutocompleteFilter.Builder()
                        .setCountry("US")
                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_GEOCODE).build();

        adapter = new AddressAdapter(getActivity(), googleApiClient, typeFilter);
        listView.setAdapter(adapter);
    }

    @Override
    public void configureListener() {
        super.configureListener();

        listView.setOnItemClickListener(this);
    }

    //  ======================================================================================

    private void buildClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();
    }

    private void reset() {
        //  inputField.setText("");

        if(adapter!=null){
            adapter.clearList();
        }
    }

    private PlaceItem getCityNameByCoordinates(double lat, double lon) throws IOException {
        List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            PlaceItem item = new PlaceItem();

            //  log.show( "get city name : " +  );

            item.address_country_code = address.getCountryCode();
            item.address_postal_code = address.getPostalCode();
            item.address_subdivision = address.getAdminArea();
            item.address_city = address.getLocality();
            /*
                address_street          : Address 1 + 2
                address_city            : mLocality,
                address_subdivision     : mAdminArea
                address_postal_code     : postal_code
                address_country_code    : getCountryCode() : US
            */
            return item;
        }
        return null;
    }

    //  ======================================================================================

    @Override
    public void onConnected(@Nullable Bundle bundle) {}

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            AddressAdapter.ViewHolder holder = (AddressAdapter.ViewHolder)view.getTag();

            final String placeId = String.valueOf(holder.item.placeId);
            final PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(googleApiClient, placeId);

            placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                @Override
                public void onResult(PlaceBuffer places) {
                    try {
                        Place place = places.get(0);
                        PlaceItem placeItem = getCityNameByCoordinates( place.getLatLng().latitude, place.getLatLng().longitude );
                        UserCacheItem cacheItem = PreferenceFactory.getUserItem(getActivity());
                        cacheItem.authUserItem.placeItem = placeItem;
                        PreferenceFactory.setUserItem(getActivity(), cacheItem);

                        ObjectUtil.toProperties(placeItem);


                    } catch (Exception e) {
                        e.printStackTrace();
                    };
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  =====================================================================================

    protected class PlaceTextWatcher implements TextWatcher {

        //  ===================================================================================

        public PlaceTextWatcher() {}

        //  ===================================================================================

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable editable) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count > 0) {
                listView.setVisibility(View.VISIBLE);
                if (adapter != null) {
                    listView.setAdapter(adapter);
                }
            } else {
                listView.setVisibility(View.GONE);
            }

            if( googleApiClient.isConnected() ) {
                String input = s.toString().trim();
                if( !input.equals("") ) {
                    adapter.getFilter().filter(input);
                }
            } else {
                Toast.makeText(getActivity(), "google api client is not connected", Toast.LENGTH_SHORT).show();
            }
        }

    }



}
