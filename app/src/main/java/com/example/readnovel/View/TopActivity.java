package com.example.readnovel.View;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.readnovel.Model.Search;
import com.example.readnovel.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TopActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private AutoCompleteTextView txtAuto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        addControls();
    }

    private void addControls() {
        tabLayout = findViewById(R.id.tab_layout);
        FragmentManager manager = getSupportFragmentManager();
        tabLayout.addTab(tabLayout.newTab().setText("Ngày"));
        tabLayout.addTab(tabLayout.newTab().setText("Tuần"));
        tabLayout.addTab(tabLayout.newTab().setText("Tháng"));

    }
}
