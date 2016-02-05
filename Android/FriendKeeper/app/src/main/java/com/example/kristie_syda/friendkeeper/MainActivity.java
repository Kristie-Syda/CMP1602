package com.example.kristie_syda.friendkeeper;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import com.parse.Parse;

/**
 * Created by Kristie_Syda on 2/4/16.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hide Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //Init Parse
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

        if(savedInstanceState == null){
            //Load up Main Fragment
            MainFragment frag = MainFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.holder,frag,MainFragment.TAG).commit();
        }
    }
}
