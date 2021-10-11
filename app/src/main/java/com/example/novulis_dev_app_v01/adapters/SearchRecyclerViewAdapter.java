package com.example.novulis_dev_app_v01.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.activities.BookActivity;
import com.example.novulis_dev_app_v01.activities.SearchActivity;
import com.example.novulis_dev_app_v01.model.Book;
import com.example.novulis_dev_app_v01.model.Profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder>{

    private ArrayList<Book> library;
    private List<Book> searchResults;
    private Context mContext;
    private RequestOptions options;
    private Profile profile;

    public SearchRecyclerViewAdapter(ArrayList<Book> library, ArrayList<Book> searchResults, Context mContext) {
        this.searchResults = searchResults;
        this.mContext = mContext;
        this.library = library;
        options = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        profile = new Profile();
        profile.loadProfile(mContext);
        profile.loadLibrary(mContext);
    }

    @NonNull
    @Override
    public SearchRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.search_result_raw_item , parent , false);
        final SearchRecyclerViewAdapter.MyViewHolder viewHolder =  new SearchRecyclerViewAdapter.MyViewHolder(view);

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext , BookActivity.class);
                int pos = viewHolder.getAdapterPosition();
                i.putExtra("book_title" ,searchResults.get(pos).getTitle());
                i.putExtra("book_author" ,searchResults.get(pos).getAuthor());
                i.putExtra("book_desc" ,searchResults.get(pos).getDescription());
                i.putExtra("book_thumbnail", searchResults.get(pos).getCover());
                i.putExtra("book_genre", searchResults.get(pos).getGenre());
                i.putExtra("book_preview", searchResults.get(pos).getPreview());

                mContext.startActivity(i);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewAdapter.MyViewHolder holder, int position) {
        Book book = searchResults.get(position);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());

        holder.addToLibraryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean inLibrary = false;
                int pos = holder.getAdapterPosition();

                // Check if the clicked book is already in the library
                for(Book b : library) {
                    if (searchResults.get(pos).getIsbn().equals(b.getIsbn()) || searchResults.get(pos).getTitle().equals(b.getTitle())) {
                        CharSequence text = "This book is already in your library.";
                        Toast.makeText(view.getContext(), text, Toast.LENGTH_LONG).show();
                        inLibrary = true;
                    }
                }

                // If not in the library then add it and reload the library fragment
                if (!inLibrary) {
                    ArrayList<Book> tempLibrary = library;
                    searchResults.get(pos).setCategory("Currently Reading");
                    tempLibrary.add(searchResults.get(pos));

                    profile.setLibrary(tempLibrary);
                    profile.saveLibrary(mContext);
                }
            }
        });

//        holder.tvPrice.setText(book.getPrice());
//        holder.tvCategory.setText(book.getCategories());
        //load image from internet and set it into imageView using Glide
        Glide.with(mContext).load(book.getCover()).timeout(6000).apply(options).into(holder.ivThumbnail);
        //Picasso.with(mContext).load(book.getCover()).fit().into(holder.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView ivThumbnail ;
        TextView tvTitle , tvAuthor;
        LinearLayout container ;
        Button addToLibraryBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivThumbnail = itemView.findViewById(R.id.bookCoverIv);
            tvTitle = itemView.findViewById(R.id.bookTitleTv);
            tvAuthor = itemView.findViewById(R.id.authorTv);
            container = itemView.findViewById(R.id.container);
            addToLibraryBtn = itemView.findViewById(R.id.addToLibraryBtn);

        }
    }



}
