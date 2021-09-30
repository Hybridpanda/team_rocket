package com.example.novulis_dev_app_v01.activities;

import static android.os.FileUtils.copy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.adapters.RecyclerViewAdapter;
import com.example.novulis_dev_app_v01.fragments.HomeFragment;
import com.example.novulis_dev_app_v01.fragments.LibraryFragment;
import com.example.novulis_dev_app_v01.fragments.LogFragment;
import com.example.novulis_dev_app_v01.fragments.ProfileFragment;
import com.example.novulis_dev_app_v01.fragments.SocialFragment;
import com.example.novulis_dev_app_v01.model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NavigationActivity extends AppCompatActivity {

    private static final int IO_BUFFER_SIZE = 4 * 1024;
    private static final String TAG = "TAG";
    // Nav bar variables
    BottomNavigationView bottomNavigationView;
    NavigationBarItemView navigationBarItemView;
    FrameLayout frameLayout;

    // --Library view variables--
    String testBookJson = "https://www.googleapis.com/books/v1/volumes?q=isbn:9780007128457";
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
    private ArrayList<Book> mBooks;
    private RecyclerViewAdapter mAdapter;
    private RequestQueue mRequestQueue;

    private static  final  String BASE_URL="https://www.googleapis.com/books/v1/volumes?q=title:";

    private TextView error_message;

    //ArrayList<Book> library;
    ArrayList<Book> library = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        mContext = this;

        mRequestQueue = Volley.newRequestQueue(this);
        // Load the fragments and set listener for nav bar
        loadFragments(new HomeFragment());
        try {
            loadLibrary();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                int id = item.getItemId();

                switch (id) {
                    case R.id.homeFragment:
                        fragment = new HomeFragment();
                        break;
                    case R.id.profileFragment:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.logFragment:
                        fragment = new LogFragment();
                        break;
                    case R.id.socialFragment:
                        fragment = new SocialFragment();
                        break;
                    case R.id.libraryFragment:
                        fragment = new LibraryFragment();
                        break;
                }

                return loadFragments(fragment);
            }
        });

//        discoverBtn = findViewById(R.id.discoverBtn);
//        bookTitleTv = findViewById(R.id.bookTitleTv);
//        authorTv = findViewById(R.id.authorTv);
//        bookCoverIv = findViewById(R.id.bookCoverIv);
//
//        currentlyReadingRecyclerView = findViewById(R.id.currentlyReadingRV);
//        currentlyReadingRecyclerView.setHasFixedSize(true);
//        currentlyReadingRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//
//        clubBooksRecyclerView = findViewById(R.id.clubBooksRV);
//        clubBooksRecyclerView.setHasFixedSize(true);
//        clubBooksRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//
//        readAgainRecyclerView = findViewById(R.id.readAgainRV);
//        readAgainRecyclerView.setHasFixedSize(true);
//        readAgainRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));



    }

    public boolean loadFragments(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .commit();
        }
        return true;
    }

    public void loadLibrary() throws IOException {
        String[] currentBooks = {
                "Deathly Hallows",
                "Do androids dream of electric sheep",
                "Coraline"
        };
        String[] clubBooks = {
                "Sea of monsters",
                "Goblet of Fire"
        };
        String[] readAgainBooks = {
                "Lightning Thief",
                "chamber of secrets",
                "philosophers stone"
        };

        boolean is_connected = Read_network_state(mContext);
        if(!is_connected)
        {
            error_message.setText("Failed to load data");
            currentlyReadingRecyclerView.setVisibility(View.INVISIBLE);
            error_message.setVisibility(View.VISIBLE);
            return;
        }
//        for (int i=0; i< currentBooks.length;i++) {
//            addBook(currentBooks[i], "Currently Reading", 50);
//        }
//        for (int i=0; i< clubBooks.length;i++) {
//            addBook(clubBooks[i], "Club Books", 20);
//        }
//        for (int i=0; i< readAgainBooks.length;i++) {
//            addBook(readAgainBooks[i], "Read Again", -1);
//        }

        addBook("fantasy", "Currently Reading", 0);
    }

    private void parseJson(String key, int currentPage) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        RecyclerView rv = currentlyReadingRecyclerView;
                        String title ="";
                        String author ="";
                        String publishedDate = "NoT Available";
                        String description = "No Description";
                        String thumbnail = "";
                        String isbn = "0";
                        int pageCount = 1000;
                        String categories = "No categories Available ";
                        String category = "Club Books";
                        String buy ="";

                        String price = "NOT_FOR_SALE";
                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int j = 0; j < items.length(); j++) {
                                if (j%3 == 0){
                                    category = "Currently Reading";
                                } else if (j%3 == 1) {
                                    category = "Read Again";
                                } else {
                                    category = "Club Books";
                                }
                                JSONObject item = items.getJSONObject(j);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");


                                try {
                                    title = volumeInfo.getString("title");

                                    JSONArray authors = volumeInfo.getJSONArray("authors");
                                    if (authors.length() == 1) {
                                        author = authors.getString(0);
                                    } else {
                                        author = authors.getString(0) + "|" + authors.getString(1);
                                    }

                                    // Error handling...
                                    /** A large number of books appear to have items missing
                                     *  I'm just checking that the key exists before trying to save it,
                                     *  otherwise I'm just sticking in a placeholder value
                                     */
                                    publishedDate = volumeInfo.getString("publishedDate");
                                    if (volumeInfo.has("pageCount")) {
                                        pageCount = volumeInfo.getInt("pageCount");
                                    } else {
                                        pageCount = 0;
                                    }

                                    if (volumeInfo.has("description")) {
                                        description = volumeInfo.getString("description");
                                    }

                                    if (volumeInfo.has("imageLinks")) {
                                        thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
                                    } else {
                                        thumbnail = ("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fbooks.google.com%2Fbooks%2Fcontent%3Fid%3DwV-YGQAACAAJ%26printsec%3Dfrontcover%26img%3D1%26zoom%3D1&f=1&nofb=1");
                                    }

                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    if (saleInfo.getString("saleability").equals("FOR_SALE")) {
                                        JSONObject listPrice = saleInfo.getJSONObject("listPrice");
                                        price = listPrice.getString("amount") + " " + listPrice.getString("currencyCode");
                                        buy = saleInfo.getString("buyLink");
                                    }


                                    categories = volumeInfo.getJSONArray("categories").getString(0);
                                    JSONArray isbns = volumeInfo.getJSONArray("industryIdentifiers");
                                    for (int i = 0; i < isbns.length(); i++) {
                                        if (isbns.getJSONObject(i).getString("type").equals("ISBN_13")) {
                                            isbn = volumeInfo.getJSONArray("industryIdentifiers").getJSONObject(i).getString("identifier");
                                        }
                                    }


                                    String previewLink = volumeInfo.getString("previewLink");
                                    String url = volumeInfo.getString("infoLink");

                                } catch (Exception e) {
                                    Log.e(key.toString(), e.toString());
                                    e.printStackTrace();
                                }
                                //                                mBooks.add(new Book(title , author , publishedDate , description ,categories
                                //                                        ,thumbnail,buy,previewLink,price,pageCount,url));
                                int pageNo = 0;
                                if (currentPage < 0 || currentPage >= pageCount) {
                                    pageNo = pageCount;
                                    category = "Read Again";
                                }

                                Book book = new Book(title, description, pageCount, thumbnail, author, category, pageNo);
                                System.out.println(book.toString());
                                library.add(book);
                            }

                            //mBooks.add(new Book(title, description, pageCount, thumbnail, author, category, currentPage));


//                            mAdapter = new RecyclerViewAdapter(mContext , mBooks);
//                            if (newCategory.equals("Currently Reading")) {
//                                rv = currentlyReadingRecyclerView;
//                            } else if (newCategory.equals("Club Books")) {
//                                rv = clubBooksRecyclerView;
//                            } else if (newCategory.equals("Read Again")) {
//                                rv = readAgainRecyclerView;
//                            }
//                            rv.setAdapter(mAdapter);
//                            rv.setLayoutManager(new LinearLayoutManager(mContext, rv.HORIZONTAL, false));
                            try {
                                System.out.println("Saving library");
                                File file = new File(mContext.getFilesDir()+"/library.txt");
                                FileOutputStream f = new FileOutputStream(file);
                                ObjectOutputStream s = new ObjectOutputStream(f);
                                s.writeObject(library);
                                System.out.println("Library saved to file");
                                s.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e("TAG" , e.toString());
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
        ConnectivityManager cm =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        is_connected=info!=null&&info.isConnectedOrConnecting();
        return is_connected;
    }

    private void search() throws IOException {
//        String search_query = search_edit_text.getText().toString();
        String[] currentBooks = {
                "Deathly Hallows",
                "Do androids dream of electric sheep",
                "Coraline"
        };
        String[] clubBooks = {
                "Sea of monsters",
                "Goblet of Fire"
        };
        String[] readAgainBooks = {
                "Lightning Thief",
                "chamber of secrets",
                "philosophers stone"
        };

        boolean is_connected = Read_network_state(mContext);
        if(!is_connected)
        {
            error_message.setText("Failed to load data");
            currentlyReadingRecyclerView.setVisibility(View.INVISIBLE);
            error_message.setVisibility(View.VISIBLE);
            return;
        }
//        for (int i=0; i< currentBooks.length;i++) {
//            addBook(currentBooks[i], "Currently Reading", 50);
//        }
//        for (int i=0; i< clubBooks.length;i++) {
//            addBook(currentBooks[i], "Club Books", 20);
//        }
//        for (int i=0; i< readAgainBooks.length;i++) {
//            addBook(currentBooks[i], "Read Again", -1);
//        }

        addBook("fantasy", "Currently Reading", 0);

//        String final_query=search_query.replace(" ","+");
//        Uri uri=Uri.parse(BASE_URL+final_query);
//        Uri.Builder builder = uri.buildUpon();
//        parseJson(builder.toString(), currentlyReadingRecyclerView, "Currently Reading", 50);
//        parseJson(builder.toString(), clubBooksRecyclerView, "Club Books", 20);
//        parseJson(builder.toString(), readAgainRecyclerView, "Read Again", -1);
    }

    private void addBook(String searchString, String category, int page) {
        String final_query=searchString.replace(" ","+");
        Uri uri=Uri.parse(BASE_URL+final_query);
        Uri.Builder builder = uri.buildUpon();
        System.out.println(builder.toString());
        parseJson(builder.toString(), page);

    }

}

