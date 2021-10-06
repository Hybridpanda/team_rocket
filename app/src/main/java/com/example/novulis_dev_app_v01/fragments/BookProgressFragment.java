package com.example.novulis_dev_app_v01.fragments;

import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.novulis_dev_app_v01.R;


public class BookProgressFragment extends Fragment {


    ProgressBar progressBar1;
    ProgressBar progressBar2;
    ProgressBar progressBar3;
    ProgressBar progressBar4;
    ProgressBar progressBar5;

    int pageCount;
    int currentPage;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookProgressFragment() {
        // Required empty public constructor
    }

    public static BookProgressFragment newInstance(Bundle extras) {
        BookProgressFragment fragment = new BookProgressFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageCount = getArguments().getInt("book_pageCount");
            currentPage = getArguments().getInt("book_currentPage");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_book_progress, container, false);

        progressBar1 = view.findViewById(R.id.progressBar1);
        progressBar2 = view.findViewById(R.id.progressBar2);
        progressBar3 = view.findViewById(R.id.progressBar3);
        progressBar4 = view.findViewById(R.id.progressBar4);
        progressBar5 = view.findViewById(R.id.progressBar5);

        progressBar1.setMax(1);
        if(currentPage >= 1) {
            progressBar1.setProgress(1);
        }


        progressBar2.setMax((int) pageCount/2);
        progressBar2.setProgress(currentPage);

        progressBar5.setMax(pageCount);
        progressBar5.setProgress(currentPage);

        return view;
    }
}