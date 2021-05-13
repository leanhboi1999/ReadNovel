package com.example.readnovel.Untils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class CheckConnect {
    private final View _view;
    private final Context _context;


    public CheckConnect(View view, Context context) {
        this._view = view;
        this._context = context;
    }

    private boolean isConnected() {
        ConnectivityManager connectivity;
        connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Objects.requireNonNull(connectivity).getActiveNetworkInfo();
        return true;
    }

    public void CheckConnection() {
        if (isConnected()) {
        } else {
            Snackbar snackbar = Snackbar.make(_view, "Không có kết nối Internet!", Snackbar.LENGTH_LONG).setAction("Bật 3G/4G/Wifi", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            snackbar.setActionTextColor(Color.RED);
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

}
