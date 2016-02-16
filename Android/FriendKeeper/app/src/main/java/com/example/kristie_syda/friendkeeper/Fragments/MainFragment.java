package com.example.kristie_syda.friendkeeper.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kristie_syda.friendkeeper.Activities.HomeActivity;
import com.example.kristie_syda.friendkeeper.Activities.SignUpActivity;
import com.example.kristie_syda.friendkeeper.NetworkConnection;
import com.example.kristie_syda.friendkeeper.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

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

        //Username & Password
        final EditText userName = (EditText) getView().findViewById(R.id.login_user);
        final EditText password = (EditText) getView().findViewById(R.id.login_password);

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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if not connected to internet show Alert
                if(!NetworkConnection.isConnected(getActivity().getApplicationContext())){
                    AlertDialog.Builder connectionAlert = new AlertDialog.Builder(getActivity());
                    connectionAlert.setTitle("No Internet Connection");
                    connectionAlert.setMessage("Must have Internet Connection to Login.");
                    connectionAlert.setPositiveButton("Okay",null);
                    connectionAlert.show();
                } else {
                    ParseUser.logInInBackground(userName.getText().toString(), password.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null) {
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "User Logged in", duration);
                                toast.show();
                                Intent hIntent = new Intent(getActivity(), HomeActivity.class);
                                startActivity(hIntent);
                            } else {
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Incorrect Username or Password. Try again", duration);
                                toast.show();
                            }
                        }
                    });
                }
            }
        });
    }
}
