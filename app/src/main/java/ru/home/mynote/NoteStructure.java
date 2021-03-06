package ru.home.mynote;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class NoteStructure implements Serializable {
    private String id;
    private String title;
    private String date;
    private String descr;

    public NoteStructure(String id, String title, String descr, String date) {
        this.id = id;
        this.title = title;
        this.descr = descr;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }
}
