package com.example.readnovel;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class FbInstanceIdService extends FirebaseMessagingService {
    public void onNewToken(String token) {
        FirebaseMessaging refreshedToken =  FirebaseMessaging.getInstance();
        Log.e("FB", "Refreshed token: " + refreshedToken);
    }
}
