package com.example.ahmed.movielovers.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ahmed on 23/03/2018.
 */

public class User implements Parcelable {
    private int id, age;
    private String userName, password, email;

    public User() {
    }

    public User(int id, int age, String userName, String password, String email) {
        this.id = id;
        this.age = age;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public User(String userName, String password, String email, int age) {
        this.age = age;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(age);
        parcel.writeString(userName);
        parcel.writeString(password);
        parcel.writeString(email);
    }

    protected User(Parcel in) {
        id = in.readInt();
        age = in.readInt();
        userName = in.readString();
        password = in.readString();
        email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
