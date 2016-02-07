package com.example.kristie_syda.friendkeeper;

import java.io.Serializable;

/**
 * Created by Kristie_Syda on 2/7/16.
 */
public class ContactObject implements Serializable {
    private String mFirst;
    private String mLast;
    private String mObjectId;
    private int mPhone;

    //Getters
    public String getmFirst() {
        return mFirst;
    }
    public String getmLast() {
        return mLast;
    }
    public int getmPhone() {
        return mPhone;
    }
    public String getmObjectId() {
        return mObjectId;
    }
    public String toString(){
        return mLast + ", " + mFirst;
    }

    public ContactObject(String first, String last, int phone, String objectId){
        mFirst = first;
        mLast = last;
        mPhone = phone;
        mObjectId = objectId;
    }
}
