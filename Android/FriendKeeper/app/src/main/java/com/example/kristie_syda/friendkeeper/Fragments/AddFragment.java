package com.example.kristie_syda.friendkeeper.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.kristie_syda.friendkeeper.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Kristie_Syda on 2/4/16.
 */
public class AddFragment extends Fragment {
    public static final String TAG = "AddFragment.TAG";
    private addListener mListener;

    //FACTORY METHOD
    public static AddFragment newInstance(){
        AddFragment frag = new AddFragment();
        return frag;
    }
    public AddFragment(){
        super();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_add,container,false);
        return view;
    }

    //INTERFACE
    public interface  addListener{
        void saveCompleted();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof addListener){
            mListener = (addListener) activity;
        } else {
            new IllegalArgumentException("Containing activity must implement addListener interface");
        }
    }

    //ACTIVITY CREATED
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TextFields
        final EditText first = (EditText) getView().findViewById(R.id.add_first);
        final EditText last = (EditText) getView().findViewById(R.id.add_last);
        final EditText phone = (EditText) getView().findViewById(R.id.add_phone);

        //Cancel Button
        Button cancel = (Button) getView().findViewById(R.id.btn_addCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        //Save Button
        Button save = (Button) getView().findViewById(R.id.btn_addSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Parse User
                ParseUser currentUser = ParseUser.getCurrentUser();
                ParseObject contact = new ParseObject("Contacts");
                contact.put("User", currentUser);
                contact.put("FirstName",first.getText().toString());
                contact.put("LastName",last.getText().toString());
                contact.put("Phone", Integer.parseInt(phone.getText().toString()));
                contact.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            //contact saved
                            mListener.saveCompleted();
                        } else {
                            System.out.println("something went wrong " + e);
                        }
                    }
                });

            }
        });

    }
}
