package com.example.covidvaccinationapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidvaccinationapplication1.Admin.AdDashboard;
import com.example.covidvaccinationapplication1.User.UsDashboard;
import com.example.covidvaccinationapplication1.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        user= auth.getCurrentUser();

        checkUser();

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.txtEmailLogin.getText().toString().trim();
                String password = binding.txtPasswordLogin.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(MainActivity.this, "Invalid email pattern..!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity.this, "Enter password..", Toast.LENGTH_SHORT).show();
                }
                else {
                    signIn(email, password);
                }
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.txtEmailSignUp.getText().toString().trim();
                String password = binding.txtPasswordSignUp.getText().toString().trim();
                String username = binding.txtUsernameSignUp.getText().toString().trim();
                if(!email.equals("") && ! password.equals("") && !username.equals("")){
                    signUp(email,password,username);
                }else{
                    Toast.makeText(MainActivity.this, "Invalid Entry!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ResetActivity.class));
            }
        });
    }

    void signIn(String email, String password){
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Successfully Login.", Toast.LENGTH_SHORT).show();
                checkUser();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void signUp(String email, String password, String username){
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                updateUserInfo(email, username);
                Toast.makeText(MainActivity.this, "User Created Successfully.", Toast.LENGTH_SHORT).show();
                binding.txtEmailSignUp.setText("");
                binding.txtPasswordSignUp.setText("");
                binding.txtUsernameSignUp.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUser() {
//        progressDialog.setMessage("Checking User...");
        FirebaseUser firebaseUser  = auth.getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        if(firebaseUser != null) {
            databaseReference.child(firebaseUser.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        progressDialog.dismiss();
                            String userType = "" + snapshot.child("userType").getValue();
                            if (userType.equals("user")) {
                                startActivity(new Intent(MainActivity.this, UsDashboard.class));
                                finish();
                            } else if (userType.equals("admin")) {
                                startActivity(new Intent(MainActivity.this, AdDashboard.class));
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void updateUserInfo(String email, String username) {
        long timeStamp = System.currentTimeMillis();
        String uId = auth.getUid();
        //setup data to add db
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uId);
        hashMap.put("email", email);
        hashMap.put("name", username);
        hashMap.put("userType", "user");
        hashMap.put("timestamp", timeStamp);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(uId)
                .setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Account created..", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(MainActivity.this, DashboardUser.class));
//                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}