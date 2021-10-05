package com.example.novulis_dev_app_v01.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.novulis_dev_app_v01.activities.SearchActivity;
import com.example.novulis_dev_app_v01.model.Book;
import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.adapters.RecyclerViewAdapter;
import com.example.novulis_dev_app_v01.model.Profile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LibraryFragment extends Fragment {

    // Discover button
    Button discoverBtn;

    // Library card test ID's
    TextView bookTitleTv;
    TextView authorTv;
    ImageView bookCoverIv;
    ProgressDialog pd;

    // Copied Variables
    private Context mContext;
    private RecyclerView currentlyReadingRecyclerView;
    private RecyclerView clubBooksRecyclerView;
    private RecyclerView readAgainRecyclerView;

    private ArrayList<Book> library;
    private Profile profile;

    private ArrayList<Book> currentBooks;
    private ArrayList<Book> clubBooks;
    private ArrayList<Book> readAgain;


    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_library, container, false);

        pd = new ProgressDialog(v.getContext());
        pd.setMessage("Please wait");
        pd.setCancelable(false);
        pd.show();

        discoverBtn = v.findViewById(R.id.discoverBtn);
        bookTitleTv = v.findViewById(R.id.bookTitleTv);
        authorTv = v.findViewById(R.id.authorTv);
        bookCoverIv = v.findViewById(R.id.bookCoverIv);

        currentlyReadingRecyclerView = v.findViewById(R.id.currentlyReadingRV);

        clubBooksRecyclerView = v.findViewById(R.id.clubBooksRV);

        readAgainRecyclerView = v.findViewById(R.id.readAgainRV);

        profile = new Profile();
        library = new ArrayList<>();
        currentBooks = new ArrayList<>();
        clubBooks = new ArrayList<>();
        readAgain = new ArrayList<>();

        discoverBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        // Import users library
        //new JsonTask().execute(testBookJson);

        try {
            readLibrary();
            System.out.println(library.toString());

            currentlyReadingRecyclerView.setAdapter(new RecyclerViewAdapter(mContext , currentBooks));
            currentlyReadingRecyclerView.setHasFixedSize(true);
            currentlyReadingRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));

            clubBooksRecyclerView.setAdapter(new RecyclerViewAdapter(mContext , clubBooks));
            clubBooksRecyclerView.setHasFixedSize(true);
            clubBooksRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));

            readAgainRecyclerView.setAdapter(new RecyclerViewAdapter(mContext , readAgain));
            readAgainRecyclerView.setHasFixedSize(true);
            readAgainRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //search();
        if (pd.isShowing()){
            pd.dismiss();
        }

        return v;
    }

    public void readLibrary() throws IOException, ClassNotFoundException {

        library = profile.getLibrary(mContext);

        for (Book b : library) {

            switch (b.getCategory()) {
                case "Currently Reading":
                    currentBooks.add(b);
                    break;
                case "Club Books":
                    clubBooks.add(b);
                    break;
                case "Read Again":
                    readAgain.add(b);
                    break;
            }
        }
        Collections.reverse(currentBooks);
        Collections.reverse(clubBooks);
        Collections.reverse(readAgain);
    }

}