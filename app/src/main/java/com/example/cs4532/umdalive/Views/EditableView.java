package com.example.cs4532.umdalive.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cs4532.umdalive.Presenters.Presenter;
import com.example.cs4532.umdalive.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by meggi on 11/5/2017.
 */

public class EditableView extends AppCompatActivity{

        private Presenter presenter;
        private String clubName;
        private String description;
        private String keywords;
        private String administrator;
        private EditText clubNameSetText;
        private EditText descriptionSetText;
        private EditText keywordSetText;
        private EditText administratorSetText;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.editable_view);
            presenter = new Presenter(this);
            setView();
        }

        /**
         * sets up view
         */
        private void setView() {
            try {
                clubName = getIntent().getStringExtra(AllClubsView.CLUB_NAME);
                String jsonResponse = presenter.restGet("getClub", clubName);
                Log.d("DisplayClub response: ", jsonResponse);
                JSONObject clubObject = new JSONObject(jsonResponse);
                description = clubObject.get("description").toString();
                keywords = clubObject.get("keywords").toString();
                administrator = clubObject.get("username").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //clubNameSetText = (TextView) findViewById(R.id.display_club_name);
            //descriptionSetText = (TextView) findViewById(R.id.display_club_description);
            //keywordSetText = (TextView) findViewById(R.id.display_clubs_keyword);
            //administratorSetText = (TextView) findViewById(R.id.display_clubs_administrator);

            clubNameSetText.setText(clubName);
            clubNameSetText.setTextSize(45);
            descriptionSetText.setText(description);
            keywordSetText.setText(keywords);
            administratorSetText.setText(administrator);
        }
}
