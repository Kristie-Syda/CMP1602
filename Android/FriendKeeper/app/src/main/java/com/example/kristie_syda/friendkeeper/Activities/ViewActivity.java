package com.example.kristie_syda.friendkeeper.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kristie_syda.friendkeeper.ContactObject;
import com.example.kristie_syda.friendkeeper.Fragments.ViewFragment;
import com.example.kristie_syda.friendkeeper.R;
import com.parse.ParseUser;

/**
 * Created by Kristie_Syda on 2/6/16.
 */
public class ViewActivity extends AppCompatActivity implements ViewFragment.viewListener {
    public static final String EXTRA_CONTACT = "com.fullsail.android.EXTRA_CONTACT";
    private ContactObject mObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //show action bar
        ActionBar bar = getSupportActionBar();
        bar.isShowing();

        if(savedInstanceState == null){
            //Load up Add Fragment
            ViewFragment frag = ViewFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.holder,frag,ViewFragment.TAG).commit();
        }
        //get intent extras
        Intent intent = getIntent();
        mObject = (ContactObject) intent.getSerializableExtra(EXTRA_CONTACT);
    }

    //ACTION BAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //Log out
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, "User logged out!", duration);
            toast.show();
            ParseUser.logOut();
            ParseUser current = ParseUser.getCurrentUser();
            //restart main activity
            Intent main = new Intent(this, MainActivity.class);
            main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            main.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(main);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //INTERFACE METHOD
    @Override
    public void deleteCompleted() {
        Intent main = new Intent();
        setResult(RESULT_OK, main);
        finish();
    }

    @Override
    public ContactObject getObject() {
        return mObject;
    }
}
