package com.example.covidvaccinationapplication1.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.covidvaccinationapplication1.R;
import com.example.covidvaccinationapplication1.databinding.ActivityAddCategoryBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddCategory extends AppCompatActivity {

    private ActivityAddCategoryBinding binding;

    private ProgressBar progressBar;

    private FirebaseAuth auth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        progressBar = new ProgressBar(AddCategory.this);

        if(user!=null){
            binding.subTitleTv.setText(user.getEmail());
        }

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = binding.edtLocation.getText().toString().trim();
                String picCode = binding.edtPincode.getText().toString().trim();
                String date = binding.edtDate.getText().toString().trim();
                String time = binding.edtTime.getText().toString().trim();

                if(!TextUtils.isEmpty(location) && !TextUtils.isEmpty(picCode) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(time)){
                    addCategoryFirebase(location,picCode,date,time);
                }
                else{
                    Toast.makeText(AddCategory.this, "Enter all required fields!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    private void addCategoryFirebase(String location, String pinCode, String date, String time) {

        long timestamp = System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("location",""+location);
        hashMap.put("pinCode",pinCode);
        hashMap.put("date", ""+date);
        hashMap.put("time", ""+time);
        hashMap.put("timestamp", ""+timestamp);
//        hashMap.put("uid", ""+auth.getUid());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("VaccineSlots");
        databaseReference.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                        progressDialog.dismiss();
//                        Toast.makeText(CategoryAddActivity.this, "Category added successfully...", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        progressDialog.dismiss();
//                        Toast.makeText(CategoryAddActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}