package com.example.kristie_syda.friendkeeper.Fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kristie_syda.friendkeeper.ContactObject;
import com.example.kristie_syda.friendkeeper.R;

import com.parse.ParseUser;

import java.util.ArrayList;



/**
 * Created by Kristie_Syda on 2/4/16.
 */
public class HomeFragment extends ListFragment {
    public static final String TAG = "HomeFragment.TAG";
    private ParseUser mUser;
    private ArrayAdapter mAdapter;
    private homeListener mListener;


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

    //INTERFACE
    public interface homeListener{
        void viewItem(ContactObject obj);
        ArrayList<ContactObject> getArray();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof homeListener){
            mListener = (homeListener) activity;
        } else {
            new IllegalArgumentException("Containing activity must implement homeListener interface");
        }
    }

    //ON ACTIVITY CREATED
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Get Parse User
        mUser = ParseUser.getCurrentUser();
        //TextView
        TextView name = (TextView) getView().findViewById(R.id.welcomeLabel);
        //Set Welcome Label
        name.setText("Welcome " + mUser.getUsername());

        //ListView
        ListView list = (ListView) getView().findViewById(android.R.id.list);
        mAdapter = new ArrayAdapter<ContactObject>(getActivity(),android.R.layout.simple_list_item_1,mListener.getArray());
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactObject object = (ContactObject) parent.getAdapter().getItem(position);
                mListener.viewItem(object);
            }
        });
    }

    //REFRESH LIST
    public void refreshList(){
       // mAdapter.clear();
        mAdapter.addAll(mListener.getArray());
    }

}
