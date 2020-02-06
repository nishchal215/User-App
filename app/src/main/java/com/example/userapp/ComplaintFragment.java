package com.example.userapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;

public class ComplaintFragment extends Fragment {

    Spinner spinner;
    EditText details;
    Button submit;
    int policeLevel, flag=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_complaint, container, false);

        spinner = view.findViewById(R.id.levelSpinner);
        details = view.findViewById(R.id.complaintDetails);
        submit = view.findViewById(R.id.btn_submit_complaint);


        String[] level = {"Constable", "Inspector", "Superintendent of Police",};



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                policeLevel = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //setting array adaptors to spinners
        //ArrayAdapter is a BaseAdapter that is backed by an array of arbitrary objects
        ArrayAdapter<String> spin_adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, level);


        // setting adapters to spinners
        spinner.setAdapter(spin_adapter);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_details = details.getText().toString();

                if(txt_details.equalsIgnoreCase("")){
                    details.setError("This field cannot be empty!");
                    flag=1;
                }

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Complainbox");

                if(flag==0){

                    Complaint complaint = new Complaint(policeLevel, txt_details);

                    databaseReference.push().setValue(complaint).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                Toast.makeText(getContext(), "Submitted Successfully!", Toast.LENGTH_SHORT).show();

                            }else{

                                Toast.makeText(getContext(), "Could not be submitted", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                    details.setText("");
                    spinner.setSelection(0);

                    flag=0;

                }

            }
        });


        return view;

    }

}
