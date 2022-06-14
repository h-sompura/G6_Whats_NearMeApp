package com.example.g6_whatsnearmeapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g6_whatsnearmeapp.databinding.CustomRowLayoutBinding;
import com.example.g6_whatsnearmeapp.models.Business;
import com.example.g6_whatsnearmeapp.views.OnBusinessClicked;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
        Business item = dataSourceArray.get(holder.getAdapterPosition());
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
            //TODO: update item bindings in rv
            itemBinding.rvBusinessTitle.setText(currBusiness.getBusinessName());
            float rating = Float.parseFloat(currBusiness.getBusinessRating());
            itemBinding.rvBusinessRating.setRating(rating);
            if(currBusiness.isStatus())
            {
                itemBinding.rvBusinessStatus.setText("Open");
            }
            else
            {
                itemBinding.rvBusinessStatus.setText("Closed");
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
