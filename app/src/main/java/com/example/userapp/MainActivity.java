package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FirFragment.OnAlertDialogBoxClickedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    FirebaseUser currentUser;
    private static final String TAG = "MainActivity";
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseAuth mAuth;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    ImageView emergencyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() == null){

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                }

            }
        };

        if(currentUser != null) {

            drawer = findViewById(R.id.drawerLayout);
            navigationView = findViewById(R.id.navView);

            toolbar = findViewById(R.id.mainActivityToolbar);
            setSupportActionBar(toolbar);

            View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_drawer_head);

            emergencyButton = navHeaderView.findViewById(R.id.emergencyButton);

            emergencyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                }
            });
            
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                }

                @Override
                public void onDrawerOpened(@NonNull View drawerView) {

                    // Hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(drawerView.getWindowToken(), 0);

                }

                @Override
                public void onDrawerClosed(@NonNull View drawerView) {

                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });
           // drawer.isDrawerOpen(GravityCompat.START);


            navigationView.setNavigationItemSelectedListener(MainActivity.this);

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            WantedFragment wantedFragment = new WantedFragment();
            fragmentTransaction.add(R.id.fragment_container, wantedFragment);
            fragmentTransaction.commit();



        }
        
//        Log.i(TAG, "onCreate: "+ currentUser.getUid());

    }


    @Override
    protected void onStart() {
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        Log.i(TAG, "onStart: here");
        super.onStart();

        mAuth.addAuthStateListener(mAuthStateListener);


    }


    private void signOut(){
        mAuth.signOut();
        mGoogleSignInClient.signOut();
    }




    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        fragmentTransaction = getSupportFragmentManager().beginTransaction();


        switch (menuItem.getItemId()){

            case R.id.wanted:
                toolbar.setTitle("Wanted");
                WantedFragment wantedFragment = new WantedFragment();
                fragmentTransaction.replace(R.id.fragment_container, wantedFragment);
                fragmentTransaction.commit();

                break;

            case R.id.file_a_fir:
                FirFragment firFragment = new FirFragment();
                fragmentTransaction.replace(R.id.fragment_container, firFragment);
                fragmentTransaction.commit();
                toolbar.setTitle("FIR");
                break;

            case R.id.complaint_box:
                ComplaintFragment complaintFragment = new ComplaintFragment();
                fragmentTransaction.replace(R.id.fragment_container, complaintFragment);
                fragmentTransaction.commit();
                toolbar.setTitle("Complaint Box");
                break;

            case R.id.account:
                AccountFragment accountFragment = new AccountFragment();
                fragmentTransaction.replace(R.id.fragment_container, accountFragment);
                fragmentTransaction.commit();
                toolbar.setTitle("Account");
                break;

            case R.id.noc:
                NocFragment nocFragment = new NocFragment();
                fragmentTransaction.replace(R.id.fragment_container,nocFragment);
                fragmentTransaction.commit();
                toolbar.setTitle("NOC");
                break;

            case R.id.appointments:
                AppointmentFragment appointmentFragment = new AppointmentFragment();
                fragmentTransaction.replace(R.id.fragment_container, appointmentFragment);
                fragmentTransaction.commit();
                toolbar.setTitle("Appointments");
                break;

            case R.id.logout :
//                Log.i(TAG, "onNavigationItemSelected: Logout Pressed");
                signOut();
                finish();

            break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick() {

        Log.i(TAG, "onClick: Clicked");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        AccountFragment accountFragment = new AccountFragment();
        fragmentTransaction.replace(R.id.fragment_container, accountFragment);
        fragmentTransaction.commit();
        toolbar.setTitle("Account");

    }
}
