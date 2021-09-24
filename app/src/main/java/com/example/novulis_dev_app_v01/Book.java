package com.example.novulis_dev_app_v01;

public class Book {

    private String title;
    private String blurb;
    private int pageCount;
    private String cover;

    public Book(String title, String blurb, int pageCount, String cover) {
        this.title = title;
        this.blurb = blurb;
        this.pageCount = pageCount;
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
