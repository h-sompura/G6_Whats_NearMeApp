package com.example.g6_whatsnearmeapp.models;

import com.google.gson.annotations.SerializedName;

public class Category {
    private @SerializedName("title")
    String categoryTitle;
    //getter and toString

    public String getCategoryTitle() {
        return categoryTitle;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryTitle='" + categoryTitle + '\'' +
                '}';
    }
}
