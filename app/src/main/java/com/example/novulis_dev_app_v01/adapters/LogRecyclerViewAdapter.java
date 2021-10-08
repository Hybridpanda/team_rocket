package com.example.novulis_dev_app_v01.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.model.Log;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LogRecyclerViewAdapter extends RecyclerView.Adapter<LogRecyclerViewAdapter.MyViewHolder>{

    private ArrayList<Log> bookLog;
    private Context mContext;

    public LogRecyclerViewAdapter(ArrayList<Log> bookLog, Context mContext) {

        this.bookLog = bookLog;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public LogRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_raw_item,parent,false);
        final LogRecyclerViewAdapter.MyViewHolder viewHolder = new LogRecyclerViewAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DateFormat df = new SimpleDateFormat();

        holder.logBookTitleTv.setText(bookLog.get(position).getBookTitle());
        holder.logAmountTv.setText("Pages read: " + bookLog.get(position).getPages());
        holder.logDateTv.setText(bookLog.get(position).getLogDate().toString());
        holder.logNoteTv.setText(bookLog.get(position).getNote());

    }

    @Override
    public int getItemCount() {
        return bookLog.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView logBookTitleTv;
        TextView logDateTv;
        TextView logAmountTv;
        TextView logNoteTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            logBookTitleTv = itemView.findViewById(R.id.logBookTitleTv);
            logDateTv = itemView.findViewById(R.id.logDateTv);
            logAmountTv = itemView.findViewById(R.id.logAmountTv);
            logNoteTv = itemView.findViewById(R.id.logNoteTv);
        }
    }
}
