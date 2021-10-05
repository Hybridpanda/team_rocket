package com.example.novulis_dev_app_v01.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.novulis_dev_app_v01.fragments.BookDetailsFragment;
import com.example.novulis_dev_app_v01.fragments.BookProgressFragment;
import com.example.novulis_dev_app_v01.fragments.BookSimilarFragment;
import com.example.novulis_dev_app_v01.model.Book;

import java.util.ArrayList;

public class TabAdapter extends FragmentStateAdapter {

    private final Context mContext;
    Book book;
    int totalTabs;
    Bundle extras;

    public TabAdapter(Context context, FragmentActivity fa, int totalTabs, Bundle Extras) {
        super(fa);
        mContext = context;
        this.totalTabs = totalTabs;
        this.book = book;
        this.extras = extras;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new BookDetailsFragment().newInstance(extras);
            case 1:
                return new BookProgressFragment().newInstance(extras);
            case 2:
                return new BookSimilarFragment().newInstance(extras);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return totalTabs;
    }
}
