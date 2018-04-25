package com.example.cs4532.umdalive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cs4532.umdalive.fragments.AllClubsFrag;
import com.example.cs4532.umdalive.fragments.ClubFrag;
import com.example.cs4532.umdalive.fragments.ProfileFrag;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //Fragment
    FrameLayout fragContainer;

    //Google Sign In
    GoogleSignInClient mGoogleSignInClient;
    static final int RC_SIGN_IN = 9001;

    //Nav Header Components
    TextView userName;
    TextView userEmail;
    ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_profile);

        //Frag Holder
        fragContainer = findViewById(R.id.fragment_container);

        //Show loading bar
        findViewById(R.id.PageLoading).setVisibility(View.VISIBLE);
        //Add Profile Fragment
        ProfileFrag frag = new ProfileFrag();
        Bundle data = new Bundle();
        data.putString("userID", "5accd44fb22f6712f23cf18b");
        frag.setArguments(data);
        getSupportFragmentManager().beginTransaction().add(fragContainer.getId(),frag).commit();

        //Google Sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        //Set Nav Header Comps
        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.user_name);
        userEmail = headerView.findViewById(R.id.user_email);
        userImage = headerView.findViewById(R.id.user_image);
    }

    @Override
    public void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            try {
                updateUI(account);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            //Show loading bar
            findViewById(R.id.PageLoading).setVisibility(View.VISIBLE);
            //Add Profile Fragment
            ProfileFrag frag = new ProfileFrag();
            Bundle data = new Bundle();
            data.putString("userID", "5accd44fb22f6712f23cf18b");
            frag.setArguments(data);
            getSupportFragmentManager().beginTransaction().replace(fragContainer.getId(),frag).commit();
        } else if (id == R.id.nav_events) {

        } else if (id == R.id.nav_my_clubs) {
            //Show loading bar
            findViewById(R.id.PageLoading).setVisibility(View.VISIBLE);
            //Add Club Fragment
            ClubFrag frag = new ClubFrag();
            Bundle data = new Bundle();
            data.putString("clubID", "5acf6feaa2e8862b20ca6dbe");
            frag.setArguments(data);
            getSupportFragmentManager().beginTransaction().replace(fragContainer.getId(),frag).commit();
        } else if (id == R.id.nav_all_clubs) {
            //Show loading bar
            findViewById(R.id.PageLoading).setVisibility(View.VISIBLE);
            //Add All Clubs Fragment
            AllClubsFrag frag = new AllClubsFrag();
            Bundle data = new Bundle();
            frag.setArguments(data);
            getSupportFragmentManager().beginTransaction().replace(fragContainer.getId(),frag).commit();
        } else if (id == R.id.nav_sign_out) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        findViewById(R.id.Login).setVisibility(View.VISIBLE);
                        findViewById(R.id.appBarLayout).setVisibility(View.GONE);
                        findViewById(R.id.Content).setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
            
        } catch (ApiException e) {
            try {
                updateUI(null);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUI(GoogleSignInAccount account) throws IOException {
        UserSingleton us = UserSingleton.getInstance();
        us.setUserID(account);

        findViewById(R.id.Login).setVisibility(View.GONE);
        findViewById(R.id.appBarLayout).setVisibility(View.VISIBLE);
        findViewById(R.id.Content).setVisibility(View.GONE);

        userName.setText(account.getDisplayName());
        userEmail.setText((account.getEmail()));
    }
}
