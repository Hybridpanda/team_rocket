package com.example.novulis_dev_app_v01.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.adapters.SearchRecyclerViewAdapter;
import com.example.novulis_dev_app_v01.model.Book;
import com.example.novulis_dev_app_v01.model.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookSimilarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookSimilarFragment extends Fragment {

    String category;

    // Copied Variables
    Profile profile = new Profile();
    private ArrayList library;
    private Context mContext;
    private RecyclerView searchResultsRecyclerView;
    private ArrayList<Book> mBooks;
    private SearchRecyclerViewAdapter mAdapter;
    private RequestQueue mRequestQueue;

    private static  final  String BASE_URL="https://www.googleapis.com/books/v1/volumes?q=subject:";

    public BookSimilarFragment() {
        // Required empty public constructor
    }

    public static BookSimilarFragment newInstance(Bundle extras) {
        BookSimilarFragment fragment = new BookSimilarFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString("book_genre");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_similar, container, false);
        mContext = view.getContext();
        library = profile.getLibrary(mContext);
        mBooks = new ArrayList<>();
        searchResultsRecyclerView = view.findViewById(R.id.similarBooksRv);
        searchResultsRecyclerView.setHasFixedSize(true);
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRequestQueue = Volley.newRequestQueue(mContext);

        mBooks.clear();
        search();

        return view;
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
                        String genre = "No genre available";
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

                                    Book book = new Book(isbn, title, description, pageCount, thumbnail, author, category, 0, genre, previewLink);
                                    mBooks.add(book);
//                                    library = getLibrary();

                                    mAdapter = new SearchRecyclerViewAdapter(library, mBooks, mContext);
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
        String search_query = category;

        boolean is_connected = Read_network_state(mContext);
        if(!is_connected)
        {
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