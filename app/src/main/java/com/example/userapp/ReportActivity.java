package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText time, date, place, details;
    Button submit;
    int flag = 0;
    private static final String TAG = "ReportActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Toolbar toolbar = findViewById(R.id.reportActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String criminal_id = getIntent().getStringExtra("criminal_id");
//        Log.i(TAG, "onCreate: "+ criminal_id);

        time = findViewById(R.id.lastSeenTime);
        date = findViewById(R.id.lastSeenDate);
        place = findViewById(R.id.lastSeenPlace);
        details = findViewById(R.id.reportDetails);

        submit = findViewById(R.id.btn_submit_report);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time_picker");

            }
        });
        
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String txt_time, txt_date, txt_place, txt_details;
                txt_time = time.getText().toString();
                txt_date = date.getText().toString();
                txt_place = place.getText().toString();
                txt_details = details.getText().toString();

                if(txt_date.trim().equalsIgnoreCase("")){

                    date.setError("This field cannot be blank");
                    flag = 1;

                }

                if(txt_place.trim().equalsIgnoreCase("")){

                    place.setError("This field cannot be blank");
                    flag = 1;

                }

                if(flag == 0){

//                    final ProgressBar progressBar = new ProgressBar(ReportActivity.this, null, android.R.attr.progressBarStyleSmall);
//                    progressBar.setVisibility(View.VISIBLE);
//                    if(progressBar.isAnimating() == true) Log.i(TAG, "onClick: true");

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Criminal Report").push();

                    Report report = new Report(criminal_id, txt_time, txt_date, txt_place, txt_details);

                    databaseReference.setValue(report).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

//                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ReportActivity.this, "Reported Successfully", Toast.LENGTH_SHORT).show();
//                                finish();

                            }

                        }
                    });

                }

            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateString = dateFormat.format(calendar.getTime());

        date.setText(currentDateString);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        time.setText(hourOfDay+":"+minute);

    }
}
