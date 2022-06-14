package com.example.g6_whatsnearmeapp.views.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.g6_whatsnearmeapp.databinding.FragmentFavouritesScreenBinding;
import com.example.g6_whatsnearmeapp.models.Business;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavouritesScreenFragment extends Fragment {

    private FragmentFavouritesScreenBinding binding;
    private ArrayList<Business> businessList = new ArrayList<>();
    private ArrayList<String> businessNames = new ArrayList<>();

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentFavouritesScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvError.setText("");

        ArrayAdapter adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, businessNames);

        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("EmailPreferences",MODE_PRIVATE);
        String currentEmail = sharedPreferences.getString("USER_EMAIL", "");
        Gson gson = new Gson();
        SharedPreferences sp = this.getContext().getSharedPreferences("SharedPreferences",MODE_PRIVATE);
        Type type = new TypeToken<ArrayList<Business>>() {}.getType();
        String json = sp.getString(currentEmail, null);
        ArrayList<Business> b = gson.fromJson(json, type);

        if(b == null){
            binding.tvError.setText("No favourites added yet!");
            businessList.clear();
            b = new ArrayList<>();
            Log.d("ABC",b.toString());
        } else{
            businessList = b;
            for(int i=0;i<businessList.size();i++){
                businessNames.add(businessList.get(i).getBusinessName());
            }
            Log.d("ABC",String.valueOf(b.size()));
            Log.d("ABC",b.toString());
        }

        ListView listView = (ListView) binding.lvFavList;
        listView.setAdapter(adapter);

    }
}