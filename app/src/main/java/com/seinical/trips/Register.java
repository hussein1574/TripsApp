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
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String mUsernameText;
    private String mPhoneNumberText;

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
            mUsernameText = mUsername.getText().toString().trim();
            final String passwordText = mPassword.getText().toString().trim();
            final String confirmPasswordText = mConfirmPassword.getText().toString().trim();
            mPhoneNumberText = mPhoneNumber.getText().toString().trim();

            if(mUsernameText.isEmpty()|| emailText[0].isEmpty()|| passwordText.isEmpty()|| confirmPasswordText.isEmpty() || mPhoneNumberText.isEmpty())
                Toast.makeText(Register.this,"Please fill all data",Toast.LENGTH_LONG).show();
            else if(!Patterns.EMAIL_ADDRESS.matcher(emailText[0]).matches()){
                mEmail.setError("Email is not valid");
                mEmail.requestFocus();
            }
            else if(!passwordText.equals(confirmPasswordText)){
                mConfirmPassword.setError("Password not matched");
                mConfirmPassword.requestFocus();
            }else if(passwordText.length()<8){
                mPassword.setError("Password is too short");
                mPassword.requestFocus();
            } else if (!Patterns.PHONE.matcher(mPhoneNumberText).matches())
            {
                mPhoneNumber.setError("Phone number is not valid");
                mPhoneNumber.requestFocus();
            }
            else{
                if(mSelectedImageUri == null)
                    Toast.makeText(Register.this, "Please choose a photo", Toast.LENGTH_SHORT).show();
                else{
                    mAuth.createUserWithEmailAndPassword(emailText[0], passwordText)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {

                                    uploadImage(task.getResult().getUser());
                                    setAdditionalData(task.getResult().getUser().getUid());
                                    Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(Register.this, "This email is already registered",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }

           /* if (emailText[0].isEmpty() || passwordText.isEmpty() || usernameText.isEmpty()
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

            } */
        });
    }

    private void uploadImage(FirebaseUser user) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(mUsernameText)
                .setPhotoUri(mSelectedImageUri)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            String test = mAuth.getCurrentUser().getDisplayName();
                            Log.d("HFirebase", "User profile updated.");
                        }
                    }
                });
    }

    private void setAdditionalData(String uid) {
        mDatabaseReference.child(uid).child("phoneNumber").setValue(mPhoneNumberText).addOnFailureListener(e -> Toast.makeText(Register.this, "Failed to set phone number", Toast.LENGTH_LONG).show());

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