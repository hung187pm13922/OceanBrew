package com.example.oceanbrew.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oceanbrew.DrinksDetailFragment;
import com.example.oceanbrew.R;
import com.example.oceanbrew.model.Drinks;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AllRecipesAdapter extends FirebaseRecyclerAdapter<Drinks, AllRecipesAdapter.myViewHolder> {

    public AllRecipesAdapter(@NonNull FirebaseRecyclerOptions<Drinks> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Drinks model) {
        holder.mNameDrinks.setText(model.getNameDrinks());
        holder.mCateOfDrinks.setText("Type of Drinks: "+model.getCategory());
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction().replace(R.id.body_container,
                        new DrinksDetailFragment(model.getNameDrinks(), model.getIngradients(), model.getGarnish(), model.getMethol(), model.getCategory()))
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
