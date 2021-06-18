package com.example.readnovel.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.readnovel.FbInstanceIdService;
import com.example.readnovel.R;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private static FirebaseDatabase database = FbInstanceIdService.create();
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dashboard);
    }
}
