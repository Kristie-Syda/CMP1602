package com.example.kristie_syda.friendkeeper.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kristie_syda.friendkeeper.NetworkConnection;
import com.example.kristie_syda.friendkeeper.R;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Kristie_Syda on 2/4/16.
 */
public class AddFragment extends Fragment {
    public static final String TAG = "AddFragment.TAG";
    private addListener mListener;
    private String mType;

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

        //Spinner
        //Array for adapter
        ArrayList<String> list = new ArrayList<>();
        list.add("Pick a Type");
        list.add("Home Phone");
        list.add("Cell Phone");
        list.add("Work");
        //adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,list);
        Spinner spinner = (Spinner) getView().findViewById(R.id.type);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mType = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
                //if not connected to internet show Alert
                if(!NetworkConnection.isConnected(getActivity().getApplicationContext())){
                    AlertDialog.Builder connectionAlert = new AlertDialog.Builder(getActivity());
                    connectionAlert.setTitle("No Internet Connection");
                    connectionAlert.setMessage("Must have Internet Connection to save contacts.");
                    connectionAlert.setPositiveButton("Okay",null);
                    connectionAlert.show();
                } else {
                //Internet is Available -- check fields
                    if ((!isString(first.getText().toString()))|(!isString(last.getText().toString()))) {
                        //Alert box
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setTitle("Alert");
                        alert.setMessage("Please make sure First & Last name fields are not blank. No Symbols, Spaces or Numbers. (Example: John)");
                        alert.setPositiveButton("OKAY", null);
                        alert.show();
                    } else {

                        // First & Last were good -- check phone field
                        if(isPhoneNumber(phone.getText().toString())) {
                            //Save object
                            ParseUser currentUser = ParseUser.getCurrentUser();
                            currentUser.setACL(new ParseACL(currentUser));
                            final ParseObject contact = new ParseObject("Contacts");
                            contact.put("User", currentUser);
                            contact.put("FirstName", first.getText().toString());
                            contact.put("LastName", last.getText().toString());

                            if (mType == "Pick a Type") {
                                //Alert for type spinner
                                AlertDialog.Builder typeAlert = new AlertDialog.Builder(getActivity());
                                typeAlert.setTitle("Alert");
                                typeAlert.setMessage("Please choose a number type.");
                                typeAlert.setPositiveButton("OKAY", null);
                                typeAlert.show();
                            } else {
                                contact.put("Type", mType);
                                try {
                                    contact.put("Phone", Integer.parseInt(phone.getText().toString()));
                                    contact.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                //contact saved
                                                contact.pinInBackground();
                                                int duration = Toast.LENGTH_SHORT;
                                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Contact was saved!", duration);
                                                toast.show();
                                                mListener.saveCompleted();
                                            } else {
                                                int duration = Toast.LENGTH_SHORT;
                                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Error: " + e, duration);
                                                toast.show();
                                            }
                                        }
                                    });
                                } catch (NumberFormatException e) {
                                    //Alert box
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setTitle("Alert");
                                    alert.setMessage("phone number is incorrect");
                                    alert.setPositiveButton("OKAY", null);
                                    alert.show();
                                    e.printStackTrace();
                                }
                            }
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

    }

    //VALIDATION METHODS
    public boolean isPhoneNumber(String number){
        if((number.length() == 10)&&(number.matches("[0-9]+"))) {
            System.out.println("number is phone");
            return true;
        } else {
            System.out.println("number is NOT phone");
            return false;
        }
    }
    public boolean isString(String letters){
        if((letters.length() > 0)&&(Pattern.matches("[a-zA-Z]+", letters))){
            System.out.println("letter is string");
            return true;
        } else {
            System.out.println("letter is NOT string");
            return false;
        }
    }
}
