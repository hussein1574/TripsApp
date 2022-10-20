package com.seinical.trips;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;


import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;



import com.google.firebase.auth.FirebaseAuth;






public class Login extends AppCompatActivity {


    private Button mLoginBtn;
    private EditText mEmail;
    private EditText mPassword;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static Bitmap mBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitializeComponents();
        login();
    }

    private void InitializeComponents() {
        mLoginBtn = findViewById(R.id.login_btn);
        mEmail = findViewById(R.id.email_text);
        mPassword = findViewById(R.id.password_text);
    }
    private void login() {
        mLoginBtn.setOnClickListener(view -> {
            final String emailText = mEmail.getText().toString().trim();
            final String passwordText = mPassword.getText().toString().trim();
            if(emailText.isEmpty() || passwordText.isEmpty())
                Toast.makeText(Login.this,"please fill all fields",Toast.LENGTH_LONG).show();
            else
            {
                mAuth.signInWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(this, HomePage.class));
                            } else {
                                Toast.makeText(Login.this, "Wrong password or email ",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}