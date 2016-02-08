package com.example.kristie_syda.friendkeeper;

/**
 * Created by Kristie_Syda on 2/7/16.
 */
public class ContactObject {
    private String mFirst;
    private String mLast;
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
    public String toString(){
        return mLast + ", " + mFirst;
    }

    public ContactObject(String first, String last, int phone){
        mFirst = first;
        mLast = last;
        mPhone = phone;
    }
}
