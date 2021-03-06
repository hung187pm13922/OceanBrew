package com.example.oceanbrew;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oceanbrew.adapter.WishlistAdapter;
import com.example.oceanbrew.model.Wishlist;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WishlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishlistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    WishlistAdapter mWishlistAdapter;
    RecyclerView recyclerView;

    public WishlistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WishlistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WishlistFragment newInstance(String param1, String param2) {
        WishlistFragment fragment = new WishlistFragment();
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
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        SharedPreferences sharedPreferences;
        sharedPreferences = view.getContext().getSharedPreferences("session_user", Context.MODE_PRIVATE);
        String Username = sharedPreferences.getString("session_username", "");

        recyclerView = view.findViewById(R.id.rcv_Wishlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<Wishlist> options = new FirebaseRecyclerOptions.Builder<Wishlist>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Wishlist").orderByChild("username").equalTo(Username), Wishlist.class)
                .build();

        mWishlistAdapter = new WishlistAdapter(options);
        recyclerView.setAdapter(mWishlistAdapter);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mWishlistAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mWishlistAdapter.stopListening();
    }
}