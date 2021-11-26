package com.example.oceanbrew.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oceanbrew.R;
import com.example.oceanbrew.model.Posts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdminAdapter extends FirebaseRecyclerAdapter<Posts, AdminAdapter.myViewHolder> {

    public AdminAdapter(@NonNull FirebaseRecyclerOptions<Posts> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Posts model) {
        holder.mUsername.setText(model.getUsername());
        holder.mCate.setText(model.getTypeOfDrinks());
        holder.mNameDrinks.setText(model.getNameOfDrink());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_admin_approve, parent, false);
        return new AdminAdapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView mUsername, mNameDrinks, mCate;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            mUsername = itemView.findViewById(R.id.tv_usernaneofPost);
            mNameDrinks = itemView.findViewById(R.id.tv_nameDrinksPost);
            mCate = itemView.findViewById(R.id.tv_cateofDrinksPost);
        }
    }
}
