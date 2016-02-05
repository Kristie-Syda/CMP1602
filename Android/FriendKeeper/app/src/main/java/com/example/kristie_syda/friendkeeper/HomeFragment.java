package com.example.kristie_syda.friendkeeper;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Kristie_Syda on 2/4/16.
 */
public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFragment.TAG";

    //FACTORY METHODS
    public static HomeFragment newInstance(){
        HomeFragment frag = new HomeFragment();
        return frag;
    }
    public HomeFragment(){
        super();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
