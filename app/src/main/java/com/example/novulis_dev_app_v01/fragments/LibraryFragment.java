package com.example.novulis_dev_app_v01.fragments;

import static android.os.FileUtils.copy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.novulis_dev_app_v01.MainActivity;
import com.example.novulis_dev_app_v01.NavigationActivity;
import com.example.novulis_dev_app_v01.SearchActivity;
import com.example.novulis_dev_app_v01.model.Book;
import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.adapters.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LibraryFragment extends Fragment {

    // Constants
    private static final int IO_BUFFER_SIZE = 4 * 1024;
    private static final String TAG = "TAG";

    // Discover button
    Button discoverBtn;

    // Library card test ID's
    TextView bookTitleTv;
    TextView authorTv;
    ImageView bookCoverIv;
    String testBookJson = "https://www.googleapis.com/books/v1/volumes?q=isbn:9780007128457";
    ProgressDialog pd;

    // Copied Variables
    private Context mContext;
    private RecyclerView currentlyReadingRecyclerView;
    private RecyclerView clubBooksRecyclerView;
    private RecyclerView readAgainRecyclerView;
    private ArrayList<Book> mBooks;
    private RecyclerViewAdapter mAdapter;
    private RequestQueue mRequestQueue;

    private static  final  String BASE_URL="https://www.googleapis.com/books/v1/volumes?q=";

    private TextView error_message;


    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
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
        currentlyReadingRecyclerView.setHasFixedSize(true);
        currentlyReadingRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        clubBooksRecyclerView = v.findViewById(R.id.clubBooksRV);
        clubBooksRecyclerView.setHasFixedSize(true);
        clubBooksRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        readAgainRecyclerView = v.findViewById(R.id.readAgainRV);
        readAgainRecyclerView.setHasFixedSize(true);
        readAgainRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        mBooks = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(mContext);

        discoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        // Import users library
        //new JsonTask().execute(testBookJson);
        search();
        if (pd.isShowing()){
            pd.dismiss();
        }

        return v;
    }

//    public void parseJson(String stringFromInputStream) {
//        String strParsed = null;
//
//        try {
//            JSONObject jsonObject = new JSONObject(stringFromInputStream);
//
//            JSONArray jArray = ((JSONObject) jsonObject).getJSONArray("items");
//            for(int i = 0; i < jArray.length(); i++){
//                JSONObject volumeInfo = jArray.getJSONObject(i).getJSONObject("volumeInfo");
//
//                String title = volumeInfo.getString("title");
//                String author = volumeInfo.getJSONArray("authors").getString(0);
//                String cover = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
//                System.out.println(title + ", " + author);
//
//                bookTitleTv.setText(title);
//                authorTv.setText(author);
////                bookCoverIv.setImageBitmap(loadBitmap(cover));
////
////
////                JSONArray authors = volumeInfo.getJSONArray("authors");
////                for(int j =0; j< authors.length(); j++){
////                    String author = authors.getString(i);
////                }
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    private void parseJson(String key, RecyclerView rv) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String title ="";
                        String author ="";
                        String publishedDate = "NoT Available";
                        String description = "No Description";
                        int pageCount = 1000;
                        String categories = "No categories Available ";
                        String buy ="";

                        String price = "NOT_FOR_SALE";
                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0 ; i< items.length() ;i++){
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");



                                try{
                                    title = volumeInfo.getString("title");

                                    JSONArray authors = volumeInfo.getJSONArray("authors");
                                    if(authors.length() == 1){
                                        author = authors.getString(0);
                                    }else {
                                        author = authors.getString(0) + "|" +authors.getString(1);
                                    }


                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");



                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");
                                    price = listPrice.getString("amount") + " " +listPrice.getString("currencyCode");
                                    description = volumeInfo.getString("description");
                                    buy = saleInfo.getString("buyLink");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);

                                }catch (Exception e){

                                }
                                String thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");
                                String url = volumeInfo.getString("infoLink");


//                                mBooks.add(new Book(title , author , publishedDate , description ,categories
//                                        ,thumbnail,buy,previewLink,price,pageCount,url));
                                mBooks.add(new Book(title, description, pageCount, thumbnail, author));


                                mAdapter = new RecyclerViewAdapter(mContext , mBooks);
                                rv.setAdapter(mAdapter);
                                rv.setLayoutManager(new LinearLayoutManager(mContext, rv.HORIZONTAL, false));
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
//    private class JsonTask extends AsyncTask<String, String, String> {
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            pd.setMessage("Please wait");
//            pd.setCancelable(false);
//            pd.show();
//        }
//
//        protected String doInBackground(String... params) {
//
//
//            HttpURLConnection connection = null;
//            BufferedReader reader = null;
//
//            try {
//                URL url = new URL(params[0]);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//
//
//                InputStream stream = connection.getInputStream();
//
//                reader = new BufferedReader(new InputStreamReader(stream));
//
//                StringBuffer buffer = new StringBuffer();
//                String line = "";
//
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line+"\n");
//                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
//
//                }
//
//                return buffer.toString();
//
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (connection != null) {
//                    connection.disconnect();
//                }
//                try {
//                    if (reader != null) {
//                        reader.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            if (pd.isShowing()){
//                pd.dismiss();
//            }
//            parseJson(result);
//        }
//    }

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
        String search_query = "Harry potter";

        boolean is_connected = Read_network_state(mContext);
        if(!is_connected)
        {
            error_message.setText("Failed to load data");
            currentlyReadingRecyclerView.setVisibility(View.INVISIBLE);
            error_message.setVisibility(View.VISIBLE);
            return;
        }

        //  Log.d("QUERY",search_query);


//        if(search_query.equals(""))
//        {
//            Toast.makeText(this,"Please enter your query",Toast.LENGTH_SHORT).show();
//            return;
//        }
        String final_query=search_query.replace(" ","+");
        Uri uri=Uri.parse(BASE_URL+final_query);
        Uri.Builder builder = uri.buildUpon();

        parseJson(buildQuery("Harry Potter").toString(), currentlyReadingRecyclerView);
        parseJson(buildQuery("Percy Jackson").toString(), clubBooksRecyclerView);
        parseJson(buildQuery("Dune").toString(), readAgainRecyclerView);
    }

    private Uri.Builder buildQuery(String searchString) {
        String final_query=searchString.replace(" ","+");
        Uri uri=Uri.parse(BASE_URL+final_query);
        //Uri.Builder builder = uri.buildUpon();
        return uri.buildUpon();
    }



}