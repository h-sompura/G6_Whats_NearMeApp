package com.example.g6_whatsnearmeapp.views.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.g6_whatsnearmeapp.databinding.FragmentBusinessDetailBinding;
import com.example.g6_whatsnearmeapp.models.Business;
import com.example.g6_whatsnearmeapp.models.Location;
import com.example.g6_whatsnearmeapp.views.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BusinessDetailFragment extends Fragment {

    private FragmentBusinessDetailBinding binding;
    private String currentUserEmail = "";
    private List<Business> spBusinessList = new ArrayList<>();
    private Business currBusiness = new Business();
    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentBusinessDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currBusiness = BusinessDetailFragmentArgs.fromBundle(getArguments()).getBusiness();
        Picasso.get().load(currBusiness.getBusinessImage()).into(binding.ivBusinessImage);
        binding.tvBusinessTitle.setText(currBusiness.getBusinessName());
        List<String> categoryTitles = new ArrayList<>();
        for(int i=0;i<currBusiness.getCategoriesList().length; i++){
            categoryTitles.add(currBusiness.getCategoriesList()[i].getCategoryTitle());
        }
        String displayCategories = Arrays.toString(categoryTitles.toArray());
        binding.tvBusinessCategories.setText(displayCategories.substring(1,displayCategories.length()-1));
        binding.tvBusinessPriceRating.setText(" ●  "+currBusiness.getBusinessPriceRating());

        if(!currBusiness.isStatus())
        {
            binding.tvOpenStatus.setText("Open");
            binding.tvOpenStatus.setBackgroundColor(Color.parseColor("#11D069")); //green for open
            binding.tvOpenStatus.setTextColor(Color.parseColor("white"));
        }
        else
        {
            binding.tvOpenStatus.setText("Closed");
            binding.tvOpenStatus.setBackgroundColor(Color.parseColor("#EF9087")); //red for closed
            binding.tvOpenStatus.setTextColor(Color.parseColor("white"));
        }

        Location businessAddress = currBusiness.getBusinessLocation();
        String address = businessAddress.getAddress1() + ", " +  businessAddress.getCity() + ", " + businessAddress.getState();

        binding.tvBusinessAddress.setText(address);


        //web handler
        binding.btnViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Detail", "Website button pressed!");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(currBusiness.getBusinessUrl()));
                startActivity(i);
            }
        });

        //fav button handler
        binding.btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Fav", "Favourite button pressed!");
                favoritePressed();
            }
        });
    }

    void favoritePressed()
    {


        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("EmailPreferences",MODE_PRIVATE);
        String currentEmail = sharedPreferences.getString("USER_EMAIL", "");

        if(currentEmail.length() != 0){
            SharedPreferences sp = this.getContext().getSharedPreferences("SharedPreferences",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            Gson gson = new Gson();

            String json = sp.getString(currentEmail, null);

            // below line is to get the type of our array list.
            Type type = new TypeToken<ArrayList<Business>>() {}.getType();

            // in below line we are getting data from gson
            // and saving it to our array list
            List<Business> b = gson.fromJson(json, type);

            if(b == null){
                b =  new ArrayList<>();
            } else {
                spBusinessList = b;
            }
            spBusinessList.add(currBusiness);
            String json2 = gson.toJson(spBusinessList);
            editor.putString(currentEmail,json2);
            editor.apply();
        }
    }
}