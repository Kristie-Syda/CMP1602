package com.example.kristie_syda.friendkeeper.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kristie_syda.friendkeeper.ContactObject;
import com.example.kristie_syda.friendkeeper.NetworkConnection;
import com.example.kristie_syda.friendkeeper.R;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * Created by Kristie_Syda on 2/4/16.
 */
public class ViewFragment extends Fragment {
    public static final String TAG = "ViewFragment.TAG";
    private viewListener mListener;
    private String mType;

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

        //Spinner
        Spinner spin = (Spinner) getView().findViewById(R.id.view_spin);
        //Array for adapter
        ArrayList<String> list = new ArrayList<>();
        list.add("Home Phone");
        list.add("Cell Phone");
        list.add("Work");
        //adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,list);
        spin.setAdapter(adapter);
        int spinPos = adapter.getPosition(mListener.getObject().getmType());
        spin.setSelection(spinPos);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mType = mListener.getObject().getmType();
            }
        });

        //Update Button
        Button update = (Button) getView().findViewById(R.id.btn_viewOkay);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if not connected to internet show Alert
                if (!NetworkConnection.isConnected(getActivity().getApplicationContext())) {
                    AlertDialog.Builder connectionAlert = new AlertDialog.Builder(getActivity());
                    connectionAlert.setTitle("No Internet Connection");
                    connectionAlert.setMessage("Must have Internet Connection to update contacts.");
                    connectionAlert.setPositiveButton("Okay", null);
                    connectionAlert.show();
                } else {
                    //check fields
                    if ((!isString(first.getText().toString()))|(!isString(last.getText().toString()))) {
                        //Alert box
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setTitle("Alert");
                        alert.setMessage("Please make sure First & Last name fields are not blank. No Symbols, Spaces or Numbers. (Example: John)");
                        alert.setPositiveButton("OKAY", null);
                        alert.show();
                    } else {
                        //check phone field
                        if (isPhoneNumber(phone.getText().toString())) {
                            // Update info in parse
                            ParseQuery<ParseObject> query = ParseQuery.getQuery("Contacts");
                            query.getInBackground(mListener.getObject().getmObjectId(), new GetCallback<ParseObject>() {
                                public void done(ParseObject contact, com.parse.ParseException e) {
                                    if (e == null) {
                                        contact.put("FirstName", first.getText().toString());
                                        contact.put("LastName", last.getText().toString());
                                        contact.put("Type", mType);
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
                                        mListener.deleteCompleted();
                                    }
                                }
                            });
                        } else {
                            //Phone number is not correct
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            alert.setTitle("Phone Number is incorrect");
                            alert.setMessage("Please enter a 10 digit phone number (Example:7048675309)");
                            alert.setPositiveButton("OKAY", null);
                            alert.show();
                        }
                    }
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
                                object.unpinInBackground();
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

    //VALIDATION METHODS
    public boolean isPhoneNumber(String number){
        if((number.length() == 10)&&(number.matches("[0-9]+"))) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isString(String letters){
        if((letters.length() > 0)&&(Pattern.matches("[a-zA-Z]+",letters))){
            return true;
        } else {
            return false;
        }
    }
}
