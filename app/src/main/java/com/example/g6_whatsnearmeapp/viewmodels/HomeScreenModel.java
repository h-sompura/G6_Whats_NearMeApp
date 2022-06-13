package com.example.g6_whatsnearmeapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class HomeScreenModel extends AndroidViewModel {
    private static HomeScreenModel instance;
    private final String TAG = this.getClass().getCanonicalName();
    private double userLatitude;
    private double userLongitude;

    public HomeScreenModel(@NonNull Application application) {
        super(application);
    }
    public static HomeScreenModel getInstance(Application application){
        //singleton
        if (instance == null){
            instance = new HomeScreenModel(application);
        }
        return instance;
    }

    public void saveCurrentLocation(double latitude, double longitude){
        this.userLatitude = latitude;
        this.userLongitude = longitude;
    }

    public double getCurrentLatitude(){
        return this.userLatitude;
    }
    public double getCurrentLongitude(){
        return this.userLongitude;
    }
}
