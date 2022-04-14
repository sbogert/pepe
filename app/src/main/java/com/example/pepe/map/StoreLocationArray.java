package com.example.pepe.map;

import java.util.ArrayList;

public class StoreLocationArray {

    private ArrayList<StoreLocation> storeLocationArray = new ArrayList<StoreLocation>();

    public StoreLocationArray () {
    }

    public void setUserProfiles(ArrayList<StoreLocation> storeLocationArray_) {
        this.storeLocationArray = storeLocationArray_;
    }

    public ArrayList<StoreLocation> getStoreLocationArray() {
        return storeLocationArray;
    }

    public void addStore(StoreLocation newStore) {
        storeLocationArray.add(newStore);
    }


}
