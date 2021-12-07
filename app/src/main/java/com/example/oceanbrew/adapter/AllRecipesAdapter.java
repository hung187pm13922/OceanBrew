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
import com.example.oceanbrew.DrinksDetailFragment;
import com.example.oceanbrew.R;
import com.example.oceanbrew.model.Drinks;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AllRecipesAdapter extends FirebaseRecyclerAdapter<Drinks, AllRecipesAdapter.myViewHolder> {

    public AllRecipesAdapter(@NonNull FirebaseRecyclerOptions<Drinks> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Drinks model) {
        holder.mNameDrinks.setText(model.getNameDrinks());
        holder.mCateOfDrinks.setText("Type of Drinks: "+model.getCategory());
        StorageReference reference = FirebaseStorage.getInstance().getReference("images").child(model.getLink());
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.mImageDrinks);
            }
        });
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction().replace(R.id.body_container,
                        new DrinksDetailFragment(model.getNameDrinks(), model.getIngradients(), model.getGarnish(), model.getMethol(), model.getCategory(), model.getLink()))
                        .addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.desgin_drinksoderbycate,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mRelativeLayout;
        TextView mNameDrinks, mCateOfDrinks;
        ImageView mImageDrinks;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameDrinks = itemView.findViewById(R.id.tv_nameDrinksO);
            mCateOfDrinks = itemView.findViewById(R.id.tv_CateOfDrinks);
            mImageDrinks = itemView.findViewById(R.id.imageDrinks_Cate);
            mRelativeLayout = itemView.findViewById(R.id.layout_drinksoderbyCate);
        }
    }
}
