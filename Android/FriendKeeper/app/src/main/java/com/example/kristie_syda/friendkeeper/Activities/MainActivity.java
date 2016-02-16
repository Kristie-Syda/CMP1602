package com.example.kristie_syda.friendkeeper.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.kristie_syda.friendkeeper.Fragments.MainFragment;
import com.example.kristie_syda.friendkeeper.NetworkConnection;
import com.example.kristie_syda.friendkeeper.R;
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

        //if not connected to internet show toast
        if(!NetworkConnection.isConnected(this.getApplicationContext())){
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this.getApplicationContext(), "No Internet Connection", duration);
            toast.show();
        } else {
            //do nothing
        }

        if(savedInstanceState == null){
            ParseUser currentUser = ParseUser.getCurrentUser();
            //user is already logged in
            if (currentUser != null) {
                //start Home activity
                Intent hIntent = new Intent(this,HomeActivity.class);
                this.startActivity(hIntent);
            } else {
                //no user is logged in
                //show login fragment
                MainFragment frag = MainFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.holder,frag,MainFragment.TAG).commit();
            }
        }
    }


}
