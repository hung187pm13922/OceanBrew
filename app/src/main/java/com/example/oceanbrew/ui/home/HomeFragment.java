package com.example.oceanbrew.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oceanbrew.R;
import com.example.oceanbrew.adapter.AdminAdapter;
import com.example.oceanbrew.adapter.CategoryAdapter;
import com.example.oceanbrew.databinding.FragmentHomeBinding;
import com.example.oceanbrew.model.Posts;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView mRecyclerView;
    AdminAdapter mAdminAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mRecyclerView = root.findViewById(R.id.rcv_post);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<Posts> options =
                new FirebaseRecyclerOptions.Builder<Posts>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Posts"), Posts.class)
                .build();

        mAdminAdapter = new AdminAdapter(options);

        mRecyclerView.setAdapter(mAdminAdapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdminAdapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        mAdminAdapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}