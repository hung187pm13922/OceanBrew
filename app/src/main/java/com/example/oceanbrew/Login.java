package com.example.oceanbrew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    TextView mSignUp;
    EditText mUsername, mPassword;
    Button mLogin;

    DatabaseReference mAccountsDbRef, mCheckUsernameDbRef;
    SharedPreferences sharedPreferencesUser;
    SharedPreferences sharedPreferencesAdmin;
    SharedPreferences.Editor editorUser;
    SharedPreferences.Editor editorAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferencesUser  = getSharedPreferences("session_user", Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(sharedPreferencesUser.getString("session_username", ""))) {
            startActivity(new Intent(this, menuu.class));
            finish();
        }

        sharedPreferencesAdmin = getSharedPreferences("session_admin", Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(sharedPreferencesAdmin.getString("session_admin_username",""))){
            startActivity(new Intent(this, Admin.class));
            finish();
        }


        mSignUp = findViewById(R.id.btn_signup);
        mSignUp.setOnClickListener(new View.OnClickListener() {
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

                if (TextUtils.isEmpty(username)) {
                    mUsername.setError("Username required!");
                    mUsername.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password required!");
                    mPassword.requestFocus();
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
                                        sharedPreferencesUser = getSharedPreferences("session_user", Context.MODE_PRIVATE);
                                        editorUser = sharedPreferencesUser.edit();
                                        editorUser.putString("session_username",username);
                                        editorUser.commit();
                                        startActivity(new Intent(Login.this, menuu.class));
                                        finish();
                                    } else if (role.equals("1")){
                                        startActivity(new Intent(Login.this,Admin.class));
                                        sharedPreferencesAdmin = getSharedPreferences("session_admin", Context.MODE_PRIVATE);
                                        editorAdmin = sharedPreferencesAdmin.edit();
                                        editorAdmin.putString("session_admin_username",username);
                                        editorAdmin.commit();
                                        finish();
                                    }
                                } else {
                                    mPassword.setError("Incorrect password!");
                                    mPassword.requestFocus();
                                }
                            } else {
                                mUsername.setError("Username is not existed!");
                                mUsername.requestFocus();
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