package com.example.novulis_dev_app_v01.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.adapters.RecyclerViewAdapter;
import com.example.novulis_dev_app_v01.adapters.SearchRecyclerViewAdapter;
import com.example.novulis_dev_app_v01.model.Book;
import com.example.novulis_dev_app_v01.model.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private Profile profile;
    private ArrayList library;
    private EditText searchEditText;
    private Button searchBtn; //Not yet set
    private Button genreBtn;
    private Button similarBtn;
    private Button curatorBtn;
    private ImageView swordShieldIv;
    private ProgressBar loading_indicator;
    private TextView error_message;

    // Library card test ID's
    TextView bookTitleTv;
    TextView authorTv;
    ImageView bookCoverIv;
    ProgressDialog pd;

    // Copied Variables
    private Context mContext;
    private RecyclerView searchResultsRecyclerView;
    private ArrayList<Book> mBooks;
    private SearchRecyclerViewAdapter mAdapter;
    private RequestQueue mRequestQueue;

    private static  final  String BASE_URL="https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.searchEditText);
        searchBtn = findViewById(R.id.searchBtn);
        genreBtn = findViewById(R.id.genreBtn);
        similarBtn = findViewById(R.id.similarBtn);
        curatorBtn = findViewById(R.id.curatorBtn);
        swordShieldIv = findViewById(R.id.imageView5);

        searchResultsRecyclerView = findViewById(R.id.searchResultsRV);
        searchResultsRecyclerView.setHasFixedSize(true);
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mContext = this;
        profile = new Profile();
        library = profile.getLibrary(mContext);
        mBooks = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBooks.clear();
                search();
                searchResultsRecyclerView.setVisibility(View.VISIBLE);
                genreBtn.setVisibility(View.INVISIBLE);
                similarBtn.setVisibility(View.INVISIBLE);
                curatorBtn.setVisibility(View.INVISIBLE);
                swordShieldIv.setVisibility(View.INVISIBLE);
            }
        });


//        // TODO: Add a search prompt which is initially hidden. Touching the search bar will display it
//        searchEditText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return false;
//            }
//        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    searchResultsRecyclerView.setVisibility(View.VISIBLE);
                    genreBtn.setVisibility(View.INVISIBLE);
                    similarBtn.setVisibility(View.INVISIBLE);
                    curatorBtn.setVisibility(View.INVISIBLE);
                    mBooks.clear();
                    search();

                    return true;
                }

                return false;
            }
        });


    }

    private void parseJson(String key, RecyclerView rv) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String isbn = "0";
                        String title ="";
                        String author ="";
                        String publishedDate = "NoT Available";
                        String description = "No Description";
                        int pageCount = 1000;
                        String categories = "No categories Available ";
                        String buy ="";
                        String category = "No Category";
                        int currentPage = 0;

                        String price = "NOT_FOR_SALE";
                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++){
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                if (volumeInfo.has("industryIdentifiers")) {

                                    try {
                                        title = volumeInfo.getString("title");

                                        JSONArray authors = volumeInfo.getJSONArray("authors");
                                        if (authors.length() == 1) {
                                            author = authors.getString(0);
                                        } else {
                                            author = authors.getString(0) + "|" + authors.getString(1);
                                        }


                                        publishedDate = volumeInfo.getString("publishedDate");
                                        pageCount = volumeInfo.getInt("pageCount");


                                        JSONObject saleInfo = item.getJSONObject("saleInfo");
                                        JSONObject listPrice = saleInfo.getJSONObject("listPrice");
                                        price = listPrice.getString("amount") + " " + listPrice.getString("currencyCode");
                                        description = volumeInfo.getString("description");
                                        buy = saleInfo.getString("buyLink");
                                        categories = volumeInfo.getJSONArray("categories").getString(0);

                                        // Check for the ISBN_13
                                        JSONArray isbns = volumeInfo.getJSONArray("industryIdentifiers");
                                        for (int j = 0; j < isbns.length(); j++) {
                                            if (isbns.getJSONObject(j).getString("type").equals("ISBN_13")) {
                                                isbn = volumeInfo.getJSONArray("industryIdentifiers").getJSONObject(j).getString("identifier");
                                            }
                                        }

                                    } catch (Exception e) {

                                    }
                                    String thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
                                    String previewLink = volumeInfo.getString("previewLink");
                                    String url = volumeInfo.getString("infoLink");

                                    Book book = new Book(isbn, title, description, pageCount, thumbnail, author, category, 0);
                                    mBooks.add(book);
//                                    library = getLibrary();

                                    mAdapter = new SearchRecyclerViewAdapter(library, mBooks, SearchActivity.this);
                                    rv.setAdapter(mAdapter);
                                    rv.setLayoutManager(new LinearLayoutManager(mContext, rv.VERTICAL, false));
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("TAG" , e.toString());

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    private boolean Read_network_state(Context context)
    {    boolean is_connected;
        ConnectivityManager cm =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        is_connected=info!=null&&info.isConnectedOrConnecting();
        return is_connected;
    }

    private void search()
    {
//        String search_query = search_edit_text.getText().toString();
        String search_query = searchEditText.getText().toString();

        boolean is_connected = Read_network_state(this);
        if(!is_connected)
        {
            error_message.setText("Failed to load data");
            searchResultsRecyclerView.setVisibility(View.INVISIBLE);
            error_message.setVisibility(View.VISIBLE);
            return;
        }
        String final_query=search_query.replace(" ","+");
        Uri uri=Uri.parse(BASE_URL+final_query);
        Uri.Builder builder = uri.buildUpon();

        parseJson(buildQuery(search_query).toString(), searchResultsRecyclerView);
    }

    private Uri.Builder buildQuery(String searchString) {
        String final_query=searchString.replace(" ","+");
        Uri uri=Uri.parse(BASE_URL+final_query);
        return uri.buildUpon();
    }

}