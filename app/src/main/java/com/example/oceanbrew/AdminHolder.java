package com.example.oceanbrew;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.oceanbrew.ui.home.ActionPost;

public class AdminHolder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_holder);

        getSupportFragmentManager().beginTransaction().replace(R.id.body_container,new ActionPost()).commit();
    }
}