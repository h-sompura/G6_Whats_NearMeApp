package com.example.g6_whatsnearmeapp.repositories.API;

import com.example.g6_whatsnearmeapp.models.BusinessContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface YelpAPI {
    //YelpAPI interface class

    //base URL must end with /
    String BASE_URL = "https://api.yelp.com/v3/";

    //HTTP request - Get all businesses based on a search term & location
    @GET("businesses/search")
    Call<String> getBusinessesUsingLocation(
            @Header("Authorization") String authHeader,
            @Query("term") String searchTerm,
            @Query("location") String location
    );

    //HTTP request - Get all businesses based on a search term & latitude and longitude
    @GET("businesses/search")
    Call<BusinessContainer> getBusinessesUsingLatLon(
            @Header("Authorization") String authHeader,
            @Query("term") String searchTerm,
            @Query("latitude") Double latitude,
            @Query("longitude") Double longitude
    );

}//endYelpAPI
