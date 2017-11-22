package com.example.cs4532.umdalive.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

    public static final String CLUB_NAME = "com.example.kevin.umdalive.MESSAGE";

        private Presenter presenter;
        private String clubName;
        private String description;
        private String keywords;
        private String ownerEmail;
        private String administrator;
        private EditText clubNameSetText;
        private EditText descriptionSetText;
        private EditText keywordSetText;
        private EditText ownerEmailSetText;
        private EditText administratorSetText;
        private TextView invalidInput;

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
                clubName = getIntent().getStringExtra(DisplayClubOwnerView.CLUB_NAME2);
                String jsonResponse = presenter.restGet("getClub", clubName);
                Log.d("DisplayClub response: ", jsonResponse);
                JSONObject clubObject = new JSONObject(jsonResponse);
                description = clubObject.get("description").toString();
                keywords = clubObject.get("keywords").toString();
                administrator = clubObject.get("username").toString();
                ownerEmail = clubObject.get("ownerEmail").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            clubNameSetText = (EditText) findViewById(R.id.edit_title);
            descriptionSetText = (EditText) findViewById(R.id.edit_description);
           // keywordSetText = (EditText) findViewById(R.id.);
            ownerEmailSetText = (EditText) findViewById(R.id.edit_email);
            administratorSetText = (EditText) findViewById(R.id.edit_owner);
            invalidInput = (TextView) findViewById(R.id.edit_invalid_input);

            clubNameSetText.setText(clubName, TextView.BufferType.EDITABLE);
            //clubNameSetText.setTextSize(45);
            descriptionSetText.setText(description, TextView.BufferType.EDITABLE);
           // keywordSetText.setText(keywords, TextView.BufferType.EDITABLE);
            ownerEmailSetText.setText(ownerEmail, TextView.BufferType.EDITABLE);
            administratorSetText.setText(administrator, TextView.BufferType.EDITABLE);

    }


    /**
     * Saves updated information and displays it in DisplayClubOwnerView
     * @param view
     */
        public void clickToSave(View view){
            Intent intent = new Intent (this, DisplayClubOwnerView.class); //need to set everything here? or not?
           intent.putExtra(CLUB_NAME, clubName);
            startActivity(intent);
        }
}
