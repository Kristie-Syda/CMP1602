package com.example.kristie_syda.friendkeeper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Kristie_Syda on 2/16/16.
 */
public class NetworkConnection {

    public static Boolean isConnected(Context context){
        Boolean connected = false;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null && info.isConnectedOrConnecting()){
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }
}
