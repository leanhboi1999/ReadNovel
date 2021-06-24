package com.example.readnovel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.readnovel.FbInstanceIdService;
import com.example.readnovel.R;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private static FirebaseDatabase database = FbInstanceIdService.create();
    private ImageView imgBackToMain;
    private TextView txtSignIn;
    private Button btnRegister;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resigter);

        addControls();
        addEvents();
    }

    private void addControls() {
        imgBackToMain = findViewById(R.id.imgBackToMain);
        txtSignIn = findViewById(R.id.txtSignIn);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void addEvents() {
        imgBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

        //Handle Adding New Users Event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
