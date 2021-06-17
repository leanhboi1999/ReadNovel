package com.example.readnovel;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;

public class FbInstanceIdService {
    public static FirebaseDatabase create() {
       FirebaseDatabase database = FirebaseDatabase.getInstance();
       return database;
    }
}
