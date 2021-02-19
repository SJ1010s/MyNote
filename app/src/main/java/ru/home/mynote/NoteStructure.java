package ru.home.mynote;

public class NoteStructure {
    private String title;
    private String descr;
    private String date;

    public NoteStructure(String title, String descr, String date) {
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
}
