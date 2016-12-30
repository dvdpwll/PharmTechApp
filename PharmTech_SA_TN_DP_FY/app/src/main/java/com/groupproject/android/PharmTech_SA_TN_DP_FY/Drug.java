package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.util.Log;

import java.util.Random;
import java.util.UUID;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * Drug: Class to hold information about each drug
 */
public class Drug {
    private static final String TAG = "Drug";
    private int mId;
    private String mGeneric_Name;
    private String mBrand_Name;
    private String mPurpose;
    private String mDEA_Sch;
    private String mSpecial;
    private String mCategory;
    private String mStudy_Topic;
    private String mNotes;
    private boolean mBoolean;

    //Constructor
    public Drug () {
        //this(UUID.randomUUID());
        this((int)Math.random());
    }

    public Drug (int id) {
        mId = id;
    }

    public int getId () {
        return mId;
    }

    public String getGeneric_Name () {
        return mGeneric_Name;
    }

    public void setGeneric_Name (String Generic_Name) {
        mGeneric_Name = Generic_Name;
    }

    public String getBrand_Name () {
        return mBrand_Name;
    }

    public void setBrand_Name (String Brand_Name) {
        mBrand_Name = Brand_Name;
    }

    public String getPurpose () {
        return mPurpose;
    }

    public void setPurpose (String Purpose) {
        mPurpose = Purpose;
    }

    public String getDEA_Sch () {
        return mDEA_Sch;
    }

    public void setDEA_Sch (String DEA_Sch) {
        mDEA_Sch = DEA_Sch;
    }

    public String getSpecial () {
        return mSpecial;
    }

    public void setSpecial (String Special) {
        mSpecial = Special;
    }

    public String getCategory () {
        return mCategory;
    }

    public void setCategory (String Category) {
        mCategory = Category;
    }

    public String getStudy_Topic () {
        return mStudy_Topic;
    }

    public void setStudy_Topic (String Study_Topic) {
        mStudy_Topic = Study_Topic;
    }

    public void setBoolean (boolean aBoolean) {
        mBoolean = aBoolean;
    }

    public boolean isBoolean () {
        return mBoolean;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }
}