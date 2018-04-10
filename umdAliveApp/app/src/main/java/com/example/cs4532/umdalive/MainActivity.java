package com.example.cs4532.umdalive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //App Page Classes
    Profile profilePage;
    Club clubPage;
    AllClubs allClubsPage;


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

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_profile);

        //Initialize pages
        clubPage = new Club(this, this);
        profilePage = new Profile(this,this);

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
            findViewById(R.id.profileView).setVisibility(View.VISIBLE);

            try {
                profilePage.buildPage("57");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            findViewById(R.id.upcomingEventsView).setVisibility(View.GONE);
            findViewById(R.id.allClubsView).setVisibility(View.GONE);
            findViewById(R.id.clubView).setVisibility(View.GONE);
            findViewById(R.id.eventView).setVisibility(View.GONE);

        } else if (id == R.id.nav_events) {
            findViewById(R.id.upcomingEventsView).setVisibility(View.VISIBLE);

            findViewById(R.id.allClubsView).setVisibility(View.GONE);
            findViewById(R.id.profileView).setVisibility(View.GONE);
            findViewById(R.id.clubView).setVisibility(View.GONE);
            findViewById(R.id.eventView).setVisibility(View.GONE);

        } else if (id == R.id.nav_my_clubs) {
            findViewById(R.id.clubView).setVisibility(View.VISIBLE);

            try {
                clubPage.buildPage("Josh-kun's test club");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            findViewById(R.id.allClubsView).setVisibility(View.GONE);
            findViewById(R.id.profileView).setVisibility(View.GONE);
            findViewById(R.id.upcomingEventsView).setVisibility(View.GONE);
            findViewById(R.id.eventView).setVisibility(View.GONE);

        } else if (id == R.id.nav_all_clubs) {
            findViewById(R.id.allClubsView).setVisibility(View.VISIBLE);

            findViewById(R.id.upcomingEventsView).setVisibility(View.GONE);
            findViewById(R.id.profileView).setVisibility(View.GONE);
            findViewById(R.id.clubView).setVisibility(View.GONE);
            findViewById(R.id.eventView).setVisibility(View.GONE);
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
        findViewById(R.id.Login).setVisibility(View.GONE);
        findViewById(R.id.appBarLayout).setVisibility(View.VISIBLE);
        findViewById(R.id.profileView).setVisibility(View.VISIBLE);

        userName.setText(account.getDisplayName());
        userEmail.setText((account.getEmail()));
    }
}
