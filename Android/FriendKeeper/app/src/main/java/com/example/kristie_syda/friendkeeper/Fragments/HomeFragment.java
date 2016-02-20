package com.example.kristie_syda.friendkeeper.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kristie_syda.friendkeeper.ContactObject;
import com.example.kristie_syda.friendkeeper.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


/**
 * Created by Kristie_Syda on 2/4/16.
 */
public class HomeFragment extends Fragment {
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
        ListView list = (ListView) getView().findViewById(R.id.listView);
        list.setEmptyView(getView().findViewById(R.id.empty));
        mAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactObject object = (ContactObject) parent.getAdapter().getItem(position);
                mListener.viewItem(object);
            }
        });
        refreshList();
    }

    //REFRESH LIST
    public void refreshList(){
        mAdapter.clear();
        //Get user data from parse
        ParseQuery<ParseObject> contactQuery = ParseQuery.getQuery("Contacts");
        contactQuery.whereEqualTo("User", mUser);
        contactQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    //loop through objects
                    for (ParseObject contact : objects) {
                        String last = contact.getString("LastName").toString();
                        String first = contact.get("FirstName").toString();
                        int phone = contact.getInt("Phone");
                        String type = contact.get("Type").toString();
                        String objId = contact.getObjectId();
                        ContactObject obj = new ContactObject(first, last, phone, objId,type);
                        mAdapter.add(obj);
                    }
                } else {
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Error: " + e, duration);
                    toast.show();
                }
            }
        });
    }

}
