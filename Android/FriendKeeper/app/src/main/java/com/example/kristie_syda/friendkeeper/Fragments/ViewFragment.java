package com.example.kristie_syda.friendkeeper.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kristie_syda.friendkeeper.ContactObject;
import com.example.kristie_syda.friendkeeper.R;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;


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
        TextView first = (TextView) getView().findViewById(R.id.view_first);
        first.setText(mListener.getObject().getmFirst());

        TextView last = (TextView) getView().findViewById(R.id.view_last);
        last.setText(mListener.getObject().getmLast());

        TextView phone = (TextView) getView().findViewById(R.id.view_phone);
        phone.setText(Integer.toString(mListener.getObject().getmPhone()));

        //Okay Button
        Button okay = (Button) getView().findViewById(R.id.btn_viewOkay);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteCompleted();
            }
        });

        //Delete Button
        Button delete = (Button) getView().findViewById(R.id.btn_viewDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Contacts");
                query.getInBackground(mListener.getObject().getmObjectId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, com.parse.ParseException e) {
                        if(e == null){
                            object.deleteInBackground();
                            mListener.deleteCompleted();
                        } else {
                            System.out.println("something went wrong when deleteing " + e);
                        }
                    }
                });
            }
        });


    }
}
