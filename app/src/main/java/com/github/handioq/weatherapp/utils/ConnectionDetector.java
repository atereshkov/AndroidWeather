package com.github.handioq.weatherapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Class for detect device connections.
 */
public class ConnectionDetector {

    private Context context;

    public ConnectionDetector(Context context) {
        this.context = context;
    }

    /**
     * Return state of device internet connection
     * @return true if connected, false otherwise
     */
    public boolean checkInternetConnection() {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();

            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

}
