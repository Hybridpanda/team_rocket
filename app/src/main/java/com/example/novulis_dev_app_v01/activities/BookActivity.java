package com.example.novulis_dev_app_v01.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.adapters.TabAdapter;
import com.example.novulis_dev_app_v01.model.Book;
import com.example.novulis_dev_app_v01.model.Profile;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BookActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    Profile profile = new Profile();



    Book book;

    private OnAboutDataReceivedListener mAboutDataListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        getSupportActionBar().hide();

        // Receive

        Bundle extras = getIntent().getExtras();

        String title =""
                , authors ="", description="" , categories ="", publishDate=""
                ,info ="", buy ="",preview ="" ,thumbnail ="";
        int pageCount = 0, currentPage = 0;
        if(extras != null){
            title = extras.getString("book_title");
            authors = extras.getString("book_author");
            description = extras.getString("book_desc");
            categories = extras.getString("book_categories");
            publishDate = extras.getString("book_publish_date");
            info = extras.getString("book_info");
            buy = extras.getString("book_buy");
            preview = extras.getString("book_preview");
            thumbnail = extras.getString("book_thumbnail");
            pageCount = extras.getInt("book_pageCount");
            currentPage = extras.getInt("book_currentPage");
        }

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tvTitle = findViewById(R.id.aa_book_name);
        TextView tvAuthors = findViewById(R.id.aa_author);
        TextView tvDesc = findViewById(R.id.aa_description);
        TextView tvCatag = findViewById(R.id.aa_categorie);
        TextView tvPublishDate = findViewById(R.id.aa_publish_date);
        TextView tvPreview = findViewById(R.id.aa_preview);
        Button setCurrentBookBtn = findViewById(R.id.setAsCurrentBookBtn);

        ImageView ivThumbnail = findViewById(R.id.aa_thumbnail);

        ProgressBar bookProgressBar = findViewById(R.id.bookProgressBar);

        tvTitle.setText(title);
        tvAuthors.setText(authors);
        tvDesc.setText(description);
        tvCatag.setText(categories);
        tvPublishDate.setText(publishDate);

        int currentProgress = 0;
        if (pageCount != 0) {
            currentProgress = (int) Math.floor(100 * currentPage/pageCount);
        }

        System.out.println(currentProgress);
        bookProgressBar.setMax(100);
        bookProgressBar.setProgress(currentProgress);


        final String finalPreview = preview;
        tvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(finalPreview));
                startActivity(i);

            }
        });

        String finalTitle = title;
        setCurrentBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.setCurrentBook(finalTitle);
                profile.saveProfile(getApplicationContext());
                Toast.makeText(getApplicationContext(), finalTitle + " set as current book", Toast.LENGTH_LONG).show();
            }
        });

        //collapsingToolbarLayout.setTitle(title);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        Glide.with(this).load(thumbnail).timeout(6000).apply(requestOptions).into(ivThumbnail);

        // Set up tabs

        tabLayout = findViewById(R.id.bookTabLayout);
        viewPager = findViewById(R.id.bookViewPager);

        viewPager.setAdapter(new TabAdapter(this, this, 3, extras));
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Details");
                        return;
                    case 1:
                        tab.setText("Progress");
                        return;
                    case 2:
                        tab.setText("Similar");
                        return;
                }

            }
        }).attach();
    }

    public interface OnAboutDataReceivedListener {
        void onDataReceived(Book book);
    }

    public void setAboutDataListener(OnAboutDataReceivedListener listener) {
        this.mAboutDataListener = listener;
    }
}