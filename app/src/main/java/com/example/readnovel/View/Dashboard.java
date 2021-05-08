package com.example.readnovel.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.readnovel.R;

public class Dashboard extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        text = findViewById(R.id.textView2);
        Intent i = getIntent();
        String name = i.getStringExtra("email");

        //Display text
        text.setText("Welcome to " + name);;;

    }
}