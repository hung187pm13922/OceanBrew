package com.example.oceanbrew;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.oceanbrew.adapter.CategoryAdapter;
import com.example.oceanbrew.model.Category;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView mCategoryRcv;
    CategoryAdapter mCategoryAdapter;
    EditText SearchString;
    ImageButton Search;

    public HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
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
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        mCategoryRcv =  view.findViewById(R.id.rcv_category);
        mCategoryRcv.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<Category> options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Category"), Category.class)
                        .build();

        mCategoryAdapter = new CategoryAdapter(options);
        mCategoryRcv.setAdapter(mCategoryAdapter);

        SearchString = view.findViewById(R.id.ed_search);
        Search = view.findViewById(R.id.btn_search);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = SearchString.getText().toString();
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction().replace(R.id.body_container,
                        new ResultSearchFragment(searchString))
                        .addToBackStack(null).commit();
            }
        });

        Button button;
        button = view.findViewById(R.id.btn_allrecipes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction().replace(R.id.body_container,
                        new AllRecipesFragment())
                        .addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mCategoryAdapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        mCategoryAdapter.stopListening();
    }
}