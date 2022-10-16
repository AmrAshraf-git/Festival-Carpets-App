package com.example.festivalcarpet.dataModels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.festivalcarpet.R;

import java.util.Random;

public class Carpet implements Parcelable {

    private int id;
    private String carpetCategory;
    private String carpetModel;
    private int[] carpetImg;
    private String carpetColor;
    private String carpetSize;
    private int price;

    public Carpet() {
        carpetImg =new int[3];
        carpetImg[0]= R.drawable.ic_baseline_image;
        id= new Random().nextInt(30);
    }


    protected Carpet(Parcel in) {
        id = in.readInt();
        carpetCategory = in.readString();
        carpetModel = in.readString();
        carpetImg = in.createIntArray();
        carpetColor = in.readString();
        carpetSize = in.readString();
        price = in.readInt();
    }

    public static final Creator<Carpet> CREATOR = new Creator<Carpet>() {
        @Override
        public Carpet createFromParcel(Parcel in) {
            return new Carpet(in);
        }

        @Override
        public Carpet[] newArray(int size) {
            return new Carpet[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarpetCategory() {
        return carpetCategory;
    }

    public void setCarpetCategory(String carpetCategory) {
        this.carpetCategory = carpetCategory;
    }

    public String getCarpetModel() {
        return carpetModel;
    }

    public void setCarpetModel(String carpetModel) {
        this.carpetModel = carpetModel;
    }

    public int[] getCarpetImg() {
        return carpetImg;
    }

    public void setCarpetImg(int[] carpetImg) {
        this.carpetImg = carpetImg;
    }

    public String getCarpetColor() {
        return carpetColor;
    }

    public void setCarpetColor(String carpetColor) {
        this.carpetColor = carpetColor;
    }

    public String getCarpetSize() {
        return carpetSize;
    }

    public void setCarpetSize(String carpetSize) {
        this.carpetSize = carpetSize;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeIntArray(carpetImg);
        dest.writeString(carpetModel);
        dest.writeString(carpetColor);
        dest.writeString(carpetSize);
        dest.writeFloat(price);
    }

    /*public CarpetSpecs getVehicleSpecs() {
        return carpetSpecs;
    }*/

    /*public void setVehicleSpecs(CarpetSpecs carpetSpecs) {
        this.carpetSpecs = carpetSpecs;
    }*/

}
