package com.example.readnovel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView text_first;
    EditText username, password;
    Button button_first, button_second;
    String username_text, password_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //Find id control
    text_first = findViewById(R.id.text_first);
    text_first.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.size_14sp));
    text_first.setText("Đăng nhập");
    username = findViewById(R.id.username);
    password = findViewById(R.id.password);

    button_first = findViewById(R.id.button_first);
    button_first.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            login(view);
        }
    });
    }

    public void login(View view) {
        Log.v("Click", "Button clicked");
        //Creating a message toast
        Toast.makeText(this, "Button is clicked", Toast.LENGTH_LONG).show();
        /*username_text = username.getText().toString();
        password_text = password.getText().toString();*/
    }
}