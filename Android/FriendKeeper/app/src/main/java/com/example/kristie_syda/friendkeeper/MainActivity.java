package com.example.kristie_syda.friendkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import com.parse.Parse;
import com.parse.ParseUser;

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
        
        if(savedInstanceState == null){
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                //start Home activity
                Intent hIntent = new Intent(this,HomeActivity.class);
                this.startActivity(hIntent);
            } else {
                //show login fragment
                MainFragment frag = MainFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.holder,frag,MainFragment.TAG).commit();
            }
        }
    }
}
