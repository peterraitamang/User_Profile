package com.example.userprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userprofile.ROOM.Model;
import com.example.userprofile.ROOM.ProfileAdapter;
import com.example.userprofile.ROOM.Repo;
import com.example.userprofile.databinding.FragmentItemListBinding;

import java.util.List;

public class itemListFragment extends Fragment {

    private ProfileAdapter profileAdapter;
    private Repo repository;
    private FragmentItemListBinding binding;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = binding.itemList;
        profileAdapter = new ProfileAdapter();
        recyclerView.setAdapter(profileAdapter);

        repository = new Repo(requireActivity().getApplication());

        repository.getAllUsers().observe(getViewLifecycleOwner(), new Observer<List<Model>>() {
            @Override
            public void onChanged(List<Model> models) {
                profileAdapter.submitList(models);
            }
        });

        if (binding.holderAddUser != null) {
            binding.holderAddUser.setOnClickListener(view1 -> {
                Intent intent = new Intent(getActivity(), AddUserProfileActivity.class);
                startActivity(intent);
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
