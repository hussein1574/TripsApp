package com.seinical.trips;




import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.seinical.trips.data.TempTripsDataManager;
import com.seinical.trips.data.Trip;
import com.seinical.trips.databinding.ActivityHomePageBinding;
import com.seinical.trips.ui.upcoming.UpcomingFragment;


public class HomePage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomePageBinding binding;
    public static final long MEGABYTES = 1024 * 1024;
    private final StorageReference mStorageReference =  FirebaseStorage.getInstance().getReference();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(mAuth.getCurrentUser().getUid()).child("Trips");

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHomePage.toolbar);
        binding.appBarHomePage.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerView = navigationView.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.userUsername);
        TextView userEmail = headerView.findViewById(R.id.userEmail);
        ImageView userImage = headerView.findViewById(R.id.userImg);
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null)
        {
            userName.setText(user.getDisplayName());
            userEmail.setText(user.getEmail());
            userImage.setImageBitmap(downloadImage(user.getUid()));

        }
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null)
                {
                    TempTripsDataManager.getInstance().getUpcoming().clear();
                    TempTripsDataManager.getInstance().getHistory().clear();
                }
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {

                    Trip trip = new Trip(dataSnapshot.child("name").getValue(String.class),dataSnapshot.child("date").getValue(String.class),
                            dataSnapshot.child("time").getValue(String.class),dataSnapshot.child("status").getValue(String.class),
                            dataSnapshot.child("source").getValue(String.class),dataSnapshot.child("destination").getValue(String.class));
                    TempTripsDataManager.getInstance().addTrip(trip);
                }
                UpcomingFragment.adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @SuppressWarnings("StatementWithEmptyBody")
    private Bitmap downloadImage(String fileName)
    {
        // Create a reference with an initial file path and name
        StorageReference imgRef =  mStorageReference.child("images/" + fileName);
        Task<byte[]> bytes = imgRef.getBytes(MEGABYTES);
        while(!bytes.isComplete());
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes.getResult(),0,bytes.getResult().length);
        return Bitmap.createScaledBitmap(bitmap,
                (int) (76*0.8),
                (int) (76*0.8),
                true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_page);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}