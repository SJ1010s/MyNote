package ru.home.mynote;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteStructure implements Parcelable {
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

    protected NoteStructure(Parcel in) {
        id = in.readString();
        title = in.readString();
        descr = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(descr);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteStructure> CREATOR = new Creator<NoteStructure>() {
        @Override
        public NoteStructure createFromParcel(Parcel in) {
            return new NoteStructure(in);
        }

        @Override
        public NoteStructure[] newArray(int size) {
            return new NoteStructure[size];
        }
    };

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
