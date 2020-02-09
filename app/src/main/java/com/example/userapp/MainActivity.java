package com.example.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    LocationManager locationManager;
    LocationListener locationListener;

    Location lastKnownLocation;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                }
            }
        }
    }



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

            OneSignal.startInit(this)


                    .unsubscribeWhenNotificationsAreDisabled(false)
                    .init();


            OneSignal.setSubscription(true);
            OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
                @Override
                public void idsAvailable(String userId, String registrationId) {

                    FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid()).child("notificationId").setValue(userId);

                }
            });
            OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification);

            drawer = findViewById(R.id.drawerLayout);
            navigationView = findViewById(R.id.navView);

            toolbar = findViewById(R.id.mainActivityToolbar);
            setSupportActionBar(toolbar);

            View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_drawer_head);

            emergencyButton = navHeaderView.findViewById(R.id.emergencyButton);

            emergencyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.show();


                    lastKnownLocation = getLastKnownLocation();

                    Location currentLocation = lastKnownLocation;

                    locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);


                    locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                            if(location.toString() != null){

                                lastKnownLocation = location;

                            }


//                                            Log.i("TAG",location.toString());

                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    };


                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    } else {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    }



                    AlertDialog.Builder dialog =  new AlertDialog.Builder(MainActivity.this);

                    dialog.setTitle("Conformation")
                            .setMessage("Do you want to share your location for emergency help ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserBeats")
                                            .child(currentUser.getUid());

                                    databaseReference.setValue(lastKnownLocation.getLatitude()+"||"+lastKnownLocation.getLongitude()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){

                                                Toast.makeText(MainActivity.this, "Emergency has been reported." +
                                                        "Help is on way!", Toast.LENGTH_SHORT).show();

                                                progressDialog.dismiss();

                                            }

                                        }
                                    });


                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                    progressDialog.dismiss();

                                }
                            })
                            .setIcon(R.drawable.ic_add_alert_black_24dp)
                            .show();


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
                OneSignal.setSubscription(false);
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



    private Location getLastKnownLocation() {
        LocationManager mLocationManager;
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.


                }
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


}
