package com.seinical.trips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private ImageView mImgTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signIn = findViewById(R.id.sign_in_btn);
        Button signUp = findViewById(R.id.sign_up_btn);
        TextView appName = findViewById(R.id.app_name);
        View img = findViewById(R.id.logo_view);
        TextView E = findViewById(R.id.E);
        mImgTest = findViewById(R.id.test_login);

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
        img.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTrip.class);
            startActivity(intent);
        } );
        E.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EditTrip.class);
            startActivity(intent);
        } );

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Login.mBitmap != null)
            mImgTest.setImageBitmap(Login.mBitmap);

    }
}