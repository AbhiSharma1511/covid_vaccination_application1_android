package com.example.covidvaccinationapplication1.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.covidvaccinationapplication1.MainActivity;
import com.example.covidvaccinationapplication1.R;
import com.example.covidvaccinationapplication1.databinding.ActivityAdDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdDashboard extends AppCompatActivity {

    private ActivityAdDashboardBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(user!=null) {
            binding.subTitleTv.setText(user.getEmail());
        }

        binding.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.logoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(AdDashboard.this, MainActivity.class));
            }
        });

        binding.addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdDashboard.this, AddCategory.class));
            }
        });

    }
}