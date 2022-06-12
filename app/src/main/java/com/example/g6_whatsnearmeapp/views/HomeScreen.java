package com.example.g6_whatsnearmeapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.MenuItem;
import android.os.Bundle;

import com.example.g6_whatsnearmeapp.R;
import com.example.g6_whatsnearmeapp.databinding.ActivityHomeScreenBinding;
import com.example.g6_whatsnearmeapp.databinding.ActivityLoginBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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