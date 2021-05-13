package com.example.readnovel.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.readnovel.R;
//import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView text_first;
    EditText username, password;
    Button button_first, button_second;
    String username_text, password_text;

    //ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check Permission
        int permission_internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int permission_read_external_storage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission_write_external_storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission_internet != PackageManager.PERMISSION_GRANTED || permission_read_external_storage != PackageManager.PERMISSION_GRANTED || permission_write_external_storage != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
        //Find id control
        text_first = findViewById(R.id.text_first);
        text_first.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.size_14sp));
        text_first.setText("Đăng nhập");

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        //Load image
        //image = findViewById(R.id.Image);
        button_first = findViewById(R.id.button_first);
        button_second = findViewById(R.id.button_first);
        button_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    public void login(View view) {
        username_text = username.getText().toString();
        password_text = password.getText().toString();

        Intent i = new Intent(this, Dashboard.class);
        i.putExtra("email", username_text);
        i.putExtra("password", password_text);
        startActivity(i);

        //Creating a message toast
        //Toast.makeText(this, "Button is clicked", Toast.LENGTH_LONG).show();

        //Change image when click button
        //image.setImageResource(R.drawable.nameImage);
    }
}