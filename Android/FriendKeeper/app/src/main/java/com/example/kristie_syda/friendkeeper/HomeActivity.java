package com.example.kristie_syda.friendkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;

/**
 * Created by Kristie_Syda on 2/5/16.
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //show action bar
        ActionBar bar = getSupportActionBar();
        bar.isShowing();

        if(savedInstanceState == null){
            //Load up Main Fragment
            HomeFragment frag = HomeFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.holder,frag,HomeFragment.TAG).commit();
        }
    }

    //ACTION BAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //Log out
            ParseUser.logOut();
            ParseUser current = ParseUser.getCurrentUser();
            //restart main activity
            Intent main = new Intent(this, MainActivity.class);
            main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            main.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(main);
            return true;
        } else if(id == R.id.action_add){
            //start add activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
