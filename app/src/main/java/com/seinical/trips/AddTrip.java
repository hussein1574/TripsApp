package com.seinical.trips;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Queue;


public class AddTrip extends AppCompatActivity {

    EditText calendarText;
    EditText time;
    EditText calendar;
    EditText timeText;
    EditText originText;
    EditText destinationText;
    EditText titleText;
    TextView tripTypeText;
    Spinner spinner;
    AppCompatButton addBtton;
    final Calendar myCalendar= Calendar.getInstance();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String origin;
    String destination;
    String title;
    String day;
    String timer;
    String trip_type;
    TripClass tripClass;
    String roundDay;
    String roundTime;
    int tripID;
    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        InitializeComponents();
        addTrip();
    }

    private void InitializeComponents() {
        spinner=findViewById(R.id.spinner);
        time=findViewById(R.id.roundTime);
        calendar=findViewById(R.id.roundCalendar);
        timeText=findViewById(R.id.time);
        calendarText=findViewById(R.id.calendar);
        addBtton=findViewById(R.id.addButton);
        originText=findViewById(R.id.originTxt);
        destinationText=findViewById(R.id.destinationTxt);
        titleText=findViewById(R.id.titleTxt);
        tripTypeText=findViewById(R.id.tripTypeTxt);
        tripClass=new TripClass();
        tripID=0;

    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for(DataSnapshot ds : snapshot.getChildren()) {
                tripID++;
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
    private void addTrip() {
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.trip_type,
                        android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item);
        spinner.setAdapter(staticAdapter);
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

            }
        };
        calendarText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    new DatePickerDialog(AddTrip.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    updateLabel(calendarText);
                    return true;
                }
                return false;
            }
        });
        timeText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    setTime(timeText);
                    return true;
                }
                return false;
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    time.setVisibility(View.VISIBLE);
                    calendar.setVisibility(View.VISIBLE);
                    trip_type="Two Direction";
                }
                else{
                    time.setVisibility(View.INVISIBLE);
                    calendar.setVisibility(View.INVISIBLE);
                    trip_type="one Direction";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    setTime(time);
                    return true;
                }
                return false;
            }
        });
        calendar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    new DatePickerDialog(AddTrip.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    updateLabel(calendar);
                    return true;
                }
                return false;
            }
        });
        addBtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase=FirebaseDatabase.getInstance();
                databaseReference=firebaseDatabase.getReference();
                origin=originText.getText().toString().trim();
                destination=destinationText.getText().toString().trim();
                title=titleText.getText().toString().trim();
                day=calendarText.getText().toString().trim();
                timer=timeText.getText().toString().trim();
                query=databaseReference.child("users").child("noressam2000@gmail").child("Trips").orderByChild("tripID");
                query.addListenerForSingleValueEvent(valueEventListener);
                //tripID++;
                if(trip_type.equals("Two Direction")){

                    roundDay=calendar.getText().toString().trim();
                    roundTime=time.getText().toString().trim();
                    tripClass=new TripClass(origin,destination,title,day,timer,trip_type,roundDay,roundTime);

                }else{
                    tripClass=new TripClass(origin,destination,title,day,timer,trip_type);
                }
                databaseReference.child("users").child("noressam2000@gmail").child("Trips").child(String.valueOf(tripID)).setValue(tripClass);
                Toast.makeText(AddTrip.this, "Trip Added successfully", Toast.LENGTH_LONG).show();
            }
        });

    }
    private void setTime(EditText editText){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddTrip.this, new TimePickerDialog.OnTimeSetListener() {
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