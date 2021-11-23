package com.example.oceanbrew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
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
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        TextView mAddRow, m1D;
        Button mSend;
        Spinner mCategory;
        DatabaseReference mCategoryDbRef;

        mCategoryDbRef = FirebaseDatabase.getInstance().getReference("Category");
        mCategory = view.findViewById(R.id.spn_category);

        ArrayList<String> Category = new ArrayList<>();

        mCategoryDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String item = ds.getKey().toString();
                    Category.add(item);
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,Category);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mCategory.setAdapter(dataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        m1D = view.findViewById(R.id.first_delete);
        m1D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableLayout table =(TableLayout) view.findViewById(R.id.tl_material);
                int num_rows = table.getChildCount();
                if (num_rows > 3) {
                    table.removeViewAt(1);
                }
            }
        });

        mAddRow = view.findViewById(R.id.tv_addrow);
        mAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableLayout table =(TableLayout) view.findViewById(R.id.tl_material);
                int num_rows = table.getChildCount();

                TableRow row = new TableRow(getActivity().getApplication());
                EditText ed_name = new EditText(getActivity().getApplication());
                EditText ed_num = new EditText(getActivity().getApplication());
                TextView tv_delete = new TextView(getActivity().getApplication());
                tv_delete.setText("Delete");
                tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TableLayout table =(TableLayout) view.findViewById(R.id.tl_material);
                        int num_rows = table.getChildCount();
                        if (num_rows > 3) {
                            View row = (View) v.getParent();
                            ViewGroup container = ((ViewGroup)row.getParent());
                            container.removeView(row);
                            container.invalidate();
                        }
                    }
                });

                TableRow r = (TableRow) table.getChildAt(num_rows-2);
                TextView t1 = (TextView) r.getChildAt(0);
                TextView t2 = (TextView) r.getChildAt(1);
                String txt1 = t1.getText().toString();
                String txt2 = t2.getText().toString();

                if (TextUtils.isEmpty(txt1)) {
                    t1.setError("aaa");
                } else if (TextUtils.isEmpty(txt2)) {
                    t2.setError("bbb");
                } else {
                    row.addView(ed_name);
                    row.addView(ed_num);
                    row.addView(tv_delete);
                    table.addView(row, num_rows-1);
                }
            }
        });
        mSend = view.findViewById(R.id.btn_send);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Ingredients = "";
                TableLayout table = (TableLayout) view.findViewById(R.id.tl_material);
                int num_rows = table.getChildCount();
                int countError = 0;

                for (int row = 1; row < num_rows-1; row++) {
                    TableRow indexOfRow = (TableRow) table.getChildAt(row);
                    for (int cell = 0; cell <= 1; cell++) {
                        TextView tv = (TextView) indexOfRow.getChildAt(cell);
                        String txt = tv.getText().toString();
                        if (!TextUtils.isEmpty(txt)) {
                            Ingredients += txt+";";
                        } else {
                            countError += 1;
                            tv.setError("ádas");
                        }
                    }
                }
                if (countError > 0) {
                    Toast.makeText(getActivity().getApplication(), "thieu du lieu", Toast.LENGTH_SHORT).show();
                } else {
                    EditText mNameOfDrink, mMethod, mGarnish;
                    DatabaseReference mPostDbRef;
                    Date currentTime;
                    SharedPreferences sharedPreferences;

                    mNameOfDrink = view.findViewById(R.id.ed_nameofdrinks);
                    mMethod = view.findViewById(R.id.ed_method);
                    mGarnish = view.findViewById(R.id.ed_garnish);
                    currentTime = Calendar.getInstance().getTime();
                    mPostDbRef = FirebaseDatabase.getInstance().getReference("Posts");
                    String NameOfDrink = mNameOfDrink.getText().toString();
                    String Method = mMethod.getText().toString();
                    String Garnish = mGarnish.getText().toString();
                    String CategorySelected = mCategory.getSelectedItem().toString();
                    String WhenPost = currentTime.toString();
                    sharedPreferences = getContext().getSharedPreferences("session_user", Context.MODE_PRIVATE);
                    String Username = sharedPreferences.getString("session_username", "");
                    ///CheckError mốt làm ở đây nha

                    Posts post = new Posts(NameOfDrink, Ingredients, CategorySelected, Garnish, Method, WhenPost, Username, "Waiting for approval");
                    mPostDbRef.push().setValue(post);
                }
            }
        });

        return view;
    }
}