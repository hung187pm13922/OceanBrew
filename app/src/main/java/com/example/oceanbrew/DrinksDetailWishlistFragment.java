package com.example.oceanbrew;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.oceanbrew.model.Wishlist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrinksDetailWishlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrinksDetailWishlistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String DrinksOfName;
    String Garnish;
    String Methol;
    String Ingradients;
    String Category;

    public DrinksDetailWishlistFragment() {

    }

    public DrinksDetailWishlistFragment(String drinksOfName, String garnish, String methol, String ingradients, String category) {
        DrinksOfName = drinksOfName;
        Garnish = garnish;
        Methol = methol;
        Ingradients = ingradients;
        Category = category;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrinksDetailWishlistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrinksDetailWishlistFragment newInstance(String param1, String param2) {
        DrinksDetailWishlistFragment fragment = new DrinksDetailWishlistFragment();
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
        View view = inflater.inflate(R.layout.fragment_drinks_detail_wishlist, container, false);

        TextView mNameDrinks, mGarnish, mMethol;
        mNameDrinks = view.findViewById(R.id.tv_NameDrinks);
        mGarnish = view.findViewById(R.id.textVj);
        mMethol = view.findViewById(R.id.textV);
        mNameDrinks.setText(DrinksOfName);
        mGarnish.setText(Garnish);
        mMethol.setText(Methol);

        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.tblo_I);
        String []arr = Ingradients.split(";");

        for(int i = -1; i < arr.length-1; i+=2 ) {
            TableRow row = new TableRow(getActivity().getApplication());
            TextView tv1 = new TextView(getActivity().getApplication());
            tv1.setText(arr[i+1]);
            tv1.setTextColor(Color.BLACK);
            TextView tv2 = new TextView(getActivity().getApplication());
            tv2.setText(arr[i+2]);
            tv2.setTextColor(Color.BLACK);
            row.addView(tv1);
            row.addView(tv2);
            tableLayout.addView(row);
        }

        ImageView mBack;
        mBack = view.findViewById(R.id.btn_BackHome);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction().replace(R.id.body_container,
                        new WishlistFragment())
                        .addToBackStack(null).commit();
            }
        });

        SharedPreferences sharedPreferences;
        sharedPreferences = view.getContext().getSharedPreferences("session_user", Context.MODE_PRIVATE);
        String Username = sharedPreferences.getString("session_username", "");

        CheckBox mAddWishlish;
        mAddWishlish = view.findViewById(R.id.checkbox_addwl);
        DatabaseReference check_ChecBox;
        check_ChecBox = FirebaseDatabase.getInstance().getReference("Wishlist");
        Query check = check_ChecBox.orderByChild("username").equalTo(Username);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Wishlist wishlist = new Wishlist();
                for (DataSnapshot data : snapshot.getChildren()) {
                    wishlist = data.getValue(Wishlist.class);
                    if (wishlist.getNameofDrinks().equals(DrinksOfName)) {
                        mAddWishlish.setChecked(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mAddWishlish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Wishlist");
                if (mAddWishlish.isChecked()) {
                    Wishlist wishlist = new Wishlist(Category, DrinksOfName, Username);
                    databaseReference.push().setValue(wishlist);
                } else {
                    Query query = databaseReference.orderByChild("username").equalTo(Username);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                Wishlist wishlist = data.getValue(Wishlist.class);
                                if (wishlist.getNameofDrinks().equals(DrinksOfName)) {
                                    data.getRef().removeValue();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        return view;
    }
}