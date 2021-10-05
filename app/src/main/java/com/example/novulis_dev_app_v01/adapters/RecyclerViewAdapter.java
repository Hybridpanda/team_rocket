package com.example.novulis_dev_app_v01.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.novulis_dev_app_v01.activities.BookActivity;
import com.example.novulis_dev_app_v01.model.Book;
//import com.google.android.gms.fido.fido2.api.common.RequestOptions;
import com.example.novulis_dev_app_v01.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Book> mData;
    private RequestOptions options;

    public RecyclerViewAdapter(Context mContext, ArrayList<Book> mData) {
        this.mContext = mContext;
        this.mData = mData;

        //Request option for Glide
        options = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.book_raw_item , parent , false);
        final MyViewHolder viewHolder =  new MyViewHolder(view);
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext , BookActivity.class);
                int pos = viewHolder.getAdapterPosition();
                i.putExtra("book", mData.get(pos));
                i.putExtra("book_title" ,mData.get(pos).getTitle());
                i.putExtra("book_author" ,mData.get(pos).getAuthor());
                i.putExtra("book_desc" ,mData.get(pos).getDescription());
                i.putExtra("book_thumbnail", mData.get(pos).getCover());
                i.putExtra("book_pageCount", mData.get(pos).getPageCount());
                i.putExtra("book_currentPage", mData.get(pos).getCurrentPage());

                mContext.startActivity(i);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Book book = mData.get(i);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.libraryProgressBar.setProgress((int) Math.floor(100 * book.getCurrentPage()/book.getPageCount()));
//        holder.tvPrice.setText(book.getPrice());
//        holder.tvCategory.setText(book.getCategories());

        //load image from internet and set it into imageView using Glide
        Glide.with(mContext).load(book.getCover()).timeout(6000).apply(options).into(holder.ivThumbnail);
        //Picasso.with(mContext).load(book.getCover()).fit().into(holder.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView ivThumbnail ;
        TextView tvTitle , tvAuthor;
        LinearLayout container ;
        ProgressBar libraryProgressBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivThumbnail = itemView.findViewById(R.id.bookCoverIv);
            tvTitle = itemView.findViewById(R.id.bookTitleTv);
            tvAuthor = itemView.findViewById(R.id.authorTv);
            container = itemView.findViewById(R.id.container);
            libraryProgressBar = itemView.findViewById(R.id.libraryProgressBar);


        }
    }

}
