package com.example.kristie_syda.friendkeeper.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kristie_syda.friendkeeper.R;


/**
 * Created by Kristie_Syda on 2/4/16.
 */
public class ViewFragment extends Fragment {
    public static final String TAG = "ViewFragment.TAG";
    private viewListener mListener;

    //FACTORY METHOD
    public static ViewFragment newInstance(){
        ViewFragment frag = new ViewFragment();
        return frag;
    }
    public ViewFragment(){
        super();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_view,container,false);
        return view;
    }

    //INTERFACE
    public interface  viewListener{
        void deleteCompleted();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof viewListener){
            mListener = (viewListener) activity;
        } else {
            new IllegalArgumentException("Containing activity must implement viewListener interface");
        }
    }

    //ACTIVITY CREATED
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //TextFields
        TextView first = (TextView) getView().findViewById(R.id.view_first);
        TextView last = (TextView) getView().findViewById(R.id.view_last);
        TextView phone = (TextView) getView().findViewById(R.id.view_phone);

    }
}
