package com.example.userapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {

    private static final String TAG = "AppointmentsAdapter";


    Context mContext;
    List<String> mAppointments;
    String type, category;

    DatabaseReference databaseReference;


    public AppointmentsAdapter(Context mContext, List<String> mAppointments, String type, String category) {
        this.mContext = mContext;
        this.mAppointments = mAppointments;
        this.type = type;
        this.category = category;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.appointment_layout, parent, false);

        return new AppointmentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


//        Log.i(TAG, "onBindViewHolder: "+ mAppointments.get(position));

        String string = mAppointments.get(position);


        databaseReference = FirebaseDatabase.getInstance().getReference(category).child(string);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(category.equals("FIRs")){

                    holder.nocAppointment.setVisibility(View.GONE);
                    holder.firAppointment.setVisibility(View.VISIBLE);

                    Map<String, String> map;

                    map = (Map<String, String>) dataSnapshot.getValue();
//
                    Fir fir = new Fir(map.get("complainantId"), map.get("state"), map.get("district"), map.get("place"), map.get("type"),
                                map.get("subject"), map.get("details"), String.valueOf(map.get("timeStamp")), map.get("status"),
                                map.get("reportingDate"), map.get("reportingPlace"), map.get("correspondent"));
//
////                    Log.i(TAG, "onDataChange: "+ fir.getTs());
//                    fir = dataSnapshot.getValue(Fir.class);

                    holder.firSubject.setText(fir.getSubject());
                    holder.firType.setText(fir.getType());

                    holder.firStatus.setText(fir.getStatus());

                    if(fir.getStatus().equals("Pending")){

                        holder.firLayoutDate.setVisibility(View.GONE);
                        holder.firLayoutPS.setVisibility(View.GONE);
                        holder.firLayoutCorrespondent.setVisibility(View.GONE);

                    }


                }else if(category.equals("NOC")){



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return mAppointments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView firSubject, firType, firStatus, firDate, firPS, firCorrespondent, nocName, nocType, nocStatus, nocDate, nocPS,
                nocCorrespondent;

        LinearLayout firAppointment, nocAppointment, firLayoutDate, firLayoutPS, firLayoutCorrespondent,
                nocLayoutDate, nocLayoutPS, nocLayoutCorrespondent ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firAppointment = itemView.findViewById(R.id.firAppointments);
            nocAppointment = itemView.findViewById(R.id.nocAppointments);

            firLayoutDate =itemView.findViewById(R.id.firLayoutDate);
            firLayoutPS =itemView.findViewById(R.id.firLayoutPS);
            firLayoutCorrespondent =itemView.findViewById(R.id.firLayoutCorrespondent);
            nocLayoutDate =itemView.findViewById(R.id.nocLayoutDate);
            nocLayoutPS =itemView.findViewById(R.id.nocLayoutPS);
            nocLayoutCorrespondent =itemView.findViewById(R.id.nocLayoutCorrespondent);

            firSubject = itemView.findViewById(R.id.appointmentFirSubject);
            firType = itemView.findViewById(R.id.appointmentFirType);
            firStatus = itemView.findViewById(R.id.appointmentFirStatus);
            firDate = itemView.findViewById(R.id.appointmentFirReportingDate);
            firPS = itemView.findViewById(R.id.appointmentFirReportingPS);
            firCorrespondent = itemView.findViewById(R.id.appointmentFirCorrespondent);
            nocName = itemView.findViewById(R.id.appointmentNocName);
            nocType = itemView.findViewById(R.id.appointmentNocType);
            nocStatus = itemView.findViewById(R.id.appointmentNocStatus);
            nocDate = itemView.findViewById(R.id.appointmentNocReportingDate);
            nocPS = itemView.findViewById(R.id.appointmentNocReportingPS);
            nocCorrespondent = itemView.findViewById(R.id.appointmentNocCorrespondent);


        }

    }

}
