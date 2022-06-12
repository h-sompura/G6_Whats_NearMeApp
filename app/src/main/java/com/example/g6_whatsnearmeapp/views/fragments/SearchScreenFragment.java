package com.example.g6_whatsnearmeapp.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.g6_whatsnearmeapp.databinding.FragmentSearchScreenBinding;

public class SearchScreenFragment extends Fragment {

    private FragmentSearchScreenBinding binding;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentSearchScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}