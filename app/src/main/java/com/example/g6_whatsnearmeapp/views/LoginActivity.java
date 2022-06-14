package com.example.g6_whatsnearmeapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.g6_whatsnearmeapp.databinding.ActivityLoginBinding;
import com.example.g6_whatsnearmeapp.repositories.firebase.UserRepository;
import com.example.g6_whatsnearmeapp.viewmodels.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity
{

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    private UserRepository userRepository;
    private String currentEmail = "";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setting up the binding variable
        this.binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.loginViewModel = LoginViewModel.getInstance(this.getApplication());
        this.userRepository = this.loginViewModel.getUserRepository();
        mAuth = FirebaseAuth.getInstance();

        //register button click handler
        binding.registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        //login button click handler
        binding.loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                authenticateUser();
            }
        });
    }

    void authenticateUser()
    {
        //grab the input fields
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();

        if(email.isEmpty() || password.isEmpty())
        {
            Log.d("abc","Email/Password empty");
            binding.tvError.setText("Email/Password cannot be left empty");
            //clear input fields
            binding.etEmail.setText("");
            binding.etPassword.setText("");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Log.d("abc","Email not valid");
            binding.tvError.setText("Please provide a valid Email address");
            //clear input fields
            binding.etEmail.setText("");
            binding.etPassword.setText("");
            return;
        }

        currentEmail = email;
        //save email to preferences
        SharedPreferences sharedPreferences = getSharedPreferences("EmailPreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("USER_EMAIL",email);
        editor.apply();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this,"Login Successful!",Toast.LENGTH_LONG).show();
                            //clear input fields
                            binding.etEmail.setText("");
                            binding.etPassword.setText("");
                            goToHomeScreen();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"Failed to Login",Toast.LENGTH_LONG).show();
                            binding.tvError.setText("Username or Password is wrong");
                        }
                    }
                });
        //clear input fields
        binding.etEmail.setText("");
        binding.etPassword.setText("");
    }

    void goToHomeScreen()
    {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    public String sendDataToFragment()
    {
        return currentEmail;
    }

}