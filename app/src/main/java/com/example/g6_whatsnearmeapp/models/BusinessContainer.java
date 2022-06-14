package com.example.g6_whatsnearmeapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BusinessContainer
{
    private @SerializedName("businesses")
    ArrayList<Business> businessList;

    public ArrayList<Business> getBusinessList() {
        return businessList;
    }

    @Override
    public String toString() {
        return "BusinessContainer{" +
                "businessList=" + businessList +
                '}';
    }



}