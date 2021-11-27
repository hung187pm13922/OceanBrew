package com.example.oceanbrew.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oceanbrew.DrinksOderByCateFragment;
import com.example.oceanbrew.R;
import com.example.oceanbrew.model.Category;
import com.example.oceanbrew.model.Wishlist;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CategoryAdapter extends FirebaseRecyclerAdapter<Category,CategoryAdapter.myViewHolder>
{

    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<Category> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Category model) {

        holder.mNameCategory.setText(model.getName());
        if (model.getName().equals("Tea")) {
            holder.mImageCategory.setImageResource(R.mipmap.tea);
        } else if (model.getName().equals("Liquor")) {
            holder.mImageCategory.setImageResource(R.mipmap.liquor);
        } else {
            holder.mImageCategory.setImageResource(R.mipmap.coffee);
        }

        holder.mLayoutItemCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.body_container,new DrinksOderByCateFragment(model.getName())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.design_homepage,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView mNameCategory;
        ImageView mImageCategory;
        RelativeLayout mLayoutItemCate;
        SearchView mSearch;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameCategory = itemView.findViewById(R.id.tv_nameCategory);
            mImageCategory = itemView.findViewById(R.id.img_category);
            mLayoutItemCate = itemView.findViewById(R.id.layout_item_category);
            mSearch = itemView.findViewById(R.id.sv_inHomePage);
        }
    }

}
