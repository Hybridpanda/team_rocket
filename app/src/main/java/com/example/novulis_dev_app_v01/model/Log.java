package com.example.novulis_dev_app_v01.model;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {

    private Date logDate;
    private String bookTitle;
    private int pages;
    private String note;
    private int duration;

    public Log(Date logDate, String bookTitle, int pages, String note, int duration) {
        this.logDate = logDate;
        this.bookTitle = bookTitle;
        this.pages = pages;
        this.note = note;
        this.duration = duration;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    @Override
    public String toString() {
        return "Log{" +
                "logDate=" + logDate +
                ", bookTitle='" + bookTitle + '\'' +
                ", pages=" + pages +
                ", note='" + note + '\'' +
                ", duration=" + duration +
                '}';
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
