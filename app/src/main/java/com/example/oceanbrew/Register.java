package com.example.oceanbrew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    EditText mUsername, mPassword, mRePassword;
    Button mRegister;

    DatabaseReference mAccountsDbRef, mCheckUsernameDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        mUsername = findViewById(R.id.ed_username_signup);
        mPassword = findViewById(R.id.ed_password_signup);
        mRePassword = findViewById(R.id.ed_repassword_signup);
        mRegister = findViewById(R.id.btn_register);

        mAccountsDbRef = FirebaseDatabase.getInstance().getReference().child("Accounts");

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String repassword = mRePassword.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
                    Toast.makeText(Register.this, "Vui long nhap day du thong tin!", Toast.LENGTH_SHORT).show();
                } else {
                    mCheckUsernameDbRef = FirebaseDatabase.getInstance().getReference("Accounts/"+username);
                    mCheckUsernameDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child("username").exists()) {
                                Toast.makeText(Register.this, "Username da ton tai!", Toast.LENGTH_SHORT).show();
                            } else {
                                if (password.equals(repassword)) {
                                    Accounts accounts = new Accounts(username, password, "0");
                                    mAccountsDbRef.child(username).setValue(accounts);
                                    Toast.makeText(Register.this, "Account is created!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Register.this, Login.class));
                                } else {
                                    Toast.makeText(Register.this, "Xac nhan password sai!", Toast.LENGTH_SHORT).show();
                                }
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