package com.example.oceanbrew;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oceanbrew.adapter.DrinksOderByCateAdapter;
import com.example.oceanbrew.model.Drinks;
import com.example.oceanbrew.model.Wishlist;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrinksOderByCateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrinksOderByCateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String name;
    RecyclerView mDrinksOderByCateRcv;
    DrinksOderByCateAdapter mDrinksOderByCateAdapter;

    public DrinksOderByCateFragment() {
        // Required empty public constructor
    }

    public DrinksOderByCateFragment(String name) {
        this.name=name;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrinksOderByCateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrinksOderByCateFragment newInstance(String param1, String param2) {
        DrinksOderByCateFragment fragment = new DrinksOderByCateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drinks_oder_by_cate, container, false);

        TextView mHeaderCategory;
        mHeaderCategory = view.findViewById(R.id.header_category);
        mHeaderCategory.setText(name);

        ImageView mBackHome;
        mBackHome = view.findViewById(R.id.btn_back_DrinksOderByCate);
        mBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.body_container,new HomePageFragment()).addToBackStack(null).commit();
            }
        });

        mDrinksOderByCateRcv =  view.findViewById(R.id.rcv_DrinksOderByCategory);
        mDrinksOderByCateRcv.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<Drinks> options =
                new FirebaseRecyclerOptions.Builder<Drinks>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Drinks").orderByChild("category").equalTo(name), Drinks.class)
                        .build();

        mDrinksOderByCateAdapter = new DrinksOderByCateAdapter(options);
        mDrinksOderByCateRcv.setAdapter(mDrinksOderByCateAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDrinksOderByCateAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDrinksOderByCateAdapter.stopListening();
    }

}