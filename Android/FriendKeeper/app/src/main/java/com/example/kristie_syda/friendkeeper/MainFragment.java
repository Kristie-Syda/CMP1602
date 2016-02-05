package com.example.kristie_syda.friendkeeper;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Kristie_Syda on 2/4/16.
 */
public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment.TAG";

    //FACTORY METHODS
    public static MainFragment newInstance(){
        MainFragment frag = new MainFragment();
        return frag;
    }
    public MainFragment(){
        super();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Sign Up Button & Listener
        Button signUp = (Button) getView().findViewById(R.id.btn_signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sIntent = new Intent(getActivity(),SignUpActivity.class);
                startActivity(sIntent);
            }
        });

        //Login Button & Listener
        Button login = (Button) getView().findViewById(R.id.btn_login);
    }
}
