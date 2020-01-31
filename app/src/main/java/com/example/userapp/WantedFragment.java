package com.example.userapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class WantedFragment extends Fragment {

    private static final String TAG = "WantedFragment";

    private RecyclerView recyclerView;
    private WantedAdapter wantedAdapter;
    private List<Criminals> criminalsLists;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_wanted, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            criminalsLists = new ArrayList<>();
            readCriminals();
            wantedAdapter = new WantedAdapter(getContext(), criminalsLists);
            recyclerView.setAdapter(wantedAdapter);

        }


        return view;
    }

    private void readCriminals(){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UsersWantedList");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String string = dataSnapshot.getValue(String.class);

//                Log.i(TAG, "onChildAdded: "+string);

                assert string != null;
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("criminal_ref").child(string);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Criminals criminals = dataSnapshot.getValue(Criminals.class);

                        criminalsLists.add(criminals);

                        assert criminals != null;
                        Log.i(TAG, "onDataChange: "+ criminalsLists.get(criminalsLists.size()-1).getCriminal_name());

                        wantedAdapter.notifyItemInserted(criminalsLists.size()-1);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
