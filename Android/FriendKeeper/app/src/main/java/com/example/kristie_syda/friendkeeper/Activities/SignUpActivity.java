package com.example.kristie_syda.friendkeeper.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;

import com.example.kristie_syda.friendkeeper.R;
import com.example.kristie_syda.friendkeeper.Fragments.SignUpFragment;

/**
 * Created by Kristie_Syda on 2/5/16.
 */
public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hide ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if(savedInstanceState == null){
            //Load up SignUp Fragment
            SignUpFragment frag = SignUpFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.holder,frag,SignUpFragment.TAG).commit();
        }
    }
}
