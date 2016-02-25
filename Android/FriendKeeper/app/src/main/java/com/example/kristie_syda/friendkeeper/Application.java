package com.example.kristie_syda.friendkeeper;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

/**
 * Created by Kristie_Syda on 2/6/16.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "XSvwBB0y1Tof3v7zTu0FvyMwTe8HtBg2lU8Ji7o2", "CU5rm1iL1Ex9hIyJF5XEPsrGtX4aKh8eX46t1oXK");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseUser user = ParseUser.getCurrentUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(false);
        defaultACL.setPublicWriteAccess(false);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
