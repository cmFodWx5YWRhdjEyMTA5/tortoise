package com.android.kreators.tortoise.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.kreators.tortoise.R;
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

/**
 * Created by rrobbie on 2017. 8. 26..
 */
public class PlaceAutoCompleteAdapter extends RecyclerView.Adapter<PlaceAutoCompleteAdapter.PlaceViewHolder> implements Filterable{

    private Context mContext;

    private PlaceAutoCompleteInterface mListener;
    private ArrayList<PlaceAutoComplete> mResultList;

    private GoogleApiClient mGoogleApiClient;

    private int layout;
    private LayoutInflater layoutInflater;
    private AutocompleteFilter mPlaceFilter;

    //  ======================================================================================

    public PlaceAutoCompleteAdapter(Context context, int resource, GoogleApiClient googleApiClient, AutocompleteFilter filter, PlaceAutoCompleteInterface mListener) {
        mContext = context;
        layout = resource;
        mGoogleApiClient = googleApiClient;
        mPlaceFilter = filter;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mListener = mListener;
    }

    public void clearList(){
        if(mResultList!=null && mResultList.size() > 0 ){
            mResultList.clear();
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

    private ArrayList<PlaceAutoComplete> getAutocomplete(CharSequence constraint) {
        if (mGoogleApiClient.isConnected()) {

            PendingResult<AutocompletePredictionBuffer> results = Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, constraint.toString(), null, mPlaceFilter);
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
                resultList.add(new PlaceAutoComplete(prediction.getPlaceId(), prediction.getPrimaryText(null).toString() ));
            }

            autocompletePredictions.release();
            return resultList;
        }
        log.show("Google API client is not connected for autocomplete query.");
        return null;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View convertView = layoutInflater.inflate(layout, viewGroup, false);
        PlaceViewHolder mPredictionHolder = new PlaceViewHolder(convertView);
        return mPredictionHolder;
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder mPredictionHolder, final int i) {
        mPredictionHolder.address.setText(mResultList.get(i).description);

        mPredictionHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPlaceClick(mResultList,i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mResultList != null)
            return mResultList.size();
        else
            return 0;
    }

    public PlaceAutoComplete getItem(int position) {
        return mResultList.get(position);
    }

    //  =======================================================================================

    public class PlaceViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout parentLayout;
        public TextView address;

        public PlaceViewHolder(View itemView) {
            super(itemView);

            parentLayout = (RelativeLayout)itemView.findViewById(R.id.predictedRow);
            address = (TextView)itemView.findViewById(R.id.addressField);
        }

    }

    //  ======================================================================================

    public interface PlaceAutoCompleteInterface{
        public void onPlaceClick(ArrayList<PlaceAutoComplete> mResultList, int position);
    }

    //  ======================================================================================

    public class PlaceAutoComplete {

        public String placeId;
        public String description;

        public PlaceAutoComplete(String placeId, String description) {
            this.placeId = placeId;
            this.description = description;
        }

        @Override
        public String toString() {
            return description.toString();
        }

    }



}