package com.example.cs4532.umdalive.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cs4532.umdalive.Presenters.Presenter;
import com.example.cs4532.umdalive.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by meggi on 11/7/2017.
 */

public class DisplayClubOwnerView extends AppCompatActivity {

        private Presenter presenter;
        private String clubName;
        private String description;
        private String keywords;
        private String ownerEmail;
        private String clubOwner;
        private TextView clubNameSetText;
        private TextView ownerEmailSetText;
        private TextView descriptionSetText;
        private TextView keywordSetText;
        private TextView clubOwnerSetText;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.display_club_owner_view);
            presenter = new Presenter(this);
            setView();
        }

        /**
         * sets up view for the display the club owner will see
         *
         * club owner role has permission to edit their club
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

            clubNameSetText = (TextView) findViewById(R.id.display_club_owner_name);
            clubNameSetText.setTextSize(45); //title of the club
            descriptionSetText = (TextView) findViewById(R.id.display_club_owner_description);
            ownerEmailSetText = (TextView) findViewById(R.id.display_club_email_owner);
            keywordSetText = (TextView) findViewById(R.id.display_clubs_owner_keyword);
            clubOwnerSetText = (TextView) findViewById(R.id.display_club_owner);

            clubNameSetText.setText(clubName);
            descriptionSetText.setText(description);
            ownerEmailSetText.setText(ownerEmail);
            keywordSetText.setText(keywords);
            clubOwnerSetText.setText(clubOwner);
        }

    /**
     * Goes to the editable view
     */
    public void clickToEdit(View view)
        {
            Intent intent = new Intent(this, EditableView.class);
            intent.putExtra("NAME_OF_CLUB", clubName);
            startActivity(intent);
        }

    /**
     * Goes back to the home screen
     */
    public void clickOwnerGoHome(View view)
    {
        Intent intent = new Intent (this, MainView.class);
        startActivity(intent);
    }
}

