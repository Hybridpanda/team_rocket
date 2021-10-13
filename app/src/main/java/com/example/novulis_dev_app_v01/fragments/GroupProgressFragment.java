package com.example.novulis_dev_app_v01.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.novulis_dev_app_v01.R;


public class GroupProgressFragment extends Fragment {

    public GroupProgressFragment() {
        // Required empty public constructor
    }

    public static GroupProgressFragment newInstance() {
        GroupProgressFragment fragment = new GroupProgressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_progress, container, false);
    }
}