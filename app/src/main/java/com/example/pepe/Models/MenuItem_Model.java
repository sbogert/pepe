package com.example.pepe.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItem_Model implements Parcelable {
    private String Name;
    private Double Price;
    private Integer Caffeine;

    public MenuItem_Model(String Name, Double Price, Integer Caffeine){
        this.Caffeine = Caffeine;
        this. Name = Name;
        this.Price = Price;
    }

    protected MenuItem_Model(Parcel in) {
        Name = in.readString();
        if (in.readByte() == 0) {
            Price = null;
        } else {
            Price = in.readDouble();
        }
        if (in.readByte() == 0) {
            Caffeine = null;
        } else {
            Caffeine = in.readInt();
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
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Integer getCaffeine() {
        return Caffeine;
    }

    public void setCaffeine(Integer caffeine) {
        Caffeine = caffeine;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        if (Price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(Price);
        }
        if (Caffeine == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(Caffeine);
        }
    }
}
