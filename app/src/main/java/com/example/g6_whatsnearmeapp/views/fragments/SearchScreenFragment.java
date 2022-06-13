package com.example.g6_whatsnearmeapp.views.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.g6_whatsnearmeapp.adapters.BusinessItemAdapter;
import com.example.g6_whatsnearmeapp.databinding.FragmentSearchScreenBinding;
import com.example.g6_whatsnearmeapp.models.Business;
import com.example.g6_whatsnearmeapp.viewmodels.HomeScreenModel;
import com.example.g6_whatsnearmeapp.views.HomeScreen;
import com.example.g6_whatsnearmeapp.views.OnRowClicked;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchScreenFragment extends Fragment implements OnRowClicked {

    private FragmentSearchScreenBinding binding;
    private HomeScreenModel homeScreenModel;
    private double currentLong;
    private double currentLat;

    private ArrayList<Business> productsList = new ArrayList<Business>();
    private BusinessItemAdapter adapter;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentSearchScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //viewmodel instance
        homeScreenModel = HomeScreenModel.getInstance(getActivity().getApplication());

        currentLat = homeScreenModel.getCurrentLatitude();
        currentLong = homeScreenModel.getCurrentLongitude();

        try {
            Geocoder geocoder = new Geocoder(this.getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(currentLat,currentLong,1);
            String address = addresses.get(0).getAddressLine(0);
            Log.d("CurrentLocation:", address);
            //get location and display location
            binding.tvLocationName.setText(address);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //viewmodel instance
        homeScreenModel = HomeScreenModel.getInstance(getActivity().getApplication());

        currentLat = homeScreenModel.getCurrentLatitude();
        currentLong = homeScreenModel.getCurrentLongitude();

        try {
            Geocoder geocoder = new Geocoder(this.getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(currentLat,currentLong,1);
            String address = addresses.get(0).getAddressLine(0);
            Log.d("CurrentLocation:", address);
            //get location and display location
            binding.tvLocationName.setText(address);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        //viewmodel instance
        homeScreenModel = HomeScreenModel.getInstance(getActivity().getApplication());

        currentLat = homeScreenModel.getCurrentLatitude();
        currentLong = homeScreenModel.getCurrentLongitude();

        try {
            Geocoder geocoder = new Geocoder(this.getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(currentLat,currentLong,1);
            String address = addresses.get(0).getAddressLine(0);
            Log.d("CurrentLocation:", address);
            //get location and display location
            binding.tvLocationName.setText(address);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //viewmodel instance
        homeScreenModel = HomeScreenModel.getInstance(getActivity().getApplication());

        currentLat = homeScreenModel.getCurrentLatitude();
        currentLong = homeScreenModel.getCurrentLongitude();

        try {
            Geocoder geocoder = new Geocoder(this.getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(currentLat,currentLong,1);
            String address = addresses.get(0).getAddressLine(0);
            Log.d("CurrentLocation:", address);
            //get location and display location
            binding.tvLocationName.setText(address);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //viewmodel instance
        homeScreenModel = HomeScreenModel.getInstance(getActivity().getApplication());

        currentLat = homeScreenModel.getCurrentLatitude();
        currentLong = homeScreenModel.getCurrentLongitude();

        try {
            Geocoder geocoder = new Geocoder(this.getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(currentLat,currentLong,1);
            String address = addresses.get(0).getAddressLine(0);
            Log.d("CurrentLocation:", address);
            //get location and display location
            binding.tvLocationName.setText(address);
        }catch (Exception e){
            e.printStackTrace();
        }

        //search button handler
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchedTerm = binding.etSearchTerm.getText().toString();
                Log.d("Search", searchedTerm);

                //call api after getting the searched term and location!
            }
        });
    }

    @Override
    public void onRowClicked(Business product) {

    }
}