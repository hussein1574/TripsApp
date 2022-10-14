package com.seinical.trips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Login extends AppCompatActivity {

    public static final long MEGABYTES = 1024 * 1024;
    private Button mLoginBtn;
    private EditText mEmail;
    private EditText mPassword;
    private final DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trips-app-dae7a-default-rtdb.firebaseio.com/");
    private final StorageReference mStorageReference =  FirebaseStorage.getInstance().getReference();

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
            final String emailText = mEmail.getText().toString().trim().replace(".",",");
            final String passwordText = mPassword.getText().toString().trim();
            if(emailText.isEmpty() || passwordText.isEmpty())
                Toast.makeText(Login.this,"please fill all fields",Toast.LENGTH_LONG).show();
            else
            {
                mDatabaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(emailText)){
                            String getPass = snapshot.child(emailText).child("password").getValue(String.class);
                            assert getPass != null;
                            if(getPass.equals(passwordText)){
                                 downloadImage(snapshot.child(emailText).child("phone").getValue(String.class));
                                 finish();
                            }else{
                                mPassword.setError("wrong password");
                                mPassword.requestFocus();
                            }
                        }
                        else{
                            mEmail.setError("wrong email");
                            mEmail.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }

        });
    }

    private void downloadImage(String fileName)
    {
        // Create a reference with an initial file path and name
        StorageReference imgRef =  mStorageReference.child("images/" + fileName);
        Task<byte[]> bytes = imgRef.getBytes(MEGABYTES);
        while(!bytes.isComplete());
        mBitmap = BitmapFactory.decodeByteArray(bytes.getResult(),0,bytes.getResult().length);
    }
}