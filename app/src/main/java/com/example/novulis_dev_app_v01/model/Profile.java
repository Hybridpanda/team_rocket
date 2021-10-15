package com.example.novulis_dev_app_v01.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable {

    private ArrayList<Book> library;
    private ArrayList<Log> bookLog;
    private String currentBook;
    private String currentShip;
    private int pagesRead;

    private static  final  String BASE_URL="https://www.googleapis.com/books/v1/volumes?q=intitle:";
    RequestQueue mRequestQueue;

    public Profile() {
        library = new ArrayList<>();
        bookLog = new ArrayList<>();
        currentShip = "Rocket";
        pagesRead = 60;
    }

    public void saveBookLog(Context mContext) {

        try {
            System.out.println("Saving book log");
            File file = new File(mContext.getFilesDir()+"/bookLog.txt");
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream s = new ObjectOutputStream(f);
            s.writeObject(bookLog);
            System.out.println("Book log saved to file");
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
            android.util.Log.e("TAG" , e.toString());
        }

    }

    public void loadBookLog(Context mContext) {

        try {
            File file = new File(mContext.getFilesDir()+"/bookLog.txt");
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream s = new ObjectInputStream(f);
            bookLog = (ArrayList) s.readObject();
            s.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void saveProfile(Context mContext) {

        ArrayList<String> profileDetails = new ArrayList<>();

        profileDetails.add(currentBook);
        profileDetails.add(currentShip);
        profileDetails.add(pagesRead + "");

        try {
            System.out.println("Saving Profile");
            File file = new File(mContext.getFilesDir()+"/profile.txt");
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream s = new ObjectOutputStream(f);
            s.writeObject(profileDetails);
            System.out.println("Profile saved to file");
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
            android.util.Log.e("TAG" , e.toString());
        }
    }

    public void saveLibrary(Context mContext) {
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
            android.util.Log.e("TAG" , e.toString());
        }
    }

    public void loadProfile(Context mContext) {

        ArrayList<String> profileDetails = new ArrayList<>();

        try {
            File file = new File(mContext.getFilesDir()+"/profile.txt");
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream s = new ObjectInputStream(f);
            profileDetails = (ArrayList) s.readObject();
            s.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        currentBook = profileDetails.get(0);
        currentShip = profileDetails.get(1);
        pagesRead = Integer.parseInt(profileDetails.get(2));

    }

    public void loadLibrary(Context mContext) {
        try {
            File file = new File(mContext.getFilesDir()+"/library.txt");
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream s = new ObjectInputStream(f);
            library = (ArrayList) s.readObject();

            System.out.println("" + library.toString());
            s.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createLibrary(Context mContext) {
        mRequestQueue = Volley.newRequestQueue(mContext);

        boolean is_connected = Read_network_state(mContext);
        if(!is_connected)
        {
            return;
        }
        // Add Philosopher's Stone to currently reading
        String searchString = "Philosopher's+Stone+inauthor:rowling";
        String final_query=searchString.replace(" ","+");
        Uri uri=Uri.parse(BASE_URL+final_query);
        Uri.Builder builder = uri.buildUpon();
        System.out.println(builder.toString());
        parseJson(builder.toString(), mContext, "Currently Reading");

        // Add Percy Jackson to club books
        searchString = "Percy+Jackson+inauthor:Riordan";
        final_query=searchString.replace(" ","+");
        uri=Uri.parse(BASE_URL+final_query);
        builder = uri.buildUpon();
        System.out.println(builder.toString());
        parseJson(builder.toString(), mContext, "Club Books");

        // Add Lord of the Rings to read again
        searchString = "Lord+of+the+Rings+inauthor:Tolkien";
        final_query=searchString.replace(" ","+");
        uri=Uri.parse(BASE_URL+final_query);
        builder = uri.buildUpon();
        System.out.println(builder.toString());
        parseJson(builder.toString(), mContext, "Read Again");
    }

    private boolean Read_network_state(Context context)
    {    boolean is_connected;
        ConnectivityManager cm =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        is_connected=info!=null&&info.isConnectedOrConnecting();
        return is_connected;
    }

    private void parseJson(String key, Context mContext, String category) {

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
                        String genre = "No genre available";
                        String previewLink = "No preview available";
                        String buy ="";
                        int currentPage = 0;

                        int booksToAdd = 1;

                        String price = "NOT_FOR_SALE";
                        try {
                            JSONArray items = response.getJSONArray("items");

                            // We have to check for an ISBN 13 before adding or displaying any
                            // books, as we need it as a unique identifier. If it doesn't have one
                            // move on to the next book in the search results.

                            if (category.equals("Currently Reading")) {
                                currentPage = 150;
                                booksToAdd = 1;
                            } else if(category.equals("Club Books")) {
                                currentPage = 50;
                                booksToAdd = items.length();
                            } else if(category.equals("Read Again")) {
                                booksToAdd = items.length();
                            }

                            for (int j = 0; j < booksToAdd; j++) {



                                JSONObject item = items.getJSONObject(j);
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


                                        genre = volumeInfo.getJSONArray("categories").getString(0);
                                        JSONArray isbns = volumeInfo.getJSONArray("industryIdentifiers");
                                        for (int i = 0; i < isbns.length(); i++) {
                                            if (isbns.getJSONObject(i).getString("type").equals("ISBN_13")) {
                                                isbn = volumeInfo.getJSONArray("industryIdentifiers").getJSONObject(i).getString("identifier");
                                            }
                                        }


                                        previewLink = volumeInfo.getString("previewLink");
                                        String url = volumeInfo.getString("infoLink");

                                    } catch (Exception e) {
                                        android.util.Log.e(key.toString(), e.toString());
                                        e.printStackTrace();
                                    }

                                    if (category.equals("Read Again")) {
                                        currentPage = pageCount;
                                    }
                                    if (currentPage >= pageCount) {currentPage = pageCount;}

                                    Book book = new Book(isbn, title, description, pageCount, thumbnail, author, category, currentPage, genre, previewLink);
                                    System.out.println(book.toString());
                                    library.add(book);
                                }
                            }
                            try {
                                System.out.println("Saving library");
                                File file = new File(mContext.getFilesDir()+"/library.txt");
                                FileOutputStream f = new FileOutputStream(file);
                                ObjectOutputStream s = new ObjectOutputStream(f);
                                s.writeObject(library);
                                setCurrentBook(library.get(0).getTitle());
                                System.out.println("Library saved to file");
                                saveProfile(mContext);
                                f.flush();
                                f.close();
                                s.flush();
                                s.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                android.util.Log.e("TAG" , e.toString());
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            android.util.Log.e("TAG" , e.toString());

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

    public ArrayList<Book> getLibrary(Context mContext) {
        loadLibrary(mContext);
        return library;
    }

    public String[] getBookTitles() {
        String[] bookTitles = new String[library.size()];
        for (int i = 0; i < library.size(); i++) {
            bookTitles[i] = library.get(i).getTitle();
        }
        return bookTitles;
    }

    public void setLibrary(ArrayList<Book> library) {
        this.library = library;
    }

    public ArrayList<Log> getBookLog() {
        return bookLog;
    }

    public void setBookLog(ArrayList<Log> bookLog) {
        this.bookLog = bookLog;
    }

    public String getCurrentBook() {
        return currentBook;
    }

    public void setCurrentBook(String currentBook) {
        this.currentBook = currentBook;
    }

    public int getPagesRead() {return pagesRead;}

    public void setPagesRead(int pagesRead) {this.pagesRead = pagesRead;}

    public String getCurrentShip() { return currentShip; }

    public void setCurrentShip(String currentShip) { this.currentShip = currentShip; }

    public void addLog(Log log) {
        for (Book book : library) {
            if (book.getTitle().equals(log.getBookTitle())) {
                int currentPage = book.getCurrentPage();
                int pageCount = book.getPageCount();
                int newPage = currentPage + log.getPages();
                System.out.println("Current Page: " + currentPage + ", Page Count: " + pageCount + "New Page: " + newPage);

                if (newPage < book.getPageCount()) {
                    book.setCurrentPage(newPage);
                } else if (newPage >= pageCount) {
                    book.setCurrentPage(pageCount);
                    book.setCategory("Read Again");
                }
                bookLog.add(log);
                break;
            }
        }

    }

    @Override
    public String toString() {
        return "Profile{" +
                "library=" + library.toString() +
                ", bookLog=" + bookLog.toString() +
                ", currentBook='" + currentBook + '\'' +
                '}';
    }
}
