package com.example.userapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class WantedAdapter extends RecyclerView.Adapter<WantedAdapter.ViewHolder> {

    private static final String TAG = "WantedAdapter";

    Context mContext;
    List<Criminals> mCriminals;

    DatabaseReference databaseReference;

    public WantedAdapter(Context mContext, List<Criminals> mCriminals){
        this.mContext = mContext;
        this.mCriminals = mCriminals;
//        Log.i(TAG, "WantedAdapter: "+ this.mCriminals.size());
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.wanted_layout,parent, false);
        return new WantedAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Criminals criminals = mCriminals.get(position);


        Glide.with(mContext).load(criminals.getProfile_pic_url()).into(holder.picture);
        holder.name.setText(criminals.getCriminal_name());
        holder.bodyMark.setText(criminals.getCriminal_BodyMark());
        holder.rating.setText(criminals.getCriminal_rating());

        holder.showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(holder.last_crime.getVisibility() == View.GONE){

                    holder.showButton.setRotation(180);
                    TransitionManager.beginDelayedTransition(holder.last_crime, new AutoTransition());

                    holder.last_crime.setVisibility(View.VISIBLE);
                }else{

                    holder.showButton.setRotation(360);
                    TransitionManager.beginDelayedTransition(holder.last_crime, new AutoTransition());
                    holder.last_crime.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCriminals.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView picture;
        TextView name, bodyMark, rating;
        Button showButton;

        LinearLayout last_crime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            picture = itemView.findViewById(R.id.wanted_picture);
            name = itemView.findViewById(R.id.wanted_name);
            bodyMark = itemView.findViewById(R.id.wanted_body_mark);
            rating = itemView.findViewById(R.id.wanted_rating);
            showButton = itemView.findViewById(R.id.showButton);
            last_crime = itemView.findViewById(R.id.last_crime);

        }
    }

}
