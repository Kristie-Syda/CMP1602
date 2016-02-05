package com.example.kristie_syda.friendkeeper;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by Kristie_Syda on 2/5/16.
 */
public class SignUpFragment extends Fragment {
    public static final String TAG = "SignUpFragment.TAG";

    //FACTORY METHODS
    public static SignUpFragment newInstance(){
        SignUpFragment frag = new SignUpFragment();
        return frag;
    }
    public SignUpFragment(){
        super();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_signup,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //TextFields
        final EditText userName = (EditText) getView().findViewById(R.id.userName);
        final EditText email = (EditText) getView().findViewById(R.id.email);
        final EditText password = (EditText) getView().findViewById(R.id.password);
        final EditText firstName = (EditText) getView().findViewById(R.id.firstName);
        final EditText lastName = (EditText) getView().findViewById(R.id.lastName);

        //Submit Button & Listener
        Button submit = (Button) getView().findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create New User
                ParseUser user = new ParseUser();
                user.setUsername(userName.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());
                user.put("First", firstName.getText().toString());
                user.put("Last", lastName.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            System.out.println("//////////////////// User Created");
                        } else {
                            System.out.println("//////////////////// Something went wrong");
                        }
                    }
                });
            }
        });

        //Cancel Button & Listener
        Button cancel = (Button) getView().findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}
