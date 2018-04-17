package com.example.ivanovnv.recycletest.mock;

/**
 * Created by IvanovNV on 17.04.2018.
 */

public class Mock {

    private String mName;
    private int mValue;

    public Mock(String mName, int mValue) {
        this.mName = mName;
        this.mValue = mValue;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getValue() {
        return String.valueOf(mValue);
    }

    public void setValue(int mValue) {
        this.mValue = mValue;
    }
}


