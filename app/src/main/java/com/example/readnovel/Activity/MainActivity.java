package com.example.readnovel.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.readnovel.Model.User;
import com.example.readnovel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Objects;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static User userConfirm;
    TextView text_first;
    TextInputEditText username,password;
    Button button_first, button_second;
    String username_text, password_text;
    ImageView imgAddNewUser;

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


        username = findViewById(R.id.usernameCard);
        password = findViewById(R.id.passwordCard);
        imgAddNewUser = findViewById(R.id.imgAddNewUser);
        imgAddNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewUser(view);
            }
        });
        //Load image
        //image = findViewById(R.id.Image);
        button_first = findViewById(R.id.button_first);
        button_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup(view);
            }
        });
        button_second = findViewById(R.id.button_second);
        button_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });
    }

    private void addNewUser(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    private void signup(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    public void login(View view) {
        username_text = Objects.requireNonNull(username.getText()).toString().trim();
        password_text = Objects.requireNonNull(password.getText()).toString().trim();
        if(username_text != null) {
            DocumentReference dr = db.collection("user").document(username_text);
            dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        assert doc != null;
                        User login = new User(doc.get("email").toString(), doc.get("username").toString(), doc.get("password").toString());
                        if(login.getPassword().equals(password_text)) {
                            userConfirm = login;
                            Intent i = new Intent(MainActivity.this, Dashboard.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(MainActivity.this, "Bạn nhập sai mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Người dùng không tồn tại", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
