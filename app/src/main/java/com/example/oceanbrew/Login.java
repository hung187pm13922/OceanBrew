package com.example.oceanbrew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {


    EditText mUsername, mPassword;
    Button mLogin, signup;

    DatabaseReference mAccountsDbRef, mCheckUsernameDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup = findViewById(R.id.btn_signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        mUsername = findViewById(R.id.ed_username_login);
        mPassword = findViewById(R.id.ed_password_login);
        mLogin = findViewById(R.id.btn_login);

        mAccountsDbRef = FirebaseDatabase.getInstance().getReference().child("Accounts");

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Vui long nhap day du thong tin!", Toast.LENGTH_SHORT).show();
                } else {
                    mCheckUsernameDbRef =FirebaseDatabase.getInstance().getReference().child("Accounts/"+username);
                    mCheckUsernameDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child("username").exists()) {
                                String _password = snapshot.child("password").getValue().toString();
                                String role = snapshot.child("role").getValue().toString();
                                if (password.equals(_password)) {
                                    if (role.equals("0")) {
                                        startActivity(new Intent(Login.this, menuu.class));
                                    } else if (role.equals("1")){
                                        startActivity(new Intent(Login.this, Admin.class));
                                    }
                                } else {
                                    Toast.makeText(Login.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Login.this, "Username is not existed!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }

            }
        });

    }
}