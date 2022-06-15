package com.example.g6_whatsnearmeapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.g6_whatsnearmeapp.databinding.ActivityRegisterBinding;
import com.example.g6_whatsnearmeapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                registerUser();
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    void registerUser()
    {
        String newEmail = binding.etNewEmail.getText().toString().trim();
        String newPassword = binding.etNewPassword.getText().toString().trim();

        if(newEmail.isEmpty() || newPassword.isEmpty())
        {
            Log.d("abc","Email/Password empty");
            binding.tvError.setText("Email/Password cannot be left empty");
            //clear input fields
            binding.etNewEmail.setText("");
            binding.etNewPassword.setText("");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())
        {
            Log.d("abc","Email not valid");
            binding.tvError.setText("Please provide a valid Email address");
            //clear input fields
            binding.etNewEmail.setText("");
            binding.etNewPassword.setText("");
            return;
        }
        //check if password is equal to 6 characters
        //because firebase auth requires password to be atleast 6 characters
        if(!(binding.etNewPassword.getText().length() >= 6)){
            Log.d("abc","Password is not 6 characters");
            binding.tvError.setText("Password should be at least 6 characters");
            //clear input fields
            binding.etNewEmail.setText("");
            binding.etNewPassword.setText("");
            return;
        }
        binding.tvError.setText("");

        mAuth.createUserWithEmailAndPassword(newEmail, newPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            User user = new User(newEmail,newPassword);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            //clear input fields
                                            binding.etNewEmail.setText("");
                                            binding.etNewPassword.setText("");
                                            showLoginScreen();
                                        }
                                    });
                        }
                        else if (!task.isSuccessful()) {
                            Log.e("Register", "onComplete: Failed=" + task.getException().getMessage());
                            Toast.makeText(RegisterActivity.this,"Failed to register",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        //clear input fields
        binding.etNewEmail.setText("");
        binding.etNewPassword.setText("");
    }

    void showLoginScreen()
    {
        Intent intent = new Intent (this, LoginActivity.class);
        startActivity(intent);
    }

}