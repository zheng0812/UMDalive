package com.example.cs4532.umdalive;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Club clubPage;
    JSONObject serverResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize pages
        clubPage = new Club(this, this);



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
                clubPage.buildPage("test");
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
}
