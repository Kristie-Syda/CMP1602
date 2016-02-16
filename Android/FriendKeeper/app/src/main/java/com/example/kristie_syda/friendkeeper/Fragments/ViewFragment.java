package com.example.kristie_syda.friendkeeper.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kristie_syda.friendkeeper.ContactObject;
import com.example.kristie_syda.friendkeeper.NetworkConnection;
import com.example.kristie_syda.friendkeeper.R;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.ParseException;


/**
 * Created by Kristie_Syda on 2/4/16.
 */
public class ViewFragment extends Fragment implements TextWatcher {
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
    @Override
    public void afterTextChanged(Editable s) {

    }

    //INTERFACE
    public interface  viewListener{
        void deleteCompleted();
        ContactObject getObject();
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

        //Set TextFields
        final EditText first = (EditText) getView().findViewById(R.id.view_first);
        first.setText(mListener.getObject().getmFirst());

        final EditText last = (EditText) getView().findViewById(R.id.view_last);
        last.setText(mListener.getObject().getmLast());

        final EditText phone = (EditText) getView().findViewById(R.id.view_phone);
        phone.setText(Integer.toString(mListener.getObject().getmPhone()));

        //Okay Button
        Button okay = (Button) getView().findViewById(R.id.btn_viewOkay);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check fields
                if ((first.getText().toString().length() == 0) | (last.getText().toString().length() == 0) | (phone.getText().toString().length() == 0)) {
                    //Alert box
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Alert");
                    alert.setMessage("please leave no fields blank");
                    alert.setPositiveButton("OKAY", null);
                    alert.show();
                } else {
                    // Update info in parse
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Contacts");
                    query.getInBackground(mListener.getObject().getmObjectId(), new GetCallback<ParseObject>() {
                        public void done(ParseObject contact, com.parse.ParseException e) {
                            if (e == null) {
                                contact.put("FirstName", first.getText().toString());
                                contact.put("LastName", last.getText().toString());
                                try {
                                    contact.put("Phone", Integer.parseInt(phone.getText().toString()));
                                } catch (NumberFormatException er) {
                                    //Alert box
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setTitle("Alert");
                                    alert.setMessage("phone number is incorrect");
                                    alert.setPositiveButton("OKAY", null);
                                    alert.show();
                                    er.printStackTrace();
                                }
                                contact.saveInBackground();
                            }
                        }
                    });
                    mListener.deleteCompleted();
                }
            }
        });

        //Delete Button
        Button delete = (Button) getView().findViewById(R.id.btn_viewDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if not connected to internet show Alert
                if(!NetworkConnection.isConnected(getActivity().getApplicationContext())){
                    AlertDialog.Builder connectionAlert = new AlertDialog.Builder(getActivity());
                    connectionAlert.setTitle("No Internet Connection");
                    connectionAlert.setMessage("Must have Internet Connection to delete contacts.");
                    connectionAlert.setPositiveButton("Okay",null);
                    connectionAlert.show();
                } else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Contacts");
                    query.getInBackground(mListener.getObject().getmObjectId(), new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, com.parse.ParseException e) {
                            if (e == null) {
                                object.deleteInBackground();
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Contact was deleted!", duration);
                                toast.show();
                                mListener.deleteCompleted();
                            } else {
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Error: " + e, duration);
                                toast.show();
                            }
                        }
                    });
                }
            }
        });


    }
}
