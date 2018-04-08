package com.example.ahmed.movielovers.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Movie implements Parcelable {
    private String name, shortDescription, img, rate, date, description;
    private List<Cinema> cinemas;

    public Movie() {
    }

    public List<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Movie(String name, String shortDescription, String img) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(shortDescription);
        parcel.writeString(img);
        parcel.writeString(rate);
        parcel.writeString(date);
        parcel.writeString(description);
        parcel.writeTypedList(cinemas);
    }

    private Movie(Parcel in) {
        name = in.readString();
        shortDescription = in.readString();
        img = in.readString();
        rate = in.readString();
        date = in.readString();
        description = in.readString();
        cinemas = in.createTypedArrayList(Cinema.CREATOR);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
