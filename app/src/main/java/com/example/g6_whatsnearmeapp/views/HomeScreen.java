package com.example.g6_whatsnearmeapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.g6_whatsnearmeapp.R;
import com.example.g6_whatsnearmeapp.databinding.ActivityHomeScreenBinding;
import com.example.g6_whatsnearmeapp.databinding.ActivityLoginBinding;
import com.example.g6_whatsnearmeapp.models.BusinessContainer;
import com.example.g6_whatsnearmeapp.repositories.API.RetrofitClient;
import com.example.g6_whatsnearmeapp.viewmodels.HomeScreenModel;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

public class HomeScreen extends AppCompatActivity implements LocationListener {

    private ActivityHomeScreenBinding binding;
    LocationManager locationManager;
    private HomeScreenModel homeScreenModel;
    private double getCurrentLat;
    private double getCurrentLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting up the binding variable
        this.binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        //logout button
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_exit_to_app_24);

        //viewmodel instance
        this.homeScreenModel = HomeScreenModel.getInstance(this.getApplication());

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // setup the bottom navigation menu
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = binding.bottomNavView;
        NavigationUI.setupWithNavController(bottomNav, navController);

        //initializing places
        String apiString = getResources().getString(R.string.google_places_api_key);
        Places.initialize(getApplicationContext(), apiString);

        //places client instance
        PlacesClient placesClient = Places.createClient(this);

        //autocomplete
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.ADDRESS,
                Place.Field.LAT_LNG, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                //places.getName will display the name we select
                Log.i("Places", "Place: " + place.getName() + ", " + place.getId());
                getCurrentLat = place.getLatLng().latitude;
                getCurrentLong = place.getLatLng().longitude;

                //saving into viewmodel
                homeScreenModel.saveCurrentLocation(getCurrentLat,getCurrentLong);

            }


            @Override
            public void onError(@NonNull Status status) {
                Log.i("Places", "An error occurred: " + status);
            }
        });

        //Get location permissions from the user
        if (ContextCompat.checkSelfPermission(HomeScreen.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HomeScreen.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }

        //get current location button handler
        binding.btnGetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CurrentLocation:", "Button Clicked!");
                getCurrentLocation();
            }
        });

    }
    // logout handler
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //get current location config
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,HomeScreen.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        getCurrentLat = location.getLatitude();
        getCurrentLong = location.getLongitude();
        //saving into viewmodel
        homeScreenModel.saveCurrentLocation(getCurrentLat, getCurrentLong);
        try {
            Geocoder geocoder = new Geocoder(HomeScreen.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);
            Log.d("CurrentLocation:", address);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }





}