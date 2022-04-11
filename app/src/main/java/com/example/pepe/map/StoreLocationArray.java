package com.example.pepe.map;

import java.util.ArrayList;

public class StoreLocationArray {

    private ArrayList<StoreLocation> storeLocationArray = new ArrayList<StoreLocation>();

    public StoreLocationArray (ArrayList<StoreLocation> storeLocationArray_) {

        System.out.println(storeLocationArray_.get(0));
        this.storeLocationArray = storeLocationArray_;
    }

    public void setUserProfiles(ArrayList<StoreLocation> storeLocationArray_) {
        this.storeLocationArray = storeLocationArray_;
    }

    public ArrayList<StoreLocation> getStoreLocationArray() {
        return storeLocationArray;
    }


}
