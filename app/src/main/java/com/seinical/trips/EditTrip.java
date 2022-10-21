package com.seinical.trips;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditTrip extends AppCompatActivity {

    final Calendar myCalendar= Calendar.getInstance();
    EditText editTimeText;
    EditText editCalendarText;
    EditText editOrigin;
    EditText editDestination;
    EditText editTitle;
    AppCompatButton editButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String origin;
    String destination;
    String title;
    String day;
    String timer;
    int tripID;
    TripClass tripClass;
    ////static data that will return from intent
    String origin1="Europe";
    String destination1="Egypt";
    String title1="Back to Om Eldonia";
    String day1="11/5/22";
    String timer1="12:05";
    int tripID1=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);
        InitializeComponents();
        editOrigin.setText(origin1);
        editDestination.setText(destination1);
        editTitle.setText(title1);
        editTimeText.setText(timer1);
        editCalendarText.setText(day1);
        edit_trip();
    }

    private void edit_trip() {
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

            }
        };
        editCalendarText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    new DatePickerDialog(EditTrip.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    updateLabel(editCalendarText);
                    return true;
                }
                return false;
            }
        });
        editTimeText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP)
                {
                    setTime(editTimeText);
                }
                return false;
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase= FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference();
                origin=editOrigin.getText().toString();
                destination=editDestination.getText().toString();
                title=editTitle.getText().toString();
                day=editCalendarText.getText().toString();
                timer=editTimeText.getText().toString();
                if(origin.isEmpty()||destination.isEmpty()||title.isEmpty()||day.isEmpty()||timer.isEmpty()){
                    Toast.makeText(EditTrip.this,"please fill all fields",Toast.LENGTH_LONG).show();
                }
                else{
                    if(dataChanged()){
                        _newData();
                        databaseReference.child("users").child("noressam2000@gmail").child("Trips").child("3").setValue(tripClass);
                        Toast.makeText(EditTrip.this,"Data has been updated",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(EditTrip.this,"Data is same and can't be updated",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }

    private void _newData(){
        tripClass=new TripClass(origin,destination,title,day,timer);
    }
    private boolean dataChanged(){
        if(origin1.equals(origin)||destination1.equals(destination)||title1.equals(title)||timer1.equals(timer)||day1.equals(day))
            return true;
        else
            return false;
    }
    private void InitializeComponents() {
        editTimeText =findViewById(R.id.editTime);
        editCalendarText =findViewById(R.id.editCalendar);
        editOrigin=findViewById(R.id.editOrigin);
        editDestination=findViewById(R.id.editDestination);
        editTitle=findViewById(R.id.editTitle);
        editButton=findViewById(R.id.editButton);

    }
    private void setTime(EditText editText){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(EditTrip.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                editText.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
    private void updateLabel(EditText editText) {
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(dateFormat.format(myCalendar.getTime()));
    }
}