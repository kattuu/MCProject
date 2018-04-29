package io.github.httpsphoenix30.mcproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.ArrayList;
import java.util.Collections;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class Add_location_2015095 extends Fragment {
    private  RecyclerView locationRecycler;
    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 101;
    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS).build();
    LocationAdapter_2015095 adapter_2015095;
    private ArrayList<Boolean> list;
    private ArrayList<String> namesList;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup,Bundle bundle){
        View view = inflater.inflate(R.layout.fragment_add_location_2015095,viewGroup,false);

        locationRecycler = (RecyclerView) view.findViewById(R.id.locations_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        locationRecycler.setLayoutManager(linearLayoutManager);
       // list =new ArrayList<>(20);
        namesList =new ArrayList<>(20);
        adapter_2015095 =new LocationAdapter_2015095(namesList);
        locationRecycler.setAdapter(adapter_2015095);
        locationRecycler.addOnItemTouchListener(new RecyclerTouchListener_2015095(getActivity(), locationRecycler, new RecyclerTouchListener_2015095.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                adapter_2015095.current = position;
                adapter_2015095.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPlace();
            }
        });
        return view;
    }



        private void findPlace() {

            try {
                Intent intent =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                .setFilter(typeFilter)
                                .build(getActivity());
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException e) {
                // TODO: Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                // TODO: Handle the error.
            }
        }

        // A place has been received; use requestCode to track the request.
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                    namesList.add(0,place.getName().toString());
                    adapter_2015095.current = 0;
                    adapter_2015095.notifyDataSetChanged();

                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                    // TODO: Handle the error.

                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }
            }
        }

    public class LocationAdapter_2015095 extends RecyclerView.Adapter<LocationAdapter_2015095.RecyclerViewHolder>  {

        // private ArrayList<Boolean> radioArray = new ArrayList<>(20);
        private ArrayList<String> names = new ArrayList<>(20);
        public int current;
        public RecyclerTouchListener_2015095.ClickListener onClickListener;



        public LocationAdapter_2015095(ArrayList<String> names){
            this.names =names;
            current = 0;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_location, parent, false);
            return new RecyclerViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
            Log.d("position","  "+ position);
            if(position == current){
                holder.radioButton.setChecked(true);
            }
            else{
                holder.radioButton.setChecked(false);
            }
           holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    names.remove(holder.getAdapterPosition());
                    adapter_2015095.notifyDataSetChanged();
                }
            });
        }


        @Override
        public int getItemCount() {
            return names.size();
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder{
            RadioButton radioButton ;
            Button deleteButton;
            TextView locationText;

            public RecyclerViewHolder(final View itemView) {
                super(itemView);
                radioButton =itemView.findViewById(R.id.radioButton);
                deleteButton =itemView.findViewById(R.id.deleteCard);
                locationText =itemView.findViewById(R.id.locationTextview);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(v, getAdapterPosition());
                    }
                });
            }

           /* @Override
            public void onClick(View v) {
                if(v.getId()==R.id.deleteCard) {
                    Log.d("ghg", ""+getAdapterPosition());
                    names.remove(getAdapterPosition());
                    adapter_2015095.notifyDataSetChanged();
                }
            }*/
        }
    }


    }



