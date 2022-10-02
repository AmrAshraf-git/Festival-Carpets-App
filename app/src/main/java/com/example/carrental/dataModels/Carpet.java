package com.example.carrental.dataModels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.carrental.PriceLabel;
import com.example.carrental.R;

import java.util.Random;

public class Carpet implements Parcelable {
    /*private String companyName;
    private String companyAddress;
    private String carModel;
    private int carImg;
    private int id;
    private double price;
    private PriceLabel priceLabel;
    private String[] specs;
    private String[] bookDetails;*/

    private int id;
    private int carpetBrandImg;
    private int[] carpetImg; //6
    private String carpetModel; //2
    private String carpetColor; //7
    private float carpetRate; //10
    private float carpetSize;

    private String companyName; //3
    private String companyAddress; //4
    private float companyRate; //5
    private float price; //11
    private PriceLabel priceLabel; //12

    //private int doorsNum; //8
    //private int seatingCapacity; //9
    //private String[] specs;
    //private String[] bookDetails;
    //private CarpetSpecs carpetSpecs;

    public Carpet() {
        carpetImg =new int[3];
        carpetImg[0]= R.drawable.img_logo_test;
        priceLabel=PriceLabel.EGYPTIAN_POUND;
        id= new Random().nextInt(30);
    }


    protected Carpet(Parcel in) {
        id = in.readInt();
        carpetBrandImg = in.readInt();
        carpetImg = in.createIntArray();
        carpetModel = in.readString();
        carpetColor = in.readString();
        carpetRate = in.readFloat();
        carpetSize = in.readFloat();
        companyName = in.readString();
        companyAddress = in.readString();
        companyRate = in.readFloat();
        price = in.readFloat();
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
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

    /*public int getDoorsNum() {
        return doorsNum;
    }*/

    /*public void setDoorsNum(int doorsNum) {
        this.doorsNum = doorsNum;
    }*/

    /*public int getSeatingCapacity() {
        return seatingCapacity;
    }*/

    /*public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }*/

    public int getCarpetBrandImg() {
        return carpetBrandImg;
    }

    public void setCarpetBrandImg(int carpetBrandImg) {
        this.carpetBrandImg = carpetBrandImg;
    }

    public float getCarpetSize() {
        return carpetSize;
    }

    public void setCarpetSize(float carpetSize) {
        this.carpetSize = carpetSize;
    }

    public float getCarpetRate() {
        return carpetRate;
    }

    public void setCarpetRate(float carpetRate) {
        this.carpetRate = carpetRate;
    }

    public float getCompanyRate() {
        return companyRate;
    }

    public void setCompanyRate(float companyRate) {
        this.companyRate = companyRate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public PriceLabel getPriceLabel() {
        return priceLabel;
    }

    public void setPriceLabel(PriceLabel priceLabel) {
        this.priceLabel = priceLabel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(carpetBrandImg);
        dest.writeIntArray(carpetImg);
        dest.writeString(carpetModel);
        dest.writeString(carpetColor);
        dest.writeFloat(carpetRate);
        dest.writeFloat(carpetSize);
        dest.writeString(companyName);
        dest.writeString(companyAddress);
        dest.writeFloat(companyRate);
        dest.writeFloat(price);
    }

    /*public CarpetSpecs getVehicleSpecs() {
        return carpetSpecs;
    }*/

    /*public void setVehicleSpecs(CarpetSpecs carpetSpecs) {
        this.carpetSpecs = carpetSpecs;
    }*/

}
