package com.example.g6_whatsnearmeapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.g6_whatsnearmeapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), Register.class);
                startActivity(intent);
            }
        });

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
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();

        if(email.isEmpty() || password.isEmpty())
        {
            Log.d("abc","Email/Password empty");
            binding.tvError.setText("Email/Password cannot be left empty");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Log.d("abc","Email not valid");
            binding.tvError.setText("Please provide a valid Email address");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            goToHomeScreen();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Failed to Login",Toast.LENGTH_LONG).show();
                            binding.tvError.setText("Username or Password is wrong");
                        }
                    }
                });
    }

    void goToHomeScreen()
    {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

}