package com.example.g6_whatsnearmeapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g6_whatsnearmeapp.databinding.CustomRowLayoutBinding;
import com.example.g6_whatsnearmeapp.models.Business;
import com.example.g6_whatsnearmeapp.models.Category;
import com.example.g6_whatsnearmeapp.views.OnBusinessClicked;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BusinessItemAdapter extends RecyclerView.Adapter<BusinessItemAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<Business> dataSourceArray;
    CustomRowLayoutBinding binding;
    private final OnBusinessClicked clickListener;

    public BusinessItemAdapter(Context context, ArrayList<Business> businesses, OnBusinessClicked clickListener){
        this.dataSourceArray = businesses;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(CustomRowLayoutBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Business item = dataSourceArray.get(holder.getLayoutPosition());
        holder.bind(context, item, clickListener);
    }

    @Override
    public int getItemCount() {
        Log.d("MyAdapter", "getItemCount: Number of items " +this.dataSourceArray.size() );
        return this.dataSourceArray.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CustomRowLayoutBinding itemBinding;

        public MyViewHolder(CustomRowLayoutBinding binding){
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        public void bind(Context context, Business currBusiness, OnBusinessClicked clickListener){
            //update item bindings in rv
            itemBinding.rvBusinessTitle.setText(currBusiness.getBusinessName());
            float rating = Float.parseFloat(currBusiness.getBusinessRating());
            List<String> categoryTitles = new ArrayList<>();
            for(int i=0;i<currBusiness.getCategoriesList().length; i++){
                categoryTitles.add(currBusiness.getCategoriesList()[i].getCategoryTitle());
            }
            String displayCategories =Arrays.toString(categoryTitles.toArray());

            //String categoryTitle = currBusiness.getCategoriesList()[0].getCategoryTitle(); //just displaying first category of the business
            itemBinding.rvBusinessCategory.setText(displayCategories.substring(1,displayCategories.length()-1));
            itemBinding.rvBusinessRating.setRating(rating);
            if(!currBusiness.isStatus())
            {
                itemBinding.rvBusinessStatus.setText("Open");
                itemBinding.rvBusinessStatus.setBackgroundColor(Color.parseColor("#11D069")); //green for open
                itemBinding.rvBusinessStatus.setTextColor(Color.parseColor("white"));
            }
            else
            {
                itemBinding.rvBusinessStatus.setText("Closed");
                itemBinding.rvBusinessStatus.setBackgroundColor(Color.parseColor("#EF9087")); //red for closed
                itemBinding.rvBusinessStatus.setTextColor(Color.parseColor("white"));
            }

            Picasso.get().load(currBusiness.getBusinessImage()).into(itemBinding.rvBusinessImage);


            itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onRowClicked(currBusiness);
                }
            });
        }
    }
}
