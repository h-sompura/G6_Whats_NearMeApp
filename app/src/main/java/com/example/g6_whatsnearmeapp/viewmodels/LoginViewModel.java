package com.example.g6_whatsnearmeapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.g6_whatsnearmeapp.repositories.firebase.UserRepository;

public class LoginViewModel extends AndroidViewModel {

    //view model for Login
    private final UserRepository repository = new UserRepository();
    private static LoginViewModel instance;
    private final String TAG = this.getClass().getCanonicalName();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public static LoginViewModel getInstance(Application application){
        //singleton
        if (instance == null){
            instance = new LoginViewModel(application);
        }
        return instance;
    }

    public UserRepository getUserRepository(){
        return this.repository;
    }

}
