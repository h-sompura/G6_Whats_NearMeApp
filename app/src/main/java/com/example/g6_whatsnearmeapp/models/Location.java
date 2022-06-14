package com.example.g6_whatsnearmeapp.models;

import com.google.gson.annotations.SerializedName;

public class Location {
    private @SerializedName("address1")
    String address1;
    private @SerializedName("city")
    String city;
    private @SerializedName("state")
    String state;

    public String getAddress1() {
        return address1;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }
}
