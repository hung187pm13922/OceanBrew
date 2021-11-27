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
import com.example.oceanbrew.DrinksDetailWishlistFragment;
import com.example.oceanbrew.R;
import com.example.oceanbrew.model.Drinks;
import com.example.oceanbrew.model.Wishlist;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WishlistAdapter extends FirebaseRecyclerAdapter<Wishlist, WishlistAdapter.myViewHolder> {

    public WishlistAdapter(@NonNull FirebaseRecyclerOptions<Wishlist> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Wishlist model) {
        holder.mNameDrinks.setText(model.getNameofDrinks());
        holder.mCateOfDrinks.setText("Type of Drinks: "+model.getCategory());
        List<Drinks> drinks = new ArrayList<>();
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Drinks");
                Query query = databaseReference.orderByChild("nameDrinks").equalTo(model.getNameofDrinks());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()){
                            drinks.add(data.getValue(Drinks.class));
                        }
                        AppCompatActivity activity=(AppCompatActivity)v.getContext();
                        activity.getSupportFragmentManager()
                                .beginTransaction().replace(R.id.body_container,
                                new DrinksDetailWishlistFragment(drinks.get(0).getNameDrinks(), drinks.get(0).getGarnish(), drinks.get(0).getMethol(), drinks.get(0).getIngradients(), drinks.get(0).getCategory()))
                                .addToBackStack(null).commit();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desgin_drinksoderbycate, parent, false);
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
