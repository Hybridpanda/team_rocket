package com.example.novulis_dev_app_v01.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.novulis_dev_app_v01.R;


public class BookDetailsFragment extends Fragment {

    private TextView descriptionTv;

    // TODO: Rename and change types of parameters
    private String description;
    private String mParam2;

    public BookDetailsFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static BookDetailsFragment newInstance(Bundle extras) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            description = getArguments().getString("book_desc");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        descriptionTv = view.findViewById(R.id.bookDescription);

//        this.onCreate(getArguments());
        System.out.println("Description: " + description);
        descriptionTv.setText(description);
        // Inflate the layout for this fragment
        return view;
    }
}