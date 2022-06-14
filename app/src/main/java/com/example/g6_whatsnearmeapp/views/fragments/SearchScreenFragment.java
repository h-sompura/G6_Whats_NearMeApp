package com.example.g6_whatsnearmeapp.views.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.g6_whatsnearmeapp.R;
import com.example.g6_whatsnearmeapp.adapters.BusinessItemAdapter;
import com.example.g6_whatsnearmeapp.databinding.FragmentBusinessDetailBinding;
import com.example.g6_whatsnearmeapp.databinding.FragmentSearchScreenBinding;
import com.example.g6_whatsnearmeapp.models.Business;
import com.example.g6_whatsnearmeapp.models.BusinessContainer;
import com.example.g6_whatsnearmeapp.repositories.API.RetrofitClient;
import com.example.g6_whatsnearmeapp.viewmodels.HomeScreenModel;
import com.example.g6_whatsnearmeapp.views.HomeScreen;
import com.example.g6_whatsnearmeapp.views.LoginActivity;
import com.example.g6_whatsnearmeapp.views.OnBusinessClicked;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchScreenFragment extends Fragment implements OnBusinessClicked {

    private FragmentSearchScreenBinding binding;
    private HomeScreenModel homeScreenModel;
    private double currentLong;
    private double currentLat;
    private ArrayList<Business> businessList = new ArrayList<>();
    private BusinessItemAdapter adapter;
    private String currentUserEmail = "";
    private List<Business> spBusinessList;
    private Business currentBusiness;


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

        this.adapter = new BusinessItemAdapter(this.getContext(), this.businessList, this::onRowClicked);

        this.binding.rvBusinessItems.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.binding.rvBusinessItems.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        this.binding.rvBusinessItems.setAdapter(this.adapter);

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
                getBusinessList(searchedTerm,currentLat,currentLong);

                //call api after getting the searched term and location!
            }
        });
    }

    @Override
    public void onRowClicked(Business business) {
        Log.d("abc","Business id:" + business.getBusinessId());
        NavDirections action = SearchScreenFragmentDirections.actionSearchScreenFragmentToBusinessDetail(business);
        Navigation.findNavController(getView()).navigate(action);
    }


    private void getBusinessList(String term, Double lat, Double lon){
        String yelpApiKey = getResources().getString(R.string.yelp_api_key);

        Call<BusinessContainer> businessCall = RetrofitClient.getInstance().getApi().getBusinessesUsingLatLon(yelpApiKey,term,lat,lon);
        this.binding.tvError.setText(""); //clear out the error
        try{

            businessCall.enqueue(new Callback<BusinessContainer>() {
                @Override
                public void onResponse(Call<BusinessContainer> call, Response<BusinessContainer> response) {

//                    if (response.isSuccessful()){
//
//                    }
                    Log.d("api",call.request().url().toString());
                    if (response.code() == 200){
                        //extract the data
                        BusinessContainer mainResponse = response.body();
                        Log.d("api", "onResponse: Received Response : " + mainResponse.toString());
                        Log.d("api", "onResponse: Number of Businesses : " + mainResponse.getBusinessList().size());
                        Log.d("api", "onResponse: BusinessesList : " + mainResponse.getBusinessList().toString());

                        if (mainResponse.getBusinessList().isEmpty()){
                            Log.e("api", "onResponse: No Businesses received");
                            //clear the business list
                            businessList.clear();
                            adapter.notifyDataSetChanged();
                            binding.tvError.setText("No items found based on your search! :(");
                            binding.tvError.setTextColor(Color.parseColor("red"));
                        }else{
                            businessList.clear();

                            for (int i=0 ; i < mainResponse.getBusinessList().size(); i++){
                                Log.d("api", "onResponse: BusinessesList objects " + mainResponse.getBusinessList().get(i).toString());
                                businessList.add(mainResponse.getBusinessList().get(i));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }else{
                        Log.e("api", "onResponse: Unsuccessful response " + response.code() + response.errorBody() );
                    }

                    call.cancel();
                }

                @Override
                public void onFailure(Call<BusinessContainer> call, Throwable t) {
                    call.cancel();
                    Log.e("api", "onFailure: Failed to get the BusinessesList from API" + t.getLocalizedMessage() );
                }
            });


        }catch(Exception ex){
            Log.e("api", "getBusinessList: Cannot retrieve Businesses list " + ex.getLocalizedMessage() );
        }
    }
}