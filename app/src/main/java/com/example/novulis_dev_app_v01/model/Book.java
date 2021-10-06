package com.example.novulis_dev_app_v01.model;

import java.io.Serializable;

public class Book implements Serializable {

    private String isbn;
    private String title;
    private String description;
    private int pageCount;
    private String cover;
    private String author;
    private String category;
    private int currentPage;
    private String genre;
    private String preview;

    public Book(String isbn, String title, String description, int pageCount, String cover, String author, String category, int currentPage, String genre, String preview) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.pageCount = pageCount;
        this.cover = cover;
        this.author = author;
        this.category = category;
        this.currentPage = currentPage;
        this.genre = genre;
        this.preview = preview;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pageCount=" + pageCount +
                ", cover='" + cover + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", currentPage=" + currentPage +
                '}';
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }
}
