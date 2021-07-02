package com.example.readnovel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.readnovel.Model.User;
import com.example.readnovel.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageView imgBackToMain;
    private TextView txtSignIn, txtName, txtEmail, txtPass, txtPassConfirm;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resigter);

        addControls();
        addEvents();
    }

    private void addControls() {
        imgBackToMain = findViewById(R.id.imgBackToMain);
        txtSignIn = findViewById(R.id.txtSignIn);
        btnRegister = findViewById(R.id.btnRegister);
        txtName = findViewById(R.id.editTextName);
        txtEmail = findViewById(R.id.editTextEmail);
        txtPass = findViewById(R.id.editTextPass);
        txtPassConfirm = findViewById(R.id.editTextPassword);

    }

    private void addEvents() {
        imgBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        //Handle Adding New Users Event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPassword()) {
                    String username = txtName.getText().toString().trim();
                    String password = txtPassConfirm.getText().toString().trim();
                    String email = txtEmail.getText().toString().trim();
                    User user = new User(email, username, password);
                    db.collection("user").document(username).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "ERROR" + e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("TAG", e.toString());
                        }
                    });
                }
            }
        });
    }

    private boolean checkPassword() {
        boolean temp =true;
        String pass = txtPass.getText().toString().trim();
        String cpass = txtPassConfirm.getText().toString().trim();
        if(!pass.equals(cpass)) {
            temp = false;
            Toast.makeText(RegisterActivity.this, "Password Not Matching", Toast.LENGTH_SHORT).show();
        }
        return temp;
    }
}
