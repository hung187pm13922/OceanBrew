package com.example.oceanbrew.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oceanbrew.ActionPostFragment;
import com.example.oceanbrew.DrinksDetailFragment;
import com.example.oceanbrew.R;
import com.example.oceanbrew.model.Posts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdminAdapter extends FirebaseRecyclerAdapter<Posts, AdminAdapter.myViewHolder> {

    public AdminAdapter(@NonNull FirebaseRecyclerOptions<Posts> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Posts model) {
        holder.mUsername.setText(model.getUsername());
        holder.mCate.setText(model.getTypeOfDrinks());
        holder.mNameDrinks.setText(model.getNameOfDrink());
        StorageReference reference = FirebaseStorage.getInstance().getReference("images").child(model.getLink());
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.mImagePost);
            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction().replace(R.id.drawer_layout,
                        new ActionPostFragment())
                        .addToBackStack(null).commit();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_admin_approve, parent, false);
        return new AdminAdapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView mUsername, mNameDrinks, mCate;
        ImageView mImagePost;
        RelativeLayout item;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
            mUsername = itemView.findViewById(R.id.tv_usernaneofPost);
            mNameDrinks = itemView.findViewById(R.id.tv_nameDrinksPost);
            mCate = itemView.findViewById(R.id.tv_cateofDrinksPost);
            mImagePost = itemView.findViewById(R.id.image_post);
        }
    }
}
