package com.example.cs4532.umdalive.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cs4532.umdalive.Presenters.Presenter;
import com.example.cs4532.umdalive.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class displays the club that is selected from all clubs and the information that goes along with it.
 */
public class DisplayClubView extends AppCompatActivity {

    private Presenter presenter;
    private String clubName;
    private String description;
    private String keywords;
    private String clubOwner;
    private String ownerEmail;
    private TextView clubNameSetText;
    private TextView descriptionSetText;
    private TextView keywordSetText;
    private TextView clubOwnerSetText;
    private TextView ownerEmailSetText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_club_view);
        presenter = new Presenter(this);
        setView();
    }

    /**
     * sets up view
     */
    private void setView() {
        try {
            clubName = getIntent().getStringExtra("NAME_OF_CLUB");
            String jsonResponse = presenter.restGet("getClub", clubName);
            Log.d("DisplayClub response: ", jsonResponse);
            JSONObject clubObject = new JSONObject(jsonResponse);

            description = clubObject.get("description").toString();
            keywords = clubObject.get("keywords").toString();
            ownerEmail = clubObject.get("ownerEmail").toString();
            clubOwner = clubObject.get("clubOwner").toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        clubNameSetText = (TextView) findViewById(R.id.display_club_name);
        descriptionSetText = (TextView) findViewById(R.id.display_club_description);
        ownerEmailSetText = (TextView) findViewById(R.id.display_club_email);
        keywordSetText = (TextView) findViewById(R.id.display_clubs_keyword);
        clubOwnerSetText = (TextView) findViewById(R.id.display_club_administrator);

        clubNameSetText.setText(clubName);
        clubNameSetText.setTextSize(45); //displayed larger as it's the title
        descriptionSetText.setText(description);
        ownerEmailSetText.setText(ownerEmail);
        keywordSetText.setText(keywords);
        clubOwnerSetText.setText(clubOwner);
    }

    /**
     * Goes back to the home screen
     */
        public void clickGoHome(View view){
            Intent intent = new Intent(this, MainView.class);
            startActivity(intent);
        }

}