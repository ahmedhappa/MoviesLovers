package com.example.ahmed.movielovers.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cinema implements Parcelable {
    private String cinemaImage;
    private String name;
    private String location;
    private String policy;
    private String Price;
    private double lat, lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }



    public String getName() {
        return name;
    }

    public Cinema() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCinemaImage() {
        return cinemaImage;
    }

    public void setCinemaImage(String cinemaImage) {
        this.cinemaImage = cinemaImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cinemaImage);
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(policy);
        parcel.writeString(Price);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
    }

    private Cinema(Parcel in) {
        cinemaImage = in.readString();
        name = in.readString();
        location = in.readString();
        policy = in.readString();
        Price = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<Cinema> CREATOR = new Creator<Cinema>() {
        @Override
        public Cinema createFromParcel(Parcel in) {
            return new Cinema(in);
        }

        @Override
        public Cinema[] newArray(int size) {
            return new Cinema[size];
        }
    };
}
