package com.example.g6_whatsnearmeapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.MenuItem;
import android.os.Bundle;

import com.example.g6_whatsnearmeapp.R;
import com.example.g6_whatsnearmeapp.databinding.ActivityHomeScreenBinding;
import com.example.g6_whatsnearmeapp.databinding.ActivityLoginBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;

public class HomeScreen extends AppCompatActivity {

    private ActivityHomeScreenBinding binding;

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
            }


            @Override
            public void onError(@NonNull Status status) {
                Log.i("Places", "An error occurred: " + status);
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
}