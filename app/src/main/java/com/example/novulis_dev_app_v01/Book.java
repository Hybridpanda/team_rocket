package com.example.novulis_dev_app_v01;

public class Book {

    private String title;
    private String description;
    private int pageCount;
    private String cover;
    private String author;

    public Book(String title, String description, int pageCount, String cover, String author) {
        this.title = title;
        this.description = description;
        this.pageCount = pageCount;
        this.cover = cover;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
