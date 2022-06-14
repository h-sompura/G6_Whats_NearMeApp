package com.example.g6_whatsnearmeapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Business implements Serializable
{
    private @SerializedName("id") String businessId;
    private @SerializedName("price") String businessPriceRating;
    private @SerializedName("name") String businessName;
    private @SerializedName("image_url") String businessImage;
    private @SerializedName("url") String businessUrl;
    private @SerializedName("rating") String businessRating;
    private @SerializedName("distance") String businessDistance;
    private @SerializedName("categories") Category[] categoriesList;
    private @SerializedName("location") Location businessLocation;
    private @SerializedName("is_closed") boolean Status;

    public Location getBusinessLocation() {
        return businessLocation;
    }

    public String getBusinessPriceRating() {
        return businessPriceRating;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessImage() {
        return businessImage;
    }

    public String getBusinessUrl() {
        return businessUrl;
    }

    public String getBusinessRating() {
        return businessRating;
    }

    public String getBusinessDistance() {
        return businessDistance;
    }

    public boolean isStatus() {
        return Status;
    }

    public Category[] getCategoriesList() {
        return categoriesList;
    }

    @Override
    public String toString() {
        return "Business{" +
                "businessId='" + businessId + '\'' +
                ", businessName='" + businessName + '\'' +
                ", businessImage='" + businessImage + '\'' +
                ", businessUrl='" + businessUrl + '\'' +
                ", businessRating='" + businessRating + '\'' +
                ", businessDistance='" + businessDistance + '\'' +
                ", Status=" + Status +
                '}';
    }
}
