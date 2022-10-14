package com.seinical.trips;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.IOException;

public class Register extends AppCompatActivity {

    private Button mRegisterBtn;
    private EditText mEmail;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mPhoneNumber;
    private ImageView mSignupImage;
    private TextView mSelectImage;
    private final DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trips-app-dae7a-default-rtdb.firebaseio.com/");
    private final StorageReference mStorageReference =  FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        InitializeComponents();
        register();
        imageChooser();
    }
    private void InitializeComponents() {
        mRegisterBtn = findViewById(R.id.register_btn);
        mEmail = findViewById(R.id.reg_email_text);
        mUsername = findViewById(R.id.reg_username_text);
        mPassword = findViewById(R.id.reg_password_text);
        mConfirmPassword = findViewById(R.id.reg_confirm_password_text);
        mPhoneNumber = findViewById(R.id.reg_phone_text);
        mSignupImage = findViewById(R.id.reg_image);
        mSelectImage = findViewById(R.id.reg_upload_photo);
    }
    private void register() {
        mRegisterBtn.setOnClickListener(view -> {
            final String[] emailText = {mEmail.getText().toString().trim()};
            final String usernameText = mUsername.getText().toString().trim();
            final String passwordText = mPassword.getText().toString().trim();
            final String confirmPasswordText = mConfirmPassword.getText().toString().trim();
            final String phoneNumberText = mPhoneNumber.getText().toString().trim();

            if (emailText[0].isEmpty() || passwordText.isEmpty() || usernameText.isEmpty()
                    || confirmPasswordText.isEmpty() || phoneNumberText.isEmpty())
                Toast.makeText(Register.this, "please fill all fields", Toast.LENGTH_LONG).show();
            else if (!Patterns.EMAIL_ADDRESS.matcher(emailText[0]).matches()) {
                mEmail.setError("email is not valid");
                mEmail.requestFocus();
            } else if (!passwordText.equals(confirmPasswordText)) {
                mConfirmPassword.setError("password not matched");
                mConfirmPassword.requestFocus();
            } else {
                mDatabaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        emailText[0] = emailText[0].replace(".", ",");
                        if (snapshot.hasChild(emailText[0])) {
                            mEmail.setError("email already registered");
                            mEmail.requestFocus();
                        } else {
                            if(uploadImage(phoneNumberText))
                            {
                                mDatabaseReference.child("users").child(emailText[0]).child("username").setValue(usernameText);
                                mDatabaseReference.child("users").child(emailText[0]).child("phone").setValue(phoneNumberText);
                                mDatabaseReference.child("users").child(emailText[0]).child("password").setValue(passwordText);
                                Toast.makeText(Register.this, "user registered successfully", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else
                            {
                                Toast.makeText(Register.this, "Failed to upload image", Toast.LENGTH_LONG).show();
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }

                });

            }
        });
    }

    private boolean uploadImage(String imageName) {
        final boolean[] uploadStatue = new boolean[1];
        uploadStatue[0] = true;
        StorageReference imagesRef = mStorageReference.child("images/" + imageName);
        imagesRef.putFile(mSelectedImageUri).addOnSuccessListener(taskSnapshot -> uploadStatue[0] = true).addOnFailureListener(e -> uploadStatue[0] = false);
        return uploadStatue[0];
    }

    //prepare intent to open gallery
    private void imageChooser() {
        mSelectImage.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            launchSomeActivity.launch(i);
        });
    }

    private Uri mSelectedImageUri;
    //take photo from gallery
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null
                            && data.getData() != null) {
                        mSelectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    mSelectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        Bitmap resized = Bitmap.createScaledBitmap(selectedImageBitmap,
                                (int) (mSignupImage.getWidth()*0.8),
                                (int) (mSignupImage.getHeight()*0.8), true);
                        mSignupImage.setImageBitmap(resized);
                    }
                }
            });
}