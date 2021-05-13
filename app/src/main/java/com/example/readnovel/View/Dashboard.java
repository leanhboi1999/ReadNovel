package com.example.readnovel.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.readnovel.Model.Comic;
import com.example.readnovel.R;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    private ArrayList<Comic> listUpdate;
    private ArrayList<Comic> listHottrend;
    private ArrayList<Comic> listGirl;
    private ArrayList<Comic> listBoy;

    Button btnBXH;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnBXH = findViewById(R.id.btn_BXH);
        btnBXH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRanking();
            }
        });
    }

    private void openRanking() {
        Intent iOpenRanking = new Intent(Dashboard.this,TopActivity.class);
        startActivity(iOpenRanking);
    }


}