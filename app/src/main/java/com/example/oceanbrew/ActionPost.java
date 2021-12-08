package com.example.oceanbrew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.oceanbrew.model.Drinks;
import com.example.oceanbrew.model.Posts;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ActionPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_post);

        Intent intent = getIntent();
        String garnish = intent.getStringExtra("garnish");
        String ingradients = intent.getStringExtra("ingradients");
        String link = intent.getStringExtra("link");
        String method = intent.getStringExtra("method");
        String nameOfDrinks = intent.getStringExtra("nameOfDrinks");
        String status = intent.getStringExtra("status");
        String typeOfDrinks = intent.getStringExtra("typeOfDrinks");
        String username = intent.getStringExtra("username");
        String whenPost = intent.getStringExtra("whenPost");

        TextView mNameDrinks, mGarnish, mMethol;
        ImageView imageView;
        mNameDrinks = findViewById(R.id.tv_NameDrinks);
        mGarnish = findViewById(R.id.textVj);
        mMethol = findViewById(R.id.textV);
        imageView = findViewById(R.id.iv_imageDrinks);
        mNameDrinks.setText(nameOfDrinks);
        mGarnish.setText(garnish);
        mMethol.setText(method);

        StorageReference reference = FirebaseStorage.getInstance().getReference("images").child(link);
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ActionPost.this).load(uri).into(imageView);
            }
        });

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tblo_I);
        String []arr = ingradients.split(";");

        for(int i = -1; i < arr.length-1; i+=2 ) {
            TableRow row = new TableRow(ActionPost.this);
            TextView tv1 = new TextView(ActionPost.this);
            tv1.setText(arr[i+1]);
            tv1.setTextColor(Color.BLACK);
            TextView tv2 = new TextView(ActionPost.this);
            tv2.setText(arr[i+2]);
            tv2.setTextColor(Color.BLACK);
            row.addView(tv1);
            row.addView(tv2);
            tableLayout.addView(row);
        }

        Button approve;
        approve = findViewById(R.id.approve);
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drinks drinks = new Drinks(typeOfDrinks, garnish, ingradients, link, method, nameOfDrinks, whenPost);
                DatabaseReference add = FirebaseDatabase.getInstance().getReference("Drinks");
                add.push().setValue(drinks);
                removePost(nameOfDrinks, username);
            }
        });

        Button deny;
        deny = findViewById(R.id.deny);
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePost(nameOfDrinks, username);
            }
        });


    }

    public  void removePost(String nameOfDrinks, String username) {
        DatabaseReference remove = FirebaseDatabase.getInstance().getReference("Posts");
        Query del = remove.orderByChild("nameOfDrink").equalTo(nameOfDrinks);
        del.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Posts p = new Posts();
                    p = data.getValue(Posts.class);
                    if (p.getUsername().equals(username)) {
                        data.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        startActivity(new Intent(ActionPost.this, Admin.class));
    }
}