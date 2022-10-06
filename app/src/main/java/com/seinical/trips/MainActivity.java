package com.seinical.trips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signIn = findViewById(R.id.sign_in_btn);
        Button signUp = findViewById(R.id.sign_up_btn);
        TextView appName = findViewById(R.id.app_name);
        signIn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        } );
        signUp.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        } );
        appName.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MyNotes.class);
            startActivity(intent);
        } );

    }
}