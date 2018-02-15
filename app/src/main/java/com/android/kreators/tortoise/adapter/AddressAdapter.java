package com.android.kreators.tortoise.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.kreators.tortoise.R;
import com.android.kreators.tortoise.model.place.PlaceItem;
import com.android.nobug.util.log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class AddressAdapter extends ArrayAdapter<AddressAdapter.PlaceAddressItem> {

    private Context mContext;
    private ViewHolder viewHolder;

    private LayoutInflater layoutInflater;

    private GoogleApiClient googleApiClient;
    private AutocompleteFilter filter;

    private ArrayList<PlaceAddressItem> mResultList;

    //  ========================================================================================

    public AddressAdapter(Context context, GoogleApiClient googleApiClient, AutocompleteFilter filter) {
        super(context, 0);
        mContext = context;
        this.googleApiClient = googleApiClient;
        this.filter = filter;
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void clearList(){
        if(mResultList!=null && mResultList.size() > 0 ){
            mResultList.clear();
        }
    }

    @Override
    public int getCount() {
        if (mResultList != null) {
            return mResultList.size();
        }
        return 0;
    }

    @Nullable
    @Override
    public PlaceAddressItem getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        final int index = position;

        if (view == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.adapter_address_item, null, false);
            viewHolder.field = (TextView) view.findViewById(R.id.field);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        update(viewHolder, index);
        return view;
    }

    private void update(ViewHolder viewHolder, int position) {
        try {
            PlaceAddressItem item = mResultList.get(position);
            viewHolder.item = item;
            viewHolder.field.setText(item.description);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null) {
                    mResultList = getAutocomplete(constraint.toString());
                    if (mResultList != null) {
                        results.values = mResultList;
                        results.count = mResultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    //  notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    private ArrayList<PlaceAddressItem> getAutocomplete(CharSequence constraint) {
        if (googleApiClient.isConnected()) {

            PendingResult<AutocompletePredictionBuffer> results = Places.GeoDataApi.getAutocompletePredictions(googleApiClient, constraint.toString(), null, filter);
            AutocompletePredictionBuffer autocompletePredictions = results.await(60, TimeUnit.SECONDS);

            final Status status = autocompletePredictions.getStatus();

            if (!status.isSuccess()) {
                Toast.makeText(mContext, "Error contacting API: " + status.toString(), Toast.LENGTH_SHORT).show();
                log.show("Error getting autocomplete prediction API call: " + status.toString());
                autocompletePredictions.release();
                return null;
            }

            Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
            ArrayList resultList = new ArrayList<>();

            while (iterator.hasNext()) {
                AutocompletePrediction prediction = iterator.next();
                resultList.add(new PlaceAddressItem(prediction.getPlaceId(), prediction.getPrimaryText(null).toString() ));
            }

            autocompletePredictions.release();
            return resultList;
        }
        log.show("Google API client is not connected for autocomplete query.");
        return null;
    }

    // =======================================================================================

    public class ViewHolder {

        public TextView field;
        public PlaceAddressItem item;

    }

    // =======================================================================================

    public class PlaceAddressItem {

        public String placeId;
        public String description;

        public PlaceAddressItem(String placeId, String description) {
            this.placeId = placeId;
            this.description = description;
        }

        @Override
        public String toString() {
            return description.toString();
        }

    }



}
