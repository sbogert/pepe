package com.example.pepe.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItem_Model implements Parcelable {
    private String name;
    private Double price;
    private Integer caffeine;

    public MenuItem_Model(String Name, Double Price, Integer Caffeine){
        this.caffeine = Caffeine;
        this.name = Name;
        this.price = Price;
    }

    protected MenuItem_Model(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        if (in.readByte() == 0) {
            caffeine = null;
        } else {
            caffeine = in.readInt();
        }
    }

    public static final Creator<MenuItem_Model> CREATOR = new Creator<MenuItem_Model>() {
        @Override
        public MenuItem_Model createFromParcel(Parcel in) {
            return new MenuItem_Model(in);
        }

        @Override
        public MenuItem_Model[] newArray(int size) {
            return new MenuItem_Model[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCaffeine() {
        return caffeine;
    }

    public void setCaffeine(Integer caffeine) {
        this.caffeine = caffeine;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
        if (caffeine == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(caffeine);
        }
    }
}
