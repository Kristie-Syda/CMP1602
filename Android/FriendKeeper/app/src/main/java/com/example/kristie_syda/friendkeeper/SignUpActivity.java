package com.example.kristie_syda.friendkeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Kristie_Syda on 2/5/16.
 */
public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            //Load up Main Fragment
            SignUpFragment frag = SignUpFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.holder,frag,SignUpFragment.TAG).commit();
        }
    }
}
