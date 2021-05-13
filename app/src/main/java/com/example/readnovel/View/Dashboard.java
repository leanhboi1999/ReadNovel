package com.example.readnovel.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import com.example.readnovel.Model.*;

import com.example.readnovel.R;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    private ArrayList<Comic> listUpdate;
    private ArrayList<Comic> listHottrend;
    private ArrayList<Comic> listGirl;
    private ArrayList<Comic> listBoy;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}