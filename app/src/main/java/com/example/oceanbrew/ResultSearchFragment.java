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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oceanbrew.adapter.ResultAdapter;
import com.example.oceanbrew.model.Drinks;
import com.example.oceanbrew.model.Search;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String searchString;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    ResultAdapter mResultAdapter;

    public ResultSearchFragment() {
        // Required empty public constructor
    }

    public ResultSearchFragment(String searchString) {
        this.searchString = searchString;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultSearchFragment newInstance(String param1, String param2) {
        ResultSearchFragment fragment = new ResultSearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_result_search, container, false);

        EditText editText;
        editText = view.findViewById(R.id.ed_searchInResult);
        editText.setText(searchString);

        sharedPreferences = getContext().getSharedPreferences("session_user", Context.MODE_PRIVATE);
        String Username = sharedPreferences.getString("session_username", "");
        editor = sharedPreferences.edit();
        editor.putString("session_searchString",searchString);
        ArrayList<Drinks> AllItemList = new ArrayList<Drinks>();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Drinks");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    AllItemList.add(data.getValue(Drinks.class));
                }
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Search");
                for (int i = 0 ; i < AllItemList.size(); i++) {
                    if (AllItemList.get(i).getNameDrinks().toLowerCase().contains(searchString.toLowerCase())) {
                        Search search = new Search(AllItemList.get(i).getCategory(), AllItemList.get(i).getGarnish(), AllItemList.get(i).getIngradients(), AllItemList.get(i).getLink(), AllItemList.get(i).getMethol(), AllItemList.get(i).getNameDrinks(), Username);
                        databaseReference.push().setValue(search);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = view.findViewById(R.id.rcv_result);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<Search> options =
                new FirebaseRecyclerOptions.Builder<Search>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Search").orderByChild("username").equalTo(Username), Search.class)
                .build();

        mResultAdapter = new ResultAdapter(options);
        recyclerView.setAdapter(mResultAdapter);

        DatabaseReference remove = FirebaseDatabase.getInstance().getReference("Search");
        remove.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Search search = new Search();
                for(DataSnapshot d : snapshot.getChildren()) {
                    search = d.getValue(Search.class);
                    if (search.getUsername().equals(Username)) {
                        d.getRef().removeValue();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button back;
        back = view.findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction().replace(R.id.body_container,
                        new HomePageFragment())
                        .addToBackStack(null).commit();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mResultAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mResultAdapter.stopListening();
    }
}